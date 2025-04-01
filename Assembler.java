/* 
	Assembler.java
 */

import java.io.*;

/** Assembler will serve as the central class that will call all other methods
	as needed. It will house the instances of the other classes. **/

public class Assembler
{
	// Static variables section
	static int debugLevel = 10;
	
	public static void main(String[] args) {
		Debug debugger = new Debug();
		Code myCoder = new Code();
		String fileName = null;
		
		// We make sure that a file has been specified or we give an error and exit
		if (args.length == 0) {
			System.out.println("Error: Filepath required");
			return;
		}
		
		// With the filename defined, extract everything before the .asm suffix
		// to get the base of the filename. This will be used to create an output file
		
		try {
			// Create a new Parser object sending in the input filename. This must be
			// in a try block as there is the potential for a IOException when dealing
			// with the file system
			Parser myParser = new Parser(fileName);
			
			// Create a new file for output. File to be named same as input file except with the
			// extension of .hack
			
			while (myParser.hasMoreLines()) {
			
				if (myParser.instructionType() == Parser.Instruction.A_INSTRUCTION)
				{
					// Process an A_Command and write to output file
				}
				if (myParser.instructionType() == Parser.Instruction.C_INSTRUCTION)
				{
					// Process the C_Command by using a Coder object to translate into binary
					// and write to the output file
				} 
				
				// Advance to the next available command
				myParser.advance();
			
			}
			
			// Close the output file
		}
		catch (IOException e) {
			System.out.println("Error " + e.getMessage());
		}
	}
}