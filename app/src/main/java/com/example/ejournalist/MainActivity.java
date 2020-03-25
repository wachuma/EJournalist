package com.example.ejournalist;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    Button newEventBtn, myEventsBtn, exitBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        newEventBtn = (Button) findViewById(R.id.newEventBtn);
        myEventsBtn = (Button) findViewById(R.id.myEventsBtn);
        exitBtn = (Button) findViewById(R.id.exitBtn);
        goToNewEvent();
        exitApp();
    }
    public void goToNewEvent(){
        newEventBtn.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(MainActivity.this, NewEventActivity.class);
                        startActivity(intent);
                    }
                }
        );
    }
    public void exitApp(){
        exitBtn.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        moveTaskToBack(true);
                        android.os.Process.killProcess(android.os.Process.myPid());
                        System.exit(1);
                    }
                }
        );
    }
}
