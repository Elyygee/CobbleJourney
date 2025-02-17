package com.cobblejourney;

import journeymap.client.ui.UIManager;
import journeymap.client.ui.minimap.MiniMap;
import journeymap.client.properties.MiniMapProperties;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;

/**
 * Tracks the user's "last known good" minimap config when not hidden,
 * and prevents the map from staying invisible across sessions.
 */
public class MinimapStateTracker implements ClientModInitializer {

    private static boolean firstTickAfterLoad = true;

    // We'll store a snapshot of relevant fields.
    // If the user changes them while the map is visible, we update them.
    // Then if the user reopens the map, we set them again.
    private static Float lastBackgroundAlpha = null;
    private static Integer lastTerrainAlpha = null;
    private static Boolean lastShowEntityNames = null;
    private static Boolean lastShowWaypoints = null;
    private static Boolean lastShowWaypointLabels = null;
    private static Boolean lastShowReticle = null;
    private static Boolean lastShowCompass = null;
    // ... add more fields as needed

    @Override
    public void onInitializeClient() {
        // On each client tick, check the minimap state
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            // If this is the first tick after load, handle the "map hidden on exit" scenario
            if (firstTickAfterLoad) {
                firstTickAfterLoad = false;
                // If we ended last session forcibly hidden, let's re-enable the map
                // so the user doesn't get stuck with a permanently invisible minimap.
                if (CobbleJourneyController.isMinimapHidden()) {
                    CobbleJourneyController.showMinimap();
                }
            }

            // If there's no world loaded, do nothing
            if (client.world == null) {
                return;
            }

            // If forcibly hidden, do not snapshot user settings (or restore them)
            if (CobbleJourneyController.isMinimapHidden()) {
                return;
            }

            MiniMap minimap = UIManager.INSTANCE.getMiniMap();
            if (minimap == null) {
                return;
            }
            MiniMapProperties props = minimap.getCurrentMinimapProperties();
            if (props == null) {
                return;
            }

            // If the minimap is actually enabled (user might have disabled it themselves)
            if (props.enabled.get()) {
                // Continuously store the user's current settings as our "last known" config
                // If user changes them in JourneyMap's UI, we pick them up here.
                lastBackgroundAlpha = props.backgroundAlpha.get();
                lastTerrainAlpha = props.terrainAlpha.get();
                lastShowEntityNames = props.showEntityNames.get();
                lastShowWaypoints = props.showWaypoints.get();
                lastShowWaypointLabels = props.showWaypointLabels.get();
                lastShowReticle = props.showReticle.get();
                lastShowCompass = props.showCompass.get();
                // ... etc
            }
            else {
                // The user disabled the minimap manually in their own config,
                // so let's reflect that in our snapshot?
                // Or we can just do nothing here. It's your choice.
            }
        });
    }

    /**
     * Re-applies the user's last known config if we forcibly re-enable the map.
     * Called by showMinimap() after we set `enabled = true`, for example.
     */
    public static void restoreUserConfig() {
        MiniMap minimap = UIManager.INSTANCE.getMiniMap();
        if (minimap == null) return;

        MiniMapProperties props = minimap.getCurrentMinimapProperties();
        if (props == null) return;

        // If we have snapshots, set them back
        if (lastBackgroundAlpha != null) props.backgroundAlpha.set(lastBackgroundAlpha);
        if (lastTerrainAlpha != null) props.terrainAlpha.set(lastTerrainAlpha);
        if (lastShowEntityNames != null) props.showEntityNames.set(lastShowEntityNames);
        if (lastShowWaypoints != null) props.showWaypoints.set(lastShowWaypoints);
        if (lastShowWaypointLabels != null) props.showWaypointLabels.set(lastShowWaypointLabels);
        if (lastShowReticle != null) props.showReticle.set(lastShowReticle);
        if (lastShowCompass != null) props.showCompass.set(lastShowCompass);

        minimap.reset();
    }
}
