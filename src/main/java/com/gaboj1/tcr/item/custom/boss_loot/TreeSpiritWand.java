package com.gaboj1.tcr.item.custom.boss_loot;

import com.gaboj1.tcr.TCRConfig;
import com.gaboj1.tcr.TheCasketOfReveriesMod;
import com.gaboj1.tcr.init.TCRModBlocks;
import com.gaboj1.tcr.init.TCRModEntities;
import com.gaboj1.tcr.item.renderer.TreeSpiritWandRenderer;
import com.gaboj1.tcr.util.ItemUtil;
import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementProgress;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.food.FoodData;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraftforge.client.extensions.common.IClientItemExtensions;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.animatable.GeoItem;
import software.bernie.geckolib.animatable.SingletonGeoAnimatable;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animation.*;
import software.bernie.geckolib.core.object.PlayState;
import software.bernie.geckolib.util.GeckoLibUtil;
import software.bernie.geckolib.util.RenderUtils;

import java.util.List;
import java.util.function.Consumer;

public class TreeSpiritWand extends MagicWeapon implements GeoItem {

    private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);

    public TreeSpiritWand() {
        super(new Item.Properties().stacksTo(1).rarity(Rarity.EPIC).defaultDurability(128));
        SingletonGeoAnimatable.registerSyncedAnimatable(this);
    }

    //右键空气消耗饱食度来回血
    @Override
    public InteractionResultHolder<ItemStack> use(Level pLevel, Player pPlayer, InteractionHand pUsedHand) {
        ItemStack itemStack = pPlayer.getItemInHand(pUsedHand);
        if(pPlayer.isCreative()){
            return InteractionResultHolder.pass(itemStack);
        }
        FoodData foodData = pPlayer.getFoodData();
        if(foodData.getFoodLevel()==0  || pPlayer.getHealth() == pPlayer.getMaxHealth()){
            return InteractionResultHolder.pass(itemStack);
        }
        if(pLevel instanceof ServerLevel serverLevel){
            recoverAnim(serverLevel,pPlayer,itemStack);
            serverLevel.playSound(null,pPlayer.getX(),pPlayer.getY(),pPlayer.getZ(), SoundEvents.BEACON_ACTIVATE, SoundSource.BLOCKS,1,1);
        }
        foodData.setFoodLevel(foodData.getFoodLevel()-TCRConfig.TREE_SPIRIT_WAND_HUNGRY_CONSUME.get());
        pPlayer.heal(TCRConfig.TREE_SPIRIT_WAND_HEAL.get());
        if(!pPlayer.isCreative())
            itemStack.setDamageValue(itemStack.getDamageValue()+1);
        return InteractionResultHolder.pass(itemStack);
    }

    //右键方块召唤小树怪
    @Override
    public InteractionResult useOn(UseOnContext pContext) {
        BlockPos pos = pContext.getClickedPos();
        Level level = pContext.getLevel();
        Player player = pContext.getPlayer();
        if(level instanceof ServerLevel serverLevel){
            if(!player.isCreative() && ItemUtil.searchAndConsumeItem(player, TCRModBlocks.DENSE_FOREST_SPIRIT_TREE_LOG.get().asItem(), TCRConfig.SPIRIT_LOG_CONSUME.get()) == 0){
                player.displayClientMessage(Component.translatable(this.getDescriptionId()+".no_spirit_tree"), true);
                return InteractionResult.PASS;
            }
            player.hurt(level.damageSources().magic(), 2f);
            if(player.isDeadOrDying()){
                Advancement _adv = player.getServer().getAdvancements().getAdvancement(new ResourceLocation(TheCasketOfReveriesMod.MOD_ID,"die_for_summon"));
                AdvancementProgress _ap = ((ServerPlayer) player).getAdvancements().getOrStartProgress(_adv);

                if (!_ap.isDone()) {
                    for (String criteria : _ap.getRemainingCriteria())
                        ((ServerPlayer) player).getAdvancements().award(_adv, criteria);
                }
            }
            ItemStack itemStack = player.getItemInHand(pContext.getHand());//pContext.getItemInHand()不知道ok不ok
            summonAnim(serverLevel,player,itemStack);
            serverLevel.playSound(null,player.getX(),player.getY(),player.getZ(), SoundEvents.EVOKER_PREPARE_SUMMON, SoundSource.BLOCKS,1,1);
            TCRModEntities.SMALL_TREE_MONSTER.get().spawn(serverLevel, pos.above(), MobSpawnType.SPAWN_EGG).tame(player);


            if(!player.isCreative())
                itemStack.setDamageValue(itemStack.getDamageValue()+1);
        }

        return InteractionResult.PASS;
    }

    //平A伤害调整
    @Override
    public Multimap<Attribute, AttributeModifier> getDefaultAttributeModifiers(EquipmentSlot equipmentSlot) {
        if (equipmentSlot == EquipmentSlot.MAINHAND) {
            ImmutableMultimap.Builder<Attribute, AttributeModifier> builder = ImmutableMultimap.builder();
            builder.putAll(super.getDefaultAttributeModifiers(equipmentSlot));
            builder.put(Attributes.ATTACK_DAMAGE, new AttributeModifier(BASE_ATTACK_DAMAGE_UUID, "Item modifier", 7d, AttributeModifier.Operation.ADDITION));
            builder.put(Attributes.ATTACK_SPEED, new AttributeModifier(BASE_ATTACK_SPEED_UUID, "Item modifier", -2.4, AttributeModifier.Operation.ADDITION));

            return builder.build();
        }
        return super.getDefaultAttributeModifiers(equipmentSlot);
    }

    //近战法师成就
    @Override
    public boolean hurtEnemy(ItemStack itemStack, LivingEntity pTarget, LivingEntity pAttacker) {
        itemStack.setDamageValue(itemStack.getDamageValue()+1);
        if(pAttacker instanceof ServerPlayer serverPlayer) {
            Advancement _adv = serverPlayer.getServer().getAdvancements().getAdvancement(new ResourceLocation(TheCasketOfReveriesMod.MOD_ID, "melee_mage"));
            AdvancementProgress _ap = serverPlayer.getAdvancements().getOrStartProgress(_adv);
            if (!_ap.isDone()) {
                for (String criteria : _ap.getRemainingCriteria())
                    serverPlayer.getAdvancements().award(_adv, criteria);
            }
        }
        return super.hurtEnemy(itemStack, pTarget, pAttacker);
    }

    @Override
    public void appendHoverText(ItemStack pStack, @Nullable Level pLevel, List<Component> pTooltipComponents, TooltipFlag pIsAdvanced) {
        super.appendHoverText(pStack, pLevel, pTooltipComponents, pIsAdvanced);
        pTooltipComponents.add(Component.translatable(this.getDescriptionId()+".usage1"));
        pTooltipComponents.add(Component.translatable(this.getDescriptionId()+".usage2"));
        pTooltipComponents.add(Component.translatable(this.getDescriptionId()+".usage3"));
    }

    @Override
    public void initializeClient(Consumer<IClientItemExtensions> consumer) {

//        System.out.println("get");
        consumer.accept(new IClientItemExtensions() {
            private TreeSpiritWandRenderer renderer;
            @Override
            public BlockEntityWithoutLevelRenderer getCustomRenderer() {
                if(this.renderer == null){
                    renderer = new TreeSpiritWandRenderer();
                }
                return this.renderer;
            }
        });
    }

    public void recoverAnim(ServerLevel level, Player player, ItemStack stack){
        triggerAnim(player, GeoItem.getOrAssignId(stack, level), "Recover", "recover");
    }

    public void summonAnim(ServerLevel level, Player player, ItemStack stack){
        triggerAnim(player, GeoItem.getOrAssignId(stack, level), "Summon", "summon");
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {
        controllers.add(new AnimationController<>(this, "idle",
                0, this::predicate));
        controllers.add(new AnimationController<>(this, "Recover", 0, state -> PlayState.STOP)
                .triggerableAnim("recover", RawAnimation.begin().thenPlay("recover")));
        controllers.add(new AnimationController<>(this, "Summon", 0, state -> PlayState.STOP)
                .triggerableAnim("summon", RawAnimation.begin().thenPlay("summon")));

    }

    private PlayState predicate(AnimationState animationState) {
        animationState.getController().setAnimation(RawAnimation.begin().then("idle", Animation.LoopType.LOOP));
        return PlayState.CONTINUE;
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return this.cache;
    }

    @Override
    public double getTick(Object o) {
        return RenderUtils.getCurrentTick();
    }
}
