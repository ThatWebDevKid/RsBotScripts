package scripts.API;

import org.tribot.api.General;
import org.tribot.api.Timing;
import org.tribot.api2007.Banking;
import org.tribot.api2007.Interfaces;
import org.tribot.api2007.types.RSInterface;
import org.tribot.api2007.types.RSInterfaceChild;

public class BankHandler {
    private static final int BANK_MASTER_ID = 12, WITHDRAW_AS_ITEM_ID = 21, WITHDRAW_AS_NOTED_ID = 23, WITHDRAW_AS_ITEM_BACKGROUND_ID = 20;
    public static boolean isNotedOn(){
        if (Banking.isBankScreenOpen()){
            RSInterface itemInterfaceChild = Interfaces.get(BANK_MASTER_ID, WITHDRAW_AS_ITEM_BACKGROUND_ID, 0);
            if(itemInterfaceChild != null) {
                return itemInterfaceChild.getTextureID() == 1141;
            }
        }
        return false;
    }

    public static boolean setNoted(boolean noted){
        if(Banking.isBankScreenOpen()){
            if(noted && !isNotedOn()){
                RSInterfaceChild notedInterfaceChild = Interfaces.get(BANK_MASTER_ID, WITHDRAW_AS_NOTED_ID);
                if (notedInterfaceChild != null && notedInterfaceChild.click("Note")){
                    Timing.waitCondition(() -> {
                        General.sleep(10, 30);
                        return isNotedOn();
                    }, General.random(800, 1200));
                    return isNotedOn();
                }
            }else if(!noted && isNotedOn()){
                RSInterfaceChild itemInterfaceChild = Interfaces.get(BANK_MASTER_ID, WITHDRAW_AS_ITEM_ID);
                if(itemInterfaceChild != null && itemInterfaceChild.click("Item"))
                {Timing.waitCondition(() -> {
                        General.sleep(10, 30);
                        return !isNotedOn();
                }, General.random(800, 1200));
                    return !isNotedOn();
                }
            }
        }
        return false;
    }
}
