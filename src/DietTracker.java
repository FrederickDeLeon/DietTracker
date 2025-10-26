import java.io.*;
import java.util.*;

public class DietTracker {
	
	private static final Scanner scanner = new Scanner(System.in);
    private static final HashMap<String, Food> foodDatabase = new HashMap<>();
    private static final ArrayList<Food> dailyLog = new ArrayList<>();
    private static final String DATABASE_FILE = "food_database.txt";
    
    public void run() {
    	
    	System.out.println("╔════════════════════════╗");
    	System.out.println("║     DIET TRACKER       ║");
    	System.out.println("╚════════════════════════╝");
    	loadDatabase();
    	
    	boolean running = true;
        while (running) {
            switch (mainMenu()) {
                case 1 -> foodDatabaseMenu();
                case 2 -> dailyLogMenu();
                case 3 -> running = false;
                default -> System.out.println("Invalid choice, try again.");
            }
        }
        
        saveDatabase();
        scanner.close();
        showDailySummary();
    	
    }
    
    private static int mainMenu() {
		System.out.println("╔════════════════════════╗");
		System.out.println("║     DIET TRACKER       ║");
		System.out.println("╠════════════════════════╣");
		System.out.println("║ 1. Food Database       ║");
		System.out.println("║ 2. Daily Log           ║");
		System.out.println("║ 3. Exit                ║");
		System.out.println("╚════════════════════════╝");
		return Integer.parseInt(scanner.nextLine());
	}
	
	private static void foodDatabaseMenu() {
		while (true) {
			
			System.out.println("╔════════════════════════╗");
			System.out.println("║ 1. Add new food        ║");
			System.out.println("║ 2. Look up food        ║");
			System.out.println("║ 3. Show all foods      ║");
			System.out.println("║ 4. Clear database      ║");
			System.out.println("║ 5. Edit database       ║");
			System.out.println("║ 6. Back                ║");
			System.out.println("╚════════════════════════╝");
            String response = scanner.nextLine();

            switch (response) {
                case "1" -> addFoodToDatabase();
                case "2" -> lookupFood();
                case "3" -> showAllFoods();
                case "4" -> clearDatabase();
                case "5" -> editDatabase();
                case "6" -> { return; }
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
	
	//Utility Methods
	
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
	    
	    private static void saveDatabase() {
	    	try (PrintWriter writer = new PrintWriter(new FileWriter(DATABASE_FILE))) {
	            for (Food food : foodDatabase.values()) {
	                writer.printf("%s,%d,%.2f,%d,%d,%d%n",
	                    food.foodName.replace(",", ""), 
	                    food.calories,
	                    food.protein,
	                    food.fat,
	                    food.carbs,
	                    food.sodium
	                );
	            }
	            System.out.println("Database saved successfully.");
	        } catch (IOException e) {
	            System.out.println("Error saving database: " + e.getMessage());
	        }
	    }
	    
	    private static void loadDatabase() {
	    	File file = new File(DATABASE_FILE);
	        if (!file.exists()) return;

	        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
	            String line;
	            while ((line = reader.readLine()) != null) {
	                String[] parts = line.split(",");
	                if (parts.length == 6) {
	                    String name = parts[0];
	                    int calories = Integer.parseInt(parts[1]);
	                    double protein = Double.parseDouble(parts[2]);
	                    int fat = Integer.parseInt(parts[3]);
	                    int carbs = Integer.parseInt(parts[4]);
	                    int sodium = Integer.parseInt(parts[5]);
	                    foodDatabase.put(name.toLowerCase(), new Food(name, calories, protein, fat, carbs, sodium));
	                }
	            }
	            System.out.println("Database loaded successfully.");
	        } catch (IOException e) {
	            System.out.println("Error loading database: " + e.getMessage());
	        }
	    }
	    
	    private static void clearDatabase() {
	        System.out.print("Are you sure you want to delete all saved foods? (Y/N): ");
	        String confirm = scanner.nextLine();

	        if (confirm.equalsIgnoreCase("Y")) {
	            foodDatabase.clear();  // clears all entries in memory

	            
	            try (PrintWriter writer = new PrintWriter(new FileWriter(DATABASE_FILE))) {
	                
	            } catch (IOException e) {
	                System.out.println("Error clearing database file: " + e.getMessage());
	            }

	            System.out.println("Database cleared successfully.");
	        } 
	        else {
	            System.out.println("Clear operation canceled.");
	        }
	    }
	    
	    private static void editDatabase() {
	    	String lookup = prompt("Enter food name to edit:");
	        Food found = foodDatabase.get(lookup.toLowerCase());
	        
	        if (found == null) {
	            System.out.println("Food not found.");
	            return;
	        }
	        
	        System.out.println("╔════════════════════════╗");
	        System.out.println("║ Please select data to  ║");
	        System.out.println("║ modify:                ║");
	        System.out.println("║                        ║");
			System.out.println("║ 1. Calories            ║");
			System.out.println("║ 2. Protein             ║");
			System.out.println("║ 3. Fat                 ║");
			System.out.println("║ 4. Carbs               ║");
			System.out.println("║ 5. Sodium              ║");
			System.out.println("║ 6. All data            ║");
			System.out.println("║ 7. Back                ║");
			System.out.println("║                        ║");
			System.out.println("╚════════════════════════╝");
            String response = scanner.nextLine();

            switch (response) {
                case "1" -> {
                	int newCalories = getInt("Calories");
                	found.calories = newCalories;
                	System.out.println("Calories updated."); 
                }
                case "2" -> {
                	double newProtein = getDouble("Protein");
                	found.protein = newProtein;
                	System.out.println("Protein updated");
                }
                case "3" -> {
                	int newFat = getInt("Fat");
                	found.fat = newFat;
                	System.out.println("Fat updated.");
                }
                case "4" -> {
                	int newCarbs = getInt("Carbs");
                	found.carbs = newCarbs;
                	System.out.println("Carbs updated.");
                }
                case "5" -> {
                	int newSodium = getInt("Sodium");
                	found.sodium = newSodium;
                	System.out.println("Sodium updated.");
                }
                case "6" -> {
                	int newCalories = getInt("Calories");
                	found.calories = newCalories;
                	
                	double newProtein = getDouble("Protein");
                	found.protein = newProtein;
                
                	int newFat = getInt("Fat");
                	found.fat = newFat;
                
                	int newCarbs = getInt("Carbs");
                	found.carbs = newCarbs;
                	
                	int newSodium = getInt("Sodium");
                	found.sodium = newSodium;
                	System.out.println("All values updated.");
                }
                case "7" -> { return; }
                default -> System.out.println("Invalid choice.");
            }
	        
	    }
	    
	    //Getter Methods for Swing UI
	    
	    public HashMap<String, Food> getFoodDatabase() {
	        return foodDatabase;
	    }

	    public ArrayList<Food> getDailyLog() {
	        return dailyLog;
	    }
	    
	    public String getDailySummary() {
	        StringBuilder sb = new StringBuilder();
	        int totalCalories = 0, totalFat = 0, totalCarbs = 0, totalSodium = 0;
	        double totalProtein = 0.0;

	        for (Food f : dailyLog) {
	            sb.append(f).append("\n");
	            totalCalories += f.calories;
	            totalProtein += f.protein;
	            totalFat += f.fat;
	            totalCarbs += f.carbs;
	            totalSodium += f.sodium;
	        }

	        sb.append("\nTotal Calories: ").append(totalCalories);
	        sb.append("\nTotal Protein: ").append(String.format("%.1f g", totalProtein));
	        sb.append("\nTotal Fat: ").append(totalFat).append(" g");
	        sb.append("\nTotal Carbs: ").append(totalCarbs).append(" g");
	        sb.append("\nTotal Sodium: ").append(totalSodium).append(" mg\n");

	        return sb.toString();
	    }
	    
	    public Food addFood(String name) {
	        Food food = createFoodEntry(name);
	        if (food != null) {
	            foodDatabase.put(food.foodName.toLowerCase(), food);
	        }
	        return food;
	    }
    

}
