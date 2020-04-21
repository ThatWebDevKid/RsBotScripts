package scripts.pohlogstoplanks;

import org.tribot.api.General;
import org.tribot.api2007.Inventory;
import org.tribot.api2007.Login;
import org.tribot.api2007.NPCChat;
import org.tribot.api2007.Skills;
import org.tribot.script.Script;
import org.tribot.script.ScriptManifest;
import org.tribot.script.interfaces.Arguments;
import org.tribot.script.interfaces.Painting;
import scripts.API.Node;
import scripts.dax_api.api_lib.DaxWalker;
import scripts.dax_api.api_lib.models.DaxCredentials;
import scripts.dax_api.api_lib.models.DaxCredentialsProvider;
import scripts.fluffeepaint.FluffeesPaint;
import scripts.fluffeepaint.PaintInfo;
import scripts.pohlogstoplanks.nodes.Bank;
import scripts.pohlogstoplanks.nodes.Restock;
import scripts.pohlogstoplanks.nodes.TalkToButler;
import scripts.pohlogstoplanks.nodes.Teleport;

import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;


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


public class PohLogsToPlanks extends Script implements Painting, Arguments {
    public static double totalPlanks = 0;
    public static ArrayList<Node> Nodes = new ArrayList<>();
    public static boolean noMoreLogs = false;
    public static String bank;
    public static String logType;
    public static int logTypeNoted;
    public static String plankType;
    private boolean scriptStarted = false;
    public static boolean needsReset = true;
    private FluffeesPaint FLUFFEES_PAINT = new FluffeesPaint(new PaintInfo() {
        @Override
        public String[] getPaintInfo() {
            return new String[]{
                    "Runtime: " + FLUFFEES_PAINT.getRuntimeString(),
                    "Total logs to planks: " + (int)totalPlanks,
                    "Logs To Planks / Hour: " + (int)((totalPlanks / FLUFFEES_PAINT.getRuntimeSeconds()) * 3600),
            };
        }
    }, FluffeesPaint.PaintLocations.TOP_LEFT_PLAY_SCREEN, new Color[]{new Color(255, 251, 255)}, "Trebuchet MS", new Color[]{new Color(93, 156, 236, 0)},
            new Color[]{new Color(39, 95, 255)}, 0, false, 5, 3, 0);


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
                new TalkToButler(),
                new Restock()
        );
    }

    @Override
    public void passArguments(HashMap<String, String> hashMap) {
        if (hashMap.containsKey("custom_input") && hashMap.get("custom_input") != null && !hashMap.get("custom_input").isEmpty()) {
            String args = hashMap.get("custom_input");
            String[] argsSplit = args.split(",");
            String logTypeArgs = argsSplit[0].split("=")[1].toLowerCase();

            switch (logTypeArgs) {
                case "mahogany":
                    logType = "Mahogany logs";
                    logTypeNoted = 8836;
                    plankType = "Mahogany plank";
                    break;
                case "teak":
                    logType = "Teak logs";
                    logTypeNoted = 6334;
                    plankType = "Teak plank";
                    break;
                default:
                    logType = "Oak logs";
                    logTypeNoted = 1522;
                    plankType = "Oak plank";
            }
        } else {
            logType = "Oak logs";
            logTypeNoted = 1522;
            plankType = "Oak plank";
        }
    }


    @Override
    public void onPaint(Graphics g) {
        if (scriptStarted) {
            FLUFFEES_PAINT.paint(g);
        }
    }


    @Override
    public void run() {
        setLoginBotState(false);
        onStart();
        scriptStarted = true;
        while (true) {
            loop();
        }
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
