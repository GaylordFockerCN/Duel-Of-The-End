package com.gaboj1.tcr.gui.screen;

import com.gaboj1.tcr.TCRConfig;
import com.gaboj1.tcr.TheCasketOfReveriesMod;
import com.gaboj1.tcr.gui.screen.component.DialogueChoiceComponent;
import com.gaboj1.tcr.init.TCRModEntities;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;


/**
 * 改编自theAether 的 ValkyrieQueenDialogueScreen
 * 搬运了相关类
 */
public class PastoralPlainVillagerElderDialogueScreen extends TCRDialogueScreen {
    public static final ResourceLocation MY_BACKGROUND_LOCATION = new ResourceLocation(TheCasketOfReveriesMod.MOD_ID,"textures/gui/background.png");

    public PastoralPlainVillagerElderDialogueScreen(Entity elder) {
        super(elder, TCRModEntities.PASTORAL_PLAIN_VILLAGER_ELDER.get());
    }

    @Override
    protected void init() {
        if (this.getMinecraft().player != null) {
            if(TCRConfig.IS_WHITE.get()){
                if(!TCRConfig.KILLED_BOSS1.get()){//是否为白方，是否击杀boss，对话不同
                    this.setDialogueAnswer(this.buildDialogueDialog(0));//勇者啊，我所期盼的勇者啊你终于来了。你可知我这十年来的心在仇恨的尖刀上是如何滴血的么。密林中的魔物危害着这个村子呵。恳请您前去剿除。
                    this.setupDialogueChoices( // Set up choices.
                        new DialogueChoiceComponent(this.buildDialogueChoice(0), button -> { // Opens a new dialogue tree.//我的命运与密林也不无关系。请你告诉我前往密林的路径，到了密林我自然会听从我的心做出我的行动。自然，我的心已经听到了你的声音。
                            this.setDialogueAnswer(this.buildDialogueDialog(1,"(x,z)"));//密林，就在 s% 。你要睁大眼睛侧起耳朵开动脑筋来揣度密林中提示之物的含义，你要是有心，那么就趁有心之时让它发挥作用。对了，尊敬的勇者…… TODO 换成密林坐标
                            this.setupDialogueChoices(
                                new DialogueChoiceComponent(this.buildDialogueChoice(1), button1 -> {// 嗯？
                                    this.setDialogueAnswer(this.buildDialogueDialog(2));//算了，有的东西不过很久，是不可能理解的。 有的东西等到理解了，又为时已晚。 大多时候，我们不得不在尚未清楚认识自己的心的情况下选择行动，因而感到迷惘和困惑，因而悲伤不已。
                                    this.setupDialogueChoices(
                                        new DialogueChoiceComponent(this.buildDialogueChoice(1), button2 -> {// 嗯？
                                            this.setDialogueAnswer(this.buildDialogueDialog(3));//有些事情还无法言喻，有的则不便言喻。但你什么也不必担心。在某种意义上，村子和密林都是公平的。关于你所需要你所应该知道的，村子和密林以后将一一在你面前提示出来。
                                            this.setupDialogueChoices(
                                                new DialogueChoiceComponent(this.buildDialogueChoice(2), button3 -> this.finishChat((byte) 0))
                                            );
                                        })
                                    );
                                })
                            );
                        })
                    );
                    this.positionDialogue();
                }else {
//                    this.setDialogueAnswer(Component.translatable(elder.getDisplayName()+".dialog1"));//勇者啊，我所期盼的勇者啊你终于来了。你可知我这十年来的心在仇恨的尖刀上是如何滴血的么。密林中的魔物危害着这个村子呵。恳请您前去剿除。
//                    this.setupDialogueChoices( // Set up choices.
//                            new DialogueChoiceComponent(this.buildDialogueChoice("question"), button -> this.finishChat((byte) 0)),
//                            new DialogueChoiceComponent(this.buildDialogueChoice("challenge"), button -> { // Opens a new dialogue tree.
//                                this.setDialogueAnswer(Component.translatable("gui.aether.queen.dialog.challenge")); // The Valkyrie Queen's response to the challenge choice in the GUI (not a chat message).
//                                int medals = this.getMinecraft().player.getInventory().countItem(AetherItems.VICTORY_MEDAL.get());
//                                DialogueChoiceComponent startFightChoice = medals >= 10
//                                        ? new DialogueChoiceComponent(this.buildDialogueChoice("have_medals"), button1 -> this.finishChat((byte) 1))
//                                        : new DialogueChoiceComponent(this.buildDialogueChoice("no_medals").append(" (" + medals + "/10)"), button1 -> this.finishChat((byte) 1));
//                                this.setupDialogueChoices( // Set up another set of choices.
//                                        startFightChoice,
//                                        new DialogueChoiceComponent(this.buildDialogueChoice("deny_fight"), button1 -> this.finishChat((byte) 2))
//                                );
//                            }),
//                            new DialogueChoiceComponent(this.buildDialogueChoice("leave"), pButton -> this.finishChat((byte) 3))
//                    );
//                    this.positionDialogue();
                }

            }else {
                if(!TCRConfig.KILLED_BOSS1.get()){

                }else {

                }
            }



        }
    }

    @Override
    public void onClose() {
        if(TCRConfig.IS_WHITE.get()){
            if(!TCRConfig.KILLED_BOSS1.get()){
                finishChat((byte) 0);
            }else {
                finishChat((byte) 1);
            }

        }else {
            if(!TCRConfig.KILLED_BOSS1.get()){
                finishChat((byte) 2);
            }else {
                finishChat((byte) 3);
            }
        }

    }
}
