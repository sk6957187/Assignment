package com.project.service;

import com.project.ast.AST;
import com.project.ast.Tree;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.HashMap;

public class RuleEngine {
    private static final Logger logger = LoggerFactory.getLogger(RuleEngine.class);
    //operator AND/OR
    //operand >, <
    private final HashMap<String, String> rules;
    private final ArrayList<String> operators;
    private final ArrayList<String> operands;
    public RuleEngine() {
        rules = new HashMap<>();
        rules.put("cartoon", "age  < 10");//age,,<,10
        rules.put("dharmic", "age > 40");//age,>,40
        rules.put("learning", "age > 15 AND age < 40");
        rules.put("rule4", "age > 30 AND department = 'Sales'");
        AST ast = new AST();
        operands = ast.getOperands();
        operators = ast.getOperators();
    }
    private int convertStringToNum(String num) {
        return Integer.parseInt(num);
    }
    public ArrayList<String> tokenizeRule(String ruleValue) {
        if (ruleValue == null) {
            return null;
        }
        String[] ruleItems = ruleValue.split(" ");
        ArrayList<String> ruleItems2 = new ArrayList<>();
        for(String str: ruleItems) {
            if (str == null) {
                continue;
            }
            if (str.isEmpty()) {
                continue;
            }
            ruleItems2.add(str);
        }
        return ruleItems2;
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
    public void updateCondition(Tree root, String department, String age, String salary, String experience) {
        if (root == null) {
            return;
        }
        if (!operators.contains(root.getType())) {
            if ("age".equals(root.getKey())) {
                root.setCondition(this.isConditionTrue(root.getType(), root.getValue(), age));
            } if ("salary".equals(root.getKey())) {
                root.setCondition(this.isConditionTrue(root.getType(), root.getValue(), salary));
            } if ("experience".equals(root.getKey())) {
                root.setCondition(this.isConditionTrue(root.getType(), root.getValue(), experience));
            } else if ("department".equals(root.getKey())) {
                root.setCondition(this.isConditionTrue(root.getType(), root.getValue(), department));
            }
        }
        if (root.getLeft() != null) {
            this.updateCondition(root.getLeft(), department, age, salary, experience);
        }
        if (root.getRight() != null) {
            this.updateCondition(root.getRight(), department, age, salary, experience);
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
        if (posix == null || posix.isEmpty() || posix.size() < 3) {
            return false;
        }
        String arg1 = null, arg2 = null, result = null;
        String str;
        for(int i=0; i<posix.size(); i++) {
            str = posix.get(i);
            if (operators.contains(str)) {
                result = this.getNodeResult(arg1, arg2, str);
                arg1 = result;
                arg2 = null;
                continue;
            }
            if (arg1 == null) {
                arg1 = str;
                continue;
            }
            if (arg2 == null) {
                arg2 = str;
            }
        }
        return result == "true";
    }
    public boolean evaluateRule(String ruleName, String department, String age, String salary, String experience) {
        if (ruleName == null) {
            return false;
        }
        String ruleValue = rules.get(ruleName);
        if (ruleValue == null) {
            return false;
        }
        ArrayList<String> ruleItems2 = this.tokenizeRule(ruleValue);
        if (ruleItems2 == null || ruleItems2.isEmpty()) {
            return false;
        }
        AST ast = new AST();
        Tree root = ast.getAST(ruleItems2);
        this.updateCondition(root, department, age, salary, experience);
        ArrayList<String> posix = this.convertTreeToStack(root, null);
        boolean finalResult = this.getFinalValue(posix);
        logger.info("Final result: {}", finalResult);
        return finalResult;
    }
}
