package scripts.minerino.nodes;

import org.tribot.api.General;
import org.tribot.api2007.GameTab;
import org.tribot.api2007.Inventory;
import org.tribot.api2007.Player;
import scripts.API.Node;
import scripts.API.ObjectHandler;
import scripts.API.TabsHandler;
import scripts.minerino.Minerino;
import scripts.minerino.constants.Constants;

public class Mine extends Node {
    public void printStatus() {
        General.println("Mining!!!");
    }
    public boolean validate() {
        return !Inventory.isFull() && Player.getPosition().distanceTo(Minerino.miningLocation.getRandomTile()) < 10;
    }
    public void execute() {
        if (Player.getAnimation() == -1) {
            TabsHandler.openTab(GameTab.TABS.INVENTORY);
            ObjectHandler.interactWithObject(Minerino.ore, "Mine");
        }
    }
}
