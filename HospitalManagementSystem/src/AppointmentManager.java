import java.time.LocalDateTime;
import java.util.*;

public class AppointmentManager {
    private WaitingList waitingList;
    private List<Appointment> appointments;
    private PriorityQueue priorityQueue;

    public AppointmentManager() {
        this.appointments = new ArrayList<>();
        this.waitingList = new WaitingList();
        this.priorityQueue = new PriorityQueue(300);
    }


    public Appointment getConflictingAppointment(LocalDateTime appointmentDateTime) {
        for (Appointment appointment : appointments) {
            if (appointment.getAppointmentDateTime().equals(appointmentDateTime)) {
                return appointment;
            }
        }
        return null;
    }

    public void scheduleAppointment(int appointmentID, Patient newPatient, LocalDateTime appointmentDateTime) {
        Appointment conflictingAppointment = getConflictingAppointment(appointmentDateTime);

        if (conflictingAppointment != null) {
            Patient conflictingPatient = conflictingAppointment.getPatient();
            if (conflictingAppointment.getStatus().equals("Cancelled")) {
                addAppointment(new Appointment(appointmentID, newPatient, appointmentDateTime));
                conflictingAppointment.setStatus("Scheduled");
                conflictingAppointment.setPatient(newPatient);
            }else if (newPatient.getPriority() < conflictingPatient.getPriority()) {
                System.out.println("Higher-priority patient detected. Rescheduling lower-priority patient.");

                cancelAppointment(conflictingAppointment.getAppointmentID());
                addAppointment(new Appointment(appointmentID, newPatient, appointmentDateTime));
                waitingList.addToWaitList(conflictingPatient);
                conflictingAppointment.setStatus("Added to waiting list");
                System.out.println("Patient " + conflictingPatient.getName() + " moved to the waiting list.");
            } else {
                System.out.println("Appointment conflict detected. Adding patient to waiting list.");
                waitingList.addToWaitList(newPatient);
            }

        } else {
            addAppointment(new Appointment(appointmentID, newPatient, appointmentDateTime));
        }
    }

    private void addAppointment(Appointment appointment) {
        appointments.add(appointment);
        priorityQueue.insert(appointment.getPatient());
        System.out.println("Appointment scheduled successfully: " + appointment);
    }

    public boolean cancelAppointment(int appointmentID) {
        Iterator<Appointment> iterator = appointments.iterator();

        while (iterator.hasNext()) {
            Appointment appointment = iterator.next();
            if (appointment.getAppointmentID() == appointmentID) {
                iterator.remove();
                priorityQueue.remove(appointment.getPatient());
                appointment.setStatus("Cancelled");
                appointments.remove(appointment);
                System.out.println("Appointment " + appointmentID + " has been cancelled.");

                // Schedule the highest-priority patient from the waiting list if available
                if (!waitingList.isEmpty()) {
                    Patient nextPatient = waitingList.removeFromWaitList();
                    scheduleAppointment(IDGenerator.generateNextAppointmentID(), nextPatient, appointment.getAppointmentDateTime());
                    System.out.println("Patient " + nextPatient.getName() + " from the waiting list has been scheduled.");
                }

                return true;
            }
        }
        System.out.println("Appointment not found.");
        return false;
    }

    public List<Appointment> displayAppointments() {
        return appointments;
    }
    public Appointment getAppointment(int patientID) {
        for (Appointment appointment : appointments) {
            if (appointment.getPatient().getPatientID() == patientID) {
                return appointment;
            }
        }
        return null; // Return null if no appointment is found for the given patient ID
    }


    public void processWaitingList() {
        System.out.println("Processing patient from waiting list: ");
        while (!waitingList.isEmpty()) {
            Patient patient = waitingList.removeFromWaitList();
            System.out.println(patient.getPatientInfo());
        }
    }

    public Appointment getNextAppointment() {
        return appointments.isEmpty() ? null : appointments.getFirst();
    }
}
