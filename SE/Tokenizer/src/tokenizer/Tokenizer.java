/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tokenizer;

import java.io.File;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.StringTokenizer;

/**
 *
 * @author placement
 */
public class Tokenizer {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws FileNotFoundException, IOException {
        // TODO code application logic here
        File f = new File("helloworld.c");
        String line;
        HashMap<String, String> hm = new HashMap<String, String>();
        int flag = 0;
     removecomments rc = new removecomments();
            rc.remove(f);
            BufferedReader br = new BufferedReader(new FileReader("abc.txt"));
            
            while ((line = br.readLine()) != null) {
                    if(line.contains("int main()")) {
                            flag = 1;
                            continue;
                    }
                    if(flag == 0 || line.contentEquals("{") || line.contentEquals("}")) continue;
                    else {
                        System.out.println(line);
                        int d = line.indexOf("(");
                        int d1 = line.indexOf(")");
                        System.out.println(d);
                        hm.put(line.substring(1, d-1), "keyword");
                        hm.put("(", "delimeter");
                        hm.put(line.substring(d+1, d1), "string");
                        hm.put(")", "delimeter");
                    }
                    
            }
            System.out.println(hm);
            
    }
}
