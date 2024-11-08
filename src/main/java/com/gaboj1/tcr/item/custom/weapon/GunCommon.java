
package com.gaboj1.tcr.item.custom.weapon;

import com.gaboj1.tcr.datagen.TCRAdvancementData;
import com.gaboj1.tcr.entity.TCREntities;
import com.gaboj1.tcr.event.PlayerModelEvent;
import com.gaboj1.tcr.item.TCRItems;
import com.gaboj1.tcr.client.keymapping.KeyMappings;
import com.gaboj1.tcr.entity.custom.projectile.BulletEntity;
import com.gaboj1.tcr.client.TCRSounds;
import com.gaboj1.tcr.item.custom.PoseItem;
import com.gaboj1.tcr.item.renderer.GunItemRenderer;
import com.gaboj1.tcr.util.ItemUtil;
import net.minecraft.client.model.PlayerModel;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.*;
import net.minecraft.world.level.LevelAccessor;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.registries.RegistryObject;

import net.minecraftforge.client.extensions.common.IClientItemExtensions;

import net.minecraft.world.level.Level;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.InteractionHand;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import org.jetbrains.annotations.NotNull;
import software.bernie.geckolib.animatable.GeoItem;
import software.bernie.geckolib.animatable.SingletonGeoAnimatable;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animation.AnimatableManager;
import software.bernie.geckolib.core.animation.AnimationController;
import software.bernie.geckolib.core.animation.RawAnimation;
import software.bernie.geckolib.core.object.PlayState;
import software.bernie.geckolib.util.GeckoLibUtil;

import java.util.List;
import java.util.function.Consumer;

import static com.gaboj1.tcr.item.TCRItems.AMMO;

/**
 * 为了子沙鹰类做准备，省的写很多重复的代码
 */

public class GunCommon extends Item implements GeoItem, PoseItem {
    private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);
    public boolean isReloading = false;

    public static final int RELOAD_TIME = 2000;

    protected float fireDamage = (float) 0.45;;//伤害值

    protected int coolDownTick = 20;

    protected float power = 15;//初速度

    public static final int NUM_AMMO_ITEMS_IN_GUN = 1;

    public final static int MAX_AMMO = 7;

    protected RegistryObject<Item> ammoType = AMMO;;

    public GunCommon() {
        super(new Item.Properties().stacksTo(1).fireResistant().rarity(Rarity.RARE).defaultDurability(MAX_AMMO));//引入弹匣了再把这个删了
        SingletonGeoAnimatable.registerSyncedAnimatable(this);
    }

    @Override
    public void initializeClient(Consumer<IClientItemExtensions> consumer) {
        consumer.accept(new IClientItemExtensions() {
            private GunItemRenderer renderer;
            @Override
            public BlockEntityWithoutLevelRenderer getCustomRenderer() {
                if(this.renderer == null){
                    renderer = new GunItemRenderer();
                }
                return this.renderer;
            }
        });
    }

    public void fireAnim(Level level, Player player, ItemStack stack){
        if (level instanceof ServerLevel serverLevel){
            triggerAnim(player, GeoItem.getOrAssignId(stack, serverLevel), "Fire", "fire");
        }

    }

    public void reloadAnim(Level level, Player player, ItemStack stack){
        if (level instanceof ServerLevel serverLevel)
            triggerAnim(player, GeoItem.getOrAssignId(stack, serverLevel), "Reload", "reload");
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar data) {
        data.add(new AnimationController<>(this, "Fire", 0, state -> PlayState.STOP)
                .triggerableAnim("fire", RawAnimation.begin().thenPlay("fire")));
        data.add(new AnimationController<>(this, "Reload", 0, state -> PlayState.STOP)
                .triggerableAnim("reload", RawAnimation.begin().thenPlay("reload")));

    }

    @Override
    public void verifyTagAfterLoad(CompoundTag p_150898_) {
        super.verifyTagAfterLoad(p_150898_);
        isReloading = false;//修复莫名换弹不了的bug
    }

    @Override
    public void onCraftedBy(ItemStack stack, Level p_41448_, Player p_41449_) {
        super.onCraftedBy(stack, p_41448_, p_41449_);
        ItemStack bullet = stack.copy();
        bullet.setCount(1);
        bullet.setDamageValue(bullet.getMaxDamage());
        setBulletItemStack(stack,bullet,0);//初始弹药应该为零。。
        //stack.setDamageValue(MAX_AMMO);
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return this.cache;
    }

    @Override
    public @NotNull InteractionResultHolder<ItemStack> use(@NotNull Level world, Player player, @NotNull InteractionHand hand) {
        double x = player.getX();
        double y = player.getY();
        double z = player.getZ();
        ItemStack handItemStake = (hand == InteractionHand.MAIN_HAND?player.getMainHandItem():player.getOffhandItem());
        if(handItemStake.getItem() instanceof GunCommon handItem){
            boolean isCooldown;
            isCooldown = player.getCooldowns().isOnCooldown(handItem);
            ItemStack bulletStack = GunCommon.getBulletItemStack(handItemStake, 0);
            if (!handItem.isReloading && !bulletStack.isEmpty()&&bulletStack.getDamageValue() < bulletStack.getMaxDamage() &&!isCooldown) {

                if (!player.isCreative()){
                    final ItemStack bullet = bulletStack;
                    final Integer bulletID1 = 0;
                    bullet.setDamageValue(bullet.getDamageValue() + 1);
                    //Update the stack in the gun
                    GunCommon.setBulletItemStack(handItemStake, bullet, bulletID1);
                }

                if (world instanceof ServerLevel projectileLevel) {
                    Projectile _entityToSpawn =	new Object() {
                        public Projectile getArrow(Level level, Entity shooter, float damage, int knockBack, byte piercing) {
                            AbstractArrow entityToSpawn = new BulletEntity(TCREntities.DESERT_EAGLE_BULLET.get(), level);
                            entityToSpawn.setOwner(shooter);
                            entityToSpawn.setNoGravity(true);
                            entityToSpawn.setBaseDamage(damage);
                            //双持伤害翻倍。不发射两发是因为有霸体时间..
                            if(player.getItemInHand((hand == InteractionHand.MAIN_HAND?InteractionHand.OFF_HAND:InteractionHand.MAIN_HAND)).getItem() instanceof GunCommon){
                                entityToSpawn.setBaseDamage(damage*2);
                                TCRAdvancementData.getAdvancement("can_double_hold",(ServerPlayer) player);

                            }
                            entityToSpawn.setKnockback(knockBack);
                            entityToSpawn.setSilent(true);
                            entityToSpawn.setPierceLevel(piercing);
                            entityToSpawn.pickup = AbstractArrow.Pickup.CREATIVE_ONLY;
                            return entityToSpawn;
                        }
                    }.getArrow(projectileLevel, player, handItem.getFireDamage(), 1, (byte) 5);
                    _entityToSpawn.setPos(x, player.getEyeY() - (double)0.15F, z);
                    _entityToSpawn.shoot(player.getViewVector(1).x, player.getViewVector(1).y, player.getViewVector(1).z, handItem.getPower(), 0);
                    projectileLevel.addFreshEntity(_entityToSpawn);
                }

                if (world instanceof ServerLevel serverLevel) {
                    handItem.fireAnim(serverLevel, player, hand == InteractionHand.MAIN_HAND?player.getMainHandItem():player.getOffhandItem());
                }

                player.getCooldowns().addCooldown(handItem, handItem.getCoolDownTick());

                if (!world.isClientSide()) {
                    //播放音效
                    world.playSound(null, x,y,z, TCRSounds.DESERT_EAGLE_FIRE.get(), SoundSource.BLOCKS, 1, 1);
                } else {
                    //实现抖动
                    double[] recoilTimer = {0}; // 后坐力计时器
                    double totalTime = 100;
                    int sleepTime = 2;
                    double recoilDuration = totalTime / sleepTime; // 后坐力持续时间
                    float speed = (float) ((Math.random() * 2) - 1) / 10;
                    Runnable recoilRunnable = () -> {
                        //开始抖动(简单匀速运动，不够真实。。)
                        while (recoilTimer[0] < recoilDuration) {
                            // 逐渐调整玩家的视角
                            float newPitch = player.getXRot() - (float) 0.2;//实时获取，以防鼠标冲突
                            float newYaw = player.getYRot() - speed;
                            player.setYRot(newYaw);
                            player.setXRot(newPitch);
                            player.yRotO = player.getYRot();
                            player.xRotO = player.getXRot();
                            recoilTimer[0]++; // 计时器递增
                            try {
                                Thread.sleep(sleepTime);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                        //归位
                        while (recoilTimer[0] > 0) {
                            float newPitch = player.getXRot() + (float) 0.2;
                            float newYaw = player.getYRot() + speed;
                            player.setXRot(newPitch);
                            player.setYRot(newYaw);
                            player.xRotO = player.getXRot();
                            player.yRotO = player.getYRot();
                            recoilTimer[0]--; // 计时器递增
                            try {
                                Thread.sleep(sleepTime);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                    };
                    Thread recoilThread = new Thread(recoilRunnable);
                    recoilThread.start();
                }

                //显示当前左右手的弹药数
                showAmmoCount(world, hand == InteractionHand.MAIN_HAND,player,handItemStake);

            } else if (hand == InteractionHand.MAIN_HAND && player.getOffhandItem().getItem() instanceof GunCommon) {//如果副手有枪就使用副手试试
                player.getOffhandItem().getItem().use(world, player,InteractionHand.OFF_HAND);
            } else {//都没有就需要换弹了
                if(player.level().isClientSide){
                    player.displayClientMessage(Component.translatable(TCRItems.GUN_COMMON.get().getDescriptionId()+".reloadbutton", KeyMappings.RELOAD.getKeyName()),true);
                }
            }
        }
        return InteractionResultHolder.pass(player.getItemInHand(hand));
    }

    /**
     * 显示当前双手子弹数量
     */
    public static void showAmmoCount(Level world,boolean isMainHand,Player player,ItemStack handItemStake){
        if(!(handItemStake.getItem() instanceof GunCommon)){
            return;
        }
        if (world instanceof ServerLevel){

            ItemStack anotherHandItemStake = player.getItemInHand(isMainHand?InteractionHand.OFF_HAND:InteractionHand.MAIN_HAND);
            MutableComponent content = (isMainHand ? Component.translatable(TCRItems.GUN_COMMON.get().getDescriptionId()+".main_hand_ammo") : Component.literal(" ").append(Component.translatable(TCRItems.GUN_COMMON.get().getDescriptionId()+".off_hand_ammo")));
            content.append(Component.literal(getBulletCount(handItemStake)+ "/" + GunCommon.MAX_AMMO));

            if(anotherHandItemStake.getItem() instanceof GunCommon){
                content = Component.translatable(TCRItems.GUN_COMMON.get().getDescriptionId()+".off_hand_ammo").append(
                        Component.literal((isMainHand ? getBulletCount(anotherHandItemStake) : getBulletCount(handItemStake)) + "/"+ GunCommon.MAX_AMMO + "      ")
                                .append(Component.translatable(TCRItems.GUN_COMMON.get().getDescriptionId()+".main_hand_ammo")
                                        .append((isMainHand ? getBulletCount(handItemStake) : getBulletCount(anotherHandItemStake) )+ "/"+ GunCommon.MAX_AMMO)));
            }
            player.displayClientMessage(content,true);
        }
    }

    @Override
    public void appendHoverText(@NotNull ItemStack itemstack, Level world, List<Component> list, @NotNull TooltipFlag flag) {
        ItemStack bulletItemStack = getBulletItemStack(itemstack,0);
        int ammo = bulletItemStack.getMaxDamage()-bulletItemStack.getDamageValue();
        list.add(Component.translatable(TCRItems.GUN_COMMON.get().getDescriptionId()+".ammo_count").append(ammo+"/"+MAX_AMMO));
        list.add(Component.translatable(TCRItems.GUN_COMMON.get().getDescriptionId()+".ammo_cooldown").append(String.format("%.2fs", coolDownTick*0.05)));
    }


    public float getFireDamage(){
        return fireDamage;
    }

    public int getCoolDownTick(){
        return coolDownTick;
    }

    public float getPower(){
        return power;
    }

    public RegistryObject<Item> getAmmoType(){
        return ammoType;
    }

    /**
     * 复合NBT，实现换弹的同时不会让物品播放切换动画，从Flan枪械那里改的
    * */
    public static ItemStack getBulletItemStack(ItemStack gun, int id) {
        // 如果枪械没有 NBT 标签，给它添加一个
        if (!gun.hasTag()) {
            gun.setTag(new CompoundTag());
            return ItemStack.EMPTY;
        }
        // 如果枪械的 NBT 标签中没有 "ammo" 标签，给它添加一个
        if (!gun.getTag().contains("ammo")) {
            ListTag ammoTagsList = new ListTag();
            for (int i = 0; i < NUM_AMMO_ITEMS_IN_GUN; i++) {
                ammoTagsList.add(new CompoundTag());
            }
            gun.getTag().put("ammo", ammoTagsList);
            return ItemStack.EMPTY;
        }
        // 获取子弹的 NBT 标签列表
        ListTag ammoTagsList = gun.getTag().getList("ammo", Tag.TAG_COMPOUND);
        // 获取特定位置的子弹的 NBT 标签
        CompoundTag ammoTags = ammoTagsList.getCompound(id);
        return ItemStack.of(ammoTags);
    }

    public static void setBulletItemStack(ItemStack gun, ItemStack bullet, int id) {
        // 如果枪械的 NBT 标签中没有 "ammo" 标签，给它添加一个
        if (!gun.getOrCreateTag().contains("ammo")) {
            ListTag ammoTagsList = new ListTag();
            for (int i = 0; i < NUM_AMMO_ITEMS_IN_GUN; i++) {
                ammoTagsList.add(new CompoundTag());
            }
            gun.getTag().put("ammo", ammoTagsList);
        }
        // 获取子弹的 NBT 标签列表
        ListTag ammoTagsList = gun.getTag().getList("ammo", Tag.TAG_COMPOUND);
        // 获取特定位置的子弹的 NBT 标签
        CompoundTag ammoTags = ammoTagsList.getCompound(id);
        // 如果子弹为空，将对应位置的 NBT 标签设为 null
        if (bullet.isEmpty()) {
            ammoTags = new CompoundTag();
        } else {
            // 将子弹的 NBT 标签应用到特定位置
            bullet.save(ammoTags);
        }

    }

    public static void reload(Player player){
        if (player == null)
            return;
        Level world = player.level();
        //分别判断左右手是否满弹药并做出换弹处理（为什么当时打算搞双持啊啊啊）
        ItemStack mainHandItem = player.getMainHandItem();
        ItemStack offhandItem = player.getOffhandItem();
        if(mainHandItem.getItem() instanceof GunCommon item /*mainHandItem.getOrCreateTag().getBoolean(FatherDesertEagleItem.RELOADING_DONE_TAG)*/){
            if(isFull(mainHandItem)){
                player.displayClientMessage(Component.translatable(TCRItems.GUN_COMMON.get().getDescriptionId()+".main_ammo_full"),true);
            } else if (item.isReloading) {
                player.displayClientMessage(Component.translatable(TCRItems.GUN_COMMON.get().getDescriptionId()+".reloading"),true);
            }else doReload(player.getMainHandItem(),player,world,item.getAmmoType().get(),true);
        }
        if(offhandItem.getItem() instanceof GunCommon item && /*offhandItem.getOrCreateTag().getBoolean(FatherDesertEagleItem.RELOADING_DONE_TAG)*/!item.isReloading  && !isFull(offhandItem)){
            if(isFull(offhandItem)){
                player.displayClientMessage(Component.translatable(TCRItems.GUN_COMMON.get().getDescriptionId()+".off_ammo_full"),true);
            } else if (item.isReloading) {
                player.displayClientMessage(Component.translatable(TCRItems.GUN_COMMON.get().getDescriptionId()+".reloading"),true);
            }else doReload(player.getOffhandItem(),player,world,item.getAmmoType().get(),false);
        }

    }

    private static void doReload(ItemStack handItemStake, Player player, LevelAccessor world, Item ammo, boolean isMainHand) {
        double x = player.getX();
        double y = player.getY();
        double z = player.getZ();

        //延迟实现换弹逻辑，等动画和音效放完
        new Thread(() -> {//防止sleep卡死

            try {
                int need = 1;
                ItemStack bullet = GunCommon.getBulletItemStack(handItemStake,0);
                if(bullet.isEmpty()){
                    need = GunCommon.MAX_AMMO;
                } else {
                    need = bullet.getDamageValue();
                }
                int total;
                //升级版的枪直接填满七发
                if(handItemStake.getItem() instanceof GunPlus){
                    total = ItemUtil.searchAndConsumeItem(player,ammo,need);
                } else {
                    total = ItemUtil.searchAndConsumeItem(player,ammo,1);
                }
                if(total>0){
                    GunCommon handItem = (GunCommon) handItemStake.getItem();
                    handItem.isReloading = true;//限制同时换弹
                    //handItemStake.getOrCreateTag().putBoolean(FatherDesertEagleItem.RELOADING_DONE_TAG,false);
                    //播放动画
                    if (world instanceof ServerLevel serverLevel) {
                        //防止开火时换弹
                        if(player.getCooldowns().isOnCooldown(handItemStake.getItem()))return;
                        //播放动画
                        ((GunCommon)handItemStake.getItem()).reloadAnim(serverLevel, player, handItemStake);
                        //播放音效
                        //serverLevel.playSound(player, x,y,z, HDLModSounds.DESERTEAGLECRCRELOAD.get(), SoundSource.HOSTILE, 1, 1);
                        serverLevel.playSound(null, x, y, z, TCRSounds.DESERT_EAGLE_RELOAD.get(), SoundSource.BLOCKS, 1, 1);
                    }

                    Thread.sleep(GunCommon.RELOAD_TIME);
                    ItemStack newBullet = handItemStake.copy();
                    newBullet.setCount(1);
                    newBullet.setDamageValue(need - total);
                    GunCommon.setBulletItemStack(handItemStake,newBullet,0);
                    handItem.isReloading = false;
                    //显示子弹数量信息
                    showAmmoCount((Level) world, isMainHand, player, handItemStake);
                }else{
                    player.displayClientMessage(Component.translatable(TCRItems.GUN_COMMON.get().getDescriptionId()+".no_ammo"),true);
                }
            } catch (Exception e) {//Exception高发地，实在不会搞
                throw new RuntimeException(e);
            }

        }).start();
    }

    private static int getBulletCount(ItemStack stack){
        if(stack.getItem() instanceof GunCommon){
            ItemStack bullet = GunCommon.getBulletItemStack(stack,0);
            return bullet.getMaxDamage()-bullet.getDamageValue();
        }
        return 0;
    }

    private static boolean isFull(ItemStack gun){
        ItemStack bullet = GunCommon.getBulletItemStack(gun,0);
        if(bullet.isEmpty())return false;
        return bullet.getDamageValue()==0;
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void adjustInMainHand(PlayerModelEvent.SetupAngles.Post event, PlayerModel<?> model, boolean right) {
        if(right){
            model.rightArm.xRot = model.head.xRot + (float) Math.toRadians(-90);
            model.rightArm.zRot = model.head.zRot;
            model.rightArm.yRot = model.head.yRot;
        }else {
            model.leftArm.xRot = model.head.xRot + (float) Math.toRadians(-90);
            model.leftArm.zRot = model.head.zRot;
            model.leftArm.yRot = model.head.yRot;
        }
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void adjustInOffHand(PlayerModelEvent.SetupAngles.Post event, PlayerModel<?> model, boolean right) {
        if(right){
            model.leftArm.xRot = model.head.xRot + (float) Math.toRadians(-90);
            model.leftArm.zRot = model.head.zRot;
            model.leftArm.yRot = model.head.yRot;
        }else {
            model.rightArm.xRot = model.head.xRot + (float) Math.toRadians(-90);
            model.rightArm.zRot = model.head.zRot;
            model.rightArm.yRot = model.head.yRot;
        }
    }
}
