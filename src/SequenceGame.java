import edu.macalester.graphics.*;
import java.awt.Color;
import java.util.ArrayDeque;

public class SequenceGame {
    /*
     * TODO:
     * Make the canvas actually wait for the user to click and not just immediately die
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

        canvas.setBackground(Color.decode("#2A87D1"));

        canvas.onClick(event -> {
            if (running) {
                GraphicsObject clickedElement = canvas.getElementAt(event.getPosition());

                // While the sequence deque is not empty, i.e., the user still has to click more tiles
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
                        } else {
                            running = false;
                        }
                    }
                } else {
                    level++;
                    levelLable.setText("Level: " + level);
                    populateTiles();
                }
            }
        });
    }

    private void init() {
        // Reset level count to 1
        level = 1;

        // Creates dimensions
        mapManage.dimensionsGenerator(10);

        // Creates tiles and grid, will need to be adapted to take in an input for the difficulty
        tileManage.createAllTiles(mapManage.dimensionsGenerator(30), 5);

        levelLable.setFillColor(Color.WHITE);
        levelLable.setText("Level: " + level);
        levelLable.setFont(FontStyle.PLAIN, CANVAS_HEIGHT * 0.04);
        levelLable.setCenter(CANVAS_WIDTH * 0.50, CANVAS_HEIGHT * 0.055);

        canvas.add(levelLable);

        populateTiles();
    }

    private void populateTiles() {
        tileManage.createRandomSequence();
        sequence.addAll(tileManage.sequence);
    }

    private void colorTile(Tile tile, Color color) {
        tile.setFillColor(color);
    }

    /*
    * Runs the game
    */
    public static void main(String[] args) {
        SequenceGame game = new SequenceGame();
        
        game.init();
    }
}
