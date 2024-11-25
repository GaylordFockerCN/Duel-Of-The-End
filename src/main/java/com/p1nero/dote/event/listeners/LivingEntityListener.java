package com.p1nero.dote.event.listeners;

import com.p1nero.dote.DOTEConfig;
import com.p1nero.dote.DuelOfTheEndMod;
import com.p1nero.dote.capability.DOTECapabilityProvider;
import com.p1nero.dote.entity.LevelableEntity;
import com.p1nero.dote.entity.MultiPlayerBoostEntity;
import com.p1nero.dote.archive.DOTEArchiveManager;
import com.p1nero.dote.entity.custom.DOTEBoss;
import com.p1nero.dote.item.DOTEItems;
import com.p1nero.dote.item.custom.IDOTEKeepableItem;
import com.p1nero.dote.item.custom.NetherRotArmorItem;
import com.p1nero.dote.item.custom.TieStoneArmorItem;
import com.p1nero.dote.item.custom.WKnightArmorItem;
import com.p1nero.dote.util.ItemUtil;
import com.p1nero.dote.worldgen.dimension.DOTEDimension;
import com.p1nero.dote.worldgen.portal.DOTETeleporter;
import net.minecraft.ChatFormatting;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.PotionUtils;
import net.minecraft.world.item.alchemy.Potions;
import net.minecraftforge.event.entity.EntityJoinLevelEvent;
import net.minecraftforge.event.entity.EntityTravelToDimensionEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import yesman.epicfight.gameasset.EpicFightSkills;
import yesman.epicfight.world.item.EpicFightItems;

import java.util.Objects;

@Mod.EventBusSubscriber(modid = DuelOfTheEndMod.MOD_ID)
public class LivingEntityListener {

    /**
     * 穿盔甲加效果
     */
    @SubscribeEvent
    public static void onEntityUpdate(LivingEvent.LivingTickEvent event) {
        LivingEntity livingEntity = event.getEntity();
        NetherRotArmorItem.onFullSet(livingEntity);
        WKnightArmorItem.onFullSet(livingEntity);
        TieStoneArmorItem.onFullSet(livingEntity);
    }

    @SubscribeEvent
    public static void onEntityDie(LivingDeathEvent event) {
        //死亡计数器
        if(event.getEntity() instanceof Player player && player.level().dimension() == DOTEDimension.P_SKY_ISLAND_LEVEL_KEY){
            player.getCapability(DOTECapabilityProvider.DOTE_PLAYER).ifPresent(dotePlayer -> {
                dotePlayer.setDeathCount(dotePlayer.getDeathCount() + 1);
                if(dotePlayer.getDeathCount() == 5){
                    player.displayClientMessage(DuelOfTheEndMod.getInfo("tip5").withStyle(ChatFormatting.BOLD, ChatFormatting.GOLD), false);
                    ItemStack parrying = new ItemStack(EpicFightItems.SKILLBOOK.get());
                    parrying.getOrCreateTag().putString("skill", EpicFightSkills.PARRYING.toString());
                    ItemStack technician = new ItemStack(EpicFightItems.SKILLBOOK.get());
                    technician.getOrCreateTag().putString("skill", EpicFightSkills.TECHNICIAN.toString());
                    ItemUtil.addItem(player, parrying);
                    ItemUtil.addItem(player, technician);
                }
                //死五次播报一次提示
                if(dotePlayer.getDeathCount() % 5 == 1){
                    player.displayClientMessage(DuelOfTheEndMod.getInfo("tip" + (5 + player.getRandom().nextInt(3))).withStyle(ChatFormatting.BOLD, ChatFormatting.GOLD), false);
                    ItemStack potion = new ItemStack(Items.POTION);
                    PotionUtils.setPotion(potion, Potions.TURTLE_MASTER);
                    ItemUtil.addItem(player, DOTEItems.TIESTONEH.get(), 1);
                    ItemUtil.addItem(player, potion);
                }

            });
        }
        //如果在维度里怪杀死了玩家则怪回满血
        if(event.getEntity() instanceof ServerPlayer serverPlayer && serverPlayer.serverLevel().dimension() == DOTEDimension.P_SKY_ISLAND_LEVEL_KEY){
            if(event.getSource().getEntity() instanceof LivingEntity livingEntity){
                livingEntity.setHealth(livingEntity.getMaxHealth());
            }
            if(event.getSource().getDirectEntity() instanceof LivingEntity livingEntity){
                livingEntity.setHealth(livingEntity.getMaxHealth());
            }
        }
    }

    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public static void onEntityHurt(EntityTravelToDimensionEvent event) {
        if(event.getEntity() instanceof ServerPlayer player){
            if(!player.isCreative() && event.getDimension() == DOTEDimension.P_SKY_ISLAND_LEVEL_KEY && player.level().dimension() != DOTEDimension.P_SKY_ISLAND_LEVEL_KEY){
                if(!IDOTEKeepableItem.check(player, true)){
                    player.displayClientMessage(DuelOfTheEndMod.getInfo("tip0"), true);
                    ServerLevel ordinalLevel = player.serverLevel();
                    event.getEntity().changeDimension(ordinalLevel, new DOTETeleporter(ordinalLevel.getSharedSpawnPos()));
                    event.setCanceled(true);
                }
            }
        }
    }

    @SubscribeEvent
    public static void onEntityHurt(LivingHurtEvent event) {
        //开发者模式方便杀boss
        if(event.getEntity() instanceof DOTEBoss && DOTEConfig.FAST_BOSS_FIGHT.get() && event.getSource().isCreativePlayer()){
            event.setAmount(event.getAmount() * 100);
        }
    }

    /**
     * 实体加入时检查根据玩家和世界等级是否要加血
     */
    @SubscribeEvent
    public static void onEntityJoinLevel(EntityJoinLevelEvent event) {
        if(event.getLevel() instanceof ServerLevel){
            if(event.getEntity() instanceof LevelableEntity levelableEntity){
                levelableEntity.levelUp(DOTEArchiveManager.getWorldLevel());
            }
            if(event.getEntity() instanceof MultiPlayerBoostEntity multiPlayerBoostEntity){
                multiPlayerBoostEntity.whenPlayerCountChange();
            }
        }
    }

}
