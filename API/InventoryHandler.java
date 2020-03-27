package scripts.API;

import org.tribot.api.General;
import org.tribot.api2007.Inventory;
import org.tribot.api2007.Objects;
import org.tribot.api2007.types.RSItem;
import org.tribot.api2007.types.RSObject;

public class InventoryHandler {
    private static boolean inventoryItemFound (int invitmId) {
        return Inventory.find(invitmId).length > 0;
    }

    private static boolean inventoryItemFoundAndValid (int invitmId) {
        return inventoryItemFound(invitmId) && Inventory.find(invitmId)[0].isClickable();
    }

    private static boolean inventoryItemFound (String invitmName) {
        return Inventory.find(invitmName).length > 0;
    }

    private static boolean inventoryItemFoundAndValid (String invitmName) {
        return inventoryItemFound(invitmName) && Inventory.find(invitmName)[0].isClickable();
    }

    public static boolean clickOnInventoryItem (String invitmName) {
        if (inventoryItemFoundAndValid(invitmName)) {
            RSItem item = Inventory.find(invitmName)[0];
            item.hover();
            General.sleep(1000,2000);
            item.click();
            General.sleep(1000,3000);
            return true;
        }
        return false;
    }
}
