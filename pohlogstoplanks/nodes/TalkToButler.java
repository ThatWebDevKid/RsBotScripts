package scripts.pohlogstoplanks.nodes;


import org.tribot.api.General;
import org.tribot.api.Timing;
import org.tribot.api.input.Keyboard;
import org.tribot.api2007.*;
import org.tribot.api2007.types.RSInterface;
import org.tribot.api2007.types.RSNPC;
import scripts.API.*;
import scripts.pohlogstoplanks.PohLogsToPlanks;

import java.awt.event.KeyEvent;

public class TalkToButler extends Node {
    public void printStatus() {
        General.println("Talking with Butler!");
    }
    public boolean validate() {
        return Objects.findNearest(ObjectHandler.DEFAULT_DISTANCE, "Portal").length > 0 && Inventory.find(PohLogsToPlanks.logType).length > 0;

    }
    public void execute() {
        // First we need to open our settings
        while(!GameTab.TABS.OPTIONS.isOpen()) { Keyboard.pressKeys(KeyEvent.VK_F10); }
        if (!TabsHandler.openTab(GameTab.TABS.OPTIONS)) {
            if (!Interfaces.isInterfaceSubstantiated(370, 19)) {
                return;
            }
        }

        RSInterface viewHouseOptions = Interfaces.get(261, 100);
        while (!InterfaceHandler.clickInterface(viewHouseOptions) && !Interfaces.isInterfaceSubstantiated(370, 19));

        Timing.waitCondition(() -> {
            General.sleep(50);
            return Interfaces.isInterfaceSubstantiated(370, 19);
        }, General.random(3000, 4000));


        RSInterface callServant = Interfaces.get(370, 19);
        while (!InterfaceHandler.clickInterface(callServant));

        RSNPC[] servant = NPCs.findNearest("Demon butler");
        if (!Timing.waitCondition(() -> NPCChat.getOptions() != null || NPCChat.getMessage() != null, General.random(2000, 3000))) {
            RSInterface closeHouseOptions = Interfaces.get(370, 21);
            while (Interfaces.isInterfaceSubstantiated(closeHouseOptions)) {
                General.sleep(50);
                InterfaceHandler.clickInterface(closeHouseOptions);
            }

            while (!NPCHandler.interactWithNPC(servant, "Talk-to")) {
                General.sleep(50, 100);
            }
        }

        if (PohLogsToPlanks.needsReset) {
            Keyboard.pressKeys(KeyEvent.VK_ESCAPE);
            boolean npcClicked = false;
            while (!npcClicked) {
                Timing.waitCondition(() -> ItemHandler.clickOnInventoryItem(Inventory.find(PohLogsToPlanks.logType)), General.random(5000, 6000));
                npcClicked =  NPCHandler.interactWithNPC(servant, "Use " + PohLogsToPlanks.logType + " ->");
            }
            PohLogsToPlanks.needsReset = false;
        }


        Timing.waitCondition(() -> NPCChat.getOptions() != null || NPCChat.getMessage() != null, General.random(5000, 10000));
        RSInterface enterAmountInterface = Interfaces.get(162,44);
        while ((NPCChat.getOptions() != null || NPCChat.getMessage() != null) || Interfaces.isInterfaceSubstantiated(enterAmountInterface)) {
            if (Interfaces.isInterfaceSubstantiated(enterAmountInterface)) {
                Keyboard.typeSend(String.valueOf(Inventory.find(PohLogsToPlanks.logType).length));
            } else if (NPCChat.getOptions() != null) {
                Keyboard.typeKeys('1');
                InterfaceHandler.clickHereToContinue();
            } else if (NPCChat.getMessage() != null) {
                if (NPCChat.getMessage().equalsIgnoreCase("Oh dear, I can't afford that.")) {
                    PohLogsToPlanks.scriptRunning = false;
                }
                Keyboard.pressKeys(KeyEvent.VK_SPACE);
            }
            Timing.waitCondition(() -> (NPCChat.getOptions() != null || NPCChat.getMessage() != null) || Interfaces.isInterfaceSubstantiated(enterAmountInterface), General.random(1000, 1200));
        }

    }
}
