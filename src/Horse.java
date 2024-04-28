package horse;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.io.IOException;
import java.net.URL;

import javax.swing.ImageIcon;

public class Horse {
    private char horseSymbol;
    private String horseName;
    private double horseConfidence;
    private int distanceTravelled;
    private boolean fallen;
   
    private double confidence;
    
    private String name;
    private double speed;
    private int lane;
    private Color color;
    private String accessory;
    private Image horseImage;

 // Expanded constructor
    public Horse(char horseSymbol, String name, double horseConfidence, Color color, String accessory, int lane) {
        this.horseSymbol = horseSymbol;
        this.name = name;
        this.horseConfidence = Math.max(0, Math.min(1, horseConfidence)); // Ensures confidence is within 0 and 1
        this.color = color;
        this.accessory = accessory;
        this.lane = lane;
        this.speed = speed;
        this.distanceTravelled = 0; // Initial distance
        this.fallen = false; // Initial fallen state
        this.confidence = horseConfidence;
       
        try {
            URL imageUrl = getClass().getResource("horse.gif");
            if (imageUrl == null) {
                throw new IOException("Resource not found: /horse.png");
            }
            ImageIcon ii = new ImageIcon(imageUrl);
            this.horseImage = ii.getImage();
        } catch (IOException e) {
            e.printStackTrace();
            this.horseImage = null;
        }

        
    }
    
    public void draw(Graphics g, int xPosition, int yPosition, int width, int height) {
    	 if (horseImage != null) {
    	        g.drawImage(horseImage, xPosition, yPosition, width, height, null);
    	    } else {
    	        // Draw a placeholder if image is not found
    	        g.setColor(Color.BLUE); // Placeholder color
    	        g.fillRect(xPosition, yPosition, width, height);
    	    }
    	 
    	 // Draw the confidence level
         g.drawString(String.format("Confidence: %.2f", confidence), xPosition, yPosition + height + 15);
        


//        // Draw the accessory if any
//        if ("Saddle".equals(this.accessory)) {
//            g.setColor(Color.blue); 
//            g.fillRect(xPosition, yPosition + (height / 2), width, height / 4);
//        }
        // Add other conditions for different accessories
    }
    
    
    public int getLane() {
        return this.lane;
    }
  
    public void fall() {
        fallen = true;
    }
     

    public String getName() {
        return name;
    }

    public double getConfidence() {
        return horseConfidence;
    }
    public char getSymbol() {
        return horseSymbol;
    }
    public void goBackToStart() {
        distanceTravelled = 0;
    }
    public boolean hasFallen() {
        return fallen;
    }
    
 
    
    public void setConfidence(double newConfidence) {
        horseConfidence = newConfidence;
    }
    
    // Methods for horse movement and status
    public void moveForward() {
        if (!fallen) {
            this.distanceTravelled += (int) (1 + 5 * horseConfidence);
        }
    }
  
    public void setSymbol(char newSymbol) {
        horseSymbol = newSymbol;
    }
    
    // Getter and setter methods for distanceTravelled
    public double getDistanceTravelled() {
        return distanceTravelled;
    }

    public void setDistanceTravelled(double distanceTravelled) {
        this.distanceTravelled = (int) distanceTravelled;
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
    
   


}