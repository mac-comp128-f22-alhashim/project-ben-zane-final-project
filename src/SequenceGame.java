import edu.macalester.graphics.*;
import edu.macalester.graphics.events.Key;      //maybe these arent needed if we have graphics*
import edu.macalester.graphics.ui.Button;
// import edu.macalester.graphics.ui.TextField;

import java.awt.Color;
import java.util.ArrayDeque;

public class SequenceGame {
    /*
     * TODO (remove lines as completed or add as necessary)
     * 
     * Fix issue where if the user clicks on the canvas when the sequence is animating, the clicks are registered
     * 
     * Potentially change the squares to ellipses as an option for the user to choose between
     * Clean up code:
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

    private final int SIDE = 650;
    private final int CANVAS_CTR = SIDE/2;
    private final int DIFF_EASY = 3;
    private final int DIFF_STD = 5;
    private final int DIFF_HARD = 7;
    private final int DIFF_EXTR = 10;

    Color transparent = new Color(250, 150, 40, 100);              
    Blocker blocker = new Blocker(0, 0, SIDE, SIDE, transparent);  

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
            if(!running){
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
            if (sequenceIsAnimating || running==false){
                return;
            }
            // Strobes tiles in a random pattern when the user clicks
            tileManage.strobeAll();                            
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
        GraphicsText title = new GraphicsText();
        GraphicsText chooseDiff = new GraphicsText();
        GraphicsText enterCustDiff = new GraphicsText();

        GraphicsText instructions = new GraphicsText();
        // TextField custDiff = new TextField();

        Button diffEasy = new Button("Easy");
        Button diffStd = new Button("Standard");
        Button diffHard = new Button("Hard");
        Button diffExtr = new Button("Extreme");
        Button quit = new Button("Exit");        


        title.setFillColor(Color.WHITE);
        title.setText("Sequence Game");
        title.setFont(FontStyle.BOLD, SIDE * 0.075);
        title.setCenter(CANVAS_CTR, SIDE * 0.250);

        instructions.setFillColor(Color.WHITE);
        instructions.setText("Click on the tiles in the order they appear");
        instructions.setFont(FontStyle.PLAIN, SIDE * 0.030);
        instructions.setCenter(CANVAS_CTR, title.getCenter().getY() * 3);



        chooseDiff.setFillColor(Color.WHITE);
        chooseDiff.setText("Select a Difficulty to Begin:");
        chooseDiff.setFont(FontStyle.PLAIN, SIDE * 0.035);
        chooseDiff.setCenter(CANVAS_CTR, title.getCenter().getY() * 1.350);

        // enterCustDiff.setFillColor(Color.WHITE);
        // enterCustDiff.setText("Or, enter a custom grid size below:");
        // enterCustDiff.setFont(FontStyle.PLAIN, SIDE * 0.030);

        diffEasy.setCenter(CANVAS_CTR, chooseDiff.getCenter().getY() * 1.250);
        diffStd.setCenter(CANVAS_CTR, diffEasy.getCenter().getY() * 1.150);
        diffHard.setCenter(CANVAS_CTR, diffStd.getCenter().getY() * 1.125);
        diffExtr.setCenter(CANVAS_CTR, diffHard.getCenter().getY() * 1.105);

        quit.setCenter(CANVAS_CTR, canvas.getHeight() * 0.950);

        // enterCustDiff.setCenter(CANVAS_CTR, quit.getCenter().getY() * 0.850);
        // custDiff.setCenter(CANVAS_CTR, enterCustDiff.getCenter().getY() * 1.050);

        // Set canvas background color
        canvas.setBackground(Color.decode("#2A87D1"));

        canvas.add(title);
        canvas.add(instructions);
        canvas.add(chooseDiff);
        canvas.add(enterCustDiff);

        canvas.add(diffEasy);
        canvas.add(diffStd);
        canvas.add(diffHard);
        canvas.add(diffExtr);
        canvas.add(quit);
        // canvas.add(custDiff);

        diffEasy.onClick(() -> prepCanvasForFirstRun(DIFF_EASY));
        diffStd.onClick(() -> prepCanvasForFirstRun(DIFF_STD));
        diffHard.onClick(() -> prepCanvasForFirstRun(DIFF_HARD));
        diffExtr.onClick(() -> prepCanvasForFirstRun(DIFF_EXTR));

        quit.onClick(() -> System.exit(0));
    }

    private void prepCanvasForFirstRun(int difficulty) {
        canvas.removeAll();
        
        // Reset level count to 1
        level = 1;

        running = true;

        // Creates tiles and grid, will need to be adapted to take in an input for the difficulty
        tileManage.createAllTiles(mapManage.dimensionsGenerator(10), difficulty);

        // Set level label color
        levelLable.setFillColor(Color.WHITE);
        levelLable.setText("Level: " + level);
        levelLable.setFont(FontStyle.PLAIN, SIDE * 0.04);
        levelLable.setCenter(CANVAS_CTR, SIDE * 0.055);
 
        canvas.add(levelLable);
 
        canvas.draw();
 
        canvas.pause(1000);
 
        // Generate sequence and populate grid for the first time
        running = false;
        populateTiles();
        running = true;

    }

    private void populateTiles() {
        
        canvas.add(blocker);
        canvas.draw();
        sequenceIsAnimating = true;

        tileManage.createRandomSequence();
        userSequence.addAll(tileManage.gameSequence);
        canvas.draw();
        sequenceIsAnimating = false;
        canvas.remove(blocker);

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
        loseLabel.setCenter(CANVAS_CTR, SIDE * 0.5);
        canvas.add(loseLabel);

        instructLabel.setText("Press SPACE to play again, or ESCAPE to quit.");
        instructLabel.setFillColor(Color.WHITE);
        instructLabel.setFont(FontStyle.PLAIN, SIDE * 0.035);
        instructLabel.setCenter(CANVAS_CTR, loseLabel.getCenter().getY() * 1.15);
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
