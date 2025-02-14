import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class PatientManagementSystem {
    private static List<Patient> PatientList= new ArrayList<>();
    private static AppointmentManager appointmentManager = new AppointmentManager();
    private static BillingManager billingManager = new BillingManager();
    private static ReportGenerator reportGenerator = new ReportGenerator();
    private static PatientBST patientBST = new PatientBST();

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Welcome to the Hospital Patient Management System!");

        boolean running = true;
        while (running) {
            System.out.println("\nPlease choose an option:");
            System.out.println("1) Add Patient");
            System.out.println("2) Search Patient");
            System.out.println("3) Schedule Appointment");
            System.out.println("4) Cancel Appointment");
            System.out.println("5) Generate Report");
            System.out.println("6) Display patients");
            System.out.println("7) Add visit records");
            System.out.println("8) generate a bill");
            System.out.println("9) Exit");
            System.out.print("Enter your choice: ");

            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    addPatient(scanner);
                    break;
                case 2:
                    searchPatient(scanner);
                    break;
                case 3:
                    scheduleAppointment(scanner);
                    break;
                case 4:
                    cancelAppointment(scanner);
                    break;
                case 5:
                    generateReport(scanner);
                    break;
                case 6:
                    DisplayPatients();
                    break;
                case 7:
                    addVisitRecord(scanner);
                    break;
                case 8:
                    generateBill(scanner);
                    break;
                case 9:
                    running = false;
                    System.out.println("Exiting the system. Goodbye!");
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }

        scanner.close();
    }

    private static void addPatient(Scanner scanner) {
        System.out.println("\n--- Add Patient ---");
        System.out.print("Enter Patient Name: ");
        String name = InputValidator.getValidString(scanner);
        System.out.print("Enter Patient Age: ");
        int age = InputValidator.getValidAge(scanner);
        String contactInfo = InputValidator.getValidContactInfo(scanner);
        System.out.print("Enter Condition: ");
        String condition = scanner.nextLine();
        System.out.print("Enter Priority: ");
        int priority = scanner.nextInt();


        Patient newPatient = new Patient(IDGenerator.generateUniquePatientID(), name, age, contactInfo,condition, priority);
        patientBST.addPatient(newPatient);
        PatientList.add(newPatient);

        System.out.println("Patient added successfully! Patient ID: " + newPatient.getPatientID());
    }

    private static void searchPatient(Scanner scanner) {
        System.out.println("\n--- Search Patient ---");
        System.out.print("Enter Patient ID: ");
        int patientID = scanner.nextInt();

        Patient patient = patientBST.findPatient(patientID);
        if (patient != null) {
            System.out.println("Patient Found: " + patient.getPatientInfo());
        } else {
            System.out.println("Patient not found!");
        }
    }
    public static void DisplayPatients(){
        System.out.println("------All patients------");
        System.out.println(patientBST.getAllPatients());
    }

    private static void scheduleAppointment(Scanner scanner) {
        System.out.println("\n--- Schedule Appointment ---");
        System.out.print("Enter Patient ID: ");
        int patientID = scanner.nextInt();
        scanner.nextLine();
        LocalDateTime time = InputValidator.getValidDateTime(scanner);

        Patient patient = patientBST.findPatient(patientID);
        if (patient != null) {
            appointmentManager.scheduleAppointment(IDGenerator.generateNextAppointmentID(),patient, time);
        } else {
            System.out.println("Patient not found! Unable to schedule appointment.");
        }
    }

    private static void cancelAppointment(Scanner scanner) {
        System.out.println("\n--- Cancel Appointment ---");
        System.out.print("Enter Appointment ID: ");
        int appointmentID = scanner.nextInt();

        boolean success = appointmentManager.cancelAppointment(appointmentID);
        if (success) {
            System.out.println("Appointment canceled successfully!");
        } else {
            System.out.println("Appointment not found or already canceled.");
        }
    }

    private static void generateReport(Scanner scanner) {
        System.out.println("\n--- Generate Report ---");
        System.out.println("1) Patient Report");
        System.out.println("2) Appointment Report");
        System.out.println("3) Revenue Report");
        System.out.print("Enter your choice: ");

        int reportChoice = scanner.nextInt();
        switch (reportChoice) {
            case 1:
                System.out.println();
                System.out.println("Generating Patient Report...");
                reportGenerator.generatePatientReport(PatientList);
                break;
            case 2:
                System.out.println("Generating Appointment Report...");
                reportGenerator.generateAppointmentReport(appointmentManager.displayAppointments());
                break;
            case 3:
                System.out.println("Generating Revenue Report...");
                reportGenerator.generateRevenueReport(billingManager.getAllBillings());
                break;
            default:
                System.out.println("Invalid choice. Returning to main menu.");
        }
    }
    private static void addVisitRecord(Scanner scanner) {
        System.out.println("\n--- Add Visit Record ---");
        System.out.println("Enter Patient ID: ");
        int patientID = scanner.nextInt();
        Patient patient = patientBST.findPatient(patientID);
        Appointment appointment = appointmentManager.getAppointment(patientID);
        System.out.println("Enter Visit Details: ");
        System.out.println("Enter The Doctor's name");
        String Doctor = InputValidator.getValidString(scanner);
        System.out.println("Enter the diagnosis");
        String diagnosis = InputValidator.getValidString(scanner);
        System.out.println("Enter the treatment name");
        String treatment = InputValidator.getValidString(scanner);
        patient.addVisitRecord(appointment,Doctor,diagnosis,treatment);
        System.out.println("Visit record added successfully!");
    }

    private static void generateBill(Scanner scanner) {
        System.out.println("\n--- Generate Bill ---");
        System.out.print("Enter Patient ID: ");
        int patientID = scanner.nextInt();
        Billing bill = new Billing(patientID);
        System.out.println("Enter amount");
        BigDecimal billAmount = scanner.nextBigDecimal();
        bill.generateBill(billAmount);
        System.out.println("Enter payed money");
        BigDecimal payment = scanner.nextBigDecimal();
        bill.addPayment(payment);
        billingManager.addBillingRecord(bill);
        System.out.println("Bill Generated for Patient ID: " + patientID);
    }
}