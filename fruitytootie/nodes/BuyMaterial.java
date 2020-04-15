package scripts.fruitytootie.nodes;

import org.tribot.api.General;
import org.tribot.api.Timing;
import org.tribot.api2007.Interfaces;
import org.tribot.api2007.Inventory;
import org.tribot.api2007.NPCs;
import org.tribot.api2007.Player;
import org.tribot.api2007.types.*;
import scripts.API.InterfaceHandler;
import scripts.API.NPCHandler;
import scripts.API.Node;
import scripts.dax_api.api_lib.DaxWalker;
import scripts.fruitytootie.FruityTootie;
import scripts.fruitytootie.constants.Constants;

public class BuyMaterial  extends Node {
    private boolean hasEnoughMaterial = false;
    public void printStatus() {
        General.println("Buying fruit blast material");
    }

    public boolean validate() {
        if (!Constants.funchsFineGroceries.contains(Player.getPosition())) {
            DaxWalker.walkTo(Constants.funchsFineGroceries.getRandomTile());
        }

        Timing.waitCondition(() -> {
            General.sleep(500);
            return Constants.funchsFineGroceries.contains(Player.getPosition());
        }, General.random(5000, 8000));

        RSNPC[] heckelFunch = NPCs.find("Heckel Funch");

        boolean successcfullyTradeheckelFunch = NPCHandler.interactWithNPC(heckelFunch, "Trade");
        if (successcfullyTradeheckelFunch) {
            RSInterface pineapple = Interfaces.get(300, 16,5);
            RSInterface oranges = Interfaces.get(300, 16, 7);
            RSInterface lemons = Interfaces.get(300, 16, 8);
            RSInterface shotGlass = Interfaces.get(300, 16, 17);
            while(!Interfaces.isInterfaceSubstantiated(pineapple)) {
                pineapple = Interfaces.get(300, 16,5);
            }
            while(!Interfaces.isInterfaceSubstantiated(oranges)) {
                oranges = Interfaces.get(300, 16, 7);
            }
            while(!Interfaces.isInterfaceSubstantiated(lemons)) {
                lemons = Interfaces.get(300, 16, 8);
            }
            while(!Interfaces.isInterfaceSubstantiated(shotGlass)) {
                shotGlass = Interfaces.get(300, 16, 17);
            }
            boolean enoughPineapple = pineapple != null && pineapple.getComponentStack() >= 5;
            boolean enoughOranges = oranges != null && oranges.getComponentStack() >= 5;
            boolean enoughLemons = lemons != null && lemons.getComponentStack() >= 10;
            boolean enoughShotGlasses = shotGlass != null && shotGlass.getComponentStack() >= 5;
            General.println("Enough Pineapples? " +  pineapple.getComponentStack());
            General.println("Enough Oranges? " + oranges.getComponentStack());
            General.println("Enough Lemons? " + lemons.getComponentStack());
            General.println("Enough Shot Glasses? " + shotGlass.getComponentStack());
            hasEnoughMaterial =  enoughPineapple && enoughOranges && enoughLemons && enoughShotGlasses;
        }

        boolean haveEnoughCoins = Inventory.find("Coins").length > 0 && Inventory.find("Coins")[0].getStack() >= 50;

        if (!hasEnoughMaterial && successcfullyTradeheckelFunch) { FruityTootie.needWorldHop = true; }

        // Close the shop window
        General.sleep(500, 1000);
        RSInterface xButton = Interfaces.get(300, 1, 11);
        InterfaceHandler.clickInterface(xButton);
        Timing.waitCondition(() -> {
            General.sleep(500, 1000);
            return !Interfaces.isInterfaceSubstantiated(Interfaces.get(300, 1, 11));
        }, General.random(3000, 5000));


        return hasEnoughMaterial && haveEnoughCoins;
    }

    public void execute() {
        RSInterface xButton = Interfaces.get(300, 1, 11);

        // Drop any fruit blasts you currently have in your inventory
        // Drop All Current Fruit Blast
        RSItem[] fruitBlasts = Inventory.find("Fruit blast");
        General.println("We have " + fruitBlasts.length + " in our inventory");
        General.sleep(2000, 4000);
        if (fruitBlasts.length > 0) {
            while (Inventory.find("Fruit blast").length > 0) {
                fruitBlasts = Inventory.find("Fruit blast");
                Inventory.drop(fruitBlasts);
            }

            Timing.waitCondition(() -> {
                General.sleep(500);
                return Inventory.find("Fruit blast").length <= 0;
            }, General.random(5000, 10000));

            General.println("We should have dropped all of our fruit blast");
        }


        // Buy the material from Heckel Finch
        RSNPC[] heckelFunch = NPCs.find("Heckel Funch");
        if (NPCHandler.interactWithNPC(heckelFunch, "Trade")) {
            while(!Interfaces.isInterfaceSubstantiated(300));
            RSInterface pineapple = Interfaces.get(300, 16,5);
            RSInterface oranges = Interfaces.get(300, 16, 7);
            RSInterface lemons = Interfaces.get(300, 16, 8);
            RSInterface shotGlass = Interfaces.get(300, 16, 17);
            xButton = Interfaces.get(300, 1, 11);
            while(!InterfaceHandler.rightClickInterface(pineapple, "Buy 5")) {
                General.sleep(500);
            }
            General.sleep(300, 500);
            while(!InterfaceHandler.rightClickInterface(oranges, "Buy 5")) {
                General.sleep(500);
            }
            General.sleep(300, 500);
            while(!InterfaceHandler.rightClickInterface(lemons, "Buy 10")){
                General.sleep(500);
            }
            General.sleep(300, 500);
            while(!InterfaceHandler.rightClickInterface(shotGlass, "Buy 5")) {
                General.sleep(500);
            }
            General.sleep(300, 500);
            InterfaceHandler.clickInterface(xButton);
        }


    }
}
