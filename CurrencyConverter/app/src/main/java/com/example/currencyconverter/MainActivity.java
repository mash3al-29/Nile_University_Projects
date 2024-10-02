package com.example.currencyconverter;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private Spinner fromCurrencySpinner;
    private Spinner toCurrencySpinner;
    private EditText fromAmountEditText;
    private EditText toAmountEditText;
    private Map<String, Double> rates;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fromCurrencySpinner = findViewById(R.id.fromCurrencySpinner);
        toCurrencySpinner = findViewById(R.id.toCurrencySpinner);
        fromAmountEditText = findViewById(R.id.fromAmountEditText);
        toAmountEditText = findViewById(R.id.toAmountEditText);

        setupSpinners();

        Button convertButton = findViewById(R.id.convertButton);
        convertButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                convertCurrency();
            }
        });

        loadRates();
    }

    private void setupSpinners() {
        String[] currencies = {"EUR", "USD", "DKK", "GBP"}; // Add more currencies as needed
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, currencies);
        fromCurrencySpinner.setAdapter(adapter);
        toCurrencySpinner.setAdapter(adapter);
    }

    private void loadRates() {
        rates = new HashMap<>();
        rates.put("EUR", 1.0);
        rates.put("USD", 1.12);
        rates.put("DKK", 7.44);
        rates.put("GBP", 0.85);
        // Add more rates as needed
    }

    private void convertCurrency() {
        String fromCurrency = fromCurrencySpinner.getSelectedItem().toString();
        String toCurrency = toCurrencySpinner.getSelectedItem().toString();
        String fromAmountString = fromAmountEditText.getText().toString();

        if (!fromAmountString.isEmpty()) {
            double fromAmount = Double.parseDouble(fromAmountString);
            double toAmount = fromAmount * (rates.get(toCurrency) / rates.get(fromCurrency));
            toAmountEditText.setText(String.format("%.2f", toAmount));
        }
    }

}
