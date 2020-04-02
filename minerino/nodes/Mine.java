package scripts.minerino.nodes;

import org.tribot.api.General;
import org.tribot.api2007.GameTab;
import org.tribot.api2007.Inventory;
import org.tribot.api2007.Objects;
import org.tribot.api2007.Player;
import org.tribot.api2007.types.RSObject;
import scripts.API.Node;
import scripts.API.ObjectHandler;
import scripts.API.TabsHandler;
import scripts.minerino.Minerino;

public class Mine extends Node {
    public void printStatus() {
        General.println("Mining!!!");
    }
    public boolean validate() {
        return !Inventory.isFull() && Player.getPosition().distanceTo(Minerino.miningLocation.getRandomTile()) < 10;
    }
    public void execute() {
        if (Player.getAnimation() == -1) {
            RSObject[] ironOre = Objects.findNearest(ObjectHandler.DEFAULT_DISTANCE, Minerino.ore);
            TabsHandler.openTab(GameTab.TABS.INVENTORY);
            ObjectHandler.interactWithObject(ironOre);
        }
    }
}
