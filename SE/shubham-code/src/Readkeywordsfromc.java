/* it will take input a c file and giv ethe frequency of  
 all the keywords present in that file*/
import java.util.*;
import java.io.*;

public class Readkeywordsfromc {

    public static void main(String[] args) throws IOException {

        //we will map every keyword in c language in a hashmao
        //we will initialiaze this map to 0 for every keyword
        //as the initial count for every keyword is 0

        Map < String, Integer > keywords = new HashMap < String, Integer > ();

        String keywd[] = {
            "auto", "break", "case", "char", "const",
        "continue", "default", "do", "double", "else", "enum", "extern",
        "float", "for", "goto", "if", "int", "long", "register", "return", "short",
        "signed", "sizeof", "static", "struct", "switch", "typedef", "union",
        "unsigned", "void", "volatile", "while"
        };

        for (String str: keywd) {
                keywords.put(str, 0);
        }
        
        //the files from which we are going to take our input and give output
        BufferedWriter out = new BufferedWriter(new FileWriter("output.txt"));
        BufferedReader in = new BufferedReader(new FileReader("input.c"));

        try {
           //we will read every line in the .c file
            //then divide each string at breakpooints like
            // space,:,{,},(,),?,,,/,!,% and &.
            //We are then checkinh the first word in this string for // or /*.
            //If it is // we are skipping that string.
            //If it is /* we will make flag 0 and wait till 
            //this string or any other string has */.
            
            String c;
            int flag = 1;

            while ((c= in.readLine()) != null) {
                
                //regular expression to remove special characters
                String[] strs = c.split(" |\\n|;|\\{|\\}|\\(|\\)|,|\\?|\\||!|\\&|%");
                if (strs.length > 0) {
                    //to check if we have found */
                    if (flag == 0) {
                        if (strs[strs.length - 1].contains("*/")) {
                            flag = 1;
                            continue;
                        }
                    }
                    //first occurence of /*
                    if (strs[0].contains("/*")) {
                        if (strs[strs.length - 1].contains("*/")) {
                            continue;
                        }
                        flag = 0;
                        continue;
                    }
                    //first occurence of //
                    if (strs[0].contains("//")) {
                        continue;
                    }
                    
                    //calculating the frequencey of the 
                    //number of words found.
                    for (String str : strs) {
                        if (keywords.containsKey(str)) {
                            keywords.put(str, keywords.get(str) + 1);
                        }
                    }
                }
            }
            
            //here is our output
            for (String str2: keywords.keySet()) {
                    out.write(str2 + " " + keywords.get(str2) + "\n");
            }

          
        } catch (IOException e) {
            System.out.println("exception");
        } finally {
            //this whole thing won't work without any bugs
            //unless we close the files we have opened
            out.close();
            in.close();
        }

        keywords.clear();
        return;
    }

}