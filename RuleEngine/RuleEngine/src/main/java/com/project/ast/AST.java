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
    }
    private Tree addNode(Tree root, Tree node) {
        if (root == null) {
            root = node;
        } else if (root.getRight() == null) {
            root.setRight(node);
        } else if (root.getLeft() == null) {
            root.setLeft(node);
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
        for (String str : rules) {
            if (operands.contains(str)) {
                node = new Tree();
                root = this.addNode(root, node);
                node.setType(str);
                node.setKey(key);
            } else if (key == null){
                key = str;
            } else {
                value = str;
                if (node != null) {
                    node.setValue(value);
                }
            }
        }
        return root;
    }
}
