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
    public static final int DEFAULT_DISTANCE = 15;

    private static boolean objectExists (RSObject[] objects) {
        return objects.length > 0;
    }

    private static boolean objectValid (RSObject object) {
        return object.isOnScreen() &&
                object.isClickable();
    }

    private static boolean rightClickObject (RSObject object, String optionToSelect) {
        if (object.hover()) {
            Mouse.click(GlobalConstants.RIGHT_CLICK);
            if (ChooseOption.isOptionValid(optionToSelect)) {
                return ChooseOption.select(optionToSelect);
            }
        }
        return false;
    }

    public static boolean interactWithObject(RSObject[] objects, String optionToSelect) {
        if (objectExists(objects)) {
            RSObject object = objects[0];
            boolean walkToAndInView = true;

            if (!objectValid(object)) {
                walkToAndInView = !objectValid(object) && Timing.waitCondition(() -> {
                    General.sleep(500);
                    return DaxWalker.walkTo(object.getPosition()) && object.adjustCameraTo();
                }, General.random(5000, 10000));
            }

            if (walkToAndInView && objectValid(object)) {
                boolean successfullyClicked = (optionToSelect == "") ? object.click() : rightClickObject(object, optionToSelect);
                return successfullyClicked && Timing.waitCondition(() -> {
                    General.sleep(500);
                    return Player.getAnimation() != -1;
                }, General.random(8000, 15000)) &&
                        Timing.waitCondition(() -> {
                            General.sleep(500);
                            return Player.getAnimation() == -1;
                        }, General.random(8000, 15000));
            }
        }
        return false;
    }

}
