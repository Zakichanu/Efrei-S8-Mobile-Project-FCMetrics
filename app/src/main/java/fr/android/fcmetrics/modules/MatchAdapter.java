package fr.android.fcmetrics.modules;

import android.content.Context;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

import java.util.List;

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

        TextView eventCellTV = convertView.findViewById(R.id.matchCellTV);

        String eventTitle = event.getName() +" "+ CalendarUtils.formattedTime(event.getTime());
        eventCellTV.setText(eventTitle);
        return convertView;
    }
}