package com.example.stefani_antonio_s2216470;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;


public class CurrencyCalc extends AppCompatActivity {

    private String targetCurrencyCode;
    private double exchangeRate; // Rate: 1 GBP = X Target Currency

    private boolean convertToGBP = false ;
    private final String baseCurrencyCode = "GBP"; // Constant for British Pound

    // UI Elements
    private TextView CalcTitle;
    private TextView tvExchangeRate;

    private TextView tvInputLabel;
    private EditText etInputAmount;
    private Button btnCalculate;

    private TextView tvResultLabel;
    private TextView tvResult;

    private Button btnReverse;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calc);


        // These values must match the values from the main activity file
        targetCurrencyCode = getIntent().getStringExtra("CURRENCY_CODE");
        exchangeRate = getIntent().getDoubleExtra("EXCHANGE_RATE", 0.0);

         //Initialise UI Views
        CalcTitle = findViewById(R.id.CalcTitle);
        tvExchangeRate = findViewById(R.id.ExchangeRate);
        etInputAmount = findViewById(R.id.InputRate);
        btnCalculate = findViewById(R.id.btnCalc);
        tvResultLabel = findViewById(R.id.ResultLabel);
        tvResult = findViewById(R.id.Result);
        btnReverse= findViewById(R.id.btnReverse);
        tvInputLabel=findViewById(R.id.tvInputLabel);

        // Set Initial Display Values
        CalcTitle.setText("Convert GBP to " + targetCurrencyCode);
        tvExchangeRate.setText("1 GBP = " + String.format("%.2f", exchangeRate) + " " + targetCurrencyCode);
        tvResult.setText("0.00 " + targetCurrencyCode);

        // Set up the Calculate Button Listener
        btnCalculate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                performCalculation();
            }
        });

        // Set up the Reverse Button Listener
        btnReverse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toggleConversionDirection();
            }
        });

    }



   // does the calculation from GBP to target Currency
    private void performCalculation() {
        String inputStr = etInputAmount.getText().toString().trim();

        // Error Prevention empty input check
        if (inputStr.isEmpty()) {
            Toast.makeText(this, "Please enter an amount to convert.", Toast.LENGTH_SHORT).show();
            tvResult.setText("0.00 " + targetCurrencyCode);
            return;
        }

        try {
            double inputAmount = Double.parseDouble(inputStr);

            double resultAmount = inputAmount * exchangeRate;

            String resultSuffix;

            if (convertToGBP) {
                // Conversion: Target Rate-> GBP (Division)

                resultAmount = inputAmount / exchangeRate;
                resultSuffix = baseCurrencyCode; // GBP
            } else {
                // Conversion: GBP -> Target rate (Multiplication)

                resultAmount = inputAmount * exchangeRate;
                resultSuffix = targetCurrencyCode; // Target Currency
            }

            // Formats the result to 2 decimal places
            String formattedResult = String.format("%.2f", resultAmount);

            // Displays the result with the Currency Code
            tvResult.setText(formattedResult + " " + targetCurrencyCode);

        } catch (NumberFormatException e) {
            // More  error prevention for non number inputs
            Toast.makeText(this, "Invalid number format. Please check your input.", Toast.LENGTH_LONG).show();
            tvResult.setText("0.00 " + targetCurrencyCode);
        }
    }


        private void toggleConversionDirection() {
            convertToGBP=! convertToGBP;
            updateDisplay();
            // Clear previous input/result when direction changes
            etInputAmount.setText("");
            tvResult.setText("0.00");
        }

    private void updateDisplay() {
        String fromCode;
        String toCode;
        String inputLabel;
        String reverseButtonText;
        String resultLabelText;

        // Convert FROM Target TO GBP
        if (convertToGBP) {
            fromCode = targetCurrencyCode;
            toCode = baseCurrencyCode;
            inputLabel = "Amount in " + targetCurrencyCode + ":";
            reverseButtonText = "REVERSE CONVERSION (GBP → " + targetCurrencyCode + ")";
            resultLabelText = "Result in " + targetCurrencyCode + ":";


            // Convert FROM GBP TO Target (Default)
        } else {

            fromCode = baseCurrencyCode;
            toCode = targetCurrencyCode;
            inputLabel = "Amount in GBP:";
            reverseButtonText = "REVERSE CONVERSION (" + targetCurrencyCode + " → GBP)";
            resultLabelText = "Result in " + targetCurrencyCode + ":";
        }

        CalcTitle.setText("Convert " + fromCode + " to " + toCode);


        tvInputLabel.setText(inputLabel);
        tvResultLabel.setText(resultLabelText);
        btnReverse.setText(reverseButtonText);
        tvResult.setText("0.00 " + toCode);

    }

    }




