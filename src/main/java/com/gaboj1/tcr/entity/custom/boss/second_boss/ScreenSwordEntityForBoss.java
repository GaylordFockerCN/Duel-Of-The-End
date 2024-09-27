package com.gaboj1.tcr.entity.custom.boss.second_boss;

import com.gaboj1.tcr.TheCasketOfReveriesMod;
import com.gaboj1.tcr.entity.TCREntities;
import com.gaboj1.tcr.entity.custom.sword.AbstractSwordEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.network.PlayMessages;
import org.jetbrains.annotations.NotNull;

/**
 * Boss的剑盾的实体
 */
public class ScreenSwordEntityForBoss extends Entity implements AbstractSwordEntity {

    private static final EntityDataAccessor<Integer> OWNER_ID = SynchedEntityData.defineId(ScreenSwordEntityForBoss.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Integer> RAIN_SCREEN_SWORD_ID = SynchedEntityData.defineId(ScreenSwordEntityForBoss.class, EntityDataSerializers.INT);
    private SecondBossEntity owner;

    public ScreenSwordEntityForBoss(EntityType<?> p_19870_, Level p_19871_) {
        super(p_19870_, p_19871_);
    }

    public ScreenSwordEntityForBoss(Level level, SecondBossEntity boss, int id){
        super(TCREntities.RAIN_SCREEN_SWORD_FOR_BOSS2.get(), level);
        setOwnerAndId(boss, id);
    }

    @Override
    protected void defineSynchedData() {
        this.getEntityData().define(OWNER_ID, -1);
        this.getEntityData().define(RAIN_SCREEN_SWORD_ID, -1);
    }

    @Override
    protected void readAdditionalSaveData(@NotNull CompoundTag compoundTag) {
        getEntityData().set(OWNER_ID,compoundTag.getInt("owner_id"));
        getEntityData().set(RAIN_SCREEN_SWORD_ID, compoundTag.getInt("rain_screen_sword_id"));
    }

    @Override
    protected void addAdditionalSaveData(@NotNull CompoundTag compoundTag) {
        compoundTag.putInt("owner_id", getEntityData().get(OWNER_ID));
        compoundTag.putInt("rain_screen_sword_id", getEntityData().get(RAIN_SCREEN_SWORD_ID));
    }
    /**
     * id: 0 ~ 3
     */
    public void setOwnerAndId(SecondBossEntity owner, int swordID) {
        this.owner = owner;
        owner.getShieldId().add(getId());
        owner.getEntityData().set(SecondBossEntity.HAS_SWORD_SHIELD, true);
        this.getEntityData().set(OWNER_ID, owner.getId());
        getEntityData().set(RAIN_SCREEN_SWORD_ID, swordID);
    }


    public int getSwordId() {
        return getEntityData().get(RAIN_SCREEN_SWORD_ID);
    }

    public Vec3 getOffset(){
        double dis = 1.3;
        return switch (getSwordId()){
            case 0 -> new Vec3(-dis,0,0);
            case 1 -> new Vec3(0,0,-dis);
            case 2 -> new Vec3(dis,0,0);
            case 3 -> new Vec3(0,0,dis);
            default -> new Vec3(0,0,0);
        };
    }

    @Override
    public void tick() {
        if(level().isClientSide && owner == null && level().getEntity(getEntityData().get(OWNER_ID)) instanceof SecondBossEntity boss){
            owner = boss;
        }

        if(owner == null){
            TheCasketOfReveriesMod.LOGGER.info("sword entity "+ getId() + " doesn't have owner " + level());
            discard();
            return;
        }

        //围绕owner旋转
        Vec3 center = owner.getPosition(0.5f);
        Vec3 now = center.add(getOffset());
        double radians = tickCount * 0.1;
        double rotatedX = center.x + (float) (Math.cos(radians) * (now.x - center.x) - Math.sin(radians) * (now.z - center.z));
        double rotatedZ = center.z + (float) (Math.sin(radians) * (now.x - center.x) + Math.cos(radians) * (now.z - center.z));
        moveTo(new Vec3(rotatedX, now.y+Math.sin(radians)*0.3, rotatedZ));
    }

    /**
     * 移除的时候播音效并让boss进入生盾冷却
     */
    @Override
    public void remove(@NotNull RemovalReason reason) {
        level().playSound(null, getX(), getY(), getZ(), SoundEvents.SHIELD_BREAK, SoundSource.BLOCKS, 1, 1);
        super.remove(reason);
    }

    /**
     * 痛苦地调位置
     * 雨帘剑的位置又他妈不一样...
     * @param poseStack 来自Renderer的render
     */
    @Override
    public void setPose(PoseStack poseStack) {
        poseStack.mulPose(Axis.ZP.rotationDegrees(-225));
        //碰撞箱偏高（调这个太折磨人了，xyz轴都不知道转成什么样了只能一个个试）
        poseStack.translate(0.8,-0.8,0);
    }

    @Override
    public ItemStack getItemStack() {
        return Items.DIAMOND_SWORD.getDefaultInstance();//TODO 换自己的武器
    }
}
