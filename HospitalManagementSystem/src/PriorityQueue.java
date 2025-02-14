import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

public class PriorityQueue {
    private Patient[] waitingList;
    private int size;
    private int capacity;
    private Map<Patient, Integer> patientIndexMap;

    public PriorityQueue(int capacity) {
        this.capacity = capacity;
        this.waitingList = new Patient[capacity + 1]; // 1-based index
        this.size = 0;
        this.patientIndexMap = new HashMap<>();
    }

    private int parentNode(int child) { return child / 2; }
    private int leftChild(int parent) { return parent * 2; }
    private int rightChild(int parent) { return parent * 2 + 1; }

    public boolean isEmpty() { return size == 0; }

    private void resize() {
        this.capacity *= 2;
        this.waitingList = Arrays.copyOf(waitingList, capacity + 1);
    }

    public void insert(Patient patient) {
        if (size == capacity) resize();
        size++;
        waitingList[size] = patient;
        patientIndexMap.put(patient, size);
        heapifyUp(size);
    }

    public boolean offer(Patient patient) {
        insert(patient);
        return true;
    }

    private void heapifyUp(int index) {
        Patient temp = waitingList[index]; // Avoid repeated array access
        while (index > 1 && temp.getPriority() < waitingList[parentNode(index)].getPriority()) {
            waitingList[index] = waitingList[parentNode(index)];
            patientIndexMap.put(waitingList[index], index);
            index = parentNode(index);
        }
        waitingList[index] = temp;
        patientIndexMap.put(temp, index);
    }

    private void swap(int i, int j) {
        Patient temp = waitingList[i];
        waitingList[i] = waitingList[j];
        waitingList[j] = temp;

        patientIndexMap.put(waitingList[i], i);
        patientIndexMap.put(waitingList[j], j);
    }

    public Patient delete() {
        if (isEmpty()) throw new IllegalStateException("Waiting list is empty");
        Patient patient = waitingList[1];
        patientIndexMap.remove(patient);

        if (size > 1) {
            waitingList[1] = waitingList[size];
            patientIndexMap.put(waitingList[1], 1);
            heapify(1);
        }
        size--;
        return patient;
    }

    private void heapify(int i) {
        int smallest = i;
        int left = leftChild(i);
        int right = rightChild(i);

        if (left <= size && waitingList[left].getPriority() < waitingList[smallest].getPriority()) {
            smallest = left;
        }
        if (right <= size && waitingList[right].getPriority() < waitingList[smallest].getPriority()) {
            smallest = right;
        }
        if (smallest != i) {
            swap(i, smallest);
            heapify(smallest);
        }
    }

    public Stream<Patient> stream() {
        return Arrays.stream(waitingList, 1, size + 1);
    }

    public Patient peek() {
        if (isEmpty()) throw new IllegalStateException("Waiting list is empty");
        return waitingList[1];
    }

    public boolean remove(Patient patient) {
        Integer index = patientIndexMap.get(patient);
        if (index == null) return false;

        waitingList[index] = waitingList[size];
        patientIndexMap.put(waitingList[index], index);
        patientIndexMap.remove(patient);
        size--;

        if (size > 0) heapify(index);
        return true;
    }

    public boolean contains(Patient patient) {
        return patientIndexMap.containsKey(patient);
    }
}
