package scripts.TutTheIsland.Nodes.CMasterChef;

import org.tribot.api.General;
import org.tribot.api.Timing;
import org.tribot.api2007.*;
import org.tribot.api2007.types.RSInterface;
import org.tribot.api2007.types.RSItem;
import org.tribot.api2007.types.RSNPC;
import org.tribot.api2007.types.RSObject;
import scripts.API.InterfaceHandler;
import scripts.API.ItemHandler;
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
        RSNPC[] masterChef = NPCs.findNearest(Constants.MASTER_CHEF);

        if (InterfaceHandler.interfaceContainsText(chatInterface, "To make dough you must mix flour with water.")) {
            RSItem[] potOfFlour = Inventory.find("Pot of flour");
            RSItem[] bucketOfWater = Inventory.find("Bucket of water");
            if (ItemHandler.clickOnInventoryItem(potOfFlour, "") && ItemHandler.clickOnInventoryItem(bucketOfWater, "")) {
                Timing.waitCondition(() -> {
                    General.sleep(500);
                    return Inventory.find("Bread dough").length > 0;
                }, General.random(3000, 5000));
            }
            return;
        }

        if (InterfaceHandler.interfaceContainsText(chatInterface, "Now you have made the dough, you can bake it into some bread.")) {
            RSObject[] range = Objects.find(ObjectHandler.DEFAULT_DISTANCE, "Range");
            if (ObjectHandler.interactWithObject(range, "")) {
                InterfaceHandler.clickHereToContinue();
            }
            return;
        }

        NPCHandler.talkToNPC(masterChef);
    }
}
