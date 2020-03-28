package scripts.TutTheIsland.Nodes.CMasterChef;

import org.tribot.api.General;
import org.tribot.api2007.*;
import org.tribot.api2007.types.RSInterface;
import scripts.API.InterfaceHandler;
import scripts.API.InventoryHandler;
import scripts.API.NPCHandler;
import scripts.API.ObjectHandler;
import scripts.TutTheIsland.API.Node;
import scripts.TutTheIsland.TutTheIsland;
import scripts.TutTheIsland.Utils.Constants;

import java.util.Arrays;

public class MasterChef extends Node {
    public void printStatus(){
        General.println("AT THE MASTER CHEF STAGE");
    }
    public boolean validate() {
        return  Arrays.stream(Constants.MASTER_CHEF_STATES).anyMatch(stage -> stage == TutTheIsland.gameState);
    }
    public void execute() {
        RSInterface chatInterface = Interfaces.get(263,1,0);

        if (InterfaceHandler.interfaceContainsText(chatInterface, "To make dough you must mix flour with water.")) {
            InventoryHandler.clickOnInventoryItem("Pot of flour");
            InventoryHandler.clickOnInventoryItem("Bucket of water", () -> Inventory.find("Bread dough").length > 0);
            return;
        }

        if (InterfaceHandler.interfaceContainsText(chatInterface, "Now you have made the dough, you can bake it into some bread.")) {
            ObjectHandler.clickOnObject("Range");
            InterfaceHandler.clickHereToContinue();
            return;
        }

        NPCHandler.talkToNPC(Constants.MASTER_CHEF);
    }
}
