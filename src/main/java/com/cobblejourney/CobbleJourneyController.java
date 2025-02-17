package com.cobblejourney;

import journeymap.client.ui.UIManager;
import journeymap.client.ui.minimap.MiniMap;
import journeymap.client.properties.MiniMapProperties;

/**
 * Disables the minimap by setting 'enabled' to false, plus zeroing out alpha and 
 * toggling other known fields (like showWaypoints, showEntityNames, etc.) that exist in MiniMapProperties.
 */
public class CobbleJourneyController {
    private static boolean isHidden = false;
    private static Boolean originalEnabled = null;
    private static Float originalBackgroundAlpha = null;
    private static Integer originalTerrainAlpha = null;
    private static Boolean originalShowEntityNames = null;
    private static Boolean originalShowWaypoints = null;
    private static Boolean originalShowWaypointLabels = null;
    private static Boolean originalShowReticle = null;
    private static Boolean originalShowCompass = null;
    
    public static boolean isMinimapHidden() {
        return isHidden;
    }
    
    public static void hideMinimap() {
        // If you're tracking a boolean to indicate forced hiding, set it here
        isHidden = true;
    
        MiniMap minimap = UIManager.INSTANCE.getMiniMap();
        if (minimap != null) {
            MiniMapProperties props = minimap.getCurrentMinimapProperties();
            if (props != null) {
                // Save original values if they haven't been saved yet
                if (originalEnabled == null) {
                    originalEnabled = props.enabled.get();
                }
                if (originalBackgroundAlpha == null) {
                    originalBackgroundAlpha = props.backgroundAlpha.get();
                }
                if (originalTerrainAlpha == null) {
                    originalTerrainAlpha = props.terrainAlpha.get();
                }
                if (originalShowEntityNames == null) {
                    originalShowEntityNames = props.showEntityNames.get();
                }
                if (originalShowWaypoints == null) {
                    originalShowWaypoints = props.showWaypoints.get();
                }
                if (originalShowWaypointLabels == null) {
                    originalShowWaypointLabels = props.showWaypointLabels.get();
                }
                if (originalShowReticle == null) {
                    originalShowReticle = props.showReticle.get();
                }
                if (originalShowCompass == null) {
                    originalShowCompass = props.showCompass.get();
                }
    
                // Disable or zero out to hide everything
                props.enabled.set(false);       // Turn off minimap entirely
                props.backgroundAlpha.set(0.0F); // Zero background alpha
                props.terrainAlpha.set(0);      // Zero terrain alpha
                props.showEntityNames.set(false);
                props.showWaypoints.set(false);
                props.showWaypointLabels.set(false);
                props.showReticle.set(false);
                props.showCompass.set(false);
    
                minimap.reset();
            }
        }
    }
    
    public static void showMinimap() {
        // If you're tracking whether we've forcibly hidden the map, reset that flag
        isHidden = false;
    
        MiniMap minimap = UIManager.INSTANCE.getMiniMap();
        if (minimap != null) {
            MiniMapProperties props = minimap.getCurrentMinimapProperties();
            if (props != null) {
    
                // Ensure the minimap is turned back on
                props.enabled.set(true);
    
                // Restore original values if present
                if (originalBackgroundAlpha != null) {
                    props.backgroundAlpha.set(originalBackgroundAlpha);
                    originalBackgroundAlpha = null;
                }
                if (originalTerrainAlpha != null) {
                    props.terrainAlpha.set(originalTerrainAlpha);
                    originalTerrainAlpha = null;
                }
                if (originalShowEntityNames != null) {
                    props.showEntityNames.set(originalShowEntityNames);
                    originalShowEntityNames = null;
                }
                if (originalShowWaypoints != null) {
                    props.showWaypoints.set(originalShowWaypoints);
                    originalShowWaypoints = null;
                }
                if (originalShowWaypointLabels != null) {
                    props.showWaypointLabels.set(originalShowWaypointLabels);
                    originalShowWaypointLabels = null;
                }
                if (originalShowReticle != null) {
                    props.showReticle.set(originalShowReticle);
                    originalShowReticle = null;
                }
                if (originalShowCompass != null) {
                    props.showCompass.set(originalShowCompass);
                    originalShowCompass = null;
                }
                if (originalEnabled != null) {
                    props.enabled.set(originalEnabled);
                    originalEnabled = null;
                }
    
                // Force JourneyMap to recalc and re-render with the restored settings
                minimap.reset();
            }
        }
    }
    
}
