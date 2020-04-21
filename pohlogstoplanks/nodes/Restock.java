package scripts.pohlogstoplanks.nodes;

import org.tribot.api.General;
import org.tribot.api.Timing;
import org.tribot.api2007.*;
import org.tribot.api2007.types.RSInterface;
import org.tribot.api2007.types.RSItem;
import org.tribot.api2007.types.RSNPC;
import scripts.API.*;
import scripts.pohlogstoplanks.PohLogsToPlanks;
import scripts.wastedbro.api.rsitem_services.GrandExchange;

public class Restock extends Node {
    public void printStatus() {
        General.println("Restocking supplies!");
    }
    public boolean validate() {
        return Inventory.find("Coins")[0].getStack() < 40000 || PohLogsToPlanks.noMoreLogs;
    }
    public void execute() {
        // Figure out what we need to restock on!
        RSItem[] lawRunes = Inventory.find("Law rune");
        int lawRunesToBuy = 200;
        int logsToBuy = 1444;
        if (lawRunes.length > 0) {
            lawRunesToBuy -= lawRunes[0].getStack();
        }
        RSNPC[] grandExchangeClerk = NPCs.findNearest("Grand Exchange Clerk");

        if (grandExchangeClerk.length <= 0) {
            Equipment.find("Ring of wealth (5)", "Ring of wealth (4)", "Ring of wealth (3)" ,"Ring of wealth (2)" ,"Ring of wealth (1)")[0].click("Grand Exchange");
            General.sleep(3000);
        }


        Banking.openBank();
        if (!Timing.waitCondition(() -> {
            General.sleep(500);
            return Banking.isBankScreenOpen();
        }, General.random(1000, 2000))) {
            return;
        }

        if (Inventory.find(PohLogsToPlanks.logType).length > 0) {
            Banking.deposit(0, PohLogsToPlanks.logType);
            General.sleep(1000,2000);
        }


        if (Equipment.find("Ring of wealth").length > 0) {
            Banking.withdraw(1, "Ring of wealth (5)");
            General.sleep(1000,2000);
        }

        if (!BankHandler.isNotedOn()) {
            BankHandler.setNoted(true);
            General.sleep(1000);
            Banking.withdraw(0, PohLogsToPlanks.plankType);
            General.sleep(1000);
            Banking.close();
        }

        if (GrandExchange.getWindowState() == null) {
            grandExchangeClerk = NPCs.findNearest("Grand Exchange Clerk");
            NPCHandler.interactWithNPC(grandExchangeClerk, "Exchange");
            Timing.waitCondition(() -> {
                General.sleep(500);
                return GrandExchange.getWindowState() != null;
            }, General.random(2000, 3000));
        }
        // Sell all planks
        GrandExchange.offer(PohLogsToPlanks.plankType, 2194, -1, true);

        General.sleep(10000);
        RSInterface collectAllInterface = Interfaces.get(465, 6, 1);
        InterfaceHandler.clickInterface(collectAllInterface);
        General.sleep(1000, 2000);

        //  Buy more logs
        if (PohLogsToPlanks.noMoreLogs) {
            GrandExchange.offer(PohLogsToPlanks.logType, 538, logsToBuy, false);
            PohLogsToPlanks.noMoreLogs = false;
            PohLogsToPlanks.needsReset = true;
        }

        if (lawRunesToBuy > 0) {
            GrandExchange.offer("Law rune", 200, lawRunesToBuy, false);
        }

        General.sleep(10000);
        collectAllInterface = Interfaces.get(465, 6, 1);
        InterfaceHandler.clickInterface(collectAllInterface);
        General.sleep(1000, 2000);
        GrandExchange.close(true);
        General.sleep(2000, 3000);
        ItemHandler.clickOnInventoryItem(Inventory.find("Ring of wealth (5)"));
        General.sleep(1000, 2000);
        Magic.selectSpell("Lumbridge Teleport");
    }
}
