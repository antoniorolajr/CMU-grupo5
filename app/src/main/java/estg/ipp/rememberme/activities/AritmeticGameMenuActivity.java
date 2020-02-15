package estg.ipp.rememberme.activities;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import estg.ipp.rememberme.R;
import estg.ipp.rememberme.models.Question;

public class AritmeticGameMenuActivity extends AppCompatActivity {
    private static final int REQUEST_CODE_QUIZ = 1;
    public static final String EXTRA_DIFICULTY = "extraDificulty";

    public static final String SHARED_PREFERENCES = "sharedPreferences";
    public static final String KEY_HIGHSCORE= "keyHighScore";

    private TextView textViewHighScore;

    private Spinner spinnerDificulty;

    private int highScore;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aritmetic_game_menu);

        spinnerDificulty = findViewById(R.id.spinner_dificulty);
        String[] dificultyLevels = Question.getAllDificultyLevels();

        ArrayAdapter<String> adapterDificulty = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, dificultyLevels);
        adapterDificulty.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerDificulty.setAdapter(adapterDificulty);

        textViewHighScore = findViewById(R.id.text_view_highscore);
        loadHighScore();

        Toolbar toolbar = findViewById(R.id.toolbar_aritmetic_game_menu);
        toolbar.setTitle("Menu do jogo aritmético");

        Button buttonStart1 = findViewById(R.id.button_start);
        buttonStart1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startGame();
            }
        });
    }

    private void startGame(){
        String dificulty = spinnerDificulty.getSelectedItem().toString();
        Intent intent = new Intent(AritmeticGameMenuActivity.this, AritmeticGameActivity.class);
        intent.putExtra(EXTRA_DIFICULTY, dificulty);
        startActivityForResult(intent, REQUEST_CODE_QUIZ);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == REQUEST_CODE_QUIZ){
            if(resultCode == RESULT_OK){
                int score = data.getIntExtra(AritmeticGameActivity.EXTRA_SCORE,0);
                if (score > highScore) {
                    updateHighScore(score);
                }
            }
        }
    }

    private void loadHighScore(){

        SharedPreferences prefs = getSharedPreferences(SHARED_PREFERENCES, MODE_PRIVATE);
        highScore = prefs.getInt(KEY_HIGHSCORE,0);
        textViewHighScore.setText("Pontuação máxima obtida: " + highScore);

    }

    private void updateHighScore(int highscoreNew){
        highScore = highscoreNew;
        textViewHighScore.setText("Pontuação máxima obtida: " +highScore);

        SharedPreferences prefs = getSharedPreferences(SHARED_PREFERENCES, MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putInt(KEY_HIGHSCORE, highScore);
        editor.apply();
    }
}
