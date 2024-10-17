package com.gaboj1.tcr.item.custom.boss_loot;

import com.gaboj1.tcr.TCRConfig;
import com.gaboj1.tcr.datagen.TCRAdvancementData;
import com.gaboj1.tcr.block.TCRBlocks;
import com.gaboj1.tcr.entity.TCREntities;
import com.gaboj1.tcr.entity.custom.villager.biome1.PastoralPlainTalkableVillager;
import com.gaboj1.tcr.entity.custom.villager.biome1.PastoralPlainVillager;
import com.gaboj1.tcr.entity.custom.villager.biome1.PastoralPlainVillagerElder;
import com.gaboj1.tcr.item.TCRItems;
import com.gaboj1.tcr.item.renderer.TreeSpiritWandRenderer;
import com.gaboj1.tcr.util.ItemUtil;
import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import net.minecraft.ChatFormatting;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
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
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.animatable.GeoItem;
import software.bernie.geckolib.animatable.SingletonGeoAnimatable;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animation.*;
import software.bernie.geckolib.core.object.PlayState;
import software.bernie.geckolib.util.GeckoLibUtil;

import java.util.List;
import java.util.Random;
import java.util.function.Consumer;

/**
 * 右键空气消耗饱食度来回血
 * 右键方块消耗原木和生命召唤小树怪
 */
public class TreeSpiritWand extends MagicWeapon implements GeoItem {

    private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);

    public TreeSpiritWand() {
        super(new Item.Properties().stacksTo(1).rarity(Rarity.EPIC).defaultDurability(128), 7d);
        SingletonGeoAnimatable.registerSyncedAnimatable(this);
    }

    //右键空气消耗饱食度来回血
    @Override
    public @NotNull InteractionResultHolder<ItemStack> use(@NotNull Level pLevel, Player pPlayer, @NotNull InteractionHand pUsedHand) {
        ItemStack itemStack = pPlayer.getItemInHand(pUsedHand);
        if(pPlayer.isCreative()){
            return InteractionResultHolder.pass(itemStack);
        }
        FoodData foodData = pPlayer.getFoodData();
        if(foodData.getFoodLevel() == 0 || pPlayer.getHealth() == pPlayer.getMaxHealth()){
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
    public @NotNull InteractionResult useOn(UseOnContext pContext) {
        BlockPos pos = pContext.getClickedPos();
        Level level = pContext.getLevel();
        Player player = pContext.getPlayer();
        if(level instanceof ServerLevel serverLevel){
            assert player != null;
            if(!player.isCreative() && ItemUtil.searchAndConsumeItem(player, TCRBlocks.DENSE_FOREST_SPIRIT_TREE_LOG.get().asItem(), TCRConfig.SPIRIT_LOG_CONSUME.get()) == 0){
                player.displayClientMessage(Component.translatable(this.getDescriptionId()+".no_spirit_tree"), true);
                return InteractionResult.PASS;
            }
            player.hurt(level.damageSources().magic(), 2f);
            if(player.isDeadOrDying()){
                TCRAdvancementData.getAdvancement("die_for_summon",(ServerPlayer) player);
            }
            ItemStack itemStack = player.getItemInHand(pContext.getHand());//pContext.getItemInHand()不知道ok不ok
            summonAnim(serverLevel,player,itemStack);
            serverLevel.playSound(null,player.getX(),player.getY(),player.getZ(), SoundEvents.EVOKER_PREPARE_SUMMON, SoundSource.BLOCKS,1,1);
            TCREntities.SMALL_TREE_MONSTER.get().spawn(serverLevel, pos.above(), MobSpawnType.SPAWN_EGG).tame(player);


            if(!player.isCreative())
                itemStack.setDamageValue(itemStack.getDamageValue()+1);
        }

        return InteractionResult.PASS;
    }

    /**
     * 1 / 30的概率吸取生命
     */
    public static void onKill(LivingDeathEvent event){
        ItemStack stack = event.getEntity().getMainHandItem();
        if(new Random().nextInt(0,30) == 5 &&event.getSource().getEntity() instanceof Player player && stack.is(TCRItems.TREE_SPIRIT_WAND.get()) && stack.getOrCreateTag().getBoolean("fromBoss")){
            LivingEntity entity = event.getEntity();
            if(entity instanceof PastoralPlainVillager || entity instanceof PastoralPlainTalkableVillager || entity instanceof PastoralPlainVillagerElder){
                AttributeInstance instance = player.getAttribute(Attributes.MAX_HEALTH);
                int cnt = player.getMainHandItem().getOrCreateTag().getInt("cnt");
                if(instance != null){
                    instance.addPermanentModifier(new AttributeModifier("killVillagerReward"+cnt++, 2.0f, AttributeModifier.Operation.ADDITION));
                    player.displayClientMessage(Component.translatable("info.the_casket_of_reveries.health_added_from_villager1"), true);
                    player.getMainHandItem().getOrCreateTag().putInt("cnt", cnt);
                }
            }
        }
    }

    /**
     * 平A伤害调整
     */
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

    /**
     *获得近战法师成就
     */
    @Override
    public boolean hurtEnemy(ItemStack itemStack, @NotNull LivingEntity pTarget, @NotNull LivingEntity pAttacker) {
        itemStack.setDamageValue(itemStack.getDamageValue()+1);
        if(pAttacker instanceof ServerPlayer serverPlayer) {
            TCRAdvancementData.getAdvancement("melee_mage", serverPlayer);
        }

        return super.hurtEnemy(itemStack, pTarget, pAttacker);
    }

    @Override
    public void appendHoverText(@NotNull ItemStack pStack, @Nullable Level pLevel, @NotNull List<Component> pTooltipComponents, @NotNull TooltipFlag pIsAdvanced) {
        super.appendHoverText(pStack, pLevel, pTooltipComponents, pIsAdvanced);
        pTooltipComponents.add(Component.translatable(this.getDescriptionId()+".usage1"));
        pTooltipComponents.add(Component.translatable(this.getDescriptionId()+".usage2"));
        pTooltipComponents.add(Component.translatable(this.getDescriptionId()+".usage3"));
        if(pStack.getOrCreateTag().getBoolean("fromBoss")){
            pTooltipComponents.add(Component.translatable(this.getDescriptionId()+".usage4").withStyle(ChatFormatting.RED, ChatFormatting.BOLD));
        }
    }

    @Override
    public void initializeClient(Consumer<IClientItemExtensions> consumer) {
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

    private PlayState predicate(AnimationState<?> animationState) {
        animationState.getController().setAnimation(RawAnimation.begin().then("idle", Animation.LoopType.LOOP));
        return PlayState.CONTINUE;
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return this.cache;
    }

}
