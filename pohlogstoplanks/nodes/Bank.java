package scripts.pohlogstoplanks.nodes;

import org.tribot.api.General;
import scripts.API.Node;

public class Bank extends Node {
    public void printStatus() {
        General.println("Banking!");
    }
    public boolean validate() {
        General.println("Test Banking validation!");
        General.sleep(2000);
        return false;

    }
    public void execute() {

    }
}
