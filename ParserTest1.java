import minipython.lexer.Lexer;
import minipython.node.Start;
import minipython.parser.Parser;
import visitors.FunctionVisitor;
import visitors.VariableVisitor;
import visitors.model.FunctionData;
import visitors.model.PrimitiveTypes;

import java.io.FileReader;
import java.io.PushbackReader;
import java.util.Hashtable;
import java.util.LinkedList;

public class ParserTest1 {
    public static void main(String[] args) {
        try {
            Parser parser =
                new Parser(
                    new Lexer(
                        new PushbackReader(
                            new FileReader(args[0].toString()), 1024
                        )
                    )
                );

            Start ast = parser.parse();

            Hashtable<String, LinkedList<FunctionData>> functions = new Hashtable<>();
            Hashtable<String, PrimitiveTypes> variables = new Hashtable<>();

            FunctionVisitor functionVisitor = new FunctionVisitor(functions, variables);
            ast.apply(functionVisitor);

            int errors = functionVisitor.getNumOfErrors();

            if (functionVisitor.getNumOfErrors() == 0) {
                VariableVisitor variableVisitor = new VariableVisitor(functions, variables);
                ast.apply(variableVisitor);
                errors += variableVisitor.getNumOfErrors();
            }

            System.out.printf("%nErrors: %d%n", errors);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

