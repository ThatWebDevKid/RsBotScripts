package scripts.fruitytootie.nodes;

import org.tribot.api.General;
import org.tribot.api2007.Banking;
import org.tribot.api2007.Inventory;
import scripts.API.Node;
import scripts.dax_api.api_lib.DaxWalker;
import scripts.dax_api.api_lib.models.RunescapeBank;

public class Bank extends Node {
    private boolean dontHaveRequiredStartingItems;
    public void printStatus() {
        General.println("Getting stuff from bank");
    }

    public boolean validate() {
        return Inventory.find("Fruit blast").length >= 10;
    }

    public void execute() {
        if (DaxWalker.walkToBank(RunescapeBank.GNOME_TREE_BANK_SOUTH)) {
            while (!Banking.openBank()) {
                General.sleep(500);
            }

            if (Banking.isBankScreenOpen()) {
                Banking.deposit(0, "Fruit blast");
            }
        }







    }
}
