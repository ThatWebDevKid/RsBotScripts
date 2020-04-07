package scripts.fletcherino.nodes;

import org.tribot.api.General;
import org.tribot.api.Timing;
import org.tribot.api2007.Banking;
import org.tribot.api2007.GrandExchange;
import org.tribot.api2007.Inventory;
import org.tribot.api2007.NPCs;
import org.tribot.api2007.types.RSItem;
import org.tribot.api2007.types.RSNPC;
import scripts.API.NPCHandler;
import scripts.fletcherino.Node;
import scripts.fletcherino.Task;
import scripts.fletcherino.graphics.GUIController;
import scripts.minerino.Minerino;

public class BuyMaterial extends Node {
    private boolean dontHaveEnoughLogs;
    private boolean dontHaveEnoughBows;
    private boolean dontHaveBowStrings;
    private String log;
    public void printStatus(Task task) {
        General.println("Buying materials!");
    }
    public boolean validate(Task task) {
//        String getWhatToDo = task.getWhatToDo();
//        String itemToDo = task.getItemToDo();
//        log = GUIController.mymap.get(itemToDo);
//        boolean buyMaterialsIfLow = task.getbuySuppliesIfLow();
//        boolean openBank = Banking.openBank();
        // If we are cutting, make sure we have enough logs to cut
        // If we are cutting and don't have enough logs
//        dontHaveEnoughLogs = (getWhatToDo.equalsIgnoreCase("Cut") && (openBank && Banking.find(log).length <= 0) && Inventory.find(log).length <= 0);


        // If we are stringing, make sure we have enough of the long/short bows + bow string
        // If we are stringing and don't have any bows or don't have any bow strings
//        // We will need to buy
//        dontHaveEnoughBows =  (getWhatToDo == "String" && // We are stringing
//                (Banking.find(itemToDo).length <= 0 && Inventory.find(itemToDo).length <= 0)  // Don't have any bows
//              );
//
//        dontHaveBowStrings = getWhatToDo == "String" && (Banking.find("Wooden shield").length <= 0 && Inventory.find("Wooden shield").length <= 0);
//        General.println("dontHaveEnoughBows? " + dontHaveEnoughBows);
//        General.println("dontHaveBowStrings? " + dontHaveBowStrings);
        return false;
    }
    public void execute(Task task) {
        if (dontHaveEnoughLogs) {
            // If we don't have enough logs, we will buy 100 of those logs
            General.println(("DONT HAVE ENOUGH LOGS SO NEED BUY!!"));
            if (Banking.openBank() && Banking.depositAll() >= 0) {
                General.sleep(3000,5000);
                if (Banking.withdraw(-1, "Coins") && Banking.close()) {
                    RSNPC[] grandExchangeClerk = NPCs.findNearest("Grand Exchange Clerk");
                    General.println(grandExchangeClerk[0].isClickable());
                    General.println(NPCHandler.interactWithNPC(grandExchangeClerk, "Exchange", false));
                    General.sleep(3000, 5000);
                    while(!GrandExchange.goToSelectionWindow(true));
                    General.sleep(3000, 5000);
                    //                General.println("Trying to buy logs!");
//                General.println(GrandExchange.offer(log, 20, 5, false));
                }
            }
        }
    }
}
