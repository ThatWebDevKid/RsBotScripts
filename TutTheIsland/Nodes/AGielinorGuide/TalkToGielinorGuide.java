package scripts.TutTheIsland.Nodes.AGielinorGuide;

import org.tribot.api.General;
import org.tribot.api2007.*;
import org.tribot.api2007.types.RSInterface;
import scripts.API.InterfaceHandler;
import scripts.API.NPCHandler;
import scripts.API.TabsHandler;
import scripts.TutTheIsland.API.Node;
import scripts.TutTheIsland.TutTheIsland;
import scripts.TutTheIsland.Utils.Constants;

import java.util.Arrays;

public class TalkToGielinorGuide extends Node {
    public void printStatus() {
        General.println("TUTTHEISLAND: TALKING TO GIELINOR GUIDE");
    }
    public boolean validate() {
        return Arrays.stream(Constants.GIELINOR_GUIDE_GAME_STATES).anyMatch(stage -> stage == TutTheIsland.gameState);
    }
    public void execute() {
        // Click on the options menu
        RSInterface clickOptionInterface = Interfaces.get(263,1,0);
        boolean shouldClickOptionsMenu = InterfaceHandler.interfaceContainsText(clickOptionInterface, "This will display your options menu.");

        if (shouldClickOptionsMenu) {
            TabsHandler.openTab(GameTab.TABS.OPTIONS);
        }

        // Choose Experience
        RSInterface[] experienceWithRSInterfaces = {Interfaces.get(219,1,1), Interfaces.get(219,1,2), Interfaces.get(219,1,3)};
        for (RSInterface experiencePlayerInterface: experienceWithRSInterfaces) {
            if (InterfaceHandler.interfaceContainsText(experiencePlayerInterface, "I am an experienced player.")) {
                InterfaceHandler.clickInterface(experiencePlayerInterface);
                InterfaceHandler.clickHereToContinue();
            }
        }


        NPCHandler.talkToNPC(Constants.GIELINOR_GUIDE);
    }
}