package scripts.API;

import org.tribot.api.General;
import org.tribot.api.Timing;
import org.tribot.api.input.Mouse;
import org.tribot.api2007.ChooseOption;
import org.tribot.api2007.Objects;
import org.tribot.api2007.Player;
import org.tribot.api2007.types.RSNPC;
import org.tribot.api2007.types.RSObject;
import scripts.dax_api.api_lib.DaxWalker;

import java.util.function.BooleanSupplier;

public class ObjectHandler {
    private static final int DEFAULT_DISTANCE = 15;

    private static boolean objectFound (int objId) { return Objects.findNearest(DEFAULT_DISTANCE, objId).length > 0; }

    private static boolean objectFound (String objName) { return Objects.findNearest(DEFAULT_DISTANCE, objName).length > 0; }

    private static boolean objectFoundAndValid (int objId) {
        return objectFound(objId) &&
                Objects.findNearest(DEFAULT_DISTANCE, objId)[0].isOnScreen() &&
                Objects.findNearest(DEFAULT_DISTANCE, objId)[0].isClickable();
    }

    public static void interactWithObject(String objName, String action) {
        if (objectFound(objName)) {
            RSObject object = Objects.findNearest(DEFAULT_DISTANCE, objName)[0];
            interactWithObject(object.getID(), action);
        }
    }

    private static boolean rightClickObject (RSObject object, String action) {
        object.hover();
        Mouse.click(GlobalConstants.RIGHT_CLICK);
        if (ChooseOption.isOptionValid(action)) {
            ChooseOption.select(action);
            return true;
        }
        return false;
    }

    public static void interactWithObject(int objId, String action) {
        if (!objectFound(objId)) {
            return;
        }

        RSObject object = Objects.findNearest(DEFAULT_DISTANCE, objId)[0];
        if (objectFoundAndValid(objId)) {
            boolean successfullyClicked = (action == "") ? object.click() : rightClickObject(object, action);
            if (successfullyClicked) {
                Timing.waitCondition(() -> {
                    General.sleep(500);
                    return Player.getAnimation() != -1;
                }, General.random(10000, 15000));
                Timing.waitCondition(() -> {
                    General.sleep(500);
                    return Player.getAnimation() == -1;
                }, General.random(10000, 15000));
                return;
            }
        }

        DaxWalker.walkTo(object.getPosition());
        object.adjustCameraTo();
        Timing.waitCondition(() -> {
            General.sleep(500);
            return objectFoundAndValid(objId);
        }, General.random(2000, 5000));
    }

    public static void clickOnObject (int objId) {
        interactWithObject(objId, "");
    }

    public static void clickOnObject (String objName) {
        interactWithObject(objName, "");
    }
}
