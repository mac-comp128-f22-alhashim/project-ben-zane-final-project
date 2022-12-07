import edu.macalester.graphics.CanvasWindow;
import edu.macalester.graphics.FontStyle;
import edu.macalester.graphics.GraphicsObject;
import edu.macalester.graphics.GraphicsText;
import edu.macalester.graphics.events.Key;
import edu.macalester.graphics.ui.Button;

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
    private boolean strobesEnabled;

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

    public SequenceGame(){
        level = 1;
        running = true;
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
            if (!running) return;
            
            // Strobes tiles in a random pattern when the user clicks
            if (strobesEnabled) tileManage.strobeAll();

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
        GraphicsText options = new GraphicsText();
        GraphicsText instructions = new GraphicsText();

        Button diffEasy = new Button("Easy");
        Button diffStd = new Button("Standard");
        Button diffHard = new Button("Hard");
        Button diffExtr = new Button("Extreme");
        Button quit = new Button("Exit");
        Button setShapeEllipse = new Button("Ellipse Mode");
        Button setShapeRect = new Button("Rectangle Mode");
        Button toggleStrobeOn = new Button("Enable Color Randomizer");
        Button toggleStrobeOff = new Button("Disable Color Randomizer");

        title.setFillColor(Color.WHITE);
        title.setText("Sequence Game");
        title.setFont(FontStyle.BOLD, SIDE * 0.075);
        title.setCenter(CANVAS_CTR, SIDE * 0.20);

        options.setFillColor(Color.WHITE);
        options.setText("Or, customize game options:");
        options.setFont(FontStyle.PLAIN, SIDE * 0.030);
        options.setCenter(CANVAS_CTR, title.getCenter().getY() * 3);

        chooseDiff.setFillColor(Color.WHITE);
        chooseDiff.setText("Select a Difficulty to Begin:");
        chooseDiff.setFont(FontStyle.PLAIN, SIDE * 0.035);
        chooseDiff.setCenter(CANVAS_CTR, title.getCenter().getY() * 1.350);

        instructions.setFillColor(Color.WHITE);
        instructions.setText("How to play: Click on the tiles in the order they appear!");
        instructions.setFont(FontStyle.PLAIN, SIDE * 0.030);
        instructions.setCenter(CANVAS_CTR, SIDE * 0.9);

        toggleStrobeOn.setCenter(CANVAS_CTR, options.getCenter().getY() * 1.07);
        toggleStrobeOff.setCenter(CANVAS_CTR, toggleStrobeOn.getCenter().getY() * 1.07);
        setShapeEllipse.setCenter(CANVAS_CTR, toggleStrobeOff.getCenter().getY() * 1.07);
        setShapeRect.setCenter(CANVAS_CTR, setShapeEllipse.getCenter().getY() * 1.065);

        diffEasy.setCenter(CANVAS_CTR, chooseDiff.getCenter().getY() * 1.250);
        diffStd.setCenter(CANVAS_CTR, diffEasy.getCenter().getY() * 1.150);
        diffHard.setCenter(CANVAS_CTR, diffStd.getCenter().getY() * 1.125);
        diffExtr.setCenter(CANVAS_CTR, diffHard.getCenter().getY() * 1.105);

        quit.setCenter(CANVAS_CTR, canvas.getHeight() * 0.950);

        canvas.setBackground(Color.decode("#2A87D1"));

        canvas.add(title);
        canvas.add(options);
        canvas.add(chooseDiff);
        canvas.add(enterCustDiff);
        canvas.add(instructions);

        canvas.add(toggleStrobeOn);
        canvas.add(toggleStrobeOff);

        canvas.add(setShapeEllipse);
        canvas.add(setShapeRect);
        
        canvas.add(diffEasy);
        canvas.add(diffStd);
        canvas.add(diffHard);
        canvas.add(diffExtr);
        canvas.add(quit);

        diffEasy.onClick(() -> prepCanvasForFirstRun(DIFF_EASY));
        diffStd.onClick(() -> prepCanvasForFirstRun(DIFF_STD));
        diffHard.onClick(() -> prepCanvasForFirstRun(DIFF_HARD));
        diffExtr.onClick(() -> prepCanvasForFirstRun(DIFF_EXTR));

        toggleStrobeOff.onClick(() -> {
            options.setText("Color randomizer disabled!");
            strobesEnabled = false;
        });

        toggleStrobeOn.onClick(() -> {
            options.setText("Color randomizer enabled!");
            strobesEnabled = true;
        });

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
        tileManage.createRandomSequence();
        userSequence.addAll(tileManage.gameSequence);
        
        canvas.draw();
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
