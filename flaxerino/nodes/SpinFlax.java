package scripts.flaxerino.nodes;

import org.tribot.api.General;

import org.tribot.api.Timing;
import org.tribot.api2007.Interfaces;
import org.tribot.api2007.Inventory;
import org.tribot.api2007.Objects;
import org.tribot.api2007.Skills;
import org.tribot.api2007.types.RSInterface;
import org.tribot.api2007.types.RSObject;
import org.tribot.api2007.types.RSTile;
import scripts.API.Antiban;
import scripts.API.InterfaceHandler;
import scripts.API.Node;
import scripts.API.ObjectHandler;
import scripts.dax_api.api_lib.DaxWalker;


public class SpinFlax extends Node {
    public void printStatus() {
        General.println("Spinning Flax!!!");
    }
    public boolean validate() {
        return Skills.getActualLevel(Skills.SKILLS.CRAFTING) >= 10 && Inventory.find("Flax").length > 0;
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
                boolean bowStringInterfaceSub = Timing.waitCondition(() -> {
                    General.sleep(500);
                    return Interfaces.isInterfaceSubstantiated(Interfaces.get(270, 16));
                }, General.random(3000, 4000));

                if (bowStringInterfaceSub) {
                    RSInterface bowStringInterface = Interfaces.get(270, 16);
                    if (bowStringInterface.click()) {

                        long startedTime = System.currentTimeMillis();
                        RSObject[] staircase = Objects.findNearest(ObjectHandler.DEFAULT_DISTANCE, "Staircase");
                        Antiban.setHoverAndMenuOpenBooleans();
                        Antiban.generateSupportingTrackerInfo((int) averageWaitTime);
                        boolean isSpinning = true;

                        while (isSpinning) {
                            General.sleep(500, 2000);
                            Antiban.checkTimedActions();
                            Antiban.executeHoverOrMenuOpenObject(staircase);
                            boolean noMoreFlax = Inventory.find("Flax").length == 0;
                            if (InterfaceHandler.clickHereToContinue()) {
                                while (InterfaceHandler.clickHereToContinue());
                                isSpinning = false;
                            } else if (noMoreFlax) {
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
