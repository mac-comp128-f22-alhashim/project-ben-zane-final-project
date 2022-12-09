import java.util.HashMap;
import java.util.Map;


public class MapManagement {

    private static  double SIDE = 650;
     
    /*
    * Creates a map of size i.
    * The key represents the number of tiles in a row or column of the game
    * The value represents the length of the side of a tile
     */
    public Map<Integer, Double> dimensionsGenerator(int i){

        Map<Integer, Double> dimensions = new HashMap<>();
        int counter = 1;

        for(int j = 0; j < i; j++){
            dimensions.put(counter, (SIDE-150)/counter);
            counter++;
        }

        return dimensions;
    }
}
