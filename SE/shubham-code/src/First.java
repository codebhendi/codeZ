import java.io.*;

public class First {
    public static void main(String args[]) throws IOException {

        // Specify the files of input and output
        File input_file = new File("input.txt");
        File output_file = new File("output.txt");

        // For reading and writing to the files
        BufferedReader br = new BufferedReader(new FileReader(input_file));
        BufferedWriter bw = new BufferedWriter(new FileWriter(output_file));

        // We will take input from the "input.txt"
        // in form of lines and then split them
        // This input is the line which is taken
        // from the file
        String input = null;

        try {

            // The loop condition is untill and unless
            // the input is not null, keep on running
            // the loop
            while ((input = br.readLine()) != null) {

                // Split the input line into an
                // array of strings
                String[] parts = input.split(" ");

                // Now, we go through each of the
                // strings in "parts" array.
                // Then exchange the last and
                // second last character of each
                // element in the array of strings
                // Before that, we check that if
                // the last element was a period,
                // comma or any other such symbol
                for (int i = 0; i < parts.length; i++) {
                    // Length of the ith string
                    // in the array of strings
                    int len = parts[i].length();

                    // Store the last character
                    // of the picked string
                    char last = parts[i].charAt(len - 1);

                    // If the last character is a symbol then
                    // change the length of this string
                    if (last == '.' || last == '!' || last == '?'
                            || last == ',' || last == ';' || len == ':') {
                        len = len - 1;
                        // Reassign the last character
                        last = parts[i].charAt(len - 1);
                    }

                    // Pick the second last character
                    char second_last = parts[i].charAt(len - 2);

                    // Strings are immutable in Java
                    // To change a character in java
                    // at any position, one method that
                    // can be followed is converting the
                    // string into a character array
                    // , change at whatever position
                    // which you want to then reconvert
                    // the character array to strings.
                    char[] part = parts[i].toCharArray();

                    part[len - 1] = second_last;
                    part[len - 2] = last;

                    parts[i] = String.valueOf(part);

                    // System.out.println(parts[i]);

                    // Write to the file
                    // This won't work untill and unless
                    // you make sure that the buffer writer is
                    // flushed and closed after the work on file
                    // has been completed
                    bw.write(parts[i] + " ");
                }

            }
        } finally {
                br.close();
                bw.flush();
                bw.close();
        }
    }
}
