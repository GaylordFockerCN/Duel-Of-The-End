package com.gaboj1.tcr.entity.custom.villager.biome2;

import com.gaboj1.tcr.client.gui.screen.LinkListStreamDialogueScreenBuilder;
import com.gaboj1.tcr.client.gui.screen.TreeNode;
import com.gaboj1.tcr.datagen.TCRAdvancementData;
import com.gaboj1.tcr.entity.TCRModEntities;
import com.gaboj1.tcr.entity.custom.villager.TCRTalkableVillager;
import com.gaboj1.tcr.item.TCRModItems;
import com.gaboj1.tcr.util.DataManager;
import com.gaboj1.tcr.util.ItemUtil;
import com.gaboj1.tcr.util.SaveUtil;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.trading.MerchantOffer;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;

import java.util.Random;

import static com.gaboj1.tcr.client.gui.screen.DialogueComponentBuilder.BUILDER;

public class TalkableVillager2 extends TCRTalkableVillager {

    private final EntityType<?> entityType = TCRModEntities.VILLAGER2_TALKABLE.get();

    public TalkableVillager2(EntityType<? extends TalkableVillager2> entityType, Level level) {
        super(entityType, level,1);
    }

    @Override
    public int getMaleTypeCnt() {
        return 3;
    }
    @Override
    public int getFemaleTypeCnt() {
        return 2;
    }

    /**
     * 对话窗口内容。为了防止做太多的类而挤到一起（何尝不是一种屎qwq）
     * 后来因为对话越来越多，才推出了皮肤id+对话id的写法，但是旧的懒得改了。。
     */
    @Override
    @OnlyIn(Dist.CLIENT)
    public void openDialogueScreen(CompoundTag serverPlayerData) {

        //构建对话系统
        LinkListStreamDialogueScreenBuilder builder =  new LinkListStreamDialogueScreenBuilder(this, entityType);
        Random random = new Random();

        Component greeting1 = BUILDER.buildDialogueAnswer(entityType,-3 + random.nextInt(2));
        switch (getSkinID()){
            //商人 对话id分配：-3~-1 返回值分配：-1 (被工匠抢先了awa只能用负数了awa）
            case 0:
                builder.start(greeting1)
                        .addFinalChoice((BUILDER.buildDialogueOption(entityType,-3)), (byte) -1);
                break;

            //工匠 对话id分配：0~6 返回值分配：0~2,110
            case 1:
                if(!DataManager.gunGot.getBool(serverPlayerData)){
                    builder.start(BUILDER.buildDialogueAnswer(entityType,0))//不许伤害小羊小牛小猪！（我们好像没有这些生物）
                            .addChoice(BUILDER.buildDialogueOption(entityType,0),BUILDER.buildDialogueAnswer(entityType,1))//那你汉堡里面的牛肉是哪来的？  上天……上天给我的……这不是祈祷就有了吗？
                            .addChoice(BUILDER.buildDialogueOption(entityType,1),BUILDER.buildDialogueOption(entityType,2).withStyle(ChatFormatting.DARK_RED,ChatFormatting.BOLD))//我也可以吗？我想要把火铳！（内心真诚地默念一遍） 【获得火铳】
                            .thenExecute((byte) 111)//代号111，获得沙鹰
                            .addChoice(BUILDER.buildDialogueOption(entityType,3),BUILDER.buildDialogueAnswer(entityType,2))
                            .addFinalChoice(BUILDER.buildDialogueOption(entityType,4),(byte) 0);//做梦吧你！火铳是我帮你祈祷出来的！
                }else {
                    builder.setAnswerRoot(
                            new TreeNode(BUILDER.buildDialogueAnswer(entityType,3))// ......
                                    .addChild(new TreeNode(BUILDER.buildDialogueAnswer(entityType,3),BUILDER.buildDialogueOption(entityType,4))// ......  ......
                                            .addLeaf(BUILDER.buildDialogueOption(entityType,5), (byte) 1)//快给我，不然嘣了你
                                            .addLeaf(BUILDER.buildDialogueOption(entityType,6), (byte) 2)//谢谢你的火铳~
                                    )
                                    .addLeaf(BUILDER.buildDialogueOption(entityType,13),(byte) 110)
                    );
                }
                break;
            //学者 对话id分配：7~9 返回值分配：3
            case 2:
                if(!serverPlayerData.getBoolean("hasTalkedTo"+getSkinID())){
                    builder.setAnswerRoot(new TreeNode(BUILDER.buildDialogueAnswer(entityType,getSkinID(), 1))
                            .addChild(new TreeNode(BUILDER.buildDialogueAnswer(entityType,getSkinID(), 2),BUILDER.buildDialogueOption(entityType,getSkinID(), 1))
                                    .addLeaf(BUILDER.buildDialogueOption(entityType,getSkinID(), 3),(byte) 0)
                                    .addLeaf(BUILDER.buildDialogueOption(entityType,getSkinID(), 4),(byte) 0))
                            .addChild(new TreeNode(BUILDER.buildDialogueAnswer(entityType,getSkinID(), 2),BUILDER.buildDialogueOption(entityType,getSkinID(), 2))
                                    .addLeaf(BUILDER.buildDialogueOption(entityType,getSkinID(), 3),(byte) 0)
                                    .addLeaf(BUILDER.buildDialogueOption(entityType,getSkinID(), 4),(byte) 0))
                    );
                    break;
                }
                Component greeting2 = BUILDER.buildDialogueAnswer(entityType,7 + random.nextInt(2));
                builder.start(greeting2)
                        .addFinalChoice((BUILDER.buildDialogueOption(entityType,7)), (byte) 3);
                break;

            //牧羊人 对话id分配：10~12 返回值分配：4
            case 3:
                if(serverPlayerData.getBoolean("hasTalkedTo"+getSkinID())){
                    Component greeting3 = BUILDER.buildDialogueAnswer(entityType,10 + random.nextInt(2));
                    builder.start(greeting3)
                            .addFinalChoice((BUILDER.buildDialogueOption(entityType,10)), (byte) 4);
                } else {
                    builder.setAnswerRoot(new TreeNode(BUILDER.buildDialogueAnswer(entityType,getSkinID(), 1))
                            .addLeaf(BUILDER.buildDialogueOption(entityType, getSkinID(),1), (byte) 9)
                            .addLeaf(BUILDER.buildDialogueOption(entityType, getSkinID(),2), (byte) 10)
                    );
                }
                break;
            //猎人 对话id分配：13~15 返回值分配：5
            case 4:
                Component greeting4 = BUILDER.buildDialogueAnswer(entityType,13 + random.nextInt(2));
                builder.start(greeting4)
                        .addFinalChoice((BUILDER.buildDialogueOption(entityType,13)), (byte) 5);
                break;
            //厨娘 对话id分配：0~4 返回值分配：1,2
            case -1:
                int i = random.nextInt(0,3);
                Component greeting_1 = BUILDER.buildDialogueAnswer(entityType,i);
                builder.setAnswerRoot(
                        new TreeNode(greeting_1)
                                //交易
                                .addLeaf(BUILDER.buildDialogueOption(entityType,1), (byte) 1)
                                //询问
                                .addLeaf(BUILDER.buildDialogueOption(entityType,2), (byte) 2)
                );
                break;

            //女商人 对话id分配：5~9 返回值分配：3,4
            case -2:
                int j = random.nextInt(5,8);
                Component greeting_2 = BUILDER.buildDialogueAnswer(entityType,j);
                builder.setAnswerRoot(
                        new TreeNode(greeting_2)
                                //交易
                                .addLeaf(BUILDER.buildDialogueOption(entityType,1), (byte) 3)
                                //询问
                                .addLeaf(BUILDER.buildDialogueOption(entityType,2), (byte) 4)
                );
                break;
            //女商人 返回值分配：11
            case -4:
                builder.start(greeting1)
                        .addFinalChoice((BUILDER.buildDialogueOption(entityType,-3)), (byte) 12);
                break;

        }

        if(SaveUtil.biome2.choice == 1){
            if(Minecraft.getInstance().player != null){
                talk(Minecraft.getInstance().player, true);
            }
            return;
        }

        if(builder.isEmpty()){
            return;
        }
        
        Minecraft.getInstance().setScreen(builder.build());

    }

    @Override
    public void handleNpcInteraction(Player player, byte interactionID) {

        //记录为已对话过。用于丰富对话多样性。
        player.getPersistentData().putBoolean("hasTalkedTo"+getSkinID(),true);

        switch (interactionID){

            //厨娘
            case 1:
                chat(BUILDER.buildDialogueAnswer(entityType,3,false));

                //TODO: 交易物品，待定
                startCustomTrade(player,
                        new MerchantOffer(
                                new ItemStack(TCRModItems.DREAMSCAPE_COIN_PLUS.get(), 32),
                                new ItemStack(TCRModItems.ORICHALCUM.get(), 1),
                                16, 0, 0),
                        new MerchantOffer(
                                new ItemStack(TCRModItems.ORICHALCUM.get(), 1),
                                new ItemStack(TCRModItems.DREAMSCAPE_COIN.get(), 48),
                                16, 0, 1),
                        new MerchantOffer(
                                new ItemStack(TCRModItems.DREAMSCAPE_COIN_PLUS.get(), 5),
                                new ItemStack(TCRModItems.HEALTH_WAND.get(), 1),
                                16, 0, 1)
                );
                break;
            case 2:
                chat(BUILDER.buildDialogueAnswer(entityType,4));
                break;

                //女商人
            case 3:
                chat(BUILDER.buildDialogueAnswer(entityType,8,false));
                startCustomTrade(player,
                        new MerchantOffer(
                                new ItemStack(TCRModItems.GUN_COMMON.get(), 1),
                                new ItemStack(TCRModItems.ORICHALCUM.get(), 16),
                                new ItemStack(TCRModItems.GUN_PLUS.get(), 1),
                                16, 0, 0.02f),
                        new MerchantOffer(
                                new ItemStack(TCRModItems.TREE_DEMON_FRUIT.get(), 1),
                                new ItemStack(TCRModItems.TREE_DEMON_HORN.get(), 1),
                                new ItemStack(TCRModItems.TREE_SPIRIT_WAND.get(), 1),
                                16, 0, 0.02f),
                        new MerchantOffer(
                                new ItemStack(TCRModItems.DREAMSCAPE_COIN.get(), 64),
                                new ItemStack(TCRModItems.GUN_COMMON.get(), 1),
                                16, 0, 0.02f),
                        new MerchantOffer(
                                new ItemStack(TCRModItems.DREAMSCAPE_COIN.get(), 16),
                                new ItemStack(TCRModItems.AMMO.get(), 2),
                                16, 0, 0.02f),
                        new MerchantOffer(
                                new ItemStack(TCRModItems.ORICHALCUM.get(), 5),
                                new ItemStack(TCRModItems.ORICHALCUM_HELMET.get(), 1),
                                16, 0, 0.02f),
                        new MerchantOffer(
                                new ItemStack(TCRModItems.ORICHALCUM.get(), 8),
                                new ItemStack(TCRModItems.ORICHALCUM_CHESTPLATE.get(), 1),
                                16, 0, 0.02f),
                        new MerchantOffer(
                                new ItemStack(TCRModItems.ORICHALCUM.get(), 7),
                                new ItemStack(TCRModItems.ORICHALCUM_LEGGINGS.get(), 1),
                                16, 0, 0.02f),
                        new MerchantOffer(
                                new ItemStack(TCRModItems.ORICHALCUM.get(), 4),
                                new ItemStack(TCRModItems.ORICHALCUM_BOOTS.get(), 1),
                                16, 0, 0.02f)
                );
                break;
            case 4:
                chat(BUILDER.buildDialogueAnswer(entityType,9,false));//真是有干劲啊，那我也要全力以赴了，朋友
                break;

            default:
                return;
        }

        this.setConversingPlayer(null);
    }

    /**
     * 防止子类名称错误
     * @return 带skinID的名称（译名）
     */
    @Override
    public @NotNull Component getDisplayName() {
        return Component.translatable(TCRModEntities.VILLAGER2_TALKABLE.get().getDescriptionId() + getSkinID());
    }

    @Override
    public String getResourceName() {
        return "talkable/villager2"+getSkinID();
    }
}
