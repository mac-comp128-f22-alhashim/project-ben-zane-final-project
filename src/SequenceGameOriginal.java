import edu.macalester.graphics.*;
import java.awt.Color;
import java.util.ArrayDeque;

public class SequenceGameOriginal {
    /*
     * TODO:
     * Make the canvas actually wait for the user to click
     * Add difficulty selector, which changes the grid size.
     * Have the tiles randomly change color
     */
    private int level;
    private boolean running;

    private CanvasWindow canvas;
    private TileManager tileManage;
    private MapManagement mapManage;
    private GraphicsText levelLable;

    private static final int CANVAS_WIDTH = 650;
    private static final int CANVAS_HEIGHT = 650;

    public SequenceGameOriginal(){
        level = 1;
        running = true;

        canvas = new CanvasWindow("Sequence Game", CANVAS_WIDTH, CANVAS_HEIGHT);
        tileManage = new TileManager(canvas);
        mapManage = new MapManagement();

        levelLable = new GraphicsText();

        canvas.setBackground(Color.decode("#2A87D1"));

        canvas.animate(() -> {
            if (running) {
                handleGameLogic();
            } else {
                levelLable.setText("You lost");
                canvas.pause(5000);
                System.exit(0);
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
    }

    public void handleGameLogic() {
        tileManage.createRandomSequence();

        ArrayDeque<Tile> modSequence = new ArrayDeque<>();
        modSequence.addAll(tileManage.sequence);

        // We need the program to wait until the sequence deque is empty or the user selects the wrong tile.
        canvas.onClick(event -> {
            GraphicsObject clickedElement = canvas.getElementAt(event.getPosition());

            // While the sequence deque is not empty, i.e., the user still has to click more tiles
            while (!modSequence.isEmpty()) {

                // If the element the user clicked on is a Tile
                if (clickedElement instanceof Tile) {

                    // If the element the user clicked on is the correct Tile in the sequence
                    if (clickedElement.equals(modSequence.peek())) {

                        // Provide visual feedback of the clicked tile
                        colorTile((Tile) clickedElement, Color.WHITE);
                        canvas.pause(200);
                        colorTile((Tile) clickedElement, TileManager.STD_COLOR);

                        // Remove the Tile from the sequence
                        modSequence.pop();
                    } else {
                        running = false;
                        return;
                    }
                }
            }
        });

        // Once the sequence deque is empty, the user has correctly clicked on all of the tiles and we can proceed to the next level.
        // We then update the lever counter.
        level++;
        levelLable.setText("Level: " + level);

        // We finally exit from the loop and lambda, and return from this method to the animate lambda in the constructor.
        // When that happens, we'll immediately return to this method and add to the sequence. 
    }

    private void colorTile(Tile tile, Color color) {
        tile.setFillColor(color);
    }

    /*
    * Runs the game
    */
    public static void main(String[] args) {
        SequenceGameOriginal game = new SequenceGameOriginal();
        
        game.init();
    }
}
