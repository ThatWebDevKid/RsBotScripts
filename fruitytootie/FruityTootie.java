package scripts.fruitytootie;

import org.tribot.api.General;
import org.tribot.api2007.Inventory;
import org.tribot.api2007.Login;
import org.tribot.script.Script;
import org.tribot.script.ScriptManifest;
import scripts.dax_api.api_lib.DaxWalker;
import scripts.dax_api.api_lib.models.DaxCredentials;
import scripts.dax_api.api_lib.models.DaxCredentialsProvider;
import scripts.API.Node;
import scripts.fruitytootie.nodes.Bank;
import scripts.fruitytootie.nodes.BuyMaterial;
import scripts.fruitytootie.nodes.MakeCockTail;
import scripts.fruitytootie.nodes.WorldHop;


import java.util.ArrayList;
import java.util.Collections;

@ScriptManifest(
        authors = {"ThatWebDevKid"},
        category = "Money Making",
        name = "FruityTootie",
        gameMode = 1,
        description = "Lets make fruit blasts :) A money making script by ThatWebDevKid!",
        version = (1.0)
)


public class FruityTootie extends Script {
    private ArrayList<Node> Nodes = new ArrayList<>();
    public static boolean needWorldHop = false;

    private void onStart () {
        DaxWalker.setCredentials(new DaxCredentialsProvider() {
            public DaxCredentials getDaxCredentials() {
                return new DaxCredentials("sub_DPjXXzL5DeSiPf", "PUBLIC-KEY");
            }
        });

        if (Login.getLoginState() == Login.STATE.LOGINSCREEN) {
            Login.login();
        }

        Collections.addAll(
                Nodes,
                new Bank(),
                new BuyMaterial(),
                new MakeCockTail(),
                new WorldHop()
        );

        Inventory.setDroppingPattern(Inventory.DROPPING_PATTERN.ZIGZAG);
        Inventory.setDroppingMethod(Inventory.DROPPING_METHOD.SHIFT);
    }

    public void run() {
        onStart();
        while (Inventory.find("Coins").length > 0 && Inventory.find("Coins")[0].getStack() >= 50) {
            loop();
        }
        Login.logout();
    }

    private void loop() {
        for (final Node node: Nodes) {
            if (node.validate()) {
                node.printStatus();
                node.execute();
                General.sleep(General.random(2500, 3000));
            }
        }
    }
}
