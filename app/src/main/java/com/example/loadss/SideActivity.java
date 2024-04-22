package com.example.loadss;

import android.content.Intent;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.Toast;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import java.util.HashMap;
import java.util.Map;

public class SideActivity extends AppCompatActivity {
    EditText weight;
    EditText month;
    EditText goal;
    Button saveButton;
    DatabaseReference databaseReference;
    Button toMainButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.side_activity);
        weight = findViewById(R.id.load_side);
        month = findViewById(R.id.binID_side);
        goal = findViewById(R.id.goal_side);
        saveButton = findViewById(R.id.pushToFirebase_side);
        toMainButton = findViewById(R.id.toMain);

        databaseReference = FirebaseDatabase.getInstance().getReference().child("month");

        toMainButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SideActivity.this,MainActivity.class);
                startActivity(intent);
            }
        });
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String preWeight = weight.getText().toString();
                String preMonth = month.getText().toString();
                String prePercent = goal.getText().toString();

                    try {
                    double loadValue = Double.parseDouble(preWeight);
                    double goalValue = Double.parseDouble(prePercent);

                    double percentage = (double) loadValue/goalValue*100;

                    Map<String, Object> dataMap = new HashMap<>();
                    dataMap.put("totalWeight", preWeight);
                    dataMap.put("totalPercentage", percentage);

//                    databaseReference.child("0001").child("editText2").setValue(input2);
                    databaseReference.child("binDetails").child(preMonth).updateChildren(dataMap);

                    Toast.makeText(SideActivity.this, "Data uploaded to Firebase", Toast.LENGTH_SHORT).show();
                } catch (NumberFormatException e) {
                    Toast.makeText(SideActivity.this, "Invalid input. Please enter valid numbers", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
