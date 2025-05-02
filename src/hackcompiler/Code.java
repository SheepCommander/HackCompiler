package hackcompiler;

import java.util.HashMap;
import java.util.Set;

/** Translates Hack assembly language mnemonics into binary code. */
public class Code {
    // Declare static data structures that will contain lookups for the
    // destinations, computations, and jumps. This can be done many ways.
    // The Java HashMap structure is a common choice. By creating 3 hashmaps
    // (one for dest, one for comp, and one for jump) you can provide an input
    // value to get the binary equivalent.
    private HashMap<String, String> compMap = new HashMap<String, String>();
    private HashMap<String, String> destMap = new HashMap<String, String>();
    private HashMap<String, String> jumpMap = new HashMap<String, String>();
    
    /**
     * Useful if you need a ist of all the valid nmenonics for C Instructions.
     * @return all the valid mnemonics for comp.
     */
    public Set<String> getCompKeys() {
        return compMap.keySet();
    }
    
    /**
     * Initialize {@link Code} and populate the {@code compMap} etc. data structures.
     */
    public Code() {
        compMap.put("0", "0101010");
        compMap.put("1", "0111111");
        compMap.put("-1", "0111010");
        compMap.put("D", "0001100");
        compMap.put("A", "0110000");
        compMap.put("!D", "0001101");
        compMap.put("!A", "0110001");
        compMap.put("-D", "0001111");
        compMap.put("-A", "0110011");
        compMap.put("D+1", "0011111");
        compMap.put("A+1", "0110111");
        compMap.put("D-1", "0001110");
        compMap.put("A-1", "0110010");
        compMap.put("D+A", "0000010");
        compMap.put("D-A", "0010011");
        compMap.put("A-D", "0000111");
        compMap.put("D&A", "0000000");
        compMap.put("D|A", "0010101");
        compMap.put("M", "1110000"); // the mnemonics that use M instead of A
        compMap.put("!M", "1110001");
        compMap.put("-M", "1110011");
        compMap.put("M+1", "1110111");
        compMap.put("M-1", "1110010");
        compMap.put("D+M", "1000010");
        compMap.put("D-M", "1010011");
        compMap.put("M-D", "1000111");
        compMap.put("D&M", "1000000");
        compMap.put("D|M", "1010101");

        destMap.put(null,"000");
        destMap.put("","000");
        destMap.put("M","001");
        destMap.put("D","010");
        destMap.put("MD","011");
        destMap.put("A","100");
        destMap.put("AM","101");
        destMap.put("AD","110");
        destMap.put("AMD","111");
        
        jumpMap.put("","000");
        jumpMap.put(null,"000");
        jumpMap.put("JGT","001");
        jumpMap.put("JEQ","010");
        jumpMap.put("JGE","011");
        jumpMap.put("JLT","100");
        jumpMap.put("JNE","101");
        jumpMap.put("JLE","110");
        jumpMap.put("JMP","111");
    }

    /** 
     * @param inStr Should not be null as that goes against the computer's specification.
     * @return Returns the binary code of the comp nmenonic.
     */
    public String comp(String inStr) {
        return compMap.get(inStr);
    }

    /**
     * Returns the binary code of the destination,
     * or {@code 000} if there is none (null/empty string passed in)
     */
    public String dest(String inStr) {
        return destMap.get(inStr);
    }

    /**
     * Returns the binary code of the jump,
     * or {@code 000} if there is none (null/empty string passed in)
     */
    public String jump(String inStr) {
        return jumpMap.get(inStr);
    }
}