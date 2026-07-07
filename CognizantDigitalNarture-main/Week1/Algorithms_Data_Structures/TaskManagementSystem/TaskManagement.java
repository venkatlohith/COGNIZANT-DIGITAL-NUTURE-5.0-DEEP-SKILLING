class Task {

    int taskId;
    String taskName;
    String status;
    Task next;

    Task(int taskId,
         String taskName,
         String status) {

        this.taskId = taskId;
        this.taskName = taskName;
        this.status = status;
    }
}

public class TaskManagement {

    Task head;

    void addTask(int id,
                 String name,
                 String status) {

        Task newTask =
                new Task(id, name, status);

        newTask.next = head;

        head = newTask;
    }

    void searchTask(int id) {

        Task temp = head;

        while (temp != null) {

            if (temp.taskId == id) {

                System.out.println(
                        "Found: " + temp.taskName);

                return;
            }

            temp = temp.next;
        }

        System.out.println("Task Not Found");
    }

    void traverseTasks() {

        Task temp = head;

        while (temp != null) {

            System.out.println(
                    temp.taskId + " "
                            + temp.taskName);

            temp = temp.next;
        }
    }

    void deleteTask(int id) {

        if (head == null)
            return;

        if (head.taskId == id) {

            head = head.next;
            return;
        }

        Task temp = head;

        while (temp.next != null) {

            if (temp.next.taskId == id) {

                temp.next =
                        temp.next.next;

                return;
            }

            temp = temp.next;
        }
    }

    public static void main(String[] args) {

        TaskManagement tm =
                new TaskManagement();

        tm.addTask(1,
                "Coding",
                "Pending");

        tm.addTask(2,
                "Testing",
                "Completed");

        tm.traverseTasks();

        tm.searchTask(1);

        tm.deleteTask(2);

        tm.traverseTasks();
    }
}