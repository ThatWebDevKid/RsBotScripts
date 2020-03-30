package scripts.minerino;

import org.tribot.api.General;
import org.tribot.api2007.Inventory;
import org.tribot.api2007.Login;
import org.tribot.api2007.types.RSArea;
import org.tribot.script.Script;
import org.tribot.script.ScriptManifest;
import scripts.API.Node;
import scripts.dax_api.api_lib.DaxWalker;
import scripts.dax_api.api_lib.models.DaxCredentials;
import scripts.dax_api.api_lib.models.DaxCredentialsProvider;
import scripts.dax_api.api_lib.models.RunescapeBank;
import scripts.minerino.constants.Constants;
import scripts.minerino.nodes.Banking;
import scripts.minerino.nodes.Dropping;
import scripts.minerino.nodes.Mine;
import scripts.minerino.nodes.Traversal;

import java.util.ArrayList;
import java.util.Collections;

@ScriptManifest(
        authors = {"ThatWebDevKid"},
        category = "Mining",
        name = "Minerino",
        gameMode = 1,
        description = "Mining Bot by ThatWebDevKid! If you like it, please leave a comment and let me know! Any problems or bugs you run into," +
                " please leave a comment and tell me the problem. Always looking to improve my scripts :D This script is fully open sourced. You can " +
                "view the source code at https://github.com/ThatWebDevKid/RsBotScripts",
        version = (1.0)
)

public class Minerino extends Script {
    public static ArrayList<Node> Nodes = new ArrayList<>();
    public static int ore = Constants.IRON_ORE;
    public static RSArea miningLocation = Constants.AL_KHARID;
    public static RunescapeBank bankingLocation = RunescapeBank.FALADOR_EAST;
    public static String miningMethod = "Dropping";


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
                new Mine(),
                new Traversal(),
                new Banking(),
                new Dropping()
        );
        Inventory.setDroppingPattern(Inventory.DROPPING_PATTERN.ZIGZAG);
        Inventory.setDroppingMethod(Inventory.DROPPING_METHOD.SHIFT);
    }

    @Override
    public void run() {
        onStart();
        while (true) {
            loop();
        }
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
