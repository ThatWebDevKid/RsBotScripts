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
        return Constants.LUMBRIDGE_AREA.contains(Player.getPosition()) && (Inventory.getAll().length <= 4
                || Inventory.find(PohLogsToPlanks.plankType).length > 0 || Inventory.find(PohLogsToPlanks.logTypeNoted).length > 0);

    }
    public void execute() {
        // Firstly we must open the bank screen
        while (!Banking.openBank());

        // First we must deposit all of our planks if any in our inventory
        if (Inventory.find(PohLogsToPlanks.plankType).length > 0) {
            if (!Banking.deposit(0, PohLogsToPlanks.plankType)) {
                return;
            }
        }

        if (Inventory.find(PohLogsToPlanks.logTypeNoted).length > 0) {
            if (!Banking.deposit(0, PohLogsToPlanks.logTypeNoted)) {
                return;
            }
        }

        if (Inventory.find("Ring of wealth").length > 0) {
            if (!Banking.deposit(0, "Ring of wealth")) {
                return;
            }
        }

        Timing.waitCondition(() -> {
            General.sleep(500);
            return Inventory.getAll().length <= 3;
        }, General.random(1000, 2000));

        if (Banking.find(PohLogsToPlanks.logType).length <= 0) {
            PohLogsToPlanks.noMoreLogs = true;
        }

        // Now we must withdraw all oak logs
        Banking.withdraw(0, PohLogsToPlanks.logType);

        Timing.waitCondition(() -> {
            General.sleep(500);
            return Inventory.getAll().length > 3;
        }, General.random(1000, 2000));

        PohLogsToPlanks.totalPlanks += Inventory.find(PohLogsToPlanks.logType).length;

        // Now close bank screen
        while (!Banking.close());
    }
}
