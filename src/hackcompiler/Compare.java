package hackcompiler;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

/**
 * A class containing helpful constants and one method in order to check yourFile.hack
 * against yourFileCompare.hack and print out a pretty table.
 */
public class Compare {
    // These are cool ANSI color codes that work in Unix-based terminals.
    // Using these make all following text in the terminal use that color/background.
    // Using these constants, only one modifier can be applied at once. But, it is possible
    // to multiple. See: https://en.wikipedia.org/wiki/ANSI_escape_code#Colors
    private static final String ANSI_BLACK = "\u001B[30m";
    private static final String ANSI_RED = "\u001B[31m";
    private static final String ANSI_GREEN = "\u001B[32m";
    private static final String ANSI_YELLOW = "\u001B[33m";
    private static final String ANSI_BLUE = "\u001B[34m";
    private static final String ANSI_PURPLE = "\u001B[35m";
    private static final String ANSI_CYAN = "\u001B[36m";
    private static final String ANSI_WHITE = "\u001B[37m";
    private static final String ANSI_BLACK_BACKGROUND = "\u001B[40m";
    private static final String ANSI_RED_BACKGROUND = "\u001B[41m";
    private static final String ANSI_GREEN_BACKGROUND = "\u001B[42m";
    private static final String ANSI_YELLOW_BACKGROUND = "\u001B[43m";
    private static final String ANSI_BLUE_BACKGROUND = "\u001B[44m";
    private static final String ANSI_PURPLE_BACKGROUND = "\u001B[45m";
    private static final String ANSI_CYAN_BACKGROUND = "\u001B[46m";
    private static final String ANSI_WHITE_BACKGROUND = "\u001B[47m";
    // Remember to RESET the color/background after you're done!
    private static final String ANSI_RESET = "\u001B[0m";

    /**
     * Given a {@code fileName}, searches for a {@code fileNameCompare} file, and if it exists
     * prints a comparison table to the console.
     * @param fileName the base stem of the file name/path, without the extension.
     * @throws IOException errors may occur while dealing with file system.
     */
    public Compare(String fileName) throws IOException {
        File translatedFile = new File(fileName+".hack");
        File compareFile = new File(fileName+"Compare.hack");
        Scanner translation = new Scanner(translatedFile);
        Scanner compare = new Scanner(compareFile);

        if (!compareFile.exists() || !compareFile.canRead() || !compare.hasNext()) {
            translation.close();
            compare.close();
            return; // The compare file does not exist or is empty, meaning there is nothing to check.
        }
        System.out.printf("%s%sCompare.hack compare file exists! Comparing...%s\n", ANSI_YELLOW, fileName, ANSI_RESET);
        
        // Iterate over the compare stuff
        Parser original = new Parser(fileName+".asm");

        boolean passed = true;
        int lineN = 1;
        System.out.printf("| %4s | %16s | %10s |\n","Line","Binary check","Symbolic");
        System.out.printf("| ---- | ---------------- | ---------- |\n");
        
        while (translation.hasNext() || compare.hasNext()) {
            String yourTL = (translation.hasNext()) ? translation.nextLine() : "empty";
            String compareTL = (compare.hasNext()) ? compare.nextLine() : "empty";
            
            while (original.instructionType()==Parser.Instruction.L_INSTRUCTION || original.instructionType()==Parser.Instruction.INVALID_INSTRUCTION)
                original.advance();

            System.out.printf("| %4s | %s%16s%s | %10s |\n", lineN, ANSI_GREEN, compareTL, ANSI_RESET, original.getCurrentInstruction());
            if ( !yourTL.equals(compareTL) ) {
                System.out.printf("%s| %4s | %s%16s%s | %10s |%s\n", "\033[41m", "", "\033[33;41m", yourTL, "\033[31m", "", ANSI_RESET);
                passed = false;//\u001b[31;31m
            }
            lineN++;
            original.advance();
        }
        String conclusion = (passed) ? "Output and Compare files are identical!" : ANSI_RED_BACKGROUND+"Output and Compare files differ.";
        System.out.println(conclusion+ANSI_RESET);
        
        translation.close();
        compare.close();
    }
}
