
public class Food {
	String foodName;
	int calories;
	double protein;
	int fat;
	int carbs;
	int sodium;
	
	
	Food(String foodName, int calories, double protein, int fat, int carbs, int sodium) {
		this.foodName = foodName;
		this.calories = calories;
		this.protein = protein;
		this.fat = fat;
		this.carbs = carbs;
		this.sodium = sodium;
	}
	
	@Override
    public String toString() {
        return foodName + " — " + calories + " cal, " + protein + "g protein, " + fat + "g fat, " + carbs + "g carbs, " + sodium + "mg sodium";
    }

}
