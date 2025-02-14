import java.util.*;

public class BillingManager {
    private final Map<Integer, Billing> billingRecords = new HashMap<>();
    private final Set<Integer> patientIDs = new HashSet<>();

    public void addBillingRecord(Billing billing) {
        if (billing == null || patientIDs.contains(billing.getPatientID())) {
            throw new IllegalArgumentException("Duplicate or null billing record.");
        }
        billingRecords.put(billing.getPatientID(), billing);
        patientIDs.add(billing.getPatientID());
    }

    public List<Billing> getAllBillings() {
        return new ArrayList<>(billingRecords.values());
    }

    public Optional<Billing> findBillingByPatientID(int patientID) {
        return Optional.ofNullable(billingRecords.get(patientID));
    }

    public boolean removeBillingRecord(int patientID) {
        if (!billingRecords.containsKey(patientID)) return false;
        billingRecords.remove(patientID);
        patientIDs.remove(patientID);
        return true;
    }
}
