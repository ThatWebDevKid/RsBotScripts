package scripts.chatspammer;

import org.tribot.api.General;
import org.tribot.api.Timing;
import org.tribot.api.input.Keyboard;
import org.tribot.api2007.Interfaces;
import org.tribot.api2007.Login;
import org.tribot.api2007.Player;
import org.tribot.api2007.types.RSInterface;
import org.tribot.script.Script;
import org.tribot.script.ScriptManifest;
import scripts.API.Antiban;
import scripts.API.InterfaceHandler;


@ScriptManifest(
        authors = {"ThatWebDevKid"},
        category = "Tools",
        name = "Chat Spammer",
        gameMode = 1,
        description = "Chat spamming script by ThatWebDevKid!",
        version = (1.0)
)

public class ChatSpammer extends Script {
    @Override
    public void run() {
        boolean alreadySpamming = false;
        if (Login.getLoginState() == Login.STATE.LOGINSCREEN) {
            Login.login();
        }

        RSInterface publicChat = Interfaces.get(162, 13);

        while (true) {

            while (Player.getIndex() > 200) {
                General.println("Plyaer PID is " + Player.getIndex());
                alreadySpamming = false;
                Login.logout();
                Login.login();
                General.sleep(500);
            }

            if (Login.getLoginState() == Login.STATE.INGAME && !alreadySpamming) {
                InterfaceHandler.rightClickInterface(publicChat, "Setup your autochat");
                Keyboard.typeString("I WANT TO EAT FRUIT CAKES FOREVER!");
                General.sleep(500);
                Keyboard.pressEnter();
                General.sleep(500);
                alreadySpamming = true;
            }

            General.println("Plyaer PID is " + Player.getIndex());
            Player.getRSPlayer().click();

            Timing.waitCondition(() -> {
                General.sleep(500);
                Antiban.checkTimedActions();
                return false;
            }, General.random(120000, 180000));
        }
    }
}
