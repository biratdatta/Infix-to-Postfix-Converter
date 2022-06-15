package com.example.infixtoothersconverter;

import android.app.Activity;
import android.graphics.Canvas;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Stack;

public class MainActivity extends AppCompatActivity {

    EditText mInput;
    TextView mConvertedPostfixOutput;
    TextView mEvaluatedValueOutput;
    Button mConvertButton;
    Button mEvaluateButton;
    Converter mConverter;
    Evaluator mEvaluator;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mConverter = new Converter();
        mEvaluator = new Evaluator();
        initialize();
    }

    private void initialize() {
        mConvertButton = findViewById( R.id.ConvertToPostfix);
        mEvaluateButton = findViewById(R.id.evaluate);
        mConvertButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                convertInfixToPostFix();
            }
        });
        mEvaluateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                evaluatePostFixValue();
            }
        });
    }

    private void convertInfixToPostFix() {
        mInput = findViewById(R.id.input);
        String inputString  = mInput.getText().toString();
        if (!isValidString(inputString)) {
            return;
        }
        mConverter.convertInfixToPostfix(inputString);
        showPostFixNotation();
        hideSoftKeyBoard();
    }

    private boolean isValidString(String inputString) {
        if(isEmptyString(inputString)) return false;
        if(!isMatchingParentheses(inputString)) return false;
        return true;
    }

    boolean isEmptyString(String inputString) {
        if (inputString.equals("")) {
            Toast.makeText(this, "Enter an infix expression",
                    Toast.LENGTH_SHORT).show();
            return true;
        }
        return false;
    }

    private boolean isMatchingParentheses(String inputString) {
        Stack<Character>stack = new Stack<>();
        for(int i=0;i<inputString.length();i++){
            if(inputString.charAt(i) == '(') {
                stack.push('(');
            } else if(inputString.charAt(i) == ')') {
                if(stack.isEmpty() || stack.pop() != '(') {
                    Toast.makeText(this, "Invalid infix expression",
                            Toast.LENGTH_SHORT).show();
                    return false;
                }
            }
        }

        if (stack.isEmpty()) {
            return true;
        } else {
            Toast.makeText(this, "Invalid infix expression",
                    Toast.LENGTH_SHORT).show();
            return false;
        }
    }

    private void hideSoftKeyBoard() {
        InputMethodManager imm = (InputMethodManager)this.getSystemService(Activity.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(this.getCurrentFocus().getWindowToken(), 0);
    }

    private void showPostFixNotation() {
        String  postFixString= mConverter.getPostFixString();
        mConvertedPostfixOutput = findViewById(R.id.postfix_output);
        mConvertedPostfixOutput.setText(postFixString);
        mEvaluateButton = findViewById(R.id.evaluate);
        mEvaluatedValueOutput = findViewById(R.id.output_evaluated_postfix);
        mEvaluateButton.setVisibility(View.VISIBLE);
        mEvaluatedValueOutput.setVisibility(View.VISIBLE);
        mEvaluatedValueOutput.setText("");
        TextView textView = findViewById(R.id.postfix_value_string);

        textView.setVisibility(View.VISIBLE);
    }

    private void evaluatePostFixValue() {
        double value = mEvaluator.evaluatePostFix((mConverter.getPostFixString()));
        mEvaluatedValueOutput.setText(Double.toString(value));
    }
}
