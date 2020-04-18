package scripts.pohlogstoplanks.nodes;

import javafx.scene.control.Tab;
import javafx.scene.input.KeyCode;
import org.tribot.api.General;
import org.tribot.api.Timing;
import org.tribot.api.input.Keyboard;
import org.tribot.api2007.*;
import org.tribot.api2007.types.RSInterface;
import org.tribot.api2007.types.RSNPC;
import scripts.API.*;
import scripts.pohlogstoplanks.Constants;

import java.awt.event.KeyEvent;

public class TalkToButler extends Node {
    public void printStatus() {
        General.println("Talking with Butler!");
    }
    public boolean validate() {
        return Objects.findNearest(ObjectHandler.DEFAULT_DISTANCE, "Portal").length > 0 && Inventory.find("Oak logs").length > 0;

    }
    public void execute() {
        // First we need to open our settings
        if (!TabsHandler.openTab(GameTab.TABS.OPTIONS)) {
            return;
        }

        RSInterface viewHouseOptions = Interfaces.get(261, 100);
        while (!InterfaceHandler.clickInterface(viewHouseOptions)) {
            General.sleep(500);
        }

        General.sleep(500, 1000);

        Timing.waitCondition(() -> {
            General.sleep(500);
            return Interfaces.isInterfaceSubstantiated(370, 19);
        }, General.random(2000, 3000));


        General.sleep(500, 1000);

        RSInterface callServant = Interfaces.get(370, 19);
        while (!InterfaceHandler.clickInterface(callServant)) {
            General.sleep(500);
        }

        General.sleep(500, 1000);

        RSNPC[] servant = NPCs.findNearest("Demon butler");
        if (NPCChat.getOptions() == null && NPCChat.getMessage() == null) {
            if (!NPCHandler.interactWithNPC(servant, "Talk-to")) {
                return;
            }
        }

        General.sleep(500, 1000);

        while (NPCChat.getOptions() != null || NPCChat.getMessage() != null ) {
            if (NPCChat.getOptions() != null) {
                Keyboard.typeKeys('1');
                InterfaceHandler.clickHereToContinue();
            } else if (NPCChat.getMessage() != null) {
                Keyboard.pressKeys(KeyEvent.VK_SPACE);
            }
            General.sleep(1200, 1500);
        }

    }
}
