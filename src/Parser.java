import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * {@link Parser}: The main function of the parser is to break each assembly
 * command into its
 * underlying components (fields and symbols).
 */
public class Parser {
    private String currentInstruction;
    private String[] instructions;
    private int nextInstrIndex = 0;

    /**
     * This enum will be used to identify that type of the current instruction.
     */
    public enum Instruction {
        L_INSTRUCTION, A_INSTRUCTION, C_INSTRUCTION, INVALID_INSTRUCTION
    }

    /**
     * Opens the input file/stream and gets ready to parse it.
     * 
     * @param fileName The file name/path to open
     * @throws IOException An error, such as file doesn't exist, may prevent it from
     *                     working.
     */
    public Parser(String fileName) throws IOException {
        // Parser will be an object whose behavior is used by the Compiler
        File file = new File(fileName);
        Scanner input = new Scanner(file);

        ArrayList<String> lines = new ArrayList<String>();
        do {
            String line = input.nextLine().trim();
            
            if (line.indexOf("//") >= 0)
                line = line.substring(0, line.indexOf("//")).trim();
            
            if (!line.isBlank())
                lines.add(line);
            
        } while (input.hasNextLine());
        instructions = lines.toArray(new String[lines.size()]);
        advance(); // set the currentInstruction

        // TODO: Remove debug
        System.out.println();
        for (String s : instructions)
            System.out.println(s);
    }

    /**
     * Read ahead to check if there's more lines.
     * 
     * @return true if there is.
     */
    public boolean hasMoreLines() {
        return nextInstrIndex < instructions.length - 1;
    }

    /**
     * Reads the next command from the input and makes it the current command.
     * Should be called only if hasMoreCommands() is true. Initially there is no
     * current command.
     */
    public void advance() {
        currentInstruction = instructions[nextInstrIndex];
        nextInstrIndex++;
    }

    /**
     * Using the {@link Instruction} enum,
     * 
     * @return Returns the type of the current command:
     *         - A_COMMAND for @Xxx where Xxx is either a symbol or a decimal number
     *         - C_COMMAND for dest=comp;jump
     *         - L_COMMAND (actually, pseudo- command) for (Xxx) where Xxx is a symbol.
     */
    public Instruction instructionType() {
        if (currentInstruction.startsWith("@")) {
            return Instruction.A_INSTRUCTION;
        }
        if (currentInstruction.startsWith("(")) {
            return Instruction.L_INSTRUCTION;
        }
        
        Code code = new Code();
        for (String comp : code.getCompKeys()) {
            if (currentInstruction.contains(comp)) return Instruction.C_INSTRUCTION;
        }

        return Instruction.INVALID_INSTRUCTION;
    }

    /**
     * @return Returns the symbol or decimal Xxx of the current command @Xxx or (Xxx).
     *         In the first iteration of the program symbol resolution will not be
     *         implemented.
     */
    public String symbol() {
        String symbol = currentInstruction.substring(1); //TODO: Expand this with symbolic
        return symbol;
    }

    /**
     * @return Returns the dest mnemonic in the current C-command (8 possibilities).
     *         Should be called only when {@link #instructionType()} is C_COMMAND.
     */
    public String dest() {
        int indexD = currentInstruction.indexOf("=");
        if (indexD < 0) return null;
        
        return currentInstruction.substring(0, indexD);
    }

    /**
     * @return Returns the comp mnemonic in the current C-command (28 possibilities).
     *         Should be called only when {@link #instructionType()} is C_COMMAND.
     */
    public String comp() {
        int indexD = currentInstruction.indexOf("=");
        int indexJ = currentInstruction.indexOf(";");
        if (indexD < 0) indexD = 0;
        if (indexJ < 0) indexJ = currentInstruction.length();

        return currentInstruction.substring(indexD+1, indexJ);
    }

    /**
     * @return Returns the jump mnemonic in the current C-command (8 possibilities).
     *         Should be called only when {@link #instructionType()} is C_COMMAND.
     */
	public String jump() {
		int indexJ = currentInstruction.indexOf(";");
        if (indexJ < 0) return null;

        return currentInstruction.substring(indexJ);
	}
}
