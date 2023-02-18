package com.example.tesst;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.android.material.button.MaterialButton;

import org.mozilla.javascript.Context;
import org.mozilla.javascript.Scriptable;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    TextView resultTv, solutionTv;
    MaterialButton buttonAC, buttonCE, buttonBracketClose, buttonBracketOpen, buttonDot, buttonHistory;
    MaterialButton buttonPlus, buttonMinus, buttonMultiply, buttonDivide, buttonEqual;
    MaterialButton button0, button1, button2, button3, button4, button5, button6, button7, button8, button9;
    String historyCalc = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        resultTv = findViewById(R.id.result_tv);
        solutionTv = findViewById(R.id.solution_tv);
        assignId(buttonCE, R.id.button_ce);
        assignId(buttonAC, R.id.button_AC);
        assignId(buttonBracketClose, R.id.button_close_bracket);
        assignId(buttonBracketOpen, R.id.button_open_bracket);
        assignId(buttonDot, R.id.button_dot);
        assignId(buttonPlus, R.id.button_plus);
        assignId(buttonMinus, R.id.button_minus);
        assignId(buttonMultiply, R.id.button_mutiply);
        assignId(buttonDivide, R.id.button_devide);
        assignId(buttonEqual, R.id.button_equal);
        assignId(button0, R.id.button_0);
        assignId(button1, R.id.button_1);
        assignId(button2, R.id.button_2);
        assignId(button3, R.id.button_3);
        assignId(button4, R.id.button_4);
        assignId(button5, R.id.button_5);
        assignId(button6, R.id.button_6);
        assignId(button7, R.id.button_7);
        assignId(button8, R.id.button_8);
        assignId(button9, R.id.button_9);
        assignId(buttonHistory, R.id.btnHistory);

    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        solutionTv = findViewById(R.id.solution_tv);
        resultTv = findViewById(R.id.result_tv);

        outState.putString("sltv", solutionTv.getText().toString());
        outState.putString("rstv", resultTv.getText().toString());

    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        solutionTv = findViewById(R.id.solution_tv);
        resultTv = findViewById(R.id.result_tv);
        solutionTv.setText(savedInstanceState.getString("sltv"));
        resultTv.setText(savedInstanceState.getString("rstv"));

    }

    void assignId(MaterialButton btn, int id) {
        btn = findViewById(id);
        btn.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        MaterialButton button = (MaterialButton) view;
        String buttonText = button.getText().toString();
        String dataCalculate = solutionTv.getText().toString();
        if (buttonText.equals("AC")) {
            solutionTv.setText("");
            resultTv.setText("0");
            return;
        }
        if (buttonText.equals("=")) {
            solutionTv.setText(resultTv.getText());
            historyCalc += dataCalculate.toString() + " = " + resultTv.getText().toString() + "/";
            return;
        }
        if (buttonText.equals("HISTORY")) {
            solutionTv.setText(dataCalculate.replace("HISTORY", ""));
            Intent intent = new Intent(getApplicationContext(), History.class);
            intent.putExtra("history", historyCalc);
            startActivity(intent);
            return;

        }
        if (buttonText.equals("CE")) {
            dataCalculate = dataCalculate.substring(0, dataCalculate.length() - 1);
        } else {
            dataCalculate = dataCalculate + buttonText;
        }

        solutionTv.setText(dataCalculate);
        String finalResult = getResult(dataCalculate);
        if (!finalResult.equals("Err")) {
            resultTv.setText(finalResult);
        }
    }

    String getResult(String data) {
        try {

            Context context = Context.enter();
            context.setOptimizationLevel(-1);
            Scriptable scriptable = context.initSafeStandardObjects();
            String finalResult = context.evaluateString(scriptable, data, "Javascript", 1, null).toString();
            if (finalResult.endsWith(".0")) {
                finalResult = finalResult.replace(".0", "");
            }
            return finalResult;
        } catch (Exception e) {
            return "Err";
        }

    }
}
