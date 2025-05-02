package hackcompiler;

import java.io.*;

/**
 * Assembler will serve as the central class that will call all other methods as needed.
 * It will house the instances of the other classes.
 */
public class Assembler {
    // Note to my future CS interns: If you care, see the Cornell style guide below.
    // https://www.cs.cornell.edu/courses/cs2110/2018fa/style_guidelines.html#Naming
    // Additionally please use a real(-ish) IDE like VSCode. Do not use the CodeHS.com
    // web editor or any other strange and incomprehensible developer decisions.

    /**
     * Composes helper class methods to translate input.
     * @param args input filepath
     */
    public static void main(String[] args) {
        // Create instances of the helper classes we're relying on (except Parser)
        Code code = new Code();
        SymbolTable symbolTable = new SymbolTable();

        // We make sure that a file has been specified or we give an error and exit.
        if (args.length == 0) {
            System.out.println("Error: Filepath required");
            return;
        }

        // Define the filename stem by keeping only everything up to the file extension.
        String fileName = args[0].substring(0,args[0].lastIndexOf("."));

        // First pass / Symbolic pass / Label resolution
        try {
            Parser parser = new Parser(fileName+".asm");
            int line = 0;

            while (parser.hasMoreLines()) {
                Parser.Instruction type = parser.instructionType();

                if (type == Parser.Instruction.L_INSTRUCTION) { // instruction = (LABEL)
                    String symbol = parser.symbol();

                    if (!symbolTable.contains(symbol)) {
                        symbolTable.addEntry(symbol, line); 
                    }
                } else if (type==Parser.Instruction.A_INSTRUCTION||type==Parser.Instruction.C_INSTRUCTION) {
                    line++; // Compiled lines are only C or A instructions, and do not include Labels.
                }
                parser.advance();
            }
        } catch (IOException e) {
            System.out.println("Error " + e.getMessage());
        }
        
        // Second pass
        try {
            // Create a new Parser object sending in the input filename. Use a try block
            // as there is the potential for a IOException when dealing with the file system.
            Parser parser = new Parser(fileName+".asm");
            parser.setValidCompInstructions(code.getCompKeys());

            // Create a new file for output.
            PrintStream fileOut = new PrintStream(new File(fileName+".hack"));

            // The address to assign each encountered variable, starting from 16.
            int newVarAddress = 16;
            // Iterate over every line in the parser.
            while (parser.hasMoreLines()) {
                String translation = "";
                
                if (parser.instructionType() == Parser.Instruction.A_INSTRUCTION) {
                    // Process an A_Command and write to output file
                    String symbol = parser.symbol();
                    int num = -1;
                    try {
                        num = Integer.parseInt(symbol);
                    } catch (NumberFormatException e) {
                        if (symbolTable.contains(symbol)) {
                            num = symbolTable.getAddress(symbol);
                            System.out.println(symbol+" number"+num);
                        } else {
                            num = newVarAddress;
                            symbolTable.addEntry(symbol, newVarAddress);
                            newVarAddress++;
                            System.out.println(symbol+" symbol"+num);
                        }
                    }
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
            new Compare(fileName);
        } catch (IOException e) {} // Ignore errors since we dont care abt this one.
    }

}