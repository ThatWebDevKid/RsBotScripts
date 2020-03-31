package scripts.TutTheIsland.Nodes.BSurvivalExpert;

import org.tribot.api.General;
import org.tribot.api.Timing;
import org.tribot.api2007.*;
import org.tribot.api2007.types.*;
import scripts.API.*;
import scripts.TutTheIsland.API.Node;
import scripts.TutTheIsland.TutTheIsland;
import scripts.TutTheIsland.Utils.Constants;
import scripts.dax_api.api_lib.DaxWalker;

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
            RSObject[] fires = Objects.findNearest(ObjectHandler.DEFAULT_DISTANCE, "Fire");

            // If we are standing on a fire then we we will walk somewhere random to hopefully not be standing on a fire
            if (fires.length > 0 && Player.getRSPlayer().getPosition().distanceTo(fires[0].getPosition()) == 0) {
                DaxWalker.walkTo(Constants.SURVIVAL_AREA.getRandomTile());
            } else {
                // If we aren't standing on a fire, lets make the fire!
                if (ItemHandler.clickOnInventoryItem(tinderBox, "")) {
                    ItemHandler.clickOnInventoryItem(logs, "");
                }
            }

            return;
        }

        if (InterfaceHandler.interfaceContainsText(chatInterface, "Now it's time to get cooking.")) {
            RSItem[] rawShrimps = Inventory.find("Raw shrimps");
            RSObject[] fires = Objects.findNearest(ObjectHandler.DEFAULT_DISTANCE, "Fire");

            // If a fire exist and we successfully click on the shrimp
            if (fires.length > 0 && ItemHandler.clickOnInventoryItem(rawShrimps, "")) {
                ObjectHandler.interactWithObject(fires, "Use Raw Shrimps ->");
            } else {
                // We need to make fire
                RSObject[] trees = Objects.findNearest(ObjectHandler.DEFAULT_DISTANCE, "Tree");
                ObjectHandler.interactWithObject(trees, "");
                RSItem[] tinderBox = Inventory.find("Tinderbox");
                RSItem[] logs = Inventory.find("Logs");
                fires = Objects.findNearest(ObjectHandler.DEFAULT_DISTANCE, "Fire");

                // If we are standing on a fire then we we will walk somewhere random to hopefully not be standing on a fire
                if (fires.length > 0 && Player.getRSPlayer().getPosition().distanceTo(fires[0].getPosition()) == 0) {
                    DaxWalker.walkTo(Constants.SURVIVAL_AREA.getRandomTile());
                } else {
                    // If we aren't standing on a fire, lets make the fire!
                    if (ItemHandler.clickOnInventoryItem(tinderBox, "")) {
                        ItemHandler.clickOnInventoryItem(logs, "");
                    }
                }
            }
            return;
        }

        NPCHandler.talkToNPC(survivalExpert);
    }
}
