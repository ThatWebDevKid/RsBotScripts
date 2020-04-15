package scripts.fruitytootie.nodes;

import org.tribot.api.General;
import org.tribot.api2007.WorldHopper;
import scripts.API.Node;
import scripts.fruitytootie.FruityTootie;

public class WorldHop extends Node {
    public void printStatus() {
        General.println("NEED TO WORLD HOP!");
    }

    public boolean validate() {
        return FruityTootie.needWorldHop;
    }

    public void execute() {
        WorldHopper.changeWorld(WorldHopper.getRandomWorld(true));
        FruityTootie.needWorldHop = false;
    }

}
