package fr.android.fcmetrics;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicReference;

import static fr.android.fcmetrics.modules.CalendarUtils.daysInWeekArray;
import static fr.android.fcmetrics.modules.CalendarUtils.monthYearFromDate;
import static fr.android.fcmetrics.modules.Controller.getUserById;

import com.google.gson.Gson;

import de.hdodenhof.circleimageview.CircleImageView;
import fr.android.fcmetrics.modules.CalendarAdapter;
import fr.android.fcmetrics.modules.CalendarUtils;
import fr.android.fcmetrics.modules.Match;
import fr.android.fcmetrics.modules.MatchAdapter;
import fr.android.fcmetrics.modules.UserEntity;

public class HomeActivity extends AppCompatActivity implements CalendarAdapter.OnItemListener {

    private TextView monthYearText;
    private RecyclerView calendarRecyclerView;
    private ListView eventListView;
    private UserEntity userConnected;


    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        Window window = this.getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.setStatusBarColor(this.getResources().getColor(R.color.black));
        CalendarUtils.selectedDate = LocalDate.now();

        // Init of widgets
        initWidgets();
        setWeekView();

        // Toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setTitle("");

        // Retrieve user Information
        Bundle bundle = getIntent().getExtras();
        String userJson = bundle.getString("userId");
        try {
            getUserInformation(userJson);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Récupération des champs de la toolbar
        CircleImageView pdp = findViewById(R.id.pdp);
        TextView pseudonyme = findViewById(R.id.pseudonyme);

        pseudonyme.setText("Welcome Back " + userConnected.getName());
        pdp.setImageResource(R.drawable.ic_imageonline_co_transparentimage);

    }

    private void initWidgets()
    {
        calendarRecyclerView = findViewById(R.id.calendarRecyclerView);
        monthYearText = findViewById(R.id.monthYearTV);
        eventListView = findViewById(R.id.eventListView);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void setWeekView()
    {
        monthYearText.setText(monthYearFromDate(CalendarUtils.selectedDate));
        ArrayList<LocalDate> days = daysInWeekArray(CalendarUtils.selectedDate);

        CalendarAdapter calendarAdapter = new CalendarAdapter(days, this);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getApplicationContext(), 7);
        calendarRecyclerView.setLayoutManager(layoutManager);
        calendarRecyclerView.setAdapter(calendarAdapter);
        setEventAdpater();
    }


    @RequiresApi(api = Build.VERSION_CODES.O)
    public void previousWeekAction(View view)
    {
        CalendarUtils.selectedDate = CalendarUtils.selectedDate.minusWeeks(1);
        setWeekView();
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void nextWeekAction(View view)
    {
        CalendarUtils.selectedDate = CalendarUtils.selectedDate.plusWeeks(1);
        setWeekView();
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onItemClick(int position, LocalDate date)
    {
        CalendarUtils.selectedDate = date;
        setWeekView();
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onResume()
    {
        super.onResume();
        setEventAdpater();
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void setEventAdpater()
    {
        ArrayList<Match> dailyEvents = Match.eventsForDate(java.sql.Date.valueOf(CalendarUtils.selectedDate.toString()));
        MatchAdapter eventAdapter = new MatchAdapter(getApplicationContext(), dailyEvents);
        eventListView.setAdapter(eventAdapter);
    }

    public void newEventAction(View view)
    {
        Intent intent = new Intent(this, MatchCreateAndUpdateActivity.class);
        intent.putExtra("type", "create");
        startActivity(intent);
    }

    public void getUserInformation(String idUser) throws InterruptedException {
        Runnable runnable = () -> {
            try{
                // Requesting the user to the db
                String getUser = getUserById(idUser);

                // Verifying the information recieved from the api
                if(!getUser.equals("User doesn't exists")){
                    Gson gson = new Gson();

                    // Parsing the json
                    userConnected = gson.fromJson(getUser, UserEntity.class);
                    Log.i("user", userConnected.getName());
                }else{
                    Log.i("result", getUser);
                    Toast.makeText(HomeActivity.this, "Id incorrect", Toast.LENGTH_LONG).show();
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        };
        Thread callApi = new Thread(runnable);
        callApi.start();
        callApi.join();

    }
}