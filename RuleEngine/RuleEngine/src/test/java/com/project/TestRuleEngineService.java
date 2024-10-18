package com.project;

import com.project.ast.AST;
import com.project.ast.Tree;
import com.project.service.RuleEngine;
import org.junit.Assert;
import org.junit.Test;
import java.util.ArrayList;

public class TestRuleEngineService {
    @Test
    public void testRuleEngine1() {
        RuleEngine ruleEngine = new RuleEngine();
        String ruleName = "cartoon";
        String department = "department";
        String age = "9";
        String salary = "30000";
        String experience = "6";
        boolean status = ruleEngine.evaluateRule(ruleName, department, age, salary, experience);
        Assert.assertEquals(true, status);
        age = "12";
        status = ruleEngine.evaluateRule(ruleName, department, age, salary, experience);
        Assert.assertEquals(false, status);
    }
    @Test
    public void testGetAST() {
        AST ast = new AST();
        ArrayList<String> operators = ast.getOperators();
        ArrayList<String> operands = ast.getOperands();

        ArrayList<String> rule = new ArrayList<>();
        //rule1= age < 10
        rule.add("age");
        rule.add("<");
        rule.add("10");
        Tree tree = ast.getAST(rule);
        Assert.assertEquals("<", tree.getType());
        //rule1 = age < 20 AND age > 5
        rule = new ArrayList<>();
        rule.add("age");
        rule.add("<");
        rule.add("20");
        rule.add("AND");
        rule.add("age");
        rule.add(">");
        rule.add("5");
        tree = ast.getAST(rule);
        Assert.assertEquals("AND", tree.getType());
        Assert.assertEquals("<", tree.getLeft().getType());
        Assert.assertEquals(">", tree.getRight().getType());
    }
    @Test
    public void testGetAST2() {
        RuleEngine ruleEngine = new RuleEngine();
        String rule = "age < 20 AND age > 5";
        ArrayList<String> ruleItems = ruleEngine.tokenizeRule(rule);
        AST ast = new AST();
        Tree tree = ast.getAST(ruleItems);
        Assert.assertEquals("AND", tree.getType());
        Assert.assertEquals("<", tree.getLeft().getType());
        Assert.assertEquals(">", tree.getRight().getType());
    }

    @Test
    public void testGetAST3() {
        RuleEngine ruleEngine = new RuleEngine();
        String rule = "((age > 30 AND department = 'Marketing') AND (salary > 20000 OR experience > 5))";
        ArrayList<String> ruleItems = ruleEngine.tokenizeRule(rule);
        AST ast = new AST();
        Tree tree = ast.getAST(ruleItems);
        Assert.assertEquals("AND", tree.getType());
        Assert.assertEquals("30", tree.getLeft().getLeft().getValue());
        Assert.assertEquals("OR", tree.getRight().getType());
        Assert.assertEquals("5", tree.getRight().getRight().getValue());
        Assert.assertEquals("20000", tree.getRight().getLeft().getValue());
    }
    @Test
    public void testEvaluateRule() {
        RuleEngine ruleEngine = new RuleEngine();
        String ruleName = "learning";
        boolean ruleCondition = ruleEngine.evaluateRule(ruleName, null, "20", null, null);
        Assert.assertEquals(true, ruleCondition);

        ruleCondition = ruleEngine.evaluateRule(ruleName, null, "45", null, null);
        Assert.assertEquals(false, ruleCondition);

        ruleCondition = ruleEngine.evaluateRule(ruleName, null, "40", null, null);
        Assert.assertEquals(false, ruleCondition);

        ruleName = "rule4";
        ruleCondition = ruleEngine.evaluateRule(ruleName, "'Sales'", "40", null, null);
        Assert.assertEquals(true, ruleCondition);
    }
}
