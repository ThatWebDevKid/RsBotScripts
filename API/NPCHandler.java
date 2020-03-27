package scripts.API;

import org.tribot.api.General;
import org.tribot.api.Timing;
import org.tribot.api2007.Combat;
import org.tribot.api2007.NPCChat;
import org.tribot.api2007.NPCs;
import org.tribot.api2007.types.RSNPC;

public class NPCHandler {
        private static boolean npcFound (int npcId) {
            return NPCs.find(npcId).length > 0;
        }

        private static boolean npcFoundAndValid (int npcId) {
            return npcFound(npcId) && NPCs.find(npcId)[0].isOnScreen() && NPCs.find(npcId)[0].isClickable();
        }

        private static boolean npcFound (String npcName) {
            return NPCs.find(npcName).length > 0;
        }

        private static boolean npcFoundAndValid (String npcName) {
            return npcFound(npcName) && NPCs.find(npcName)[0].isOnScreen() && NPCs.find(npcName)[0].isClickable();
        }

        public static boolean clickOnNPC (int npcId) {
            if (npcFoundAndValid(npcId)) {
                RSNPC npc = NPCs.findNearest(npcId)[0];
                npc.click();
                return true;
            }
            return false;
        }

        public static boolean clickOnNPC (String npcName) {
            if (npcFoundAndValid(npcName)) {
                RSNPC npc = NPCs.findNearest(npcName)[0];
                npc.click();
                return true;
            }
            return false;
        }

        public static boolean attackNPC (String npcName, int timeout, boolean closeCombat) {
            if (npcFoundAndValid(npcName)) {
                RSNPC npc = NPCs.findNearest(npcName)[0];
                npc.click();
                if (closeCombat) {
                    Timing.waitCondition(() -> !Combat.isUnderAttack() && Combat.getTargetEntity() == null, timeout);
                } else {
                    Timing.waitCondition(() -> Combat.isUnderAttack() && Combat.getTargetEntity() != null, timeout);
                }
                return true;
            }
            return false;
        }

        public static boolean talkToNPC (int npcId) {
            if (npcFoundAndValid(npcId)) {
                RSNPC npc = NPCs.find(npcId)[0];
                if (NPCChat.getMessage() == null && NPCChat.getName() == null) {
                    npc.click();
                }
                General.sleep(2000, 4000);

                while (NPCChat.clickContinue(true) || InterfaceHandler.clickHereToContinue()) {
                    General.sleep(1000, 2000);
                }

                return true;
            }
            return false;
        }
}
