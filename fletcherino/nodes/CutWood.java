package scripts.fletcherino.nodes;

import org.tribot.api.General;
import org.tribot.api.Timing;
import org.tribot.api2007.*;
import org.tribot.api2007.types.RSInterface;
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

import java.util.Arrays;

public class CutWood extends Node {
    public void printStatus(Task task) {
        General.println("Cutting wood!");
    }
    public boolean validate(Task task) {
        if (GrandExchange.getWindowState() != null) {
            while (!GrandExchange.close()) {
                General.sleep(100, 300);
            }
        }

        return task.getWhatToDo().equalsIgnoreCase("Cut") && (!task.ranOutOfWoodForCutting() || !task.reachedDesiredLevel() || task.getSuppliesToFletch() > 0);
    }

    /**
     * Updates the variables used in ABC2
     *
     */
    private long lastCuttingWaitTime;
    private long averageCuttingWaitTime;
    private long totalCuttingWaitTime;
    private long totalCuttingInstances;
    private void updateCuttingWoodStastics(long startCutWoodTime) {
        lastCuttingWaitTime = System.currentTimeMillis() - startCutWoodTime;
        totalCuttingInstances++;
        totalCuttingWaitTime+=lastCuttingWaitTime;
        averageCuttingWaitTime = totalCuttingWaitTime / totalCuttingInstances;
    }

    public void execute(Task task) {
        if (!Banking.isBankScreenOpen()) {
            if (Banking.openBank()) {
                if(!Timing.waitCondition(() -> {
                    General.sleep(250, 500);
                    return Banking.isBankScreenOpen();
                }, General.random(5000, 8000))) {
                    return;
                }
            }
        }


        if (Banking.depositAllExcept("Knife") > 0) {
            Timing.waitCondition(() -> {
                General.sleep(250, 500);
                return Inventory.getAll().length <= 1;
            }, General.random(5000, 8000));
        }



        if (Inventory.find("Knife").length <= 0) {
            if(Banking.withdraw(1, "Knife")) {
                Timing.waitCondition(() -> {
                    General.sleep(250, 500);
                    return Inventory.find("Knife").length > 0;
                }, General.random(5000, 8000));
            }
        }

        int amountToWithdraw = task.getSuppliesToFletch() == -1 ? 27 : task.getSuppliesToFletch();
        amountToWithdraw = Math.min(27, amountToWithdraw);
        if (Banking.withdraw(amountToWithdraw, task.getLogType())) {
            Timing.waitCondition(() -> {
                General.sleep(250, 500);
                return Inventory.find(task.getLogType()).length > 0;
            }, General.random(3000, 6000));
        }



        if (Banking.close()) {
            Timing.waitCondition(() -> {
                General.sleep(250, 500);
                return !Banking.isBankScreenOpen();
            }, General.random(5000, 8000));
        }

        RSItem[] knife = Inventory.find("Knife");
        RSItem[] logs = Inventory.find(task.getLogType());
        RSNPC[] bankers = NPCs.findNearest("Banker");
        task.setSluppliesToFletch(task.getSuppliesToFletch() - logs.length);
        Fletcherino.totalItemsFletched += logs.length;
        while (logs.length > 0) {
            if (GrandExchange.getWindowState() != null) {
                while (!GrandExchange.close()) {
                    General.sleep(100, 300);
                }
            }

            if (ItemHandler.clickOnInventoryItem(knife)) {
                if (ItemHandler.clickOnInventoryItem(logs)) {
                    Timing.waitCondition(() -> {
                        General.sleep(50, 100);
                        RSInterface[] whatWouldYouLikeToMakeInterfaces2 = {
                                Interfaces.get(270, 14),
                                Interfaces.get(270, 15),
                                Interfaces.get(270, 16),
                                Interfaces.get(270, 17),
                        };
                        boolean interfacesInstantiated = Arrays.stream(whatWouldYouLikeToMakeInterfaces2).anyMatch(runescapeInterface -> Interfaces.isInterfaceSubstantiated(runescapeInterface));
                        return interfacesInstantiated;
                    }, General.random(5000, 8000));
                }
                RSInterface[] whatWouldYouLikeToMakeInterfaces = {
                        Interfaces.get(270, 14),
                        Interfaces.get(270, 15),
                        Interfaces.get(270, 16),
                        Interfaces.get(270, 17),
                        Interfaces.get(270, 18),
                };

                for (final RSInterface runescapeInterface : whatWouldYouLikeToMakeInterfaces) {
                    if (InterfaceHandler.interfaceContainsTextInComponentName(runescapeInterface, task.getItemToDo().toLowerCase()) ||
                            InterfaceHandler.interfaceContainsTextInComponentName(runescapeInterface, task.getItemToDo()) ||
                            (task.getItemToDo().toLowerCase().contains("shafts") && InterfaceHandler.interfaceContainsTextInComponentName(runescapeInterface, "shafts")) ||
                            (task.getItemToDo().toLowerCase().contains("stock") && InterfaceHandler.interfaceContainsTextInComponentName(runescapeInterface, "stock"))

                    ) {
                        if (InterfaceHandler.clickInterface(runescapeInterface)) {
                            long startedCutting = System.currentTimeMillis();

                            Antiban.setHoverBooleans();
                            Antiban.generateSupportingTrackerInfo((int) averageCuttingWaitTime);

                            if (Timing.waitCondition(() -> {
                                General.sleep(50, 100);
                                RSPlayer player = Player.getRSPlayer();
                                return player != null && player.getAnimation() != -1;
                            }, General.random(20000, 25000))) {
                                Timing.waitCondition(() -> {
                                    General.sleep(50, 100);

                                    Antiban.checkTimedActions();
                                    Antiban.executeHoverOpenNPC(bankers, false);

                                    boolean clickedNext = false;
                                    while (InterfaceHandler.clickHereToContinue()) {
                                        clickedNext = true;
                                    }
                                    boolean noMoreLogs = Inventory.find(task.getLogType()).length <= 0;
                                    return clickedNext || noMoreLogs;
                                }, 60000);

                                updateCuttingWoodStastics(startedCutting);
                                Antiban.generateAndSleep((int) lastCuttingWaitTime);
                            }

                        }
                    }
                }

                logs = Inventory.find(task.getLogType());
            }
        }
    }
}
