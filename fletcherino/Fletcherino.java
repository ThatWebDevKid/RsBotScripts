package scripts.fletcherino;

import org.tribot.api.General;
import org.tribot.api2007.*;
import org.tribot.script.Script;
import org.tribot.script.ScriptManifest;
import org.tribot.script.interfaces.Painting;
import scripts.fletcherino.graphics.FXMLString;
import scripts.fletcherino.graphics.GUI;
import scripts.fletcherino.graphics.GUIController;
import scripts.fletcherino.nodes.CutWood;
import scripts.fletcherino.nodes.StringBow;
import scripts.fluffeepaint.FluffeesPaint;
import scripts.fluffeepaint.PaintInfo;


import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;

@ScriptManifest(
        authors = {"ThatWebDevKid"},
        category = "Fletching",
        name = "Fletcherino",
        gameMode = 1,
        description = "Fletching bot script by ThatWebDevKid",
        version = (1.0)
)

public class Fletcherino extends Script implements Painting {
    public static double totalItemsFletched = 0;
    private ArrayList<Node> Nodes = new ArrayList<>();
    private GUI gui;
    private boolean scriptStarted = false;
    private FluffeesPaint FLUFFEES_PAINT = new FluffeesPaint(new PaintInfo() {
        @Override
        public String[] getPaintInfo() {
            return new String[]{
                    "Runtime: " + FLUFFEES_PAINT.getRuntimeString(),
                    "Total items fletched: " + (int)totalItemsFletched,
                    "Current fletching level: " + Skills.getActualLevel(Skills.SKILLS.FLETCHING),
                    "Items fletched / Hour: " + (int)((totalItemsFletched / FLUFFEES_PAINT.getRuntimeSeconds()) * 3600),
                    "Exp until next level: " + Skills.getXPToNextLevel(Skills.SKILLS.FLETCHING),
            };
        }
    }, FluffeesPaint.PaintLocations.TOP_LEFT_PLAY_SCREEN, new Color[]{new Color(255, 251, 255)}, "Trebuchet MS", new Color[]{new Color(93, 156, 236, 0)},
            new Color[]{new Color(39, 95, 255)}, 0, false, 5, 3, 0);

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
    public void onPaint(Graphics g) {
        if (scriptStarted) {
            FLUFFEES_PAINT.paint(g);
        }
    }

    @Override
    public void run() {
        gui = new GUI(FXMLString.get);
        gui.show();
        while (gui.isOpen()) {
            General.sleep(500);
        }
        onStart();
        scriptStarted = true;
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

}
