package ru.Makivay.sandbox.searcher;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import javax.annotation.Nullable;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Created by makivay on 14.02.17.
 */
public class LcsPathHolder {

    private Map<Integer, Map<Expression, List<LcsPath>>> positionExpressionPathMap;

    public LcsPathHolder() {
        this.positionExpressionPathMap = Maps.newHashMap();
    }

    @Nullable
    public Map<Expression, List<LcsPath>> getExpressions(int position) {
        return positionExpressionPathMap.get(position);
    }

    @Nullable
    public List<LcsPath> getPaths(int position, Expression expression) {
        return positionExpressionPathMap.get(position) != null ? positionExpressionPathMap.get(position).get(expression) : null;
    }

    public void put(int position, Expression expression, List<LcsPath> lcsPaths) {
        for (LcsPath lcsPath : lcsPaths) {
            put(position, expression, lcsPath);
        }

    }

    public void put(int position, Expression expression, LcsPath lcsPath) {
        final Map<Expression, List<LcsPath>> expressionListMap;

        if (positionExpressionPathMap.get(position) != null) {
            expressionListMap = positionExpressionPathMap.get(position);
        } else {
            expressionListMap = Maps.newHashMap();
            positionExpressionPathMap.put(position, expressionListMap);
        }

        final List<LcsPath> paths;
        if (expressionListMap.get(expression) != null) {
            paths = expressionListMap.get(expression);
        } else {
            paths = Lists.newArrayList();
            expressionListMap.put(expression, paths);
        }

        paths.add(lcsPath);
    }

    public List<LcsPath> getAllPaths() {
        return positionExpressionPathMap
                .values()
                .stream()
                .flatMap(expressionListMap -> expressionListMap.values().stream())
                .flatMap(Collection::stream)
                .collect(Collectors.toList());
    }

}
