/* it will take input a c file and giv ethe frequency of  
 all the keywords present in that file*/
import java.util.*;
import java.io.*;

public class Readkeywordsfromc {

    public static void main(String[] args) {

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

        try {
            //the files from which we are going to take our input and give output

            BufferedWriter out = new BufferedWriter(new FileWriter("output.txt"));
            BufferedReader in = new BufferedReader(new FileReader("input.c"));

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
                String[] strs = c.split(" |\\n|;|\\{|\\}|\\(|\\)|,|\\?|\\||!|\\&|%");
                if (strs.length > 0) {
                    if (flag == 0) {
                        if (strs[strs.length - 1].contains("*/")) {
                            flag = 1;
                            continue;
                        }
                    }
                    if (strs[0].contains("/*")) {
                        if (strs[strs.length - 1].contains("*/")) {
                            continue;
                        }
                        flag = 0;
                        continue;
                    }
                    if (strs[0].contains("//")) {
                        continue;
                    }
                    for (String str : strs) {
                        if (keywords.containsKey(str)) {
                            keywords.put(str, keywords.get(str) + 1);
                        }
                    }
                }
            }
            for (String str2: keywords.keySet()) {
                    out.write(str2 + " " + keywords.get(str2) + "\n");
            }

            out.close();
            in.close();
        } catch (IOException e) {
            System.out.println("exception");
        }

        keywords.clear();
        return;
    }

}