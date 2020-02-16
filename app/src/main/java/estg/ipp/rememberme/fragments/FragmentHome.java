package estg.ipp.rememberme.fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
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
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import com.google.firebase.auth.FirebaseAuth;

import estg.ipp.rememberme.R;
import estg.ipp.rememberme.activities.AtivityC;
import estg.ipp.rememberme.activities.Games_Activity;
import estg.ipp.rememberme.activities.NotificacaoActivity;
import estg.ipp.rememberme.activities.NutricaoActivity;
import estg.ipp.rememberme.activities.PaisesActivity;

public class FragmentHome extends Fragment {

    private static final String TAG = "FragmentHome";


    FirebaseAuth mFirebaseAuth;
    private FirebaseAuth.AuthStateListener mAuthStateListener;

    ListView listView;

    String mTitle[] = {"Clínicas" , "Alimentos" , "Jogos" , "Países", "Notificações"};
    String mDescription[] = {"Ver Clínicas perto de si" ,"Restrições Nutritivas", "Lapsos de Memória" ,
            "Recorde alguns países" , "Defina um alarme"};

    int images[] = {R.drawable.clinica, R.drawable.food, R.drawable.games, R.drawable.paises, R.drawable.notification};

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_home_layout,container,false);
        listView = view.findViewById(R.id.listViewHome);


        Toolbar toolbar = view.findViewById(R.id.toolbar_home);
        toolbar.setTitle("Home");

        //adapter
        MyAdapter adapter = new MyAdapter(getContext(), mTitle, mDescription, images);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(position == 0) {
                    Intent intent = new Intent(getContext(), AtivityC.class);
                    startActivity(intent);

                }
                if(position == 1) {
                    Intent intent = new Intent(getContext(),NutricaoActivity.class );
                    startActivity(intent);

                }
                if(position == 2) {
                    Intent intent = new Intent(getContext(), Games_Activity.class);
                    startActivity(intent);

                }
                if(position == 3) {
                    Intent intent = new Intent(getContext(), PaisesActivity.class);
                    startActivity(intent);
                }
                if(position == 4) {
                    Intent intent = new Intent(getContext(), NotificacaoActivity.class);
                    startActivity(intent);
                }
            }
        });

        return view;
    }

    //adapter
    class MyAdapter extends ArrayAdapter<String> {

        Context context;
        String rTitle[];
        String[] rDescription;
        int rImgs[];

        MyAdapter(Context c, String title[], String description[], int imgs[]) {

            super(c, R.layout.fragment_home_row_layout, R.id.textView1, title);
            this.context = c;
            this.rTitle = title;
            this.rDescription = description;
            this.rImgs = imgs;

        }

        @NonNull
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

            LayoutInflater layountInflater = (LayoutInflater) context.getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View row = layountInflater.inflate(R.layout.fragment_home_row_layout, parent, false);
            ImageView images = row.findViewById(R.id.image);
            TextView myTitle = row.findViewById(R.id.textView1);
            TextView myDescription = row.findViewById(R.id.textView2);

            //set resources on views
            images.setImageResource(rImgs[position]);
            myTitle.setText(rTitle[position]);
            myDescription.setText(rDescription[position]);

            return row;

        }
    }
}
