package estg.ipp.rememberme.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import com.google.firebase.auth.FirebaseAuth;

import estg.ipp.rememberme.R;
import estg.ipp.rememberme.activities.LoginActivity;

public class FragmentHome extends Fragment {

    private static final String TAG = "FragmentHome";

    Button btnLogout;
    FirebaseAuth mFirebaseAuth;
    private FirebaseAuth.AuthStateListener mAuthStateListener;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_home_layout,container,false);
        btnLogout = view.findViewById(R.id.buttonLogout);

        Toolbar toolbar = view.findViewById(R.id.toolbar_home);
        toolbar.setTitle("Home");

        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                Intent intentoMain= new Intent(getContext(), LoginActivity.class);
                startActivity(intentoMain);
            }
        });

        return view;
    }
}
