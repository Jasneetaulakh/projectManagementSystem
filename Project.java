public class Project {

    private static int projNum;
    private String projName;
    private String buildingType;
    private String projAddress;
    private int erfNum;
    private double totalFee;
    private double totalPaid;
    private String deadline;
    private Person architect;
    private Person contractor;
    private Person customer;

    public Project(String projName, String buildingType,
                   String projAddress, int erfNum, double totalFee,
                   double totalPaid, String deadline, Person architect, Person contractor,
                   Person customer) {
        projNum++;
        this.projName = projName;
        this.buildingType = buildingType;
        this.projAddress = projAddress;
        this.erfNum = erfNum;
        this.totalFee = totalFee;
        this.totalPaid = totalPaid;
        this.deadline = deadline;
        this.architect = architect;
        this.contractor = contractor;
        this.customer = customer;
    }

    public String toString() {
        String output = "Project Num: " + projNum +
                "\nProject Name: " + projName + "\n" +
                "Building Type: " + buildingType + "\n" +
                "Project Address: " + projAddress + "\n" +
                "ERF Number: " + erfNum + "\n" +
                "Total Fee: " + totalFee + "\n" +
                "Total paid: " + totalPaid + "\n" +
                "Deadline: " + deadline + "\n" +
                "Architect: " + architect.toString() + "\n" +
                "Contractor: " + contractor.toString() + "\n" +
                "Customer: " + customer.toString();
        return output;
    }


    public int getProjNum() {
        return projNum;
    }

    public void setProjNum(int projNum) {
        this.projNum = projNum;
    }

    public String getProjName() {
        return projName;
    }

    public void setProjName(String projName) {
        this.projName = projName;
    }

    public Person getArchitect() {
        return architect;
    }

    public void setArchitect(Person architect) {
        this.architect = architect;
    }

    public String getDeadline() {
        return deadline;
    }

    public void setDeadline(String deadline) {
        this.deadline = deadline;
    }

    public double getTotalPaid() {
        return totalPaid;
    }

    public void setTotalPaid (double totalPaid) {
        this.totalPaid = totalPaid;
    }

}//end class
