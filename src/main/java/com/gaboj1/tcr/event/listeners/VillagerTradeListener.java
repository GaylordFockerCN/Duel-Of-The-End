//package com.gaboj1.tcr.listener;
//
//import com.gaboj1.tcr.TheCasketOfReveriesMod;
//import com.gaboj1.tcr.item.TCRModItems;
//import com.gaboj1.tcr.entity.TCRModVillagers;
//import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
//import net.minecraft.world.entity.npc.VillagerTrades;
//import net.minecraft.world.item.ItemStack;
//import net.minecraft.world.item.Items;
//import net.minecraft.world.item.trading.MerchantOffer;
//import net.minecraftforge.event.village.VillagerTradesEvent;
//import net.minecraftforge.eventbus.api.SubscribeEvent;
//import net.minecraftforge.fml.common.Mod;
//import java.util.List;
//
//@Mod.EventBusSubscriber(modid = TheCasketOfReveriesMod.MOD_ID)
//public class VillagerTradeListener {
//
//    @SubscribeEvent
//    public static void addCustomTrade(VillagerTradesEvent event) {
//        if(event.getType() == TCRModVillagers.TCR_MERCHANT.get()){
//            Int2ObjectMap<List<VillagerTrades.ItemListing>> trades = event.getTrades();
//
//            trades.get(1).add((pTrader, pRandom) -> new MerchantOffer(
//                    new ItemStack(Items.EMERALD, 16),
//                    new ItemStack(TCRModItems.BEER.get(), 1),
//                    16, 8, 0.02f));
//
//            trades.get(2).add((pTrader, pRandom) -> new MerchantOffer(
//                    new ItemStack(Items.EMERALD, 6),
//                    new ItemStack(TCRModItems.BEER.get(), 2),
//                    5, 12, 0.02f));
//        }
//    }
//
//}
