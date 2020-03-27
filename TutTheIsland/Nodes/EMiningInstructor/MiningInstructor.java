package scripts.TutTheIsland.Nodes.EMiningInstructor;

import org.tribot.api.General;
import org.tribot.api2007.Game;
import org.tribot.api2007.GameTab;
import org.tribot.api2007.Interfaces;
import org.tribot.api2007.types.RSInterface;
import scripts.API.InterfaceHandler;
import scripts.API.NPCHandler;
import scripts.API.ObjectHandler;
import scripts.TutTheIsland.API.Node;
import scripts.TutTheIsland.TutTheIsland;
import scripts.TutTheIsland.Utils.Constants;

import java.util.Arrays;

public class MiningInstructor extends Node {
    public void printStatus(){
        General.println("AT THE MINING AREA STAGE");
    }
    public boolean validate() {
        return Arrays.stream(Constants.MINING_INSTRUCTOR_STATES).anyMatch(stage -> stage == TutTheIsland.gameState);
    }
    public void execute() {
        RSInterface chatInterface = Interfaces.get(263,1,0);

        if (InterfaceHandler.interfaceContainsText(chatInterface, "Now that you have some tin ore, you just need some copper.")) {
            ObjectHandler.clickOnObject(10079);
        }

        if (InterfaceHandler.interfaceContainsText(chatInterface, "To mine a rock, all you need to do is click on it.")) {
            ObjectHandler.clickOnObject(10080);
        }

        if (InterfaceHandler.interfaceContainsText(chatInterface, "You now have some tin ore and some copper ore.")) {
            ObjectHandler.clickOnObject("Furnace");
        }

        if (InterfaceHandler.interfaceContainsText(chatInterface, "click on the anvil, or alternatively use the bar on it.") ||
                InterfaceHandler.interfaceContainsText(chatInterface, "an anvil to open the smithing menu,")) {
            ObjectHandler.clickOnObject("Anvil");
        }

        if (InterfaceHandler.interfaceContainsText(chatInterface, "Now you have the smithing menu open, you will see a list of all")) {
            RSInterface daggerInterface = Interfaces.get(312,9,2);
            if (InterfaceHandler.interfaceIsVisibleOnScreen(daggerInterface)) {
                InterfaceHandler.clickInterface(daggerInterface);
                General.sleep(2000,3000);
            }
            return;
        }

        NPCHandler.talkToNPC(Constants.MINING_INSTRUCTOR);
        General.sleep(2000,4000);
    }
}
