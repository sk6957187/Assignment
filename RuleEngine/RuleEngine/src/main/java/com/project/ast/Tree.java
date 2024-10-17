package com.project.ast;

import com.project.RuleEngineApplication;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;

public class Tree {
    private static final Logger logger = LoggerFactory.getLogger(Tree.class);
    private Tree node;
    private Tree left;
    private Tree right;
    private String type;
    private String value;
    private String key;
    private Boolean condition;
    public Tree getNode() {
        return node;
    }
    public void printInOrder(ArrayList<String> operators, ArrayList<String> operands) {
        if (this.left != null) {
            this.left.printInOrder(operators, operands);
        }
        if (operators.contains(this.type)) {
            logger.info("dataInOrder operator: {}", this.type);
        } else {
            logger.info("dataInOrder operand: {}{}{}", this.key, this.type, this.value);
        }
        if (this.right != null) {
            this.right.printInOrder(operators, operands);
        }
    }
    public void printPreOrder(ArrayList<String> operators, ArrayList<String> operands) {
        if (operators.contains(this.type)) {
            logger.info("dataPreOrder operator: {}", this.type);
        } else {
            logger.info("dataPreOrder operand: {}{}{}", this.key, this.type, this.value);
        }
        if (this.left != null) {
            this.left.printPreOrder(operators, operands);
        }
        if (this.right != null) {
            this.right.printPreOrder(operators, operands);
        }

    }
    public void printPostOrder(ArrayList<String> operators, ArrayList<String> operands) {
        if (this.left != null) {
            this.left.printPostOrder(operators, operands);
        }
        if (this.right != null) {
            this.right.printPostOrder(operators, operands);
        }
        if (operators.contains(this.type)) {
            logger.info("dataPostOrder operator: {}", this.type);
        } else {
            logger.info("dataPostOrder operand: {}{}{}", this.key, this.type, this.value);
        }
    }
    public void setNode(Tree node) {
        this.node = node;
    }

    public Tree getLeft() {
        return left;
    }

    public void setLeft(Tree left) {
        this.left = left;
    }

    public Tree getRight() {
        return right;
    }

    public void setRight(Tree right) {
        this.right = right;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public Boolean getCondition() {
        return condition;
    }

    public void setCondition(Boolean condition) {
        this.condition = condition;
    }
}
