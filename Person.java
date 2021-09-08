public class Person {

    private String name;
    private String phoneNum;
    private String emailAdd;
    private String address;

    public Person(String name, String phoneNum, String emailAdd,
                  String address) {
        this.name = name;
        this.phoneNum = phoneNum;
        this.emailAdd = emailAdd;
        this.address = address;
    }

    public String toString() {
        return "\n" +
                "Name: " + name +
                "\nPhone Num: " + phoneNum +
                "\nEmail add: " + emailAdd +
                "\nAddress: " + address;
    }


    public String getName() {
        return name;
    }

    public String getPhoneNum() {
        return phoneNum;
    }

    public String getEmailAdd() {
        return emailAdd;
    }

    public String getAddress() {
        return address;
    }


    public void setName(String name) {
        this.name = name;
    }

    public void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum;
    }

    public void setEmailAdd(String emailAdd) {
        this.emailAdd = emailAdd;
    }

    public void setAddress(String address) {
        this.address = address;
    }



}
