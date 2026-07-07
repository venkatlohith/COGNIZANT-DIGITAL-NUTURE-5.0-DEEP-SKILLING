public class Main {

    public static void main(String[] args) {

        Student student =
                new Student("Saketh", "101", "A");

        StudentView view = new StudentView();

        StudentController controller =
                new StudentController(student, view);

        controller.updateView();
    }
}