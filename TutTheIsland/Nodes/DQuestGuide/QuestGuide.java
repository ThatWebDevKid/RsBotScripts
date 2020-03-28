package scripts.TutTheIsland.Nodes.DQuestGuide;

import org.tribot.api.General;
import org.tribot.api2007.Game;
import org.tribot.api2007.GameTab;
import org.tribot.api2007.Interfaces;
import org.tribot.api2007.types.RSInterface;
import scripts.API.*;
import scripts.TutTheIsland.API.Node;
import scripts.TutTheIsland.TutTheIsland;
import scripts.TutTheIsland.Utils.Constants;

import java.util.Arrays;

public class QuestGuide extends Node {
    public void printStatus(){
        General.println("AT THE QUEST AREA STAGE");
    }
    public boolean validate() {
        return Arrays.stream(Constants.QUEST_GUIDE_STATES).anyMatch(stage -> stage == TutTheIsland.gameState);
    }
    public void execute() {
        RSInterface chatInterface = Interfaces.get(263,1,0);

        if (InterfaceHandler.interfaceContainsText(chatInterface, "Click on the flashing icon to the left of your inventory.")) {
            TabsHandler.openTab(GameTab.TABS.QUESTS);
            return;
        }

        NPCHandler.talkToNPC(Constants.QUEST_GUIDE);
    }
}
