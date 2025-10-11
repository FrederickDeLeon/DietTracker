import java.util.Scanner;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Main {

	public static void main(String[] args) {
		
		Scanner scanner = new Scanner(System.in);
		
		HashMap<String, Food> foodDatabase = new HashMap<>();
		ArrayList<Food> dailyLog = new ArrayList<>();
		
		System.out.println("___ Diet Tracker ___");
		
		while(true) {
			System.out.println("Do you want to access the food database or enter new entries? Answer 1 or 2: ");
			String userResponse = scanner.nextLine();
			if (userResponse.equals("1")) {
				
				while(true) {
					int calories = 0;
					double protein = 0;
					
					System.out.println("Enter 1 to add new food object, 2 to look up food, or 3 to show all foods ");
					String userResponse2 = scanner.nextLine();
					
					if (userResponse2.equals("1")) { //add new food to database 
						
						System.out.println("What is the name of the new food you want to add to the database? Or type 'back' to go back. ");
						String newFoodEntry = scanner.nextLine();
						if (newFoodEntry.equalsIgnoreCase("back")) break;
						
						System.out.println("What are the calories for this food? Or type 'back' to go back. ");
						String checkForBack = scanner.nextLine();
						if (checkForBack.equalsIgnoreCase("back")) break;
						else {
							calories = Integer.parseInt(checkForBack);
						}
						
						System.out.println("What is the protein content of this food in grams? Or type 'back' to go back. ");
						String checkForBack2 = scanner.nextLine();
						if (checkForBack2.equalsIgnoreCase("back")) break;
						else {
							protein = Double.parseDouble(checkForBack2);
						}
						
						Food food = new Food (newFoodEntry, calories, protein);
						foodDatabase.put(newFoodEntry, food);
					}
					else if (userResponse2.equals("2")) { //look up foods in database 
						System.out.println("What food are you trying to look up? ");
						String lookUpEntry = scanner.nextLine();
						Food foundFood = foodDatabase.get(lookUpEntry);
						if (foundFood != null) {
						    System.out.println(foundFood);
						} 
						else {
						    System.out.println("Food not found in database.");
						}
					}
					else if (userResponse2.equals("3")) { //show all foods in database 
						for (Map.Entry<String, Food> entry : foodDatabase.entrySet()) {
			                System.out.println("Food: " + entry.getKey() + ", Value: " + entry.getValue());
			            }
					}
				}
			
			}
			else if (userResponse.equals("2")) {
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
			else {
				
			}
			
			
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
