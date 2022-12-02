import java.util.HashMap;
import java.util.Map;

import edu.macalester.graphics.*;;

public class MapManagement {


    private CanvasWindow canvas;
    private static  double CANVAS_WIDTH = 650;
    private static  double CANVAS_HEIGHT = 650;
    
    private int gameLives = 5;                  
    private GraphicsText lives;

    


    //this gives us the width/height (the number stored as the value. Tells us the total number of tiles (key squared))
    //tells us the number of tiles to light up (key)
    public Map<Integer, Double> dimensionsGenerator(int i){

        Map<Integer, Double> dimensions = new HashMap<>();
        int counter = 1;

        for(int j = 0; j < i; j++){
            dimensions.put(counter, (CANVAS_WIDTH-150)/counter);
            counter++;
        }

        return dimensions;
    }

  
        

    


    //main method to print the map
    public static void main(String[] args) {
        MapManagement map = new MapManagement();
        System.out.println(map.dimensionsGenerator(10));
    }

   

    
}
