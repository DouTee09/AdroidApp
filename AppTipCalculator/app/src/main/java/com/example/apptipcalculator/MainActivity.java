package com.example.apptipcalculator;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;
import androidx.core.view.ViewCompat;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.WindowInsetsCompat;
import androidx.appcompat.app.AppCompatActivity;



public class MainActivity extends AppCompatActivity {

    private EditText etBaseAmount;
    private SeekBar seekBarTip;
    private TextView tvTipPercentLabel;
    private TextView tvTipAmount;
    private TextView tvTotalAmount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle(R.string.app_name);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        etBaseAmount = findViewById(R.id.etBaseAmount);
        seekBarTip = findViewById(R.id.seekBarTip);
        tvTipPercentLabel = findViewById(R.id.tvTipPercentLabel);
        tvTipAmount = findViewById(R.id.tvTipAmount);
        tvTotalAmount = findViewById(R.id.tvTotalAmount);

        // Setup listeners
        etBaseAmount.addTextChangedListener(baseAmountTextChangedListener);
        seekBarTip.setOnSeekBarChangeListener(tipSeekBarChangeListener);
    }

    // Listener for changes in the base amount
    private TextWatcher baseAmountTextChangedListener = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {}

        @Override
        public void afterTextChanged(Editable s) {
            calculateTipAndTotal();
        }
    };

    // Listener for changes in the tip percentage
    private SeekBar.OnSeekBarChangeListener tipSeekBarChangeListener = new SeekBar.OnSeekBarChangeListener() {
        @Override
        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
            updateTipPercentLabel(progress);
            calculateTipAndTotal();
        }

        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {}

        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {}
    };

    // Update the tip percentage label based on SeekBar progress
    private void updateTipPercentLabel(int progress) {
        tvTipPercentLabel.setText(String.format("%d%%", progress));
    }

    // Calculate tip and total amount
    private void calculateTipAndTotal() {
        double baseAmount = 0.0;
        try {
            baseAmount = Double.parseDouble(etBaseAmount.getText().toString());
        } catch (NumberFormatException e) {
            // Handle invalid input gracefully
        }

        int tipPercentage = seekBarTip.getProgress();
        double tipAmount = baseAmount * tipPercentage / 100.0;
        double totalAmount = baseAmount + tipAmount;

        tvTipAmount.setText(String.format("%.2f", tipAmount));
        tvTotalAmount.setText(String.format("%.2f", totalAmount));
    }
}