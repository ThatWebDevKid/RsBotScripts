package scripts.TutTheIsland.Nodes.BSurvivalExpert;

import org.tribot.api.General;
import org.tribot.api.Timing;
import org.tribot.api2007.*;
import org.tribot.api2007.types.RSInterface;
import org.tribot.api2007.types.RSItem;
import org.tribot.api2007.types.RSNPC;
import org.tribot.api2007.types.RSObject;
import scripts.API.*;
import scripts.TutTheIsland.API.Node;
import scripts.TutTheIsland.TutTheIsland;
import scripts.TutTheIsland.Utils.Constants;

import java.util.Arrays;

public class SurvivalExpert extends Node {
    public void printStatus(){
        General.println("AT SURVIVAL EXPERT STAGE");
    }
    public boolean validate() {
        return Arrays.stream(Constants.SURVIVAL_AREA_STATES).anyMatch(stage -> stage == TutTheIsland.gameState);
    }

    public void execute() {
        RSInterface chatInterface = Interfaces.get(263,1,0);
        RSNPC[] survivalExpert = NPCs.findNearest(Constants.SURVIVAL_EXPERT);

        if (InterfaceHandler.interfaceContainsText(chatInterface, "To view the item you've been given,")) {
            TabsHandler.openTab(GameTab.TABS.INVENTORY);
            return;
        }

        if (InterfaceHandler.interfaceContainsText(chatInterface, "To start fishing, just click on the sparkling fishing spot,")) {
            RSNPC[] fishingSpots = NPCs.findNearest(Constants.FISHING_SPOT);
            NPCHandler.interactWithNPC(fishingSpots, "", true);
            return;
        }

        if (InterfaceHandler.interfaceContainsText(chatInterface, "Click on the flashing bar graph icon")) {
            TabsHandler.openTab(GameTab.TABS.STATS);
            return;
        }

        if (InterfaceHandler.interfaceContainsText(chatInterface, "However, you require a fire to do that which means you need some logs.")) {
            RSObject[] trees = Objects.findNearest(ObjectHandler.DEFAULT_DISTANCE, "Tree");
            ObjectHandler.interactWithObject(trees, "");
            return;
        }

        if (InterfaceHandler.interfaceContainsText(chatInterface, "click on the tinderbox in your inventory. Then, with the tinderbox highlighted,")) {
            RSItem[] tinderBox = Inventory.find("Tinderbox");
            RSItem[] logs = Inventory.find("Logs");
            if (ItemHandler.clickOnInventoryItem(tinderBox, "")) {
                ItemHandler.clickOnInventoryItem(logs, "");
            }
            return;
        }

        if (InterfaceHandler.interfaceContainsText(chatInterface, "Now it's time to get cooking.")) {
            RSItem[] rawShrimps = Inventory.find("Raw shrimps");
            if (ItemHandler.clickOnInventoryItem(rawShrimps, "")) {
                RSObject[] fires = Objects.findNearest(ObjectHandler.DEFAULT_DISTANCE, "Fire");
                ObjectHandler.interactWithObject(fires, "Use");
            }
            return;
        }

        NPCHandler.talkToNPC(survivalExpert);
    }
}
