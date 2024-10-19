package com.gaboj1.tcr.event.listeners;

import com.gaboj1.tcr.TheCasketOfReveriesMod;
import com.gaboj1.tcr.block.TCRBlocks;
import com.gaboj1.tcr.block.custom.DenseForestTreeBlock;
import com.gaboj1.tcr.datagen.TCRAdvancementData;
import com.gaboj1.tcr.entity.TCREntities;
import com.gaboj1.tcr.entity.custom.biome1.SmallTreeMonsterEntity;
import com.gaboj1.tcr.entity.custom.biome1.UnknownEntity;
import com.gaboj1.tcr.util.SaveUtil;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.event.level.BlockEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.Random;

@Mod.EventBusSubscriber(modid = TheCasketOfReveriesMod.MOD_ID)
public class TreeSpawnMonster {
    @SubscribeEvent
    public static void spawnTreeMonsterWhen(BlockEvent.BreakEvent event) {
        if(event.getLevel() instanceof ServerLevel serverLevel){
            Block block = event.getState().getBlock();
            //挖密林木概率出小树怪
            if (block instanceof DenseForestTreeBlock) {
                LevelAccessor level = event.getLevel();
                Player player = event.getPlayer();
                if (player instanceof ServerPlayer serverPlayer && 1 == new Random().nextInt(10)) {// 1/10概率
                    //生成树怪并获得成就
                    SmallTreeMonsterEntity entity = TCREntities.SMALL_TREE_MONSTER.get().spawn(serverLevel, event.getPos(), MobSpawnType.NATURAL);
                    assert entity != null;
                    entity.hurt(((ServerLevel) level).damageSources().playerAttack(player), 1f);
                    entity.setTarget(player);
                    TCRAdvancementData.getAdvancement("wow", serverPlayer);
                }
            }
            //第一群系支线
            if(SaveUtil.biome1.smithTalked && block.defaultBlockState().is(TCRBlocks.BLUE_MUSHROOM.get()) && !SaveUtil.biome1.monsterSummoned){
                UnknownEntity unknownEntity = TCREntities.UNKNOWN.get().spawn(serverLevel, event.getPos().above(5), MobSpawnType.NATURAL);
                assert unknownEntity != null;
                unknownEntity.setCanPurify(true);
                serverLevel.addFreshEntity(unknownEntity);
                SaveUtil.biome1.monsterSummoned = true;
            }
        }
    }
}