package scripts.flaxerino.nodes;

import org.tribot.api.General;
import org.tribot.api.Timing;
import org.tribot.api2007.Banking;
import org.tribot.api2007.Inventory;
import org.tribot.api2007.Skills;
import scripts.API.Node;
import scripts.dax_api.api_lib.DaxWalker;
import scripts.dax_api.api_lib.models.RunescapeBank;
import scripts.flaxerino.Flaxerino;


public class Bank extends Node {
    public void printStatus() {
        General.println("Banking!!");
    }
    public boolean validate() {
        boolean inventoryOnlyHasBallOfWool = (Inventory.getAll().length - Inventory.find("Ball of wool").length) == 0;
        boolean inventoryOnlyHasBowString = (Inventory.getAll().length - Inventory.find("Bow string").length) == 0;
        return inventoryOnlyHasBallOfWool || inventoryOnlyHasBowString || Inventory.getAll().length == 0 || Skills.getActualLevel(Skills.SKILLS.CRAFTING) == 10;
    }
    public void execute() {
        if (DaxWalker.walkToBank(RunescapeBank.LUMBRIDGE_TOP)) {
            if (Banking.openBank()) {
                if (Timing.waitCondition(() -> {
                    General.sleep(500);
                    return Banking.isBankScreenOpen();
                },  General.random(2000, 3000))) {
                    Banking.depositAll();

                    if (Timing.waitCondition(() -> {
                        General.sleep(500);
                        return Inventory.getAll().length == 0;
                    }, General.random(2000, 3000))) {
                        if (Skills.getActualLevel(Skills.SKILLS.CRAFTING) < 10 ) {
                            Flaxerino.withdrewMaterialSuccessful = Banking.withdraw(0, "Wool");
                            if (Flaxerino.withdrewMaterialSuccessful) {
                                Timing.waitCondition(() -> {
                                    General.sleep(500);
                                    return Inventory.find("Wool").length > 0;
                                }, General.random(2000, 3000));
                            }
                        } else {
                            Flaxerino.withdrewMaterialSuccessful = Banking.withdraw(0, "Flax");
                            if (Flaxerino.withdrewMaterialSuccessful) {
                                General.println("Successfully withdrew Flax");
                                boolean flaxInInventory = Timing.waitCondition(() -> {
                                    General.sleep(500);
                                    return Inventory.find("Flax").length > 0;
                                }, General.random(3000, 5000));

                                if (flaxInInventory) {
                                    Banking.close();
                                }
                            }
                        }
                    }

                }
            }
        }
    }
}
