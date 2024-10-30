package com.gaboj1.tcr.item.custom.armor;

import com.gaboj1.tcr.datagen.tags.TCREntityTagGenerator;
import com.gaboj1.tcr.item.TCRItems;
import com.gaboj1.tcr.item.renderer.armor.TreeArmorRenderer;
import com.gaboj1.tcr.util.ItemUtil;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.ChatFormatting;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.decoration.ArmorStand;
import net.minecraft.world.item.*;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.extensions.common.IClientItemExtensions;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.animatable.GeoItem;
import software.bernie.geckolib.constant.DataTickets;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animation.AnimatableManager;
import software.bernie.geckolib.core.animation.AnimationController;
import software.bernie.geckolib.core.animation.RawAnimation;
import software.bernie.geckolib.core.object.PlayState;
import software.bernie.geckolib.renderer.GeoArmorRenderer;
import software.bernie.geckolib.util.GeckoLibUtil;

import java.util.List;
import java.util.function.Consumer;

public final class TreeArmorItem extends ArmorItem implements GeoItem {
    private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);

    public TreeArmorItem(ArmorMaterial armorMaterial, Type type, Properties properties) {
        super(armorMaterial, type, properties);
    }

    /**
     * 获得再生效果，但着火时间延长
     */
    public static void onFullSet(LivingEntity livingEntity){
        if(livingEntity.level() instanceof ServerLevel){
            if(livingEntity.isOnFire()){
                livingEntity.setRemainingFireTicks(100);
                livingEntity.getArmorSlots().forEach((itemStack -> {
                    itemStack.setDamageValue(itemStack.getDamageValue() + 1);
                    if(itemStack.getDamageValue() >= itemStack.getMaxDamage()){
                        itemStack.shrink(1);
                    }
                }));
            } else {
                if(!livingEntity.hasEffect(MobEffects.REGENERATION)){
                    livingEntity.addEffect(new MobEffectInstance(MobEffects.REGENERATION, 100, 1));
                }
            }
        }
    }

    public static void onLivingHurt(LivingHurtEvent event){
        LivingEntity entity = event.getEntity();
        //减伤来自密林的怪物的攻击
        if (isFullSet(entity) && event.getSource().getEntity() != null && event.getSource().getEntity().getType().is(TCREntityTagGenerator.MOB_IN_DENSE_FOREST)) {
            event.setAmount(event.getAmount() * 0.45F);
        }
    }

    public static boolean isFullSet(Entity entity){
        return ItemUtil.isFullSets(entity, ObjectArrayList.of(
                TCRItems.TREE_BOOTS.get(),
                TCRItems.TREE_LEGGINGS.get(),
                TCRItems.TREE_CHESTPLATE.get(),
                TCRItems.TREE_HELMET.get()));
    }

    @Override
    public void appendHoverText(@NotNull ItemStack itemStack, @Nullable Level level, @NotNull List<Component> list, @NotNull TooltipFlag flag) {
        super.appendHoverText(itemStack, level, list, flag);
        list.add(Component.translatable(this.getDescriptionId()+".usage").withStyle(ChatFormatting.BLUE));
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void initializeClient(Consumer<IClientItemExtensions> consumer) {
        consumer.accept(new IClientItemExtensions() {
            private GeoArmorRenderer<?> renderer;

            @Override
            public @NotNull HumanoidModel<?> getHumanoidArmorModel(LivingEntity livingEntity, ItemStack itemStack, EquipmentSlot equipmentSlot, HumanoidModel<?> original) {
                if (this.renderer == null)
                    this.renderer = new TreeArmorRenderer();
                this.renderer.prepForRender(livingEntity, itemStack, equipmentSlot, original);
                return this.renderer;
            }
        });
    }

    /**
     * 集齐套装才能播放效果
     */
    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {
        controllers.add(new AnimationController<>(this, 20, state -> {
            state.setAnimation(RawAnimation.begin().thenLoop("animation.model.idle"));
            Entity entity = state.getData(DataTickets.ENTITY);

            if (entity instanceof ArmorStand){
                return PlayState.CONTINUE;
            }

            for (ItemStack stack : entity.getArmorSlots()) {
                if (stack.isEmpty()){
                    return PlayState.STOP;
                }
            }

            return isFullSet(entity) ? PlayState.CONTINUE : PlayState.STOP;
        }));
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return this.cache;
    }
}