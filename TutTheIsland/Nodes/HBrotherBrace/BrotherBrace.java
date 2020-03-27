package scripts.TutTheIsland.Nodes.HBrotherBrace;

import org.tribot.api.General;
import org.tribot.api2007.Game;
import org.tribot.api2007.GameTab;
import org.tribot.api2007.Interfaces;
import org.tribot.api2007.types.RSInterface;
import scripts.API.InterfaceHandler;
import scripts.API.NPCHandler;
import scripts.TutTheIsland.API.Node;
import scripts.TutTheIsland.TutTheIsland;
import scripts.TutTheIsland.Utils.Constants;

import java.util.Arrays;

public class BrotherBrace extends Node {
    public void printStatus(){
        General.println("AT THE BROTHER BRACE AREA STAGE");
    }
    public boolean validate() {
        return Arrays.stream(Constants.BROTHER_BRACE_STATES).anyMatch(stage -> stage == TutTheIsland.gameState);
    }
    public void execute() {
        RSInterface chatInterface = Interfaces.get(263,1,0);

        if (InterfaceHandler.interfaceContainsText(chatInterface, "Click on the flashing icon to open the Prayer menu.")) {
            GameTab.TABS.PRAYERS.open();
            return;
        }

        if (InterfaceHandler.interfaceContainsText(chatInterface, "on the flashing face icon to open your friends and ignore lists.")) {
            GameTab.TABS.FRIENDS.open();
            return;
        }

        NPCHandler.talkToNPC(Constants.BROTHER_BRACE);
        General.sleep(2000,4000);
    }
}
