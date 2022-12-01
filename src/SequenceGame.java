import edu.macalester.graphics.*;
import java.awt.Color;
import java.util.ArrayDeque;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class SequenceGame {
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
    private ArrayDeque<Tile> sequence;

    private Lock lock = new ReentrantLock();
    Condition condition = lock.newCondition();
    boolean levelCompleted = false;

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

        canvas.animate(() -> {
            if (running) {
                try {
                    levelCompleted = false;
                    awaitLevelCompletion();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
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

    public void awaitLevelCompletion() throws InterruptedException {
        lock.lock();

        tileManage.createRandomSequence();
        sequence.addAll(tileManage.sequence);

        try {
            while (!levelCompleted) {
                condition.await();
            }
        } finally {
            lock.unlock();
            level++;
            levelLable.setText("Level: " + level);
        }
    }

    public void handleGameLogic() {
        lock.lock();

        canvas.onClick(event -> {
            GraphicsObject clickedElement = canvas.getElementAt(event.getPosition());

            if (!sequence.isEmpty()) {
                if (clickedElement instanceof Tile) {
                    if (clickedElement.equals(sequence.peek())) {
                        colorTile((Tile) clickedElement, Color.WHITE);
                        canvas.pause(200);
                        colorTile((Tile) clickedElement, TileManager.STD_COLOR);

                        sequence.pop();
                    } else {
                        running = false;
                    }
                }
            }
        });

        try {
            if (sequence.isEmpty() || !running) {
                levelCompleted = true;
                condition.signal();
            }
        } finally {
            lock.unlock();
        }
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
