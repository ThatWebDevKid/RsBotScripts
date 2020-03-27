package scripts.TutTheIsland.Nodes.FCombatArea;

import org.tribot.api.General;
import org.tribot.api2007.Game;
import org.tribot.api2007.GameTab;
import org.tribot.api2007.Interfaces;
import org.tribot.api2007.NPCChat;
import org.tribot.api2007.types.RSInterface;
import scripts.API.InterfaceHandler;
import scripts.API.InventoryHandler;
import scripts.API.NPCHandler;
import scripts.TutTheIsland.API.Node;
import scripts.TutTheIsland.TutTheIsland;
import scripts.TutTheIsland.Utils.Constants;
import scripts.dax_api.api_lib.DaxWalker;

import java.util.Arrays;

public class CombatArea extends Node {
    public void printStatus(){
        General.println("AT THE COMBAT AREA STAGE");
    }
    public boolean validate() {
        return Arrays.stream(Constants.COMBAT_AREA_STATES).anyMatch(stage -> stage == TutTheIsland.gameState);
    }
    public void execute() {
        RSInterface chatInterface = Interfaces.get(263,1,0);

        if (InterfaceHandler.interfaceContainsText(chatInterface, "You now have access to a new interface. Click on the flashing icon of a man,")) {
            GameTab.TABS.EQUIPMENT.open();
            return;
        }

        if (InterfaceHandler.interfaceContainsText(chatInterface, "This is your worn inventory. Here you can see what items you have equipped.")) {
            RSInterface shieldAndHelmet = Interfaces.get(387,1);
            InterfaceHandler.clickInterface(shieldAndHelmet);
            return;
        }

        if (InterfaceHandler.interfaceContainsText(chatInterface, "Click your dagger to equip it.")) {
            InventoryHandler.clickOnInventoryItem("Bronze dagger");
            return;
        }

        if (InterfaceHandler.interfaceContainsText(chatInterface, "You're now holding your dagger.")) {
            InterfaceHandler.clickInterface(Interfaces.get(84,4));
        }

        if (InterfaceHandler.interfaceContainsText(chatInterface, "Try this out now by swapping your dagger for the sword and shield that the combat instructor gave you.")) {
            InventoryHandler.clickOnInventoryItem("Bronze sword");
            InventoryHandler.clickOnInventoryItem("Wooden shield");
            return;
        }

        if (InterfaceHandler.interfaceContainsText(chatInterface, "on the flashing crossed swords icon to open the combat interface.")) {
            GameTab.TABS.COMBAT.open();
            return;
        }

        if (InterfaceHandler.interfaceContainsText(chatInterface, "Click on the gates to continue.")) {
            DaxWalker.walkTo(Constants.RATPIT.getRandomTile());
            return;
        }

        if (InterfaceHandler.interfaceContainsText(chatInterface, "It's time to slay some rats!")) {
            NPCHandler.attackNPC("Giant rat", 15000, true);
            return;
        }

        if (InterfaceHandler.interfaceContainsText(chatInterface, "He will give you your next task.")) {
            DaxWalker.walkTo(Constants.COMBAT_AREA.getRandomTile());
            InterfaceHandler.clickHereToContinue();
        }

        if (InterfaceHandler.interfaceContainsText(chatInterface, "Now you have a bow and some arrows.")) {
            if (!GameTab.TABS.INVENTORY.isOpen()) {
                GameTab.TABS.INVENTORY.open();
                General.sleep(1000);
            }
            InventoryHandler.clickOnInventoryItem("Shortbow");
            InventoryHandler.clickOnInventoryItem("Bronze arrow");
            NPCHandler.attackNPC("Giant rat", 15000, false);
            return;
        }

        NPCHandler.talkToNPC(Constants.COMBAT_INSTRUCTOR);
        General.sleep(2000,4000);
    }
}
