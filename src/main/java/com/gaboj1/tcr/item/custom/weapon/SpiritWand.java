package com.gaboj1.tcr.item.custom.weapon;

import com.gaboj1.tcr.entity.custom.boss.yggdrasil.MagicProjectile;
import com.gaboj1.tcr.item.TCRRarities;
import com.gaboj1.tcr.item.custom.armor.TreeArmorItem;
import com.gaboj1.tcr.item.renderer.SpiritWandRenderer;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.client.extensions.common.IClientItemExtensions;
import org.jetbrains.annotations.NotNull;
import software.bernie.geckolib.animatable.GeoItem;
import software.bernie.geckolib.animatable.SingletonGeoAnimatable;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animation.AnimatableManager;
import software.bernie.geckolib.core.animation.Animation;
import software.bernie.geckolib.core.animation.AnimationController;
import software.bernie.geckolib.core.animation.RawAnimation;
import software.bernie.geckolib.core.object.PlayState;
import software.bernie.geckolib.util.GeckoLibUtil;

import java.util.function.Consumer;


public class SpiritWand extends Item implements GeoItem {
    private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);

    public SpiritWand() {
        super(new Properties().stacksTo(1).rarity(TCRRarities.SHANG_PIN).defaultDurability(128));
        SingletonGeoAnimatable.registerSyncedAnimatable(this);
    }

    @Override
    public void initializeClient(Consumer<IClientItemExtensions> consumer) {
        consumer.accept(new IClientItemExtensions() {
            private SpiritWandRenderer renderer;
            @Override
            public BlockEntityWithoutLevelRenderer getCustomRenderer() {
                if(this.renderer == null){
                    renderer = new SpiritWandRenderer();
                }
                return this.renderer;
            }
        });
    }

    @Override
    public @NotNull InteractionResultHolder<ItemStack> use(@NotNull Level pLevel, @NotNull Player pPlayer, @NotNull InteractionHand pUsedHand) {
        ItemStack itemStack = pPlayer.getItemInHand(pUsedHand);
        if(!TreeArmorItem.isFullSet(pPlayer)){
            return InteractionResultHolder.pass(itemStack);
        }
        setDamage(itemStack, getDamage(itemStack) + 1);
        if(getDamage(itemStack) == getMaxDamage(itemStack)){
            itemStack.shrink(1);
        }

        if(pLevel instanceof ServerLevel serverLevel){
            CompoundTag data = itemStack.getOrCreateTag();
            if(data.getInt("AttackTimer") == 0){
                data.putInt("AttackTimer", 41);
                attackAnim(serverLevel, pPlayer, itemStack);
            }
        }

        return InteractionResultHolder.fail(itemStack);
    }

    @Override
    public void onInventoryTick(ItemStack stack, Level level, Player player, int slotIndex, int selectedIndex) {
        if(!level.isClientSide){
            CompoundTag data = stack.getOrCreateTag();
            int timer = data.getInt("AttackTimer");
            if(timer == 1){
                MagicProjectile projectile = new MagicProjectile(level, player);
                projectile.setGlowingTag(true);
                projectile.setDamage(18);
                Vec3 view = player.getViewVector(1.0F);
                projectile.shoot(view.x, view.y, view.z, 5.0F, 10.0F);
                level.addFreshEntity(projectile);
            }
            stack.getOrCreateTag().putInt("AttackTimer", Math.max(0, stack.getOrCreateTag().getInt("AttackTimer") - 1));
        }
        super.onInventoryTick(stack, level, player, slotIndex, selectedIndex);
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {
        controllers.add(new AnimationController<>(this, "idle",
                10, (animationState -> {
            animationState.getController().setAnimation(RawAnimation.begin().then("animation.model.idle", Animation.LoopType.LOOP));
            return PlayState.CONTINUE;
        })));
        controllers.add(new AnimationController<>(this, "Attack", 10, state -> PlayState.STOP)
                .triggerableAnim("attack", RawAnimation.begin().thenPlay("animation.model.attack2")));
    }

    public void attackAnim(ServerLevel level, Player player, ItemStack stack){
        triggerAnim(player, GeoItem.getOrAssignId(stack, level), "Attack", "attack");

    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return this.cache;
    }


}
