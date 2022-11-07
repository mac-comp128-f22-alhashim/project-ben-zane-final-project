import edu.macalester.graphics.Rectangle;
import java.awt.Color;

/**
 * An interactable tile object that will appear on a canvas
 */
public class Tile extends Rectangle {
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
     * If a tile is part of a sequence, it will have its color set to Cyan and have its boolean set to true
     */
    public void lightUpCorrect(){
        this.setFillColor(Color.CYAN);
        tileIsLit = true;
    }  

    /**
     * If a tile that a user clicks is part of the correct sequence, it will light up and its boolean will be true 
     */
    public void onRightClick(){             
        this.lightUpCorrect();      
    }

    public void lightUpWrong(){
        this.setFillColor(Color.BLACK);
    }  

    /**
    * If a tile that a user clicks is not part of the correct sequence. it will light up Black and 
    * the game lives will go down 1
    */
    public void onWrongClick(){
        lightUpWrong();
        gameLives -= 1 ;
    }
    
    /**
     * This is used to tell if a tile is part of the sequence of correct tiles.
     */
    public boolean isLit(){
        return tileIsLit;
    }
}



