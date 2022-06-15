package com.example.infixtoothersconverter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Stack;

public class Converter {
    String mInfixString = "";
    String mPostFixString = "";
    Map<Character, Integer> mPriorityMap ;
    List<Character> mNumberList;

    public Converter() {
        initializeMap();
        initializeList();
    }

    private void initializeList() {
        mNumberList = new ArrayList<>();
        for(int i=0;i<10;i++){
            mNumberList.add((char) (i+'0'));
        }
        mNumberList.add('.');
    }

    private void initializeMap() {
        mPriorityMap = new HashMap<>();
        mPriorityMap.put('(', 1);
        mPriorityMap.put('+',2);
        mPriorityMap.put('-',2);
        mPriorityMap.put('*',3);
        mPriorityMap.put('/',3);
    }

    public String getInfixString() {
        return mInfixString;
    }

    public void setInfixString(String mInfixString) {
        this.mInfixString = mInfixString;
    }

    public String getPostFixString() {
        return mPostFixString;
    }

    public void setPostFixString(String mPostFixString) {
        this.mPostFixString = mPostFixString;
    }

    public void convertInfixToPostfix() {
        setPostFixString("");
        Stack<Character> stack = new Stack<>();
        for(int i=0;i<mInfixString.length();i++){
            if(mNumberList.contains(mInfixString.charAt(i))) {
                mPostFixString += mInfixString.charAt(i);
            }
            else if(mPriorityMap.keySet().contains(mInfixString.charAt(i))) {
                if(mInfixString.charAt(i) == '(') {
                    stack.push(mInfixString.charAt(i));
                } else {
                    mPostFixString += " ";
                    while(!stack.isEmpty() && mPriorityMap.get(mInfixString.charAt(i)) <= mPriorityMap.get(stack.peek())) {
                        mPostFixString += stack.pop() + " ";
                    }
                    stack.push(mInfixString.charAt(i));
                }
            }

            else if(mInfixString.charAt(i) == ')') {
                while (stack.peek() != '(') {
                    mPostFixString += " " + stack.pop();
                }
                stack.pop();
            }
        }
        while (!stack.isEmpty()) {
            mPostFixString += " " + stack.pop();
        }

        removeExtraSpaces();
    }

    private void removeExtraSpaces() {
        String temp = "";
        int i = 0;
        while(true) {
            if(mPostFixString.charAt(i) != ' ') {
                temp += mPostFixString.charAt(i);
                i++;
                break;
            }
            i++;
        }
        for(;i<mPostFixString.length();i++) {
            if(!(mPostFixString.charAt(i) == ' ' && mPostFixString.charAt(i-1) == ' ')) {
                temp += mPostFixString.charAt(i);
            }
        }
        mPostFixString = temp;
    }

    public void convertInfixToPostfix(String string) {
        setInfixString(string);
        convertInfixToPostfix();
    }

}
