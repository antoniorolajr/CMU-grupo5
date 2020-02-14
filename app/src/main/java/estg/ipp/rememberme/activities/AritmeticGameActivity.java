package estg.ipp.rememberme.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import estg.ipp.rememberme.R;

public class AritmeticGameActivity extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aritmetic_game);

        Toolbar toolbar = findViewById(R.id.toolbar_aritmetic_game);
        toolbar.setTitle("Jogo Aritmético");

        Button buttonStart1 = findViewById(R.id.button_start1);
        buttonStart1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startNivel1();
            }
        });
    }

    private void startNivel1(){
        Intent intent = new Intent(AritmeticGameActivity.this, AritmeticGame1Activity.class);
        startActivity(intent);
    }
}
