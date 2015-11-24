package ast;

/**
 * A factory that produces the public static Mutation objects corresponding to each
 * mutation
 */
public class MutationFactory {
    public static Mutation getRemove() {
        // TODO Auto-generated method stub
        return new Remove();
    }

    public static Mutation getSwap() {
        // TODO Auto-generated method stub
        return new Swap();
    }

    public static Mutation getReplace() {
        // TODO Auto-generated method stub
        return new Replace();
    }

    public static Mutation getTransform() {
        // TODO Auto-generated method stub
        return new Transform();
    }

    public static Mutation getInsert() {
        // TODO Auto-generated method stub
        return new Insert();
    }

    public static Mutation getDuplicate() {
        // TODO Auto-generated method stub
        return new Duplicate();
    }
}
