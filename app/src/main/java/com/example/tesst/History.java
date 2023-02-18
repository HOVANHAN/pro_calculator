package com.example.tesst;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class History extends AppCompatActivity {
    TextView text;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        text = findViewById(R.id.HisList);

        Intent intent = getIntent();
        String result = intent.getStringExtra("history");
        result = result.replace("null","");
        String[] fnresult = result.split("/");
        StringBuilder sb = new StringBuilder();
        for (String s : fnresult) {
            sb.append(s).append("\n");
        }
        text.setText(sb.toString());
    }
}