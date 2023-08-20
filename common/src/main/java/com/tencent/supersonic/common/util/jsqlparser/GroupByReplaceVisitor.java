package com.tencent.supersonic.common.util.jsqlparser;

import java.util.List;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import net.sf.jsqlparser.JSQLParserException;
import net.sf.jsqlparser.expression.Expression;
import net.sf.jsqlparser.expression.Function;
import net.sf.jsqlparser.expression.operators.relational.ExpressionList;
import net.sf.jsqlparser.parser.CCJSqlParserUtil;
import net.sf.jsqlparser.schema.Column;
import net.sf.jsqlparser.statement.select.GroupByElement;
import net.sf.jsqlparser.statement.select.GroupByVisitor;
import org.apache.commons.lang3.StringUtils;

@Slf4j
public class GroupByReplaceVisitor implements GroupByVisitor {

    ParseVisitorHelper parseVisitorHelper = new ParseVisitorHelper();
    private Map<String, String> fieldToBizName;


    public GroupByReplaceVisitor(Map<String, String> fieldToBizName) {
        this.fieldToBizName = fieldToBizName;
    }

    public void visit(GroupByElement groupByElement) {
        groupByElement.getGroupByExpressionList();
        ExpressionList groupByExpressionList = groupByElement.getGroupByExpressionList();
        List<Expression> groupByExpressions = groupByExpressionList.getExpressions();

        for (int i = 0; i < groupByExpressions.size(); i++) {
            Expression expression = groupByExpressions.get(i);

            String replaceColumn = parseVisitorHelper.getReplaceColumn(expression.toString(), fieldToBizName);
            if (StringUtils.isNotEmpty(replaceColumn)) {
                if (expression instanceof Column) {
                    groupByExpressions.set(i, new Column(replaceColumn));
                }
                if (expression instanceof Function) {
                    try {
                        Expression element = CCJSqlParserUtil.parseExpression(replaceColumn);
                        ((Function) expression).getParameters().getExpressions().set(0, element);
                    } catch (JSQLParserException e) {
                        log.error("e", e);
                    }
                }
            }
        }
    }
}