package com.ecaree.refreshinventory;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(RefreshInventory.MOD_ID)
public class RefreshInventory {
    public static final String MOD_ID = "refreshinventory";
    public RefreshInventory() {
        MinecraftForge.EVENT_BUS.register(this);
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::init);
    }

    private void init(FMLClientSetupEvent event) {
        InventoryRefresher.init();
    }
}