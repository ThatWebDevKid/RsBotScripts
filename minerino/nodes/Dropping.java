package scripts.minerino.nodes;

import org.tribot.api.General;
import org.tribot.api2007.Inventory;
import scripts.API.Node;
import scripts.minerino.Minerino;

public class Dropping extends Node {
    public void printStatus() {
        General.println("Dropping!!!");
    }
    public boolean validate() {
        return Minerino.miningMethod.equalsIgnoreCase("Dropping") && Inventory.isFull();
    }
    public void execute() {
        Inventory.dropAllExcept();
    }
}
