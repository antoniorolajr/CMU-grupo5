package estg.ipp.rememberme.fragments;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import com.google.firebase.auth.FirebaseAuth;

import java.util.Objects;

import estg.ipp.rememberme.R;
import estg.ipp.rememberme.activities.LoginActivity;

public class FragmentUser extends Fragment {

    private static final String TAG = "FragmentUser";
    public static final String SHARED_PREFERENCES = "sharedPreferences";
    public static final String TEXT = "text";
    public static final String TEXT2 = "text2";
    public static final String TEXT3 = "text3";
    public static final String TEXT4 = "text4";
    public static final String SWITCH1 = "switch1";

    private String textNome, textGenero, textIdade, textContacto;
    private boolean switchOnOff;

    //FirebaseAuth mFirebaseAuth;
    //private FirebaseAuth.AuthStateListener mAuthStateListener;

    private TextView textViewNome,textViewGenero,textViewIdade,textViewContacto;
    private EditText editTextDefineNome, editTextDefineGenero, editTextDefineIdade, editTextDefineContacto;
    private Switch switch1;
    private Button aply_button,save_button,btnLogout ;



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_user_layout,container,false);

        textViewNome = view.findViewById(R.id.textViewNome);
        textViewGenero = view.findViewById(R.id.textViewGenero);
        textViewIdade = view.findViewById(R.id.textViewIdade);
        textViewContacto = view.findViewById(R.id.textViewContacto);

        editTextDefineNome = view.findViewById(R.id.editTextDefineNome);
        editTextDefineGenero = view.findViewById(R.id.editTextDefineGenero);
        editTextDefineIdade = view.findViewById(R.id.editTextDefineIdade);
        editTextDefineContacto = view.findViewById(R.id.editTextDefineContacto);

        aply_button = view.findViewById(R.id.aply_button);
        save_button = view.findViewById(R.id.save_button);
        switch1 = view.findViewById(R.id.switch1);
        btnLogout = view.findViewById(R.id.buttonLogout);

        Toolbar toolbar = view.findViewById(R.id.toolbar_user);
        toolbar.setTitle("Perfil do Utilizador");

        aply_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                textViewNome.setText(editTextDefineNome.getText().toString());
                textViewGenero.setText(editTextDefineGenero.getText().toString());
                textViewIdade.setText(editTextDefineIdade.getText().toString());
                textViewContacto.setText(editTextDefineContacto.getText().toString());
            }
        });

        save_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveData();
            }
        });


        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                Intent intentoMain= new Intent(getContext(), LoginActivity.class);
                startActivity(intentoMain);
            }
        });

        loadData();
        updateViews();

        return view;
    }

    public void saveData(){
        SharedPreferences sharedPreferences = Objects.requireNonNull(getActivity()).getSharedPreferences(SHARED_PREFERENCES, Context.MODE_PRIVATE);
        @SuppressLint("CommitPrefEdits") SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putString(TEXT, textViewNome.getText().toString());
        editor.putString(TEXT2, textViewGenero.getText().toString());
        editor.putString(TEXT3, textViewIdade.getText().toString());
        editor.putString(TEXT4, textViewContacto.getText().toString());

        editor.putBoolean(SWITCH1, switch1.isChecked());

        editor.apply();

        Toast.makeText(getContext(), "Data guardada com sucesso", Toast.LENGTH_SHORT).show();

    }

    public void loadData(){
        SharedPreferences sharedPreferences = Objects.requireNonNull(getActivity()).getSharedPreferences(SHARED_PREFERENCES, Context.MODE_PRIVATE);
        textNome = sharedPreferences.getString(TEXT, "");
        textGenero = sharedPreferences.getString(TEXT2, "");
        textIdade = sharedPreferences.getString(TEXT3, "");
        textContacto = sharedPreferences.getString(TEXT4, "");
        switchOnOff = sharedPreferences.getBoolean(SWITCH1, false);


    }

    public void updateViews(){
        textViewNome.setText(textNome);
        textViewGenero.setText(textGenero);
        textViewIdade.setText(textIdade);
        textViewContacto.setText(textContacto);
        switch1.setChecked(switchOnOff);
    }


}
