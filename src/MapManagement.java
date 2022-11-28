import java.util.HashMap;
import java.util.Map;

import edu.macalester.graphics.*;;

public class MapManagement {


    private CanvasWindow canvas;
    private static  double CANVAS_WIDTH = 700;
    private static  double CANVAS_HEIGHT = 700;
    
    private int gameLives = 5;                  
    private GraphicsText lives;
    private Map<String, String> map = new HashMap<>();

    


    //this gives us the width/height (the number stored as the value. Tells us the total number of tiles (key squared))
    //tells us the number of tiles to light up (key)
    public Map<Double, Double> dimensionsGenerator(Double i){

        Map<Double, Double> dimensions = new HashMap<>();
        double counter = 3;

        double x = i;
        for(int j = 0; j < i; j++){
            dimensions.put(counter, (CANVAS_WIDTH-100)/counter);
            counter++;
        }

        return dimensions;
    }


    //main method to print the map
    public static void main(String[] args) {
        MapManagement map = new MapManagement();
        System.out.println(map.dimensionsGenerator(10.0));
    }

   

    
}
