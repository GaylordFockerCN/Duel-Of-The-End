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

        switch (getSkinID()){
            //守卫 对话id分配：10~11 返回值：null
            case 0:
                Component greeting1 = BUILDER.buildDialogueAnswer(entityType, random.nextInt(10,12));
                builder.start(greeting1)
                        .addFinalChoice((BUILDER.buildDialogueOption(entityType,4)), (byte) 666);
                break;

            //药师 对话id分配：16~17 返回值分配：6
            case 1:
                Component greeting2 = BUILDER.buildDialogueAnswer(entityType,random.nextInt(16,18));
                builder.start(greeting2)
                        .addFinalChoice(BUILDER.buildDialogueOption(entityType,1),(byte) 6);
                break;

            //铁匠 对话id分配：12~13 返回值分配：5
            case 2:
                Component greeting3 = BUILDER.buildDialogueAnswer(entityType,random.nextInt(12,14));
                builder.start(greeting3)
                        .addFinalChoice(BUILDER.buildDialogueOption(entityType,3),(byte) 5);
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
                                16, 0, 0.02f)
                );
                break;
            case 4:
                chat(BUILDER.buildDialogueAnswer(entityType,9,false));
                break;
            case 5:
                startCustomTrade(player,
                        new MerchantOffer(
                                new ItemStack(TCRModItems.GUN_COMMON.get(), 1),
                                new ItemStack(TCRModItems.ORICHALCUM.get(), 16),
                                new ItemStack(TCRModItems.GUN_PLUS.get(), 1),
                                16, 0, 0.02f)
                );
                break;
            case 6:
                startCustomTrade(player,
                        new MerchantOffer(
                                new ItemStack(TCRModItems.DREAMSCAPE_COIN_PLUS.get(), 5),
                                new ItemStack(TCRModItems.LIGHT_ELIXIR.get(), 1),
                                16, 0, 0.02f),
                        new MerchantOffer(
                                new ItemStack(TCRModItems.DREAMSCAPE_COIN_PLUS.get(), 10),
                                new ItemStack(TCRModItems.ASCENSION_ELIXIR.get(), 1),
                                16, 0, 0.02f),
                        new MerchantOffer(
                                new ItemStack(TCRModItems.DREAMSCAPE_COIN_PLUS.get(), 5),
                                new ItemStack(TCRModItems.LUCKY_ELIXIR.get(), 1),
                                16, 0, 0.02f),
                        new MerchantOffer(
                                new ItemStack(TCRModItems.DREAMSCAPE_COIN_PLUS.get(), 5),
                                new ItemStack(TCRModItems.EVASION_ELIXIR.get(), 1),
                                16, 0, 0.02f),
                        new MerchantOffer(
                                new ItemStack(TCRModItems.DREAMSCAPE_COIN_PLUS.get(), 5),
                                new ItemStack(TCRModItems.WATER_AVOIDANCE_ELIXIR.get(), 1),
                                16, 0, 0.02f),
                        new MerchantOffer(
                                new ItemStack(TCRModItems.DREAMSCAPE_COIN_PLUS.get(), 5),
                                new ItemStack(TCRModItems.FIRE_AVOIDANCE_ELIXIR.get(), 1),
                                16, 0, 0.02f),
                        new MerchantOffer(
                                new ItemStack(TCRModItems.DREAMSCAPE_COIN_PLUS.get(), 5),
                                new ItemStack(TCRModItems.COLD_AVOIDANCE_ELIXIR.get(), 1),
                                16, 0, 0.02f),
                        new MerchantOffer(
                                new ItemStack(TCRModItems.DREAMSCAPE_COIN_PLUS.get(), 5),
                                new ItemStack(TCRModItems.THUNDER_AVOIDANCE_ELIXIR.get(), 1),
                                16, 0, 0.02f),
                        new MerchantOffer(
                                new ItemStack(TCRModItems.DREAMSCAPE_COIN_PLUS.get(), 5),
                                new ItemStack(TCRModItems.POISON_AVOIDANCE_ELIXIR.get(), 1),
                                16, 0, 0.02f),
                        new MerchantOffer(
                                new ItemStack(TCRModItems.DREAMSCAPE_COIN_PLUS.get(), 10),
                                new ItemStack(TCRModItems.STRENGTH_PILL.get(), 1),
                                16, 0, 0.02f)
                );
                break;
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
