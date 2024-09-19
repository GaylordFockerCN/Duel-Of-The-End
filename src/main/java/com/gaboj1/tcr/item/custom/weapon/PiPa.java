package com.gaboj1.tcr.item.custom.weapon;

import com.gaboj1.tcr.client.TCRSounds;
import com.gaboj1.tcr.entity.custom.boss.second_boss.SecondBossEntity;
import com.gaboj1.tcr.event.PlayerModelEvent;
import com.gaboj1.tcr.item.custom.PoseItem;
import com.gaboj1.tcr.item.renderer.PiPaRenderer;
import com.gaboj1.tcr.util.EntityUtil;
import net.minecraft.client.model.PlayerModel;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.extensions.common.IClientItemExtensions;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.animatable.GeoItem;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animation.AnimatableManager;
import software.bernie.geckolib.core.animation.Animation;
import software.bernie.geckolib.core.animation.AnimationController;
import software.bernie.geckolib.core.animation.RawAnimation;
import software.bernie.geckolib.core.object.PlayState;
import software.bernie.geckolib.util.GeckoLibUtil;

import java.util.List;
import java.util.function.Consumer;

public class PiPa extends Item implements GeoItem, PoseItem {
    private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);
    public PiPa() {
        super(new Properties().rarity(Rarity.RARE).stacksTo(1).durability(256));
    }

    @Override
    public void appendHoverText(@NotNull ItemStack itemStack, @Nullable Level level, @NotNull List<Component> list, @NotNull TooltipFlag flag) {
        super.appendHoverText(itemStack, level, list, flag);
        list.add(Component.translatable(this.getDescriptionId()+".usage"));
    }

    @Override
    public boolean hurtEnemy(@NotNull ItemStack itemStack, @NotNull LivingEntity pTarget, @NotNull LivingEntity pAttacker) {
        itemStack.setDamageValue(itemStack.getDamageValue() + 50);
        if(itemStack.getDamageValue() > itemStack.getMaxDamage()){
            itemStack.shrink(1);
        }
        pAttacker.level().playSound(null, pTarget.getX(), pTarget.getY(), pTarget.getZ(), SoundEvents.SHIELD_BREAK, SoundSource.BLOCKS, 1, 1);
        return super.hurtEnemy(itemStack, pTarget, pAttacker);
    }

    /**
     * 右键发射音波
     */
    @Override
    public @NotNull InteractionResultHolder<ItemStack> use(@NotNull Level level, @NotNull Player player, @NotNull InteractionHand hand) {
        ItemStack stack = player.getItemInHand(hand);
        if(level instanceof ServerLevel serverLevel && stack.getDamageValue() < stack.getMaxDamage() - 1){
            Vec3 attacker = player.getEyePosition();
            Vec3 target = player.getViewVector(1.0F).normalize();
            for(int i = 1; i < Mth.floor(target.length()) + 7; ++i) {
                Vec3 pos = attacker.add(target.scale(i));
                serverLevel.sendParticles(ParticleTypes.SONIC_BOOM, pos.x, pos.y, pos.z, 1, 0, 0, 0, 0);
            }
            stack.setDamageValue(stack.getDamageValue() + 1);
            level.playSound(null, player.getX(), player.getY(), player.getZ(), TCRSounds.PIPA.get(), SoundSource.BLOCKS, 1, 1);
            for(LivingEntity entity : EntityUtil.getNearByEntities(serverLevel, player, 20)){
                if(EntityUtil.isInFront(entity, player, 20) && entity.distanceTo(player) < 10){
                    entity.hurt(player.damageSources().sonicBoom(player), 5);
                }
                //对第二boss有奇效
                if(entity instanceof SecondBossEntity secondBoss){
                    secondBoss.hurtByPiPa(player);
                }

            }
        }
        return InteractionResultHolder.fail(stack);//取消播放动画
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {
        controllers.add(new AnimationController<>(this, "idle",
                0, animationState -> {
                animationState.getController().setAnimation(RawAnimation.begin().then("idle", Animation.LoopType.LOOP));
                return PlayState.CONTINUE;
        }));
    }

    @Override
    public void initializeClient(Consumer<IClientItemExtensions> consumer) {
        consumer.accept(new IClientItemExtensions() {
            private PiPaRenderer renderer;
            @Override
            public BlockEntityWithoutLevelRenderer getCustomRenderer() {
                if(this.renderer == null){
                    renderer = new PiPaRenderer();
                }
                return this.renderer;
            }
        });
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return cache;
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void adjustInMainHand(PlayerModelEvent.SetupAngles.Post event, PlayerModel<?> model, boolean right) {

    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void adjustInOffHand(PlayerModelEvent.SetupAngles.Post event, PlayerModel<?> model, boolean right) {

    }
}
