package scripts.fletcherino;

/*
 * Node class with a execute and validate function.
 */
public abstract class Node {
    public abstract void printStatus(Task task);
    public abstract boolean validate(Task task);
    public abstract void execute(Task task);
}
