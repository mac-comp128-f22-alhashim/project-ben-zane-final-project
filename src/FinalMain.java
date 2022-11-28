import edu.macalester.graphics.*;
import java.awt.Color;
import java.util.Scanner;

public class FinalMain {

    private CanvasWindow canvas;
    private static  int CANVAS_WIDTH = 700;
    private static  int CANVAS_HEIGHT = 700;
    
    private int gameLives = 5;                  
    private GraphicsText lives;
    Scanner scanner;


    public FinalMain(){
        canvas = new CanvasWindow("Memory Game", CANVAS_WIDTH, CANVAS_HEIGHT);
        canvas.setBackground(Color.GRAY);
        lives = new GraphicsText("Lives:" + gameLives, CANVAS_WIDTH/2, 50);
    }

    /*
    * Runs the game
    */
    public static void main(String[] args) {
        MemoryTestGame game = new MemoryTestGame();
        game.runGame();
    }

    /*
    * Creates a user input to choose grid size and contains the methods for the game's functions
    */
    public void runGame(){
        System.out.println(
            "Please enter a number. Grids range from 3x3 to 11x11. If you want a 3x3 grid you would just write 3.");
        scanner = new Scanner(System.in);
        int levelSize = scanner.nextInt();
        chooseGrid(levelSize);
        scanner.close();
    }

    

    
}
