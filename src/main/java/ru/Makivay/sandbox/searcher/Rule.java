package ru.Makivay.sandbox.searcher;

import com.google.common.base.Strings;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by makivay on 14.02.17.
 */
public class Rule {
    private final List<Expression> expressions;

    private Rule(List<Expression> expressions) {
        this.expressions = expressions;
    }

    public static Rule parse(final String rule) {
        final List<Expression> expressions = new ArrayList<>();
        final Pattern pattern = Pattern.compile("\"([^\"]*)\"|(Any\\(\\))");
        final Matcher stringMatcher = pattern.matcher(rule);

        int exprPosotion = 0;
        while (stringMatcher.find()) {
            for (int i = 1; i <= stringMatcher.groupCount(); i++) {
                final String text = stringMatcher.group(i);
                if(!Strings.isNullOrEmpty(text)) {
                    switch (i) {
                        case 1:
                            expressions.add(new Expression(Expression.Type.String, text, exprPosotion++));
                            break;
                        case 2:
                            expressions.add(new Expression(Expression.Type.Any, "", exprPosotion++, 0, Integer.MAX_VALUE));
                            break;
                    }
                }
            }
        }

        return new Rule(expressions);
    }

    public List<Expression> getExpressions() {
        return expressions;
    }

    public int lenght(){
        return expressions != null ? expressions.size() : 0;
    }
}
