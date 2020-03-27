package scripts.TutTheIsland.Utils;

import org.tribot.api2007.types.RSArea;
import org.tribot.api2007.types.RSTile;

public class Constants {
    // Retrieved from https://github.com/zperkins11/Tribot/blob/master/scripts/tutorialIsland/data/Finals.java
    public static final RSArea RATPIT = new RSArea(new RSTile[] {
            new RSTile(3094, 9517, 0),
            new RSTile(3094, 9519, 0),
            new RSTile(3096, 9522, 0),
            new RSTile(3098, 9523, 0),
            new RSTile(3099, 9524, 0),
            new RSTile(3101, 9526, 0),
            new RSTile(3103, 9526, 0),
            new RSTile(3104, 9525, 0),
            new RSTile(3106, 9524, 0),
            new RSTile(3107, 9523, 0),
            new RSTile(3108, 9523, 0),
            new RSTile(3109, 9523, 0),
            new RSTile(3110, 9522, 0),
            new RSTile(3110, 9521, 0),
            new RSTile(3111, 9520, 0),
            new RSTile(3111, 9519, 0),
            new RSTile(3111, 9518, 0),
            new RSTile(3110, 9517, 0),
            new RSTile(3110, 9516, 0),
            new RSTile(3110, 9515, 0),
            new RSTile(3109, 9514, 0),
            new RSTile(3107, 9513, 0),
            new RSTile(3107, 9512, 0),
            new RSTile(3106, 9511, 0),
            new RSTile(3105, 9510, 0),
            new RSTile(3102, 9510, 0)
    });
    public static final RSArea SURVIVAL_AREA = new RSArea(new RSTile(3101, 3097, 0), new RSTile(3104, 3094, 0));
    public static final RSArea KITCHEN_AREA = new RSArea(new RSTile(3076, 3083, 0), new RSTile(3074, 3086, 0));
    public static final RSArea QUEST_GUIDE_HOUSE = new RSArea(new RSTile(3089, 3119, 0), new RSTile(3080, 3125, 0));
    public static final RSArea MINING_AREA = new RSArea(new RSTile(3078, 9508, 0), new RSTile(3082, 9504, 0));
    public static final RSArea COMBAT_AREA = new RSArea(new RSTile(3104, 9509, 0), new RSTile(3106, 9507, 0));
    public static final RSArea BANK_AREA = new RSArea(new RSTile(3118, 3125, 0), new RSTile(3124, 3119, 0));
    public static final RSArea ACCOUNT_GUIDE_AREA = new RSArea(new RSTile(3126,3124,0), new RSTile(3129,3125,0));
    public static final RSArea PRIEST_GUIDE_HOUSE = new RSArea(new RSTile(3120, 3110, 0), new RSTile(3128, 3103, 0));
    public static final RSArea WIZARD_HOUSE = new RSArea(new RSTile(3141, 3084, 0), new RSTile(3143, 3090, 0));

    // TRAVERSAL STATES
    public static final int TRAVERSAL_STATES[] = {10, 120, 130, 170, 250, 360, 500, 540, 610};
    public static final int WALK_TO_SURVIVAL_AREA = 10;
    public static final int WALK_TO_KITCHEN_AREA = 120;
    public static final int WALK_TO_KITCHEN_AREA2 = 130;
    public static final int WALK_TO_QUEST_GUIDE_AREA = 170;
    public static final int WALK_TO_MINING_AREA = 250;
    public static final int WALK_TO_COMBAT_AREA = 360;
    public static final int WALK_TO_ACCOUNT_GUIDE_AREA = 500;
    public static final int WALK_TO_BROTHER_BRACE_AREA = 540;
    public static final int WALK_TO_WIZARD_HOUSE_AREA = 610;

    // GIELINOR GUIDE AREA CONSTANTS
    public static final int GIELINOR_GUIDE = 3308;
    public static final int GIELINOR_GUIDE_GAME_STATES[] =  {2,3,7};

    // SURVIVAL AREA CONSTANTS
    public static final int SURVIVAL_EXPERT = 8503;
    public static final int SURVIVAL_AREA_STATES[] = {20, 30, 40, 50, 70, 80, 90};
    public static final int FISHING_SPOT = 3317;

    // MASTER CHEF CONSTANTS
    public static final int MASTER_CHEF = 3305;
    public static final int MASTER_CHEF_STATES[] = {140, 150, 160};

    // QUEST GUIDE CONSTANTS
    public static final int QUEST_GUIDE = 3312;
    public static final int QUEST_GUIDE_STATES[] = {220, 230, 240};

    // MINING INSTRUCTORS CONSTANTS
    public static final int MINING_INSTRUCTOR = 3311;
    public static final int MINING_INSTRUCTOR_STATES[] = {260, 300, 310, 320, 330, 340, 350};

    // COMBAT AREA CONSTANTS
    public static final int COMBAT_INSTRUCTOR = 3307;
    public static final int COMBAT_AREA_STATES[] = {370, 390, 400, 405, 410, 420, 430, 440, 450, 470, 480, 490};

    // ACCOUNT AREA CONSTANTS
    public static final int BANKER = 3318;
    public static final int ACCOUNT_GUIDE = 3310;
    public static final int ACCOUNT_AREA_STATES[] = {510, 520, 525, 530, 531, 532};

    // BROTHER BRACE AREA CONSTANTS
    public static final int BROTHER_BRACE = 3319;
    public static final int BROTHER_BRACE_STATES[] = {550, 560, 570, 580, 600};

    // WIZARD AREA CONSTANTS
    public static final int MAGIC_INSTRUCTOR = 3309;
    public static final int WIZARD_HOUSE_STATES[] = {620, 630, 640, 650, 670};
}
