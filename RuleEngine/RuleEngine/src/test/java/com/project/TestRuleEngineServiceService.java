package com.project;

import com.project.ast.AST;
import com.project.ast.Tree;
import com.project.data.Data;
import com.project.service.RuleEngineService;
import org.junit.Assert;
import org.junit.Test;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

public class TestRuleEngineServiceService {
    @Test
    public void testRuleEngine1() {
        RuleEngineService ruleEngineService = new RuleEngineService();
        String ruleName = "cartoon";
        HashMap<String, String> userInput = new HashMap<>();
        userInput.put("department", "department");
        userInput.put("age", "9");
        userInput.put("salary", "30000");
        userInput.put("experience", "6");
        boolean status = ruleEngineService.evaluateRule(ruleName, userInput);
        Assert.assertEquals(true, status);
        userInput.put("age", "12");
        status = ruleEngineService.evaluateRule(ruleName, userInput);
        Assert.assertEquals(false, status);
    }
    @Test
    public void testGetAST() {
        AST ast = new AST();

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
        RuleEngineService ruleEngineService = new RuleEngineService();
        String rule = "age < 20 AND age > 5";
        ArrayList<String> ruleItems = ruleEngineService.tokenizeRule(rule);
        AST ast = new AST();
        Tree tree = ast.getAST(ruleItems);
        Assert.assertEquals("AND", tree.getType());
        Assert.assertEquals("<", tree.getLeft().getType());
        Assert.assertEquals(">", tree.getRight().getType());
    }

    @Test
    public void testGetAST3() {
        RuleEngineService ruleEngineService = new RuleEngineService();
        String rule = "((age > 30 AND department = 'Marketing') AND (salary > 20000 OR experience > 5))";
        ArrayList<String> ruleItems = ruleEngineService.tokenizeRule(rule);
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
        RuleEngineService ruleEngineService = new RuleEngineService();
        String ruleName = "learning";
        HashMap<String, String> userInput = new HashMap<>();
        userInput.put("age", "20");
        boolean ruleCondition = ruleEngineService.evaluateRule(ruleName, userInput);
        Assert.assertEquals(true, ruleCondition);
        userInput.put("age", "45");
        ruleCondition = ruleEngineService.evaluateRule(ruleName, userInput);
        Assert.assertEquals(false, ruleCondition);
        userInput.put("age", "40");
        ruleCondition = ruleEngineService.evaluateRule(ruleName, userInput);
        Assert.assertEquals(false, ruleCondition);

        ruleName = "rule4";
        userInput.put("department", "'Sales'");
        ruleCondition = ruleEngineService.evaluateRule(ruleName, userInput);
        Assert.assertEquals(true, ruleCondition);
    }
    @Test
    public void sqlTest() throws SQLException {
        Data data = new Data();
        ArrayList<String> rule = new ArrayList<>();
        rule.add("CARTOON");
        rule.add("age>200");
//        Connection conn = data.createConn();
        data.checkRule(rule);
        data.loadData(rule);
    }
}
