package scripts.TutTheIsland.Nodes.BSurvivalExpert;

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

public class SurvivalExpert extends Node {
    public void printStatus(){
        General.println("AT SURVIVAL EXPERT STAGE");
    }
    public boolean validate() {
        return Arrays.stream(Constants.SURVIVAL_AREA_STATES).anyMatch(stage -> stage == TutTheIsland.gameState);
    }
    public void execute() {
        RSInterface chatInterface = Interfaces.get(263,1,0);

        if (InterfaceHandler.interfaceContainsText(chatInterface, "To view the item you've been given,")) {
            GameTab.TABS.INVENTORY.open();
            General.sleep(2000,5000);
            return;
        }

        if (InterfaceHandler.interfaceContainsText(chatInterface, "To start fishing, just click on the sparkling fishing spot,")) {
            NPCHandler.clickOnNPC(Constants.FISHING_SPOT);
            return;
        }

        if (InterfaceHandler.interfaceContainsText(chatInterface, "Click on the flashing bar graph icon")) {
            GameTab.TABS.STATS.open();
            General.sleep(2000,5000);
            return;
        }

        if (InterfaceHandler.interfaceContainsText(chatInterface, "However, you require a fire to do that which means you need some logs.")) {
            ObjectHandler.clickOnObject("Tree");
            return;
        }

        if (InterfaceHandler.interfaceContainsText(chatInterface, "click on the tinderbox in your inventory. Then, with the tinderbox highlighted,")) {
            if (!GameTab.TABS.INVENTORY.isOpen()) {
                GameTab.TABS.INVENTORY.open();
                General.sleep(1000);
            }
            InventoryHandler.clickOnInventoryItem("Tinderbox");
            InventoryHandler.clickOnInventoryItem("Logs");
            return;
        }

        if (InterfaceHandler.interfaceContainsText(chatInterface, "Now it's time to get cooking.")) {
            if (!GameTab.TABS.INVENTORY.isOpen()) {
                GameTab.TABS.INVENTORY.open();
                General.sleep(1000);
            }
            InventoryHandler.clickOnInventoryItem("Raw shrimps");
            ObjectHandler.clickOnObject("Fire");
            return;
        }

        NPCHandler.talkToNPC(Constants.SURVIVAL_EXPERT);
        General.sleep(2000,4000);
    }
}
