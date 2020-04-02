package scripts.API;

import com.sun.org.apache.xpath.internal.operations.Bool;
import org.tribot.api.General;
import org.tribot.api.Timing;
import org.tribot.api.input.Mouse;
import org.tribot.api2007.*;
import org.tribot.api2007.types.RSNPC;
import org.tribot.api2007.types.RSObject;
import org.tribot.api2007.types.RSPlayer;
import scripts.dax_api.api_lib.DaxWalker;

import java.util.function.BooleanSupplier;
import java.util.function.Predicate;

public class NPCHandler {
    private static boolean npcFound (RSNPC[] npcs) {
        return npcs.length > 0;
    }

    private static boolean npcValid (RSNPC npc) {
        return npc.isOnScreen() && npc.isClickable() && PathFinding.canReach(npc.getAnimablePosition(), false);
    }

    private static boolean rightClickNPC (RSNPC npc, String optionToSelect) {
        if (npc.hover()) {
            Mouse.click(GlobalConstants.RIGHT_CLICK);
            String optionToSelectFullString = optionToSelect + " " + npc.getDefinition().getName();
            General.println(optionToSelectFullString);
            if (ChooseOption.isOptionValid(optionToSelectFullString)) {
                General.println("OPTION IS VALID");
                return ChooseOption.select(optionToSelectFullString);
            } else {
                ChooseOption.select("Cancel");
            }
        }
        return false;
    }

    public static boolean interactWithNPC (RSNPC[] npcs, String optionToSelect, boolean closeInteract) {
        if (npcFound(npcs) && Player.getRSPlayer().getInteractingCharacter() == null) {
            RSNPC npc = npcs[0];
            boolean walkToAndInView = true;

            if (!npcValid(npc) && closeInteract) {
                General.println(("NPC IS NOT VALID!"));
                if (DaxWalker.walkTo(npc.getAnimablePosition())) {
                    General.println("Using Dax Walker to walk to NPC");
                    Timing.waitCondition(() -> Player.getPosition().distanceTo(npc.getAnimablePosition()) < 5, General.random(5000, 10000));
                    npc.adjustCameraTo();
                } else if (WebWalking.walkTo(npc.getAnimablePosition())) {
                    General.println("Using Web walker to walk to NPC");
                    Timing.waitCondition(() -> Player.getPosition().distanceTo(npc.getAnimablePosition()) < 5, General.random(5000, 10000));
                    npc.adjustCameraTo();
                }
                walkToAndInView = npcValid(npc);
            } else {
                walkToAndInView = npc.adjustCameraTo() && npcValid(npc);
            }

            if (walkToAndInView && npcValid(npc)) {
                boolean successfullyClicked = (optionToSelect == "") ? npc.click() : rightClickNPC(npc, optionToSelect);
                return successfullyClicked;
            }
        }
        return false;
    }

    public static boolean interactWithNPC (RSNPC[] npcs, String optionToSelect) {
        return interactWithNPC(npcs, optionToSelect, true);
    }

    public static boolean talkToNPC (RSNPC[] npcs) {

        // If we are already in a middle of a conversation with an NPC, we need to finish it.
        if (NPCChat.getMessage() != null && NPCChat.getName() != null || InterfaceHandler.clickHereToContinue()) {
            while (NPCChat.clickContinue(true) || InterfaceHandler.clickHereToContinue()) {
                General.sleep(General.random(1000, 2000), General.random(3000, 4000));
            };
            return true;
        }

        // Otherwise click on the NPC and wait for the chat dialogue to appear
        if (interactWithNPC(npcs,  GlobalConstants.TALK_TO, true)) {
            return Timing.waitCondition(() -> {
                General.sleep(500);
                boolean talkingToNPC = NPCChat.getMessage() != null && NPCChat.getName() != null;
                return talkingToNPC;
            }, General.random(5000, 10000));
        }

        return false;
    }

    public static boolean attackNPC (RSNPC[] npcs, boolean closeRangeContact) {
        // Otherwise click on the NPC and wait for the chat dialogue to appear
        if (interactWithNPC(npcs,  GlobalConstants.ATTACK, closeRangeContact)) {
            return Timing.waitCondition(() -> {
                General.sleep(500);
                RSPlayer player = Player.getRSPlayer();
                return player != null && player.getInteractingIndex() != -1 && player.getInteractingCharacter() != null;
            }, General.random(10000, 15000)) &&
            Timing.waitCondition(() -> {
                General.sleep(500);
                RSPlayer player = Player.getRSPlayer();
                return player != null && player.getInteractingIndex() == -1 && player.getInteractingCharacter() == null;
            }, General.random(10000, 15000));
        }
        return false;
    }
}
