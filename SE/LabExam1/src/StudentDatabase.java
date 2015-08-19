//To enter and read data from database of students
import java.io.*;
import java.sql.*;
import java.util.Properties;
import java.util.Scanner;
//import java.util.*;

class StudentDatabase {

    String url;
    String user;
    String password;
    InputStream inputStream;

    StudentDatabase() throws IOException {
        //Properties file used to connect to database
        Properties prop = new Properties();
        String propFileName = "config.properties";
        BufferedReader f = new BufferedReader(new FileReader("student.properties"));

        if (f != null) {
            prop.load(f);
            user = prop.getProperty("username");
            url = prop.getProperty("db_url");
            password = prop.getProperty("password");
            //System.out.print(url);
        } else {
            throw new FileNotFoundException("property file '" + propFileName + "' not found in the classpath");
        }
    }

    public static void main(String args[]) throws IOException {
        StudentDatabase t = new StudentDatabase();
        Statement stmt = null;


        try {
            //connection is established to connect to database
            Connection con = DriverManager.getConnection(t.url, t.user, t.password);
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
            Scanner s = new Scanner(System.in);
            System.out.println("Success");
            stmt = con.createStatement();

            System.out.println("Welcome to Student Database");
            System.out.println("Please enter your choice of action : ");
            System.out.println("1. View student information\n2.View all student information\n3.Enter new student entry.\n0.To exit");

            while (true) {
                int c = br.read();
                if (c == '1') {
                    String sql = "select student_basic.student_name, student_basic.roll_no,"
                            + " student_basic.address, student_course.course, student_marks.total_marks"
                            + " from student_basic inner join student_course inner join student_marks on "
                            + "student_basic.roll_no = student_course.roll_no && student_basic.roll_no = student_marks.roll_no;";

                    ResultSet rs = stmt.executeQuery(sql);
                    while (rs.next()) {
                        //Retrieve by column name
                        int enroll = rs.getInt("roll_no");
                        String name = rs.getString("student_name");
                        String add = rs.getString("address");
                        String course = rs.getString("course");
                        int marks = rs.getInt("total_marks");

                        System.out.print("Enroll_no : " + enroll + " ");
                        System.out.print("Name :  " + name + " ");
                        System.out.print("Add :  " + add + " ");
                        System.out.print("Course :  " + course + " ");
                        System.out.print(", marks :" + marks + "\n");
                    }
                    rs.close();
                } else if (c == '2') {
                    System.out.println("Enter name of student :");
                    String str = s.nextLine();

                    String sql = "select student_basic.student_name, student_basic.roll_no,"
                            + " student_basic.address, student_course.course, student_marks.total_marks"
                            + " from student_basic inner join student_course inner join student_marks on "
                            + "student_basic.roll_no = student_course.roll_no && student_basic.roll_no = student_marks.roll_no;";
                    ResultSet rs = stmt.executeQuery(sql);
                    while (rs.next()) {
                        //Retrieve by column name
                        int enroll = rs.getInt("roll_no");
                        String name = rs.getString("student_name");
                        String add = rs.getString("address");
                        String course = rs.getString("course");
                        int marks = rs.getInt("total_marks");

                        if (name.equals(str)) {
                            System.out.print("Enroll_no : " + enroll + " ");
                            System.out.print("Name :  " + name + " ");
                            System.out.print("Add :  " + add + " ");
                            System.out.print("Course :  " + course + " ");
                            System.out.print(", marks :" + marks + "\n");
                        }
                    }
                    rs.close();

                } else if (c == '3') {
                    System.out.print("Enter name of student : ");
                    String name = s.nextLine();
                    System.out.print("Enter address : ");
                    String add = s.nextLine();
                    System.out.print("Enter course : ");
                    String course = s.nextLine();
                    System.out.print("Enter marks : ");
                    int t_m = s.nextInt();

                    String test = "select student_course.course from student_course "
                            + "inner join student_basic on "
                            + "student_basic.student_name like '" + name +"' and student_basic.roll_no = student_course.roll_no;";
                    ResultSet rs = stmt.executeQuery(test);
                    if (rs.next()) {
                        System.out.println("Already has a course");
                        continue;
                    }
                    String sql = "insert into student_basic(student_name, address) values('"+name+"', '"+add+"');";
                    stmt.executeUpdate(sql);

                    sql = "insert into student_course(course) values('"+course+"');";
                    stmt.executeUpdate(sql);

                    sql = "insert into student_marks(total_marks) values('"+t_m+"');";
                    stmt.executeUpdate(sql);
                    System.out.println("Data entered");
                } else if (c == '0') {
                    System.out.println("Have a good day!");
                    break;
                }
            }
            con.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}