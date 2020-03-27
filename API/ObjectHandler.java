package scripts.API;

import org.tribot.api.General;
import org.tribot.api2007.Objects;
import org.tribot.api2007.types.RSObject;

public class ObjectHandler {
    private static final int DEFAULT_DISTANCE = 8;
    private static boolean objectFound (int objId) {
        return Objects.find(DEFAULT_DISTANCE, objId).length > 0;
    }

    private static boolean objectFoundAndValid (int objId) {
        return objectFound(objId) && Objects.findNearest(DEFAULT_DISTANCE, objId)[0].isOnScreen() && Objects.findNearest(DEFAULT_DISTANCE, objId)[0].isClickable();
    }

    private static boolean objectFound (String objName) {
        return Objects.find(DEFAULT_DISTANCE, objName).length > 0;
    }

    private static boolean objectFoundAndValid (String objName) {
        return objectFound(objName) && Objects.findNearest(DEFAULT_DISTANCE, objName)[0].isOnScreen() && Objects.findNearest(DEFAULT_DISTANCE, objName)[0].isClickable();
    }

    public static boolean clickOnObject (int objId) {
        if (objectFoundAndValid(objId)) {
            RSObject object = Objects.findNearest(DEFAULT_DISTANCE, objId)[0];
            object.click();
            General.sleep(3000,6000);
            return true;
        }
        return false;
    }

    public static boolean clickOnObject (String objName) {
        if (objectFoundAndValid(objName)) {
            RSObject object = Objects.findNearest(DEFAULT_DISTANCE, objName)[0];
            object.click();
            General.sleep(3000,6000);
            return true;
        }
        return false;
    }
}
