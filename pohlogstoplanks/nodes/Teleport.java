package scripts.pohlogstoplanks.nodes;

import org.tribot.api.General;
import org.tribot.api.Timing;
import org.tribot.api.input.Keyboard;
import org.tribot.api2007.*;
import org.tribot.api2007.types.RSItem;
import scripts.API.Antiban;
import scripts.API.Node;
import scripts.API.ObjectHandler;
import scripts.pohlogstoplanks.Constants;
import scripts.pohlogstoplanks.PohLogsToPlanks;

import java.awt.event.KeyEvent;

public class Teleport extends Node {
    int numOflogs;
    boolean atBankingLocation;
    boolean atHome;
    public void printStatus() {
        General.println("Teleporting!");
    }
    public boolean validate() {
        numOflogs = Inventory.find(PohLogsToPlanks.logType).length;
        atBankingLocation = PohLogsToPlanks.bank.contains(Player.getPosition());
        atHome = Objects.findNearest(ObjectHandler.DEFAULT_DISTANCE, "Portal").length > 0;
        return ( numOflogs > 0 &&  atBankingLocation) || (atHome &&  numOflogs <= 0);

    }

    public void execute() {
        RSItem[] lawRunes = Inventory.find("Law rune");

        if (lawRunes.length <= 0) {
            PohLogsToPlanks.scriptRunning = false;
            return;
        }

        if (Banking.isBankScreenOpen()) {
            while (!Banking.close());
        }

        Keyboard.pressKeys(KeyEvent.VK_F6);
        if (numOflogs > 0 && atBankingLocation) {
            if (Magic.selectSpell("Teleport to House")) {
                General.sleep(750);
                Timing.waitCondition(() -> {
                    Antiban.getInstance().leaveGame();
                    return Player.getAnimation() == -1;
                }, General.random(2000, 3000));
            }
        }

        if (atHome && numOflogs <= 0) {
            Keyboard.pressKeys(KeyEvent.VK_F6);
            boolean spellSuccessfull;
            if (PohLogsToPlanks.bank.contains(Constants.LUMBRIDGE_AREA.getRandomTile())) {
                spellSuccessfull = Magic.selectSpell("Lumbridge Teleport");
            } else {
                spellSuccessfull = Magic.selectSpell("Camelot Teleport");
            }

            if (spellSuccessfull){
                General.sleep(750);
                Timing.waitCondition(() -> {
                    Antiban.getInstance().leaveGame();
                    return Player.getAnimation() == -1;
                }, General.random(2000, 3000));
            }
        }
    }
}
