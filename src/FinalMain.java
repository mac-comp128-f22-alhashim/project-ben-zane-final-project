import edu.macalester.graphics.*;
import java.awt.Color;
import java.util.Scanner;

public class FinalMain {

    private static CanvasWindow canvas;
    private static TileManager tm;
    private static MapManagement mm;

    private static final int CANVAS_WIDTH = 650;
    private static final int CANVAS_HEIGHT = 650;

    public FinalMain(){
        canvas = new CanvasWindow("Sequence Game", CANVAS_WIDTH, CANVAS_HEIGHT);
        canvas.setBackground(Color.BLACK);
    }

    /*
    * Runs the game
    */
    public static void main(String[] args) {
        FinalMain game = new FinalMain();
        tm = new TileManager(canvas);
        mm = new MapManagement();
        mm.dimensionsGenerator(10);

        tm.createAllTiles(mm.dimensionsGenerator(30), 5);

        for (int i = 0; i < 10; i++) {
            tm.createRandomSequence();
            canvas.pause(2000);
        }        

        canvas.onClick(event -> {
            
        });
    }

    // so there should be a phase where the sequence has 1 element
    // then the user clicks it
    // then the sequence has 2 elements
    // then the user clicks them
    // then keep going until the user fails

}
