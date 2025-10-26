import javax.swing.*;
import java.awt.*;


public class DietTrackerUI extends JFrame {

    private DietTracker tracker;
    private CardLayout cardLayout;
    private JPanel mainPanel;
    private JTextArea output;

    public DietTrackerUI(DietTracker tracker) {
        this.tracker = tracker;
        setTitle("Diet Tracker");
        setSize(600, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // --- Card Layout setup ---
        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);

        // Output area (shared across menus)
        output = new JTextArea(10, 40);
        output.setEditable(false);

        // Create menu panels
        mainPanel.add(createMainMenu(), "main");
        mainPanel.add(createFoodDatabaseMenu(), "foodDatabase");

        add(mainPanel);
        cardLayout.show(mainPanel, "main"); // start on main menu
    }

    // --- MAIN MENU ---
    private JPanel createMainMenu() {
        JPanel panel = new JPanel(new BorderLayout());
        JLabel title = new JLabel("DIET TRACKER", SwingConstants.CENTER);
        title.setFont(new Font("SansSerif", Font.BOLD, 24));
        panel.add(title, BorderLayout.NORTH);

        JPanel buttons = new JPanel(new GridLayout(3, 1, 10, 10));
        JButton foodDbBtn = new JButton("Food Database");
        foodDbBtn.setFont(new Font("SansSerif", Font.BOLD, 18));
        JButton dailyLogBtn = new JButton("Daily Log");
        dailyLogBtn.setFont(new Font("SansSerif", Font.BOLD, 18));
        JButton exitBtn = new JButton("Exit");
        exitBtn.setFont(new Font("SansSerif", Font.BOLD, 18));

        buttons.add(foodDbBtn);
        buttons.add(dailyLogBtn);
        buttons.add(exitBtn);
        panel.add(buttons, BorderLayout.CENTER);

        foodDbBtn.addActionListener(e -> cardLayout.show(mainPanel, "foodDatabase"));
        dailyLogBtn.addActionListener(e -> {
            output.setText(tracker.getDailySummary());
            JOptionPane.showMessageDialog(this, new JScrollPane(output), "Daily Log", JOptionPane.PLAIN_MESSAGE);
        });
        exitBtn.addActionListener(e -> System.exit(0));

        return panel;
    }

    // --- FOOD DATABASE MENU ---
    private JPanel createFoodDatabaseMenu() {
        JPanel panel = new JPanel(new BorderLayout());
        JLabel title = new JLabel("FOOD DATABASE", SwingConstants.CENTER);
        title.setFont(new Font("SansSerif", Font.BOLD, 22));
        panel.add(title, BorderLayout.NORTH);

        JPanel buttons = new JPanel(new GridLayout(5, 1, 10, 10));
        JButton addFoodBtn = new JButton("1. Add New Food");
        JButton lookupBtn = new JButton("2. Look Up Food");
        JButton showAllBtn = new JButton("3. Show All Foods");
        JButton clearDbBtn = new JButton("4. Clear Database");
        JButton backBtn = new JButton("5. Go Back");

        buttons.add(addFoodBtn);
        buttons.add(lookupBtn);
        buttons.add(showAllBtn);
        buttons.add(clearDbBtn);
        buttons.add(backBtn);
        panel.add(buttons, BorderLayout.CENTER);
        panel.add(new JScrollPane(output), BorderLayout.SOUTH);

        // Button actions
        addFoodBtn.addActionListener(e -> {
            String name = JOptionPane.showInputDialog("Enter food name:");
            if (name == null || name.isBlank()) return;
            Food food = tracker.addFood(name);
            tracker.getFoodDatabase().put(name.toLowerCase(), food);
            output.append("Added: " + food + "\n");
        });

        lookupBtn.addActionListener(e -> {
            String name = JOptionPane.showInputDialog("Enter food name to look up:");
            if (name == null || name.isBlank()) return;
            Food found = tracker.getFoodDatabase().get(name.toLowerCase());
            output.append(found != null ? found + "\n" : "Food not found.\n");
        });

        showAllBtn.addActionListener(e -> {
            output.setText("");
            if (tracker.getFoodDatabase().isEmpty()) {
                output.append("No foods in database yet.\n");
            } else {
                output.append("All Foods:\n");
                for (Food f : tracker.getFoodDatabase().values()) {
                    output.append(f + "\n");
                }
            }
        });

        clearDbBtn.addActionListener(e -> {
            int confirm = JOptionPane.showConfirmDialog(this, "Clear the entire database?", "Confirm", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                tracker.getFoodDatabase().clear();
                output.setText("Database cleared.\n");
            }
        });

        backBtn.addActionListener(e -> cardLayout.show(mainPanel, "main"));

        return panel;
    }
    
    // --- DAILY LOG MENU ---
    private JPanel createDailyLogMenu() {
        JPanel panel = new JPanel(new BorderLayout());
        JLabel title = new JLabel("DAILY LOG", SwingConstants.CENTER);
        title.setFont(new Font("SansSerif", Font.BOLD, 22));
        panel.add(title, BorderLayout.NORTH);

        JPanel buttons = new JPanel(new GridLayout(4, 1, 10, 10));
        JButton addLogBtn = new JButton("1. Add Food to Log");
        JButton showLogBtn = new JButton("2. Show Daily Log");
        JButton clearLogBtn = new JButton("3. Clear Daily Log");
        JButton backBtn = new JButton("4. Go Back");

        buttons.add(addLogBtn);
        buttons.add(showLogBtn);
        buttons.add(clearLogBtn);
        buttons.add(backBtn);
        panel.add(buttons, BorderLayout.CENTER);
        panel.add(new JScrollPane(output), BorderLayout.SOUTH);

        addLogBtn.addActionListener(e -> {
            String name = JOptionPane.showInputDialog("Enter the food name:");
            if (name == null || name.isBlank()) return;

            Food food = tracker.getFoodDatabase().get(name.toLowerCase());
            if (food != null) {
                tracker.getDailyLog().add(food);
                output.append("Added to daily log: " + food.foodName + "\n");
            } else {
                int choice = JOptionPane.showConfirmDialog(this, "Food not found. Add new entry?", "Add New Food", JOptionPane.YES_NO_OPTION);
                if (choice == JOptionPane.YES_OPTION) {
                    Food newFood = tracker.addFood(name);
                    tracker.getDailyLog().add(newFood);
                    tracker.getFoodDatabase().put(name.toLowerCase(), newFood);
                    output.append("Added new food to log and database: " + newFood + "\n");
                }
            }
        });

        showLogBtn.addActionListener(e -> {
            output.setText("");
            if (tracker.getDailyLog().isEmpty()) {
                output.append("No items in daily log.\n");
            } else {
                output.append("Today's Log:\n");
                for (Food f : tracker.getDailyLog()) {
                    output.append(f + "\n");
                }
                output.append("\n" + tracker.getDailySummary());
            }
        });

        clearLogBtn.addActionListener(e -> {
            int confirm = JOptionPane.showConfirmDialog(this, "Clear your daily log?", "Confirm", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                tracker.getDailyLog().clear();
                output.setText("Daily log cleared.\n");
            }
        });

        backBtn.addActionListener(e -> cardLayout.show(mainPanel, "main"));

        return panel;
    }

}