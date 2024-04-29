package com.gaboj1.tcr.item.custom.boss_loot;

import com.gaboj1.tcr.item.renderer.HolySwordRenderer;
import com.gaboj1.tcr.network.PacketRelay;
import com.gaboj1.tcr.network.TCRPacketHandler;
import com.gaboj1.tcr.network.packet.client.UpdateFlySpeedPacket;
import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.client.extensions.common.IClientItemExtensions;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.animatable.GeoItem;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animation.*;
import software.bernie.geckolib.core.object.PlayState;
import software.bernie.geckolib.util.GeckoLibUtil;

import java.util.ArrayDeque;
import java.util.List;
import java.util.Queue;
import java.util.function.Consumer;

/**
 * 随着速度增大而加伤
 * 右键地面引雷
 * 可选附魔引雷
 * 右键御剑飞行
 * @author LZY
 */
public class HolySword extends MagicWeapon implements GeoItem {

    private final float damage = 5.0f;
    //最长记录向量的时间
    private static final int maxTick = 40;
    //最大灵力值
    private static final int maxSpiritValue = 10000;
    AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);
    public HolySword() {
        super(new Item.Properties().stacksTo(1).rarity(Rarity.EPIC).defaultDurability(1428));
    }

    public boolean isFlying(ItemStack sword) {
        return sword.getOrCreateTag().getBoolean("isFlying");
    }

    public void setFlying(ItemStack sword, boolean isFlying) {
        sword.getOrCreateTag().putBoolean("isFlying", isFlying);
    }

    public int getLeftTick(ItemStack sword) {
        return sword.getOrCreateTag().getInt("leftTick");
    }

    public void setLeftTick(ItemStack sword, int leftTick) {
        if(leftTick<0){
            return;
        }
        sword.getOrCreateTag().putInt("leftTick", leftTick);
    }

    public int getSpiritValue(ItemStack sword) {
        return sword.getOrCreateTag().getInt("spiritValue");
    }

    public void setSpiritValue(ItemStack sword, int spiritValue) {
        if(spiritValue<0 || spiritValue > maxSpiritValue){
            return;
        }
        sword.getOrCreateTag().putInt("spiritValue", spiritValue);
    }

    /**
     * 右键实现飞行模式的开关
     * 关闭时获得短暂的缓降效果。
     */
    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        if(!level.isClientSide){
            ItemStack sword = player.getItemInHand(hand);
            boolean isFlying = sword.getOrCreateTag().getBoolean("isFlying");
            isFlying = !isFlying;
            setFlying(sword, isFlying);
            if(!isFlying && getLeftTick(sword) == 0){
                player.addEffect(new MobEffectInstance(MobEffects.SLOW_FALLING, 10, 1, false, true));
                setLeftTick(sword, maxTick);
            }
        }
        return super.use(level, player, hand);
    }

    /**
     * 如果在飞行模式下，直接向朝向加速。
     * 空格加速shift减速
     */
    @Override
    public void inventoryTick(ItemStack itemStack, Level level, Entity entity, int slotId, boolean isSelected) {
//        if(!(slotId == EquipmentSlot.FEET.ordinal())) {
//            return;
//        }
        double flySpeedScale = itemStack.getOrCreateTag().getDouble("flySpeedScale");
        if(flySpeedScale == 0){
            flySpeedScale = 1;
        }
        if(entity instanceof Player player){
            //空格加速
            if(player instanceof LocalPlayer localPlayer){
                if(localPlayer.input.jumping){
                    PacketRelay.sendToServer(TCRPacketHandler.INSTANCE, new UpdateFlySpeedPacket(slotId, flySpeedScale+0.1));
                }
            }
            //再次获取一遍，等待客户端的修改。
            flySpeedScale = itemStack.getOrCreateTag().getDouble("flySpeedScale");
            //shift减速，不低于0.5倍
            if(player.isShiftKeyDown() && flySpeedScale > 0.5){
                flySpeedScale-=0.1;
                itemStack.getOrCreateTag().putDouble("flySpeedScale", flySpeedScale);
            }

            //往朝向加速
            if(isFlying(itemStack)){
                //获取10tick前的速度并且根据按键对其缩放。
                if(getViewVec(itemStack).length() != 0){
                    //灵力够才能起飞
                    int spiritValue = getSpiritValue(itemStack) - (int) (getViewVec(itemStack).length() * 10);
                    if(spiritValue > 0){
                        setSpiritValue(itemStack, spiritValue);
                        player.setDeltaMovement(getViewVec(itemStack, maxTick / 4).scale(flySpeedScale));
                    } else {
                        setFlying(itemStack,false);
                        setLeftTick(itemStack, maxTick);
                    }
                }
                //更新前几个tick的向量队列
                updateViewVec(itemStack, player.getViewVector(0));
            } else {
                //缓冲
                if(getLeftTick(itemStack) > 0){
                    int leftTick = getLeftTick(itemStack);
                    setLeftTick(itemStack,leftTick-1);
                    if(getViewVec(itemStack).length() != 0){
                        //速度越来越慢
                        player.setDeltaMovement(getViewVec(itemStack).lerp(Vec3.ZERO, (double) (maxTick-leftTick) /maxTick));
                    }
                    updateViewVec(itemStack, Vec3.ZERO);
                }
                //回复灵力
                setSpiritValue(itemStack, getSpiritValue(itemStack) + 10);
            }
        }

        super.inventoryTick(itemStack, level, entity, slotId, isSelected);
    }

    /**
     * 获取前n个tick前的方向向量
     */
    public static Vec3 getViewVec(ItemStack sword, int tickBefore){
        checkOrCreateTag(sword);
        return getQueue(sword).toArray(new Vec3[maxTick])[maxTick-tickBefore];
    }

    public static Vec3 getViewVec(ItemStack sword){
        return getQueue(sword).peek();
    }

    /**
     * 保存很多个tick前的方向向量，实现惯性效果
     * 通过队列来保存。
     * 并作插值，实现惯性漂移（太妙了）
     */
    public static void updateViewVec(ItemStack sword, Vec3 viewVec){
        checkOrCreateTag(sword);
        Queue<Vec3> tickValues = getQueue(sword);
        tickValues.add(viewVec);
        Vec3 old = tickValues.poll();
        Queue<Vec3> newTickValues = new ArrayDeque<>();
        for(double i = 1; i <= tickValues.size(); i++){
            Vec3 newVec3 = old.lerp(viewVec, i / tickValues.size());
            newTickValues.add(newVec3);
        }
        saveQueue(sword, newTickValues);
    }

    /**
     * 获取前几个tick内的方向向量队列
     */
    public static Queue<Vec3> getQueue(ItemStack sword){
        CompoundTag tag = checkOrCreateTag(sword);
        Queue<Vec3> tickValues = new ArrayDeque<>();
        for(int i = 0; i < maxTick; i++){
            CompoundTag tickVec = tag.getList("view_vec_queue", Tag.TAG_COMPOUND).getCompound(i);
            tickValues.add(new Vec3(tickVec.getDouble("x"),tickVec.getDouble("y"),tickVec.getDouble("z")));
        }
        return tickValues;
    }

    /**
     * 保存前几个tick内的方向向量队列
     */
    public static void saveQueue(ItemStack sword, Queue<Vec3> tickValues){
        CompoundTag tag = checkOrCreateTag(sword);
        for(int i = 0; i < maxTick; i++){
            CompoundTag tickVecTag = tag.getList("view_vec_queue", Tag.TAG_COMPOUND).getCompound(i);
            Vec3 tickVec = tickValues.remove();
            tickVecTag.putDouble("x", tickVec.x);
            tickVecTag.putDouble("y", tickVec.y);
            tickVecTag.putDouble("z", tickVec.z);
        }
    }

    /**
     * 检查是否为空标签，是则创建一个完备的给它。防止异常。
     */
    public static CompoundTag checkOrCreateTag(ItemStack sword){
        CompoundTag tag = sword.getOrCreateTag();
        if (!tag.contains("view_vec_queue")) {
            ListTag tickTagsList = new ListTag();
            for (int i = 0; i < maxTick; i++) {
                tickTagsList.add(new CompoundTag());
            }
            tag.put("view_vec_queue", tickTagsList);
        }
        return tag;
    }


    /**
     *平A伤害调整
     */
    @Override
    public Multimap<Attribute, AttributeModifier> getDefaultAttributeModifiers(EquipmentSlot equipmentSlot) {
        if (equipmentSlot == EquipmentSlot.MAINHAND || equipmentSlot == EquipmentSlot.OFFHAND) {
            ImmutableMultimap.Builder<Attribute, AttributeModifier> builder = ImmutableMultimap.builder();
            builder.putAll(super.getDefaultAttributeModifiers(equipmentSlot));
            builder.put(Attributes.ATTACK_DAMAGE, new AttributeModifier(BASE_ATTACK_DAMAGE_UUID, "Item modifier", damage, AttributeModifier.Operation.ADDITION));
            builder.put(Attributes.ATTACK_SPEED, new AttributeModifier(BASE_ATTACK_SPEED_UUID, "Item modifier", -2.4, AttributeModifier.Operation.ADDITION));

            return builder.build();
        }
        return super.getDefaultAttributeModifiers(equipmentSlot);
    }

    /**
     * 天下武功，唯快不破！
     */
    @Override
    public boolean hurtEnemy(ItemStack pStack, LivingEntity pTarget, LivingEntity pAttacker) {
        if(pAttacker instanceof ServerPlayer serverPlayer) {

//            float speed = serverPlayer.getSpeed();
            float speed = (float) serverPlayer.getDeltaMovement().distanceTo(Vec3.ZERO) * 2;
            System.out.println("speed:"+speed+" damage:"+speed*speed*damage);//Ek = mv²/2哈哈
            pTarget.setHealth(pTarget.getHealth()-speed*speed*damage);
//            pTarget.hurt(pAttacker.damageSources().playerAttack(serverPlayer),speed==0?damage:damage*speed*speed);
        }
        return true;
    }

    @Override
    public void appendHoverText(ItemStack pStack, @Nullable Level pLevel, List<Component> pTooltipComponents, TooltipFlag pIsAdvanced) {
        super.appendHoverText(pStack, pLevel, pTooltipComponents, pIsAdvanced);
        pTooltipComponents.add(Component.translatable(this.getDescriptionId()+".usage1"));
//        pTooltipComponents.add(Component.translatable(this.getDescriptionId()+".usage2"));
        pTooltipComponents.add(Component.translatable(this.getDescriptionId()+".usage3", pStack.getOrCreateTag().getInt("spiritValue")));
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {
            controllers.add(new AnimationController<>(this, "Controller",
                    0, this::predicate));

    }

    private PlayState predicate(AnimationState<HolySword> holySwordAnimationState) {
//        if(){
//            holySwordAnimationState.getController().setAnimation(RawAnimation.begin().then("fly", Animation.LoopType.LOOP));
//        }else {
            holySwordAnimationState.getController().setAnimation(RawAnimation.begin().then("idle", Animation.LoopType.LOOP));
//        }
        return PlayState.CONTINUE;
    }
    @Override
    public void initializeClient(Consumer<IClientItemExtensions> consumer) {
        consumer.accept(new IClientItemExtensions() {
            private HolySwordRenderer renderer;
            @Override
            public BlockEntityWithoutLevelRenderer getCustomRenderer() {
                if(this.renderer == null){
                    renderer = new HolySwordRenderer();
                }
                return this.renderer;
            }
        });
    }
    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return cache;
    }
}
