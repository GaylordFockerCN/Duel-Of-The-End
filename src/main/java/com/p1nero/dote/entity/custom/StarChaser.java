package com.p1nero.dote.entity.custom;

import com.p1nero.dote.archive.DOTEArchiveManager;
import com.p1nero.dote.client.gui.DialogueComponentBuilder;
import com.p1nero.dote.client.gui.screen.LinkListStreamDialogueScreenBuilder;
import com.p1nero.dote.client.gui.TreeNode;
import com.p1nero.dote.entity.NpcDialogue;
import com.p1nero.dote.entity.ai.goal.NpcDialogueGoal;
import com.p1nero.dote.item.DOTEItems;
import com.p1nero.dote.network.DOTEPacketHandler;
import com.p1nero.dote.network.PacketRelay;
import com.p1nero.dote.network.packet.clientbound.NPCDialoguePacket;
import net.minecraft.client.Minecraft;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.RandomStrollGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.PotionUtils;
import net.minecraft.world.item.alchemy.Potions;
import net.minecraft.world.item.trading.Merchant;
import net.minecraft.world.item.trading.MerchantOffer;
import net.minecraft.world.item.trading.MerchantOffers;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import yesman.epicfight.gameasset.EpicFightSkills;
import yesman.epicfight.world.entity.ai.attribute.EpicFightAttributes;
import yesman.epicfight.world.item.EpicFightItems;

import java.util.ArrayList;
import java.util.List;

/**
 * 追星者，设定是和玩家同阵营的，可交易可对话
 */
public class StarChaser extends PathfinderMob implements NpcDialogue, Merchant {
    public static final List<Item> WEAPONS = new ArrayList<>();
    private final DialogueComponentBuilder BUILDER;
    @Nullable
    private Player conversingPlayer;
    private Player tradingPlayer;
    private MerchantOffers currentOffers = new MerchantOffers();
    protected static final EntityDataAccessor<Integer> SKIN_ID = SynchedEntityData.defineId(StarChaser.class, EntityDataSerializers.INT);
    private static final int MAX_SKIN_ID = 13;//总人数
    public StarChaser(EntityType<? extends PathfinderMob> entityType, Level level) {
        super(entityType, level);
        WEAPONS.add(Items.IRON_AXE);
        WEAPONS.add(EpicFightItems.IRON_GREATSWORD.get());
        WEAPONS.add(EpicFightItems.IRON_LONGSWORD.get());
        WEAPONS.add(EpicFightItems.IRON_SPEAR.get());
        WEAPONS.add(EpicFightItems.IRON_TACHI.get());
        WEAPONS.add(EpicFightItems.UCHIGATANA.get());
        setItemInHand(InteractionHand.MAIN_HAND, WEAPONS.get(getRandom().nextInt(WEAPONS.size())).getDefaultInstance());
        BUILDER = new DialogueComponentBuilder(getType());
    }

    public static AttributeSupplier setAttributes() {
        return Animal.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 70.0f)
                .add(Attributes.ATTACK_DAMAGE, 8.0f)
                .add(Attributes.MOVEMENT_SPEED, 0.3f)
                .add(Attributes.KNOCKBACK_RESISTANCE, 114514f)
                .add(EpicFightAttributes.MAX_STRIKES.get(), 3)
                .add(EpicFightAttributes.MAX_STAMINA.get(), 80)
                .add(EpicFightAttributes.WEIGHT.get(), 3)
                .build();
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        getEntityData().define(SKIN_ID, getRandom().nextInt(MAX_SKIN_ID));
    }

    @Override
    public void readAdditionalSaveData(@NotNull CompoundTag tag) {
        super.readAdditionalSaveData(tag);
        this.getEntityData().set(SKIN_ID,tag.getInt("skin_id"));
    }

    @Override
    public void addAdditionalSaveData(@NotNull CompoundTag tag) {
        super.addAdditionalSaveData(tag);
        tag.putInt("skin_id", this.getEntityData().get(SKIN_ID));
    }

    public int getSkinId() {
        return getEntityData().get(SKIN_ID);
    }

    @Override
    protected void registerGoals() {
        super.registerGoals();
        this.targetSelector.addGoal(0, new HurtByTargetGoal(this));
        this.targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(this, DOTEZombie.class, false));
        this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, Monster.class, false));
        this.targetSelector.addGoal(0, new NpcDialogueGoal<>(this));
        this.goalSelector.addGoal(1, new RandomStrollGoal(this, 1));
        this.goalSelector.addGoal(2, new RandomLookAroundGoal(this));
    }

    @Override
    public boolean hurt(@NotNull DamageSource source, float p_21017_) {
        if(getConversingPlayer() != null || getTradingPlayer() != null){
            return false;
        }
        if(source.getEntity() instanceof DOTEBoss){
            return super.hurt(source, p_21017_ * 10);
        }
        return super.hurt(source, p_21017_);
    }

    @Override
    public boolean removeWhenFarAway(double p_21542_) {
        return false;
    }

    @Override
    public boolean shouldShowName() {
        return true;
    }

    @Override
    public @NotNull Component getDisplayName() {
        return DOTEArchiveManager.getWorldLevel() < 3 ? super.getDisplayName() : Component.literal("§k???");
    }

    @Nullable
    @Override
    public LivingEntity getTarget() {
        if(getTradingPlayer() != null || getConversingPlayer() != null){
            return null;
        }
        return super.getTarget();
    }

    @Override
    protected @NotNull InteractionResult mobInteract(@NotNull Player player, @NotNull InteractionHand hand) {
        //shift右键换皮
        if(player.isCreative() && player.isShiftKeyDown()){
            getEntityData().set(SKIN_ID, (getSkinId() + 1) % MAX_SKIN_ID);
            return InteractionResult.sidedSuccess(isClientSide());
        }
        //打架的时候不能对话
        if(getTarget() != null){
            return InteractionResult.sidedSuccess(isClientSide());
        }
        if (player instanceof ServerPlayer serverPlayer) {
            this.lookAt(player, 180.0F, 180.0F);
            if (this.getConversingPlayer() == null) {
                PacketRelay.sendToPlayer(DOTEPacketHandler.INSTANCE, new NPCDialoguePacket(this.getId(), new CompoundTag()), serverPlayer);
                this.setConversingPlayer(serverPlayer);
            }
        }
        return InteractionResult.sidedSuccess(isClientSide());
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void openDialogueScreen(CompoundTag senderData) {
        LinkListStreamDialogueScreenBuilder builder =  new LinkListStreamDialogueScreenBuilder(this);
        Component greeting = BUILDER.buildDialogueAnswer(getRandom().nextInt(4));//0 1 2 3随机选一句问候
        Component answer = BUILDER.buildDialogueAnswer(4 + getRandom().nextInt(7));//4 ~ 10随机选一句回答
        //初次对话
        builder.setAnswerRoot(new TreeNode(greeting)
                .addLeaf(BUILDER.buildDialogueOption(0), (byte) 1)//交易技能书
                .addLeaf(BUILDER.buildDialogueOption(1), (byte) 2)//交易物品
                .addChild(new TreeNode(answer, BUILDER.buildDialogueOption(2))//询问
                        .addLeaf(BUILDER.buildDialogueOption(3), (byte) 3)));
        if(!builder.isEmpty()){
            Minecraft.getInstance().setScreen(builder.build());
        }
    }

    @Override
    public void handleNpcInteraction(Player player, byte interactionID) {
        switch (interactionID){
            case 1:
                currentOffers = getSkillBookOffers();
                setTradingPlayer(player);
                openTradingScreen(player, Component.empty(), 1);
                break;
            case 2:
                currentOffers = getCustomMerchantOffers();
                setTradingPlayer(player);
                openTradingScreen(player, Component.empty(), 1);
                break;
        }
        setConversingPlayer(null);
    }

    @Override
    public void setConversingPlayer(@Nullable Player player) {
        this.conversingPlayer = player;
    }

    @Nullable
    @Override
    public Player getConversingPlayer() {
        return conversingPlayer;
    }

    @Override
    public void setTradingPlayer(@Nullable Player player) {
        this.tradingPlayer = player;
    }

    @Nullable
    @Override
    public Player getTradingPlayer() {
        return tradingPlayer;
    }

    public MerchantOffers getSkillBookOffers(){

        ItemStack roll = new ItemStack(EpicFightItems.SKILLBOOK.get());
        roll.getOrCreateTag().putString("skill", EpicFightSkills.ROLL.toString());

        ItemStack step = new ItemStack(EpicFightItems.SKILLBOOK.get());
        step.getOrCreateTag().putString("skill", EpicFightSkills.STEP.toString());

        ItemStack guard = new ItemStack(EpicFightItems.SKILLBOOK.get());
        guard.getOrCreateTag().putString("skill", EpicFightSkills.GUARD.toString());

        ItemStack parrying = new ItemStack(EpicFightItems.SKILLBOOK.get());
        parrying.getOrCreateTag().putString("skill", EpicFightSkills.PARRYING.toString());

        ItemStack impactGuard = new ItemStack(EpicFightItems.SKILLBOOK.get());
        impactGuard.getOrCreateTag().putString("skill", EpicFightSkills.IMPACT_GUARD.toString());

        ItemStack berserker = new ItemStack(EpicFightItems.SKILLBOOK.get());
        berserker.getOrCreateTag().putString("skill", EpicFightSkills.BERSERKER.toString());

        ItemStack deathHarvest = new ItemStack(EpicFightItems.SKILLBOOK.get());
        deathHarvest.getOrCreateTag().putString("skill", EpicFightSkills.DEATH_HARVEST.toString());

        ItemStack emergencyEscape = new ItemStack(EpicFightItems.SKILLBOOK.get());
        emergencyEscape.getOrCreateTag().putString("skill", EpicFightSkills.EMERGENCY_ESCAPE.toString());

        ItemStack endurance = new ItemStack(EpicFightItems.SKILLBOOK.get());
        endurance.getOrCreateTag().putString("skill", EpicFightSkills.ENDURANCE.toString());

        ItemStack forbiddenStrength = new ItemStack(EpicFightItems.SKILLBOOK.get());
        forbiddenStrength.getOrCreateTag().putString("skill", EpicFightSkills.FORBIDDEN_STRENGTH.toString());

        ItemStack hypervitality = new ItemStack(EpicFightItems.SKILLBOOK.get());
        hypervitality.getOrCreateTag().putString("skill", EpicFightSkills.HYPERVITALITY.toString());

        ItemStack staminaPillager = new ItemStack(EpicFightItems.SKILLBOOK.get());
        staminaPillager.getOrCreateTag().putString("skill", EpicFightSkills.STAMINA_PILLAGER.toString());

        ItemStack swordMaster = new ItemStack(EpicFightItems.SKILLBOOK.get());
        swordMaster.getOrCreateTag().putString("skill", EpicFightSkills.SWORD_MASTER.toString());

        ItemStack technician = new ItemStack(EpicFightItems.SKILLBOOK.get());
        technician.getOrCreateTag().putString("skill", EpicFightSkills.TECHNICIAN.toString());

        ItemStack meteorStrike = new ItemStack(EpicFightItems.SKILLBOOK.get());
        meteorStrike.getOrCreateTag().putString("skill", EpicFightSkills.METEOR_STRIKE.toString());

        ItemStack demolitionLeap = new ItemStack(EpicFightItems.SKILLBOOK.get());
        demolitionLeap.getOrCreateTag().putString("skill", EpicFightSkills.DEMOLITION_LEAP.toString());

        ItemStack phantomAscent = new ItemStack(EpicFightItems.SKILLBOOK.get());
        phantomAscent.getOrCreateTag().putString("skill", EpicFightSkills.PHANTOM_ASCENT.toString());

        MerchantOffers skillBooks = new MerchantOffers();

        skillBooks.add(new MerchantOffer(
                new ItemStack(DOTEItems.ADGRAIN.get(), 5),
                roll,
                142857, 0, 0.02f));

        skillBooks.add(new MerchantOffer(
                new ItemStack(DOTEItems.ADGRAIN.get(), 5),
                step,
                142857, 0, 0.02f));

        skillBooks.add(new MerchantOffer(
                new ItemStack(DOTEItems.ADGRAIN.get(), 5),
                guard,
                142857, 0, 0.02f));

        skillBooks.add(new MerchantOffer(
                new ItemStack(DOTEItems.ADVENTURESPAR.get(), 15),
                parrying,
                142857, 0, 0.02f));

        skillBooks.add(new MerchantOffer(
                new ItemStack(DOTEItems.ADVENTURESPAR.get(), 15),
                impactGuard,
                142857, 0, 0.02f));

        skillBooks.add(new MerchantOffer(
                new ItemStack(DOTEItems.ADVENTURESPAR.get(), 15),
                berserker,
                142857, 0, 0.02f));

        skillBooks.add(new MerchantOffer(
                new ItemStack(DOTEItems.ADVENTURESPAR.get(), 15),
                deathHarvest,
                142857, 0, 0.02f));

        skillBooks.add(new MerchantOffer(
                new ItemStack(DOTEItems.ADVENTURESPAR.get(), 15),
                emergencyEscape,
                142857, 0, 0.02f));

        skillBooks.add(new MerchantOffer(
                new ItemStack(DOTEItems.ADVENTURESPAR.get(), 15),
                endurance,
                142857, 0, 0.02f));

        skillBooks.add(new MerchantOffer(
                new ItemStack(DOTEItems.ADVENTURESPAR.get(), 15),
                forbiddenStrength,
                142857, 0, 0.02f));

        skillBooks.add(new MerchantOffer(
                new ItemStack(DOTEItems.ADVENTURESPAR.get(), 15),
                hypervitality,
                142857, 0, 0.02f));

        skillBooks.add(new MerchantOffer(
                new ItemStack(DOTEItems.ADVENTURESPAR.get(), 15),
                staminaPillager,
                142857, 0, 0.02f));

        skillBooks.add(new MerchantOffer(
                new ItemStack(DOTEItems.ADVENTURESPAR.get(), 15),
                swordMaster,
                142857, 0, 0.02f));

        skillBooks.add(new MerchantOffer(
                new ItemStack(DOTEItems.ADVENTURESPAR.get(), 15),
                technician,
                142857, 0, 0.02f));

        skillBooks.add(new MerchantOffer(
                new ItemStack(DOTEItems.ADVENTURESPAR.get(), 25),
                meteorStrike,
                142857, 0, 0.02f));

        skillBooks.add(new MerchantOffer(
                new ItemStack(DOTEItems.ADVENTURESPAR.get(), 25),
                demolitionLeap,
                142857, 0, 0.02f));

        skillBooks.add(new MerchantOffer(
                new ItemStack(DOTEItems.ADVENTURESPAR.get(), 15),
                phantomAscent,
                142857, 0, 0.02f));
        return skillBooks;
    }

    public MerchantOffers getCustomMerchantOffers() {
        MerchantOffers merchantOffers = new MerchantOffers();
        ItemStack potion = new ItemStack(Items.POTION);
        PotionUtils.setPotion(potion, Potions.STRONG_HEALING);
        merchantOffers.add(new MerchantOffer(
                new ItemStack(Items.ROTTEN_FLESH, 5),
                new ItemStack(DOTEItems.ADGRAIN.get(), 1),
                142857, 0, 0.02f));
        merchantOffers.add(new MerchantOffer(
                new ItemStack(DOTEItems.ADGRAIN.get(), 1),
                new ItemStack(Items.ROTTEN_FLESH, 5),
                142857, 0, 0.02f));
        merchantOffers.add(new MerchantOffer(
                new ItemStack(DOTEItems.ADGRAIN.get(), 1),
                new ItemStack(Items.BREAD, 10),
                142857, 0, 0.02f));
        merchantOffers.add(new MerchantOffer(
                new ItemStack(DOTEItems.ADVENTURESPAR.get(), 25),
                potion,
                142857, 0, 0.02f));
        merchantOffers.add(new MerchantOffer(
                new ItemStack(DOTEItems.ADVENTURESPAR.get(), 5),
                new ItemStack(Items.RESPAWN_ANCHOR, 1),
                142857, 0, 0.02f));
        merchantOffers.add(new MerchantOffer(
                new ItemStack(DOTEItems.ADGRAIN.get(), 5),
                new ItemStack(Items.GLOWSTONE, 1),
                142857, 0, 0.02f));
        merchantOffers.add(new MerchantOffer(
                new ItemStack(DOTEItems.ADGRAIN.get(), 5),
                new ItemStack(Items.WHITE_WOOL, 1),
                142857, 0, 0.02f));
        merchantOffers.add(new MerchantOffer(
                new ItemStack(DOTEItems.ADGRAIN.get(), 5),
                new ItemStack(Items.LEATHER, 5),
                142857, 0, 0.02f));
        merchantOffers.add(new MerchantOffer(
                new ItemStack(DOTEItems.ADGRAIN.get(), 1),
                new ItemStack(Items.OAK_PLANKS, 8),
                142857, 0, 0.02f));
        merchantOffers.add(new MerchantOffer(
                new ItemStack(DOTEItems.ADGRAIN.get(), 1),
                new ItemStack(Items.COBBLESTONE, 4),
                142857, 0, 0.02f));
        merchantOffers.add(new MerchantOffer(
                new ItemStack(DOTEItems.ADGRAIN.get(), 5),
                new ItemStack(Items.IRON_INGOT, 2),
                142857, 0, 0.02f));
        merchantOffers.add(new MerchantOffer(
                new ItemStack(DOTEItems.ADGRAIN.get(), 5),
                new ItemStack(Items.COPPER_INGOT, 2),
                142857, 0, 0.02f));
        merchantOffers.add(new MerchantOffer(
                new ItemStack(DOTEItems.ADGRAIN.get(), 1),
                new ItemStack(Items.APPLE, 5),
                142857, 0, 0.02f));
        merchantOffers.add(new MerchantOffer(
                new ItemStack(DOTEItems.ADGRAIN.get(), 5),
                new ItemStack(Items.GOLD_INGOT, 1),
                142857, 0, 0.02f));
        merchantOffers.add(new MerchantOffer(
                new ItemStack(DOTEItems.ADGRAIN.get(), 10),
                new ItemStack(Items.DIAMOND, 1),
                142857, 0, 0.02f));
        merchantOffers.add(new MerchantOffer(
                new ItemStack(DOTEItems.NETHERITESS.get(), 1),
                new ItemStack(Items.GOLD_INGOT, 1),
                new ItemStack(Items.NETHERITE_SCRAP, 2),
                142857, 0, 0.02f));
        merchantOffers.add(new MerchantOffer(
                new ItemStack(DOTEItems.ADVENTURESPAR.get(), 20),
                new ItemStack(EpicFightItems.UCHIGATANA.get(), 1),
                142857, 0, 0.02f));
        merchantOffers.add(new MerchantOffer(
                new ItemStack(DOTEItems.NETHERITESS.get(), 1),
                new ItemStack(Items.COPPER_BLOCK, 4),
                new ItemStack(DOTEItems.NETHERROT_INGOT.get(), 1),
                142857, 0, 0.02f));
        merchantOffers.add(new MerchantOffer(
                new ItemStack(DOTEItems.WITHERC.get(), 1),
                new ItemStack(Items.OBSIDIAN, 4),
                new ItemStack(DOTEItems.DRAGONSTEEL_INGOT.get(), 1),
                142857, 0, 0.02f));
        return merchantOffers;
    }

    @Override
    public @NotNull MerchantOffers getOffers() {
        return currentOffers;
    }

    @Override
    public void overrideOffers(@NotNull MerchantOffers merchantOffers) {

    }

    @Override
    public void notifyTrade(@NotNull MerchantOffer merchantOffer) {

    }

    @Override
    public void notifyTradeUpdated(@NotNull ItemStack itemStack) {

    }

    @Override
    public int getVillagerXp() {
        return 0;
    }

    @Override
    public void overrideXp(int i) {

    }

    @Override
    public boolean showProgressBar() {
        return false;
    }

    @Override
    public @NotNull SoundEvent getNotifyTradeSound() {
        return SoundEvents.EXPERIENCE_ORB_PICKUP;
    }

    @Override
    public boolean isClientSide() {
        return level().isClientSide();
    }
}
