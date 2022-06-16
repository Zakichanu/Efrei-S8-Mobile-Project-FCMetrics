package fr.android.fcmetrics;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalTime;

import fr.android.fcmetrics.modules.CalendarUtils;
import fr.android.fcmetrics.modules.Match;

public class MatchCreateAndUpdateActivity extends AppCompatActivity
{
    private EditText eventNameET;
    private EditText eventDateET;
    private EditText eventTimeET;
    private EditText eventLatitudeET;
    private EditText eventLongitudeET;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_match);

        if (Build.VERSION.SDK_INT >= 21) {
            Window window = this.getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(this.getResources().getColor(R.color.black));
        }

        initWidgets();
    }

    private void initWidgets()
    {
        eventNameET = (EditText) findViewById(R.id.eventNameET);
        eventDateET = (EditText) findViewById(R.id.dateET);
        eventTimeET = (EditText) findViewById(R.id.timeET);
        eventLatitudeET = (EditText) findViewById(R.id.latitudeET);
        eventLongitudeET = (EditText) findViewById(R.id.longitudeET);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void saveEventAction(View view) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("dd MMMM yyyy HH:mm");
            String eventName = eventNameET.getText().toString();
            if(eventLatitudeET.getText().toString().length() != 0 && eventLongitudeET.getText().toString().length() != 0) {
                double latitude = Double.parseDouble(eventLatitudeET.getText().toString());
                double longitude = Double.parseDouble(eventLongitudeET.getText().toString());
                Match newEvent = new Match(eventName, sdf.parse(eventDateET.getText().toString() + " " + eventTimeET.getText().toString()), latitude, longitude);
                Match.eventsList.add(newEvent);
            }
            finish();
        }catch (Exception e) {
            Toast.makeText(this, "Error: Error dans l'insertion des informations, vérifier le typage de vos données", Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }

    }
}