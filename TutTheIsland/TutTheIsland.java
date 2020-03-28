package scripts.TutTheIsland;
import org.tribot.api.General;
import org.tribot.api2007.Game;
import org.tribot.api2007.Login;
import org.tribot.script.Script;
import org.tribot.script.ScriptManifest;

import scripts.TutTheIsland.API.Node;
import scripts.TutTheIsland.Nodes.AGielinorGuide.CreateCharacter;
import scripts.TutTheIsland.Nodes.AGielinorGuide.TalkToGielinorGuide;
import scripts.TutTheIsland.Nodes.CMasterChef.MasterChef;
import scripts.TutTheIsland.Nodes.DQuestGuide.QuestGuide;
import scripts.TutTheIsland.Nodes.EMiningInstructor.MiningInstructor;
import scripts.TutTheIsland.Nodes.FCombatArea.CombatArea;
import scripts.TutTheIsland.Nodes.GAccountGuide.AccountGuide;
import scripts.TutTheIsland.Nodes.HBrotherBrace.BrotherBrace;
import scripts.TutTheIsland.Nodes.IWizardHouse.WizardHouse;
import scripts.TutTheIsland.Nodes.LoginUser;
import scripts.TutTheIsland.Nodes.BSurvivalExpert.SurvivalExpert;
import scripts.TutTheIsland.Nodes.Traversal;
import scripts.dax_api.api_lib.DaxWalker;
import scripts.dax_api.api_lib.models.DaxCredentials;
import scripts.dax_api.api_lib.models.DaxCredentialsProvider;

import java.util.ArrayList;
import java.util.Collections;

@ScriptManifest(
        authors = {"ThatWebDevKid"},
        category = "Tutorial Island",
        name = "TutTheIsland",
        gameMode = 1,
        description = "Tutorial Island Bot",
        version = (1.0)
)

public class TutTheIsland extends Script {
    public static ArrayList<Node> Nodes = new ArrayList<>();
    public static int gameState = Game.getSetting(281);

    @Override
    public void run() {
        DaxWalker.setCredentials(new DaxCredentialsProvider() {
            @Override
            public DaxCredentials getDaxCredentials() {
                return new DaxCredentials("sub_DPjXXzL5DeSiPf", "PUBLIC-KEY");
            }
        });

        Collections.addAll(
                Nodes,
                new LoginUser(),
                new Traversal(),
                new CreateCharacter(),
                new TalkToGielinorGuide(),
                new SurvivalExpert(),
                new MasterChef(),
                new QuestGuide(),
                new MiningInstructor(),
                new CombatArea(),
                new AccountGuide(),
                new BrotherBrace(),
                new WizardHouse()
        );

        while (true) {
            loop();

            if (gameState == 1000) {
                Login.logout();
                break;
            }
        }
    }

    private void loop() {
        for (final Node node: Nodes) {
            gameState = Game.getSetting(281);
            if (node.validate()) {
                node.printStatus();
                node.execute();
                General.sleep(General.random(2500, 3000));
            }
        }
    }
}
