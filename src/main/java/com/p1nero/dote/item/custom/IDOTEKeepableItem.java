package com.p1nero.dote.item.custom;

import com.p1nero.dote.util.ItemUtil;
import net.minecraft.core.NonNullList;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.items.IItemHandlerModifiable;
import top.theillusivec4.curios.api.CuriosApi;

import java.util.concurrent.atomic.AtomicBoolean;

/**
 * 进出维度不会被删除的物品
 */
public interface IDOTEKeepableItem {
    default boolean shouldKeepWhenEnterDim(){
        return true;
    }
    default boolean shouldKeepWhenExitDim(){
        return true;
    }

    /**
     * 确保不存在非DOTEKeepableItem的物品
     */
    static boolean check(Player player, boolean isEnter){
        AtomicBoolean checkCurios = new AtomicBoolean(true);
        if(ModList.get().isLoaded("curios")){
            CuriosApi.getCuriosInventory(player).ifPresent(iCuriosItemHandler -> {
                IItemHandlerModifiable itemHandler = iCuriosItemHandler.getEquippedCurios();
                for(int i = 0; i < itemHandler.getSlots(); i++){
                    ItemStack stack = itemHandler.getStackInSlot(i);
                    if(isEnter){
                        if(!stack.isEmpty() && !(stack.getItem() instanceof IDOTEKeepableItem doteKeepableItem && doteKeepableItem.shouldKeepWhenEnterDim())){
                            checkCurios.set(false);
                            break;
                        }
                    } else {
                        if(!stack.isEmpty() && !(stack.getItem() instanceof IDOTEKeepableItem doteKeepableItem && doteKeepableItem.shouldKeepWhenExitDim())){
                            checkCurios.set(false);
                            break;
                        }
                    }
                }
            });
        }
        for(NonNullList<ItemStack> list : ItemUtil.getCompartments(player)){
            for (ItemStack stack : list){
                if(isEnter){
                    if(!stack.isEmpty() && !(stack.getItem() instanceof IDOTEKeepableItem doteKeepableItem && doteKeepableItem.shouldKeepWhenEnterDim())){
                        return false;
                    }
                } else {
                    if(!stack.isEmpty() && !(stack.getItem() instanceof IDOTEKeepableItem doteKeepableItem && doteKeepableItem.shouldKeepWhenExitDim())){
                        return false;
                    }
                }
            }
        }
        return checkCurios.get();
    }

}
