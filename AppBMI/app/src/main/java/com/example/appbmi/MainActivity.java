package com.example.appbmi;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.text.DecimalFormat;

public class MainActivity extends AppCompatActivity {

    Button btnBMI;
    EditText editName,editHeight,editHeavy,editBMI,editDiag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        btnBMI= findViewById(R.id.btBMI);
        editName=findViewById(R.id.edtName);
        editHeight= findViewById(R.id.edtHeight);
        editHeavy= findViewById(R.id.edtHeavy);
        editBMI= findViewById(R.id.edtBMI);
        editDiag = findViewById(R.id.edtDiag);

        btnBMI.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                double H=Double.parseDouble(editHeight.getText()+"");
                double W=Double.parseDouble(editHeavy.getText()+"");
                double BMI=W/Math.pow(H,2);
                String diag ="";

                if(BMI<18)
                {
                    diag ="Bạn gầy";
                }
                else if(BMI<=24.9)
                {
                    diag ="Bạn bình thường";
                }
                else if(BMI<=29.9)
                {
                    diag ="Bạn béo phì độ 1";
                }
                else if(BMI<=34.9)
                {
                    diag ="Bạn béo phì độ 2";
                }
                else
                {
                    diag ="Bạn béo phì độ 3";
                }

                DecimalFormat dcf=new DecimalFormat("#.0");
                editBMI.setText(dcf.format(BMI));
                editDiag.setText(diag);
            }
        });

    }
}