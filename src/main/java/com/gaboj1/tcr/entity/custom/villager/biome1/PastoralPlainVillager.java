package com.gaboj1.tcr.entity.custom.villager.biome1;

import com.gaboj1.tcr.entity.custom.villager.TCRVillager;
import com.gaboj1.tcr.init.TCRModEntities;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.Random;

public class PastoralPlainVillager extends TCRVillager {

    public PastoralPlainVillager(EntityType<? extends TCRVillager> pEntityType, Level pLevel) {
        super(pEntityType, pLevel, new Random().nextInt(MAX_TYPES+MAX_FEMALE_TYPES)-MAX_FEMALE_TYPES);
        whatCanISay = 5;
    }

    public static AttributeSupplier setAttributes() {//生物属性
        return Animal.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 20.0D)//最大血量
                .add(Attributes.ATTACK_DAMAGE, 6.0f)//单次攻击伤害
                .add(Attributes.ATTACK_SPEED, 1.0f)//攻速
                .add(Attributes.MOVEMENT_SPEED, 0.4f)//移速
                .build();
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void talk(Player player){
        talk(player, Component.translatable(TCRModEntities.PASTORAL_PLAIN_VILLAGER.get().getDescriptionId()+".chat"+(r.nextInt(whatCanISay))));
    }
    @Override
    @OnlyIn(Dist.CLIENT)
    public void talkFuck(Player player){
        talk(player, Component.translatable(TCRModEntities.PASTORAL_PLAIN_VILLAGER.get().getDescriptionId()+".fuck_chat"+(r.nextInt(whatCanISay))));
    }

}
