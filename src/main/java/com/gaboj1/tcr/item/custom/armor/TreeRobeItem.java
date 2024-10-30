package com.gaboj1.tcr.item.custom.armor;

import com.gaboj1.tcr.item.TCRItems;
import com.gaboj1.tcr.item.renderer.armor.IceTigerArmorRenderer;
import com.gaboj1.tcr.item.renderer.armor.TreeRobeRenderer;
import com.gaboj1.tcr.util.ItemUtil;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import it.unimi.dsi.fastutil.objects.ObjectOpenHashSet;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.decoration.ArmorStand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.extensions.common.IClientItemExtensions;
import org.antlr.runtime.tree.Tree;
import org.jetbrains.annotations.NotNull;
import software.bernie.geckolib.animatable.GeoItem;
import software.bernie.geckolib.constant.DataTickets;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animation.AnimatableManager;
import software.bernie.geckolib.core.animation.AnimationController;
import software.bernie.geckolib.core.animation.RawAnimation;
import software.bernie.geckolib.core.object.PlayState;
import software.bernie.geckolib.renderer.GeoArmorRenderer;
import software.bernie.geckolib.util.GeckoLibUtil;

import java.util.Set;
import java.util.function.Consumer;

public class TreeRobeItem extends ArmorItem implements GeoItem {
    private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);

    public TreeRobeItem(ArmorMaterial armorMaterial, Type type, Properties properties) {
        super(armorMaterial, type, properties);
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {
        controllers.add(new AnimationController<>(this, 10, state -> {
            if(state.getData(DataTickets.ENTITY).isSprinting()){
                state.setAnimation(RawAnimation.begin().thenLoop("animation.model.idle2"));
            } else {
                state.setAnimation(RawAnimation.begin().thenLoop("animation.model.idle"));
            }
            return PlayState.CONTINUE;
        }));
        controllers.add(new AnimationController<>(this, "Attack", 0, state -> PlayState.CONTINUE)
                .triggerableAnim("attack", RawAnimation.begin().thenPlay("animation.model.attack")));
    }

    public void attackAnim(ServerPlayer player){
        triggerAnim(player, GeoItem.getOrAssignId(player.getItemInHand(InteractionHand.MAIN_HAND), player.serverLevel()), "Recover", "recover");
    }

    /**
     * 集齐全套装效果
     */
    public static boolean isFullSet(Entity entity){
        return ItemUtil.isFullSets(entity, ObjectArrayList.of(
                TCRItems.TREE_ROBE_HELMET.get(),
                TCRItems.TREE_ROBE_CHESTPLATE.get(),
                TCRItems.TREE_ROBE_BOOTS.get()), 3);
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return cache;
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void initializeClient(Consumer<IClientItemExtensions> consumer) {
        consumer.accept(new IClientItemExtensions() {
            private GeoArmorRenderer<?> renderer;

            @Override
            public @NotNull HumanoidModel<?> getHumanoidArmorModel(LivingEntity livingEntity, ItemStack itemStack, EquipmentSlot equipmentSlot, HumanoidModel<?> original) {
                if (this.renderer == null)
                    this.renderer = new TreeRobeRenderer();
                this.renderer.prepForRender(livingEntity, itemStack, equipmentSlot, original);
                return this.renderer;
            }
        });
    }
}
