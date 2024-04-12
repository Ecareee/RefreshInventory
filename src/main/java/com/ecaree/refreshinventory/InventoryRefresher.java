package com.ecaree.refreshinventory;

import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.network.packet.c2s.play.ClickSlotC2SPacket;
import net.minecraft.screen.slot.SlotActionType;
import net.minecraft.text.TranslatableText;
import net.minecraftforge.client.ClientRegistry;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber
public class InventoryRefresher {
    public static KeyBinding key;

    public static void init() {
        key = new KeyBinding("key.refreshinventory", 'Z', "key.categories.misc");
        ClientRegistry.registerKeyBinding(key);
    }

    @SubscribeEvent
    public static void onKeyInput(InputEvent.KeyInputEvent event) {
        if (key.isPressed()) {
            refresh();
        }
    }

    /**
     * copy of
     * <a href="https://github.com/Fallen-Breath/tweakermore/blob/stable/src/main/java/me/fallenbreath/tweakermore/impl/features/refreshInventory/InventoryRefresher.java">TweakerMore</a>
     */
    public static void refresh() {
        MinecraftClient mc = MinecraftClient.getInstance();
        ClientPlayNetworkHandler networkHandler = mc.getNetworkHandler();
        if (networkHandler != null && mc.player != null) {
            ItemStack uniqueItem = new ItemStack(Items.STONE);
            uniqueItem.getOrCreateNbt().putDouble("force_resync", Double.NaN);  // Tags with NaN are not equal
            networkHandler.sendPacket(new ClickSlotC2SPacket(
                    mc.player.currentScreenHandler.syncId,
                    mc.player.currentScreenHandler.getRevision(),
                    -999, 2,
                    SlotActionType.QUICK_CRAFT,
                    uniqueItem,
                    new Int2ObjectOpenHashMap<>()
            ));

            mc.player.sendMessage(new TranslatableText("msg.refreshinventory"), true);
        }
    }
}