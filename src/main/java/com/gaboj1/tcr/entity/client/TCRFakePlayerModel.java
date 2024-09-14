//package com.gaboj1.tcr.entity.client;
//
//import com.gaboj1.tcr.TheCasketOfReveriesMod;
//import com.gaboj1.tcr.entity.TCRFakePlayer;
//import net.minecraft.resources.ResourceLocation;
//import software.bernie.geckolib.model.DefaultedEntityGeoModel;
//
//public class TCRFakePlayerModel extends DefaultedEntityGeoModel<TCRFakePlayer> {
//    public TCRFakePlayerModel() {
//        super(new ResourceLocation(TheCasketOfReveriesMod.MOD_ID, "pastoral_plain_villager"), true);
//    }
//
//    @Override
//    public ResourceLocation getModelResource(TCRFakePlayer fakePlayer) {
//        return new ResourceLocation(TheCasketOfReveriesMod.MOD_ID, "geo/entity/pastoral_plain_villager"+(fakePlayer.isSlim()?"_slim":"")+".geo.json");
//    }
//
//    @Override
//    public ResourceLocation getTextureResource(TCRFakePlayer fakePlayer) {
//        return fakePlayer.getSkinTextureLocation();
//    }
//
//
//}
