package ru.Makivay.sandbox.searcher;

import com.google.common.collect.Lists;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Created by makivay on 13.02.17.
 */
public class Searcher {

    private final static double MIN_PROB = 0.5;
    private final static int maxMissedExpressions = 1;

    public static LcsPathHolder process(final String text, final Rule rule) {
        int iterations = 0;
        final LcsPathHolder holder = new LcsPathHolder();
        // build simple paths
        for (int searchPosition = 0; searchPosition < text.length(); searchPosition++) {
            for (Expression expression : rule.getExpressions()) {
                iterations++;
                if (expression.getType() != Expression.Type.Any && expression.getText().length() + searchPosition < text.length()) {
                    final double weight = calculateWeight(text, searchPosition, expression);
                    final LcsPath path = new LcsPath(text.substring(searchPosition, searchPosition + expression.getText().length()), searchPosition, weight);
                    if (path.getPathProb() > MIN_PROB) {
                        holder.put(searchPosition, expression, path);
                    }
                }
            }
        }
        // build paths with parents
        for (int searchPosition = 0; searchPosition < text.length(); searchPosition++) {
            for (Expression expression : rule.getExpressions()) {
                final List<LcsPath> paths = holder.getPaths(searchPosition, expression);
                final List<LcsPath> newPaths = Lists.newArrayList();
                if (paths != null) {
                    for (LcsPath path : paths) {
                        for (int parentPosition = 0; parentPosition < searchPosition; parentPosition++) {
                            for (Expression parentExpression : rule.getExpressions()) {
                                if (parentPosition + parentExpression.getMaxLength() < searchPosition
                                        && parentExpression.getPosition() < expression.getPosition()
                                        && parentExpression.getPosition() >= expression.getPosition() - maxMissedExpressions - 1) { // добавь сюда проверку на кол-во ани идущих вподряд
                                    final Optional<Expression> missedAny = getMissedAnny(rule.getExpressions(), parentExpression.getPosition(), expression.getPosition());
                                    final List<LcsPath> parentPaths = holder.getPaths(parentPosition, parentExpression);
                                    if (parentPaths != null) {
                                        for (LcsPath parentPath : parentPaths) {
                                            iterations++;
                                            final LcsPath potentialPath;
                                            if (!missedAny.isPresent()) {
                                                potentialPath = new LcsPath(path.getText(), searchPosition, path.getProb(), parentPath);
                                            } else {
                                                final int anyStartPosition = parentPath.getStartPosition() + parentPath.getCharLenght();
                                                final String anyText = text.substring(anyStartPosition, path.getStartPosition());
                                                final LcsPath anyPath = new LcsPath(anyText, anyStartPosition, 1.0, parentPath);
                                                potentialPath = new LcsPath(path.getText(), searchPosition, path.getProb(), anyPath);
                                            }
                                            if (potentialPath.getPathProb() > MIN_PROB) {
                                                newPaths.add(potentialPath);
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
                holder.put(searchPosition, expression, newPaths);
            }
        }

        System.out.println("iterations: " + iterations);
        return holder;
    }

    private static Optional<Expression> getMissedAnny(List<Expression> expressions, int fromPosition, int toPosition) {
        return expressions.stream()
                .filter(expression -> expression.getPosition() > fromPosition)
                .filter(expression -> expression.getPosition() < toPosition)
                .filter(expression -> expression.getType().equals(Expression.Type.Any))
                .findFirst();
    }

    private static double calculateWeight(final String text, final int startFrom, final Expression expression) {
        final String expressionText = expression.getText();
        final List<Boolean> marks = new ArrayList<>();
        final int searchTo = Math.min(startFrom + expression.getMaxLength(), text.length());
        for (int i = 0; i < expressionText.length(); i++) {
            final int index = text.indexOf(expressionText.charAt(i));
            marks.add(index > 0 && index < searchTo);
            if (startFrom + i < text.length()) {
                marks.add(expressionText.charAt(i) == text.charAt(i + startFrom));
            }
        }
        final long positiveMarks = marks.stream().filter(Boolean::booleanValue).count();
        return (double) positiveMarks / marks.size();
    }
}
