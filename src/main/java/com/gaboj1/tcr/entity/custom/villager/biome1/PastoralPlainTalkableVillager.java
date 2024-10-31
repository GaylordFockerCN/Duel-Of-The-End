package com.gaboj1.tcr.entity.custom.villager.biome1;

import com.gaboj1.tcr.datagen.TCRAdvancementData;
import com.gaboj1.tcr.entity.TCREntities;
import com.gaboj1.tcr.entity.custom.villager.TCRTalkableVillager;
import com.gaboj1.tcr.client.gui.screen.LinkListStreamDialogueScreenBuilder;
import com.gaboj1.tcr.client.gui.screen.TreeNode;
import com.gaboj1.tcr.item.TCRItems;
import com.gaboj1.tcr.util.DataManager;
import com.gaboj1.tcr.util.ItemUtil;
import com.gaboj1.tcr.util.SaveUtil;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.damagesource.DamageSource;
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

public class PastoralPlainTalkableVillager extends TCRTalkableVillager {

    private final EntityType<?> entityType = TCREntities.PASTORAL_PLAIN_TALKABLE_VILLAGER.get();

    public PastoralPlainTalkableVillager(EntityType<? extends PastoralPlainTalkableVillager> entityType, Level level) {
        super(entityType, level,1);
    }

    @Override
    public int getMaleTypeCnt() {
        return 5;
    }
    @Override
    public int getFemaleTypeCnt() {
        return 4;
    }

    /**
     * 关键npc不能似，除非选boss阵营
     */
    @Override
    public boolean hurt(DamageSource source, float v) {
        if(!level().isClientSide){
            return SaveUtil.biome1.choice == SaveUtil.BiomeProgressData.BOSS;
        }
        return false;
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

            //NOTE 废案
            //工匠 对话id分配：0~6 返回值分配：0~2,110
//            case 1:
//                if(!DataManager.gunGot.get(serverPlayerData)){
//                    builder.start(BUILDER.buildDialogueAnswer(entityType,0))
//                            .addChoice(BUILDER.buildDialogueOption(entityType,0),BUILDER.buildDialogueAnswer(entityType,1))
//                            .addChoice(BUILDER.buildDialogueOption(entityType,1),BUILDER.buildDialogueOption(entityType,2).withStyle(ChatFormatting.DARK_RED,ChatFormatting.BOLD))//我也可以吗？我想要把火铳！（内心真诚地默念一遍） 【获得火铳】
//                            .thenExecute((byte) 111)//代号111，获得沙鹰
//                            .addChoice(BUILDER.buildDialogueOption(entityType,3),BUILDER.buildDialogueAnswer(entityType,2))
//                            .addFinalChoice(BUILDER.buildDialogueOption(entityType,4),(byte) 0);//做梦吧你！火铳是我帮你祈祷出来的！
//                }else {
//                    builder.setAnswerRoot(
//                            new TreeNode(BUILDER.buildDialogueAnswer(entityType,3))// ......
//                                    .addChild(new TreeNode(BUILDER.buildDialogueAnswer(entityType,3),BUILDER.buildDialogueOption(entityType,4))// ......  ......
//                                            .addLeaf(BUILDER.buildDialogueOption(entityType,5), (byte) 1)//快给我，不然嘣了你
//                                            .addLeaf(BUILDER.buildDialogueOption(entityType,6), (byte) 2)//谢谢你的火铳~
//                                    )
//                                    .addLeaf(BUILDER.buildDialogueOption(entityType,13),(byte) 110)
//                    );
//                }
//                break;
            case 1:
                if(!DataManager.gunGot.get(serverPlayerData)){
                    builder.start(BUILDER.buildDialogueAnswer(entityType,0))
                            .addChoice(BUILDER.buildDialogueOption(entityType,0),BUILDER.buildDialogueAnswer(entityType,1))
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
            //舞女 对话id分配：16~18 返回值分配：6
            case -1:
                Component greeting_1 = BUILDER.buildDialogueAnswer(entityType,16);
                builder.start(greeting_1)//嘿，你是来看我跳舞的吗？在这个小镇上我可是最出名的舞者哦。
                        .addChoice((BUILDER.buildDialogueOption(entityType,16)),(BUILDER.buildDialogueAnswer(entityType,17)))//你跳的舞一定很精彩//当然可以！这支舞是我从外地学来的，希望你喜欢！顺便问一下，你是来这里做什么的呢？
                        .addFinalChoice((BUILDER.buildDialogueOption(entityType,17)), (byte) 6);
                break;

            //服务生 对话id分配：19~21 返回值分配：7,8,112
            case -2:
                Component greeting_2 = BUILDER.buildDialogueAnswer(entityType,19);
                builder.setAnswerRoot(
                        new TreeNode(greeting_2)
                                .addChild(new TreeNode((BUILDER.buildDialogueAnswer(entityType,20)),(BUILDER.buildDialogueOption(entityType,19)))
                                        .execute((byte) 112)
                                        .addLeaf((BUILDER.buildDialogueOption(entityType,20)), (byte) 7)
                                        .addLeaf((BUILDER.buildDialogueOption(entityType,21)), (byte) 8)
                                )
                );
                break;
            //女商人 返回值分配：10
            case -3:
                builder.start(greeting1)
                        .addFinalChoice((BUILDER.buildDialogueOption(entityType,-3)), (byte) 11);
                break;
            //女商人 返回值分配：11
            case -4:
                builder.start(greeting1)
                        .addFinalChoice((BUILDER.buildDialogueOption(entityType,-3)), (byte) 12);
                break;

        }

        if(SaveUtil.biome1.choice == 1){
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
        DataManager.putData(player, "hasTalkedTo"+getSkinID(),true);

        switch (interactionID){

            //商人
            case -1:
                chat(BUILDER.buildDialogueAnswer(entityType,-1,false));
                startCustomTrade(player,
                        new MerchantOffer(
                                new ItemStack(TCRItems.DREAMSCAPE_COIN_PLUS.get(), 32),
                                new ItemStack(TCRItems.ORICHALCUM.get(), 1),
                                142857, 0, 0),
                        new MerchantOffer(
                                new ItemStack(TCRItems.ORICHALCUM.get(), 1),
                                new ItemStack(TCRItems.DREAMSCAPE_COIN.get(), 48),
                                142857, 0, 1)

                );
                break;
            case 0:
                //什么都不做
                break;

            case 110:
                chat(BUILDER.buildDialogueAnswer(entityType,1,1,false));
                startCustomTrade(player,
                        new MerchantOffer(
                                new ItemStack(TCRItems.GUN_COMMON.get(), 1),
                                new ItemStack(TCRItems.ORICHALCUM.get(), 16),
                                new ItemStack(TCRItems.GUN_PLUS.get(), 1),
                                142857, 0, 0.02f),
                        new MerchantOffer(
                                new ItemStack(TCRItems.TREE_DEMON_FRUIT.get(), 1),
                                new ItemStack(TCRItems.TREE_DEMON_HORN.get(), 1),
                                new ItemStack(TCRItems.TREE_SPIRIT_WAND.get(), 1),
                                142857, 0, 0.02f),
                        new MerchantOffer(
                                new ItemStack(TCRItems.DREAMSCAPE_COIN.get(), 16),
                                new ItemStack(TCRItems.AMMO.get(), 2),
                                142857, 0, 0.02f),
                        new MerchantOffer(
                                new ItemStack(TCRItems.STARLIT_DEWDROP.get(), 4),
                                new ItemStack(TCRItems.DREAMSCAPE_COIN.get(), 20),
                                new ItemStack(TCRItems.SPRITE_WAND.get(), 1),
                                142857, 0, 0.02f),
                        new MerchantOffer(
                                new ItemStack(TCRItems.ORICHALCUM.get(), 5),
                                new ItemStack(TCRItems.ORICHALCUM_HELMET.get(), 1),
                                142857, 0, 0.02f),
                        new MerchantOffer(
                                new ItemStack(TCRItems.ORICHALCUM.get(), 8),
                                new ItemStack(TCRItems.ORICHALCUM_CHESTPLATE.get(), 1),
                                142857, 0, 0.02f),
                        new MerchantOffer(
                                new ItemStack(TCRItems.ORICHALCUM.get(), 7),
                                new ItemStack(TCRItems.ORICHALCUM_LEGGINGS.get(), 1),
                                142857, 0, 0.02f),
                        new MerchantOffer(
                                new ItemStack(TCRItems.ORICHALCUM.get(), 4),
                                new ItemStack(TCRItems.ORICHALCUM_BOOTS.get(), 1),
                                142857, 0, 0.02f)
                );
                break;
            case 111:
                ItemUtil.addItem(player,TCRItems.GUN_COMMON.get(),1);
                ItemStack ammo = TCRItems.AMMO.get().getDefaultInstance();
                ItemUtil.addItem(player,ammo.getItem(),20);
                DataManager.gunGot.put(player,true);//存入得用玩家

                if(player instanceof ServerPlayer serverPlayer){
                    TCRAdvancementData.getAdvancement("day_dreamer",serverPlayer);
                }

                return;//NOTE 记得返回，否则对话中断！
            case 1:
                ItemUtil.searchAndConsumeItem(player, TCRItems.AMMO.get(), 20);
//                player.addItem(Book.getBook("book1"));//测试书籍生成
                chat(BUILDER.buildDialogueAnswer(entityType,4,false));//......
                break;
            case 2:
                if(!DataManager.ammoGot.get(player.getPersistentData())){
                    ItemStack stack = TCRItems.AMMO.get().getDefaultInstance();
                    ItemUtil.addItem(player,stack.getItem(),20);
                    chat(BUILDER.buildDialogueAnswer(entityType,5,false));//快给我，不然嘣了你
                    DataManager.ammoGot.put(player,true);
                }
                break;
            case 3:
                chat(BUILDER.buildDialogueAnswer(entityType, -getRandom().nextInt(6, 10),false));
                break;
            case 4:
                chat(BUILDER.buildDialogueAnswer(entityType,12,false));//真是有干劲啊，那我也要全力以赴了，朋友
                break;
            case 9:
                chat(BUILDER.buildDialogueAnswer(entityType,getSkinID(), 2,false));//异乡人，你怎么了？
                break;
            case 10:
                chat(BUILDER.buildDialogueAnswer(entityType,getSkinID(), 3,false));//啊，欸，很厉害的样子
                break;
            //猎人
            case 5:
                chat(BUILDER.buildDialogueAnswer(entityType,15,false));
                startCustomTrade(player,
                        new MerchantOffer(
                                new ItemStack(TCRItems.HEART_OF_THE_SAPLING.get(), 10),
                                new ItemStack(TCRItems.DREAMSCAPE_COIN.get(), 1),
                                142857, 0, 0.02f),
                        new MerchantOffer(
                                new ItemStack(TCRItems.ESSENCE_OF_THE_ANCIENT_TREE.get(), 2),
                                new ItemStack(TCRItems.DREAMSCAPE_COIN.get(), 1),
                                142857, 0, 0.02f),
                        new MerchantOffer(
                                new ItemStack(TCRItems.BARK_OF_THE_GUARDIAN.get(), 1),
                                new ItemStack(TCRItems.DREAMSCAPE_COIN.get(), 1),
                                142857, 0, 0.02f),
                        new MerchantOffer(
                                new ItemStack(TCRItems.STARLIT_DEWDROP.get(), 1),
                                new ItemStack(TCRItems.DREAMSCAPE_COIN.get(), 3),
                                142857, 0, 0.02f),
                        new MerchantOffer(
                                new ItemStack(TCRItems.DREAMSCAPE_COIN.get(), 1),
                                new ItemStack(TCRItems.BASIC_RESIN.get(), 5),
                                142857, 0, 0.02f)
                );
                break;
            case 6:
                chat(BUILDER.buildDialogueAnswer(entityType,18,false));//哦，那你一定要小心，那些传说听起来让人直做噩梦。
                break;
            case 7:
                chat(BUILDER.buildDialogueAnswer(entityType,21,false));
                break;
            case 8:
                chat(BUILDER.buildDialogueAnswer(entityType,23,false));
                startCustomTrade(player,
                        new MerchantOffer(
                                new ItemStack(TCRItems.DREAMSCAPE_COIN.get(), 1),
                                new ItemStack(TCRItems.BEER.get(), 1),
                                142857, 0, 0.02f),
                        new MerchantOffer(
                                new ItemStack(TCRItems.DREAMSCAPE_COIN.get(), 1),
                                new ItemStack(TCRItems.DRINK1.get(), 1),
                                142857, 0, 0.02f),
                        new MerchantOffer(
                                new ItemStack(TCRItems.DREAMSCAPE_COIN.get(), 1),
                                new ItemStack(TCRItems.DRINK2.get(), 1),
                                142857, 0, 0.02f),
                        new MerchantOffer(
                                new ItemStack(TCRItems.DREAMSCAPE_COIN.get(), 1),
                                new ItemStack(TCRItems.DREAM_TA.get(), 1),
                                142857, 0, 0.02f),
                        new MerchantOffer(
                                new ItemStack(TCRItems.DREAMSCAPE_COIN.get(), 1),
                                new ItemStack(TCRItems.JUICE_TEA.get(), 1),
                                142857, 0, 0.02f),
                        new MerchantOffer(
                                new ItemStack(TCRItems.DREAMSCAPE_COIN.get(), 2),
                                new ItemStack(TCRItems.HOT_CHOCOLATE.get(), 1),
                                142857, 0, 0.02f),
                        new MerchantOffer(
                                new ItemStack(TCRItems.DREAMSCAPE_COIN.get(), 4),
                                new ItemStack(Items.COOKED_BEEF, 1),
                                142857, 0, 0.02f),
                        new MerchantOffer(
                                new ItemStack(TCRItems.DREAMSCAPE_COIN.get(), 2),
                                new ItemStack(Items.COOKED_CHICKEN, 2),
                                142857, 0, 0.02f),
                        new MerchantOffer(
                                new ItemStack(TCRItems.DREAMSCAPE_COIN.get(), 3),
                                new ItemStack(Items.COOKED_PORKCHOP, 1),
                                142857, 0, 0.02f)
                );
                break;
            case 11:
                chat(BUILDER.buildDialogueAnswer(entityType,-1,false));
                startCustomTrade(player,
                        new MerchantOffer(
                                new ItemStack(TCRItems.DREAMSCAPE_COIN.get(), 2),
                                new ItemStack(TCRItems.BEER.get(), 1),
                                142857, 0, 0.02f),
                        new MerchantOffer(
                                new ItemStack(TCRItems.DREAMSCAPE_COIN.get(), 2),
                                new ItemStack(TCRItems.DRINK1.get(), 1),
                                142857, 0, 0.02f),
                        new MerchantOffer(
                                new ItemStack(TCRItems.DREAMSCAPE_COIN.get(), 2),
                                new ItemStack(TCRItems.DRINK2.get(), 1),
                                142857, 0, 0.02f),
                        new MerchantOffer(
                                new ItemStack(TCRItems.DREAMSCAPE_COIN.get(), 3),
                                new ItemStack(TCRItems.DREAM_TA.get(), 1),
                                142857, 0, 0.02f)
                );
                break;
            case 12:
                chat(BUILDER.buildDialogueAnswer(entityType,-1,false));
                startCustomTrade(player,
                        new MerchantOffer(
                                new ItemStack(TCRItems.DREAMSCAPE_COIN.get(), 2),
                                new ItemStack(TCRItems.JUICE_TEA.get(), 1),
                                142857, 0, 0.02f),
                        new MerchantOffer(
                                new ItemStack(TCRItems.DREAMSCAPE_COIN.get(), 3),
                                new ItemStack(TCRItems.HOT_CHOCOLATE.get(), 1),
                                142857, 0, 0.02f),
                        new MerchantOffer(
                                new ItemStack(TCRItems.DREAMSCAPE_COIN.get(), 2),
                                new ItemStack(TCRItems.COOKIE.get(), 1),
                                142857, 0, 0.02f)
                );
                break;
            case 112:
                if(!DataManager.drinkGot.get(player)){
                    ItemUtil.addItem(player,TCRItems.DRINK2.get(),1);
                    DataManager.drinkGot.put(player,true);
                }else {
                    chat(BUILDER.buildDialogueAnswer(entityType,22,false));
                }
                return;//NOTE 记得返回，否则对话中断！

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
        return Component.translatable(TCREntities.PASTORAL_PLAIN_TALKABLE_VILLAGER.get().getDescriptionId() + getSkinID());
    }

    @Override
    public String getResourceName() {
        return "talkable/pastoral_plain_villager"+getSkinID();
    }
}
