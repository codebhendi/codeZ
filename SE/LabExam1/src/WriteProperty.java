
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Properties;

public class WriteProperty {
    
    public static void main(String[] args) {
        
        try {
            Properties properties = new Properties();
            properties.setProperty("db_url", "jdbc:mysql://localhost/shubham_se");
            properties.setProperty("username", "root");
            properties.setProperty("password", "1234");

            FileWriter file = new FileWriter("student.properties");
            BufferedWriter fileOut = new BufferedWriter(file);
            properties.store(fileOut, "Database");
            fileOut.close();
            
        }  catch (IOException e) {
        
        }

    }
}
