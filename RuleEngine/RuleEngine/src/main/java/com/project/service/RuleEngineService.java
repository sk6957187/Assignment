package com.project.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import com.project.RuleEngineConfiguration;
import com.project.ast.AST;
import com.project.ast.Stack;
import com.project.ast.Tree;
import com.project.dbConnection.DbConnection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

public class RuleEngineService {
    private static final Logger logger = LoggerFactory.getLogger(RuleEngineService.class);
    //operator AND/OR
    //operand >, <
    private final HashMap<String, String> rules;
    private final ArrayList<String> operators;
    private final ArrayList<String> operands;
    private final DbConnection dbConn;

    RuleEngineConfiguration ruleEngineConfig = new RuleEngineConfiguration();
//    DbConnection dbConn = new DbConnection(ruleEngineConfig);

    public RuleEngineService(RuleEngineConfiguration ruleEngineConfig) {
        this.dbConn = new DbConnection(ruleEngineConfig.getOracleSqlConfig());
        rules = new HashMap<>();
        rules.put("cartoon", "age  < 10");//age,,<,10
        rules.put("dharmic", "age > 40");//age,>,40
        rules.put("learning", "age > 15 AND age < 40");
        rules.put("rule1", "((age > 30 AND department = Sales) OR (age < 25 AND department = Marketing)) AND (salary > 50000 OR experience > 5)");
        rules.put("rule2","((age > 30 AND department = Marketing)) AND (salary > 20000 OR experience > 5)");
        rules.put("rule4", "age > 30 AND department = Sales");
        rules.put("rule3", "(age > 10) AND  (age > 20 and department = Store)");
        AST ast = new AST();
        operands = ast.getOperands();
        operators = ast.getOperators();
    }

    public String getUiBaseApi() {
        return ruleEngineConfig.getUiConfig().getUiBaseApi();
    }

    public String getRuleValue(String ruleName) {
        if (ruleName == null) {
            return null;
        }
//        return rules.get(ruleName);
        return dbConn.fetchedValue(ruleName);
    }
    public int convertStringToNum(String num) {
        if (num == null || num.isEmpty()) {
            return 0;
        }
        try {
            return Integer.parseInt(num);
        } catch (Exception e) {
            logger.info("Error in parsing string to num: {}", num);
        }
        return 0;

    }
    public ArrayList<String> tokenizeBinary(String expression) {
        ArrayList<String> tokens = new ArrayList<>();
        ArrayList<String> validTokens = new ArrayList<>();
        validTokens.add("\\(");
        validTokens.add("\\)");
        validTokens.addAll(operators);
        validTokens.addAll(operands);
        if (expression == null) {
            return tokens;
        }
        String[] temp;
        int i;
        String t;
        for (i=0; i<validTokens.size(); i++) {
            t = validTokens.get(i);
            temp = expression.split(t, -1);
            if (i == 0) {
                t = "(";
            } else if (i == 1) {
                t = ")";
            }
            expression = String.join(","+t+",", temp);
        }
        temp = expression.split(",");
        String str;
        for (i=0; i<temp.length; i++) {
            str = temp[i];
            if (str != null) {
                str = str.trim();
                if (!str.isEmpty()) {
                    tokens.add(str);
                }

            }
        }
        return tokens;
    }
    public ArrayList<String> tokenizeRule(String ruleValue) {
        return this.tokenizeBinary(ruleValue);
    }
    private boolean isConditionTrue(String operand, String configData, String userData) {
        int configNum = 0;
        int userNum = 0;
        if ("<".equals(operand)) {
            configNum = this.convertStringToNum(configData);
            userNum = this.convertStringToNum(userData);
            return configNum > userNum;
        } else if (">".equals(operand)) {
            configNum = this.convertStringToNum(configData);
            userNum = this.convertStringToNum(userData);
            return configNum < userNum;
        } else if ("=".equals(operand)) {
            return configData.equals(userData);
        }
        return false;
    }
    public void updateCondition(Tree root, HashMap<String, String> userInput) {
        if (root == null || userInput == null) {
            return;
        }
        if (!operators.contains(root.getType()) && root.getType() != null && !root.getType().isEmpty()) {
            String str = userInput.get(root.getKey());
            root.setCondition(this.isConditionTrue(root.getType(), root.getValue(), userInput.get(root.getKey())));
        }
        if (root.getLeft() != null) {
            this.updateCondition(root.getLeft(), userInput);
        }
        if (root.getRight() != null) {
            this.updateCondition(root.getRight(), userInput);
        }
    }
    private ArrayList<String> convertTreeToStack(Tree root, ArrayList<String> result) {
        if (root == null) {
            return result;
        }
        if (result == null) {
            result = new ArrayList<>();
        }
        if (root.getLeft() != null) {
            this.convertTreeToStack(root.getLeft(), result);
        }
        if (root.getRight() != null) {
            this.convertTreeToStack(root.getRight(), result);
        }
        if (operators.contains(root.getType())) {
            result.add(root.getType());
        } else if (operands.contains(root.getType())) {
            result.add(root.getCondition().toString());
        }
        return result;
    }
    private String getNodeResult(String arg1, String arg2, String op) {
        if (arg1 == null || arg2 == null || op == null) {
            return "false";
        }
        boolean argA = false;
        boolean argB = false;
        if (arg1 == "true") {
            argA = true;
        }
        if (arg2 == "true") {
            argB = true;
        }
        boolean result = false;
        if ("AND".equals(op)) {
            result = argA && argB;
        } else if ("OR".equals(op)) {
            result = argA || argB;
        }
        return Boolean.toString(result);
    }
    private boolean getFinalValue(ArrayList<String> posix) {
        if (posix == null || posix.isEmpty()) {
            return false;
        }
        Stack stack = new Stack();
        String arg1, arg2, result = null;
        String str;
        for (String s : posix) {
            str = s;
            if (operators.contains(str)) {
                arg1 = (String) stack.pop();
                arg2 = (String) stack.pop();
                result = this.getNodeResult(arg1, arg2, str);
                stack.push(result);
            } else {
                stack.push(str);
            }
        }
        result = (String) stack.pop();
        return "true".equals(result);
    }
    public boolean evaluateRule(String ruleName, HashMap<String, String> userInput) {
        if (ruleName == null) {
            return false;
        }
        String ruleValue = this.getRuleValue(ruleName);
        if (ruleValue == null) {
            return false;
        }
        ArrayList<String> ruleItems2 = this.tokenizeRule(ruleValue);
        if (ruleItems2 == null || ruleItems2.isEmpty()) {
            return false;
        }
        AST ast = new AST();
        Tree root = ast.getAST(ruleItems2);
        this.updateCondition(root, userInput);
        ArrayList<String> posix = this.convertTreeToStack(root, null);
        boolean finalResult = this.getFinalValue(posix);
        logger.info("Final result: {}", finalResult);
        return finalResult;
    }

    public boolean combineRule(HashMap<String, String> userData) throws SQLException {
        logger.info("combineRule data: {}",userData);
        if(userData == null){
            return false;
        }
        String combinedRuleName = userData.get("combinedRuleName");
        String combinedRuleValue = userData.get("combinedRuleValue");
        if(combinedRuleValue == null || combinedRuleName == null || combinedRuleName.isEmpty() || combinedRuleValue.isEmpty()){
            logger.info("Invalid Rule Name or rule data: {},{}", combinedRuleName, combinedRuleValue);
            return false;
        }
        return dbConn.combineRule(combinedRuleName,combinedRuleValue);
    }

    public boolean createRule(HashMap<String, String> userData) throws SQLException {
        logger.info("createRule data : {}",userData);
        String ruleName = userData.get("ruleName");
        String ruleValue = userData.get("ruleValue");
        return dbConn.insertRuleInTable(ruleName,ruleValue);
    }

    public static RuleEngineConfiguration getOracleSqlConfig(String configPath) {
        if (configPath == null) {
            return null;
        }
        RuleEngineConfiguration configuration = null;
        ObjectMapper objectMapper = new ObjectMapper(new YAMLFactory());
        try {
            configuration = objectMapper.readValue(new File(configPath), RuleEngineConfiguration.class);
        } catch (IOException ioe) {
            logger.info("IOE: for file: " + configPath);
        }
        return configuration;
    }
}
