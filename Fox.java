import java.util.ArrayList;
import java.util.List;
import java.awt.Image;
import java.util.Collections;

public class Fox extends Animal {
    private static int calories;

    private static final int sightDistance = 10;
    private static final int moveDistance = 2;
    private static int maxHunger;
    private static final int maxAge = 100;
    
    private static ArrayList<String> prey = new ArrayList<String>();
    private static ArrayList<String> predators = new ArrayList<String>();
    
    public Fox(Location loc){
        Debug.echo("Constructing a new Fox object");
        setLocation(loc);
        hunger = 0;
        age = 0;
    }

    public void act(Grid grid){
        Debug.echo("Here is where the Fox would act");
        
        GridSquare mySquare = grid.get(getLocation());
        List<DistanceSquarePair> visibleSquares = grid.getAdjacentSquares(getLocation(), sightDistance);
        List<DistanceSquarePair> predatorSquares = grid.getOrganismSquares(visibleSquares, predators);
        List<DistanceSquarePair> preySquares = grid.getOrganismSquares(visibleSquares, prey);
        List<DistanceSquarePair> emptySquares = grid.getEmptySquares(visibleSquares);
        
        
        
        if(predatorSquares.size() > 0) {
            Debug.echo("OH SHIT RUN?");
        }
        
        Plant myPlant = null;
        if (mySquare.getPlant() != null && mySquare.getPlant().isAlive() && prey.contains(mySquare.getPlant().getClass().getName())){
            myPlant = mySquare.getPlant();
        }
        GridSquare bestPreySquare = null;
        List<DistanceSquarePair> reachableSquares = grid.getAdjacentSquares(getLocation(), moveDistance);
        Collections.shuffle(reachableSquares);
        for(DistanceSquarePair pair: reachableSquares){
            if(preySquares.contains(pair)){
                if(emptySquares.contains(pair)){
                    move(grid, pair.gridSquare);
            
                    myPlant = grid.get(getLocation()).getPlant();
                    eat(myPlant.getEaten());
                    return;
                } else {
                    eat(pair.gridSquare.getAnimal().getCalories());
                    grid.removeAnimal(pair.gridSquare);
                    move(grid, pair.gridSquare);
                    return;
                }
            }
        }
        if(bestPreySquare != null) {
            if(bestPreySquare.getAnimal() == null){
                move(grid, bestPreySquare);
                eat(bestPreySquare.getPlant().getEaten());
            } else {
                eat(bestPreySquare.getAnimal().getCalories());
                grid.removeAnimal(bestPreySquare);
                move(grid, bestPreySquare);
            }
            return;
        } else if (myPlant != null) {
            eat(myPlant.getEaten());
        }
        
        List<DistanceSquarePair> emptyReachableSquares = emptySquares;
        emptyReachableSquares.retainAll(reachableSquares);
        if (emptyReachableSquares.size() > 0) {
            move(grid, emptyReachableSquares.get(Util.randInt(emptyReachableSquares.size())).gridSquare);
        }
    }

    public static void addPrey(String p)     { prey.add(p);      }
    public static void addPredator(String p) { predators.add(p); }
    public static void setCalories(int c)    { 
        calories = c;     
        maxHunger = c * 10;
    }

    protected int getMaxHunger()    { return maxHunger;                     }
    protected int getMaxAge()       { return maxAge;                        }
    protected int getSightDistance(){ return sightDistance;                 }
    protected int getMoveDistance() { return moveDistance;                  }
    
    public int getCalories()        { return calories;                      }
    public String toString()        { return "Fox";                         }
    public Image getImage()         { return Resources.imageByName("Fox");  }
}
