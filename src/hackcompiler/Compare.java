package hackcompiler;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class Compare {
    // These are cool ANSI color codes that work in Unix-based terminals.
    // Using these make all following text in the terminal use that color/background.
    // See: https://en.wikipedia.org/wiki/ANSI_escape_code#Colors
    public static final String ANSI_BLACK = "\u001B[30m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_YELLOW = "\u001B[33m";
    public static final String ANSI_BLUE = "\u001B[34m";
    public static final String ANSI_PURPLE = "\u001B[35m";
    public static final String ANSI_CYAN = "\u001B[36m";
    public static final String ANSI_WHITE = "\u001B[37m";
    public static final String ANSI_BLACK_BACKGROUND = "\u001B[40m";
    public static final String ANSI_RED_BACKGROUND = "\u001B[41m";
    public static final String ANSI_GREEN_BACKGROUND = "\u001B[42m";
    public static final String ANSI_YELLOW_BACKGROUND = "\u001B[43m";
    public static final String ANSI_BLUE_BACKGROUND = "\u001B[44m";
    public static final String ANSI_PURPLE_BACKGROUND = "\u001B[45m";
    public static final String ANSI_CYAN_BACKGROUND = "\u001B[46m";
    public static final String ANSI_WHITE_BACKGROUND = "\u001B[47m";
    // Remember to RESET the color/background after you're done!
    public static final String ANSI_RESET = "\u001B[0m";

    /**
     * 
     * @param fileName the base stem of the file name/path, without the extension.
     * @throws IOException errors may occur while dealing with file system.
     */
    public static void compareFile(String fileName) throws IOException {
        File translatedFile = new File(fileName+".hack");
        File compareFile = new File(fileName+"Compare.hack");
        Scanner myTranslation = new Scanner(translatedFile);
        Scanner compare = new Scanner(compareFile);

        if (!compareFile.exists() || !compareFile.canRead() || !compare.hasNext()) {
            myTranslation.close();
            compare.close();
            return; // The compare file does not exist or is empty, meaning there is nothing to check.
        }
        System.out.printf("%s%sCompare.hack compare file exists! Comparing...%s\n", ANSI_YELLOW, fileName, ANSI_RESET);
        
        boolean passed = true;
        int lineN = 1;
        while (myTranslation.hasNext() || compare.hasNext()) {
            String lineTL = (myTranslation.hasNext()) ? myTranslation.nextLine() : "empty";
            String compareTL = (compare.hasNext()) ? compare.nextLine() : "empty";

            if ( !lineTL.equals(compareTL) ) {
                System.out.printf("Line %s- Wrong:  \t%s%s%s\n", lineN, ANSI_RED, lineTL, ANSI_RESET);
                passed = false;
            }
                System.out.printf("Line %s- Correct:\t%s%s%s\n", lineN, ANSI_GREEN, compareTL, ANSI_RESET);
            lineN++;
        }
        String conclusion = (passed) ? "Output and Compare files are identical!" : "Output and Compare files differ.";
        System.out.println(conclusion+"\n");
        
        myTranslation.close();
        compare.close();
    }
}
