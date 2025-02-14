import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Patient extends Person {
    private int patientID;
    private String condition;
    private int priority;
    private LocalDateTime arrivalTime;
    private List<VisitRecord> visitRecords;
    private Billing billing;

    public Patient(int patientID, String name, int age, String contactInfo, String condition, int priority) {
        super(name, age, contactInfo);
        this.patientID = patientID;
        this.condition = condition;
        this.priority = priority;
        this.arrivalTime = LocalDateTime.now();
        this.visitRecords = new ArrayList<>();
        this.billing = new Billing(patientID);
    }

    public int getPatientID() {
        return patientID;
    }


    public int getPriority() {
        return priority;
    }


    public void updateContactInfo(String newContactInfo) {
        super.setContactInfo(newContactInfo);
        System.out.println("Updated contact info for Patient ID " + patientID + " to: " + newContactInfo);
    }

    public void addVisitRecord(Appointment appointment, String doctor, String diagnosis, String treatment) {
        VisitRecord visit = new VisitRecord(appointment.getAppointmentDateTime(), doctor, diagnosis, treatment);
        visitRecords.add(visit);
        this.arrivalTime = LocalDateTime.now();
        System.out.println("Added visit record for Patient ID " + patientID + " and updated arrival time.");
    }

    public String getPatientInfo() {
        StringBuilder info = new StringBuilder();
        info.append("- Patient ID: ").append(patientID)
                .append("\n- Name: ").append(super.getName())
                .append("\n- Age: ").append(super.getAge())
                .append("\n- Contact Info: +20 1").append(super.getContactInfo())
                .append("\n- Condition: ").append(condition)
                .append("\n- Arrival Time: ").append(arrivalTime)
                .append("\n- Visit Records:\n");

        for (VisitRecord visit : visitRecords) {
            info.append(visit.toString()).append("\n");
        }

        info.append("- Billing Status: ").append(billing.getPaymentStatus())
                .append("\n============================");

        return info.toString();
    }

    @Override
    public String toString() {
        return getPatientInfo();
    }
}
