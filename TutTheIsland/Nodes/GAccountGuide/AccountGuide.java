package scripts.TutTheIsland.Nodes.GAccountGuide;

import org.tribot.api.General;
import org.tribot.api.Timing;
import org.tribot.api2007.*;
import org.tribot.api2007.types.RSInterface;
import org.tribot.api2007.types.RSNPC;
import org.tribot.api2007.types.RSObject;
import scripts.API.InterfaceHandler;
import scripts.API.NPCHandler;
import scripts.API.ObjectHandler;
import scripts.API.TabsHandler;
import scripts.TutTheIsland.API.Node;
import scripts.TutTheIsland.TutTheIsland;
import scripts.TutTheIsland.Utils.Constants;
import scripts.dax_api.api_lib.DaxWalker;

import java.util.Arrays;

public class AccountGuide extends Node {
    public void printStatus(){
        General.println("AT THE ACCOUNT AREA STAGE");
    }
    public boolean validate() {
        return Arrays.stream(Constants.ACCOUNT_AREA_STATES).anyMatch(stage -> stage == TutTheIsland.gameState);
    }
    public void execute() {
        RSInterface chatInterface = Interfaces.get(263,1,0);
        RSNPC[] accountGuide = NPCs.findNearest(Constants.ACCOUNT_GUIDE);
        RSNPC[] banker = NPCs.findNearest(Constants.BANKER);

        if (InterfaceHandler.interfaceContainsText(chatInterface, "close the bank and click on the indicated poll booth.")) {
            if (Banking.isBankScreenOpen()) { Banking.close(); }
            RSObject[] pollBooth = Objects.find(ObjectHandler.DEFAULT_DISTANCE, "Poll booth");
            ObjectHandler.interactWithObject(pollBooth, "");
            while (InterfaceHandler.clickHereToContinue());
            return;
        }

        if (InterfaceHandler.interfaceContainsText(chatInterface, "are run periodically to let the Old School RuneScape community vote on how the game should")) {
            InterfaceHandler.clickInterface(Interfaces.get(310,2,11));
            DaxWalker.walkTo(Constants.ACCOUNT_GUIDE_AREA.getRandomTile());
            return;
        }

        if (InterfaceHandler.interfaceContainsText(chatInterface, "Just click on him to hear what he's got to say.")) {
            NPCHandler.talkToNPC(accountGuide);
             return;
        }

        if (InterfaceHandler.interfaceContainsText(chatInterface, "on the flashing icon to open your Account Management")) {
            TabsHandler.openTab(GameTab.TABS.ACCOUNT);
            return;
        }

        if (InterfaceHandler.interfaceContainsText(chatInterface, "Talk to the Account Guide to learn more.")) {
            NPCHandler.talkToNPC(accountGuide);
            return;
        }

        NPCHandler.talkToNPC(banker);
    }
}
