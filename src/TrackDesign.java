package horse;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.List;

public class TrackDesign extends JFrame {
    private JTextField numLanesField, trackLengthField, betAmountField;
    private JPanel trackDisplayPanel;
    private JComboBox<String> breedComboBox, colorComboBox, accessoryComboBox, horseBetComboBox;
    private JTextArea statsTextArea, bettingTextArea;
    private List<RaceResult> raceResults;
    private List<Bet> bets;
    private List<Horse> horses = new ArrayList<>(); 

    public TrackDesign() {
        super("Track and Horse Design with Betting");
        raceResults = new ArrayList<>();
        bets = new ArrayList<>();
        createUI();
        setSize(600, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pack();
        setVisible(true);
    }

    private void createUI() {
        setLayout(new BorderLayout());

        // Input panel for track, horse, and betting
        JPanel inputPanel = new JPanel(new GridLayout(3, 2));
        inputPanel.add(new JLabel("Number of Lanes:"));
        numLanesField = new JTextField();
        inputPanel.add(numLanesField);
        inputPanel.add(new JLabel("Track Length:"));
        trackLengthField = new JTextField();
        inputPanel.add(trackLengthField);

        // Horse customization inputs
        JPanel horsePanel = new JPanel(new GridLayout(4, 2));
        horsePanel.add(new JLabel("Breed:"));
        breedComboBox = new JComboBox<>(new String[]{"Thoroughbred", "Arabian", "Quarter Horse"});
        horsePanel.add(breedComboBox);
        horsePanel.add(new JLabel("Color:"));
        colorComboBox = new JComboBox<>(new String[]{"Black", "Brown", "White"});
        horsePanel.add(colorComboBox);
        horsePanel.add(new JLabel("Accessory:"));
        accessoryComboBox = new JComboBox<>(new String[]{"Saddle", "Blanket", "Bridle"});
        horsePanel.add(accessoryComboBox);
        horsePanel.add(new JLabel("Bet on Horse:"));
        horseBetComboBox = new JComboBox<>(new String[]{"Horse 1", "Horse 2", "Horse 3", "Horse 4"});
        horsePanel.add(horseBetComboBox);

        // Betting inputs
        JPanel bettingPanel = new JPanel();
        bettingPanel.add(new JLabel("Bet Amount:"));
        betAmountField = new JTextField(10);
        bettingPanel.add(betAmountField);
        JButton placeBetButton = new JButton("Place Bet");
        placeBetButton.addActionListener(e -> placeBet());
        bettingPanel.add(placeBetButton);
        
        inputPanel.setPreferredSize(new Dimension(600, 100));
        horsePanel.setPreferredSize(new Dimension(600, 100));
        bettingPanel.setPreferredSize(new Dimension(600, 100));

        JButton simulateRaceButton = new JButton("Simulate Race");
        simulateRaceButton.addActionListener(e -> simulateRace());

        // Panel to display the track
        trackDisplayPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                drawTrack(g);
            }
        };
        trackDisplayPanel.setPreferredSize(new Dimension(350, 200));

        // Text areas for statistics and betting
        statsTextArea = new JTextArea(5, 25);
        statsTextArea.setEditable(false);
        JScrollPane statsScrollPane = new JScrollPane(statsTextArea);

        bettingTextArea = new JTextArea(5, 20);
        bettingTextArea.setEditable(false);
        JScrollPane bettingScrollPane = new JScrollPane(bettingTextArea);


     // Use a layout that respects preferred sizes like BoxLayout
        JPanel topPanel = new JPanel();
        topPanel.setLayout(new BoxLayout(topPanel, BoxLayout.Y_AXIS));
        topPanel.add(inputPanel);
        topPanel.add(horsePanel);
        topPanel.add(bettingPanel);
        
     // Now add the topPanel to the NORTH
        add(topPanel, BorderLayout.NORTH);

        add(topPanel, BorderLayout.NORTH);
        add(simulateRaceButton, BorderLayout.CENTER);
        add(trackDisplayPanel, BorderLayout.SOUTH);
        add(statsScrollPane, BorderLayout.WEST);
        add(bettingScrollPane, BorderLayout.EAST);
    }

    
    private Color getColorFromSelection(String colorSelection) {
        switch (colorSelection) {
            case "Black": return Color.BLACK;
            case "Brown": return new Color(165, 42, 42); // Example brown
            case "White": return Color.WHITE;
            default: return Color.GRAY;
        }
    }

    
    
    
    private void placeBet() {
        try {
            double amount = Double.parseDouble(betAmountField.getText());
            String horse = (String) horseBetComboBox.getSelectedItem();
            bets.add(new Bet(horse, amount));
            updateBettingArea();
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Please enter a valid bet amount.");
        }
    }

    private void updateBettingArea() {
        StringBuilder sb = new StringBuilder();
        for (Bet bet : bets) {
            sb.append(String.format("Bet on %s: $%.2f\n", bet.getHorseName(), bet.getAmount()));
        }
        bettingTextArea.setText(sb.toString());
    }

    private void simulateRace() {
    	raceResults.clear();
    	
    	horses.clear();
    
    	
        
        // Parse number of lanes and length
        int lanes = Integer.parseInt(numLanesField.getText());
        int length = Integer.parseInt(trackLengthField.getText());

        // Create and place horses at the start line
        for (int i = 0; i < lanes; i++) {
            // Use 0 for speed and distance initially, and set the lane number
        	 raceResults.add(new RaceResult("Horse " + (i + 1), 0, 0, i)); // lane number starts from 0
  }
     // Create and place horses at the start line
        for (int i = 0; i < lanes; i++) {
            Color color = getColorFromSelection(colorComboBox.getSelectedItem().toString());
            String accessory = accessoryComboBox.getSelectedItem().toString();
            String name = "Horse " + (i + 1);
            char horseSymbol = (char) ('A' + i);  // Assigns a unique symbol (A, B, C, etc.) to each horse
            double horseConfidence = Math.random(); // Randomly assigns confidence, this could also be influenced by user input or other factors

            // Instantiate Horse with the updated constructor that includes all necessary parameters
            Horse horse = new Horse(horseSymbol, name, horseConfidence, color, accessory, i);
            horses.add(horse);
        }

        
        // Run the race in a new Thread to avoid freezing the GUI
        new Thread(() -> {
            boolean raceOver = false;
            while (!raceOver) {
            	 for (Horse horse : horses) {
            		 double newSpeed = Math.random() * 5; // Random speed for each iteration
                     horse.setSpeed(newSpeed); // Update horse's speed
                     horse.moveForward(); // This moves the horse forward
                     // Check if the horse has passed the finish line
                     if (horse.getDistanceTravelled() >= length * 10) {
                         raceOver = true;
                     }
                 }

            	 // Update GUI with new positions
                 SwingUtilities.invokeLater(() -> {
                     updateStats();
                     trackDisplayPanel.repaint(); // This will cause the panel to be repainted with new horse positions
                 });

                 try {
                     Thread.sleep(100); // Control the speed of the simulation
                 } catch (InterruptedException e) {
                     e.printStackTrace();
                 }
             }
         }).start();

    }

    private void updateStats() {
    	 StringBuilder sb = new StringBuilder();
    	    for (Horse horse : horses) {
    	        // Assuming Horse class has methods getSpeed() and calculateTime()
    	        sb.append(String.format("%s: Speed %.2f m/s, Time %.2f s\n", horse.getName(), horse.getSpeed(), horse.getTime()));
    	    }
    	    statsTextArea.setText(sb.toString());
    }

    private void drawTrack(Graphics g) {
    	 String lanesText = numLanesField.getText();
         String lengthText = trackLengthField.getText();
         
         if (lanesText.isEmpty() || lengthText.isEmpty()) {
             
             return; // Skip drawing
         }
         
         int lanes = Integer.parseInt(lanesText);
         int length = Integer.parseInt(lengthText);
         
         // Define the starting position and height of each lane
         int startY = 10; // Starting Y position for the first lane
         int laneHeight = 20;
         int laneMargin = 17; // Margin between lanes
         
         // Draw the lanes
         for (int i = 0; i < lanes; i++) {
             int laneY = startY + i * (laneHeight + laneMargin);
             g.drawRect(10, laneY, length * 10, laneHeight);
         }
         
    
         
      // Assuming horseWidth and horseHeight are defined and you have a horse image of this size
         int horseWidth = 20; 
         int horseHeight = 20; 
         
         // Loop through the horse list and draw each one
         for (Horse horse : horses) {
             int xPosition = 10 + (int) horse.getDistanceTravelled();
             int yPosition = startY + horse.getLane() * (laneHeight + laneMargin);
             horse.draw(g, xPosition, yPosition, horseWidth, horseHeight);
         }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new TrackDesign());
    }

    class RaceResult {
        private String horseName;
        private double speed;
        private double time;
        private int lane;
        private double distanceTravelled;

        public RaceResult(String horseName, double speed, double distanceTravelled, int lane) {
            this.horseName = horseName;
            this.speed = speed;
            this.distanceTravelled = distanceTravelled;
         
            this.lane = lane;
        }
        
        

        // Getter and setter methods for distanceTravelled
        public double getDistanceTravelled() {
            return distanceTravelled;
        }

        public void setDistanceTravelled(double distanceTravelled) {
            this.distanceTravelled = distanceTravelled;
        }

        // Getter and setter methods for speed
        public double getSpeed() {
            return speed;
        }

        public void setSpeed(double speed) {
            this.speed = speed;
        }

        // Rest of the getters and setters
        public String getHorseName() {
            return horseName;
        }

        public double getTime() {
            if (speed == 0) {
                return 0;
            }
            return distanceTravelled / speed;
        }

        public int getLane() {
            return lane;
        }
    }

    class Bet {
        private String horseName;
        private double amount;

        public Bet(String horseName, double amount) {
            this.horseName = horseName;
            this.amount = amount;
        }

        public String getHorseName() {
            return horseName;
        }

        public double getAmount() {
            return amount;
        }
    }
}