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

public class MainActivity extends AppCompatActivity {
    EditText load;
    EditText binID;
    EditText goal;
    Button saveButton;
    Button toSideButton;
    DatabaseReference databaseReference;
////    private SeekBar bin1SeekBar;
//    SeekBar bin1SeekBar = findViewById(R.id.bin1SeekBar);

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        load = findViewById(R.id.load);
        binID = findViewById(R.id.binID);
        goal = findViewById(R.id.goal);
        saveButton = findViewById(R.id.pushToFirebase);
        toSideButton = findViewById(R.id.toSide);

        databaseReference = FirebaseDatabase.getInstance().getReference();

        toSideButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,SideActivity.class);
                startActivity(intent);
            }
        });

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String dailyWeight = MainActivity.this.load.getText().toString();
                String binID = MainActivity.this.binID.getText().toString();
                String goal = MainActivity.this.goal.getText().toString();

                try {
                    double loadValue = Double.parseDouble(dailyWeight);
                    double goalValue = Double.parseDouble(goal);

                    double dailyPercent = (double) loadValue/goalValue*100;

                    Map<String, Object> dataMap = new HashMap<>();
                    dataMap.put("weight", dailyWeight);
                    dataMap.put("dailyPercent", dailyPercent);

//                    databaseReference.child("0001").child("editText2").setValue(binID);
                    databaseReference.child("binDetails").child(binID).updateChildren(dataMap);

                    Toast.makeText(MainActivity.this, "Data uploaded to Firebase", Toast.LENGTH_SHORT).show();
                } catch (NumberFormatException e) {
                    Toast.makeText(MainActivity.this, "Invalid input. Please enter valid numbers", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}