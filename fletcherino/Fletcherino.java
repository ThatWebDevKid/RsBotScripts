package scripts.fletcherino;

import org.tribot.api.General;
import org.tribot.api2007.*;
import org.tribot.script.Script;
import org.tribot.script.ScriptManifest;
import org.tribot.util.Util;
import scripts.fletcherino.Node;
import scripts.dax_api.api_lib.DaxWalker;
import scripts.dax_api.api_lib.models.DaxCredentials;
import scripts.dax_api.api_lib.models.DaxCredentialsProvider;
import scripts.dax_api.api_lib.models.RunescapeBank;
import scripts.fletcherino.graphics.FXMLString;
import scripts.fletcherino.graphics.GUI;
import scripts.fletcherino.graphics.GUIController;
import scripts.fletcherino.nodes.BuyMaterial;
import scripts.fletcherino.nodes.CutWood;
import scripts.fletcherino.nodes.StringBow;


import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@ScriptManifest(
        authors = {"ThatWebDevKid"},
        category = "Fletching",
        name = "Fletcherino",
        gameMode = 1,
        description = "Fletching Bot by ThatWebDevKid! If you like it, please leave a comment and let me know! Any problems or bugs you run into," +
                " please leave a comment and tell me the problem. Always looking to improve my scripts :D This script is fully open sourced. You can " +
                "view the source code at https://github.com/ThatWebDevKid/RsBotScripts",
        version = (1.0)
)

public class Fletcherino extends Script {
    private ArrayList<Node> Nodes = new ArrayList<>();
    private GUI gui;

    private void onStart () {

        if (Login.getLoginState() == Login.STATE.LOGINSCREEN) {
            Login.login();
        }

        Collections.addAll(
                Nodes,
                new CutWood(),
                new StringBow()
        );

    }

    @Override
    public void run() {
        gui = new GUI(FXMLString.get);
        gui.show();
        while (gui.isOpen()) {
            General.sleep(500);
        }


        onStart();
        for (final Task task: GUIController.tasks) {
            while (!task.isCompleted()) {
                for (final Node node: Nodes) {
                    if (node.validate(task)) {
                        node.printStatus(task);
                        node.execute(task);
                        General.sleep(General.random(2500, 3000));
                    }
                }
                General.sleep(1000);
            }
        }
        Login.logout();
    }

//    private void loop() {
//        for (final Node node: Nodes) {
//            if (node.validate()) {
//                node.printStatus();
//                node.execute();
//                General.sleep(General.random(2500, 3000));
//            }
//        }
//    }
}
