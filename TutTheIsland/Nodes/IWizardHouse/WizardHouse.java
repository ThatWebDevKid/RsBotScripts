package scripts.TutTheIsland.Nodes.IWizardHouse;

import org.tribot.api.General;
import org.tribot.api2007.*;
import org.tribot.api2007.types.RSInterface;
import scripts.API.InterfaceHandler;
import scripts.API.NPCHandler;
import scripts.TutTheIsland.API.Node;
import scripts.TutTheIsland.TutTheIsland;
import scripts.TutTheIsland.Utils.Constants;

import java.util.Arrays;

public class WizardHouse extends Node {
    public void printStatus(){
        General.println("AT THE WIZARD HOUSE AREA STAGE");
    }
    public boolean validate() {
        return Arrays.stream(Constants.WIZARD_HOUSE_STATES).anyMatch(stage -> stage == TutTheIsland.gameState);
    }
    public void execute() {
        RSInterface chatInterface = Interfaces.get(263,1,0);

        if (InterfaceHandler.interfaceContainsText(chatInterface, "up the magic interface by clicking on the flashing icon.")) {
            GameTab.TABS.MAGIC.open();
            return;
        }

        if (InterfaceHandler.interfaceContainsText(chatInterface, "Look for the Wind Strike spell in your magic interface.")) {
            Magic.selectSpell("Wind Strike");
            NPCHandler.attackNPC("Chicken", 15000, false);
            return;
        }

        if (InterfaceHandler.interfaceContainsText(Interfaces.get(219,1,0), "Do you want to go to the mainland?")) {
            InterfaceHandler.clickInterface(Interfaces.get(219,1,1));
        }

        if (NPCChat.getOptions() != null && NPCChat.getOptions()[2].contains("No, I'm not planning to do that.")) {
            NPCChat.selectOption(NPCChat.getOptions()[2], true);
        }

        NPCHandler.talkToNPC(Constants.MAGIC_INSTRUCTOR);
        General.sleep(2000,4000);
    }
}
