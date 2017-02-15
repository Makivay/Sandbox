package ru.Makivay.sandbox.rulesProcesser;

import org.apache.poi.openxml4j.opc.StreamHelper;

import java.util.Optional;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

@FunctionalInterface
public interface RulesChecker {
    boolean check(RuleData ruleData, Iterable<Rule> rules);
}
