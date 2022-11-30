import edu.macalester.graphics.CanvasWindow;
import java.awt.Color;
import java.lang.reflect.Array;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
/*
* Creates a class that creates the grids of the tiles and generates a sequence for 
* the randomization of the tiles
*/
public class TileManager {
   
   
    private double numberOfTiles;
    private Color color;

    CanvasWindow canvas;

    private int randomNum;
    
    private List<Tile> tileList = new ArrayList<Tile>();
    private List<Integer> correctIndex = new ArrayList<Integer>();  
    private List<Tile> correctTileList = new ArrayList<Tile>();
 

    private ArrayDeque<Tile> sequenceDeque = new ArrayDeque<Tile>();


    /**
     * TileManager constructor
     * @param canvas active canvas input
    */
    public TileManager(CanvasWindow canvas){
        this.canvas = canvas;
    }
    
    /* 
    * A method that compares the orginal list of tiles and the list of tiles that has the 
    * correct tiles to be clicked
    */
    public boolean compareTheTiles(){
        for(int x = 0; x< correctTileList.size(); x ++){
            if(correctTileList.get(x).isLit() != tileList.get(x).isLit()){
                return false;
            }
        }
        return true;
    }

    /**
     * A method that returns true if the the correct tile from the correct tile list is lit
     */
    public boolean ifCorrectTileisClicked(int x){
        if(correctTileList.get(x).isLit()){
            return true;
        }
        else{
            return false;
        }
    }
    
    public int returnIndex(Tile tile){
        return correctTileList.indexOf(tile);
    }

    public void removeCorrectTiles(){
       for(int i = 0; i < correctTileList.size(); i++){
        canvas.remove(correctTileList.get(i));
        }
    }

    public void removeAllTiles(){
        canvas.removeAll();
        tileList.clear();
    }

    public List<Tile> returnTileList(){
        return tileList;
    }

    public void clearAllLists(){            
        correctTileList.clear();
        tileList.clear();
        correctIndex.clear();
    }
                                    //tried to get method below to make use of the map
    /**
     * A method that creates the tile objects with rows and columns for the tiles
     */
    public void createAllTiles(Map<Integer, Double> dimensions, int i){

        double topX = 75;
        double topY = 75;
        color = Color.BLUE;

        for(int column = 0; column < i; column ++){
            for(int row = 0; row < i; row ++){
                Tile newTile = new Tile(topX, topY, dimensions.get(i), dimensions.get(i), color);
                Tile correctTileListTile = new Tile(topX, topY, dimensions.get(i), dimensions.get(i), color);
                canvas.add(newTile);
                tileList.add(newTile);
                canvas.add(correctTileListTile);
                correctTileList.add(correctTileListTile);
                topX += dimensions.get(i);
            }
            topX = 75;
            topY += dimensions.get(i);
        }
    }
    
    public int getRandNum(){
        randomNum = (int)(Math.random() * numberOfTiles);      
        return randomNum;
    }


    /**             IN PROGRESS
     * A method that creates a random sequence of tiles that will be clicked
     */
    public void createRandomSequence(ArrayDeque<Tile> sequenceDeque){
            
        int x = getRandNum();
        sequenceDeque.add(tileList.get(x));
        correctIndex.add(x);
    }

    
}
