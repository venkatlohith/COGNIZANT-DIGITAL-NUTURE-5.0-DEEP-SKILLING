class Employee {

    int employeeId;
    String name;
    String position;
    double salary;

    Employee(int employeeId,
             String name,
             String position,
             double salary) {

        this.employeeId = employeeId;
        this.name = name;
        this.position = position;
        this.salary = salary;
    }
}

public class EmployeeManagement {

    static Employee[] employees = new Employee[10];
    static int count = 0;

    static void addEmployee(Employee e) {
        employees[count++] = e;
    }

    static void searchEmployee(int id) {

        for (int i = 0; i < count; i++) {

            if (employees[i].employeeId == id) {

                System.out.println(
                        "Found: " + employees[i].name);

                return;
            }
        }

        System.out.println("Employee Not Found");
    }

    static void traverseEmployees() {

        for (int i = 0; i < count; i++) {

            System.out.println(
                    employees[i].employeeId + " "
                            + employees[i].name);
        }
    }

    static void deleteEmployee(int id) {

        for (int i = 0; i < count; i++) {

            if (employees[i].employeeId == id) {

                for (int j = i; j < count - 1; j++) {

                    employees[j] = employees[j + 1];
                }

                count--;

                System.out.println("Deleted");
                return;
            }
        }
    }

    public static void main(String[] args) {

        addEmployee(
                new Employee(1,
                        "Saketh",
                        "Developer",
                        50000));

        addEmployee(
                new Employee(2,
                        "Ram",
                        "Tester",
                        40000));

        traverseEmployees();

        searchEmployee(1);

        deleteEmployee(2);

        traverseEmployees();
    }
}