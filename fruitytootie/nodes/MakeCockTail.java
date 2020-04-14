package scripts.fruitytootie.nodes;

import org.apache.commons.collections4.IterableMap;
import org.tribot.api.General;
import org.tribot.api.Timing;
import org.tribot.api.input.Keyboard;
import org.tribot.api2007.*;
import org.tribot.api2007.types.RSGroundItem;
import org.tribot.api2007.types.RSInterface;
import org.tribot.api2007.types.RSItem;
import org.tribot.api2007.types.RSNPC;
import scripts.API.InterfaceHandler;
import scripts.API.ItemHandler;
import scripts.API.NPCHandler;
import scripts.API.Node;
import scripts.dax_api.api_lib.DaxWalker;
import scripts.fruitytootie.FruityTootie;
import scripts.fruitytootie.constants.Constants;

import java.awt.event.KeyEvent;

public class MakeCockTail extends Node {
    public void printStatus() {
        General.println("Making Cock Tails");
    }

    public boolean validate() {
        boolean haveMaterialsInInventory = Inventory.find("Pineapple").length >= 5 &&
                ( Inventory.find("Lemon").length >= 10 ||
                        ( Inventory.find("Lemon").length >= 5 && Inventory.find("Lemon slices").length >= 5 )
                ) &&
                Inventory.find("Cocktail glass").length >= 5;

        return haveMaterialsInInventory;
    }

    public void execute() {
        GameTab.open(GameTab.TABS.INVENTORY);

        RSItem[] knife = Inventory.find("Knife");
        RSItem[] lemons = Inventory.find("Lemon");
        RSItem[] lemonSlices = Inventory.find("Lemon slices");
        General.println("We have " + lemonSlices.length + " lemon slices in our inventory");

        // Do we need to make the lemon slices? If yes then make it, otherwise skip to making the cocktails
        if (lemonSlices.length <= 0) {
            if (ItemHandler.clickOnInventoryItem(knife)) {
                General.sleep(500, 800);
                if (ItemHandler.clickOnInventoryItem(lemons)) {
                    General.sleep(500, 800);
                    if (Timing.waitCondition(() -> {
                        General.sleep(500, 1000);
                        return Interfaces.isInterfaceSubstantiated(270, 11);
                    }, General.random(2000, 3000))) {
                        RSInterface xInterface = Interfaces.get(270, 11);
                        RSInterface fiveInterface = Interfaces.get(270,8, 9);
                        RSInterface spaceInterface = Interfaces.get(270,13,0);
                        boolean canPressSpace = InterfaceHandler.interfaceContainsText(spaceInterface, "Space");
                        boolean fiveAlreadySelected = InterfaceHandler.interfaceContainsText(fiveInterface, "<col=ffffff>5</col>");


                        if (!fiveAlreadySelected) {
                            if (InterfaceHandler.clickInterface(xInterface)) {
                                Keyboard.typeKeys('5');
                                General.sleep(500, 700);
                                Keyboard.pressEnter();
                                General.sleep(500, 700);
                            }
                        }

                        if (canPressSpace) {
                            Keyboard.pressKeys(KeyEvent.VK_SPACE);
                        } else {
                            RSInterface lemonSliceInterface = Interfaces.get(270, 14);
                            InterfaceHandler.clickInterface(lemonSliceInterface);
                        }

                        if (Timing.waitCondition(() -> {
                            General.sleep(500, 1000);
                            return Inventory.find("Lemon slices").length == 5;
                        }, General.random(6000, 8000))) {
                            General.println("We now have 5 lemon slices");
                        }

                    }
                }
            }
        }

        // Make fruit blast
        General.println("We have this many cocktail glasses in our inventory: " + Inventory.find("Cocktail glass").length);
        while (Inventory.find("Cocktail glass").length > 0) {
            if (ItemHandler.clickOnInventoryItem(Inventory.find("Cocktail shaker"))) {
                if (Timing.waitCondition(() -> {
                    General.sleep(500);
                    return Interfaces.isInterfaceSubstantiated(436, 9);
                }, General.random(1000, 3000))) {
                    General.sleep(500, 800);
                    RSInterface createInterface = Interfaces.get(436, 9);
                    if (InterfaceHandler.clickInterface(createInterface)) {
                        General.println("Clicked on create!");
                        General.sleep(2000);
                        RSItem [] mixedBlast = Inventory.find("Mixed blast");
                        ItemHandler.clickOnInventoryItem(mixedBlast);
                    }
                }
            }
        }
        General.println("We have finished making our 5 mixed blast");


        // Pick up our dropped Fruit blast
        while (GroundItems.find("Fruit blast").length > 0) {
            ItemHandler.clickOnGroundItem(GroundItems.find("Fruit blast"), "Take");
            General.sleep(500, 800);
        }
        
    }
}
