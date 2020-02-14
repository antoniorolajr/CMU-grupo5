package estg.ipp.rememberme.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;

import java.util.ArrayList;

import estg.ipp.rememberme.R;
import estg.ipp.rememberme.adapters.RecyclerViewPaisesAdapter;

public class PaisesActivity extends AppCompatActivity {

    private static final String TAG = "Paises_Activity";

    //vars
    private ArrayList<String> mNames = new ArrayList<>();
    private ArrayList<String> mImageUrls = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_paises);

        Toolbar toolbar = findViewById(R.id.toolbar_paises);
        toolbar.setTitle("Lista de Países");

        initImageBitMaps();
    }
    private void initImageBitMaps() {
        Log.d(TAG, "initImageBitMaps: preparing bitmaps.");

        mImageUrls.add("https://www.pngfind.com/pngs/m/65-656737_germany-flag-icon-png-germany-flag-icon-flat.png");
        mNames.add("Alemanha");

        mImageUrls.add("https://www.pngfind.com/pngs/m/105-1056035_argentina-png-argentina-flag-icon-png-transparent-png.png");
        mNames.add("Argentina");

        mImageUrls.add("https://www.pngfind.com/pngs/m/243-2431847_brilliant-australian-get-fucked-commentary-on-tim-australia.png");
        mNames.add("Austrália");

        mImageUrls.add("https://www.pngfind.com/pngs/m/637-6370573_ultra-flag-high-res-1280x960-bandera-de-belgica.png");
        mNames.add("Belgica");

        mImageUrls.add("https://www.pngfind.com/pngs/m/207-2074199_bandeira-do-brasil-brazil-flag-hd-png-download.png");
        mNames.add("Brasil");

        mImageUrls.add("https://www.pngfind.com/pngs/m/637-6370467_flag-of-croatia-national-flag-flag-of-belgium.png");
        mNames.add("Croácia");

        mImageUrls.add("https://www.pngfind.com/pngs/m/609-6093891_netherlands-flag-hd-free-download-netherlands-flag-2017.png");
        mNames.add("Holanda");

        mImageUrls.add("https://www.pngfind.com/pngs/m/486-4861663_england-coat-of-arms-northern-ireland-barbados-wedding.png");
        mNames.add("Inglaterra");

        mImageUrls.add("https://www.pngfind.com/pngs/m/603-6035289_portugal-green-flag-nationality-portugal-flag-hd-png.png");
        mNames.add("Portugal");

        mImageUrls.add("https://www.pngfind.com/pngs/m/638-6382553_download-svg-download-png-bandera-uruguaya-transparent-png.png");
        mNames.add("Uruguay");

        initRecyclerView();
    }

    private void initRecyclerView(){
        Log.d(TAG, "initRecyclerView: init recyclerView.");

        RecyclerView recyclerView = findViewById(R.id.recycler_view_paises);

        RecyclerViewPaisesAdapter adapter = new RecyclerViewPaisesAdapter(this, mNames,mImageUrls);

        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

    }
}
