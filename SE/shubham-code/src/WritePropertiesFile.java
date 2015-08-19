import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Properties;

public class WritePropertiesFile {
    
    public static void main(String[] args) {
        
        try {
            Properties properties = new Properties();
            properties.setProperty("db_url", "jdbc:mysql://localhost/shubham_se");
            properties.setProperty("username", "root");
            properties.setProperty("password", "1234");
            System.out.println(properties.get("username"));

            FileWriter file = new FileWriter("config.properties");
            BufferedWriter fileOut = new BufferedWriter(file);
            properties.store(fileOut, "Database");
            fileOut.close();
            
        } catch (FileNotFoundException e) {
            e.printStackTrace(); 
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}