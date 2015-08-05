import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

public class WritePropertiesFile {
    
    public static void main(String[] args) {
        
        try {
            Properties properties = new Properties();
            properties.setProperty("db_url", "jdbc:mysql://localhost/shubham_se");
            properties.setProperty("username", "root");
            properties.setProperty("password", "1234");

            File file = new File("test2.properties");
            FileOutputStream fileOut = new FileOutputStream(file);
            properties.store(fileOut, "Database");
            fileOut.close();
            
        } catch (FileNotFoundException e) {
            e.printStackTrace(); 
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}