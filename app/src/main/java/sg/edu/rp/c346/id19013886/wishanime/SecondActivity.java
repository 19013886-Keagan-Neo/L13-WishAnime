package sg.edu.rp.c346.id19013886.wishanime;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RatingBar;

import java.util.ArrayList;

public class SecondActivity extends AppCompatActivity {

    ListView lv;
    ArrayList<Anime> animeList;
    int requestCode = 9;
    Button btn5Stars, btnShowAll, btnInsert;
    CustomAdapter caAnime;

    /*ArrayList<String> alStars;
    ArrayAdapter<String> aaSpn;
    Spinner spnStars;*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        setTitle(getTitle().toString() + "~" + getResources().getText(R.string.title_activity_second));

        lv = (ListView) this.findViewById(R.id.lv);
        btn5Stars = (Button) this.findViewById(R.id.btnShow5Stars);
        btnShowAll = (Button) this.findViewById(R.id.btnShowAll);
        btnInsert = (Button) this.findViewById(R.id.btnInsert);
        //spnStars = findViewById(R.id.spnStars);

        DBHelper dbh = new DBHelper(this);
        animeList = dbh.getAllAnimes();
        //alStars = dbh.getStars();
        dbh.close();

        /*aaSpn = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, alStars);
        spnStars.setAdapter(aaSpn);

        spnStars.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                DBHelper dbh = new DBHelper(SecondActivity.this);
                animeList.clear();
                animeList.addAll(dbh.getAllanimesByStars(Integer.valueOf(alStars.get(position))));
                aaSpn.notifyDataSetChanged();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });*/

        caAnime = new CustomAdapter(this, R.layout.row, animeList);
        lv.setAdapter(caAnime);

        btn5Stars.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DBHelper dbh = new DBHelper(SecondActivity.this);
                animeList.clear();
                animeList.addAll(dbh.getAllanimesByStars(5));
                caAnime.notifyDataSetChanged();
            }
        });

        btnShowAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DBHelper dbh = new DBHelper(SecondActivity.this);
                animeList.clear();
                animeList.addAll(dbh.getAllAnimes());
                caAnime.notifyDataSetChanged();
            }
        });

        btnInsert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                View viewDialog = inflater.inflate(R.layout.insert_anime,null);

                final EditText etTitle = viewDialog.findViewById(R.id.etTitle);
                final EditText etDesc = viewDialog.findViewById(R.id.etDesc);
                final EditText etSeason = viewDialog.findViewById(R.id.etSeason);
                final RatingBar rbStar = viewDialog.findViewById(R.id.rbStars);

                AlertDialog.Builder myBuilder = new AlertDialog.Builder(SecondActivity.this);
                myBuilder.setView(viewDialog);
                myBuilder.setTitle("Insert New Anime");
                myBuilder.setPositiveButton("Insert", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String title = etTitle.getText().toString().trim();
                        String desc = etDesc.getText().toString().trim();
                        String season = etSeason.getText().toString().trim();
                        int seasons = Integer.valueOf(season);
                        int rating = (int) rbStar.getRating();

                        DBHelper dbh = new DBHelper(SecondActivity.this);
                        dbh.insertAnime(title, desc, seasons, rating);
                        caAnime.notifyDataSetChanged();
                        animeList = dbh.getAllAnimes();
                        dbh.close();

                        caAnime=new CustomAdapter(SecondActivity.this, R.layout.row, animeList);
                        lv.setAdapter(caAnime);
                    }
                });

                myBuilder.setNegativeButton("CANCEL", null);
                AlertDialog myDialog = myBuilder.create();
                myDialog.show();
            }
        });

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent i = new Intent(SecondActivity.this, ThirdActivity.class);
                i.putExtra("anime", animeList.get(position));
                startActivity(i);
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        DBHelper dbh = new DBHelper(this);
        animeList.clear();
        animeList.addAll(dbh.getAllAnimes());
        dbh.close();
        caAnime.notifyDataSetChanged();

        /*alStars.clear();
        alStars.addAll(dbh.getStars());
        aaSpn.notifyDataSetChanged();*/

    }
}