package com.gaboj1.tcr.entity.custom.villager;

import com.gaboj1.tcr.gui.screen.LinkListStreamDialogueScreenBuilder;
import com.gaboj1.tcr.gui.screen.TreeNode;
import com.gaboj1.tcr.init.TCRModEntities;
import com.gaboj1.tcr.init.TCRModItems;
import com.gaboj1.tcr.util.DataManager;
import com.gaboj1.tcr.util.ItemUtil;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.npc.Villager;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import static com.gaboj1.tcr.gui.screen.DialogueComponentBuilder.BUILDER;

public class PastoralPlainTalkableVillager1 extends TCRStationaryVillager {

    CompoundTag serverPlayerData;
    EntityType<?> entityType = TCRModEntities.PASTORAL_PLAIN_TALKABLE_VILLAGER_1.get();

    public PastoralPlainTalkableVillager1(EntityType<? extends Villager> entityType, Level level) {
        super(entityType, level);
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void openDialogueScreen(CompoundTag serverPlayerData) {

        LinkListStreamDialogueScreenBuilder builder =  new LinkListStreamDialogueScreenBuilder(this, entityType);
        this.serverPlayerData = serverPlayerData;//用于handle的时候判断

        if(!DataManager.gunGot.getBool(serverPlayerData)){
            builder.start(BUILDER.buildDialogueDialog(entityType,0))//不许伤害小羊小牛小猪！（我们好像没有这些生物）
                .addChoice(BUILDER.buildDialogueChoice(entityType,0),BUILDER.buildDialogueDialog(entityType,1))//那你汉堡里面的牛肉是哪来的？  上天……上天给我的……这不是祈祷就有了吗？
                .addChoice(BUILDER.buildDialogueChoice(entityType,1),BUILDER.buildDialogueChoice(entityType,2).withStyle(ChatFormatting.RED,ChatFormatting.BOLD))//我也可以吗？我想要把火铳！（内心真诚地默念一遍） 【获得火铳】
                .thenExecute((byte) 7)//代号7，获得沙鹰
                .addChoice(BUILDER.buildDialogueChoice(entityType,3),BUILDER.buildDialogueDialog(entityType,2))
                .addFinalChoice(BUILDER.buildDialogueChoice(entityType,4),(byte) 0);//做梦吧你！火铳是我帮你祈祷出来的！
        }else {
            builder.setAnswerRoot(
                    new TreeNode(BUILDER.buildDialogueDialog(entityType,3))// ......
                        .addChild(new TreeNode(BUILDER.buildDialogueDialog(entityType,3),BUILDER.buildDialogueChoice(entityType,4))// ......  ......
                                .addLeaf(BUILDER.buildDialogueChoice(entityType,5), (byte) 0)//快给我，不然嘣了你
                                .addLeaf(BUILDER.buildDialogueChoice(entityType,6), (byte) 1)//谢谢你的火铳~
                    ));
        }
        Minecraft.getInstance().setScreen(builder.build());

    }

    @Override
    public void handleNpcInteraction(Player player, byte interactionID) {

        switch (interactionID){
            case 0:
                ItemUtil.searchAndConsumeItem(player, TCRModItems.DESERT_EAGLE_AMMO.get(), 20);
                chat(BUILDER.buildDialogueDialog(entityType,4,false));
                break;
            case 1:
                ItemStack itemStack = TCRModItems.DESERT_EAGLE_AMMO.get().getDefaultInstance();
                itemStack.setCount(20);
                player.addItem(itemStack);
                chat(BUILDER.buildDialogueDialog(entityType,5,false));
                break;
            case 7:
                player.addItem(TCRModItems.DESERT_EAGLE.get().getDefaultInstance());
                ItemStack ammo = TCRModItems.DESERT_EAGLE_AMMO.get().getDefaultInstance();
                ammo.setCount(20);
                player.addItem(ammo);
                DataManager.gunGot.putBool(player,true);//存入得用玩家
                return;
            default:
                return;
        }

        this.setConversingPlayer(null);
    }
//
//    @Override
//    public String getResourceName() {
//        return "pastoral_plain_villager";
//    }
}
