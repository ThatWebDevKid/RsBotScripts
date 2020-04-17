package scripts.pohlogstoplanks.nodes;

import org.tribot.api.General;
import scripts.API.Node;

public class Teleport extends Node {
    public void printStatus() {
        General.println("Teleporting!");
    }
    public boolean validate() {
        return false;

    }
    public void execute() {

    }
}
