package com.gaboj1.tcr.listener;

import com.gaboj1.tcr.TheCasketOfReveriesMod;
import com.gaboj1.tcr.block.custom.DenseForestTreeBlock;
import com.gaboj1.tcr.init.TCRModEntities;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementProgress;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.event.level.BlockEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;

import java.util.Random;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class TreeSpawnMoster {

    static Random r = new Random();
    @SubscribeEvent
    public static void init(FMLCommonSetupEvent event) {
        new TreeSpawnMoster();
    }
    @Mod.EventBusSubscriber
    private static class ForgeBusEvents {

        @SubscribeEvent
        public static void spawnTreeMonsterWhen(BlockEvent.BreakEvent event) {
            Block block = event.getState().getBlock();
            if(!(block instanceof DenseForestTreeBlock)){
                return;
            }
            LevelAccessor level =  event.getLevel();
            Player player = event.getPlayer();
            if(level instanceof ServerLevel serverLevel && player instanceof ServerPlayer serverPlayer && 1 == r.nextInt(10)){// 1/10概率

                //生成树怪并获得成就
                TCRModEntities.SMALL_TREE_MONSTER.get().spawn(serverLevel,event.getPos(), MobSpawnType.NATURAL).hurt(((ServerLevel) level).damageSources().playerAttack(player),0.1f);//直接激怒哈哈 FIXME 没法激怒。。？

                Advancement _adv = serverPlayer.server.getAdvancements().getAdvancement(new ResourceLocation(TheCasketOfReveriesMod.MOD_ID,"wow"));
                AdvancementProgress _ap = serverPlayer.getAdvancements().getOrStartProgress(_adv);
                if (!_ap.isDone()) {
                    for (String criteria : _ap.getRemainingCriteria())
                        serverPlayer.getAdvancements().award(_adv, criteria);
                }
            }
        }
    }
}
