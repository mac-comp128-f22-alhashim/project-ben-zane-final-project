import edu.macalester.graphics.Rectangle;
import java.awt.Color;


    
public class Blocker extends Rectangle {

    public Blocker(double topX, double topY, double WIDTH, double HEIGHT, Color color){
        super(topX, topY, WIDTH, HEIGHT);
        this.setFillColor(color);
    }



}
