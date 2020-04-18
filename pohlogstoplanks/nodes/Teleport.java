package scripts.pohlogstoplanks.nodes;

import org.tribot.api.General;
import org.tribot.api.Timing;
import org.tribot.api2007.*;
import org.tribot.api2007.types.RSArea;
import org.tribot.api2007.types.RSTile;
import scripts.API.Node;
import scripts.API.ObjectHandler;
import scripts.API.TabsHandler;
import scripts.pohlogstoplanks.Constants;

public class Teleport extends Node {
    public void printStatus() {
        General.println("Teleporting!");
    }
    public boolean validate() {
        return (Inventory.find("Oak logs").length > 0 && Constants.LUMBRIDGE_AREA.contains(Player.getPosition()) ) || (Objects.findNearest(ObjectHandler.DEFAULT_DISTANCE, "Portal").length > 0 &&  Inventory.find("Oak logs").length <= 0);

    }
    public void execute() {

        if (Inventory.find("Oak logs").length > 0 && Constants.LUMBRIDGE_AREA.contains(Player.getPosition())) {
            if (!Magic.selectSpell("Teleport to House")) {
                return;
            }
        }

        if (Objects.findNearest(ObjectHandler.DEFAULT_DISTANCE, "Portal").length > 0 && Inventory.find("Oak logs").length <= 0) {
            if (!Magic.selectSpell("Lumbridge Teleport")){
                return;
            }
        }

        General.sleep(1000, 1500);
    }
}
