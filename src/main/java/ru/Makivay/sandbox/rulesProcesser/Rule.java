package ru.Makivay.sandbox.rulesProcesser;

/**
 * Created by makivay on 24.01.17.
 */
@FunctionalInterface
public interface Rule {
    boolean test(RuleData ruleData);
}
