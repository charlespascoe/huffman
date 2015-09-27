package uk.co.cpascoe.huffman;

public class Leaf<T> {
    private int frequency;
    private T symbol;

    public Leaf(T symbol) {
        this.symbol = symbol;
    }

    public Leaf(T symbol, int frequency) {
        this.symbol = symbol;
        this.frequency = frequency;
    }

    public T getSymbol() {
        return this.symbol;
    }

    public int getFrequency() {
        return this.frequency;
    }
}
