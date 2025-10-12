import java.util.Scanner;
import java.util.ArrayList;
import java.util.HashMap;


public class Main {
	
	private static final Scanner scanner = new Scanner(System.in);
    private static final HashMap<String, Food> foodDatabase = new HashMap<>();
    private static final ArrayList<Food> dailyLog = new ArrayList<>();

	public static void main(String[] args) {
		
		System.out.println("___ Diet Tracker ___");
		
		boolean running = true;
        while (running) {
            switch (mainMenu()) {
                case 1 -> foodDatabaseMenu();
                case 2 -> dailyLogMenu();
                case 3 -> running = false;
                default -> System.out.println("Invalid choice, try again.");
            }
        }
        
        scanner.close();
        showDailySummary();
        
	}

	// Menu Methods
	
	private static int mainMenu() {
		System.out.println("Enter 1 to access food database, 2 to add new entries to daily log, or 3 to exit. ");
		return Integer.parseInt(scanner.nextLine());
	}
	
	private static void foodDatabaseMenu() {
		while (true) {
            System.out.println("\n1. Add new food");
            System.out.println("2. Look up food");
            System.out.println("3. Show all foods");
            System.out.println("4. Go back");
            String response = scanner.nextLine();

            switch (response) {
                case "1" -> addFoodToDatabase();
                case "2" -> lookupFood();
                case "3" -> showAllFoods();
                case "4" -> { return; }
                default -> System.out.println("Invalid choice.");
            }
        }
	
	}
	
	private static void dailyLogMenu() {
		while (true) {
            System.out.print("Enter the name of what you ate (type 'done' when finished): ");
            String foodName = scanner.nextLine();
            if (foodName.equalsIgnoreCase("done")) break;

            Food food = foodDatabase.get(foodName.toLowerCase());
            if (food != null) {
                System.out.println("Found in database — added to log.");
                dailyLog.add(food);
                continue;
            }

            food = createFoodEntry(foodName);
            dailyLog.add(food);

            System.out.print("Save this food to the database? (Y/N): ");
            if (scanner.nextLine().equalsIgnoreCase("Y")) {
                foodDatabase.put(foodName.toLowerCase(), food);
                System.out.println("Food saved!");
            }
        }
	}
	
	// Core Methods
	
	private static void addFoodToDatabase() {
		Food food = createFoodEntry(prompt("Enter food name (or 'back'): "));
        if (food != null)
            foodDatabase.put(food.foodName.toLowerCase(), food);
	}
	
	private static void lookupFood() {
        String lookup = prompt("Enter food name to look up:");
        Food found = foodDatabase.get(lookup.toLowerCase());
        System.out.println(found != null ? found : "Food not found.");
    }
	
	private static void showAllFoods() {
		if (foodDatabase.isEmpty()) System.out.println("No foods in database yet.");
        for (var entry : foodDatabase.entrySet()) {
            System.out.println(entry.getValue());
        }
	}
	
	// Utility Methods
	
	 private static Food createFoodEntry(String name) {
	        if (name.equalsIgnoreCase("back")) return null;

	        int calories = getInt("Calories");
	        double protein = getDouble("Protein (g)");
	        int fat = getInt("Fat (g)");
	        int carbs = getInt("Carbs (g)");
	        int sodium = getInt("Sodium (mg)");

	        return new Food(name, calories, protein, fat, carbs, sodium);
	    }

	    private static int getInt(String promptText) {
	        while (true) {
	            String input = prompt("Enter " + promptText + ":");
	            if (input.equalsIgnoreCase("back")) return 0;
	            if (input.matches("\\d+")) return Integer.parseInt(input);
	            System.out.println("Invalid number, try again.");
	        }
	    }

	    private static double getDouble(String promptText) {
	        while (true) {
	            String input = prompt("Enter " + promptText + ":");
	            if (input.equalsIgnoreCase("back")) return 0;
	            if (input.matches("\\d+(\\.\\d+)?")) return Double.parseDouble(input);
	            System.out.println("Invalid number, try again.");
	        }
	    }

	    private static String prompt(String message) {
	        System.out.print(message + " ");
	        return scanner.nextLine();
	    }

	    private static void showDailySummary() {
	        System.out.println("\n___ Daily Summary ___");
	        int totalCalories = 0, totalFat = 0, totalCarbs = 0, totalSodium = 0;
	        double totalProtein = 0.0;

	        for (Food f : dailyLog) {
	            System.out.println(f);
	            totalCalories += f.calories;
	            totalProtein += f.protein;
	            totalFat += f.fat;
	            totalCarbs += f.carbs;
	            totalSodium += f.sodium;
	        }

	        System.out.printf("\nTotal Calories: %d%n", totalCalories);
	        System.out.printf("Total Protein: %.1f g%n", totalProtein);
	        System.out.printf("Total Fat: %d g%n", totalFat);
	        System.out.printf("Total Carbs: %d g%n", totalCarbs);
	        System.out.printf("Total Sodium: %d mg%n", totalSodium);
	    }
	}
		
		