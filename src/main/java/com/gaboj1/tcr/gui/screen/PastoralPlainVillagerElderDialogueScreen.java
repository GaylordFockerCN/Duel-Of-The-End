package com.gaboj1.tcr.gui.screen;

import com.gaboj1.tcr.TCRConfig;
import com.gaboj1.tcr.TheCasketOfReveriesMod;
import com.gaboj1.tcr.entity.custom.TreeGuardianEntity;
import com.gaboj1.tcr.entity.custom.villager.PastoralPlainVillagerElder;
import com.gaboj1.tcr.gui.screen.component.DialogueAnswerComponent;
import com.gaboj1.tcr.gui.screen.component.DialogueChoiceComponent;
import com.gaboj1.tcr.init.TCRModEntities;
import com.gaboj1.tcr.network.PacketRelay;
import com.gaboj1.tcr.network.TCRPacketHandler;
import com.gaboj1.tcr.network.packet.NpcPlayerInteractPacket;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Renderable;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.client.event.ScreenEvent;
import net.minecraftforge.common.MinecraftForge;

/**
 * 改编自theAether 的 ValkyrieQueenDialogueScreen
 * 搬运了相关类
 */
public class PastoralPlainVillagerElderDialogueScreen extends Screen {
    public static final ResourceLocation MY_BACKGROUND_LOCATION = new ResourceLocation(TheCasketOfReveriesMod.MOD_ID,"textures/gui/background.png");
    private final DialogueAnswerComponent dialogueAnswer;
    private final TreeGuardianEntity elder;//TODO 改回去

    public PastoralPlainVillagerElderDialogueScreen(TreeGuardianEntity elder) {
        super(elder.getDisplayName());
        this.dialogueAnswer = new DialogueAnswerComponent(this.buildDialogueAnswerName(elder.getDisplayName().copy().withStyle(ChatFormatting.YELLOW)));
        this.elder = elder;
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

    /**
     * Adds and repositions a new set of dialogue options.
     * @param options The {@link DialogueChoiceComponent} option buttons to render.
     */
    public void setupDialogueChoices(DialogueChoiceComponent... options) {
        this.clearWidgets();
        for (DialogueChoiceComponent option : options) {
            this.addRenderableWidget(option);
        }
        this.positionDialogue();
    }

    /**
     * Repositions the Valkyrie Queen's dialogue answer and the player's dialogue choices based on the amount of choices.
     */
    private void positionDialogue() {
        // Dialogue answer.
        this.dialogueAnswer.reposition(this.width, this.height *5/4);//相较于天堂的下移了一点
        // Dialogue choices.
        int lineNumber = this.dialogueAnswer.height / 12 + 1;
        for (Renderable renderable : this.renderables) {
            if (renderable instanceof DialogueChoiceComponent option) {
                option.setX(this.width / 2 - option.getWidth() / 2);
                option.setY(this.height / 2 *5/4 + 12 * lineNumber);//调低一点
                lineNumber++;
            }
        }
    }

    /**
     * Sets what message to display for a dialogue answer.
     * @param component The message {@link Component}.
     */
    private void setDialogueAnswer(Component component) {
        elder.chat(component);//左下角重复一下，方便回看
        this.dialogueAnswer.updateDialogue(this.buildDialogueAnswerName(this.elder.getDisplayName()).append(": ").append(component));
    }

    /**
     * Sets up the formatting for the Valkyrie Queen's name in the {@link DialogueAnswerComponent} widget.
     * @param component The name {@link Component}.
     * @return The formatted {@link MutableComponent}.
     */
    public MutableComponent buildDialogueAnswerName(Component component) {
        return Component.literal("[").append(component.copy().withStyle(ChatFormatting.YELLOW)).append("]");
    }

    /**
     * Sets up the text for a player dialogue choice in a {@link DialogueChoiceComponent} button.
     * @param key The suffix {@link String} to get the full translation key from.
     * @return The {@link MutableComponent} for the choice text.
     */
    public MutableComponent buildDialogueChoice(String key) {
        return Component.translatable(TCRModEntities.PASTORAL_PLAIN_VILLAGER_ELDER.get()+".choice." + key);
    }
    public MutableComponent buildDialogueChoice(int i) {
        return Component.translatable(TCRModEntities.PASTORAL_PLAIN_VILLAGER_ELDER.get()+".choice" + i);
    }
    public MutableComponent buildDialogueDialog(int i) {
        Component component = Component.translatable(TCRModEntities.PASTORAL_PLAIN_VILLAGER_ELDER.get()+".dialog"+i);
        Player player = elder.getConversingPlayer();
        if(player!=null)
            player.sendSystemMessage(Component.literal("[").append(player.getCustomName().copy().withStyle(TCRConfig.IS_WHITE.get()?ChatFormatting.YELLOW:ChatFormatting.BLACK)).append("]: ").append(component));
        return component.copy();
    }

    public MutableComponent buildDialogueDialog(int i, String s) {
        Component component = Component.translatable(TCRModEntities.PASTORAL_PLAIN_VILLAGER_ELDER.get()+".dialog"+i,s);
        Player player = elder.getConversingPlayer();
        if(player!=null)
            player.sendSystemMessage(Component.literal("[").append(player.getCustomName().copy().withStyle(TCRConfig.IS_WHITE.get()?ChatFormatting.YELLOW:ChatFormatting.BLACK)).append("]: ").append(component));
        return component.copy();
    }

    /**
     * Sends an NPC interaction to the server, which is sent through a packet to be handled in {@link PastoralPlainVillagerElder#handleNpcInteraction(Player, byte)}.
     * @param interactionID A code for which interaction was performed on the client.<br>
     *                      0 - "What can you tell me about this place?"<br>
     *                      1 - "I wish to fight you!"<br>
     *                      2 - "On second thought, I'd rather not."<br>
     *                      3 - "Nevermind."<br>
     * @see NpcPlayerInteractPacket
     * @see PastoralPlainVillagerElder#handleNpcInteraction(Player, byte)
     */
    private void finishChat(byte interactionID) {
        PacketRelay.sendToServer(TCRPacketHandler.INSTANCE, new NpcPlayerInteractPacket(this.elder.getId(), interactionID));
        super.onClose();
    }

    @Override
    public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTicks) {
        this.renderBackground(guiGraphics);
        this.dialogueAnswer.render(guiGraphics);
        super.render(guiGraphics, mouseX, mouseY, partialTicks);
    }

    /**
     * [CODE COPY] - {@link Screen#renderBackground(GuiGraphics)}.<br><br>
     * Remove code for dark gradient and dirt background.
     */
    @Override
    public void renderBackground(GuiGraphics guiGraphics) {
        if (this.getMinecraft().level != null) {
//            guiGraphics.blit(MY_BACKGROUND_LOCATION, 0, 0, 0, 0.0F, 0.0F, this.width, this.height, 214, 252);
            MinecraftForge.EVENT_BUS.post(new ScreenEvent.BackgroundRendered(this, guiGraphics));
        }
    }

    @Override
    public void resize(Minecraft minecraft, int width, int height) {
        this.width = width;
        this.height = height;
        this.positionDialogue();
    }

    @Override
    public boolean isPauseScreen() {
        return false;
    }

    @Override
    public void onClose() {
        if(TCRConfig.IS_WHITE.get()){
            if(!TCRConfig.KILLED_BOSS1.get()){
                this.finishChat((byte) 0);
            }else {
                this.finishChat((byte) 1);
            }

        }else {
            if(!TCRConfig.KILLED_BOSS1.get()){
                this.finishChat((byte) 2);
            }else {
                this.finishChat((byte) 3);
            }
        }

    }
}
