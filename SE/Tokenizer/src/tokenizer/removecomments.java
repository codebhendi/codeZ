package tokenizer;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class removecomments  {
    public void remove(File filename) throws FileNotFoundException, IOException {
        String line;
        BufferedReader br = new BufferedReader(new FileReader(filename));
        BufferedWriter bw = new BufferedWriter(new FileWriter("abc.txt"));
        BufferedWriter cc = new BufferedWriter(new FileWriter("commnents.txt"));
        int i = 0, flag;
        while ((line = br.readLine()) != null) {
                flag = 0;
                if (line.contains("//")) {
                    i = line.indexOf("//");
                    if (i > 0) {
                        bw.write(line.substring(0, i));
                    }
                    cc.write(line.substring(i + 2, line.length()));
                    cc.newLine();
                } else if (line.contains("/*")) {
                    i = line.indexOf("/*");
                    if (i > 0) {
                        bw.write(line.substring(0, i));
                    }
                    if (!line.contains("*/")) {
                        cc.write(line.substring(line.indexOf("/*") + 2, line.length()));
                        cc.newLine();
                    }
                    while (!line.contains("*/")) {
                        line = br.readLine();
                        if (!line.contains("*/")) {
                            cc.write(line);
                            cc.newLine();
                        }
                        flag = 1;
                    }
                    i = line.indexOf("*/");
                    if (flag == 0) {
                        bw.append(line.substring(i + 2, line.length()));
                        cc.write(line.substring(line.indexOf("/*") + 2, line.indexOf("*/")));
                        cc.newLine();
                    } else {
                        bw.newLine();
                        bw.write(line.substring(i + 2, line.length()));
                        cc.write(line.substring(0, line.indexOf("*/") - 1));
                        cc.newLine();
                    }
                } else {
                    bw.write(line);
                }
                bw.newLine();
            }
            bw.close();
            cc.close();
    }
}