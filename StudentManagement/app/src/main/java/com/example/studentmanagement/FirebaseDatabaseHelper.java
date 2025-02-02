package com.example.studentmanagement;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class FirebaseDatabaseHelper {
    private FirebaseDatabase mDatabase;
    private DatabaseReference mReferenceStudents;

    public FirebaseDatabaseHelper() {
        mDatabase = FirebaseDatabase.getInstance("https://studentmanagement-fa9b0-default-rtdb.asia-southeast1.firebasedatabase.app/");
        mReferenceStudents = mDatabase.getReference("students");
    }

    public void addStudent(Student student) {
        String id = mReferenceStudents.push().getKey();
        student.setId(id);
        mReferenceStudents.child(id).setValue(student);
    }

    public void updateStudent(String id, Student student) {
        mReferenceStudents.child(id).setValue(student);
    }

    public void deleteStudent(String id) {
        mReferenceStudents.child(id).removeValue();
    }

    public DatabaseReference getReference() {
        return mReferenceStudents;
    }
}
