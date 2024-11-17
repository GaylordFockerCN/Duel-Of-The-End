package com.gaboj1.tcr.entity.custom;

import com.gaboj1.tcr.entity.NpcDialogue;
import com.gaboj1.tcr.entity.ai.goal.NpcDialogueGoal;
import com.gaboj1.tcr.item.DOTEItems;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.EntityType;
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
import net.minecraft.world.item.trading.Merchant;
import net.minecraft.world.item.trading.MerchantOffer;
import net.minecraft.world.item.trading.MerchantOffers;
import net.minecraft.world.level.Level;
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
    @Nullable
    private Player conversingPlayer;
    private Player tradingPlayer;
    protected static final EntityDataAccessor<Integer> SKIN_ID = SynchedEntityData.defineId(StarChaser.class, EntityDataSerializers.INT);
    private static final int MAX_SKIN_ID = 1;
    public StarChaser(EntityType<? extends PathfinderMob> entityType, Level level) {
        super(entityType, level);
        WEAPONS.add(Items.IRON_AXE);
        WEAPONS.add(EpicFightItems.IRON_GREATSWORD.get());
        WEAPONS.add(EpicFightItems.IRON_LONGSWORD.get());
        WEAPONS.add(EpicFightItems.IRON_SPEAR.get());
        WEAPONS.add(EpicFightItems.IRON_TACHI.get());
        WEAPONS.add(EpicFightItems.UCHIGATANA.get());
        setItemInHand(InteractionHand.MAIN_HAND, WEAPONS.get(getRandom().nextInt(WEAPONS.size())).getDefaultInstance());
    }

    public static AttributeSupplier setAttributes() {
        return Animal.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 100.0f)
                .add(Attributes.ATTACK_DAMAGE, 20.0f)
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
    protected @NotNull InteractionResult mobInteract(@NotNull Player player, @NotNull InteractionHand hand) {
        setTradingPlayer(player);
        openTradingScreen(player, Component.empty(), 1);
        return InteractionResult.sidedSuccess(isClientSide());
    }

    @Override
    public void openDialogueScreen(CompoundTag senderData) {

    }

    @Override
    public void handleNpcInteraction(Player player, byte interactionID) {

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
        ItemStack knockdownWakeup = new ItemStack(EpicFightItems.SKILLBOOK.get());
        knockdownWakeup.getOrCreateTag().putString("skill", EpicFightSkills.KNOCKDOWN_WAKEUP.toString());

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

        ItemStack catharsis = new ItemStack(EpicFightItems.SKILLBOOK.get());
        catharsis.getOrCreateTag().putString("skill", EpicFightSkills.CATHARSIS.toString());

        ItemStack avengersPatience = new ItemStack(EpicFightItems.SKILLBOOK.get());
        avengersPatience.getOrCreateTag().putString("skill", EpicFightSkills.AVENGERS_PATIENCE.toString());

        ItemStack adaptiveSkin = new ItemStack(EpicFightItems.SKILLBOOK.get());
        adaptiveSkin.getOrCreateTag().putString("skill", EpicFightSkills.ADAPTIVE_SKIN.toString());

        ItemStack meteorStrike = new ItemStack(EpicFightItems.SKILLBOOK.get());
        meteorStrike.getOrCreateTag().putString("skill", EpicFightSkills.METEOR_STRIKE.toString());

        ItemStack revelation = new ItemStack(EpicFightItems.SKILLBOOK.get());
        revelation.getOrCreateTag().putString("skill", EpicFightSkills.REVELATION.toString());

        ItemStack demolitionLeap = new ItemStack(EpicFightItems.SKILLBOOK.get());
        demolitionLeap.getOrCreateTag().putString("skill", EpicFightSkills.DEMOLITION_LEAP.toString());

        ItemStack phantomAscent = new ItemStack(EpicFightItems.SKILLBOOK.get());
        phantomAscent.getOrCreateTag().putString("skill", EpicFightSkills.PHANTOM_ASCENT.toString());

        MerchantOffers skillBooks = new MerchantOffers();

        skillBooks.add(new MerchantOffer(
                new ItemStack(DOTEItems.ADGRAIN.get(), 5),
                knockdownWakeup,
                142857, 0, 0.02f));

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
                new ItemStack(DOTEItems.ADGRAIN.get(), 15),
                parrying,
                142857, 0, 0.02f));

        skillBooks.add(new MerchantOffer(
                new ItemStack(DOTEItems.ADGRAIN.get(), 15),
                impactGuard,
                142857, 0, 0.02f));

        skillBooks.add(new MerchantOffer(
                new ItemStack(DOTEItems.ADGRAIN.get(), 15),
                berserker,
                142857, 0, 0.02f));

        skillBooks.add(new MerchantOffer(
                new ItemStack(DOTEItems.ADGRAIN.get(), 15),
                deathHarvest,
                142857, 0, 0.02f));

        skillBooks.add(new MerchantOffer(
                new ItemStack(DOTEItems.ADGRAIN.get(), 15),
                emergencyEscape,
                142857, 0, 0.02f));

        skillBooks.add(new MerchantOffer(
                new ItemStack(DOTEItems.ADGRAIN.get(), 15),
                endurance,
                142857, 0, 0.02f));

        skillBooks.add(new MerchantOffer(
                new ItemStack(DOTEItems.ADGRAIN.get(), 15),
                forbiddenStrength,
                142857, 0, 0.02f));

        skillBooks.add(new MerchantOffer(
                new ItemStack(DOTEItems.ADGRAIN.get(), 15),
                hypervitality,
                142857, 0, 0.02f));

        skillBooks.add(new MerchantOffer(
                new ItemStack(DOTEItems.ADGRAIN.get(), 15),
                staminaPillager,
                142857, 0, 0.02f));

        skillBooks.add(new MerchantOffer(
                new ItemStack(DOTEItems.ADGRAIN.get(), 15),
                swordMaster,
                142857, 0, 0.02f));

        skillBooks.add(new MerchantOffer(
                new ItemStack(DOTEItems.ADGRAIN.get(), 15),
                technician,
                142857, 0, 0.02f));

        skillBooks.add(new MerchantOffer(
                new ItemStack(DOTEItems.ADGRAIN.get(), 15),
                catharsis,
                142857, 0, 0.02f));

        skillBooks.add(new MerchantOffer(
                new ItemStack(DOTEItems.ADGRAIN.get(), 15),
                avengersPatience,
                142857, 0, 0.02f));

        skillBooks.add(new MerchantOffer(
                new ItemStack(DOTEItems.ADGRAIN.get(), 15),
                adaptiveSkin,
                142857, 0, 0.02f));
        skillBooks.add(new MerchantOffer(
                new ItemStack(DOTEItems.ADGRAIN.get(), 25),
                meteorStrike,
                142857, 0, 0.02f));

        skillBooks.add(new MerchantOffer(
                new ItemStack(DOTEItems.ADGRAIN.get(), 15),
                revelation,
                142857, 0, 0.02f));

        skillBooks.add(new MerchantOffer(
                new ItemStack(DOTEItems.ADGRAIN.get(), 25),
                demolitionLeap,
                142857, 0, 0.02f));

        skillBooks.add(new MerchantOffer(
                new ItemStack(DOTEItems.ADGRAIN.get(), 15),
                phantomAscent,
                142857, 0, 0.02f));
        return skillBooks;
    }

    @Override
    public @NotNull MerchantOffers getOffers() {
        MerchantOffers merchantOffers = new MerchantOffers();
        merchantOffers.add(new MerchantOffer(
                new ItemStack(Items.ROTTEN_FLESH, 5),
                new ItemStack(DOTEItems.ADGRAIN.get(), 1),
                142857, 0, 0.02f));
        merchantOffers.add(new MerchantOffer(
                new ItemStack(DOTEItems.ADGRAIN.get(), 1),
                new ItemStack(Items.ROTTEN_FLESH, 5),
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
                new ItemStack(DOTEItems.ADGRAIN.get(), 64),
                new ItemStack(EpicFightItems.UCHIGATANA.get(), 1),
                142857, 0, 0.02f));
        return merchantOffers;
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
