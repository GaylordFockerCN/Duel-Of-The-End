package com.gaboj1.tcr.item.custom.armor;

import com.gaboj1.tcr.datagen.TCRAdvancementData;
import com.gaboj1.tcr.effect.TCREffects;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class OrichalcumArmorItem extends TCRArmorItem {
    public OrichalcumArmorItem(ArmorMaterial armorMaterial, Type type, Properties properties) {
        super(armorMaterial, type, properties.rarity(Rarity.RARE));
    }

    //没鸟用，飞舞
//    @Override
//    public void onInventoryTick(ItemStack stack, Level level, Player player, int slotIndex, int selectedIndex) {
//        super.onInventoryTick(stack, level, player, slotIndex, selectedIndex);
//        if(player instanceof ServerPlayer serverPlayer){
//            System.out.println("ok is on");
//            if(TCRArmorItem.hasOrichalcumArmorSet(player)){
//                TCRAdvancementData.getAdvancement("spend_money_like_water", serverPlayer);
//                serverPlayer.addEffect(new MobEffectInstance(TCREffects.ORICHALCUM.get(), 200, 0, false, true, true));
//            }
//        }
//    }


    /**
     * {@link net.minecraft.world.item.ArmorItem#onArmorTick(ItemStack, Level, Player)}不让用，
     * {@link net.minecraft.world.item.ArmorItem#onInventoryTick(ItemStack, Level, Player, int, int)} 没卵用
     * 只能学the Aether的来做这个小妙招。
     * @param livingEntity
     */
    public static void handleArmorAbility(LivingEntity livingEntity){
        if(livingEntity instanceof ServerPlayer serverPlayer){
            if(TCRArmorItem.hasOrichalcumArmorSet(serverPlayer)){
                TCRAdvancementData.getAdvancement("spend_money_like_water", serverPlayer);
                serverPlayer.addEffect(new MobEffectInstance(TCREffects.ORICHALCUM.get(), 200, 0, false, true, true));
            }
        }
    }

    @Override
    public void appendHoverText(ItemStack p_41421_, @Nullable Level p_41422_, List<Component> components, TooltipFlag p_41424_) {
        components.add(Component.translatable(this.getDescriptionId()+".usage"));
        super.appendHoverText(p_41421_, p_41422_, components, p_41424_);
    }
}
