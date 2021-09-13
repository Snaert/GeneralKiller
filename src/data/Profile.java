package data;

import org.osbot.rs07.api.map.Area;
import org.osbot.rs07.api.map.Position;

public enum Profile {
    LUMBRIDGE_NORTH_CHICKEN(new Area(new int[][]{{ 3183, 3289 }, { 3171, 3289 }, { 3169, 3291 }, { 3169, 3295 }, { 3170, 3296 }, { 3170, 3298 }, { 3169, 3299 }, { 3169, 3300 }, { 3173, 3304 }, { 3173, 3307 }, { 3180, 3307 }, { 3180, 3303 }, { 3183, 3303 }, { 3186, 3301 }, { 3186, 3298 }, { 3187, 3297 }, { 3186, 3295 }, { 3186, 3291 }, { 3184, 3289 }}), new Area(3170, 3300, 3184, 3290), new Position(3177, 3296, 0), new Position(3209, 3218, 2), false),
    LUMBRIDGE_NORTH(new Area(new int[][]{{ 3196, 3282 }, { 3193, 3288 }, { 3193, 3300 }, { 3196, 3302 }, { 3200, 3302 }, { 3201, 3301 }, { 3205, 3301 }, { 3206, 3302 }, { 3209, 3302 }, { 3210, 3301 }, { 3210, 3296 }, { 3213, 3291 }, { 3213, 3290 }, { 3211, 3289 }, { 3212, 3285 }, { 3207, 3284 }}), new Area(3193, 3299, 3210, 3285), new Position(3202, 3293, 0), new Position(3209, 3218, 2), false),
    LUMBRIDGE_EAST(new Area(new int[][]{{ 3253, 3255 }, { 3253, 3273 }, { 3251, 3277 }, { 3246, 3280 }, { 3241, 3285 }, { 3241, 3288 }, { 3243, 3290 }, { 3243, 3293 }, { 3241, 3297 }, { 3256, 3298 }, { 3258, 3299 }, { 3260, 3299 }, { 3262, 3298 }, { 3264, 3298 }, { 3265, 3296 }, { 3265, 3255 }}), new Area(3253, 3272, 3265, 3255), new Position(3259, 3267, 0), new Position(3209, 3218, 2), false),
    LUMBRIDGE_SWAMP_FROG(new Area(new int[][]{{ 3205, 3171 },{ 3193, 3171 },{ 3193, 3177 },{ 3195, 3180 },{ 3198, 3181 },{ 3201, 3181 },{ 3205, 3178 }}), new Area(3192, 3180, 3207, 3171), new Position(3198, 3176, 0), new Position(3209, 3218, 2), false);

    private Area areaChicken;
    private Area areaCows;
    private Area areaFrogs;
    private Area areaBank;
    private Position posChicken;
    private Position posCows;
    private Position posFrogs;
    private Position posBank;
    private boolean isDepositBox;

    private Profile(Area areaNpcs, Area areaBank, Position posNpcs, Position posBank, boolean isDepositBox) {
        this.areaChicken = areaNpcs;
        this.areaCows = areaNpcs;
        this.areaFrogs = areaNpcs;
        this.areaBank = areaBank;
        this.posChicken = posNpcs;
        this.posCows = posNpcs;
        this.posFrogs = posNpcs;
        this.posBank = posBank;
        this.isDepositBox = isDepositBox;
    }

    public Area getChickenArea() {
        return this.areaChicken;
    }

    public Area getCowsArea() {
        return this.areaCows;
    }

    public Area getFrogsArea() {
        return this.areaFrogs;
    }

    public Area getBankArea() {
        return this.areaBank;
    }

    public Position getCowsPosition() {
        return this.posCows;
    }

    public Position getBankPosition() {
        return this.posBank;
    }

    public boolean isDepositBox() {
        return this.isDepositBox;
    }
}
