
import java.io.*;
import java.sql.*;
import java.util.Properties;
//import java.util.*;

class JDBCTest {
    String url;
    String user;
    String password;
    InputStream inputStream;
    
    JDBCTest() throws IOException {
        Properties prop = new Properties();
        String propFileName = "config.properties";
        BufferedReader f = new BufferedReader(new FileReader("config.properties"));
        
        if (f != null) {
                prop.load(f);
                user  = prop.getProperty("username");
                url = prop.getProperty("db_url");
                password = prop.getProperty("password");
                System.out.print(url);
        } else {
                throw new FileNotFoundException("property file '" + propFileName + "' not found in the classpath");
        }
    }
    
    
    public static void main(String args[]) throws IOException {
        JDBCTest t = new JDBCTest();
        Statement stmt = null;
        
        
        try {
            
            Connection con = DriverManager.getConnection(t.url, t.user, t.password);
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
            System.out.println("Success");
            System.out.println("Enter Student name");
            //Scanner s = new Scanner(System.in);
            
            String str = br.readLine();
            
            stmt = con.createStatement();
            
            String sql = "SELECT  STUDENTSNAME.enroll_no, STUDENTSNAME.name,  STUDENTS.marks"
            + " FROM STUDENTS " +
            "INNER JOIN STUDENTSNAME " +
            "ON STUDENTS.enroll_no = STUDENTSNAME.enroll_no" +
            " order by STUDENTS.enroll_no";
                
            ResultSet rs = stmt.executeQuery(sql);
            
            while(rs.next()){
                //Retrieve by column name
                int enroll = rs.getInt("enroll_no");
                String name  = rs.getString("name");
                int age = rs.getInt("marks");
                
                if (name.equals(str)) {
                    System.out.print("Enroll_no : " + enroll + " ");
                    System.out.print("Name :  " + name + " ");
                    System.out.print(", marks :" + age + "\n");
                }
            }
            
            rs.close();
            
            con.close();
            
        } catch (Exception e) {
        }
        
    }
}