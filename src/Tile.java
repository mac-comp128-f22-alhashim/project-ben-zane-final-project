import edu.macalester.graphics.Rectangle;
import edu.macalester.graphics.Ellipse;
import java.awt.Color;
import edu.macalester.graphics.*;


/**
 * An interactable tile object that will appear on a canvas
 */
public class Tile  {
    
    Boolean tileIsLit = false;
    Boolean isRectangle = false;

    private Fillable shape;

    /**
     * Constructs tile with variable input
     * @param topX upper left x position
     * @param topY upper left y position
     * @param WIDTH width of tile
     * @param HEIGHT height of tile
     * @param color fill color of tile
     */
    public Tile(double topX, double topY, double WIDTH, double HEIGHT, Color color, boolean isRectangle){
        if (isRectangle) {
            shape = new Rectangle(topX, topY, WIDTH, HEIGHT);
            shape.setFillColor(color);
        } else {
            shape = new Ellipse(topX, topY, WIDTH, HEIGHT);
        }
    }
    
    /**
     * This is used to tell if a tile is part of the sequence of correct tiles.
     */
    public boolean isLit(){
        return tileIsLit;
    } 

    public Fillable getShape() {
        return shape;
    }

   public GraphicsObject getShapeGO(){
        return (GraphicsObject) shape;
    }

    public void setFillColor(Color color) {
        shape.setFillColor(color);
    }
}