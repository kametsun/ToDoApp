import java.time.LocalDate;
import java.util.Scanner;
import java.util.List;

import Explanation.Explanation;

public class App {

    public static void main(String[] args) throws Exception {
        ToDoList toDoList = new ToDoList();
        Scanner sc = new Scanner(System.in);
        boolean running = true;
        Explanation.explanation();

        while (running) {
            displayMenu();
            int choice = sc.nextInt();
            System.out.println();
            switch (choice) {
                case 1: {
                    addTask(sc, toDoList);
                    break;
                }
                case 2: {
                    removeTask(sc, toDoList);
                }
                case 3: {
                    System.out.println("_____________________________________");
                    System.out.print("編集したいタスクidを入力してください: ");
                    int taskId = sc.nextInt();
                    sc.nextLine();

                    Task task = toDoList.getTaskByIdFromDB(taskId);
                    printTask(task);
                    System.out.print("編集するタスクはこれですか？ y: ");
                    String ans = sc.nextLine();
                    if (ans.equals("y")) {
                        if (task != null) {
                            // 編集処理
                            System.out.println("項目を選んでください");
                            System.out.println("1: タイトルの変更");
                            System.out.println("2: 期日");
                            System.out.println("3: 状態の変更");
                            System.out.print("選択してください: ");
                            int select = sc.nextInt();
                            sc.nextLine();

                            switch (select) {
                                case 1: {
                                    // タイトルの編集処理
                                    toDoList.editTitleToDB(task);
                                    break;
                                }
                                case 2: {
                                    // 期日の変更処理
                                    break;
                                }
                                case 3: {
                                    // 状態の変更処理
                                    break;
                                }
                            }
                        } else {
                            System.out.println("指定されたIDのタスクが見つかりません");
                        }
                    }
                    break;
                }
                case 4: {
                    System.out.println("----- Task List -----");
                    List<Task> tasks = toDoList.getTasksFromDB();
                    if (tasks.isEmpty()) {
                        System.out.println("タスクはありません");
                    } else {
                        for (Task task : tasks) {
                            printTask(task);
                        }
                    }
                    break;
                }
                case 5: {
                    searchTaskById(sc, toDoList);
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
            }
            System.out.println();
        }
        sc.close();
    }

    private static void displayMenu() {
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
        if (ans.equals("y")) {
            isRemove = true;
        }
        sc.close();
        return isRemove;
    }

    private static void addTask(Scanner sc, ToDoList toDoList) {
        sc.nextLine();
        System.out.print("タイトルを入力してください: ");
        String title = sc.nextLine();
        System.out.print("期日を入力してください (YYYY-MM-DD)");
        String strDeadline = sc.nextLine();
        LocalDate deadline = LocalDate.parse(strDeadline);

        if (printNewTask(title, deadline)) {
            Task newTask = new Task(title, deadline);
            toDoList.addTask(newTask);
            System.out.println("追加に成功しました");
        } else {
            System.out.println("メニューに戻ります");
        }
    }

    private static void removeTask(Scanner sc, ToDoList toDoList) {
        System.out.println("削除したいタスクidを入力してください: ");
        int removeIndex = sc.nextInt();
        sc.nextLine();
        if (removeIndex >= 1) {
            Task taskToRemove = toDoList.getTaskByIdFromDB(removeIndex);
            printTask(taskToRemove);
            if (confirmRemove()) {
                toDoList.removeTask(taskToRemove);
            }
        } else {
            System.out.println("タスクidが無効です");
        }
    }

    private static void searchTaskById(Scanner sc, ToDoList toDoList) {
        System.out.println("タスクを検索します");
        System.out.print("表示したいタスクのIDを入力してください: ");
        int taskId = sc.nextInt();
        sc.nextLine();
        Task task = toDoList.getTaskByIdFromDB(taskId);
        if (task != null) {
            printTask(task);
            System.out.println("選択してください");
            System.out.println("1: このタスクを削除する");
            System.out.println("2: このタスクを編集する");
            System.out.println("3: メニューに戻る");
            int select = sc.nextInt();
            sc.nextLine();
            
            switch (select){
                case 1:{
                    nextRemove(task);
                    break;
                }
                case 2:{
                    
                }
            }
        } else {
            System.out.println("指定されたIDのタスクが見つかりませんでした");
        }
    }
}
