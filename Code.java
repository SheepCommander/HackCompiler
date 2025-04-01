public class Code
{
	/* Declare static data structures to contain lookups for the 
		destinations, computations and jumps. This can be done with
		a number of different methods. The Java HashTable structure
		is a common choice. By creating 3 HashTables (one for dest,
		one for computation and one for jump), you can provide an
		input value to get the binary equivalent.*/
	
	/* Initialize and populate the data structures*/
	public Code() {
	
	}
	
	/* Returns the translated destination or "000" if there is no
		destination (null string passed in) */
	public String dest(String inStr)
	{
		return "000";
	}
	
	/* Returns the translated computation. This should not be called
		if there is no computation as that would be a violation of the
		language specification. */
	public String comp(String inStr)
	{
		return "0000000";
	}
	
	/* Returns the translated jump or "000" if there is no
		jump (null string passed in) */
	public String jump(String inStr)
	{
		return "000";
	}
}