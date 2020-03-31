package scripts.TutTheIsland.Nodes.FCombatArea;

import org.tribot.api.General;
import org.tribot.api2007.*;
import org.tribot.api2007.types.RSInterface;
import org.tribot.api2007.types.RSItem;
import org.tribot.api2007.types.RSNPC;
import scripts.API.*;
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
        RSNPC[] combatInstructor = NPCs.findNearest(Constants.COMBAT_INSTRUCTOR);
        RSNPC[] giantRat;

        if (InterfaceHandler.interfaceContainsText(chatInterface, "You now have access to a new interface. Click on the flashing icon of a man,")) {
            TabsHandler.openTab(GameTab.TABS.EQUIPMENT);
            return;
        }

        if (InterfaceHandler.interfaceContainsText(chatInterface, "This is your worn inventory. Here you can see what items you have equipped.")) {
            RSInterface shieldAndHelmet = Interfaces.get(387,1);
            TabsHandler.openTab(GameTab.TABS.EQUIPMENT);
            InterfaceHandler.clickInterface(shieldAndHelmet);
            return;
        }

        if (InterfaceHandler.interfaceContainsText(chatInterface, "Click your dagger to equip it.")) {
            RSItem[] bronzeDagger = Inventory.find("Bronze Dagger");
            ItemHandler.clickOnInventoryItem(bronzeDagger, "");
            return;
        }

        if (InterfaceHandler.interfaceContainsText(chatInterface, "You're now holding your dagger.")) {
            InterfaceHandler.clickInterface(Interfaces.get(84,4));
        }

        if (InterfaceHandler.interfaceContainsText(chatInterface, "Try this out now by swapping your dagger for the sword and shield that the combat instructor gave you.")) {
            RSItem[] bronzeSword = Inventory.find("Bronze sword");
            RSItem[] woodenShield = Inventory.find("Wooden shield");
            ItemHandler.clickOnInventoryItem(bronzeSword, "");
            ItemHandler.clickOnInventoryItem(woodenShield, "");
            return;
        }

        if (InterfaceHandler.interfaceContainsText(chatInterface, "on the flashing crossed swords icon to open the combat interface.")) {
            TabsHandler.openTab(GameTab.TABS.COMBAT);
            return;
        }

        if (InterfaceHandler.interfaceContainsText(chatInterface, "Click on the gates to continue.")) {
            DaxWalker.walkTo(Constants.RATPIT.getRandomTile());
            return;
        }

        if (InterfaceHandler.interfaceContainsText(chatInterface, "It's time to slay some rats!") ||
        InterfaceHandler.interfaceContainsText(chatInterface, "While you are fighting you will see a bar over your head.")) {
            if (!Combat.isUnderAttack()) {
                giantRat = NPCs.findNearest("Giant rat");
                NPCHandler.attackNPC(giantRat, true);
                return;
            }
        }

        if (InterfaceHandler.interfaceContainsText(chatInterface, "He will give you your next task.")) {
            DaxWalker.walkTo(Constants.COMBAT_AREA.getRandomTile());
            InterfaceHandler.clickHereToContinue();
        }

        if (InterfaceHandler.interfaceContainsText(chatInterface, "Now you have a bow and some arrows.")) {
            giantRat = NPCs.findNearest("Giant rat");
            RSItem[] shortbow = Inventory.find("Shortbow");
            RSItem[] bronzeArrow = Inventory.find("Bronze arrow");
            ItemHandler.clickOnInventoryItem(shortbow, "");
            ItemHandler.clickOnInventoryItem(bronzeArrow, "");
            NPCHandler.interactWithNPC(giantRat, "Attack", false);
            return;
        }

        NPCHandler.talkToNPC(combatInstructor);
    }
}
