package com.gaboj1.tcr.item.custom.boss_loot;

import com.gaboj1.tcr.TheCasketOfReveriesMod;
import com.gaboj1.tcr.capability.TCRCapabilityProvider;
import com.gaboj1.tcr.entity.TCRModEntities;
import com.gaboj1.tcr.entity.custom.sword.RainScreenSwordEntity;
import com.gaboj1.tcr.network.PacketRelay;
import com.gaboj1.tcr.network.TCRPacketHandler;
import com.gaboj1.tcr.network.packet.clientbound.SyncSwordOwnerPacket;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.Set;

/**
 * 右键召唤盾
 */
@Mod.EventBusSubscriber(modid = TheCasketOfReveriesMod.MOD_ID)
public class GuardSword extends MagicWeapon{

    public static final int CD = 400;

    public GuardSword(Properties pProperties) {
        super(pProperties, 5.0);
    }

    /**
     * 召唤盾
     */
    @Override
    public InteractionResultHolder<ItemStack> use(Level p_41432_, Player p_41433_, InteractionHand p_41434_) {

        if(p_41433_ instanceof ServerPlayer player){
            player.getCapability(TCRCapabilityProvider.TCR_PLAYER).ifPresent(tcrPlayer -> {

                //多个保险，以防重启的时候剑帘不存在。其实可以设置不读取即可（
                Set<Integer> swordID = tcrPlayer.getSwordScreensID();

                for(int i = 0; i < 4; i++){
                    RainScreenSwordEntity sword = TCRModEntities.RAIN_SCREEN_SWORD.get().spawn(player.serverLevel(), player.getOnPos(), MobSpawnType.MOB_SUMMONED);
                    if(sword == null){
                        return;
                    }
                    sword.setRider(player);
                    sword.setItemStack(player.getMainHandItem());
                    PacketRelay.sendToAll(TCRPacketHandler.INSTANCE, new SyncSwordOwnerPacket(player.getId(), sword.getId()));
                    sword.setSwordID(i);
                    sword.setPos(player.getPosition(0.5f).add(sword.getOffset()));
                    tcrPlayer.setSwordScreenEntityCount(tcrPlayer.getSwordScreenEntityCount()+1);
                    swordID.add(sword.getId());
                }
            });

            player.getCooldowns().addCooldown(player.getItemInHand(p_41434_).getItem(), CD);
        }

        return InteractionResultHolder.sidedSuccess(p_41433_.getItemInHand(p_41434_), p_41432_.isClientSide);
    }

    /**
     * 实现格挡伤害
     */
    @SubscribeEvent
    public static void onPlayerHurt(LivingHurtEvent event){
        LivingEntity entity = event.getEntity();
        if(entity instanceof ServerPlayer serverPlayer && !event.getSource().is(DamageTypeTags.IS_FALL)){
            serverPlayer.getCapability(TCRCapabilityProvider.TCR_PLAYER).ifPresent(ssPlayer -> {
                if(ssPlayer.getSwordScreenEntityCount() > 0 ){
                    Set<Integer> swordID = ssPlayer.getSwordScreensID();
                    //剑挡伤害并回血（顺序很重要，需要先销毁剑再反弹，省得获取SourceEntity为null的时候被打断）
                    for(int i : swordID){
                        Entity sword = serverPlayer.serverLevel().getEntity(i);
                        if(sword != null){
                            serverPlayer.heal(2f);
                            serverPlayer.serverLevel().addParticle(ParticleTypes.SPIT, serverPlayer.getX(), serverPlayer.getY(), serverPlayer.getZ(),0,0,0.1);
                            serverPlayer.serverLevel().playSound(null, serverPlayer.getX(), serverPlayer.getY(), serverPlayer.getZ(), SoundEvents.SHIELD_BREAK, SoundSource.BLOCKS, 1, 1);
                            ssPlayer.setSwordScreenEntityCount(ssPlayer.getSwordScreenEntityCount()-1);
                            sword.discard();
                            swordID.remove(i);
                            ssPlayer.setSwordID(swordID);
                            event.setCanceled(true);
                            break;
                        }
                    }
                }
            });
        }

    }

}
