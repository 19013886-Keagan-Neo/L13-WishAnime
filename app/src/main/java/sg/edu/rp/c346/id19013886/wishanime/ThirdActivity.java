package sg.edu.rp.c346.id19013886.wishanime;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.Toast;

public class ThirdActivity extends AppCompatActivity {

    EditText etID, etTitle, etDesc, etSeason;
    Button btnCancel, btnUpdate, btnDelete;
    RatingBar rbStars;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_third);

        setTitle(getTitle().toString() + "~" + getResources().getText(R.string.title_activity_third));

        btnUpdate = (Button) findViewById(R.id.btnUpdate);
        btnDelete = (Button) findViewById(R.id.btnDelete);
        btnCancel = (Button) findViewById(R.id.btnCancel);
        etID = (EditText) findViewById(R.id.etID);
        etTitle = (EditText) findViewById(R.id.etTitle);
        etDesc = (EditText) findViewById(R.id.etDesc);
        etSeason = (EditText) findViewById(R.id.etSeason);
        rbStars =(RatingBar)findViewById(R.id.rbStars);

        Intent i = getIntent();
        final Anime currentAnime = (Anime) i.getSerializableExtra("anime");

        etID.setText(currentAnime.getId() + "");
        etTitle.setText(currentAnime.getTitle());
        etDesc.setText(currentAnime.getDescription());
        etSeason.setText(currentAnime.getSeason() + "");
        rbStars.setRating(currentAnime.getStars());

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DBHelper dbh = new DBHelper(ThirdActivity.this);
                currentAnime.setTitle(etTitle.getText().toString().trim());
                currentAnime.setDescription(etDesc.getText().toString().trim());
                int season = 0;
                try {
                    season = Integer.valueOf(etSeason.getText().toString().trim());
                } catch (Exception e){
                    Toast.makeText(ThirdActivity.this, "Invalid season", Toast.LENGTH_SHORT).show();
                    return;
                }
                currentAnime.setSeason(season);

                currentAnime.setStars((int) rbStars.getRating());

                int result = dbh.updateAnime(currentAnime);
                if (result > 0){
                    Toast.makeText(ThirdActivity.this, "Anime updated", Toast.LENGTH_SHORT).show();
                    Intent i = new Intent();
                    setResult(RESULT_OK);

                    finish();
                } else {
                    Toast.makeText(ThirdActivity.this, "Update failed", Toast.LENGTH_SHORT).show();
                }
            }
        });

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder myBuilder = new AlertDialog.Builder(ThirdActivity.this);
                myBuilder.setTitle("Danger");
                myBuilder.setMessage("Are you sure you want to delete the island\n" + currentAnime.getTitle());
                myBuilder.setCancelable(false);

                myBuilder.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        DBHelper dbh = new DBHelper(ThirdActivity.this);
                        int result = dbh.deleteAnime(currentAnime.getId());
                        if (result > 0){
                            Toast.makeText(ThirdActivity.this, "Anime deleted", Toast.LENGTH_SHORT).show();
                            Intent i = new Intent();
                            setResult(RESULT_OK);
                            finish();
                        } else {
                            Toast.makeText(ThirdActivity.this, "Delete failed", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

                myBuilder.setNegativeButton("Cancel", null);
                AlertDialog myDialog = myBuilder.create();
                myDialog.show();
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder myBuilder = new AlertDialog.Builder(ThirdActivity.this);
                myBuilder.setTitle("Danger");
                myBuilder.setMessage("Are you sure you want to discard the changes?");
                myBuilder.setCancelable(false);

                myBuilder.setPositiveButton("Discard", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                });

                myBuilder.setNegativeButton("Do Not Discard", null);
                AlertDialog myDialog = myBuilder.create();
                myDialog.show();
            }
        });

    }
}