package scripts.API;

import org.tribot.api.General;
import org.tribot.api.Timing;
import org.tribot.api2007.GameTab;
import org.tribot.api2007.Objects;

public class TabsHandler {
    public static boolean openTab (GameTab.TABS tab) {
        if (tab.isOpen()) { return true; };
        return tab.open() && Timing.waitCondition(() -> {
            General.sleep(500);
            return tab.isOpen();
        }, General.random(3000, 5000));
    }

}
