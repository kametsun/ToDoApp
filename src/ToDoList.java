import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;

public class ToDoList {
    private List<Task> tasks;

    public ToDoList() {
        tasks = new ArrayList<>();
    }

    public void addTask(Task task) {
        tasks.add(task);
        addTaskToDB(task);
    }

    public void removeTask(int id) {
        removeTaskToDB(id);
    }

    public void editTask(Task task, String newTitle, LocalDate newDeadline) {
        task.setTitle(newTitle);
        task.setDeadline(newDeadline);
        task.setStatus("Incomplete");
    }

    public List<Task> getTasks() {
        return tasks;
    }

    public void displayTasks() {
        if (tasks.isEmpty()) {
            System.out.println("タスクはありません");
        } else {
            for (Task task : tasks) {
                int cnt = 0;
                System.out.println("タスク" + (cnt + 1) + ":" + task.getTitle());
                cnt++;
            }
        }
    }

    // DB接続情報
    private static final String DB_URL = "jdbc:mysql://localhost/todolist";
    private static final String DB_USERNAME = "root";
    private static final String DB_PASSWORD = "root";

    // DB接続
    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
    }

    // タスクをDBに追加
    public void addTaskToDB(Task task) {
        try (Connection connection = getConnection()) {
            String sql = "INSERT INTO tasks (title, deadline, status) VALUES (?, ?, ?)";
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setString(1, task.getTitle());
                statement.setDate(2, java.sql.Date.valueOf(task.getDeadline()));
                statement.setString(3, task.getStatus());
                statement.executeUpdate();
            }
            System.out.println("タスクをデータベースに追加しました");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // DBからタスクを取得
    public List<Task> getTasksFromDB() {
        List<Task> tasks = new ArrayList<>();
        try (Connection connection = getConnection()) {
            String sql = "SELECT * FROM tasks";
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                try (ResultSet resultSet = statement.executeQuery()) {
                    while (resultSet.next()) {
                        int id = resultSet.getInt("id");
                        String title = resultSet.getString("title");
                        LocalDate deadline = resultSet.getDate("deadline").toLocalDate();
                        String status = resultSet.getString("status");
                        Task task = new Task(id, title, deadline, status);
                        tasks.add(task);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return tasks;
    }

    public void removeTaskToDB(int id) {
        try(Connection connection = getConnection()){
            Task taskToRemove = getTaskByIdFromDB(id);
            if(taskToRemove != null){
                String query = "DELETE FROM tasks WHERE id = ?";
                PreparedStatement statement = connection.prepareStatement(query);
                statement.setInt(1, taskToRemove.getId());
                statement.executeUpdate();
                System.out.println("削除に成功しました");
            }else{
                System.out.println("指定されたIDのタスクが見つかりません");
            }
        }catch(SQLException e){
            e.printStackTrace();
            System.out.println("削除に失敗しました");
        }
    }

    public Task getTaskByIdFromDB(int id) {
        try (Connection connection = getConnection()) {
            String query = "SELECT * FROM tasks WHERE id = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                int taskId = resultSet.getInt("id");
                String taskName = resultSet.getString("title");
                LocalDate deadline = resultSet.getDate("deadline").toLocalDate();
                String status = resultSet.getString("status");
                Task task = new Task(taskId, taskName, deadline, status);
                return task;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("タスクの抽出に成功しました");
        }
        return null;
    }
}
