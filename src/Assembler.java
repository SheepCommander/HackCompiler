import java.io.*;

/**
 * {@link Assembler}: Assembler will serve as the central class that will call
 * all other methods as needed. It will house the instances of the other classes.
 */
public class Assembler {
    // Static variables section

    public static void main(String[] args) {
        // Create instances of the other helper classes except Parser.
        Code code = new Code();

        // We make sure that a file has been specified or we give an error and exit.
        if (args.length == 0) {
            System.out.println("Error: Filepath required");
            return;
        }

        // Define the filename using the first argument passed from the terminal.
        String fileName = args[0];

        try {
            // Create a new Parser object sending in the input filename. This must be
            // in a try block as there is the potential for a IOException when dealing
            // with the file system.
            Parser parser = new Parser(fileName);

            // TODO: Create a new file for output. File needs to be the same name as
            // the input file except with the extension of .hack
            String outputName = fileName.substring(0,fileName.lastIndexOf("."))+".hack";
            PrintStream fileOut = new PrintStream(new File(outputName));

            // Iterate over every line in the parser.
            while (parser.hasMoreLines()) {
                String translation = "";
                if (parser.instructionType() == Parser.Instruction.A_INSTRUCTION) {
                    // Process an A_Command and write to output file
                    int num = Integer.valueOf(parser.symbol());
                    translation = String.format("%16s", Integer.toBinaryString(num)).replace(' ','0');
                }
                if (parser.instructionType() == Parser.Instruction.C_INSTRUCTION) {
                    // Process the C_Command by using a Coder object to translate into binary
                    // and write to the output file
                    translation += code.dest(parser.dest());
                    translation += code.comp(parser.comp());
                    translation += code.jump(parser.jump());
                }
                if (!translation.isBlank())
                    fileOut.println(translation);

                // Advance to the next available command
                parser.advance();
            }
        } catch (IOException e) {
            System.out.println("Error " + e.getMessage());
        }
    }
}