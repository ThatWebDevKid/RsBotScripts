package scripts.pohlogstoplanks.nodes;

import org.tribot.api.General;
import org.tribot.api2007.*;
import scripts.API.Node;
import scripts.API.ObjectHandler;
import scripts.pohlogstoplanks.Constants;
import scripts.pohlogstoplanks.PohLogsToPlanks;

public class Teleport extends Node {
    public void printStatus() {
        General.println("Teleporting!");
    }
    public boolean validate() {
        return (Inventory.find(PohLogsToPlanks.logType).length > 0 && Constants.LUMBRIDGE_AREA.contains(Player.getPosition()) ) || (Objects.findNearest(ObjectHandler.DEFAULT_DISTANCE, "Portal").length > 0 &&  Inventory.find(PohLogsToPlanks.logType).length <= 0);

    }
    public void execute() {

        if (Inventory.find(PohLogsToPlanks.logType).length > 0 && Constants.LUMBRIDGE_AREA.contains(Player.getPosition())) {
            if (!Magic.selectSpell("Teleport to House")) {
                return;
            }
        }

        if (Objects.findNearest(ObjectHandler.DEFAULT_DISTANCE, "Portal").length > 0 && Inventory.find(PohLogsToPlanks.logType).length <= 0) {
            if (!Magic.selectSpell("Lumbridge Teleport")){
                return;
            }
        }

        General.sleep(1000, 1500);
    }
}
