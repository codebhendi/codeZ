
import java.sql.*;
import java.sql.DriverManager;
import java.util.*;

class JDBCTest {
    
    String url = "jdbc:mysql://localhost/shubham_se";
    
    String user = "root";
    
    String password = "1234";
    
    public static void main(String args[]) {
        JDBCTest t = new JDBCTest();
        Statement stmt = null;
        
        
        
        try {
            
            Connection con = DriverManager.getConnection(t.url, t.user, t.password);
            
            System.out.println("Success");
            System.out.println("Enter Student name");
            Scanner s = new Scanner(System.in);
            
            String str = s.nextLine();
            
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
            
            e.printStackTrace();
            
        }
        
    }
}