package com.gaboj1.tcr.item.custom.boss_loot;

import com.gaboj1.tcr.capability.TCRCapabilityProvider;
import com.gaboj1.tcr.entity.TCRModEntities;
import com.gaboj1.tcr.entity.custom.sword.RainScreenSwordEntity;
import com.gaboj1.tcr.network.PacketRelay;
import com.gaboj1.tcr.network.TCRPacketHandler;
import com.gaboj1.tcr.network.packet.server.SyncSwordOwnerPacket;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

import java.util.Set;

/**
 * 右键召唤盾
 */
public class GuardSword extends MagicWeapon{

    public static final int CD = 400;

    public GuardSword(Properties pProperties) {
        super(pProperties);
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
}
