package scripts.API;

import org.tribot.api.Clicking;
import org.tribot.api.DynamicClicking;
import org.tribot.api.General;
import org.tribot.api.input.Mouse;
import org.tribot.api.util.abc.ABCUtil;
import org.tribot.api2007.ChooseOption;
import org.tribot.api2007.types.RSNPC;
import org.tribot.api2007.types.RSObject;

// Antiban functions have been retrieved from https://github.com/Einstein-TRiBot/Universal-Woodcutter/blob/master/utility/Antiban.java
// Thanks to Einstein from Tribot for open sourcing his code to help beginners like myself understand Antiban better

public class Antiban {

    private static ABCUtil single_instance = null;
    // Used in action conditions
    private static boolean shouldHover = false;
    private static boolean shouldOpenMenu = false;

    // static method to create instance of Singleton class
    public static ABCUtil getInstance()
    {
        if (single_instance == null)
            single_instance = new ABCUtil();

        return single_instance;
    }

    public static void checkTimedActions() {
        // Here our player is idling, so we can check/perform the timed actions.
        if (getInstance().shouldCheckTabs())
            getInstance().checkTabs();

        if (getInstance().shouldCheckXP())
            getInstance().checkXP();

        if (getInstance().shouldExamineEntity())
            getInstance().examineEntity();

        if (getInstance().shouldMoveMouse())
            getInstance().moveMouse();

        if (getInstance().shouldPickupMouse())
            getInstance().pickupMouse();

        if (getInstance().shouldRightClick())
            getInstance().rightClick();

        if (getInstance().shouldRotateCamera())
            getInstance().rotateCamera();

        if (getInstance().shouldLeaveGame())
            getInstance().leaveGame();
    }

    /**
     * Updates the variables.
     *
     * Must be called upon starting an action. (example: clicking a tree)
     */
    public static void setHoverAndMenuOpenBooleans() {
        shouldHover = getInstance().shouldHover();
        shouldOpenMenu= getInstance().shouldOpenMenu();
    }

    public static void setHoverBooleans() {
        shouldHover = getInstance().shouldHover();
    }

    /**
     * Hovers over or opens the menu for target, if it should.
     *
     * Will be called while performing an action. (example: while cutting a tree)
     *
     * @param targets to hover/open menu
     */
    public static void executeHoverOrMenuOpenObject(RSObject[] targets) {
        if (ObjectHandler.objectExistsAndValidWithoutCanReach(targets)) {
            RSObject target = targets[0];
            if (Mouse.isInBounds() && shouldHover) {
                Clicking.hover(target);
                if (shouldOpenMenu)
                    if (!ChooseOption.isOpen())
                        DynamicClicking.clickRSObject(target, 3);
            }
        }

    }

    public static void executeHoverOrMenuOpenNPC(RSNPC[] targets, boolean closeInteract) {
        if (NPCHandler.npcFoundAndValid(targets, closeInteract)) {
            RSNPC target = targets[0];
            if (Mouse.isInBounds() && shouldHover) {
                Clicking.hover(target);
                if (shouldOpenMenu)
                    if (!ChooseOption.isOpen())
                        DynamicClicking.clickRSNPC(target, 3);
            }
        }
    }

    public static void executeHoverOpenNPC(RSNPC[] targets, boolean closeInteract) {
        if (NPCHandler.npcFoundAndValid(targets, closeInteract)) {
            RSNPC target = targets[0];
            if (Mouse.isInBounds() && shouldHover) {
                Clicking.hover(target);
            }
        }
    }

    // 4. Reaction times  ______________________________________________________________________________________________________________________

    /**
     * Generates reaction time using bit flags.
     *
     * @param waitingTime - the amount of time the script was waiting for (e.g. amount of time spent cutting down a tree)
     * @return the generated reaction time in ms
     */
    private static int generateReactionTime(int waitingTime) {
        boolean menuOpen = getInstance().shouldOpenMenu() && getInstance().shouldHover();
        boolean hovering = getInstance().shouldHover();
        long menuOpenOption = menuOpen ? ABCUtil.OPTION_MENU_OPEN : 0;
        long hoverOption = hovering ? ABCUtil.OPTION_HOVERING : 0;

        return getInstance().generateReactionTime(getInstance().generateBitFlags(waitingTime, menuOpenOption, hoverOption));
    }

    /**
     * Generates supporting tracking information using bit flags.
     * This method should be called right after clicking something that will require the player to wait for a while.
     *
     * @param estimatedTime for the action to complete (e.g. amount of time spent cutting down a tree)
     */
    public static void generateSupportingTrackerInfo(int estimatedTime) {
        getInstance().generateTrackers(estimatedTime);
    }

    /**
     * Calls generate() and passes waitingTime as an argument.
     * Sleeps for the generated amount of time.
     *
     * @param waitingTime the amount of time the script was waiting for (e.g. amount of time spent cutting down a tree)
     */
    public static void generateAndSleep(int waitingTime) {
        try {
            int time = generateReactionTime(waitingTime) / 2;
            General.println("Sleeping for: " + ((double) (time / 1000)) + " seconds.");
            getInstance().sleep(time);
        } catch (InterruptedException e) {
            General.println("The background thread interrupted the abc sleep.");
        }
    }
}
