package estg.ipp.rememberme.activities;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import java.util.Arrays;
import java.util.Collections;

import estg.ipp.rememberme.R;
import estg.ipp.rememberme.fragments.FragmentHome;

public class Games_Activity extends AppCompatActivity {

    ListView listView;

    String mTitle[] = {"Jogo das cartas" , "Jogo Aritmético"};
    String mDescription[] = {"Memorize as cartas" ,"Resolva operações aritméticas"};

    int images[] = {R.drawable.memory, R.drawable.aritmetic};

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_games_);

        listView = findViewById(R.id.listViewGames);

        Toolbar toolbar = findViewById(R.id.toolbar_games);
        toolbar.setTitle("Jogos");

        //adapter
        MyAdapter adapter = new MyAdapter(this, mTitle, mDescription, images);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(position == 0) {
                    Intent intent = new Intent(Games_Activity.this, CardsGameActivity.class);
                    startActivity(intent);

                }
                if(position == 1) {
                    Intent intent = new Intent(Games_Activity.this, AritmeticGameActivity.class);
                    startActivity(intent);

                }

            }
        });

    }

    //adapter
    class MyAdapter extends ArrayAdapter<String> {

        Context context;
        String rTitle[];
        String[] rDescription;
        int rImgs[];

        MyAdapter(Context c, String title[], String description[], int imgs[]) {

            super(c, R.layout.games_row_layout, R.id.textViewGamesRow, title);
            this.context = c;
            this.rTitle = title;
            this.rDescription = description;
            this.rImgs = imgs;

        }

        @NonNull
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

            LayoutInflater layountInflater = (LayoutInflater) context.getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View row = layountInflater.inflate(R.layout.games_row_layout, parent, false);
            ImageView images = row.findViewById(R.id.imageGamesRow);
            TextView myTitle = row.findViewById(R.id.textViewGamesRow);
            TextView myDescription = row.findViewById(R.id.textViewGamesRow2);

            //set resources on views
            images.setImageResource(rImgs[position]);
            myTitle.setText(rTitle[position]);
            myDescription.setText(rDescription[position]);

            return row;

        }
    }


}
