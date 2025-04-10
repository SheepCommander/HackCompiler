package hackcompiler;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * {@link Parser}: The main function of the parser is to break each assembly
 * command into its underlying components (fields and symbols).
 */
public class Parser {
    private String currentInstruction;
    private String[] instructions;
    private int instrIndex = -1;

    /**
     * This enum will be used to identify the type of the current instruction.
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
        input.close(); // close the input stream.
    }

    /** Return true if there's more lines. */
    public boolean hasMoreLines() {
        return instrIndex < instructions.length;
    }

    /**
     * Reads the next command from the input and makes it the current command.
     * Should be called only if hasMoreCommands() is true. Initially there is no
     * current command.
     */
    public void advance() {
        instrIndex++; // Initially -1 before advance() is called
        currentInstruction = (instrIndex < instructions.length) ? instructions[instrIndex] : null;
    }

    /**
     * Using the {@link Instruction} enum, returns the type of the current command:
     * @return 
     *         * A_COMMAND for {@code @Xxx} where Xxx is either a symbol or a decimal number
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
        String symbol = currentInstruction.substring(1);
        if (symbol.contains(")"))
            symbol = symbol.substring(0, symbol.indexOf(")"));
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
        if (indexD < 0) indexD = -1; // Should be -1 since we add +1 in the substring call.
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

        return currentInstruction.substring(indexJ+1);
	}

    /**
     * ONLY USED FOR THE {@link Compare} CLASS.
     * @return currentInstruction
     */
    public String getCurrentInstruction() { return currentInstruction; }
}
