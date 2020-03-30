package scripts.minerino.nodes;

import org.tribot.api.General;
import org.tribot.api2007.Inventory;
import org.tribot.api2007.Player;
import scripts.API.Node;
import scripts.dax_api.api_lib.DaxWalker;
import scripts.minerino.Minerino;
import scripts.minerino.constants.Constants;

public class Traversal extends Node {
    private static boolean shouldGoToBank, shouldGoToMine;
    public void printStatus(){
        General.println("WALKING");
    }
    public boolean validate() {
        shouldGoToBank = Inventory.isFull() && Minerino.miningMethod.equalsIgnoreCase("Banking");
        shouldGoToMine = Inventory.getAll().length <= 2;
        return shouldGoToBank || shouldGoToMine;
    }
    public void execute() {
        if (shouldGoToBank) {
            DaxWalker.walkToBank(Minerino.bankingLocation);
            return;
        }

        if (shouldGoToMine) {
            DaxWalker.walkTo(Minerino.miningLocation.getRandomTile());
            return;
        }
    }
}
