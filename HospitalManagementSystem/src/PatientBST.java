import java.util.ArrayList;
import java.util.List;

class PatientBST {
    private PatientNode root;
    List<Patient> patients;

    public PatientBST() {
        root = null;
        patients = new ArrayList<>();
    }


    public void addPatient(Patient patient) {
        root = insert(root, patient);
    }

    private PatientNode insert(PatientNode rootNode, Patient patient) {
        if (rootNode == null) {
            return new PatientNode(patient);
        }
        if (patient.getPatientID() < rootNode.patient.getPatientID()) {
            rootNode.left = insert(rootNode.left, patient);
        } else if (patient.getPatientID() > rootNode.patient.getPatientID()) {
            rootNode.right = insert(rootNode.right, patient);
        }
        patients.add(patient);
        return rootNode;
    }

    public Patient findPatient(int patientID) {
        return search(root, patientID);
    }

    private Patient search(PatientNode rootNode, int patientID) {
        if (rootNode == null || rootNode.patient.getPatientID() == patientID) {
            return rootNode == null ? null : rootNode.patient;
        }

        if (patientID < rootNode.patient.getPatientID()) {
            return search(rootNode.left, patientID);
        } else {
            return search(rootNode.right, patientID);
        }
    }

    public List<Patient> getAllPatients() {
        traverseInOrder(root, patients); // Assuming `root` is the root of the BST
        return patients;
    }

    private static void traverseInOrder(PatientNode node, List<Patient> patients) {
        if (node != null) {
            traverseInOrder(node.left, patients);
            System.out.println(node.patient.getPatientInfo());
            traverseInOrder(node.right, patients);
        }
    }
    public void printAllPatients() {
        System.out.println("=== All Patients ===");
        for (Patient patient : patients) {
            System.out.println(patient.getPatientInfo());
        }
        System.out.println("====================");
    }


}
