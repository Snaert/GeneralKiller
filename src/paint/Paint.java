package paint;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.Point;
import java.awt.Font;
import core.Task;
import java.awt.Graphics2D;
import java.text.DecimalFormat;
import org.osbot.rs07.api.ui.Skill;
import java.awt.Rectangle;
import java.awt.Color;
import org.osbot.rs07.script.Script;

public class Paint {
    private Script script;
    private final Color COLOR_BLUE;
    private final Color COLOR_GREEN;
    private final Color COLOR_YELLOW;
    private final Color COLOR_RED;
    private Color COLOR_SELECTED;
    private final Rectangle HIDE;
    private final Rectangle ATTACK;
    private final Rectangle STRENGTH;
    private final Rectangle DEFENCE;
    private final Rectangle CONSTITUTION;
    private final long startTime;
    private long runTime;
    private boolean isHide;
    private long timeToLevel;
    private Skill currentSkill;
    private int startLevel;
    private int startExperience;
    private final int startLevelAttack;
    private final int startExperienceAttack;
    private final int startLevelStrength;
    private final int startExperienceStrength;
    private final int startLevelDefence;
    private final int startExperienceDefence;
    private final int startLevelHitpoints;
    private final int startExperienceHitpoints;
    private int currentExperience;
    private int gainedLevel;
    private int gainedExperience;
    private int experiencePerHour;
    private int pNextLevel;
    private int pCurrentLevel;
    private int pTotalExperience;
    private int pGainedExperience;
    private int experienceToLevel;
    private int percent;
    private final DecimalFormat formatter;
    private static int[] expTable;

    static {
        Paint.expTable = new int[]{0, 0, 83, 174, 276, 388, 512, 650, 801, 969, 1154, 1358, 1584, 1833, 2107, 2411, 2746, 3115, 3523, 3973, 4470, 5018, 5624, 6291, 7028, 7842, 8740, 9730, 10824, 12031, 13363, 14833, 16456, 18247, 20224, 22406, 24815, 27473, 30408, 33648, 37224, 41171, 45529, 50339, 55649, 61512, 67983, 75127, 83014, 91721, 101333, 111945, 123660, 136594, 150872, 166636, 184040, 203254, 224466, 247886, 273742, 302288, 333804, 368599, 407015, 449428, 496254, 547953, 605032, 668051, 737627, 814445, 899257, 992895, 1096278, 1210421, 1336443, 1475581, 1629200, 1798808, 1986068, 2192818, 2421087, 2673114, 2951373, 3258594, 3597792, 3972294, 4385776, 4842295, 5346332, 5902831, 6517253, 7195629, 7944614, 8771558, 9684577, 10692629, 11805606, 13034431};
    }

    public Paint(final Script script) {
        this.COLOR_BLUE = new Color(67, 122, 213, 255);
        this.COLOR_GREEN = new Color(96, 203, 10, 255);
        this.COLOR_YELLOW = new Color(214, 205, 86, 255);
        this.COLOR_RED = new Color(186, 20, 24, 255);
        this.COLOR_SELECTED = this.COLOR_BLUE;
        this.HIDE = new Rectangle(495, 245, 10, 10);
        this.ATTACK = new Rectangle(5, 285, 20, 20);
        this.STRENGTH = new Rectangle(26, 285, 20, 20);
        this.DEFENCE = new Rectangle(47, 285, 20, 20);
        this.CONSTITUTION = new Rectangle(68, 285, 20, 20);
        this.isHide = false;
        this.formatter = new DecimalFormat("###,###,###");
        this.script = script;
        this.startTime = System.currentTimeMillis();
        this.startLevelAttack = script.getSkills().getStatic(Skill.ATTACK);
        this.startExperienceAttack = script.getSkills().getExperience(Skill.ATTACK);
        this.startLevelStrength = script.getSkills().getStatic(Skill.STRENGTH);
        this.startExperienceStrength = script.getSkills().getExperience(Skill.STRENGTH);
        this.startLevelDefence = script.getSkills().getStatic(Skill.DEFENCE);
        this.startExperienceDefence = script.getSkills().getExperience(Skill.DEFENCE);
        this.startLevelHitpoints = script.getSkills().getStatic(Skill.HITPOINTS);
        this.startExperienceHitpoints = script.getSkills().getExperience(Skill.HITPOINTS);
        this.startLevel = this.startLevelAttack;
        this.startExperience = this.startExperienceAttack;
        this.currentSkill = Skill.ATTACK;
    }

    public void render(final Graphics2D g, final Task task) {
        this.runTime = (System.currentTimeMillis() - this.startTime) / 1000L;
        this.currentExperience = this.script.getSkills().getExperience(this.currentSkill);
        this.gainedLevel = this.script.getSkills().getStatic(this.currentSkill) - this.startLevel;
        this.gainedExperience = this.currentExperience - this.startExperience;
        this.experiencePerHour = (int) (this.gainedExperience * 3600000.0 / (System.currentTimeMillis() - this.startTime));
        this.pNextLevel = Paint.expTable[this.script.getSkills().getStatic(this.currentSkill) + 1];
        this.pCurrentLevel = Paint.expTable[this.script.getSkills().getStatic(this.currentSkill)];
        this.pTotalExperience = this.pNextLevel - this.pCurrentLevel;
        this.pGainedExperience = this.currentExperience - this.pCurrentLevel;
        this.experienceToLevel = this.pTotalExperience - this.pGainedExperience;
        this.percent = (int) Math.round(this.pGainedExperience / (double) this.pTotalExperience * 100.0);
        this.timeToLevel = (long) (this.experienceToLevel * 3600000.0 / this.experiencePerHour) / 1000L;
        g.setColor(new Color(0, 0, 0, 160));
        g.fillRect(327, 240, 185, 20);
        g.setFont(new Font("Ubuntu", 1, 11));
        g.setColor(Color.WHITE);
        g.drawString(String.valueOf(this.script.getName()) + " v" + this.script.getVersion(), 365, 255);
        g.setColor(new Color(0, 0, 0, 80));
        g.fillRect(327, 260, 185, 75);
        g.setFont(new Font("Ubuntu", 0, 11));
        g.setColor(Color.WHITE);
        g.drawString("Runtime: " + String.format("%02d:%02d:%02d", this.runTime / 3600L, this.runTime % 3600L / 60L, this.runTime % 60L), 340, 275);
        g.drawString("Status: " + task.status(), 340, 295);
        g.setColor(Color.BLACK);
    }
}
