package com.project.service;

import com.project.ast.AST;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;

public class RuleEngine {
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
        operators = new ArrayList<>();
        operands = new ArrayList<>();
        operators.add("AND");
        operators.add("OR");
        operands.add(">");
        operands.add("<");
    }
    private int convertStringToNum(String num) {
        return Integer.parseInt(num);
    }
    public boolean evaluateRule(String ruleName, String department, String age, String salary, String experience) {
        if (ruleName == null) {
            return false;
        }
        String ruleValue = rules.get(ruleName);
        if (ruleValue == null) {
            return false;
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
        if (ruleItems2.isEmpty()) {
            return false;
        }
        AST ast = new AST();
        ast.getAST(ruleItems2);
        if (ruleItems2.get(0).equals("age")) {
            if (ruleItems2.get(1).equals(">")) {
                return this.convertStringToNum(age) > this.convertStringToNum(ruleItems2.get(2));
            } else if (ruleItems2.get(1).equals("<")) {
                return this.convertStringToNum(age) < this.convertStringToNum(ruleItems2.get(2));
            }
        }
        return false;
    }
}
