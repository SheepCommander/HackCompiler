/* 
	Parser.java
 */

/** The main function of the parser is to break each assembly command into its
	underlying components (fields and symbols). **/

import java.io.*;

public class Parser
{
	static String currentInstruction;
	
	// This enum will be used to identify that type of the current instruction.
	
	public enum Instruction { L_INSTRUCTION, A_INSTRUCTION, C_INSTRUCTION, INVALID_INSTRUCTION }
	
	/* The constructor opens the input file/stream and gets ready to parse it.
		In our implementation, it is recommended that we open the file, transfer
		all of its contents into an array with each element containing a line
		of the file and close the file. This will make doing multiple passes
		a little easier*/
	public Parser(String fileName) throws IOException {
	
	}
	
	/* hasMoreCommands will do a look-ahead scan of lines of code to see if there
		are any more lines with valid commands.*/
	public boolean hasMoreLines() {
				
		return false;
	}
	
	/* advance is functionally similar to hasMoreCommands in that it will proceed
		to the next valid command and prepare to parse the line.*/
	public void advance() {
	
	}
	
	/* Returns the type of the current command. These values are defined as 
		static variables.*/
	public Instruction instructionType() {
		// if we have an A-Instruction
		// return Parser.Instruction.A_INSTRUCTION;
		
		// if we have a C-Instruction
		// return Parser.Instruction.C_INSTRUCTION;
		
		return Instruction.INVALID_INSTRUCTION;
	}
	
	/* Returns the symbol or decimal Xxx of the current command @Xxx or (Xxx).
		In the first iteration of the program symbol resolution will not be
		implemented.*/
	public String symbol() 
	{
		return null;
	}
	
	/* Returns the destination portion of the current command. */
	public String dest()
	{
		return null;	
	}
	
	/* Returns the computation portion of the current command. */
	public String comp()
	{
		return null;
	}
	
	/* Returns the jump portion of the current command. */
	public String jump()
	{
		return null;
	}
}