package estg.ipp.rememberme.fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import com.google.firebase.auth.FirebaseAuth;

import estg.ipp.rememberme.R;
import estg.ipp.rememberme.activities.LoginActivity;

public class FragmentUser extends Fragment {

    private static final String TAG = "FragmentUser";


    FirebaseAuth mFirebaseAuth;
    private FirebaseAuth.AuthStateListener mAuthStateListener;
    Button btnLogout;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_user_layout,container,false);

        btnLogout = view.findViewById(R.id.buttonLogout);

        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                Intent intentoMain= new Intent(getContext(), LoginActivity.class);
                startActivity(intentoMain);
            }
        });

        Toolbar toolbar = view.findViewById(R.id.toolbar_user);
        toolbar.setTitle("Perfil do Utilizador");

        return view;
    }


}
