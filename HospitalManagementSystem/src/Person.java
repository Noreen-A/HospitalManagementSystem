public class Person {
    private String name;
    private int age;
    private String contactInfo;

    public Person(){
        this.name = null;
        this.age = 0;
        this.contactInfo = "+20 0000000000";
    }
    public Person(String name, int age, String contactInfo){
        this.name = name;
        this.age = age;
        this.contactInfo = contactInfo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        if (name == null || name.trim().isEmpty() || !name.matches("[a-zA-Z ]+")) {
            throw new IllegalArgumentException("Invalid name! Name must contain only letters.");
        }
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        if (age < 0 || age > 120) {
            throw new IllegalArgumentException("Invalid age! Age must be between 0 and 120.");
        }
        this.age = age;
    }

    public String getContactInfo() {
        return contactInfo;
    }

    public void setContactInfo(String contactInfo) {
        if (!contactInfo.matches("\\d{11}")) {
            throw new IllegalArgumentException("Invalid contact number! Must be 11 digits.");
        }
        this.contactInfo = contactInfo;    }
}
