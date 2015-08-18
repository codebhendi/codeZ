import java.io.*;

public class Second {
    public static void main(String[] args) {
        try{
            OutputStream os1 = new FileOutputStream("output1.txt");
            OutputStream os2 = new FileOutputStream("output2.txt");
            OutputStream os3 = new FileOutputStream("output3.txt");
            
            InputStream f = new FileInputStream("input.txt");
            
            int size = f.available();
            String s = new String();
            String s1[] = new String[200];
            String s3[] = new String[200];
            String s2[] = new String[200];
            
            int count = 0;
            int count1 = 0;
            int count2 = 0;
            int count3 = 0;
            int flag = 0;
            
            for (int i = 0; i < size; i++) {
                int c = f.read();
                if (c >= 48 && c <= 57)  {
                    flag = 1;
                    s = s + (char)c;
                } else if ((c >= 65 && c <= 90) || (c >= 97 && c <= 122)) {
                    flag = 2;
                    s = s + (char)c;
                } else if (c == ' ') {
                    if (flag == 1) {
                        flag = 0;
                        s1[count1++] = s;
                        s = "";
                        /*for (int j = 0; j < count; j++) {
                            os1.write(s1[count]);
                        }*/
                        count = 0;
                    } else if (flag == 2) {
                        flag = 0;
                        s2[count2++] = s;
                        /*for (int j = 0; j < count; j++) {
                            os2.write(s2[count]);
                        }*/
                        count = 0;
                    } else if (flag == 0) {
                        s3[count3++] = s;
                        /*for (int j = 0; j < count; j++) {
                            os3.write(s3[count]);
                        }*/
                        count = 0;
                    }
                } else {
                    flag = 0;
                    s = s + (char)c;
                }
            }
            
            int arr[] = new int[count1];
            
            for (int i = 0; i < count1; i++) {
                arr[i] = Integer.parseInt(s1[i]);
            }
            
            for (int i = 0; i < count1;  i++) {
                for (int j = 1; j < count - i; j++) {
                    if (arr[j - 1] > arr[j]) {
                        int temp = arr[j - 1];
                        arr[j - 1] = arr[j];
                        arr[j] = temp;
                    }
                }
            }
            
            for (int i = 0; i < count1; i++) {
                System.out.print(arr[i] + "    " );
                os1.write(arr[i]);
                os1.write(' ');
            }
           // os1.close();
            //os2.close();
            //os3.close();
        }catch(IOException e){
            System.out.print("Exception");
        }	 
    }
}
