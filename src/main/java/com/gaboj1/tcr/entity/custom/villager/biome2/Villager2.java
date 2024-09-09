package com.gaboj1.tcr.entity.custom.villager.biome2;

import com.gaboj1.tcr.entity.TCRModEntities;
import com.gaboj1.tcr.entity.custom.villager.TCRVillager;
import com.gaboj1.tcr.util.SaveUtil;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

/**
 * 牧歌原野村民。继承主要是为了更好分配id
 * @author LZY
 */
public class Villager2 extends TCRVillager {

    public Villager2(EntityType<? extends TCRVillager> pEntityType, Level pLevel) {
        super(pEntityType, pLevel, TCRVillager.RANDOM_SKIN);
    }

    @Override
    public int getMaleTypeCnt() {
        return 2;
    }
    @Override
    public int getFemaleTypeCnt() {
        return 2;
    }

    @Override
    public String getResourceName() {
        return "villager2_"+getSkinID();
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void talk(Player player, boolean isFWord){
        if(SaveUtil.biome2.choice != 1){
            talk(player, Component.translatable(TCRModEntities.VILLAGER2.get().getDescriptionId()+".chat"+(r.nextInt(whatCanISay))));
        }else {
            talk(player, Component.translatable(TCRModEntities.VILLAGER2.get().getDescriptionId()+".fuck_chat"+(r.nextInt(whatCanISay))));
        }
    }

    @Override
    public boolean hurt(DamageSource source, float v) {
        if(source.getEntity() instanceof ServerPlayer player && level().isClientSide && SaveUtil.biome2.choice == 2){
            player.displayClientMessage(Component.literal("info.the_casket_of_reveries.alreadyAddWhite"),true);
            return false;
        }
        return super.hurt(source, v);
    }

    /**
     * 如果是玩家杀的而且非boss阵营则不能杀
     */
    @Override
    public void die(DamageSource damageSource) {
        if(damageSource.getEntity() instanceof Player && SaveUtil.biome2.choice != 1) {
            setHealth(1);
            return;
        }
        super.die(damageSource);
    }
}
