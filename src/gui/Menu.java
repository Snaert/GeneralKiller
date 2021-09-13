package gui;

import core.Task;
import data.Profile;

import java.awt.Component;
import java.awt.Insets;
import java.util.List;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.border.EmptyBorder;
import org.osbot.rs07.script.Script;
import tasks.*;

public class Menu extends JFrame {
    private Script script;
    private List<Task> tasks;
    private Profile profile;

    public Menu(Script script, List<Task> tasks) {
        this.script = script;
        this.tasks = tasks;
        this.initUI();
    }

    public void initUI() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, 1));
        panel.setBorder(new EmptyBorder(new Insets(10, 15, 10, 15)));
        this.add(panel);
        JPanel topPanel = new JPanel();
        panel.add(topPanel);
        JLabel locationLabel = new JLabel("Location: ");
        JComboBox<String> locationComboBox = new JComboBox(new String[]{"Lumbridge North Chicken", "Lumbridge East Cows", "Lumbridge North Cows", "Lumbridge Swamp Giant Frogs"});
        topPanel.add(locationLabel);
        topPanel.add(locationComboBox);
        JPanel midPanel = new JPanel();
        GroupLayout gl = new GroupLayout(midPanel);
        midPanel.setLayout(gl);
        panel.add(midPanel);
        JLabel modeLabel = new JLabel("Select mode: ");
        JRadioButton fightAndLootRadioButton = new JRadioButton("Fight & Loot (Will bury regular bones)");
        JRadioButton fightOnlyRadioButton = new JRadioButton("Fight only");
        JRadioButton lootOnlyRadioButton = new JRadioButton("Loot only (Cows only)");
        ButtonGroup modeButtonGroup = new ButtonGroup();
        modeButtonGroup.add(fightAndLootRadioButton);
        modeButtonGroup.add(fightOnlyRadioButton);
        modeButtonGroup.add(lootOnlyRadioButton);
        fightAndLootRadioButton.setSelected(true);
        midPanel.add(modeLabel);
        midPanel.add(fightAndLootRadioButton);
        midPanel.add(fightOnlyRadioButton);
        midPanel.add(lootOnlyRadioButton);
        gl.setAutoCreateContainerGaps(true);
        gl.setAutoCreateGaps(true);
        gl.setHorizontalGroup(gl.createSequentialGroup().addGroup(gl.createParallelGroup().addComponent(modeLabel)).addPreferredGap(ComponentPlacement.UNRELATED).addGroup(gl.createParallelGroup().addComponent(fightAndLootRadioButton).addComponent(fightOnlyRadioButton).addComponent(lootOnlyRadioButton)));
        gl.setVerticalGroup(gl.createParallelGroup(Alignment.CENTER).addGroup(gl.createSequentialGroup().addComponent(modeLabel)).addGroup(gl.createSequentialGroup().addComponent(fightAndLootRadioButton).addComponent(fightOnlyRadioButton).addComponent(lootOnlyRadioButton)));
        JPanel botPanel = new JPanel();
        panel.add(botPanel);
        JButton startButton = new JButton("Start");
        startButton.addActionListener((e) -> {
            if (locationComboBox.getSelectedItem().equals("Lumbridge North Chicken")) {
                this.profile = Profile.LUMBRIDGE_NORTH_CHICKEN;
            } else if (locationComboBox.getSelectedItem().equals("Lumbridge East")) {
                this.profile = Profile.LUMBRIDGE_EAST;
            } else if (locationComboBox.getSelectedItem().equals("Lumbridge North")) {
                this.profile = Profile.LUMBRIDGE_NORTH;
            } else {
                this.profile = Profile.LUMBRIDGE_SWAMP_FROG;
            }

            if (fightAndLootRadioButton.isSelected()) {
                this.tasks.add(new AttackLootingChicken(this.script, this.profile));
                this.tasks.add(new AttackLootingCow(this.script, this.profile));
                this.tasks.add(new AttackLootingFrog(this.script, this.profile));
                this.tasks.add(new Walking(this.script, this.profile));
                this.tasks.add(new Banking(this.script, this.profile));
            } else if (fightOnlyRadioButton.isSelected()) {
                this.tasks.add(new AttackingChicken(this.script, this.profile));
                this.tasks.add(new AttackingCow(this.script, this.profile));
                this.tasks.add(new AttackingFrog(this.script, this.profile));
                this.tasks.add(new Walking(this.script, this.profile));
            } else {
                this.tasks.add(new Looting(this.script, this.profile));
                this.tasks.add(new Walking(this.script, this.profile));
                this.tasks.add(new Banking(this.script, this.profile));
            }

            this.setVisible(false);
            this.dispose();
        });
        botPanel.add(startButton);
        this.setTitle("GeneralKillerV3");
        this.pack();
        this.setResizable(false);
        this.setLocationRelativeTo((Component)null);
    }
}