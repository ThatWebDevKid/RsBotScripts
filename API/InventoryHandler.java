package scripts.API;

import org.tribot.api.General;
import org.tribot.api2007.GameTab;
import org.tribot.api2007.Inventory;
import org.tribot.api2007.Objects;
import org.tribot.api2007.types.RSItem;
import org.tribot.api2007.types.RSObject;

import java.util.function.BooleanSupplier;

public class InventoryHandler {

    private static boolean inventoryItemFoundAndValid (String invitmName) {
        return Inventory.find(invitmName).length > 0 && Inventory.find(invitmName)[0].isClickable();
    }

    public static boolean clickOnInventoryItem (String invitmName) {
        TabsHandler.openTab(GameTab.TABS.INVENTORY);
        if (inventoryItemFoundAndValid(invitmName)) {
            RSItem item = Inventory.find(invitmName)[0];
            return ItemHandler.clickOnItem(item);
        }
        return false;
    }

    public static boolean clickOnInventoryItem (String invitmName, BooleanSupplier supplier) {
        TabsHandler.openTab(GameTab.TABS.INVENTORY);
        if (inventoryItemFoundAndValid(invitmName)) {
            RSItem item = Inventory.find(invitmName)[0];
            return ItemHandler.clickOnItem(item, supplier);
        }
        return false;
    }
}
