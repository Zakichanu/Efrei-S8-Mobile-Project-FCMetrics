package fr.android.fcmetrics;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class AuthActivity extends AppCompatActivity {
    private Button btn_inscription;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auth);

        // Hiding toolbar
        getSupportActionBar().hide();

        btn_inscription = findViewById(R.id.inscription);

        btn_inscription.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AuthActivity.this, SignUpActivity.class);
                startActivity(intent);
            }
        });
    }
}