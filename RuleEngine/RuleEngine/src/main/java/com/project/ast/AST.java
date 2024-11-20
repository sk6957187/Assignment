package com.project.ast;

import java.util.ArrayList;
import java.util.Stack;

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
        } else if (root.getRight() == null) {
            if (operators.contains(node.getType())) {
                node.setRight(root);
                root = node;
            } else {
                root.setRight(node);
            }
        }
        return root;
    }

    public Tree getASTBcp(ArrayList<String> rules) {
        if (rules == null) {
            return null;
        }
        String key = null, value = null;
        Tree node = null;
        Tree root = null;
        boolean keyFound = false;

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
            } else if (!keyFound) {
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

    public Tree getAST(ArrayList<String> infix) {
        if (infix == null) {
            return null;
        }
        Stack<Tree> stack = new Stack<>();
        Tree root = new Tree("");
        stack.push(root);
        Tree currentTree = root;
        String temp;
        Tree parent;
        boolean keyFound = false;
        String key = null;

        for (String token : infix) {
            temp = token;
            if ("(".equals(temp)) {
                currentTree.insertLeft(currentTree, "");
                stack.push(currentTree);
                currentTree = currentTree.getLeftOrSelf(currentTree);
            } else if (")".equals(temp)) {
                currentTree = stack.pop();
            } else if (operators.contains(temp)) {
                if (operators.contains(currentTree.getType()) || operands.contains(currentTree.getType())) {
                    Tree newParent = new Tree(temp);
                    newParent.setLeft(currentTree);
                    currentTree = newParent;
                } else {
                    currentTree.setType(temp);
                }
                currentTree.insertRight(currentTree, "");
                stack.push(currentTree);
                currentTree = currentTree.getRightOrSelf(currentTree);
            } else if (operands.contains(temp)) {
                currentTree.setType(temp);
                currentTree.setKey(key);
            } else if (!keyFound) {
                key = temp;
                keyFound = true;
            } else {
                currentTree.setValue(temp);
                keyFound = false;
                parent = stack.pop();
                currentTree = parent;
            }
        }
        return currentTree;
    }

    public ArrayList<String> infixToPostfix(ArrayList<String> infix) {
        if (infix == null) {
            return null;
        }
        ArrayList<String> postfixList = new ArrayList<>();
        Stack<String> opStack = new Stack<>();

        for (String token : infix) {
            if (token.chars().allMatch(Character::isDigit)) {
                postfixList.add(token);
            } else if ("(".equals(token)) {
                opStack.push(token);
            } else if (")".equals(token)) {
                while (!"(".equals(opStack.peek())) {
                    postfixList.add(opStack.pop());
                }
                opStack.pop();
            } else if (operators.contains(token)) {
                while (!opStack.isEmpty() && operators.contains(opStack.peek())) {
                    postfixList.add(opStack.pop());
                }
                opStack.push(token);
            }
        }

        while (!opStack.isEmpty()) {
            postfixList.add(opStack.pop());
        }

        return postfixList;
    }
}
