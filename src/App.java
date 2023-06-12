import java.time.LocalDate;
import java.util.Scanner;
import java.util.List;

public class App {

    public static void main(String[] args) throws Exception {
        ToDoList toDoList = new ToDoList();
        Scanner sc = new Scanner(System.in);
        boolean running = true;

        while (running) {
            start();
            int choice = sc.nextInt();
            switch (choice) {
                case 1: {
                    sc.nextLine();
                    System.out.println("タイトルを入力してください: ");
                    String title = sc.nextLine();
                    System.out.println("期日を入力してください (YYYY-MM-DD)");
                    String strDeadline = sc.nextLine();
                    LocalDate deadline = LocalDate.parse(strDeadline);
                    if (printNewTask(title, deadline)) {
                        Task newTask = new Task(title, deadline);
                        toDoList.addTask(newTask);
                        System.out.println("追加に成功しました");
                    } else {
                        System.out.println("メニューに戻ります");
                    }
                    break;
                }
                case 2: {
                    System.out.println("削除したいタスクidを入力してください: ");
                    int removeIndex = sc.nextInt();
                    if (removeIndex >= 1) {
                        Task taskToRemove = toDoList.getTaskByIdFromDB(removeIndex);
                        printTask(taskToRemove);
                        if(confirmRemove()){
                            toDoList.removeTask(taskToRemove);
                        }
                    } else {
                        System.out.println("タスクidが無効です");
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
                    if (tasks.isEmpty()) {
                        System.out.println("タスクはありません");
                    } else {
                        for (int i = 0; i < tasks.size(); i++) {
                            Task task = tasks.get(i);
                            printTask(task);
                        }
                    }
                    break;
                }
                case 5: {
                    System.out.println("タスクを検索します");
                    System.out.print("表示したいタスクのIDを入力してください: ");
                    int taskId = sc.nextInt();
                    Task task = toDoList.getTaskByIdFromDB(taskId);
                    if (task != null) {
                        printTask(task);
                        nextRemove(task);
                    } else {
                        System.out.println("指定されたIDのタスクが見つかりません");
                    }
                    break;
                }
                case 6: {
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
    }

    private static void start() {
        System.out.println("----- ToDo App -----");
        System.out.println("1. タスクの追加(完成)");
        System.out.println("2. タスクの削除");
        System.out.println("3. タスクの編集");
        System.out.println("4. タスクの表示(完成)");
        System.out.println("5. タスク検索");
        System.out.println("6. 終了(完成)");
        System.out.print("選択してください: ");
    }

    private static void nextRemove(Task task) {
        Scanner sc = new Scanner(System.in);
        System.out.print("削除しますか？ y : ");
        String ans = sc.nextLine();
        sc.close();
        if (ans.equals("y")) {
            ToDoList tdl = new ToDoList();
            tdl.removeTask(task);
        }
    }

    private static void printTask(Task task) {
        System.out.println("_____________________________________");
        System.out.println("タスクID: " + task.getId());
        System.out.println("タイトル: " + task.getTitle());
        System.out.println("期日: " + task.getDeadline());
        System.out.println("ステータス: " + task.getStatus());
        System.out.println("_____________________________________");
    }

    private static boolean printNewTask(String title, LocalDate deadline) {
        boolean isAdd = false;
        Scanner sc = new Scanner(System.in);

        System.out.println("_____________________________________");
        System.out.println("タイトル: " + title);
        System.out.println("期日: " + deadline);
        System.out.println("_____________________________________");
        System.out.print("よろしいですか？ y / n: ");
        String ans = sc.nextLine();

        if (ans.equals("y")) {
            isAdd = true;
        }
        sc.close();
        return isAdd;
    }

    private static boolean confirmRemove() {
        boolean isRemove = false;
        Scanner sc = new Scanner(System.in);

        System.out.print("削除しますか？ y: ");
        String ans = sc.nextLine();
        if(ans.equals("y")){
            isRemove = true;
        }
        sc.close();
        return isRemove;
    }
}
