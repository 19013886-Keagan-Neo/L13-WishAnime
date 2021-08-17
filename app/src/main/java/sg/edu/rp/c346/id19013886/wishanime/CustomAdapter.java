package sg.edu.rp.c346.id19013886.wishanime;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import java.util.ArrayList;

public class CustomAdapter extends ArrayAdapter {

    Context parent_context;
    int layout_id;
    ArrayList<Anime> animeList;

    public CustomAdapter(Context context, int resource, ArrayList<Anime> objects) {
        super(context, resource, objects);

        parent_context = context;
        layout_id = resource;
        animeList = objects;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        // Obtain the LayoutInflater object
        LayoutInflater inflater = (LayoutInflater)
                parent_context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        // "Inflate" the View for each row
        View rowView = inflater.inflate(layout_id, parent, false);

        // Obtain the UI components and do the necessary binding
        TextView tvTitle = rowView.findViewById(R.id.tvTitle);
        TextView tvDesc = rowView.findViewById(R.id.tvDesc);
        TextView tvSeason = rowView.findViewById(R.id.tvSeason);
        RatingBar rbStar = rowView.findViewById(R.id.rbStars);
        ImageView imgWow = rowView.findViewById(R.id.ivWow);

        // Obtain the Android Version information based on the position
        Anime currentAnime = animeList.get(position);

        // Set values to the TextView to display the corresponding information
        tvTitle.setText(currentAnime.getTitle());
        tvDesc.setText(currentAnime.getDescription());
        tvSeason.setText(String.valueOf(currentAnime.getSeason()));
        rbStar.setRating(currentAnime.getStars());
        imgWow.setImageResource(R.drawable.wow_image);

        if (currentAnime.getSeason() >= 10) {
            imgWow.setVisibility(View.VISIBLE);
        } else {
            imgWow.setVisibility(View.INVISIBLE);
        }

        return rowView;
    }

}
