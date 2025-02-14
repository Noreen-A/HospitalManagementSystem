import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Appointment {
    private int appointmentID;
    private Patient patient;
    private LocalDateTime appointmentDateTime;
    private String status;

    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    public Appointment(int appointmentID, Patient patient, LocalDateTime appointmentDateTime) {
        this.appointmentID = appointmentID;
        this.patient = patient;
        this.appointmentDateTime = appointmentDateTime;
        this.status = "Scheduled";
    }

    public int getAppointmentID() { return appointmentID; }
    public LocalDateTime getAppointmentDateTime() { return appointmentDateTime; }
    public Patient getPatient() { return patient; }
    public void setPatient(Patient patient) {this.patient = patient;}
    public void setStatus(String status) { this.status = status; }
    public String getStatus() { return status; }

    public void reschedule(LocalDateTime newDateTime) {
        this.appointmentDateTime = newDateTime;
        this.status = "Rescheduled";
        System.out.println("Appointment " + appointmentID + " rescheduled to " + appointmentDateTime.format(formatter));
    }

    @Override
    public String toString() {
        return "Appointment{" +
                "ID='" + appointmentID + '\'' +
                ", Patient=" + patient.getName() +
                ", DateTime='" + appointmentDateTime.format(formatter) + '\'' +
                ", Status='" + status + '\'' +
                '}';
    }
}
