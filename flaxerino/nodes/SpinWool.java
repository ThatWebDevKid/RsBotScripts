package scripts.flaxerino.nodes;

import org.tribot.api.General;
import org.tribot.api.Timing;
import org.tribot.api2007.*;
import org.tribot.api2007.types.RSInterface;
import org.tribot.api2007.types.RSObject;
import org.tribot.api2007.types.RSTile;
import scripts.API.Antiban;
import scripts.API.InterfaceHandler;
import scripts.API.Node;
import scripts.API.ObjectHandler;
import scripts.dax_api.api_lib.DaxWalker;


public class SpinWool extends Node {
    public void printStatus() {
        General.println("Spinning Wool");
    }
    public boolean validate() {
        return Skills.getActualLevel(Skills.SKILLS.CRAFTING) < 10 && Inventory.find("Wool").length > 0;
    }

    /**
     * Updates the variables used in ABC2
     *
     */
    private long waitTime;
    private long averageWaitTime;
    private long totalWaitTime;
    private long totalInstances;
    private void updateStats(long startTime) {
        waitTime = System.currentTimeMillis() - startTime;
        totalInstances++;
        totalWaitTime+=waitTime;
        averageWaitTime = totalWaitTime / totalInstances;
    }


    public void execute() {
        if (DaxWalker.walkTo(new RSTile(3209, 3213, 1))) {
            RSObject[] spinningWheel = Objects.find(3, "Spinning wheel");
            General.sleep(1000,3000);
            if (ObjectHandler.interactWithObjectWithoutAnimation(spinningWheel, "Spin")) {
                spinningWheel[0].adjustCameraTo();
                boolean ballOfWoolInterfaceSub = Timing.waitCondition(() -> {
                    General.sleep(500);
                    return Interfaces.isInterfaceSubstantiated(Interfaces.get(270, 14));
                }, General.random(3000, 4000));

                if (ballOfWoolInterfaceSub) {
                    RSInterface ballOfWoolInterface = Interfaces.get(270, 14);
                    if (ballOfWoolInterface.click()) {
                        long startedTime = System.currentTimeMillis();
                        RSObject[] staircase = Objects.findNearest(ObjectHandler.DEFAULT_DISTANCE, "Staircase");
                        Antiban.setHoverAndMenuOpenBooleans();
                        Antiban.generateSupportingTrackerInfo((int) averageWaitTime);

                        boolean isSpinning = true;
                        while (isSpinning) {
                            General.sleep(500, 2000);
                            Antiban.checkTimedActions();
                            Antiban.executeHoverOrMenuOpenObject(staircase);

                            boolean noMoreWool = Inventory.find("Wool").length == 0;
                            if (InterfaceHandler.clickHereToContinue()) {
                                while (InterfaceHandler.clickHereToContinue());
                                isSpinning = false;
                            } else if (noMoreWool) {
                                isSpinning = false;
                            }
                        }
                        updateStats(startedTime);
                        Antiban.generateAndSleep((int) waitTime);
                    }
                }

            }
        }
    }
}
