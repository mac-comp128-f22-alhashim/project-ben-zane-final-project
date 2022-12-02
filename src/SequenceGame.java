import edu.macalester.graphics.*;
import edu.macalester.graphics.events.Key;

import java.awt.Color;
import java.util.ArrayDeque;

public class SequenceGame {
    /*
     * TODO:
     * Add difficulty selector, which changes the grid size.
     * Have the tiles randomly change color
     */
    private int level;
    private boolean running;
    private ArrayDeque<Tile> sequence;

    private CanvasWindow canvas;
    private TileManager tileManage;
    private MapManagement mapManage;
    private GraphicsText levelLable;

    private static final int CANVAS_WIDTH = 650;
    private static final int CANVAS_HEIGHT = 650;

    public SequenceGame(){
        level = 1;
        running = true;
        sequence = new ArrayDeque<>();

        canvas = new CanvasWindow("Sequence Game", CANVAS_WIDTH, CANVAS_HEIGHT);
        tileManage = new TileManager(canvas);
        mapManage = new MapManagement();

        levelLable = new GraphicsText();

        canvas.onClick(event -> {
            if (running) {
                GraphicsObject clickedElement = canvas.getElementAt(event.getPosition());

                // If the sequence deque is not empty, i.e., the user still has to click more tiles
                if (!sequence.isEmpty()) {

                    // If the element the user clicked on is a Tile
                    if (clickedElement instanceof Tile) {

                        // If the element the user clicked on is the correct Tile in the sequence
                        if (clickedElement.equals(sequence.peek())) {

                            // Provide visual feedback of the clicked tile
                            colorTile((Tile) clickedElement, Color.WHITE);
                            canvas.pause(200);
                            colorTile((Tile) clickedElement, TileManager.STD_COLOR);

                            // Remove the Tile from the sequence
                            sequence.pop();

                            if (sequence.isEmpty()) {
                                level++;
                                levelLable.setText("Level: " + level);
                                canvas.draw();
                                canvas.pause(1000);
                                populateTiles();
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
        levelLable.setFont(FontStyle.PLAIN, CANVAS_HEIGHT * 0.04);
        levelLable.setCenter(CANVAS_WIDTH * 0.50, CANVAS_HEIGHT * 0.055);

        canvas.add(levelLable);

        canvas.draw();

        canvas.pause(1000);

        // Generate sequence and populate grid for the first time
        populateTiles();
    }

    private void populateTiles() {
        tileManage.createRandomSequence();
        sequence.addAll(tileManage.sequence);
        canvas.draw();
    }

    private void colorTile(Tile tile, Color color) {
        tile.setFillColor(color);
        canvas.draw();
    }

    private void gameLose() {
        canvas.removeAll();
        sequence.clear();
        tileManage.clearAllLists();

        GraphicsText loseLabel = new GraphicsText();
        GraphicsText instructLabel = new GraphicsText();

        loseLabel.setText("You lost at level " + level + "!");
        loseLabel.setFillColor(Color.WHITE);
        loseLabel.setFont(FontStyle.PLAIN, CANVAS_HEIGHT * 0.045);
        loseLabel.setCenter(CANVAS_WIDTH * 0.5, CANVAS_HEIGHT * 0.5);
        canvas.add(loseLabel);

        instructLabel.setText("Press SPACE to play again, or ESCAPE to quit.");
        instructLabel.setFillColor(Color.WHITE);
        instructLabel.setFont(FontStyle.PLAIN, CANVAS_HEIGHT * 0.035);
        instructLabel.setCenter(CANVAS_WIDTH * 0.5, loseLabel.getCenter().getY() * 1.15);
        canvas.add(instructLabel);

        canvas.draw();

        canvas.onKeyDown(event -> {
            if (event.getKey().equals(Key.ESCAPE)) {
                System.exit(0);
            }

            if (event.getKey().equals(Key.SPACE)) {
                canvas.removeAll();
                init();
            }
        });
    }

    /*
    * Runs the game
    */
    public static void main(String[] args) {
        SequenceGame game = new SequenceGame();
        
        game.init();
    }
}
