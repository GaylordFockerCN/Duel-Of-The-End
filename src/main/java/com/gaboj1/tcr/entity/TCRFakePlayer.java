package com.gaboj1.tcr.entity;

import com.gaboj1.tcr.block.entity.PortalBedEntity;
import com.gaboj1.tcr.item.TCRModItems;
import com.gaboj1.tcr.network.PacketRelay;
import com.gaboj1.tcr.network.TCRPacketHandler;
import com.gaboj1.tcr.network.packet.clientbound.SyncFakePlayerPacket;
import com.gaboj1.tcr.worldgen.dimension.TCRDimension;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

import java.util.*;

/**
 * 假的玩家，用于进入维度后留下人模型在床上
 */
public class TCRFakePlayer extends LivingEntity{

    public static final EntityDataAccessor<Optional<UUID>> REAL_PLAYER_UUID = SynchedEntityData.defineId(TCRFakePlayer.class, EntityDataSerializers.OPTIONAL_UUID);
    public static final EntityDataAccessor<Boolean> IS_SLIM = SynchedEntityData.defineId(TCRFakePlayer.class, EntityDataSerializers.BOOLEAN);
    public static final EntityDataAccessor<BlockPos> BED_POS = SynchedEntityData.defineId(TCRFakePlayer.class, EntityDataSerializers.BLOCK_POS);
    public static final String KEY = "TCRFakePlayerID";

    public TCRFakePlayer(EntityType<? extends LivingEntity> entityType, Level level) {
        super(entityType, level);
        //防止出现无主的情况
        if(!level.isClientSide && getRealPlayer() == null){
            discard();
        }
    }

    public TCRFakePlayer(Player realPlayer, Level level, BlockPos pos) {
        super(TCRModEntities.FAKE_PLAYER.get(), level);
        setRealPlayer(realPlayer);
        getEntityData().set(BED_POS, pos);
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        getEntityData().define(REAL_PLAYER_UUID, Optional.empty());
        getEntityData().define(IS_SLIM, false);
        getEntityData().define(BED_POS, BlockPos.ZERO);
    }

    @Override
    public void readAdditionalSaveData(@NotNull CompoundTag tag) {
        super.readAdditionalSaveData(tag);
        getEntityData().set(REAL_PLAYER_UUID, Optional.of(tag.getUUID("REAL_PLAYER_ID")));
        getEntityData().set(IS_SLIM, tag.getBoolean("IS_SLIM"));
        getEntityData().set(BED_POS, new BlockPos(tag.getInt("BED_POS_X"), tag.getInt("BED_POS_Y"), tag.getInt("BED_POS_Z")));
    }

    @Override
    public void addAdditionalSaveData(@NotNull CompoundTag tag) {
        super.addAdditionalSaveData(tag);
        tag.putUUID("REAL_PLAYER_ID", getEntityData().get(REAL_PLAYER_UUID).orElseThrow());
        tag.putBoolean("IS_SLIM", getEntityData().get(IS_SLIM));
        BlockPos pos = getEntityData().get(BED_POS);
        tag.putInt("BED_POS_X", pos.getX());
        tag.putInt("BED_POS_Y", pos.getY());
        tag.putInt("BED_POS_Z", pos.getZ());
    }

    @Override
    public void tick() {
        super.tick();
        BlockPos pos = getEntityData().get(BED_POS);
        //床被摧毁或被推下床则真身传送回来
        if(!level().isClientSide){
            if(pos.getCenter().distanceTo(position()) > 1 || !(level().getBlockEntity(pos) instanceof PortalBedEntity)){
                callBackRealPlayer();
            }
        }
        //假身不能位于同一维度
        if(level().getPlayerByUUID(getRealPlayerUuid()) != null){
            discard();
        }

    }

    public void callBackRealPlayer(){
        this.discard();
        Player player = getRealPlayer();
        if(player != null){
            player.teleportTo(((ServerLevel) level()), getX(), getY(), getZ(), new HashSet<>(), getXRot(), getYRot());
        }
    }

    @Override
    public @NotNull Component getDisplayName() {
        return Component.literal("");
    }

    @Override
    public boolean hurt(@NotNull DamageSource p_21016_, float p_21017_) {
        return false;
    }

    public void setRealPlayer(Player player){
        getEntityData().set(REAL_PLAYER_UUID, Optional.of(player.getUUID()));
        if(level().isClientSide){
            if(player instanceof AbstractClientPlayer clientPlayer && clientPlayer.getModelName().equals("slim")){
                getEntityData().set(IS_SLIM, true);
            }
        } else {
            PacketRelay.sendToPlayer(TCRPacketHandler.INSTANCE, new SyncFakePlayerPacket(player.getId(), this.getId()), ((ServerPlayer) player));
        }
        player.getPersistentData().putInt(KEY, this.getId());
    }

    public Player getRealPlayer(){
        return level().getServer().getLevel(TCRDimension.P_SKY_ISLAND_LEVEL_KEY).getPlayerByUUID(getRealPlayerUuid());
    }

    public UUID getRealPlayerUuid() {
        return getEntityData().get(REAL_PLAYER_UUID).orElseThrow();
    }

    public ResourceLocation getSkinTextureLocation() {
        if(level().getPlayerByUUID(getRealPlayerUuid()) instanceof AbstractClientPlayer player){
            return player.getSkinTextureLocation();
        }
        return null;
    }

    public BlockPos getBedPos(){
        return getEntityData().get(BED_POS);
    }
    public Direction getSleepDirection() {
        return level().getBlockState(getBedPos()).getBedDirection(level(), getBedPos());
    }

    public boolean isSlim(){
        return getEntityData().get(IS_SLIM);
    }

    @Override
    public @NotNull Iterable<ItemStack> getArmorSlots() {
        return new ArrayList<>();
    }

    @Override
    public @NotNull ItemStack getItemBySlot(@NotNull EquipmentSlot equipmentSlot) {
        return new ItemStack(TCRModItems.DREAMSCAPE_COIN_PLUS.get());
    }

    @Override
    public void setItemSlot(@NotNull EquipmentSlot equipmentSlot, @NotNull ItemStack itemStack) {

    }

    @Override
    public @NotNull HumanoidArm getMainArm() {
        return HumanoidArm.RIGHT;
    }

}
