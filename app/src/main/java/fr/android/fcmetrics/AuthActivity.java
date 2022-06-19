package fr.android.fcmetrics;

import static fr.android.fcmetrics.modules.Controller.getUser;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;


import com.google.gson.Gson;
import com.rengwuxian.materialedittext.MaterialEditText;

import fr.android.fcmetrics.modules.UserEntity;

public class AuthActivity extends AppCompatActivity {
    private MaterialEditText email, password;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auth);

        Window window = this.getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.setStatusBarColor(this.getResources().getColor(R.color.black));
        Button btn_inscription = findViewById(R.id.inscription);
        email = findViewById(R.id.mail);
        password = findViewById(R.id.mdp);

        btn_inscription.setOnClickListener(v -> {
            Intent intent = new Intent(AuthActivity.this, SignUpActivity.class);
            startActivity(intent);
        });


    }

    public void connectionHandler(View view) {
        try {
            Runnable runnable = () -> {
                try{
                    // Requesting the user to the db
                    String getUser = getUser(email.getText().toString(), password.getText().toString());

                    // Verifying the information recieved from the api
                    if(!getUser.equals("Password is incorrect") && !getUser.equals("User doesn't exists")){
                        Gson gson = new Gson();

                        // Parsing the json
                        UserEntity user = gson.fromJson(getUser, UserEntity.class);
                        Log.i("user", user.getName());
                    }else{
                        Log.i("result", getUser);
                        Toast.makeText(AuthActivity.this, "E-mail ou mot de passe incorrect", Toast.LENGTH_LONG).show();
                    }


                }catch (Exception e){
                    e.printStackTrace();
                }
            };
            new Thread(runnable).start();

        } catch (Exception e) {
            e.printStackTrace();
        }


    }
}