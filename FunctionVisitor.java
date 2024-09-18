package visitors;

import minipython.analysis.DepthFirstAdapter;
import minipython.node.*;
import visitors.model.FunctionData;
import visitors.model.PrimitiveTypes;

import java.util.Hashtable;
import java.util.LinkedList;

public class FunctionVisitor extends DepthFirstAdapter {
    private Hashtable<String, LinkedList<FunctionData>> functions;
    private Hashtable<String, PrimitiveTypes> variables;
    private FunctionData currentFunction;
    private int numOfErrors = 0;

    public FunctionVisitor(Hashtable<String, LinkedList<FunctionData>> functions, Hashtable<String, PrimitiveTypes> variables) {
        this.functions = functions;
        this.variables = variables;
    }

    // Function for just entering the function in Parser
    @Override
    public void inAFunction(AFunction node) {
        currentFunction = new FunctionData(node.getId().toString(), node.getId().getLine(), node.getId().getPos());
    }

    // When we exit the function check for duplicate functions
    @Override
    public void outAFunction(AFunction node) {
        if (functions.containsKey(currentFunction.getFunctionName())) {
            // Iterate through functions with the same name (but possibly different arguments)
            for (FunctionData data : functions.get(currentFunction.getFunctionName())) {
                if (data.sizeOfArgs() == currentFunction.sizeOfArgs() || data.sizeOfActualArgs() == currentFunction.sizeOfActualArgs()) {
                    int line = node.getId().getLine();
                    int col = node.getId().getPos();
                    int prevLine = data.getLine();
                    int prevCol = data.getCol();
                    System.out.println("At (" + line + "," + col + "): " + node.getId().toString() + "was defined again. Previous definition was at (" + prevLine + ", " + prevCol + ")");
                    numOfErrors++;

                    return;
                }
            }

            // If we reached here function is not defined again so add it to the list
            functions.get(currentFunction.getFunctionName()).add(currentFunction);
            return;
        }

        // First time seeing this function name. Add it on the map
        LinkedList<FunctionData> temp = new LinkedList<>();
        temp.add(currentFunction);
        functions.put(currentFunction.getFunctionName(), temp);
    }

    // Found a return statement. Saving the value of the return statement
    @Override
    public void inAReturnStatement(AReturnStatement node) {
        currentFunction.setReturnExpression(node.getExpression());
    }

    // parameter with default value
    @Override
    public void inAWithDefaultArgument(AWithDefaultArgument node) {
        currentFunction.getArgs().put(node.getId().toString(), getValueType(node.getValue()));

        PrimitiveTypes type = getValueType(node.getValue());
        variables.put(node.getId().toString(), type);

        if (type == PrimitiveTypes.UNKNOWN) {
            ADotFCallValue dotCall = ((ADotFCallValue) node.getValue());
            int line = dotCall.getId().getLine();
            int col = dotCall.getId().getPos();
            System.out.println("At (" + line + "," + col + "): Default argument to functions can't be function calls.");
            numOfErrors++;
        }
    }

    // parameter without a default value
    @Override
    public void inAWithoutDefaultArgument(AWithoutDefaultArgument node) {
        currentFunction.getArgs().put(node.getId().toString(), PrimitiveTypes.UNKNOWN);
        variables.put(node.getId().toString(), PrimitiveTypes.UNKNOWN);
    }

    // get the value type
    private PrimitiveTypes getValueType(PValue value) {
        if (value instanceof AStringValue) return PrimitiveTypes.STRING;
        else if (value instanceof ANoneValue) return PrimitiveTypes.NONE;
        else if (value instanceof ADotFCallValue) return PrimitiveTypes.UNKNOWN;
        else return PrimitiveTypes.NUMERIC;
    }

    public int getNumOfErrors() {
        return numOfErrors;
    }
}
