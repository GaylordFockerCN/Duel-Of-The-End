package com.gaboj1.tcr.entity.custom.sword;

import com.gaboj1.tcr.TheCasketOfReveriesMod;
import com.gaboj1.tcr.capability.TCRCapabilityProvider;
import com.gaboj1.tcr.capability.TCRPlayer;
import com.gaboj1.tcr.entity.TCREntities;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.network.PlayMessages;

import java.util.Optional;
import java.util.UUID;

/**
 * 都用EntityDataAccessor了还继承有点没必要..但是不知道怎么抛弃EntityDataAccessor
 */
public class ScreenSwordEntity extends SwordEntity{

    private static final EntityDataAccessor<Optional<UUID>> RIDER_UUID = SynchedEntityData.defineId(ScreenSwordEntity.class, EntityDataSerializers.OPTIONAL_UUID);
    private static final EntityDataAccessor<ItemStack> ITEM_STACK = SynchedEntityData.defineId(ScreenSwordEntity.class, EntityDataSerializers.ITEM_STACK);
    private static final EntityDataAccessor<Integer> RAIN_SCREEN_SWORD_ID = SynchedEntityData.defineId(ScreenSwordEntity.class, EntityDataSerializers.INT);

    public ScreenSwordEntity(EntityType<?> p_19870_, Level p_19871_) {
        super(p_19870_, p_19871_);
        this.getEntityData().define(ITEM_STACK, ItemStack.EMPTY);
        this.getEntityData().define(RIDER_UUID, Optional.empty());
        this.getEntityData().define(RAIN_SCREEN_SWORD_ID, -1);
    }

    public ScreenSwordEntity(PlayMessages.SpawnEntity spawnEntity, Level level) {
        this(TCREntities.RAIN_SCREEN_SWORD.get(), level);
    }

    @Override
    public ItemStack getItemStack() {
        return this.getEntityData().get(ITEM_STACK);
    }

    @Override
    public void setItemStack(ItemStack itemStack) {
        this.getEntityData().set(ITEM_STACK, itemStack);
    }

    @Override
    public void setRider(Player rider) {
        this.rider = rider;
        this.getEntityData().set(RIDER_UUID, Optional.of(rider.getUUID()));
    }

    /**
     * 0 ~ 3
     */
    public void setSwordID(int swordID){
        getEntityData().set(RAIN_SCREEN_SWORD_ID, swordID);
    }

    public int getRainScreenSwordId() {
        return getEntityData().get(RAIN_SCREEN_SWORD_ID);
    }

    public Vec3 getOffset(){
        double dis = 1.3;
        return switch (getRainScreenSwordId()){
            case 0 -> new Vec3(-dis,0,0);
            case 1 -> new Vec3(0,0,-dis);
            case 2 -> new Vec3(dis,0,0);
            case 3 -> new Vec3(0,0,dis);
            default -> new Vec3(0,0,0);
        };
    }

    @Override
    public void tick() {
        //想办法不让rider为null
        if(rider == null){
            if(this.getEntityData().get(RIDER_UUID).isPresent()){
                rider = level().getPlayerByUUID(this.getEntityData().get(RIDER_UUID).get());
            }else {
                TheCasketOfReveriesMod.LOGGER.info("sword entity "+ getId() + " doesn't have rider "+level());
                discard();
                return;
            }
        }

        if(rider == null){
            TheCasketOfReveriesMod.LOGGER.info("sword entity "+ getId() + " doesn't have rider "+level());
            discard();
            return;
        }

        //围绕rider旋转
        Vec3 center = rider.getPosition(0.5f);
        Vec3 now = center.add(getOffset());
        double radians = tickCount * 0.1;
        double rotatedX = center.x + (float) (Math.cos(radians) * (now.x - center.x) - Math.sin(radians) * (now.z - center.z));
        double rotatedZ = center.z + (float) (Math.sin(radians) * (now.x - center.x) + Math.cos(radians) * (now.z - center.z));
        setPos(new Vec3(rotatedX, now.y+Math.sin(radians)*0.3, rotatedZ));

        TCRPlayer tcrPlayer = rider.getCapability(TCRCapabilityProvider.TCR_PLAYER).orElse(new TCRPlayer());
        if(this.tickCount > 200){
            if(!level().isClientSide){
                tcrPlayer.getSwordScreensID().remove(getId());
            }
            tcrPlayer.setSwordScreenEntityCount(tcrPlayer.getSwordScreenEntityCount() - 1);
            discard();
        }

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
}
