package hackcompiler;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.Set;

/**
 * {@link Parser}: The main function of the parser is to break each assembly
 * command into its underlying components (fields and symbols).
 */
public class Parser {
    private String currentInstruction;
    private String[] instructions;
    private int instrIndex = -1;
    private Set<String> validCompInstructions;
    
    /**
     * Optional: Allows for accurate discernment between C instructions and invalid instructions.
     * If not set, every non-L non-A instruction is treated like a C instruction.
     */
    public void setValidCompInstructions(Set<String> validCompInstructions) {
        this.validCompInstructions = validCompInstructions;
    }

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
     * Using the {@link Instruction} enum, returns the type of the current command.
     * Optionally, use {@link #setValidCompInstructions(Set)} to enable Invalid_Command detection.
     * @return 
     *  - A_COMMAND for {@code @Xxx} where Xxx is either a symbol or a decimal number  <br>
     *  - C_COMMAND for dest=comp;jump  <br>
     *  - L_COMMAND (actually, pseudo- command) for (Xxx) where Xxx is a symbol.  <br>
     *  - INVALID_INSTRUCTION can be accurately returned if {@link #setValidCompInstructions(Set)} is set
     */
    public Instruction instructionType() {
        if (currentInstruction.startsWith("@")) {
            return Instruction.A_INSTRUCTION;
        }
        if (currentInstruction.startsWith("(")) {
            return Instruction.L_INSTRUCTION;
        }
        
        // Parser has not been told the valid comp instructions;
        if (this.validCompInstructions == null) {
            return Instruction.C_INSTRUCTION;
        } // Treat all remaining symbols as C Instructions.
        
        // Parser has been told the valid comp instructions;
        for (String comp : this.validCompInstructions) {
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
     * ONLY USED FOR DEBUGGING PURPOSES IN {@link Compare} CLASS.
     * @return currentInstruction
     */
    public String getCurrentInstruction() { return currentInstruction; }
}
