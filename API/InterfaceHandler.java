package scripts.API;

import org.tribot.api.General;
import org.tribot.api2007.Interfaces;
import org.tribot.api2007.types.RSInterface;

public class InterfaceHandler {

    public static boolean interfaceIsValid(RSInterface runescapeInterface) {
        return runescapeInterface != null;
    }

    public static boolean interfaceIsVisibleOnScreen(RSInterface runescapeInterface) {
        return interfaceIsValid(runescapeInterface) &&  !runescapeInterface.isHidden();
    }

    public static boolean interfaceContainsText(RSInterface runescapeInterface, CharSequence text) {
        return interfaceIsVisibleOnScreen(runescapeInterface) &&
                runescapeInterface.getText().contains(text);
    }

    public static boolean clickHereToContinue () {
        RSInterface clickHereToContinueInterfaces[] = {
                Interfaces.get(193,0,2),
                Interfaces.get(229,2),
                Interfaces.get(162,45)
        };
        for (RSInterface clickHereToContinueInterface: clickHereToContinueInterfaces) {
            if (interfaceContainsText(clickHereToContinueInterface, "Click here to continue") ||
            interfaceContainsText(clickHereToContinueInterface, "Click to continue")) {
                return clickInterface(clickHereToContinueInterface);
            }
        }
        return false;

    }

    public static boolean clickInterface(RSInterface runescapeInterface) {
        if (interfaceIsVisibleOnScreen(runescapeInterface)) {
            runescapeInterface.click();
            General.sleep(General.random(1000, 2000), General.random(3000,5000));
            return true;
        }
        return false;
    }
}
