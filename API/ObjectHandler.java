package scripts.API;

import org.tribot.api.General;
import org.tribot.api.Timing;
import org.tribot.api.input.Mouse;
import org.tribot.api2007.*;
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
                object.isClickable() &&
                PathFinding.canReach(object.getAnimablePosition(), false);
    }

    private static boolean rightClickObject (RSObject object, String optionToSelect) {
        if (object.hover()) {
            Mouse.click(GlobalConstants.RIGHT_CLICK);
            String optionToSelectFullString = optionToSelect + " " + object.getDefinition().getName();
            if (ChooseOption.isOptionValid(optionToSelectFullString)) {
                return ChooseOption.select(optionToSelectFullString);
            }
        }
        return false;
    }

    public static boolean interactWithObject(RSObject[] objects, String optionToSelect) {
        if (objectExists(objects)) {
            RSObject object = objects[0];
            boolean walkToAndInView = true;

            if (!objectValid(object)) {
                if (DaxWalker.walkTo(object.getAnimablePosition())) {
                    General.println("Using Dax walker to walk to object");
                    Timing.waitCondition(() -> Player.getPosition().distanceTo(object.getAnimablePosition()) < 5, General.random(5000, 10000));
                    object.adjustCameraTo();
                } else if (WebWalking.walkTo(object.getAnimablePosition())) {
                    General.println("Using Web walker to walk to object");
                    Timing.waitCondition(() -> Player.getPosition().distanceTo(object.getAnimablePosition()) < 5, General.random(5000, 10000));
                    object.adjustCameraTo();
                }
                walkToAndInView = objectValid(object);
            }

            if (walkToAndInView && objectValid(object)) {
                boolean successfullyClicked = (optionToSelect == "") ? object.click() : rightClickObject(object, optionToSelect);
                General.println("Object Sucessfully clicked? : " + successfullyClicked);
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

    public static boolean interactWithObject(RSObject[] objects) {
        return interactWithObject(objects, "" );
    }

}
