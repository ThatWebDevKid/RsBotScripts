package scripts.TutTheIsland.Nodes;

import org.tribot.api.General;
import org.tribot.api2007.Game;
import scripts.TutTheIsland.API.Node;
import scripts.TutTheIsland.TutTheIsland;
import scripts.TutTheIsland.Utils.Constants;
import scripts.dax_api.api_lib.DaxWalker;

import java.util.Arrays;

public class Traversal extends Node {
    public void printStatus(){
        General.println("WALKING TO THE NEXT STAGE");
    }
    public boolean validate() {
        return Arrays.stream(Constants.TRAVERSAL_STATES).anyMatch(stage -> stage == TutTheIsland.gameState);
    }
    public void execute() {
        switch (TutTheIsland.gameState) {
            case Constants.WALK_TO_SURVIVAL_AREA:
                DaxWalker.walkTo(Constants.SURVIVAL_AREA.getRandomTile());
                break;
            case Constants.WALK_TO_KITCHEN_AREA:
                DaxWalker.walkTo(Constants.KITCHEN_AREA.getRandomTile());
                break;
            case Constants.WALK_TO_KITCHEN_AREA2:
                DaxWalker.walkTo(Constants.KITCHEN_AREA.getRandomTile());
                break;
            case Constants.WALK_TO_QUEST_GUIDE_AREA:
                DaxWalker.walkTo(Constants.QUEST_GUIDE_HOUSE.getRandomTile());
                break;
            case Constants.WALK_TO_MINING_AREA:
                DaxWalker.walkTo(Constants.MINING_AREA.getRandomTile());
                break;
            case Constants.WALK_TO_COMBAT_AREA:
                DaxWalker.walkTo(Constants.COMBAT_AREA.getRandomTile());
                break;
            case Constants.WALK_TO_ACCOUNT_GUIDE_AREA:
                DaxWalker.walkTo(Constants.BANK_AREA.getRandomTile());
                break;
            case Constants.WALK_TO_BROTHER_BRACE_AREA:
                DaxWalker.walkTo(Constants.PRIEST_GUIDE_HOUSE.getRandomTile());
                break;
            case Constants.WALK_TO_WIZARD_HOUSE_AREA:
                DaxWalker.walkTo(Constants.WIZARD_HOUSE.getRandomTile());
                break;
        }

        General.sleep(7000);
    }

}
