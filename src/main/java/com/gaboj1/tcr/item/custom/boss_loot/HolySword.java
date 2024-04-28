package com.gaboj1.tcr.item.custom.boss_loot;

import com.gaboj1.tcr.effect.TCREffects;
import com.gaboj1.tcr.item.renderer.HolySwordRenderer;
import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
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
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
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
    private boolean isFlying;
    //最长记录向量的时间
    private static final int maxTick = 10;
    AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);
    public HolySword() {
        super(new Item.Properties().stacksTo(1).rarity(Rarity.EPIC).defaultDurability(1428));
    }

    public boolean isFlying() {
        return isFlying;
    }

    /**
     * 右键实现飞行模式的开关
     * 关闭时获得短暂的缓降效果。
     */
    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        if(!level.isClientSide){
            isFlying = !isFlying;
            if(!isFlying){
                player.addEffect(new MobEffectInstance(MobEffects.SLOW_FALLING, 10, 1, false, true));
            }
        }
        return super.use(level, player, hand);
    }

    /**
     * 如果在飞行模式下，直接向朝向加速。
     */
    @Override
    public void inventoryTick(ItemStack itemStack, Level level, Entity entity, int slotId, boolean isSelected) {
//        if(!(slotId == EquipmentSlot.FEET.ordinal())) {
//            return;
//        }
        if(isFlying && entity instanceof Player player){

//            Vec3 d = player.getDeltaMovement();
//            player.addEffect(new MobEffectInstance(TCREffects.FLY_SPEED.get(), 10, 1, false, true));
//            if(player.isShiftKeyDown() && !player.onGround()){
//                player.setDeltaMovement(d.x(), Math.max(d.y() - 0.1, -0.3), d.z());
//            } else {
////                double pX,pZ;
////                //取最大的为比例
////                if(Math.abs(d.x)>Math.abs(d.z)){
////                    pX = d.x > 0 ? 0.5 : -0.5;
////                    pZ = pX * (d.z / d.x);
////                }else {
////                    pZ = d.z > 0 ? 0.5 : -0.5;
////                    pX = pZ * (d.x / d.z);
////                }
////                player.setDeltaMovement(pX, Math.min(d.y() + 0.1, 0.3), pZ);
//
//                player.setDeltaMovement(d.x, Math.min(d.y() + 0.1, 0.3), d.z);
//            }

            //还是直接读这个省事，鼠标控制不比方向键舒服多了
//            player.setDeltaMovement(player.getViewVector(0));
            if(getViewVec(itemStack).length() != 0){
                player.setDeltaMovement(getViewVec(itemStack));//add(new Vec3(1, 0, 0)
            }
            updateViewVec(itemStack, player.getViewVector(0));
        }
        super.inventoryTick(itemStack, level, entity, slotId, isSelected);
    }

    /**
     * 获取前n个tick前的方向向量
     * @param sword
     * @param tick
     * @return
     */
    public static Vec3 getViewVec(ItemStack sword, int tick){
        checkOrCreateTag(sword);
        return getQueue(sword).toArray(new Vec3[maxTick-tick])[maxTick-tick];
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
//        pTooltipComponents.add(Component.translatable(this.getDescriptionId()+".usage3"));
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {
            controllers.add(new AnimationController<>(this, "Controller",
                    0, this::predicate));

    }

    private PlayState predicate(AnimationState<HolySword> holySwordAnimationState) {
        if(isFlying){
            holySwordAnimationState.getController().setAnimation(RawAnimation.begin().then("fly", Animation.LoopType.LOOP));
        }else {
            holySwordAnimationState.getController().setAnimation(RawAnimation.begin().then("idle", Animation.LoopType.LOOP));
        }
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
