package Explanation;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.*;


public class Explanation {
    public static void explanation(){
        final String FILE_NAME = "src\\Explanation\\Explanation.txt";
        File file = new File(FILE_NAME);
        ArrayList<String> list = new ArrayList<>();
        

        try(BufferedReader br = new BufferedReader(new FileReader(file))){
            String text;
            while((text = br.readLine()) != null){
                list.add(text);
                System.out.println(text);
            }
        }catch(Exception e){
            System.out.println(e);
        }
    }
}
