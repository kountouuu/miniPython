package visitors;

import minipython.analysis.DepthFirstAdapter;
import minipython.node.*;
import visitors.model.FunctionData;
import visitors.model.PrimitiveTypes;

import java.util.Arrays;
import java.util.Hashtable;
import java.util.LinkedList;

public class VariableVisitor extends DepthFirstAdapter {
    private Hashtable<String, LinkedList<FunctionData>> functions;
    private Hashtable<String, PrimitiveTypes> variables;

    private int numOfErrors = 0;

    private int currentLine = 0;
    private int currentCol = 0;

    public VariableVisitor(Hashtable<String, LinkedList<FunctionData>> functions, Hashtable<String, PrimitiveTypes> variables) {
        this.functions = functions;
        this.variables = variables;
    }

    @Override
    public void inAAssignmentStatement(AAssignmentStatement node) {
        variables.put(node.getId().toString(), PrimitiveTypes.UNKNOWN);
    }

    @Override
    public void outAFunctionCall(AFunctionCall node) {
        String name = node.getId().toString();
        if (!functions.containsKey(name)) {
            System.out.printf("At(%d, %d): %s function was called but wasn't defined.%n", currentLine, currentCol, name.trim());
            numOfErrors++;
            // If not defined exit early
            return;
        }

        // Number of arguments this function was called
        int numberOfArgs;

        if (node.getArgList() == null) numberOfArgs = 0;
        else numberOfArgs = Arrays.asList(node.getArgList().toString().split(" ")).size();

        boolean found = false;
        for (FunctionData data : functions.get(name)) {
            if (data.sizeOfArgs() == numberOfArgs || data.sizeOfActualArgs() == numberOfArgs) {
                found = true;
                break;
            }
        }

        if (!found) {
            System.out.printf("At (%d, %d): Function %s doesn't match any defined function with %d arguments.%n", currentLine, currentCol, name.trim(), numberOfArgs);
            numOfErrors++;
        }
    }

    @Override
    public void outAIdValue(AIdValue node) {
        String name = node.getId().toString();
        if (!variables.containsKey(name)) {
            System.out.printf("At (%d, %d): %s was used but was not declared.%n", currentLine, currentCol, name.trim());
            numOfErrors++;
        }
    }

    @Override
    public void caseTId(TId node) {
        super.caseTId(node);
        currentLine = node.getLine();
        currentCol = node.getPos();
    }

    @Override
    public void caseTString(TString node) {
        super.caseTString(node);
        currentLine = node.getLine();
        currentCol = node.getPos();
    }

    @Override
    public void caseTNumber(TNumber node) {
        super.caseTNumber(node);
        currentLine = node.getLine();
        currentCol = node.getPos();
    }

    @Override
    public void caseTNone(TNone node) {
        super.caseTNone(node);
        currentLine = node.getLine();
        currentCol = node.getPos();
    }

    public int getNumOfErrors() {
        return numOfErrors;
    }
}
