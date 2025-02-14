import java.util.HashSet;
import java.util.Random;

public class IDGenerator {
    private static final HashSet<Integer> usedIDs = new HashSet<>();
    private static final Random random = new Random();
    private static int currentAppointmentID = 99; // Start before the minimum range
    private static final int minAppointmentID = 100;
    private static final int maxAppointmentID = 500;

    public static int generateUniquePatientID() {
        if (usedIDs.size() >= 999901) {
            throw new IllegalStateException("No more unique IDs available.");
        }

        int id;
        do {
            id = 100 + random.nextInt(999901); // Generates number between 100 and 1,000,000
        } while (!usedIDs.add(id)); // Add to set and check if it's already present

        return id;
    }
    public static int generateNextAppointmentID() {
        if (currentAppointmentID < maxAppointmentID) {
            currentAppointmentID++; // Increment to the next ID
            return currentAppointmentID;
        } else {
            throw new IllegalStateException("Maximum appointment ID limit reached.");
        }
    }
}
