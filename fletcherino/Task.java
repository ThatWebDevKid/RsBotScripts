package scripts.fletcherino;

import org.tribot.api.General;
import org.tribot.api.Timing;
import org.tribot.api2007.Banking;
import org.tribot.api2007.Inventory;
import org.tribot.api2007.Player;
import org.tribot.api2007.Skills;
import org.tribot.api2007.types.RSPlayer;
import scripts.fletcherino.graphics.GUIController;

public class Task {

    private String whatToDo;
    private String itemToDo;
    private int levelToGoUpTo;
    private int suppliesToFletch;

    public Task(String task) {
        String[] args = task.split(":");
        whatToDo = args[0];
        itemToDo = args[1];
        levelToGoUpTo = Integer.parseInt(args[2]);
        suppliesToFletch = Integer.parseInt(args[3]);
        validateLevelToGoUpTo();
    }

    private void validateLevelToGoUpTo() {
        if (levelToGoUpTo < 2 || levelToGoUpTo > 99) {
            levelToGoUpTo = -1;
        }
    }

    public String getWhatToDo() {
        return whatToDo;
    }

    public String getItemToDo() {
        return itemToDo;
    }

    public String getLogType() {
        return GUIController.mymap.get(itemToDo);
    }

    public int getlevelToGoUpTo() {
        return levelToGoUpTo;
    }

    public int getSuppliesToFletch() {
        return suppliesToFletch;
    }

    public void setSluppliesToFletch(int remainder) {
        if (suppliesToFletch > 0) {
            suppliesToFletch = remainder;
        }
    }

    public boolean ranOutOfWoodForCutting() {
        boolean openBank = true;
        if (!Banking.isBankScreenOpen()) {
            Banking.openBank();
            General.sleep(500, 1000);
            openBank = Timing.waitCondition(() -> {
                General.sleep(500, 1000);
                return Banking.isBankScreenOpen();
            }, General.random(5000, 8000));
        }
        boolean woodNotFoundInBankOrInventory = openBank && Banking.find(GUIController.mymap.get(itemToDo)).length <= 0
                && Inventory.find(GUIController.mymap.get(itemToDo)).length <= 0;
        return whatToDo.equalsIgnoreCase("Cut") && woodNotFoundInBankOrInventory;
    }

    public boolean ranOutOfBowsAndBowStrings() {
        boolean openBank = true;
        if (!Banking.isBankScreenOpen()) {
            Banking.openBank();
            General.sleep(500, 1000);
            openBank = Timing.waitCondition(() -> {
                General.sleep(500, 1000);
                return Banking.isBankScreenOpen();
            }, General.random(5000, 8000));
        }
        boolean itemToDoNotFoundInBankOrInventory =  openBank && Banking.find(itemToDo).length <= 0 && Inventory.find(itemToDo).length <= 0;
        boolean bowStringNotFoundinBankOrInventory = openBank && Banking.find("Bow string").length <= 0 && Inventory.find("Bow string").length <= 0;
        return whatToDo.equalsIgnoreCase("String")
                && (itemToDoNotFoundInBankOrInventory  || bowStringNotFoundinBankOrInventory);
    }


    public boolean reachedDesiredLevel() {
        int fletchingLevel = Skills.getCurrentLevel(Skills.SKILLS.FLETCHING);;
        boolean reachedDesiredFletchingLevel = levelToGoUpTo != -1 && fletchingLevel >= levelToGoUpTo;
        return reachedDesiredFletchingLevel;
    }

    public boolean isCompleted() {
        return reachedDesiredLevel() || ranOutOfWoodForCutting() || ranOutOfBowsAndBowStrings() || (suppliesToFletch == 0);
    }


}
