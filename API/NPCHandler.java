package scripts.API;

import com.sun.org.apache.xpath.internal.operations.Bool;
import org.tribot.api.General;
import org.tribot.api.Timing;
import org.tribot.api.input.Mouse;
import org.tribot.api2007.*;
import org.tribot.api2007.types.RSNPC;
import org.tribot.api2007.types.RSObject;
import scripts.dax_api.api_lib.DaxWalker;

import java.util.function.BooleanSupplier;
import java.util.function.Predicate;

public class NPCHandler {
    private static boolean npcFound (String npcName){
        return NPCs.findNearest(npcName).length > 0;
    }

    private static boolean npcFoundAndValid (int npcId) {
        return  NPCs.findNearest(npcId).length > 0 && NPCs.findNearest(npcId)[0].isOnScreen() && NPCs.findNearest(npcId)[0].isClickable();
    }

    public static void interactWithNPC(String npcName, String action, BooleanSupplier supplier, boolean walkToNPCFirst) {
        if (npcFound(npcName)) {
            RSNPC npc = NPCs.findNearest(npcName)[0];
            interactWithNPC(npc.getID(), action, supplier, walkToNPCFirst);
        }
    }

    private static boolean rightClickNPC (RSNPC npc, String action) {
        npc.hover();
        Mouse.click(GlobalConstants.RIGHT_CLICK);
        if (ChooseOption.isOptionValid(action)) {
            ChooseOption.select(action);
            return true;
        }
        return false;
    }

    public static void interactWithNPC (int npcId, String action, BooleanSupplier supplier, boolean walkToNPCFirst) {
        RSNPC npc = NPCs.findNearest(npcId)[0];
        if (walkToNPCFirst) {
            DaxWalker.walkTo(npc.getPosition());
        }

        if (npcFoundAndValid(npcId)) {
            boolean successfullyClicked = (action == "") ? npc.click() : rightClickNPC(npc, action);
            if (successfullyClicked) {
                Timing.waitCondition(() -> {
                    General.sleep(500);
                    return supplier.getAsBoolean();
                }, 10000);
                return;
            }
        }

        npc.adjustCameraTo();
        Timing.waitCondition(() -> {
            General.sleep(500);
            return npcFoundAndValid(npcId);
        }, General.random(2000, 4000));
    }

    public static void clickOnNPC (int npcId) {
        interactWithNPC(npcId, "", () -> false, true);
    }

    public static void talkToNPC (int npcId) {
        if (NPCChat.getMessage() != null && NPCChat.getName() != null || InterfaceHandler.clickHereToContinue()) {
            while (NPCChat.clickContinue(true) || InterfaceHandler.clickHereToContinue()) {
                General.sleep(General.random(1000, 2000), General.random(3000, 4000));
            };
            return;
        }

        interactWithNPC(npcId, GlobalConstants.TALK_TO, () -> NPCChat.getMessage() != null && NPCChat.getName() != null, true);
    }
}
