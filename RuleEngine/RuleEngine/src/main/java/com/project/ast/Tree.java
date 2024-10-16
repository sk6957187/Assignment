package com.project.ast;

import com.project.RuleEngineApplication;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Tree {
    private static final Logger logger = LoggerFactory.getLogger(Tree.class);
    private Tree node;
    private Tree left;
    private Tree right;
    private String type;
    private String value;
    private String key;

    public Tree getNode() {
        return node;
    }
    public void printInOrder() {
        logger.info("Printing in order");
    }
    public void printPreOrder() {
        logger.info("Printing pre order");

    }
    public void printPostOrder() {
        logger.info("Printing post order");

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
}
