package com.dyonovan.brlib.collections;

public class Couplet<A, B> {
    protected A first;
    protected B second;

    public Couplet(A one, B two) {
        first = one;
        second = two;
    }

    public A getFirst() {
        return first;
    }

    public B getSecond() {
        return second;
    }
}