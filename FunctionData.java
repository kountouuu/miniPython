package visitors.model;

import minipython.node.PExpression;

import java.util.*;

public class FunctionData {
    private String name;
    private final int line;
    private final int col;
    private PrimitiveTypes returnType;
    private PExpression returnExpression = null;
    private Hashtable<String, PrimitiveTypes> args;

    // Constructor
    public FunctionData(String name, int line, int col) {
        this.name = name;
        this.line = line;
        this.col = col;
        args = new Hashtable<>();
        returnType = PrimitiveTypes.UNKNOWN;
    }

    public String getFunctionName() {
        return this.name;
    }

    /**
     * Returns the number of args ignoring
     * arguments with default values.
     * @return Number
     */
    public int sizeOfActualArgs() {
        int result = 0;
        for (PrimitiveTypes type : args.values()) {
            if (type == PrimitiveTypes.UNKNOWN) {
                result++;
            }
        }
        return result;
    }

    public int sizeOfArgs() {
        return args.size();
    }

    public String getName() {
        return name;
    }

    public PrimitiveTypes getReturnType() {
        return returnType;
    }

    public PExpression getReturnExpression() {
        return returnExpression;
    }

    public Hashtable<String, PrimitiveTypes> getArgs() {
        return args;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setReturnType(PrimitiveTypes returnType) {
        this.returnType = returnType;
    }

    public void setReturnExpression(PExpression returnExpression) {
        this.returnExpression = returnExpression;
    }

    public int getLine() {
        return line;
    }

    public int getCol() {
        return col;
    }
}
