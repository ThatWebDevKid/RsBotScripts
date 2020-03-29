package scripts.API;

/*
 * Node class with a execute and validate function.
 */
public abstract class Node {
    public abstract void printStatus();
    public abstract boolean validate();
    public abstract void execute();
}
