package control.myawt;

/**
 * Used to distinguish mode change events triggered by human and code
 */
public enum ModeTrigger {
    /**
     * resetting to first mode
     */
    DOCK_PANEL,
    /**
     * human action in tool bar
     */
    TOOLBAR,
    /**
     * exit mode set temporarily
     */
    EXIT_TEMPORARY_MODE,
}
