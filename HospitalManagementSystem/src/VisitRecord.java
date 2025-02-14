import java.time.LocalDateTime;

public class VisitRecord {
    private LocalDateTime date;
    private String doctor;
    private String diagnosis;
    private String treatment;

    public VisitRecord(LocalDateTime date, String doctor, String diagnosis, String treatment) {
        this.date = date;
        this.doctor = doctor;
        this.diagnosis = diagnosis;
        this.treatment = treatment;
    }

    @Override
    public String toString() {
        return "- Date: " + date +
                "\n- Doctor: " + doctor +
                "\n - Diagnosis: " + diagnosis +
                "\n - Treatment: " + treatment;
    }
}

