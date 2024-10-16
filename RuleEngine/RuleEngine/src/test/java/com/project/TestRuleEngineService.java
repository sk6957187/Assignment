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
        ArrayList<String> rule = new ArrayList<>();
        rule.add("age");
        rule.add("<");
        rule.add("10");
        Tree tree = ast.getAST(rule);
        tree.printInOrder();
        tree.printPostOrder();
        tree.printPreOrder();
    }
}
