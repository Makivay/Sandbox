package ru.Makivay.sandbox.searcher;

/**
 * Created by makivay on 14.02.17.
 */
public class Expression {
    private final Type type;
    private final String text;
    private final int position;
    private final int minLength;
    private final int maxLength;

    public Expression(Type type, String text, int exprPosition) {
        this.type = type;
        this.text = text;
        this.position = exprPosition;
        this.minLength = 0;
        this.maxLength = text.length();

    }

    public Expression(Type type, String text, int position, int minLength, int maxLength) {
        this.type = type;
        this.text = text;
        this.position = position;
        this.minLength = minLength;
        this.maxLength = maxLength;
    }

    public Type getType() {
        return type;
    }

    public String getText() {
        return text;
    }

    public int getPosition() {
        return position;
    }

    public int getMaxLength() {
        return maxLength;
    }

    public int getMinLength() {
        return minLength;
    }

    enum Type {String, Any}
}
