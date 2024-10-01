package com.example.calculator;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
public class MainActivity extends AppCompatActivity {
    private TextView resultTextView;
    private StringBuilder currentInput = new StringBuilder();
    private double runningNumber = 0;
    private String operator = "";
    private boolean isNewOperation = true;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        resultTextView = findViewById(R.id.resultTextView);
        int[] numberButtonIDs = new int[] {
                R.id.button0, R.id.button1, R.id.button2, R.id.button3, R.id.button4,
                R.id.button5, R.id.button6, R.id.button7, R.id.button8, R.id.button9
        };
        View.OnClickListener numberClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Button button = (Button) v;
                if (isNewOperation) {
                    currentInput.setLength(0);
                    isNewOperation = false;
                }
                currentInput.append(button.getText().toString());
                resultTextView.setText(currentInput.toString());
            }
        };
        for (int id : numberButtonIDs) {
            findViewById(id).setOnClickListener(numberClickListener);
        }
        findViewById(R.id.buttonAdd).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                performOperation("+");
            }
        });
        findViewById(R.id.buttonSubtract).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                performOperation("-");
            }
        });
        findViewById(R.id.buttonMultiply).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                performOperation("*");
            }
        });
        findViewById(R.id.buttonDivide).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                performOperation("/");
            }
        });
        findViewById(R.id.buttonEquals).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calculateResult();
                isNewOperation = true;
            }
        });
        findViewById(R.id.buttonAC).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentInput.setLength(0);
                runningNumber = 0;
                operator = "";
                isNewOperation = true;
                resultTextView.setText("0");
            }
        });
    }
    private void performOperation(String op) {
        if (currentInput.length() > 0) {
            double inputNumber = Double.parseDouble(currentInput.toString());
            if (!operator.isEmpty()) {
                calculateResult();
            } else {
                runningNumber = inputNumber;
            }
            operator = op;
            isNewOperation = true;
        }
    }
    private void calculateResult() {
        if (!operator.isEmpty() && currentInput.length() > 0) {
            double inputNumber = Double.parseDouble(currentInput.toString());
            switch (operator) {
                case "+":
                    runningNumber += inputNumber;
                    break;
                case "-":
                    runningNumber -= inputNumber;
                    break;
                case "*":
                    runningNumber *= inputNumber;
                    break;
                case "/":
                    if (inputNumber != 0) {
                        runningNumber /= inputNumber;
                    } else {
                        resultTextView.setText("Error");
                        return;
                    }
                    break;
            }
            resultTextView.setText(String.valueOf(runningNumber));
            currentInput.setLength(0);
            operator = "";
        }
    }
}
