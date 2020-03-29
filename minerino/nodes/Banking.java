package scripts.minerino.nodes;

import org.tribot.api.General;
import org.tribot.api2007.Inventory;
import scripts.API.Node;
import scripts.API.ObjectHandler;
import scripts.minerino.Minerino;

public class Banking extends Node {
    public void printStatus() {
        General.println("Banking!!!");
    }
    public boolean validate() {
        return org.tribot.api2007.Banking.isInBank() && Inventory.isFull() && Minerino.miningMethod.equalsIgnoreCase("Banking");
    }
    public void execute() {
        if (org.tribot.api2007.Banking.openBank()) {
            org.tribot.api2007.Banking.depositAll();
        }
    }
}

