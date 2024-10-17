package com.project.ast;

import java.util.ArrayList;

//Abstract syntax tree
public class AST {

    private final ArrayList<String> operators;
    private final ArrayList<String> operands;
    public AST() {
        operators = new ArrayList<>();
        operands = new ArrayList<>();
        operators.add("AND");
        operators.add("OR");
        operands.add(">");
        operands.add("<");
        operands.add("=");
    }

    public ArrayList<String> getOperators() {
        return operators;
    }

    public ArrayList<String> getOperands() {
        return operands;
    }

    private Tree addNode(Tree root, Tree node) {
        if (root == null) {
            root = node;
        } else if (root.getLeft() == null) {
            if (operators.contains(node.getType())) {
                node.setLeft(root);
                root = node;
            } else {
                root.setLeft(node);
            }
        } else if (root.getRight() == null ) {
            if (operators.contains(node.getType())) {
                node.setRight(root);
                root = node;
            } else {
                root.setRight(node);
            }
        }
        return root;
    }
    public Tree getAST(ArrayList<String> rules) {
        if (rules == null) {
            return null;
        }
        String key = null, value = null;
        Tree node = null;
        Tree root = null;
        boolean keyFound = false;
        boolean valueFound = false;
        for (String str : rules) {
            if (operators.contains(str)) {
                node = new Tree();
                node.setType(str);
                root = this.addNode(root, node);
            } else if (operands.contains(str)) {
                node = new Tree();
                node.setType(str);
                node.setKey(key);
                root = this.addNode(root, node);
            } else if (!keyFound){
                key = str;
                keyFound = true;
            } else {
                value = str;
                if (node != null && keyFound) {
                    node.setValue(value);
                    keyFound = false;
                }
            }
        }
        return root;
    }
}
