import java.awt.Image;

public class Grass extends Plant {
    private static int calories;

    private static final int maxAmount = 100;
    private static final int maxStepsUntilEdible = 50;
    
    public Grass(Location loc) {
        Debug.echo("Constructing a new Grass object");
        setLocation(loc);
        alive = true;
        amount = maxAmount;
        stepsUntilEdible = maxStepsUntilEdible;
    }
    
    public static int getCalories()        { return calories;            }
    public static void setCalories(int c)  { calories = c;               }
    
    protected int getMaxAmount()           { return maxAmount;           }
    protected int getMaxStepsUntilEdible() { return maxStepsUntilEdible; }

    public String toString() { return "Grass"; }
    public Image getImage(){
        if(isAlive()){
            return Resources.imageByName("Grass");
        } else {
            return null;
        }
    }

   
}
