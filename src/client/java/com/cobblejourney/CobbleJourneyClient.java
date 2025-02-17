package com.cobblejourney;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.minecraft.client.MinecraftClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Client-side initializer.
 * Detects when a screen is opened or closed and hides/shows the minimap accordingly.
 */
public class CobbleJourneyClient implements ClientModInitializer {

    private static final Logger LOGGER = LoggerFactory.getLogger("cobblejourney");
    private static boolean wasScreenOpen = false;

    @Override
    public void onInitializeClient() {
        LOGGER.info("CobbleJourneyClient initialized!");

        // Run code every client tick
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            if (client.world == null) {
                // Not in a game world, do nothing
                return;
            }

            // If any screen (inventory, pause, battle UI, etc.) is open, currentScreen != null
            boolean isScreenOpen = (MinecraftClient.getInstance().currentScreen != null);

            // Only toggle if there's a change
            if (isScreenOpen != wasScreenOpen) {
                wasScreenOpen = isScreenOpen;
                if (isScreenOpen) {
                    CobbleJourneyController.hideMinimap();
                } else {
                    CobbleJourneyController.showMinimap();
                }
            }
        });
    }
}
