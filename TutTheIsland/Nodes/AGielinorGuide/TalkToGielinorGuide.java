package scripts.TutTheIsland.Nodes.AGielinorGuide;

import org.tribot.api.General;
import org.tribot.api2007.*;
import org.tribot.api2007.types.RSInterface;
import scripts.API.InterfaceHandler;
import scripts.API.NPCHandler;
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
        boolean shouldClickOptionsMenu = InterfaceHandler.interfaceContainsText(clickOptionInterface, "click on the flashing spanner icon found at the bottom right of your screen. This will display your options menu.");

        if (shouldClickOptionsMenu) {
            if (!GameTab.TABS.OPTIONS.isOpen()) {
                GameTab.TABS.OPTIONS.open();
            }
            General.sleep(2000,5000);
        }

        // Choose Experience
        RSInterface[] experienceWithRSInterfaces = {Interfaces.get(219,1,1), Interfaces.get(219,1,2), Interfaces.get(219,1,3)};
        for (RSInterface experiencePlayerInterface: experienceWithRSInterfaces) {
            if (InterfaceHandler.interfaceContainsText(experiencePlayerInterface, "I am an experienced player.")) {
                InterfaceHandler.clickInterface(experiencePlayerInterface);
                InterfaceHandler.clickHereToContinue();
//                NPCChat.clickContinue(true);
            }
        }

        // Talk to Gielinor Guide
//        if (NPCs.find(Constants.GIELINOR_GUIDE).length > 0) {
////            RSNPC gielinorGuide = NPCs.find(Constants.GIELINOR_GUIDE)[0];
////            if (gielinorGuide.isOnScreen() && gielinorGuide.isClickable()) {
////                gielinorGuide.click();
////                General.sleep(2000,4000);
////
////                while (NPCChat.clickContinue(true)) {
////                    General.sleep(1000,2000);
////                }
////            }
////        }

            NPCHandler.talkToNPC(Constants.GIELINOR_GUIDE);
            General.sleep(1000, 2000);

    }
}