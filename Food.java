
public class Food {
	String foodName;
	int calories;
	double protein;
	
	
	Food(String foodName, int calories, double protein) {
		this.foodName = foodName;
		this.calories = calories;
		this.protein = protein;
	}
	
	@Override
    public String toString() {
        return foodName + " — " + calories + " cal, " + protein + "g protein";
    }

}
