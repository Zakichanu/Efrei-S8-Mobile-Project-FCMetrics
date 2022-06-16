package fr.android.fcmetrics.modules;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Parcelable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

import java.io.Serializable;
import java.util.List;

import fr.android.fcmetrics.MatchDetailActivity;
import fr.android.fcmetrics.R;


public class MatchAdapter extends ArrayAdapter<Match>
{
    public MatchAdapter(@NonNull Context context, List<Match> events)
    {
        super(context, 0, events);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent)
    {
        Match event = getItem(position);

        if (convertView == null)
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.celulle_match, parent, false);

        TextView matchCellTV = convertView.findViewById(R.id.matchCellTV);
        RelativeLayout matchCellRL = convertView.findViewById(R.id.matchCellRL);
        matchCellRL.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(getContext(), MatchDetailActivity.class);
                // Passing data through intent
                intent.putExtra("match", event.getUuid());
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                getContext().startActivity(intent);
            }
        });

        String eventTitle = event.getName() +" ⌚️ : "+ event.getDate().getHours() + "h" + event.getDate().getMinutes() + " ⚽️ : " + event.getScoreUser() + " - " + event.getScoreOpponent();
        Log.i("eventTitle", eventTitle);
        matchCellTV.setText(eventTitle);
        return convertView;
    }
}