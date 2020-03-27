package scripts.TutTheIsland.Nodes;

import org.tribot.api.General;
import org.tribot.api2007.Login;
import scripts.TutTheIsland.API.Node;

public class LoginUser extends Node {
    @Override
    public void printStatus() {
        General.println("TUTTHEISLAND: LOGGING USER IN");
    }

    @Override
    public boolean validate() {
        return Login.getLoginState() == Login.STATE.LOGINSCREEN;
    }

    @Override
    public void execute() {
        if(!Login.login()) {
            General.println("Sorry, there seems to be a problem with your account information.");
            General.println("Please re-check your account information and run the script again.");
        }
    }
}
