package com.example.calculator;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.renderscript.Script;
import android.view.View;
import android.widget.TextView;

import com.google.android.material.button.MaterialButton;
import org.mozilla.javascript.Context;
import org.mozilla.javascript.Scriptable;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    TextView workingsTV;
    TextView resultsTV;

    MaterialButton buttonC, buttonBracketsOpen, buttonBracketsClose, buttonDot, buttonAC;
    MaterialButton buttonDivide, buttonMultiply, buttonPlus, buttonMinus,buttonEqual;
    MaterialButton button0, button1, button2, button3, button4, button5, button6, button7, button8, button9;

    String workings = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        workingsTV =(TextView)findViewById(R.id.workingsTextView);
        resultsTV =(TextView)findViewById(R.id.resultsTextView);

        assignId(buttonC,R.id.cButton);
        assignId(buttonBracketsOpen,R.id.oBracketsButton);
        assignId(buttonBracketsClose,R.id.cBracketsButton);
        assignId(buttonDot,R.id.dotButton);
        assignId(buttonDivide,R.id.devideButton);
        assignId(buttonMultiply,R.id.xButton);
        assignId(buttonPlus,R.id.plusButton);
        assignId(buttonMinus,R.id.minusButton);
        assignId(buttonEqual,R.id.equalButton);
        assignId(button0,R.id.zeroButton);
        assignId(button1,R.id.oneButton);
        assignId(button2,R.id.twoButton);
        assignId(button3,R.id.threeButton);
        assignId(button4,R.id.fourButton);
        assignId(button5,R.id.fiveButton);
        assignId(button6,R.id.sixButton);
        assignId(button7,R.id.sevenButton);
        assignId(button8,R.id.eightButton);
        assignId(button9,R.id.nineButton);
        assignId(buttonAC,R.id.ACButton);

    }

    void assignId(MaterialButton btn, int id){
        btn = findViewById(id);
        btn.setOnClickListener(this);
    }

    @Override
    public void onClick(View view){
        MaterialButton button = (MaterialButton) view;
        String buttonText = button.getText().toString();
        String dataToCalculate = workingsTV.getText().toString();

        if (buttonText.equals("AC"))
        {
            workingsTV.setText("");
            resultsTV.setText("0");
            return;
        }
        if (buttonText.equals("="))
        {
            workingsTV.setText(resultsTV.getText());
            return;
        }
        if (buttonText.equals("C"))
        {
            dataToCalculate = dataToCalculate.substring(0,dataToCalculate.length()-1);
        }
        else
        {
            dataToCalculate = dataToCalculate + buttonText;
        }

        workingsTV.setText(dataToCalculate);

        String finalResult = getResult(dataToCalculate);

        if (!finalResult.equals("Err")){
            resultsTV.setText(finalResult);
        }
    }

    String getResult (String data){
        try {
            Context context = Context.enter();
            context.setOptimizationLevel(-1);
            Scriptable scriptable = context.initStandardObjects();
            String finalResult = context.evaluateString(scriptable,data, "Javascript", 1,null).toString();
            if (finalResult.endsWith(".0")){
                finalResult = finalResult.replace(".0", "");
            }

            return finalResult;
        } catch (Exception e){
            return "Err";
        }
    }

}