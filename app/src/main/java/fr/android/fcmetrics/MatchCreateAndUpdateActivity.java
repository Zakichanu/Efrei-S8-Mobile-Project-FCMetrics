package fr.android.fcmetrics;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.Toast;

import java.text.SimpleDateFormat;

import fr.android.fcmetrics.modules.Match;

public class MatchCreateAndUpdateActivity extends AppCompatActivity
{
    private EditText eventNameET;
    private EditText eventDateET;
    private EditText eventTimeET;
    private EditText eventLatitudeET;
    private EditText eventLongitudeET;
    private EditText eventScoreUserET;
    private EditText eventScoreOpponentET;
    private Bundle bundle;

    // Parsing date and time
    SimpleDateFormat sdf = new SimpleDateFormat("dd MMMM yyyy HH:mm");
    SimpleDateFormat datewithoutTime = new SimpleDateFormat("dd MMMM yyyy");
    SimpleDateFormat timewithoutDate = new SimpleDateFormat("HH:mm");

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_match_create_update);

        if (Build.VERSION.SDK_INT >= 21) {
            Window window = this.getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(this.getResources().getColor(R.color.black));
        }

        bundle = getIntent().getExtras();

        initWidgets();
    }

    private void initWidgets()
    {
        eventNameET = (EditText) findViewById(R.id.eventNameET);
        eventDateET = (EditText) findViewById(R.id.dateET);
        eventTimeET = (EditText) findViewById(R.id.timeET);
        eventLatitudeET = (EditText) findViewById(R.id.latitudeET);
        eventLongitudeET = (EditText) findViewById(R.id.longitudeET);
        eventScoreUserET = (EditText) findViewById(R.id.scoreUserET);
        eventScoreOpponentET = (EditText) findViewById(R.id.scoreOppET);

        // If we are in update mode, we fill the fields with the data of the match
        if(bundle.getString("type").equals("update")){
            Match eventToUpdate = Match.eventsList.get(bundle.getInt("match"));
            // Filling fields
            eventNameET.setText(eventToUpdate.getName());
            eventDateET.setText(datewithoutTime.format(eventToUpdate.getDate()));
            eventTimeET.setText(timewithoutDate.format(eventToUpdate.getDate()));
            eventLatitudeET.setText(String.valueOf(eventToUpdate.getLatitude()));
            eventLongitudeET.setText(String.valueOf(eventToUpdate.getLongitude()));
            eventScoreUserET.setText(eventToUpdate.getScoreUser());
            eventScoreOpponentET.setText(eventToUpdate.getScoreOpponent());
        }

    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void saveEventAction(View view) {
        try {
            // Name of event
            String eventName = eventNameET.getText().toString();

            // If latitude and longitude are not empty
            if(eventLatitudeET.getText().toString().length() != 0 && eventLongitudeET.getText().toString().length() != 0) {
                double latitude = Double.parseDouble(eventLatitudeET.getText().toString());
                double longitude = Double.parseDouble(eventLongitudeET.getText().toString());

                // If creation of event
                if(bundle.getString("type").equals("create")) {
                    Match newEvent = new Match(eventName, sdf.parse(eventDateET.getText().toString() + " " + eventTimeET.getText().toString()), latitude, longitude, eventScoreUserET.getText().toString(), eventScoreOpponentET.getText().toString());
                    Match.eventsList.add(newEvent);
                }else if(bundle.getString("type").equals("update")) {
                    Match eventToUpdate = Match.eventsList.get(bundle.getInt("match"));

                    // Putting new values
                    eventToUpdate.setName(eventName);
                    eventToUpdate.setDate(sdf.parse(eventDateET.getText().toString() + " " + eventTimeET.getText().toString()));
                    eventToUpdate.setLatitude(latitude);
                    eventToUpdate.setLongitude(longitude);
                    eventToUpdate.setScoreUser(eventScoreUserET.getText().toString());
                    eventToUpdate.setScoreOpponent(eventScoreOpponentET.getText().toString());

                    // Updating event
                    Match.eventsList.set(Match.eventsList.indexOf(eventToUpdate), eventToUpdate);
                }
            }
            finish();
        }catch (Exception e) {
            Toast.makeText(this, "Error: Error dans l'insertion des informations, vérifier le typage de vos données", Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }

    }
}