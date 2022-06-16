package fr.android.fcmetrics;

import androidx.appcompat.app.AppCompatActivity;

import android.location.Geocoder;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.util.Locale;

import fr.android.fcmetrics.modules.Match;

public class MatchDetailActivity extends AppCompatActivity {

    private String matchUuid;
    private TextView eventNameET;
    private TextView latitudeET;
    private TextView longitudeET;
    private TextView address;
    private TextView dateET;
    private TextView timeET;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_match_detail);

        try {
            printInfo();
        }catch (Exception e){
            e.printStackTrace();
        }
    }


    public void printInfo(){
        try {
            // Affiliate object variables to the views
            eventNameET = findViewById(R.id.eventNameET);
            latitudeET = findViewById(R.id.latitudeET);
            longitudeET = findViewById(R.id.longitudeET);
            address = findViewById(R.id.address);
            dateET = findViewById(R.id.dateET);
            timeET = findViewById(R.id.timeET);

            // Get the matchUuid from the intent
            Bundle bundle = getIntent().getExtras();
            matchUuid = bundle.getString("match");

            // Get the match object from the database
            Match match = Match.getMatch(matchUuid);

            // Geocode the address
            Geocoder geocoder = new Geocoder(this, Locale.getDefault());
            String addressString = geocoder.getFromLocation(match.getLatitude(), match.getLongitude(), 1).get(0).getAddressLine(0);

            // Print the match info 48.944799542579744, 2.542579210438741
            eventNameET.setText(match.getName());
            latitudeET.setText(String.valueOf(match.getLatitude()));
            longitudeET.setText(String.valueOf(match.getLongitude()));
            address.setText(addressString);
            String dateToString = match.getDate().getDate() + "/" + (match.getDate().getMonth() + 1) + "/" + (match.getDate().getYear() + 1900);
            dateET.setText(dateToString);
            timeET.setText(match.getDate().getHours() + "h" + match.getDate().getMinutes());
        }catch (Exception e){
            e.printStackTrace();
        }



    }


    public void backtoHome(View view) {
        finish();
    }
}