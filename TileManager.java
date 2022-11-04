import edu.macalester.graphics.CanvasWindow;
import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
/*
* Creates a class that creates the grids of the tiles and generates a sequence for 
* the randomization of the tiles
*/
public class TileManager {
   
    private double tileWidth;
    private double tileHeight;
    private double numberOfTiles;
    private Color color;

    CanvasWindow canvas;

    private int randomNum;
    
    private List<Tile> tileList = new ArrayList<Tile>();
    private List<Integer> correctIndex = new ArrayList<Integer>();  
    private List<Tile> correctTileList = new ArrayList<Tile>();
 
    /**
     * TileManager constructor
     * @param canvas active canvas input
     * @param tileWidth width of tile
     * @param tileHeight height of tile
     * @param numberOfTiles total tile number
    */
    public TileManager(CanvasWindow canvas, double tileWidth, double tileHeight, double numberOfTiles){
        this.canvas = canvas;
        this.tileWidth = tileWidth;
        this.tileHeight = tileHeight;
        this.numberOfTiles = numberOfTiles;
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

    /**
     * A method that creates the tile objects with rows and columns for the tiles
     */
    public void createAllTiles(){

        double topX = 100;
        double topY = 100;
        color = Color.BLUE;

        for(int column = 0; column < Math.sqrt(numberOfTiles); column ++){
            for(int row = 0; row < Math.sqrt(numberOfTiles); row ++){
                
                Tile newTile = new Tile(topX, topY, tileWidth, tileHeight, color);
                Tile correctTileListTile = new Tile(topX, topY, tileWidth, tileHeight, color);
                canvas.add(newTile);
                tileList.add(newTile);
                canvas.add(correctTileListTile);
                correctTileList.add(correctTileListTile);
                topX += tileWidth;
            }
            topX = 100;
            topY += tileWidth;
        }
    }
    
    public int getRandNum(){
        randomNum = (int)(Math.random() * numberOfTiles);      
        return randomNum;
    }

    /**
    * A method that generates a random sequence for the tiles to appear randomly on 
    * the grid
    */
    public List<Integer> generateSequence(){
        for(int i = 0; i < (Math.sqrt(numberOfTiles)); i++){      
            int randomTile = getRandNum();
            if(!correctIndex.contains(randomTile)){
                correctIndex.add(randomTile);
            }
            else if(correctIndex.contains(randomTile)){
                randomTile = getRandNum();
                i--;
            }
        }    
        for(Integer i : correctIndex){
            correctTileList.get(i).lightUpCorrect();
        }
        return correctIndex;                            
    }
}
