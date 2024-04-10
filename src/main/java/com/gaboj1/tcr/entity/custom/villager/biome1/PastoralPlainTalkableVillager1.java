package com.gaboj1.tcr.entity.custom.villager.biome1;

import com.gaboj1.tcr.datagen.ModAdvancementData;
import com.gaboj1.tcr.entity.custom.villager.TCRStationaryVillager;
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
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.npc.Villager;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.trading.MerchantOffer;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;

import java.util.Random;

import static com.gaboj1.tcr.gui.screen.DialogueComponentBuilder.BUILDER;

public class PastoralPlainTalkableVillager1 extends TCRStationaryVillager {

    EntityType<?> entityType = TCRModEntities.PASTORAL_PLAIN_TALKABLE_VILLAGER_1.get();

    public PastoralPlainTalkableVillager1(EntityType<? extends Villager> entityType, Level level) {
        super(entityType, level,1);
    }

    /**
     * 对话窗口内容。为了防止做太多的类而挤到一起（何尝不是一种屎qwq）
     */
    @Override
    @OnlyIn(Dist.CLIENT)
    public void openDialogueScreen(CompoundTag serverPlayerData) {

        LinkListStreamDialogueScreenBuilder builder =  new LinkListStreamDialogueScreenBuilder(this, entityType);
        Random random = new Random();
        switch (skinID){
            //商人 对话id分配：-3~-1 返回值分配：-1 (被工匠抢先了awa只能用负数了awa）
            case 0:
                Component greeting1 = BUILDER.buildDialogueDialog(entityType,-3 + random.nextInt(2));
                builder.start(greeting1)
                        .addFinalChoice((BUILDER.buildDialogueChoice(entityType,-3)), (byte) -1);
                break;

            //工匠 对话id分配：0~6 返回值分配：0~2
            case 1:
                if(!DataManager.gunGot.getBool(serverPlayerData)){
                    builder.start(BUILDER.buildDialogueDialog(entityType,0))//不许伤害小羊小牛小猪！（我们好像没有这些生物）
                            .addChoice(BUILDER.buildDialogueChoice(entityType,0),BUILDER.buildDialogueDialog(entityType,1))//那你汉堡里面的牛肉是哪来的？  上天……上天给我的……这不是祈祷就有了吗？
                            .addChoice(BUILDER.buildDialogueChoice(entityType,1),BUILDER.buildDialogueChoice(entityType,2).withStyle(ChatFormatting.DARK_RED,ChatFormatting.BOLD))//我也可以吗？我想要把火铳！（内心真诚地默念一遍） 【获得火铳】
                            .thenExecute((byte) 111)//代号111，获得沙鹰
                            .addChoice(BUILDER.buildDialogueChoice(entityType,3),BUILDER.buildDialogueDialog(entityType,2))
                            .addFinalChoice(BUILDER.buildDialogueChoice(entityType,4),(byte) 0);//做梦吧你！火铳是我帮你祈祷出来的！
                }else {
                    builder.setAnswerRoot(
                            new TreeNode(BUILDER.buildDialogueDialog(entityType,3))// ......
                                    .addChild(new TreeNode(BUILDER.buildDialogueDialog(entityType,3),BUILDER.buildDialogueChoice(entityType,4))// ......  ......
                                            .addLeaf(BUILDER.buildDialogueChoice(entityType,5), (byte) 1)//快给我，不然嘣了你
                                            .addLeaf(BUILDER.buildDialogueChoice(entityType,6), (byte) 2)//谢谢你的火铳~
                                    )
                                    .addLeaf(BUILDER.buildDialogueChoice(entityType,13),(byte) 110)
                    );
                }
                break;
            //学者 对话id分配：7~9 返回值分配：3
            case 2:
                Component greeting2 = BUILDER.buildDialogueDialog(entityType,7 + random.nextInt(2));
                builder.start(greeting2)
                        .addFinalChoice((BUILDER.buildDialogueChoice(entityType,7)), (byte) 3);
                break;

            //牧羊人 对话id分配：10~12 返回值分配：4
            case 3:
                Component greeting3 = BUILDER.buildDialogueDialog(entityType,10 + random.nextInt(2));
                builder.start(greeting3)
                        .addFinalChoice((BUILDER.buildDialogueChoice(entityType,10)), (byte) 4);
                break;
            //猎人 对话id分配：13~15 返回值分配：5
            case 4:
                Component greeting4 = BUILDER.buildDialogueDialog(entityType,13 + random.nextInt(2));
                builder.start(greeting4)
                        .addFinalChoice((BUILDER.buildDialogueChoice(entityType,13)), (byte) 5);
                break;
            //舞女 对话id分配：16~18 返回值分配：6
            case -1:
                Component greeting_1 = BUILDER.buildDialogueDialog(entityType,16);
                builder.start(greeting_1)//嘿，你是来看我跳舞的吗？在这个小镇上我可是最出名的舞者哦。
                        .addChoice((BUILDER.buildDialogueChoice(entityType,16)),(BUILDER.buildDialogueDialog(entityType,17)))//你跳的舞一定很精彩//当然可以！这支舞是我从外地学来的，希望你喜欢！顺便问一下，你是来这里做什么的呢？
                        .addFinalChoice((BUILDER.buildDialogueChoice(entityType,17)), (byte) 6);
                break;

            //服务生 对话id分配：19~21 返回值分配：7
            case -2:
                Component greeting_2 = BUILDER.buildDialogueDialog(entityType,19);
                builder.setAnswerRoot(
                        new TreeNode(greeting_2)
                                .addChild(new TreeNode((BUILDER.buildDialogueDialog(entityType,20)),(BUILDER.buildDialogueChoice(entityType,19)))
                                        .execute((byte) 112)
                                        .addLeaf((BUILDER.buildDialogueChoice(entityType,20)), (byte) 7)
                                        .addLeaf((BUILDER.buildDialogueChoice(entityType,21)), (byte) 8)
                                )
                );
                break;
            case -3:
                break;

        }

        Minecraft.getInstance().setScreen(builder.build());

    }

    @Override
    public void handleNpcInteraction(Player player, byte interactionID) {

        switch (interactionID){
            case -1:
                chat(BUILDER.buildDialogueDialog(entityType,-1,false));
//                VillagerData data = getVillagerData();
//                data.setProfession(TCRModVillagers.TCR_MERCHANT.get());//没有屌用
//                setVillagerData(data);
//                System.out.println(getVillagerData().getProfession());
                startCustomTrade(player,
                        new MerchantOffer(
                                new ItemStack(TCRModItems.DREAMSCAPE_COIN.get(), 16),
                                new ItemStack(TCRModItems.BEER.get(), 1),
                                16, 0, 0),
                        new MerchantOffer(
                                new ItemStack(TCRModItems.DREAMSCAPE_COIN.get(), 16),
                                new ItemStack(TCRModItems.DRINK2.get(), 1),
                                16, 0, 1)
                );
                break;
            case 0:
                //什么都不做
                break;

            case 110:
                startCustomTrade(player,
                        new MerchantOffer(
                                new ItemStack(TCRModItems.DREAMSCAPE_COIN.get(), 64),
                                new ItemStack(TCRModItems.DESERT_EAGLE.get(), 1),
                                16, 0, 0.02f),
                        new MerchantOffer(
                                new ItemStack(TCRModItems.DREAMSCAPE_COIN.get(), 16),
                                new ItemStack(TCRModItems.DESERT_EAGLE_AMMO.get(), 2),
                                16, 0, 0.02f)
                );
                break;
            case 111:
                player.addItem(TCRModItems.DESERT_EAGLE.get().getDefaultInstance());
                ItemStack ammo = TCRModItems.DESERT_EAGLE_AMMO.get().getDefaultInstance();
                ammo.setCount(20);
                player.addItem(ammo);
                DataManager.gunGot.putBool(player,true);//存入得用玩家

                if(player instanceof ServerPlayer serverPlayer){
                    ModAdvancementData.getAdvancement("day_dreamer",serverPlayer);
                }

                return;//NOTE 记得返回，否则对话中断！
            case 1:
                ItemUtil.searchAndConsumeItem(player, TCRModItems.DESERT_EAGLE_AMMO.get(), 20);
//                player.addItem(Book.getBook("book1"));//测试书籍生成
                chat(BUILDER.buildDialogueDialog(entityType,4,false));//......
                break;
            case 2:
                if(!DataManager.ammoGot.getBool(player.getPersistentData())){
                    ItemStack stack = TCRModItems.DESERT_EAGLE_AMMO.get().getDefaultInstance();
                    stack.setCount(20);
                    player.addItem(stack);
                    chat(BUILDER.buildDialogueDialog(entityType,5,false));//快给我，不然嘣了你
                    DataManager.ammoGot.putBool(player,true);
                }
                break;
            case 3:
                chat(BUILDER.buildDialogueDialog(entityType,9,false));//愿我的智慧为你扫开前路
                break;
            case 4:
                chat(BUILDER.buildDialogueDialog(entityType,12,false));//真是有干劲啊，那我也要全力以赴了，朋友
                break;
            case 5:
                chat(BUILDER.buildDialogueDialog(entityType,15,false));
                startCustomTrade(player,
                        new MerchantOffer(
                                new ItemStack(TCRModItems.DREAMSCAPE_COIN.get(), 16),
                                new ItemStack(TCRModItems.BEER.get(), 1),
                                16, 0, 0.02f),
                        new MerchantOffer(
                                new ItemStack(TCRModItems.DREAMSCAPE_COIN.get(), 16),
                                new ItemStack(TCRModItems.DRINK2.get(), 1),
                                16, 0, 0.02f)
                );
                break;
            case 6:
                chat(BUILDER.buildDialogueDialog(entityType,16,false));//哦，那你一定要小心，那些传说听起来让人直做噩梦。
                break;
            case 7:
                chat(BUILDER.buildDialogueDialog(entityType,21,false));
                break;
            case 8:
                chat(BUILDER.buildDialogueDialog(entityType,23,false));
                startCustomTrade(player,
                        new MerchantOffer(
                                new ItemStack(TCRModItems.DREAMSCAPE_COIN.get(), 32),
                                new ItemStack(TCRModItems.BEER.get(), 1),
                                16, 0, 0.02f),
                        new MerchantOffer(
                                new ItemStack(TCRModItems.DREAMSCAPE_COIN.get(), 16),
                                new ItemStack(TCRModItems.DRINK1.get(), 1),
                                16, 0, 0.02f),
                        new MerchantOffer(
                                new ItemStack(TCRModItems.DREAMSCAPE_COIN.get(), 16),
                                new ItemStack(TCRModItems.DRINK2.get(), 1),
                                16, 0, 0.02f),
                        new MerchantOffer(
                                new ItemStack(TCRModItems.DREAMSCAPE_COIN.get(), 16),
                                new ItemStack(TCRModItems.DREAM_TA.get(), 1),
                                16, 0, 0.02f),
                        new MerchantOffer(
                                new ItemStack(TCRModItems.DREAMSCAPE_COIN.get(), 16),
                                new ItemStack(TCRModItems.JUICE_TEA.get(), 1),
                                16, 0, 0.02f),
                        new MerchantOffer(
                                new ItemStack(TCRModItems.DREAMSCAPE_COIN.get(), 20),
                                new ItemStack(TCRModItems.HOT_CHOCOLATE.get(), 1),
                                16, 0, 0.02f),
                        new MerchantOffer(
                                new ItemStack(TCRModItems.DREAMSCAPE_COIN.get(), 16),
                                new ItemStack(TCRModItems.COOKIE.get(), 2),
                                16, 0, 0.02f)
                );
                break;
            case 112:
                if(!DataManager.drinkGot.getBool(player)){
                    player.addItem(TCRModItems.DRINK2.get().getDefaultInstance());
                    DataManager.drinkGot.putBool(player,true);
                }else {
                    chat(BUILDER.buildDialogueDialog(entityType,22,false));
                }
                return;//NOTE 记得返回，否则对话中断！

            default:
                return;
        }

        this.setConversingPlayer(null);
    }

    @Override
    public @NotNull Component getDisplayName() {
        return Component.translatable(TCRModEntities.PASTORAL_PLAIN_TALKABLE_VILLAGER_1.get().getDescriptionId()+skinID);
    }

}
