import java.time.LocalDate;

public class Task{
    private String title;
    private LocalDate deadline;
    private String status;
    //DB用
    private int id;

    public Task(String title, LocalDate deadline){
        setTitle(title);
        setDeadline(deadline);
        setStatus("Incomplete");
    }

    public Task(int id, String title, LocalDate deadline, String status){
        setId(id);
        setTitle(title);
        setDeadline(deadline);
        setStatus(status);
    }

    public String getTitle(){
        return title;
    }

    public void setTitle(String title){
        this.title = title;
    }

    public LocalDate getDeadline(){
        return deadline;
    }

    public void setDeadline(LocalDate deadline){
        this.deadline = deadline;
    }

    public String getStatus(){
        return status;
    }

    public void setStatus(String status){
        this.status = status;
    }

    public int getId(){
        return id;
    }

    public void setId(int id){
        this.id = id;
    }

    @Override
    public String toString(){
        return "Titel: " + getTitle() + ", Deadline: " + getDeadline() + ", Status: " + getStatus();
    }

    //DBから渡されたデータをtasksリストに追加して見れるようにする
    public void addTaskFromDB(Task task){
        
    }
}