package com.gaboj1.tcr.item.custom.boss_loot;

import com.gaboj1.tcr.TheCasketOfReveriesMod;
import com.gaboj1.tcr.block.custom.DenseForestFlower;
import com.gaboj1.tcr.capability.TCRCapabilityProvider;
import com.gaboj1.tcr.datagen.TCRAdvancementData;
import com.gaboj1.tcr.block.TCRBlocks;
import com.gaboj1.tcr.entity.TCREntities;
import com.gaboj1.tcr.entity.custom.boss.yggdrasil.MagicProjectile;
import com.gaboj1.tcr.entity.custom.villager.biome1.PastoralPlainTalkableVillager;
import com.gaboj1.tcr.entity.custom.villager.biome1.PastoralPlainVillager;
import com.gaboj1.tcr.entity.custom.villager.biome1.PastoralPlainVillagerElder;
import com.gaboj1.tcr.item.TCRItems;
import com.gaboj1.tcr.item.TCRRarities;
import com.gaboj1.tcr.item.custom.armor.TreeRobeItem;
import com.gaboj1.tcr.item.renderer.TreeSpiritWandRenderer;
import com.gaboj1.tcr.util.ItemUtil;
import com.gaboj1.tcr.util.SaveUtil;
import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import net.minecraft.ChatFormatting;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Vec3i;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
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
import net.minecraft.world.level.block.Block;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.client.extensions.common.IClientItemExtensions;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.registries.RegistryObject;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.animatable.GeoItem;
import software.bernie.geckolib.animatable.SingletonGeoAnimatable;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animation.*;
import software.bernie.geckolib.core.object.PlayState;
import software.bernie.geckolib.util.GeckoLibUtil;

import java.util.*;
import java.util.function.Consumer;

/**
 * 右键空气消耗饱食度来回血
 * 右键方块消耗原木和生命召唤小树怪
 */
public class TreeSpiritWand extends MagicWeapon implements GeoItem {

    private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);
    protected static final UUID MAX_HEALTH_UUID = UUID.fromString("FA123E1C-4180-4865-B01B-BCCE1234ACA9");
    private static final List<RegistryObject<Block>> FLOWER_BLOCKS = new ArrayList<>();

    public TreeSpiritWand() {
        super(new Item.Properties().stacksTo(1).rarity(TCRRarities.TE_PIN).defaultDurability(128), 7d);
        SingletonGeoAnimatable.registerSyncedAnimatable(this);
        FLOWER_BLOCKS.add(TCRBlocks.LAZY_ROSE);
        FLOWER_BLOCKS.add(TCRBlocks.MELANCHOLIC_ROSE);
        FLOWER_BLOCKS.add(TCRBlocks.THIRST_BLOOD_ROSE);
    }

    /**
     * 右键空气
     */
    @Override
    public @NotNull InteractionResultHolder<ItemStack> use(@NotNull Level pLevel, Player pPlayer, @NotNull InteractionHand pUsedHand) {
        ItemStack itemStack = pPlayer.getItemInHand(pUsedHand);
        if(pPlayer instanceof ServerPlayer serverPlayer){
            //花海
            if(pPlayer.isShiftKeyDown()){
                if(TreeRobeItem.isFullSet(serverPlayer) || serverPlayer.isCreative()){
                    if(!serverPlayer.isCreative()){
                        serverPlayer.getCooldowns().addCooldown(this, 300);
                    }
                    ItemStack chest = pPlayer.getItemBySlot(EquipmentSlot.CHEST);
                    if(chest.getItem() instanceof TreeRobeItem treeRobeItem){
                        treeRobeItem.attackAnim(serverPlayer);
                    }
                    pPlayer.getPersistentData().putInt("start_flower_sea", 119);
                } else {
                    pPlayer.displayClientMessage(TheCasketOfReveriesMod.getInfo("need_suit"), true);
                }
            } else {
                if(!serverPlayer.isCreative()){
                    serverPlayer.getCooldowns().addCooldown(this, 20);
                }
                if(itemStack.getOrCreateTag().getInt("AttackTimer") == 0){
                    itemStack.getOrCreateTag().putInt("AttackTimer", 15);
                    attackAnim(serverPlayer);
                }
            }
        }
        return InteractionResultHolder.fail(itemStack);
    }

    @Override
    public void inventoryTick(@NotNull ItemStack itemStack, @NotNull Level level, @NotNull Entity entity, int p_41407_, boolean p_41408_) {
        super.inventoryTick(itemStack, level, entity, p_41407_, p_41408_);
        //延时发射
        if(entity instanceof ServerPlayer serverPlayer){
            if(itemStack.getOrCreateTag().getInt("AttackTimer") == 1){
                MagicProjectile projectile = new MagicProjectile(level, serverPlayer);
                projectile.setGlowingTag(true);
                projectile.setDamage(18);
                projectile.shootFromRotation(serverPlayer, serverPlayer.getXRot(), serverPlayer.getYRot(), 0.0F, 4.0F, 1.0F);
                level.addFreshEntity(projectile);
            }
            itemStack.getOrCreateTag().putInt("AttackTimer", Math.max(0, itemStack.getOrCreateTag().getInt("AttackTimer") - 1));
        }

        int startFlowerSeaCnt = entity.getPersistentData().getInt("start_flower_sea");
        if(startFlowerSeaCnt > 0){
            entity.getPersistentData().putInt("start_flower_sea", startFlowerSeaCnt - 1);
            if(startFlowerSeaCnt % 20 == 0){
                if(entity instanceof Player player){
                    player.getCapability(TCRCapabilityProvider.TCR_PLAYER).ifPresent((tcrPlayer -> {
                        float dis = (120 - startFlowerSeaCnt) / 5.0F;
                        // 获取以玩家为中心、dis为半径的圆上所有方块的位置
                        int radius = Math.round(dis);
                        BlockPos blockPos = player.getOnPos().above();
                        // 以0到360度的角度遍历，生成圆周上的点
                        for (int angle = 0; angle < 360; angle++) {
                            double radians = Math.toRadians(angle);
                            int x = (int) Math.round(radius * Math.cos(radians));
                            int z = (int) Math.round(radius * Math.sin(radians));
                            BlockPos flowerPos = blockPos.offset(x, 0, z);
                            if(flowerPos.equals(blockPos) || flowerPos.closerThan(blockPos, 2)){
                                continue;
                            }
                            if(level.getBlockState(flowerPos).isAir() && flowerPos.distSqr(player.getOnPos()) > 3 * 3){
                                level.setBlock(flowerPos, FLOWER_BLOCKS.get(player.getRandom().nextInt(FLOWER_BLOCKS.size())).get().defaultBlockState(), 3);
                                Vec3i pos = new Vec3i(flowerPos.getX(), flowerPos.getY(), flowerPos.getZ());
                                tcrPlayer.flowerPos.add(pos);
                            }
                            level.explode(null, player.damageSources().explosion(player, player), null, flowerPos.getCenter(), 0.8F, false, Level.ExplosionInteraction.NONE);

                        }
                        for(Vec3i vec3i : tcrPlayer.flowerPos){
                            level.explode(null, player.damageSources().explosion(player, player), null, new Vec3(vec3i.getX(), vec3i.getY(), vec3i.getZ()), 1.2F, false, Level.ExplosionInteraction.NONE);
                        }
                    }));
                }
            }
        } else {
            if(entity instanceof Player player){
                player.getCapability(TCRCapabilityProvider.TCR_PLAYER).ifPresent(tcrPlayer -> {
                    for(Vec3i pos : tcrPlayer.flowerPos){
                        BlockPos flowerPos = new BlockPos(pos.getX(), pos.getY(), pos.getZ());
                        if(level.getBlockState(flowerPos).getBlock() instanceof DenseForestFlower){
                            level.destroyBlock(flowerPos, false);
                        }
                    }
                    tcrPlayer.flowerPos.clear();
                });
            }
        }
    }

    /**
     * 右键方块
     * 召唤小怪还是留给上一层级用吧
     */
    @Override
    public @NotNull InteractionResult useOn(UseOnContext pContext) {
        if(pContext.getPlayer() instanceof ServerPlayer serverPlayer){
            ItemStack itemStack = serverPlayer.getItemInHand(pContext.getHand());
            if(!serverPlayer.isCreative()){
                itemStack.setDamageValue(itemStack.getDamageValue()+1);
            }
        }

        return InteractionResult.FAIL;
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
                if(instance != null && cnt < 10){
                    instance.addPermanentModifier(new AttributeModifier(UUID.fromString("FA123E1C-4180-4865-B01B-BCCE1234ACA"+cnt), "killVillagerReward"+cnt++, 2.0f, AttributeModifier.Operation.ADDITION));
                    player.displayClientMessage(Component.translatable("info.the_casket_of_reveries.health_added_from_villager1"), true);
                    player.getMainHandItem().getOrCreateTag().putInt("cnt", cnt);
                }
            }
        }
    }

    /**
     * 玩家属性调整
     */
    @Override
    public Multimap<Attribute, AttributeModifier> getDefaultAttributeModifiers(EquipmentSlot equipmentSlot) {
        if (equipmentSlot == EquipmentSlot.MAINHAND) {
            ImmutableMultimap.Builder<Attribute, AttributeModifier> builder = ImmutableMultimap.builder();
            builder.putAll(super.getDefaultAttributeModifiers(equipmentSlot));
            builder.put(Attributes.ATTACK_DAMAGE, new AttributeModifier(BASE_ATTACK_DAMAGE_UUID, "Item modifier", 7d, AttributeModifier.Operation.ADDITION));
            builder.put(Attributes.ATTACK_SPEED, new AttributeModifier(BASE_ATTACK_SPEED_UUID, "Item modifier", -2.4, AttributeModifier.Operation.ADDITION));
            builder.put(Attributes.MAX_HEALTH, new AttributeModifier(MAX_HEALTH_UUID, "Item modifier", 1, AttributeModifier.Operation.ADDITION));
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
        if(pStack.getOrCreateTag().getBoolean("fromBoss")){
            pTooltipComponents.add(Component.translatable(this.getDescriptionId()+".usage3", pStack.getOrCreateTag().getInt("cnt")).withStyle(ChatFormatting.RED, ChatFormatting.BOLD));
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

    public void attackAnim(ServerPlayer player){
        triggerAnim(player, GeoItem.getOrAssignId(player.getMainHandItem(), player.serverLevel()), "Attack", "attack");
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {
        controllers.add(new AnimationController<>(this, "idle",
                10, (animationState -> {
            animationState.getController().setAnimation(RawAnimation.begin().then("animation.model.idle", Animation.LoopType.LOOP));
            return PlayState.CONTINUE;
        })));
        controllers.add(new AnimationController<>(this, "Attack", 0, state -> PlayState.STOP)
                .triggerableAnim("attack", RawAnimation.begin().thenPlay("animation.model.attack")));
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return this.cache;
    }

}
