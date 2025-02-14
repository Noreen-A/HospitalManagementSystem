import java.util.ArrayList;
import java.util.List;

public class ReportGenerator {
    private String reportType;
    private List<String> data;
    private MergeSort<Integer> sort;

    public ReportGenerator() {
        this.data = new ArrayList<>();
        this.sort = new MergeSort<>();
    }

    public ReportGenerator(String reportType) {
        this();
        this.reportType = reportType;
    }

    public void setReportType(String reportType) {
        this.reportType = reportType;
    }

    public void generatePatientReport(List<Patient> patientList) {
        List<Integer> patientIDs = new ArrayList<>();
        for (Patient patient : patientList) {
            patientIDs.add(patient.getPatientID());
        }

        sort.mergeSort(patientIDs);

        System.out.println("Patient Report:");
        for (int id : patientIDs) {
            for (Patient patient : patientList) {
                if (patient.getPatientID() == id) {
                    System.out.println(patient.getPatientInfo());
                    System.out.println("========================");
                    break;
                }
            }
        }
    }

    public void generateAppointmentReport(List<Appointment> appointments) {
        List<Integer> appointmentIDs = new ArrayList<>();
        for (Appointment appointment : appointments) {
            appointmentIDs.add(appointment.getAppointmentID());
        }

        sort.mergeSort(appointmentIDs);

        System.out.println("Appointment Report:");
        for (int id : appointmentIDs) {
            for (Appointment appointment : appointments) {
                if (appointment.getAppointmentID() == id) {
                    System.out.println(appointment.toString());
                    break;
                }
            }
        }
    }

    public void generateRevenueReport(List<Billing> revenue) {
        List<Integer> billingPatientIDs = new ArrayList<>();
        for (Billing billing : revenue) {
            billingPatientIDs.add(billing.getPatientID());
        }

        sort.mergeSort(billingPatientIDs);

        System.out.println("Revenue Report:");
        for (int id : billingPatientIDs) {
            for (Billing billing : revenue) {
                if (billing.getPatientID() == id) {
                    System.out.println(billing.getPaymentStatus());
                    System.out.println(billing.getPaymentHistory()); // Ensure this method exists in Billing class
                    break;
                }
            }
        }
    }
}
