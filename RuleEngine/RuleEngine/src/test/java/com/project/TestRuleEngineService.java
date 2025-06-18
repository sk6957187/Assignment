package com.project;

import com.project.ast.AST;
import com.project.ast.Tree;
import com.project.dbConnection.DbConnection;
import com.project.resources.RuleEngineController;
import com.project.service.RuleEngineService;
import org.junit.Assert;
import org.junit.Test;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

public class TestRuleEngineService {
//    private RuleEngineConfiguration configuration;

    public RuleEngineConfiguration getOracleConfig() {
        String configPath = "A:/workspace/Assignment/RuleEngine/RuleEngine/meta-data/app_env_config.yml";
        RuleEngineConfiguration configuration = RuleEngineService.getOracleSqlConfig(configPath);
        return configuration;
    }


    @Test
    public void getOracleConfig1() {
        String configPath = "A:/workspace/Assignment/RuleEngine/RuleEngine/meta-data/app_env_config_test.yml";
        RuleEngineConfiguration configuration= RuleEngineService.getOracleSqlConfig(configPath);
        configuration.getOracleSqlConfig();
    }

    @Test
    public void testRuleEngine1() {
        String configPath = "A:/workspace/Assignment/RuleEngine/RuleEngine/meta-data/app_env_config_test.yml";
        RuleEngineConfiguration configuration= RuleEngineService.getOracleSqlConfig(configPath);
        RuleEngineService ruleEngineService = new RuleEngineService(configuration);
        String ruleName = "cartoon";
        HashMap<String, String> userInput = new HashMap<>();
        userInput.put("department", "department");
        userInput.put("age", "9");
        userInput.put("salary", "30000");
        userInput.put("experience", "6");

        boolean status = ruleEngineService.evaluateRule(ruleName, userInput);
        Assert.assertTrue(status);
        userInput.put("AGE", "12");
        status = ruleEngineService.evaluateRule(ruleName, userInput);
        Assert.assertFalse(status);
    }

    @Test
    public void testGetAST() {
        AST ast = new AST();
        ArrayList<String> rule = new ArrayList<>();

        // Test with a simple rule: age < 10
        rule.add("age");
        rule.add("<");
        rule.add("10");
        Tree tree = ast.getAST(rule);
        Assert.assertEquals("<", tree.getType());

        // Test with a compound rule: age < 20 AND age > 5
        rule.clear();
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
        String configPath = "A:/workspace/Assignment/RuleEngine/RuleEngine/meta-data/app_env_config_test.yml";
        RuleEngineConfiguration configuration= RuleEngineService.getOracleSqlConfig(configPath);
        RuleEngineService ruleEngineService = new RuleEngineService(configuration);
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
        String configPath = "A:/workspace/Assignment/RuleEngine/RuleEngine/meta-data/app_env_config_test.yml";
        RuleEngineConfiguration configuration= RuleEngineService.getOracleSqlConfig(configPath);
        RuleEngineService ruleEngineService = new RuleEngineService(configuration);
        String rule = "((age > 30 AND department = 'Marketing') AND (salary > 20000 OR experience > 5))";
        ArrayList<String> ruleItems = ruleEngineService.tokenizeRule(rule);

        AST ast = new AST();
        Tree tree = ast.getAST(ruleItems);
        Assert.assertEquals("AND", tree.getType());
        Assert.assertEquals("30", tree.getLeft().getLeft().getValue());
        Assert.assertEquals("OR", tree.getRight().getType());
        Assert.assertEquals("5", tree.getRight().getRight().getValue());
        Assert.assertEquals("20000", tree.getRight().getLeft().getValue());

        rule = "((AGE > 30 AND DEPARTMENT = SALES) OR (AGE < 25 AND DEPARTMENT = MARKETING)) AND (SALARY > 50000 OR EXPERIENCE > 5)";
        ruleItems = ruleEngineService.tokenizeRule(rule);
        tree = ast.getAST(ruleItems);
        Assert.assertEquals("AND",tree.getType());
    }

    // Test generating an AST with a rule having multiple AND conditions
    @Test
    public void testGetAST4() {
        String configPath = "A:/workspace/Assignment/RuleEngine/RuleEngine/meta-data/app_env_config_test.yml";
        RuleEngineConfiguration configuration= RuleEngineService.getOracleSqlConfig(configPath);
        RuleEngineService ruleEngineService = new RuleEngineService(configuration);
        String rule = "age > 10 AND age > 20 AND department = Store";
        ArrayList<String> ruleItems = ruleEngineService.tokenizeRule(rule);
        AST ast = new AST();
        Tree tree = ast.getAST(ruleItems);
        Assert.assertEquals("AND", tree.getType());
        Assert.assertEquals("AND", tree.getLeft().getType());
        Assert.assertEquals("=", tree.getRight().getType());
        Assert.assertEquals("Store", tree.getRight().getValue());
        Assert.assertEquals("20", tree.getLeft().getRight().getValue());
    }

    // Test evaluating a rule
    @Test
    public void testEvaluateRule() {
        String configPath = "A:/workspace/Assignment/RuleEngine/RuleEngine/meta-data/app_env_config_test.yml";
        RuleEngineConfiguration configuration= RuleEngineService.getOracleSqlConfig(configPath);
        RuleEngineService ruleEngineService = new RuleEngineService(configuration);
        String ruleName = "learning";
        HashMap<String, String> userInput = new HashMap<>();
        userInput.put("age", "20");
        boolean ruleCondition = ruleEngineService.evaluateRule(ruleName, userInput);
        Assert.assertTrue(ruleCondition);
        userInput.put("age", "45");
        ruleCondition = ruleEngineService.evaluateRule(ruleName, userInput);
        Assert.assertFalse(ruleCondition);
        userInput.put("age", "40");
        ruleCondition = ruleEngineService.evaluateRule(ruleName, userInput);
        Assert.assertFalse(ruleCondition);
        ruleName = "rule4";
        userInput.put("department", "'Sales'");
        ruleCondition = ruleEngineService.evaluateRule(ruleName, userInput);
        Assert.assertTrue(ruleCondition);
    }

    // Test checking rule existence in the database
    @Test
    public void testCheckRuleExist() throws SQLException {
        String configPath = "A:/workspace/Assignment/RuleEngine/RuleEngine/meta-data/app_env_config_test.yml";
        RuleEngineConfiguration configuration= RuleEngineService.getOracleSqlConfig(configPath);
        DbConnection dataConn = new DbConnection(configuration.getOracleSqlConfig());
        ArrayList<String> rule = new ArrayList<>();
        rule.add("CARTOON2");
        rule.add("age>200");
        ArrayList<String> res = dataConn.checkRule(rule);
        Assert.assertEquals(2, res.size());
    }

    @Test
    public void testInsertRule() throws SQLException {
        String configPath = "A:/workspace/Assignment/RuleEngine/RuleEngine/meta-data/app_env_config_test.yml";
        RuleEngineConfiguration configuration= RuleEngineService.getOracleSqlConfig(configPath);
        DbConnection data = new DbConnection(configuration.getOracleSqlConfig());
        boolean status = data.insertRuleInTable("CARTOON2", "Age>20");
        Assert.assertFalse(status);
    }

    @Test
    public void testGetRuleValue() {
        String configPath = "A:/workspace/Assignment/RuleEngine/RuleEngine/meta-data/app_env_config_test.yml";
        RuleEngineConfiguration configuration= RuleEngineService.getOracleSqlConfig(configPath);
        DbConnection data = new DbConnection(configuration.getOracleSqlConfig());
        RuleEngineService ruleEngineService = new RuleEngineService(configuration);
        String ruleValue = ruleEngineService.getRuleValue("CARTOON2");
        Assert.assertEquals("Age>20", ruleValue);
    }

    @Test
    public void testCombineRule() throws SQLException {
        String configPath = "A:/workspace/Assignment/RuleEngine/RuleEngine/meta-data/app_env_config_test.yml";
        RuleEngineConfiguration configuration= RuleEngineService.getOracleSqlConfig(configPath);
        DbConnection data = new DbConnection(configuration.getOracleSqlConfig());
//        boolean status = data.combineRule("Rule3", "Rule1 AND Rule2");
        HashMap<String, String> hm = new HashMap<>();
        hm.put("combinedRuleName", "Rule3");
        hm.put("combinedRuleValue", "Rule1 AND Rule2");
        RuleEngineController ruleEngCon = new RuleEngineController(configuration);
        ruleEngCon.combineRule(hm);
//        Assert.assertFalse(status);
    }

}
