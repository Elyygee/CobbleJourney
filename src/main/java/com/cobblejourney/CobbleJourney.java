package com.cobblejourney;

import net.fabricmc.api.ModInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * (Optional)
 * Implements ModInitializer for any common (server + client) initialization logic.
 * If you don't need shared code, you can omit this file and remove the "main" entrypoint.
 */
public class CobbleJourney implements ModInitializer {

    public static final Logger LOGGER = LoggerFactory.getLogger("cobblejourney");

    @Override
    public void onInitialize() {
        LOGGER.info("CobbleJourney Mod (common init)!");
        // Put any common (client+server) initialization logic here,
        // or leave empty if you only want a client entrypoint.
    }
}
