package estg.ipp.rememberme.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;

import estg.ipp.rememberme.R;

public class ClinicasActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clinicas);

        Toolbar toolbar = findViewById(R.id.toolbar_clinicas);
        toolbar.setTitle("Clinicas");
    }
}
