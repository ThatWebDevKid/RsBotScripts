package scripts.TutTheIsland.Nodes.AGielinorGuide;

import org.tribot.api.General;
import org.tribot.api.input.Keyboard;
import org.tribot.api2007.Interfaces;
import scripts.API.InterfaceHandler;
import scripts.TutTheIsland.API.Node;
import scripts.TutTheIsland.TutTheIsland;

public class CreateCharacter extends Node {
    @Override
    public void printStatus() {
        General.println("TUTTHEISLAND: CREATING CHARACTER");
    }

    @Override
    public boolean validate() {
//        boolean isCreatingCharacterStage = ;
//        boolean chooseDisplayNameInterfaceOnScreen = Utils.interfaceIsVisibleOnScreen(Interfaces.get(558,3,1));
//        boolean settingYourApperanceOnScreen = Utils.interfaceIsVisibleOnScreen(Interfaces.get(263, 1,0)) &&
//                Interfaces.get(263, 1,0).getText().contains("Setting your appearance");
//        return isCreatingCharacterStage &&
//                (chooseDisplayNameInterfaceOnScreen || settingYourApperanceOnScreen);
        return TutTheIsland.gameState == 1;
    }

    @Override
    public void execute() {
        // Being asked to enter name
        if (InterfaceHandler.interfaceIsVisibleOnScreen(Interfaces.get(162,44))) {
            General.sleep(3000,4000);
            Keyboard.typeString("SirEatsAlot");
            Keyboard.pressEnter();
            General.sleep(1000,3000);
        }

        // Being asked to select a recommended name text is available, then randomly choose one of the 3 suggested names
        if (InterfaceHandler.interfaceContainsText(Interfaces.get(558, 12), "Sorry, this display name is <col=ff0000>not available</col>")) {
            switch (General.random(0, 2)) {
                case 0:
                    InterfaceHandler.clickInterface(Interfaces.get(558,14));
                    break;
                case 1:
                    InterfaceHandler.clickInterface(Interfaces.get(558,15));
                    break;
                case 2:
                    InterfaceHandler.clickInterface(Interfaces.get(558,16));
                    break;
            }
        }

        // If display name is available, click the set name button
        if (InterfaceHandler.interfaceContainsText(Interfaces.get(558, 12), "Great! This display name is")) {
            InterfaceHandler.clickInterface(Interfaces.get(558,18));
        }

        // Selecting character screen and then press accept
        if (InterfaceHandler.interfaceContainsText(Interfaces.get(263, 1,0), "Setting your appearance")) {
            final int[] RIGHT_ARROWS_CHILD_IDS = {113, 114, 115, 116, 117, 118, 119, 121, 127, 129, 130, 131};
            for (int i = 0; i < RIGHT_ARROWS_CHILD_IDS.length; i++) {
                for (int j = 0; j < General.random(1, 10); j++) {
                    InterfaceHandler.clickInterface(Interfaces.get(269, RIGHT_ARROWS_CHILD_IDS[i]));
                }
            }

            // Clicking the accept button
            InterfaceHandler.clickInterface(Interfaces.get(269,100));
        }
    }
}
