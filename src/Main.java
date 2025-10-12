import java.util.Scanner;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Main {

	public static void main(String[] args) {
		
		Scanner scanner = new Scanner(System.in);
		
		HashMap<String, Food> foodDatabase = new HashMap<>();
		ArrayList<Food> dailyLog = new ArrayList<>();
		
		boolean running = true;
		
		System.out.println("___ Diet Tracker ___");
		
		while(running) {
			
			System.out.println("Enter 1 to access food database, 2 to add new entries to daily log, or 3 to exit. ");
			String userResponse = scanner.nextLine();
			
			if (userResponse.equals("1")) {
				
				while(true) {
					
					System.out.println("Enter 1 to add new food object, 2 to look up food, 3 to show all foods, or 4 to go back. ");
					String userResponse2 = scanner.nextLine();
					
					if (userResponse2.equals("1")) { //add new food to database 
						int calories = 0;
						double protein = 0;
						int fat = 0;
						int carbs = 0;
						int sodium = 0;
						
						System.out.println("What is the name of the new food you want to add to the database? Or type 'back' to go back. ");
						String newFoodEntry = scanner.nextLine();
						if (newFoodEntry.equalsIgnoreCase("back")) break;
						
						System.out.println("What are the calories for this food? Or type 'back' to go back. ");
						String checkForBack = scanner.nextLine();
						if (checkForBack.equalsIgnoreCase("back")) continue;
						else if(!checkForBack.matches("\\d+")) {
							System.out.println("Invalid input. Please enter a valid number.");
							continue; 
						}
						else {
							calories = Integer.parseInt(checkForBack);
						}
						
						System.out.println("What is the protein content of this food in grams? Or type 'back' to go back. ");
						String checkForBack2 = scanner.nextLine();
						if (checkForBack2.equalsIgnoreCase("back")) continue;
						else if(!checkForBack2.matches("\\d+(\\.\\d+)?")) {
							System.out.println("Invalid input. Please enter a valid number.");
							continue; 
						}
						else {
							protein = Double.parseDouble(checkForBack2);
						}
						
						System.out.println("What is the fat content of this food in grams? Or type 'back' to go back. ");
						String checkForBack3 = scanner.nextLine();
						if (checkForBack3.equalsIgnoreCase("back")) continue;
						else if(!checkForBack3.matches("\\d+")) {
							System.out.println("Invalid input. Please enter a valid number.");
							continue; 
						}
						else {
							fat = Integer.parseInt(checkForBack3);
						}
						
						System.out.println("What is the carbs content of this food in grams? Or type 'back' to go back. ");
						String checkForBack4 = scanner.nextLine();
						if (checkForBack4.equalsIgnoreCase("back")) continue;
						else if(!checkForBack4.matches("\\d+")) {
							System.out.println("Invalid input. Please enter a valid number.");
							continue; 
						}
						else {
							carbs = Integer.parseInt(checkForBack4);
						}
						
						System.out.println("What is the sodium content of this food in milligrams? Or type 'back' to go back. ");
						String checkForBack5 = scanner.nextLine();
						if (checkForBack5.equalsIgnoreCase("back")) continue;
						else if(!checkForBack5.matches("\\d+")) {
							System.out.println("Invalid input. Please enter a valid number.");
							continue; 
						}
						else {
							sodium = Integer.parseInt(checkForBack5);
						}
						
						Food food = new Food (newFoodEntry, calories, protein, fat, carbs, sodium);
						foodDatabase.put(newFoodEntry.toLowerCase(), food);
					}
					else if (userResponse2.equals("2")) { //look up foods in database 
						System.out.println("What food are you trying to look up? ");
						String lookUpEntry = scanner.nextLine();
						Food foundFood = foodDatabase.get(lookUpEntry.toLowerCase());
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
					else if (userResponse2.equals("4")) { //return to first menu
						break;
					}
				}
			
			}
			else if (userResponse.equals("2")) {
				while (true) {
					System.out.print("Enter the name of what you ate: (Type done when finished.) ");
					String foodName = scanner.nextLine();
					if (foodName.equalsIgnoreCase("done")) break;
					else if(foodDatabase.containsKey(foodName.toLowerCase())) {
						System.out.println("This food is already in the database, added to your daily log.");
						System.out.println("Type another food name or 'done' to finish.");
						Food existingFood = foodDatabase.get(foodName.toLowerCase());
						dailyLog.add(existingFood);
					}
					else {
						System.out.print("Enter the calorie count: ");
						int calories = Integer.parseInt(scanner.nextLine());
						
						System.out.print("Enter the amount of protein in grams: ");
						double protein = Double.parseDouble(scanner.nextLine());
						
						System.out.print("Enter the amount of fat in grams: ");
						int fat = Integer.parseInt(scanner.nextLine());
						
						System.out.print("Enter the amount of carbs in grams: ");
						int carbs = Integer.parseInt(scanner.nextLine());
						
						System.out.print("Enter the amount of sodium in milligrams: ");
						int sodium = Integer.parseInt(scanner.nextLine());
						
						Food food = new Food (foodName, calories, protein, fat, carbs, sodium);
						dailyLog.add(food);
						
						System.out.println("Do you want to save this food for next time? Y/N");
						String saveConfirm = scanner.nextLine();
						if (saveConfirm.equalsIgnoreCase("Y")) {
							foodDatabase.put(foodName.toLowerCase(), food);
							System.out.println("Entry added! Type another food name or 'done' to finish.");
						}
						else if (saveConfirm.equalsIgnoreCase("N")) {
							System.out.println("Entry added! Type another food name or 'done' to finish.");
							continue;
						}
					}
				}
					
			}
			else if(userResponse.equals("3")) { //exit loop
				running = false;
			}
			
			
		}
		
		scanner.close();
		
		System.out.println("___ Daily Summary ___");
		int totalCalories = 0;
		double totalProtein = 0.0;
		int totalFat = 0;
		int totalCarbs = 0;
		int totalSodium = 0;
		
		for (Food food : dailyLog) {
			System.out.println(food);
			totalCalories += food.calories;
			totalProtein += food.protein;
			totalFat += food.fat;
			totalCarbs += food.carbs;
			totalSodium += food.sodium;
		}
		
		System.out.println("\nTotal Calories: " + totalCalories + " calories");
        System.out.println("Total Protein: " + totalProtein + "g");
        System.out.println("Total Fat: " + totalFat + "g");
        System.out.println("Total Carbs: " + totalCarbs + "g");
        System.out.println("Total Sodium: " + totalSodium + "mg");
        
	}

}
