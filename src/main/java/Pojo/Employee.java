package Pojo;

public class Employee {

    public String name;
    public String salary;
    public String age;

    public Employee(String name, String salary, String age) {
        this.name = name;
        this.salary = salary;
        this.age = age;
    }

    public String getName() { return name; }
    public String getSalary() { return salary; }
    public String getAge() { return age; }

    public void setName(String name) { this.name = name; }
    public void setSalary(String salary) { this.salary = salary; }
    public void setAge(String age) { this.age = age; }
}
