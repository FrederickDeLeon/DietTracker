import java.util.Scanner;
import java.util.ArrayList;
//import java.util.HashMap;

public class Main {

	public static void main(String[] args) {
		
		Scanner scanner = new Scanner(System.in);
		
		//HashMap<String, Food> foodDatabase = new HashMap<>();
		ArrayList<Food> dailyLog = new ArrayList<>();
		
		System.out.println("___ Diet Tracker ___");
		
		while(true) {
				System.out.print("Enter the name of what you ate: (Type done when finished.) ");
				String foodName = scanner.nextLine();
				if (foodName.equalsIgnoreCase("done")) break;
				
				System.out.print("Enter the calorie count: ");
				int calories = Integer.parseInt(scanner.nextLine());
				
				System.out.print("Enter protein amount in grams: ");
				double protein = Double.parseDouble(scanner.nextLine());
				
				Food food = new Food (foodName, calories, protein);
				dailyLog.add(food);
			
		}
		
		scanner.close();
		
		System.out.println("___ Daily Summary ___");
		int totalCalories = 0;
		double totalProtein = 0.0;
		
		for (Food food : dailyLog) {
			System.out.println(food);
			totalCalories += food.calories;
			totalProtein += food.protein;
		}
		
		System.out.println("\nTotal calories: " + totalCalories);
        System.out.println("Total protein: " + totalProtein + "g");
	}

}
