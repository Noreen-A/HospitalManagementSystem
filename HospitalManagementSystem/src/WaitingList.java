public class WaitingList {
    private PriorityQueue queue;
    private int counter;

    public WaitingList() {
        this.queue = new PriorityQueue(300);
        this.counter = 0;
    }
    public boolean isEmpty() {
        return queue.isEmpty();
    }
    public void addToWaitList(Patient patient) {
        counter++;
        queue.insert(patient);
    }

    public Patient removeFromWaitList() {
        if (queue.isEmpty()) {
            System.out.println("The waiting list is empty.");
            return null;
        }
        Patient patient = queue.delete();
        return patient;
    }

    public void showWaitingList() {
        if (queue.isEmpty()) {
            System.out.println("The waiting list is empty.");
        } else {
            System.out.println("Current Waiting List:");
            queue.stream().sorted().forEach(patient -> System.out.println(patient.getPatientInfo()));
        }
    }


}
