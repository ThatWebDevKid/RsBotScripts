package scripts.API;

import org.tribot.api.General;
import org.tribot.api.Timing;
import org.tribot.api.input.Mouse;
import org.tribot.api2007.ChooseOption;
import org.tribot.api2007.GameTab;
import org.tribot.api2007.types.RSGroundItem;
import org.tribot.api2007.types.RSItem;


public class ItemHandler {

    private static boolean inventoryItemFoundAndValid (RSItem[] inventoryItems) {
        return inventoryItems.length > 0 && inventoryItems[0].isClickable();
    }

    private static boolean groundItemFoundAndValid (RSGroundItem[] groundItems) {
        return groundItems.length > 0 && groundItems[0].isClickable();
    }

    private static boolean rightClickInventoryItem (RSItem item, String optionToSelect) {
        if (item.hover()) {
            Mouse.click(GlobalConstants.RIGHT_CLICK);
            String optionToSelectFullString = optionToSelect + " " + item.getDefinition().getName();
            if (ChooseOption.isOptionValid(optionToSelectFullString)) {
                return ChooseOption.select(optionToSelectFullString);
            } else if (ChooseOption.isOptionValid("Cancel")) {
                if (ChooseOption.select("Cancel")) {
                    return rightClickInventoryItem(item, optionToSelect);
                }
            }
        }
        return false;
    }

    private static boolean rightClickGroundItem (RSGroundItem item, String optionToSelect) {
        if (item.hover()) {
            Mouse.click(GlobalConstants.RIGHT_CLICK);
            String optionToSelectFullString = optionToSelect + " " + item.getDefinition().getName();
            if (ChooseOption.isOptionValid(optionToSelectFullString)) {
                return ChooseOption.select(optionToSelectFullString);
            } else if (ChooseOption.isOptionValid("Cancel")) {
                ChooseOption.select("Cancel");
            }
        }
        return false;
    }

    public static boolean clickOnInventoryItem (RSItem[] inventoryItems, String optionToSelect) {
        boolean inventoryOpenedSuccessfully = TabsHandler.openTab(GameTab.TABS.INVENTORY) &&
        Timing.waitCondition(() -> {
            General.sleep(500);
            return GameTab.TABS.INVENTORY.isOpen();
        }, General.random(2000, 5000));

        if (inventoryOpenedSuccessfully && inventoryItemFoundAndValid(inventoryItems)) {
            RSItem inventoryItem = inventoryItems[0];
            boolean successfullyClicked = (optionToSelect == "") ? inventoryItem.click() : rightClickInventoryItem(inventoryItem, optionToSelect);
            return successfullyClicked;
        }
        return false;
    }

    public static boolean clickOnGroundItem (RSGroundItem[] groundItems, String optionToSelect) {
        if (groundItemFoundAndValid(groundItems)) {
            RSGroundItem groundItem = groundItems[0];
            boolean successfullyClicked = (optionToSelect == "") ? groundItem.click() : rightClickGroundItem(groundItem, optionToSelect);
            return successfullyClicked;
        }
        return false;
    }

    public static boolean clickOnInventoryItem (RSItem[] inventoryItems) {
        return clickOnInventoryItem(inventoryItems, "");
    }

    public static boolean clickOnGroundItem (RSGroundItem[] groundItems) {
        return clickOnGroundItem(groundItems, "");
    }
}
