package com.example.infixtoothersconverter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Stack;

public class Evaluator {

    List<String>mOperatorList;
    public Evaluator() {
        initOperatorList();
    }

    private void initOperatorList() {
        mOperatorList = new ArrayList<>();
        mOperatorList.add("+");
        mOperatorList.add("-");
        mOperatorList.add("*");
        mOperatorList.add("/");
    }


    public double evaluatePostFix(String str) {
        double result = 0;
        Stack<Double> stack = new Stack<>();
        String [] strArray = str.split(" ");
        List<String> strList = new ArrayList<>(Arrays.asList(strArray));

        for(int i=0;i<strList.size();i++) {
            String c = strList.get(i);
            if (mOperatorList.contains(c)) {
                if(c.equals("+")) {
                    if(isSignBeforeNumber(stack)) {
                        result = stack.pop();
                    }
                    else result = stack.pop() + stack.pop();
                } else if(c.equals("-")) {
                    if(isSignBeforeNumber(stack)) {
                        result = -stack.pop();
                    }
                    else result = -stack.pop() + stack.pop();
                } else if(c.equals("*")) {
                    result = stack.pop() * stack.pop();
                } else if(c.equals("/")) {
                    result = 1 / stack.pop() * stack.pop();
                }
                stack.push(result);


            } else{
                if(convertStrToNumber(c) != Double.MIN_VALUE) {
                    stack.push(convertStrToNumber(c));
                }
            }
        }
        return stack.pop();
    }

    private boolean isSignBeforeNumber(Stack<Double> stack) {
        return stack.size() < 2;
    }

    private Double convertStrToNumber(String str) {
        return Double.valueOf(str);
    }
}
