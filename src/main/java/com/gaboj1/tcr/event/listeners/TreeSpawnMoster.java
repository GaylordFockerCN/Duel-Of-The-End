package com.gaboj1.tcr.event.listeners;

import com.gaboj1.tcr.TheCasketOfReveriesMod;
import com.gaboj1.tcr.block.custom.DenseForestTreeBlock;
import com.gaboj1.tcr.datagen.TCRAdvancementData;
import com.gaboj1.tcr.entity.custom.tree_monsters.SmallTreeMonsterEntity;
import com.gaboj1.tcr.entity.TCRModEntities;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.event.level.BlockEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;

import java.util.Random;

@Mod.EventBusSubscriber(modid = TheCasketOfReveriesMod.MOD_ID)
public class TreeSpawnMoster {
    @SubscribeEvent
    public static void spawnTreeMonsterWhen(BlockEvent.BreakEvent event) {
        Block block = event.getState().getBlock();
        if (!(block instanceof DenseForestTreeBlock)) {
            return;
        }
        LevelAccessor level = event.getLevel();
        Player player = event.getPlayer();
        if (level instanceof ServerLevel serverLevel && player instanceof ServerPlayer serverPlayer && 1 == new Random().nextInt(10)) {// 1/10概率

            //生成树怪并获得成就
            SmallTreeMonsterEntity entity = TCRModEntities.SMALL_TREE_MONSTER.get().spawn(serverLevel, event.getPos(), MobSpawnType.NATURAL);
            entity.hurt(((ServerLevel) level).damageSources().playerAttack(player), 1f);
            entity.setTarget(player);
            TCRAdvancementData.getAdvancement("wow", serverPlayer);
        }
    }
}