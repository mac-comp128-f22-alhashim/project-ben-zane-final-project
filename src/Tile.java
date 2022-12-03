import edu.macalester.graphics.Ellipse;
import edu.macalester.graphics.Rectangle;
import java.awt.Color;


/**
 * An interactable tile object that will appear on a canvas
 */
public class Tile extends Ellipse {   //extends either ellipse or rectangle to change the shape 
    int gameLives;
    
    Boolean tileIsLit = false;

    /**
     * Constructs tile with variable input
     * @param topX upper left x position
     * @param topY upper left y position
     * @param WIDTH width of tile
     * @param HEIGHT height of tile
     * @param color fill color of tile
     */
    public Tile(double topX, double topY, double WIDTH, double HEIGHT, Color color){
        super(topX, topY, WIDTH, HEIGHT);
        this.setFillColor(color);
    }
    
    /**
     * This is used to tell if a tile is part of the sequence of correct tiles.
     */
    public boolean isLit(){
        return tileIsLit;
    } 
}



