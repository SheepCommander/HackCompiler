package hackcompiler;
import java.io.*;

/**
 * {@link Assembler}: Assembler will serve as the central class that will call
 * all other methods as needed. It will house the instances of the other classes.
 */
public class Assembler {
    // Note to my future CS interns: If you care, see the Cornell style guide below.
    // https://www.cs.cornell.edu/courses/cs2110/2018fa/style_guidelines.html#Naming
    // Additionally please use a real(-ish) IDE like VSCode. Do not use the CodeHS.com
    // web editor or any other strange and incomprehensible developer decisions.

    public static void main(String[] args) {
        // Create instances of the other helper classes except Parser.
        Code code = new Code();

        // We make sure that a file has been specified or we give an error and exit.
        if (args.length == 0) {
            System.out.println("Error: Filepath required");
            return;
        }

        // Define the filename using the first argument passed from the terminal,
        // but only keeping everything up to the file extension. We will use this stem
        // to create an output file.
        String fileName = args[0].substring(0,args[0].lastIndexOf("."));

        try {
            // Create a new Parser object sending in the input filename. This must be
            // in a try block as there is the potential for a IOException when dealing
            // with the file system.
            Parser parser = new Parser(fileName+".asm");

            // TODO: Create a new file for output.
            PrintStream fileOut = new PrintStream(new File(fileName+".hack"));

            // Iterate over every line in the parser.
            while (parser.hasMoreLines()) {
                String translation = "";
                if (parser.instructionType() == Parser.Instruction.A_INSTRUCTION) {
                    // Process an A_Command and write to output file
                    // TODO: Expand this section to work with symbols. Currently throws an error if any symbols are entered.
                    // Reminder: SP=0, LCL=1, ARG=2, THIS=3, THAT=4, R0-R15=0-15, SCREEN=16384, KBD=24576
                    // Reminder: User-defined variables (@my_var) start from 16 as they're encountered and count up.
                    String symbol = parser.symbol();
                    int num = Integer.parseInt(symbol);
                    translation = String.format("%16s", Integer.toBinaryString(num)).replace(' ','0');
                }
                if (parser.instructionType() == Parser.Instruction.C_INSTRUCTION) {
                    // Process the C_Command by using a Coder object to translate into binary
                    // and write to the output file. Reminder: 111+comp+dest+jump.
                    translation += "111";
                    translation += code.comp(parser.comp());
                    translation += code.dest(parser.dest());
                    translation += code.jump(parser.jump());
                }
                if (!translation.isBlank())
                    fileOut.println(translation);

                // Advance to the next available command
                parser.advance();
            }
            fileOut.close(); // Close the file stream.
        } catch (IOException e) {
            System.out.println("Error " + e.getMessage());
        }

        // EXTRA: Compare the outputted file to a "filenameCompare.hack" file if one exists.
        try {
            Compare.compareFile(fileName);
        } catch (IOException e) {} // Ignore errors since we dont care abt this one.
    }

}