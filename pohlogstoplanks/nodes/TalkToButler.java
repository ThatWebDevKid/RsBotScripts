package scripts.pohlogstoplanks.nodes;

import org.tribot.api.General;
import scripts.API.Node;

public class TalkToButler extends Node {
    public void printStatus() {
        General.println("Talking with Butler!");
    }
    public boolean validate() {
        return false;

    }
    public void execute() {

    }
}
