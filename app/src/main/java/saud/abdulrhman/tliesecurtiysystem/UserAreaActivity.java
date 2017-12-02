package saud.abdulrhman.tliesecurtiysystem;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

public class UserAreaActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_area);

        ImageButton img =(ImageButton) findViewById(R.id.imageButton5);
        ImageButton img1 =(ImageButton) findViewById(R.id.imageButton2);

        img1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Intent intent = new Intent(UserAreaActivity.this,support.class);
                UserAreaActivity.this.startActivity(intent);

                }




        });

        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(UserAreaActivity.this,Alrte_Activity.class);
                UserAreaActivity.this.startActivity(intent);

            }
        });


        findViewById(R.id.imageButton4).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(UserAreaActivity.this,CountActivity.class));
            }
        });
    }


    }

