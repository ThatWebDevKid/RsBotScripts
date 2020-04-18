package scripts.pohlogstoplanks;

import org.tribot.api.General;
import org.tribot.api2007.Inventory;
import org.tribot.api2007.Login;
import org.tribot.script.Script;
import org.tribot.script.ScriptManifest;
import scripts.API.Node;
import scripts.dax_api.api_lib.DaxWalker;
import scripts.dax_api.api_lib.models.DaxCredentials;
import scripts.dax_api.api_lib.models.DaxCredentialsProvider;
import scripts.pohlogstoplanks.nodes.Bank;
import scripts.pohlogstoplanks.nodes.TalkToButler;
import scripts.pohlogstoplanks.nodes.Teleport;

import java.util.ArrayList;
import java.util.Collections;


@ScriptManifest(
        authors = {"ThatWebDevKid"},
        category = "Money Making",
        name = "POHLogsToPlanks",
        gameMode = 1,
        description = "POH Logs to Planks Bot by ThatWebDevKid! If you like it, please leave a comment and let me know! Any problems or bugs you run into," +
                " please leave a comment and tell me the problem. Always looking to improve my scripts :D This script is fully open sourced. You can " +
                "view the source code at https://github.com/ThatWebDevKid/RsBotScripts",
        version = (1.0)
)


public class PohLogsToPlanks extends Script {
    public static ArrayList<Node> Nodes = new ArrayList<>();
    public static boolean noMoreLogs = false;

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
                new Teleport(),
                new TalkToButler()
        );

    }

    @Override
    public void run() {
        onStart();
        while (Inventory.find("Coins")[0].getStack() >= 6250 && !noMoreLogs) {
            loop();
        }
        Login.logout();
    }

    private void loop() {
        for (final Node node: Nodes) {
            if (node.validate()) {
                node.printStatus();
                node.execute();
                General.sleep(1000, 1500);
            }
        }
    }

}
