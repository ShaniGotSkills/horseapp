package horse;


import java.util.concurrent.TimeUnit;
import java.lang.Math;



public class Race {
    private int raceLength;
    private Horse lane1Horse;
    private Horse lane2Horse;
    private Horse lane3Horse;
    private static int stepCounter = 0;

    public Race(int distance) {
        this.raceLength = distance;
        this.lane1Horse = null;
        this.lane2Horse = null;
        this.lane3Horse = null;
    }

    public void addHorse(Horse theHorse, int laneNumber) {
        switch (laneNumber) {
            case 1: lane1Horse = theHorse; break;
            case 2: lane2Horse = theHorse; break;
            case 3: lane3Horse = theHorse; break;
            default: System.out.println("Invalid lane number " + laneNumber);
        }
    }

    public void startRace() {
        Horse winner = null;
        double Confidence = 0;
        while (winner == null) {
            if (lane1Horse != null) moveHorse(lane1Horse);
            if (lane2Horse != null) moveHorse(lane2Horse);
            if (lane3Horse != null) moveHorse(lane3Horse);

//            printRace();

            winner = checkForWinner();

            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                System.out.println("Race interrupted.");
            }
        }
        if (winner != null) {
            Confidence = winner.getConfidence() * 10;
            Confidence = Math.round(Confidence);
            Confidence = Confidence / 10;
            System.out.println("And the winner is " + winner.getName() + " with confidence " + Confidence + "!");
        }
    }

    private void moveHorse(Horse theHorse) {
        if (!theHorse.hasFallen()) {
            double confidenceChange = (Math.random() - 0.5) * 0.1;
            theHorse.setConfidence(Math.max(0, Math.min(1, theHorse.getConfidence() + confidenceChange)));
    
            if (Math.random() < theHorse.getConfidence()) {
                theHorse.moveForward();
            }
    
            if (Math.random() < (0.1 * theHorse.getConfidence() * theHorse.getConfidence())) {
                theHorse.fall();
            }
        }
    }

    private Horse checkForWinner() {
        if (lane1Horse != null && lane1Horse.getDistanceTravelled() >= raceLength-1) return lane1Horse;
        if (lane2Horse != null && lane2Horse.getDistanceTravelled() >= raceLength-1) return lane2Horse;
        if (lane3Horse != null && lane3Horse.getDistanceTravelled() >= raceLength-1) return lane3Horse;
        return null;
    }

//    private void printRace() {
//        System.out.println("\nCurrent Race Positions:");
//        multiplePrint('=', raceLength + 2);
//        System.out.println();
//
//        if (lane1Horse != null) printLane(lane1Horse);
//        if (lane2Horse != null) printLane(lane2Horse);
//        if (lane3Horse != null) printLane(lane3Horse);
//
//        multiplePrint('=', raceLength + 2);
//        System.out.println();
//    }
    
    
    
//    private void printLane(Horse theHorse) {
//        System.out.print('|');
//    
//        // Space padding for horse's travelled distance
//        multiplePrint(' ', theHorse.getDistanceTravelled());
//    
//        // Show the horse's symbol or a mark if it has fallen
//        System.out.print(theHorse.hasFallen() ? '✖' : theHorse.getSymbol());
//
//        // Space padding after horse's symbol up to raceLength
//        multiplePrint(' ', raceLength - theHorse.getDistanceTravelled());
//    
//        // Print the finishing line '|' and the horse's name with its confidence level
//        System.out.print("| " + theHorse.getName() + " (Confidence: " + String.format("%.1f", theHorse.getConfidence()) + ")");
//        System.out.println();
//    }


    private void multiplePrint(char aChar, int times) {
        for (int i = 0; i < times; i++) {
            System.out.print(aChar);
        }
    }
}
