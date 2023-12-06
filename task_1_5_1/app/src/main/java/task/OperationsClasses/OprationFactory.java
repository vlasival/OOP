package task.operationsClasses;

/**
 * Factory class create objects of arithmetical operations classes.
 */
public class OprationFactory {
    /**
     * Method creates class instances.
     *
     * @param token operation.
     * @return wrapped class of arithmetical operation.
     */
    public static Operation create(String token) {
        switch (token) {
            case "+": return new Addition();
            case "-": return new Substraction();
            case "*": return new Multiplication();
            case "/": return new Division();
            case "sin": return new Sin();
            case "cos": return new Cos();
            case "log": return new Log();
            case "sqrt": return new Sqrt();
            case "pow": return new Pow();
            default: return null;
        }
    }
}
