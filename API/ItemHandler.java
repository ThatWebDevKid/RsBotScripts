package scripts.API;


import org.tribot.api.General;
import org.tribot.api.Timing;
import org.tribot.api2007.types.RSItem;

import java.util.function.BooleanSupplier;

public class ItemHandler {
    public static boolean interactWithItem(RSItem item, String action, BooleanSupplier supplier) {
        if (item.click(action)) {
            Timing.waitCondition(() -> {
                General.sleep(500);
                return supplier.getAsBoolean();
            }, General.random(3000, 6000));
            return true;
        }

        return false;
    }

    public static boolean clickOnItem(RSItem item, BooleanSupplier supplier) {
        return interactWithItem(item, "", supplier);
    }

    public static boolean clickOnItem(RSItem item) {
        return interactWithItem(item, "", () -> true);
    }
}
