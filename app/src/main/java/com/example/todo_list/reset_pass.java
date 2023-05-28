package com.example.todo_list;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.todo_list.R;

public class reset_pass extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.reset_pass);
    Button back_btn = (Button)findViewById(R.id.back_btn);
    back_btn.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent intent = new Intent(reset_pass.this,sign_in.class);
            startActivity(intent);
        }
    });
    Button send_btn = (Button) findViewById(R.id.reset_btn);
    send_btn.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Toast.makeText(getApplicationContext(),"Check your  Email",Toast.LENGTH_LONG).show();
            Intent intent = new Intent(reset_pass.this,sign_in.class);
            startActivity(intent);


        }
    });
}
}
