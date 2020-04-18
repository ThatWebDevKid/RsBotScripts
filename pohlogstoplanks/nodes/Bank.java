package scripts.pohlogstoplanks.nodes;

import org.tribot.api.General;
import org.tribot.api.Timing;
import org.tribot.api2007.Banking;
import org.tribot.api2007.Inventory;
import org.tribot.api2007.Player;
import scripts.API.Node;
import scripts.pohlogstoplanks.Constants;
import scripts.pohlogstoplanks.PohLogsToPlanks;

public class Bank extends Node {

    public void printStatus() {
        General.println("Banking!");
    }
    public boolean validate() {
        return Constants.LUMBRIDGE_AREA.contains(Player.getPosition()) && (Inventory.getAll().length == 3 || Inventory.find("Oak plank").length > 0);

    }
    public void execute() {
        // Firstly we must open the bank screen
        while (!Banking.openBank());

        // First we must deposit all of our planks if any in our inventory
        if (Inventory.find("Oak plank").length > 0) {
            if (!Banking.deposit(0, "Oak plank")) {
                return;
            }
        }

        Timing.waitCondition(() -> {
            General.sleep(500);
            return Inventory.getAll().length == 3;
        }, General.random(1000, 2000));

        if (Banking.find("Oak logs").length <= 0) {
            PohLogsToPlanks.noMoreLogs = true;
            return;
        }

        // Now we must withdraw all oak logs
        if (!Banking.withdraw(0, "Oak logs")) {
            return;
        }

        Timing.waitCondition(() -> {
            General.sleep(500);
            return Inventory.getAll().length > 3;
        }, General.random(1000, 2000));

        // Now clsoe bank screen
        while (!Banking.close());
    }
}
