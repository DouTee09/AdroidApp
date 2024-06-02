package com.example.studentmanagement;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private EditText editTextName, editTextEmail;
    private Button buttonAdd, buttonUpdate, buttonDelete;
    private ListView listViewStudents;
    private ArrayList<Student> studentList;
    private ArrayAdapter<Student> adapter;
    private FirebaseDatabaseHelper databaseHelper;
    String slectedStudentId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editTextName = findViewById(R.id.editTextName);
        editTextEmail = findViewById(R.id.editTextEmail);
        buttonAdd = findViewById(R.id.buttonAdd);
        buttonUpdate = findViewById(R.id.buttonUpdate);
        buttonDelete = findViewById(R.id.buttonDelete);
        listViewStudents = findViewById(R.id.listViewStudents);
        studentList = new ArrayList<>();
        databaseHelper = new FirebaseDatabaseHelper();

        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, studentList);
        listViewStudents.setAdapter(adapter);

        buttonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addStudent();
            }
        });

        buttonUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateStudent();
            }
        });

        buttonDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Implement deleteStudent() method
            }
        });

        listViewStudents.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Student selectedStudent = studentList.get(position);
                slectedStudentId = selectedStudent.getId(); // Gán giá trị cho biến instance slectedStudentId
                editTextName.setText(selectedStudent.getName());
                editTextEmail.setText(selectedStudent.getEmail());
            }
        });


        loadStudents();
    }

    private void addStudent() {
        String name = editTextName.getText().toString();
        String email = editTextEmail.getText().toString();
        if (!name.isEmpty() && !email.isEmpty()) {
            DatabaseReference newStudentRef = databaseHelper.getReference().push();
            String studentId = newStudentRef.getKey(); // Lấy ID được tạo tự động
            Student student = new Student(studentId, name, email);
            newStudentRef.setValue(student);
            clearFields();
        } else {
            Toast.makeText(this, "Please enter name and email", Toast.LENGTH_SHORT).show();
        }
    }

    private void updateStudent() {
        String studentId = slectedStudentId;
        String name = editTextName.getText().toString();
        String email = editTextEmail.getText().toString();
        Student student = new Student(studentId, name, email);
        databaseHelper.updateStudent(studentId, student);
    }


    private void clearFields() {
        editTextName.setText("");
        editTextEmail.setText("");
    }

    private void loadStudents() {
        databaseHelper.getReference().addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                studentList.clear();
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    Student student = postSnapshot.getValue(Student.class);
                    studentList.add(student);
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(MainActivity.this, "Failed to load students", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
