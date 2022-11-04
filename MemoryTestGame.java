import edu.macalester.graphics.*;
import java.awt.Color;
import java.util.Scanner;

/**
 * Comp 127 Final Project
 * Professor Joslenne Peña
 * 
 * @authors Zane, Owen, and Noah
 * Help from Erik Borgehammar, Julia Kispert, Myles Klapkowski, Isabella Valdivia and Dino Weinstock
 * 
 * A class that holds the main and run methods for the Memory Test Game
 */
public class MemoryTestGame {

    private CanvasWindow canvas;
    private static final int CANVAS_WIDTH = 800;
    private static final int CANVAS_HEIGHT = 800;
    
    private int gameLives = 5;                  
    private GraphicsText lives;
    Scanner scanner;

    /**
     * Constructs a MemoryTestGame
     */
    public  MemoryTestGame(){
        canvas = new CanvasWindow("Memory Game", CANVAS_WIDTH, CANVAS_HEIGHT);
        canvas.setBackground(Color.GRAY);
        lives = new GraphicsText("Lives:" + gameLives, 400, 50);
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
    
    /**
     * Makes a level from the user input ranging 3 to 11
     */
    public void chooseGrid(int levelSize){
        if(levelSize == 3){
            TileManager tm9 = new TileManager(canvas, 200, 200, 9);
            levelMaker(tm9);
        }
        if(levelSize == 4){
            TileManager tm16 = new TileManager(canvas, 150, 150, 16); 
            levelMaker(tm16);
        }
        if(levelSize == 5){
            TileManager tm25 = new TileManager(canvas, 120, 120, 25);
            levelMaker(tm25);
        }
        if(levelSize == 6){
            TileManager tm36 = new TileManager(canvas, 100, 100, 36);
            levelMaker(tm36);
        }
        if(levelSize == 7){
            TileManager tm49 = new TileManager(canvas, (600/7), (600/7), 49);
            levelMaker(tm49);
        }
        if(levelSize == 8){
            TileManager tm64 = new TileManager(canvas, 75, 75, 64);
            levelMaker(tm64);
        }
        if(levelSize == 9){
            TileManager tm81 = new TileManager(canvas, 600/9, 600/9, 81);
            levelMaker(tm81);
        }
        if(levelSize == 10){
            TileManager tm100 = new TileManager(canvas, 60, 60, 100);
            levelMaker(tm100);
        }
        if(levelSize == 11){
            TileManager tm121 = new TileManager(canvas, 600/11, 600/11, 121);
            levelMaker(tm121);
        }
    }
    
    /**
    *   Runs lambda to detect clicks on TileManager, finds the point of click and runs code
    *   depending on the correct status of the tile clicked, either turing cyan or black
    *
    *   Also runs the loss method when lives fall below 1
    */
    public boolean clicking(TileManager tm){ 
        canvas.onClick(event -> { 
            Point pp = event.getPosition();
            GraphicsObject graphics = canvas.getElementAt(pp);
                if(graphics instanceof Tile){             
                    int x = tm.returnIndex((Tile) graphics);
                    if(tm.ifCorrectTileisClicked(x)){
                        ((Tile) graphics).onRightClick();
                        canvas.draw();
                        if(tm.compareTheTiles() == true){
                            canvas.pause(1000);
                            tm.clearAllLists();
                            tm.removeAllTiles();
                            winGame();
                        }
                    }
                    else{
                        ((Tile) graphics).onWrongClick();
                        gameLives -= 1;
                        lifeCounter();
                        loseGame();
                    }            
                }
        });
        if(tm.compareTheTiles()){
            return true;
        }
        else{
            return false;
        }
    }
    
    /**
    * Runs each componant to play through one round, updating displays, flashing the 
    * sequence, and redrawing the canvas to be clickable.
    */
    public void levelMaker(TileManager tm){
        lifeCounter();
        tm.createAllTiles();
        drawPause();
        tm.generateSequence();
        drawPause();
        tm.removeCorrectTiles();
        canvas.draw();
        drawPause();
        clicking(tm);            
    }

    /**
     * Controls how long the sequence is seen on screen
     */
    public void drawPause(){
        canvas.draw();
        canvas.pause(1000);
    }
    
    /**
    * Conditional method allowing game win conditions to be activated
    */
    public void winGame(){
        canvas.removeAll();
        GraphicsText youWon = new GraphicsText("You won!", 0, 0);
        youWon.setFontSize(56);
        youWon.setCenter(400,400);
        canvas.add(youWon);
        canvas.draw();
        System.out.println("You won!");
        canvas.pause(3000);
        canvas.closeWindow();
    }

    /**
    * Conditional method allowing game loss conditions to be activated
    */
    public void loseGame(){             
        if(gameLives == 0){
            canvas.removeAll();
            GraphicsText youLose = new GraphicsText("You lost!", 0, 0);
            youLose.setFontSize(56);
            youLose.setCenter(400,400);
            canvas.add(youLose);
            canvas.draw();
            System.out.println("You lost!");
            canvas.pause(3000);
            canvas.closeWindow();
        }
    }

    /**
    * A method used to display the number of lives on screen
    */
    public void lifeCounter(){
        lives.setText("Lives:" + gameLives);
        lives.setFontSize(25);
        lives.setCenter(400, 50);
        canvas.add(lives);
    }
}
