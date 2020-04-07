package scripts.API;

import org.tribot.api.General;
import org.tribot.api2007.Interfaces;
import org.tribot.api2007.types.RSInterface;

public class InterfaceHandler {

    public static boolean interfaceContainsText(RSInterface runescapeInterface, CharSequence text) {
        return Interfaces.isInterfaceSubstantiated(runescapeInterface) &&
                runescapeInterface.getText().contains(text);
    }

    public static boolean interfaceContainsTextInComponentName(RSInterface runescapeInterface, CharSequence text) {
        return Interfaces.isInterfaceSubstantiated(runescapeInterface) &&
                runescapeInterface.getComponentName().contains(text);
    }

    public static boolean clickHereToContinue () {
        RSInterface clickHereToContinueInterfaces[] = {
                Interfaces.get(193,0,2),
                Interfaces.get(229,2),
                Interfaces.get(162,45),
                Interfaces.get(233,3),
                Interfaces.get(193,2)
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
        if (Interfaces.isInterfaceSubstantiated(runescapeInterface)) {
            return runescapeInterface.click();
        }
        return false;
    }
}
