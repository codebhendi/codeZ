/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tokenizer;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;

/**
 *
 * @author placement
 */
public class Tokenized {
     public static void main(String[] args) throws FileNotFoundException, IOException {
        // TODO code application logic here
        File f = new File("helloworld.c");
        String[] keywords = {"printf", "int", "float", "double", "break", "switch", "char", "string","continue","for","do","while"};
        String[] delimeters = {"(",")",";","."};
        String[] punc = {"*","/","+","-","=","^","!"};
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
                        for(int i=0;i<keywords.length;i++) {
                            if(line.contains(keywords[i])) {
                                hm.put(keywords[i], "keyword");
                            }
                        }
                        for(int i=0;i<delimeters.length;i++) {
                            if(line.contains(delimeters[i])) {
                                hm.put(delimeters[i], "delimeter");
                            }
                        }
                        for(int i=0;i<punc.length;i++) {
                            if(line.contains(punc[i])) {
                                hm.put(punc[i], "punc");
                            }
                        }
                        int start = -1;
                        int end = -1;
                       for(int i=0;i<line.length();i++) {
                           start = i;
                           while(line.charAt(i)=='0'||line.charAt(i)=='1'||line.charAt(i)=='2'||line.charAt(i)=='3'||line.charAt(i)=='4'||line.charAt(i)=='5'||line.charAt(i)=='6'||line.charAt(i)=='7'||line.charAt(i)=='8'||line.charAt(i)=='9') {
                               i++;
                           }
                           end = i;
                           if(start != end ) hm.put(line.substring(start, end),"int");
                       
                       }
                       
                       start = -1;
                       end = -1;
                       for(int i=0;i<line.length();i++) {
                           start = i;
                           while(line.charAt(i)>='a'&&line.charAt(i)<='z') {
                               i++;
                           }
                           end = i;
                           if(start != end && !hm.containsKey(line.substring(start, end))) {
                               hm.put(line.substring(start, end),"string");
                           }
                       
                       }
                        
                    }
                    
            }
            System.out.println(hm);
            
    }
}
