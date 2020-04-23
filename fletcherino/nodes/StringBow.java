package scripts.fletcherino.nodes;

import org.tribot.api.General;
import org.tribot.api.Timing;
import org.tribot.api2007.*;
import org.tribot.api2007.types.RSItem;
import org.tribot.api2007.types.RSNPC;
import org.tribot.api2007.types.RSPlayer;
import scripts.API.Antiban;
import scripts.API.InterfaceHandler;
import scripts.API.ItemHandler;
import scripts.fletcherino.Fletcherino;
import scripts.fletcherino.Node;
import scripts.fletcherino.Task;
import scripts.wastedbro.api.rsitem_services.GrandExchange;

public class StringBow extends Node {
    public void printStatus(Task task) {
        General.println("Stringing your bows!!");
    }
    public boolean validate(Task task) {
        if (GrandExchange.getWindowState() != null) {
            while (!GrandExchange.close()) {
                General.sleep(100, 300);
            }
        }


        return task.getWhatToDo().equalsIgnoreCase("String") && (!task.ranOutOfBowsAndBowStrings() || !task.reachedDesiredLevel() || task.getSuppliesToFletch() > 0);
    }

    /**
     * Updates the variables used in ABC2
     *
     */
    private long lastStringingWaitTime;
    private long averageStringingWaitTime;
    private long totalStringingWaitTime;
    private long totalStringingInstances;
    private void updateStringingdStastics(long startStringingTime) {
        lastStringingWaitTime = System.currentTimeMillis() - startStringingTime;
        totalStringingInstances++;
        totalStringingWaitTime+=lastStringingWaitTime;
        averageStringingWaitTime = totalStringingWaitTime / totalStringingInstances;
    }


    public void execute(Task task) {
        if (!Banking.isBankScreenOpen()) {
            if (Banking.openBank()) {
                if(!Timing.waitCondition(() -> {
                    General.sleep(500, 1000);
                    return Banking.isBankScreenOpen();
                }, General.random(5000, 8000))) {
                    return;
                }
            }
        }

        if (Banking.depositAll() >= 0) {
            if (!Timing.waitCondition(() -> {
                General.sleep(500, 1000);
                return Inventory.getAll().length <= 0;
            }, General.random(5000, 8000))) {
                return;
            }
        }

        int amountToWithdraw = task.getSuppliesToFletch() == -1 ? 14 : task.getSuppliesToFletch();
        amountToWithdraw = Math.min(14, amountToWithdraw);
        if (Banking.withdraw(amountToWithdraw, "Bow string")) {
            Timing.waitCondition(() -> {
                General.sleep(500, 1000);
                return Inventory.find("Bow string").length > 0;
            }, General.random(5000, 8000));
        }


        if (Banking.withdraw(amountToWithdraw, task.getItemToDo())) {
            Timing.waitCondition(() -> {
                General.sleep(500, 1000);
                return Inventory.find(task.getItemToDo()).length > 0;
            }, General.random(5000, 8000));
        }

        if (Banking.close()) {
            Timing.waitCondition(() -> {
                General.sleep(500, 1000);
                return !Banking.isBankScreenOpen();
            }, General.random(5000, 8000));
        }


        RSItem[] bowStrings = Inventory.find("Bow string");
        RSItem[] bows = Inventory.find(task.getItemToDo());
        RSNPC[] bankers = NPCs.findNearest("Banker");
        task.setSluppliesToFletch(task.getSuppliesToFletch() - Math.min(bows.length, bowStrings.length));
        Fletcherino.totalItemsFletched += Math.min(bowStrings.length, bows.length);
        while (bowStrings.length > 0 && bows.length > 0) {
            if (GrandExchange.getWindowState() != null) {
                while (!GrandExchange.close()) {
                    General.sleep(100, 300);
                }
            }


            if (ItemHandler.clickOnInventoryItem(bowStrings)) {
                General.sleep(500, 1000);
                if (ItemHandler.clickOnInventoryItem(bows)) {

                    // Click on the thing we want to string ex. string maple longbow
                    if (Timing.waitCondition(() -> {
                        General.sleep(500, 1000);
                        return InterfaceHandler.clickInterface(Interfaces.get(270, 14));
                    }, General.random(3000, 6000))) {
                        long startedStringing = System.currentTimeMillis();

                        Antiban.setHoverBooleans();
                        Antiban.generateSupportingTrackerInfo((int) averageStringingWaitTime);

                        General.sleep(500, 1000);

                        if (Timing.waitCondition(() -> {
                            General.sleep(500, 1000);
                            Antiban.checkTimedActions();
                            RSPlayer player = Player.getRSPlayer();
                            return player != null && player.getAnimation() != -1;
                        }, General.random(20000, 25000))) {
                            Timing.waitCondition(() -> {
                                General.sleep(500, 1000);

                                Antiban.checkTimedActions();
                                Antiban.executeHoverOpenNPC(bankers, false);

                                boolean clickedNext = false;
                                while (InterfaceHandler.clickHereToContinue()) {
                                    clickedNext = true;
                                }
                                boolean noMoreToString = Inventory.find("Bow string").length <= 0 || Inventory.find(task.getItemToDo()).length <= 0;
                                return clickedNext || noMoreToString;
                            }, 60000);

                            updateStringingdStastics(startedStringing);
                            Antiban.generateAndSleep((int) lastStringingWaitTime);
                        }
                    }
                }
            }

            bowStrings = Inventory.find("Bow string");
            bows = Inventory.find(task.getItemToDo());
        }
    }
}
