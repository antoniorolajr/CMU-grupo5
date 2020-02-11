package estg.ipp.rememberme.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import estg.ipp.rememberme.R;

public class FragmentUser extends Fragment {

    private static final String TAG = "FragmentUser";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_user_layout,container,false);

        Toolbar toolbar = view.findViewById(R.id.toolbar_user);
        toolbar.setTitle("Perfil do Utilizador");

        return view;
    }
}
