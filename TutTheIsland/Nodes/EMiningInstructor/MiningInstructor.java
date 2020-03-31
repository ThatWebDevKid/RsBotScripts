package scripts.TutTheIsland.Nodes.EMiningInstructor;

import org.tribot.api.General;
import org.tribot.api2007.*;
import org.tribot.api2007.types.*;
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
        RSInterface anvilInterface = Interfaces.get(312,1,1);
        RSNPC[] miningInstructor = NPCs.findNearest(Constants.MINING_INSTRUCTOR);

        if (InterfaceHandler.interfaceContainsText(chatInterface, "Now that you have some tin ore, you just need some copper.")) {
            RSObject[] copperRocks = Objects.find(ObjectHandler.DEFAULT_DISTANCE, Constants.COPPER_ROCKS);
            ObjectHandler.interactWithObject(copperRocks, "");
            return;
        }

        if (InterfaceHandler.interfaceContainsText(chatInterface, "To mine a rock, all you need to do is click on it.")) {
            RSObject[] tinRocks = Objects.find(ObjectHandler.DEFAULT_DISTANCE, Constants.TIN_ROCKS);
            ObjectHandler.interactWithObject(tinRocks, "");
            return;
        }

        if (InterfaceHandler.interfaceContainsText(chatInterface, "You now have some tin ore and some copper ore.")) {
            RSObject[] furnace = Objects.find(ObjectHandler.DEFAULT_DISTANCE, "Furnace");
            ObjectHandler.interactWithObject(furnace, "");
        }

        if (InterfaceHandler.interfaceContainsText(chatInterface, "click on the anvil, or alternatively use the bar on it.") ||
                InterfaceHandler.interfaceContainsText(chatInterface, "an anvil to open the smithing menu,")) {
            RSObject[] anvil = Objects.find(ObjectHandler.DEFAULT_DISTANCE, "Anvil");
            ObjectHandler.interactWithObject(anvil, "");
            return;
        }

        if (InterfaceHandler.interfaceContainsText(anvilInterface, "What would you like to make?")) {
            RSInterface daggerInterface = Interfaces.get(312,9,2);
            InterfaceHandler.clickInterface(daggerInterface);
            return;
        }

        NPCHandler.talkToNPC(miningInstructor);
    }
}
