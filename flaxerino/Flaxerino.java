package scripts.flaxerino;

import org.tribot.api.General;
import org.tribot.api2007.Login;
import org.tribot.script.Script;
import org.tribot.script.ScriptManifest;
import scripts.API.Node;
import scripts.dax_api.api_lib.DaxWalker;
import scripts.dax_api.api_lib.models.DaxCredentials;
import scripts.dax_api.api_lib.models.DaxCredentialsProvider;
import scripts.flaxerino.nodes.Bank;
import scripts.flaxerino.nodes.SpinFlax;
import scripts.flaxerino.nodes.SpinWool;

import java.util.ArrayList;
import java.util.Collections;

@ScriptManifest(
        authors = {"ThatWebDevKid"},
        category = "Crafting",
        name = "Flaxerino",
        gameMode = 1,
        description = "Spin Flax to Bow Strings Bot by ThatWebDevKid! If you like it, please leave a comment and let me know! Any problems or bugs you run into," +
                " please leave a comment and tell me the problem. Always looking to improve my scripts :D This script is fully open sourced. You can " +
                "view the source code at https://github.com/ThatWebDevKid/RsBotScripts",
        version = (1.0)
)

public class Flaxerino extends Script {
    public static ArrayList<Node> Nodes = new ArrayList<>();
    public static boolean withdrewMaterialSuccessful = true;

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
                new SpinWool(),
                new SpinFlax()
        );

    }

    @Override
    public void run() {
        onStart();
        while (withdrewMaterialSuccessful) {
            loop();
        }
        Login.logout();
    }

    private void loop() {
        for (final Node node: Nodes) {
            if (node.validate()) {
                node.printStatus();
                node.execute();
                General.sleep(General.random(500, 1000));
            }
        }
    }
}
