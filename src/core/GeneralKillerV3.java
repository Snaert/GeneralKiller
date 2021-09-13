package core;

import gui.Menu;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.osbot.rs07.script.Script;
import org.osbot.rs07.script.ScriptManifest;
import paint.Paint;

@ScriptManifest(
        author = "Snaert",
        name = "GeneralKillerV3",
        info = "Slays what we want and loots too",
        version = 0.61,
        logo = ""
)
public class GeneralKillerV3 extends Script {
    private Paint paint;
    private Task currentTask;
    private List<Task> tasks = new ArrayList();

    public void onStart() {
        Menu menu = new Menu(this, this.tasks);
        menu.setVisible(true);

        while(menu.isVisible()) {
            try {
                sleep(1000L);
            } catch (InterruptedException var3) {
                var3.printStackTrace();
            }
        }

        this.paint = new Paint(this);
    }

    public int onLoop() throws InterruptedException {
        Iterator var2 = this.tasks.iterator();

        while(var2.hasNext()) {
            Task t = (Task)var2.next();
            if (t.validate()) {
                this.currentTask = t;
                if (t.execute()) {
                    return 0;
                }
            }
        }

        return random(200, 400);
    }

    public void onPaint(Graphics2D g) {
        this.paint.render(g, this.currentTask);
    }
}