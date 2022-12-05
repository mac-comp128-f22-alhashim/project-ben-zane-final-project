import edu.macalester.graphics.*;
import edu.macalester.graphics.events.Key;

import java.awt.Color;
import java.util.ArrayDeque;

public class SequenceGame {
    /*
     * TODO (remove lines as completed or add as necessary)
     * 
     * 
     * IMPORTANT!!!!!!!!!!!!!!!!!!!!!!!!!!1
     * I still get issues where if I click while the screen is showing the sequence the game can crash...
     * 
     * Add difficulty selector, which changes the grid size.
     * Include easy (3x3), medium (5x5), hard (10x10), custom (user input)
     * Potentially change the squares to ellipses as an option for the user to choose between
     * Clean up code:
     *      Put canvas dimensions into one variable (it's a square)
     *      Decomposition in all classes
     *      Potentially remove MapManagement and move into TileManager
     *      Refactor for readability (variable names, comments/javadoc, structure + organization, etc.)
     */
    private int level;
    private boolean running;
    private boolean sequenceIsAnimating;
    private ArrayDeque<Tile> userSequence;  // Tiles will be popped from this deque.

    private CanvasWindow canvas;
    private TileManager tileManage;
    private MapManagement mapManage;
    private GraphicsText levelLable;

    private static final int SIDE = 650;

    public SequenceGame(){
        level = 1;
        running = true;
        sequenceIsAnimating = false;
        userSequence = new ArrayDeque<>();

        canvas = new CanvasWindow("Sequence Game", SIDE, SIDE);
        tileManage = new TileManager(canvas);
        mapManage = new MapManagement();

        levelLable = new GraphicsText();
        
        canvas.onKeyDown(event -> {
            if(running == false){
                if (event.getKey().equals(Key.ESCAPE)) {
                    System.exit(0);
                }
    
                if (event.getKey().equals(Key.SPACE)) {
                    canvas.removeAll();
                    init();
                }
            }
        });

        canvas.onClick(event -> {
            if (running && !sequenceIsAnimating) {
                tileManage.strobeAll();                             // This is for the rainbow mode
                
                GraphicsObject clickedElement = canvas.getElementAt(event.getPosition());

                // If the sequence deque is not empty, i.e., the user still has to click more tiles
                if (!userSequence.isEmpty()) {

                    // If the element the user clicked on is a Tile
                    if (clickedElement instanceof Tile) {

                        // If the element the user clicked on is the correct Tile in the sequence
                        if (clickedElement.equals(userSequence.peek())) {

                            // Provide visual feedback of the clicked tile
                            userClickedTile(clickedElement);

                            // Remove the Tile from the sequence
                            userSequence.pop();

                            // If the user sequence is empty, i.e., the level is done, we call changeLevel to update the canvas accordingly.
                            if (userSequence.isEmpty()) {
                                changeLevel();
                            }
                        } else {
                            // The user clicked on an incorrect tile, so we stop the game.
                            running = false;
                            gameLose();
                        }
                    }
                }
            }
        });
    }

    private void userClickedTile(GraphicsObject clickedElement) {
        colorTile((Tile) clickedElement, Color.WHITE);
        canvas.pause(200);
        colorTile((Tile) clickedElement, TileManager.STD_COLOR);
    }

    private void changeLevel() {
        level++;
        levelLable.setText("Level: " + level);
        canvas.draw();
        canvas.pause(1000);
        populateTiles();
    }

    private void init() {
        // Game is running!
        running = true;

        // Reset level count to 1
        level = 1;

        // Set canvas background color
        canvas.setBackground(Color.decode("#2A87D1"));

        // Creates tiles and grid, will need to be adapted to take in an input for the difficulty
        tileManage.createAllTiles(mapManage.dimensionsGenerator(30), 5);

        // Set level label color
        levelLable.setFillColor(Color.WHITE);
        levelLable.setText("Level: " + level);
        levelLable.setFont(FontStyle.PLAIN, SIDE * 0.04);
        levelLable.setCenter(SIDE * 0.50, SIDE * 0.055);

        canvas.add(levelLable);

        canvas.draw();

        canvas.pause(1000);

        // Generate sequence and populate grid for the first time
        populateTiles();
    }

    private void populateTiles() {
        sequenceIsAnimating = true;
        
        tileManage.createRandomSequence();
        userSequence.addAll(tileManage.gameSequence);
        canvas.draw();

        sequenceIsAnimating = false;
    }

    private void colorTile(Tile tile, Color color) {
        tile.setFillColor(color);
        canvas.draw();
    }

    private void gameLose() {
        running = false;

        canvas.removeAll();
        wipeSequence();

        GraphicsText loseLabel = new GraphicsText();
        GraphicsText instructLabel = new GraphicsText();

        loseLabel.setText("You lost at level " + level + "!");
        loseLabel.setFillColor(Color.WHITE);
        loseLabel.setFont(FontStyle.PLAIN, SIDE * 0.045);
        loseLabel.setCenter(SIDE * 0.5, SIDE * 0.5);
        canvas.add(loseLabel);

        instructLabel.setText("Press SPACE to play again, or ESCAPE to quit.");
        instructLabel.setFillColor(Color.WHITE);
        instructLabel.setFont(FontStyle.PLAIN, SIDE * 0.035);
        instructLabel.setCenter(SIDE * 0.5, loseLabel.getCenter().getY() * 1.15);
        canvas.add(instructLabel);

        canvas.draw();

     
    }

    private void wipeSequence() {
        userSequence.clear();
        tileManage.clearAllLists();
    }

    /*
    * Runs the game
    */
    public static void main(String[] args) {
        SequenceGame game = new SequenceGame();
        
        game.init();
    }
}
