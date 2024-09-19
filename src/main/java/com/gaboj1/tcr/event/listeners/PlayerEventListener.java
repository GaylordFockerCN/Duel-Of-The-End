package com.gaboj1.tcr.event.listeners;

import com.gaboj1.tcr.TheCasketOfReveriesMod;
import com.gaboj1.tcr.capability.TCRCapabilityProvider;
import com.gaboj1.tcr.entity.TCRFakePlayer;
import com.gaboj1.tcr.item.custom.weapon.GunCommon;
import com.gaboj1.tcr.util.DataManager;
import com.gaboj1.tcr.worldgen.biome.TCRBiomeTags;
import com.gaboj1.tcr.worldgen.dimension.TCRDimension;
import net.minecraft.client.resources.language.I18n;
import net.minecraft.commands.CommandSource;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.living.LivingEntityUseItemEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.entity.player.PlayerWakeUpEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = TheCasketOfReveriesMod.MOD_ID)
public class PlayerEventListener {

    /**
     * 初始化玩家数据
     *
     * 重新登录时如果在维度且主世界没有假身则召唤假身
     */
    @SubscribeEvent
    public static void onPlayerLoggedIn(PlayerEvent.PlayerLoggedInEvent event) {
        DataManager.init(event.getEntity());

        Player player = event.getEntity();
        if(player instanceof ServerPlayer serverPlayer && serverPlayer.serverLevel().dimension() == TCRDimension.P_SKY_ISLAND_LEVEL_KEY && serverPlayer.getServer() != null){
            serverPlayer.getCapability(TCRCapabilityProvider.TCR_PLAYER).ifPresent((tcrPlayer -> {
                if(tcrPlayer.getFakePlayerUuid() == null || !(serverPlayer.serverLevel().getEntity(tcrPlayer.getFakePlayerUuid()) instanceof TCRFakePlayer)){
                    //召唤假人
                    ServerLevel overworld = serverPlayer.getServer().overworld();
                    BlockPos bedBlockPos = tcrPlayer.getBedPointBeforeEnter();
                    TCRFakePlayer fakePlayer = new TCRFakePlayer(serverPlayer, overworld, bedBlockPos);
                    overworld.addFreshEntity(fakePlayer);
                    fakePlayer.setSleepingPos(bedBlockPos);
                }
            }));
        }
    }

    /**
     * 前面的区域以后再来探索吧~
     */
    @SubscribeEvent
    public static void enterBiome(TickEvent.PlayerTickEvent event) {
        Player player = event.player;
        if(player.isCreative()){
            return;
        }
        Level level = player.level();
        Holder<Biome> currentBiome = level.getBiome(player.getOnPos());
        if (currentBiome.is(TCRBiomeTags.FORBIDDEN_BIOME)) {

            if (player instanceof ServerPlayer serverPlayer && player.getServer() != null) {
                String commands = "/title "+player.getName().getString()+" title {\"text\":\""+ I18n.get("info.the_casket_of_reveries.enter_forbidden_biome") +"\"}";
                player.getServer().getCommands().performPrefixedCommand(new CommandSourceStack(CommandSource.NULL, player.position(), player.getRotationVector(), serverPlayer.serverLevel(), 4,
                        player.getName().getString(), player.getDisplayName(), serverPlayer.server, player), commands);
            }

            player.displayClientMessage(Component.translatable("info.the_casket_of_reveries.enter_forbidden_biome"), true);
            player.addEffect(new MobEffectInstance(MobEffects.BLINDNESS, 20, 1, false, true));
        }
    }


    @SubscribeEvent
    public static void onPlayerUseItem(LivingEntityUseItemEvent.Start event){
    }

    @SubscribeEvent
    public static void onPlayerWakeup(PlayerWakeUpEvent event){
        if(event.getEntity() instanceof ServerPlayer serverPlayer){
            if(serverPlayer.serverLevel().dimension() == TCRDimension.P_SKY_ISLAND_LEVEL_KEY){
                serverPlayer.serverLevel().setDayTime(1000);
            }
        }
    }

    /**
     * @author LZY_Pinero
     * 利用原版物品冷却的特性使沙鹰双持状态下可以左右开火（不要小看这段代码，没有这段代码无法实现交替开火而且会变成一起开火）
     * 原版右键空气时左右两边都会触发use，但是主手会更快点，所以只要判断沙鹰双持且两物品不同（物品共用冷却）时让没冷却的那个物品迅速冷却1刻（防止一只手use完立马被调用）
     * 有一点很关键，开始是没有判断canFire的，会导致左手可以开火但是由于右手先点击而导致左手武器被禁用
     *
     */
    @SubscribeEvent
    public static void doubleHoldCoolDownController(PlayerInteractEvent event) {

        Player player = event.getEntity();
        ItemStack offHandStack = player.getOffhandItem();
        ItemStack mainHandStack = player.getMainHandItem();
        if (offHandStack.getItem().getClass() == mainHandStack.getItem().getClass())return;
        if(mainHandStack.getItem() instanceof GunCommon mainHandItem&& offHandStack.getItem() instanceof GunCommon offHandItem){

            if(event.getHand() == InteractionHand.MAIN_HAND){
                if(!player.getCooldowns().isOnCooldown(offHandItem)&& !mainHandItem.isReloading){
                    player.getCooldowns().addCooldown(offHandItem,1);
                }
            }else{
                if((!player.getCooldowns().isOnCooldown(mainHandItem)) && !offHandItem.isReloading){
                    player.getCooldowns().addCooldown(mainHandItem,1);
                }
            }

        }
    }

}
