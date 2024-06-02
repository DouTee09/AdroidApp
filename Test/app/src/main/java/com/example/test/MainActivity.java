package com.example.test;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {

    private EditText editTextNote;
    private Button buttonSave, buttonLoad;
    private TextView textViewResult;
    private DatabaseReference databaseReference;

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

        editTextNote = findViewById(R.id.editTextNote);
        buttonSave = findViewById(R.id.buttonSave);
        buttonLoad = findViewById(R.id.buttonLoad);
        textViewResult = findViewById(R.id.textViewResult);

        // Khởi tạo tham chiếu tới Firebase Realtime Database
        databaseReference = FirebaseDatabase.getInstance("https://test-f0f23-default-rtdb.asia-southeast1.firebasedatabase.app/").getReference("notes");

        buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveNote();
            }
        });

        buttonLoad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadNote();
            }
        });
    }

    private void saveNote() {
        String note = editTextNote.getText().toString();
        // Tạo một ID duy nhất cho mỗi ghi chú
        String noteId = databaseReference.push().getKey();
        if (noteId != null) {
            databaseReference.child(noteId).setValue(note);
            textViewResult.setText("Note saved to Firebase");
        }
    }

    private void loadNote() {
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                StringBuilder notesBuilder = new StringBuilder();
                for (DataSnapshot noteSnapshot : dataSnapshot.getChildren()) {
                    String note = noteSnapshot.getValue(String.class);
                    notesBuilder.append(note).append("\n");
                }
                textViewResult.setText(notesBuilder.toString());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                textViewResult.setText("Error loading notes");
            }
        });
    }
}
