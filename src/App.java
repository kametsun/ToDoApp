import java.time.LocalDate;
import java.util.Scanner;
import java.util.List;

public class App {
    public static void main(String[] args) throws Exception {
        ToDoList toDoList = new ToDoList();
        Scanner sc = new Scanner(System.in);
        boolean running = true;

        while (running) {
            System.out.println("----- ToDo App -----");
            System.out.println("1. タスクの追加");
            System.out.println("2. タスクの削除");
            System.out.println("3. タスクの編集");
            System.out.println("4. タスクの表示");
            System.out.println("5. 終了");
            System.out.println("選択してください: ");
            int choice = sc.nextInt();

            switch (choice) {
                case 1: {
                    sc.nextLine();
                    System.out.println("タイトルを入力してください: ");
                    String title = sc.nextLine();
                    System.out.println("期日を入力してください (YYYY-MM-DD)");
                    String strDeadline = sc.nextLine();
                    LocalDate deadline = LocalDate.parse(strDeadline);
                    Task newTask = new Task(title, deadline);
                    toDoList.addTask(newTask);
                    System.out.println("追加に成功しました");
                    break;
                }
                case 2: {
                    System.out.println("削除したいタスクの番号を入力してください: ");
                    int removeIndex = sc.nextInt();
                    if (removeIndex >= 1 && removeIndex <= toDoList.getTasks().size()) {
                        Task taskToRemove = toDoList.getTasks().get(removeIndex - 1);
                        toDoList.removeTask(taskToRemove);
                        System.out.println("削除に成功");
                    } else {
                        System.out.println("無効です");
                    }
                    break;
                }
                case 3: {
                    System.out.println("編集したいタスクの番号を入力してください: ");
                    int editIndex = sc.nextInt();
                    if (editIndex >= 1 && editIndex <= toDoList.getTasks().size()) {
                        sc.nextLine();
                        Task taskToEdit = toDoList.getTasks().get(editIndex - 1);
                        System.out.println("タスクのタイトル: ");
                        String newTitle = sc.nextLine();
                        System.out.println("タスクの期日(YYYY-MM-DD)");
                        String strNewDeadline = sc.nextLine();
                        LocalDate newDeadline = LocalDate.parse(strNewDeadline);
                        toDoList.editTask(taskToEdit, newTitle, newDeadline);
                        System.out.println("タスクの編集に成功");
                    } else {
                        System.out.println("無効です");
                    }
                    break;
                }
                case 4: {
                    System.out.println("----- Task List -----");
                    List<Task> tasks = toDoList.getTasksFromDB();
                    if(tasks.isEmpty()){
                        System.out.println("タスクはありません");
                    } else {
                        for(int i = 0; i < tasks.size(); i++){
                            Task task = tasks.get(i);
                            System.out.println(i + 1 + ": " + task.getTitle());
                            System.out.println("期日: " + task.getDeadline());
                            System.out.println("ステータス: " + task.getStatus());
                            System.out.println();
                        }
                    }

                    break;
                }
                case 5: {
                    running = false;
                    System.out.println("終了します");
                    break;
                }
                default: {
                    System.out.println("無効です。");
                }
                    System.out.println();
            }
        }
        sc.close();
        System.out.println(System.getProperty("java.class.path"));
    }
}
