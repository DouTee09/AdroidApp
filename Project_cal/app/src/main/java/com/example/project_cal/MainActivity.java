package com.example.project_cal;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import java.text.DecimalFormat;
import android.widget.TextView;

public class MainActivity extends Activity {
    EditText edt1, edt2, edt3;
    Button btncong, btntru, btnnhan, btnchia;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Ánh xạ các thành phần giao diện
        edt1 = findViewById(R.id.editTextA);
        edt2 = findViewById(R.id.editTextB);
        edt3 = findViewById(R.id.editTextC);
        btncong = findViewById(R.id.btTong);
        btntru = findViewById(R.id.btHieu);
        btnchia = findViewById(R.id.btThuong);
        btnnhan = findViewById(R.id.btTich);

        // Thiết lập sự kiện cho các nút tính toán
        btncong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Lấy giá trị từ EditText và chuyển đổi thành số nguyên
                int a = Integer.parseInt("0" + edt1.getText().toString());
                int b = Integer.parseInt("0" + edt2.getText().toString());
                // Thực hiện phép tính và hiển thị kết quả
                edt3.setText("a + b = " + (a + b));
            }
        });

        btntru.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int a = Integer.parseInt("0" + edt1.getText().toString());
                int b = Integer.parseInt("0" + edt2.getText().toString());
                edt3.setText("a - b = " + (a - b));
            }
        });

        btnnhan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int a = Integer.parseInt("0" + edt1.getText().toString());
                int b = Integer.parseInt("0" + edt2.getText().toString());
                edt3.setText("a * b = " + (a * b));
            }
        });

        btnchia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int a = Integer.parseInt("0" + edt1.getText().toString());
                int b = Integer.parseInt("0" + edt2.getText().toString());
                if (b == 0) {
                    edt3.setText("B phải khác 0");
                } else {
                    float result = (float) a / b;
                    DecimalFormat decimalFormat = new DecimalFormat("#.###");
                    String formattedResult = decimalFormat.format(result);
                    edt3.setText("a / b = " + formattedResult);
                }
            }
        });
    }
}
