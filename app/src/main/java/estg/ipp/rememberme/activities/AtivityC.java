package estg.ipp.rememberme.activities;

import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import estg.ipp.rememberme.R;

public class AtivityC extends AppCompatActivity implements ActivityC {

    private static final String TAG = "Activity";
    private TextView mToolbarTitle;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clinicas);
        mToolbarTitle = findViewById(R.id.toolbar_tittle);

        init();

    }

    private void init() {

        FragmentSelect fragment = new FragmentSelect();
        doFragmentTransaction(fragment, "Procurar clínicas", false, "");
    }

    private void doFragmentTransaction(Fragment fragment, String fragment_selector, boolean b, String s) {

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

        if (!s.equals("")) {
            Bundle bundle = new Bundle();
            bundle.putString("selector", s);
            fragment.setArguments(bundle);
        }

        transaction.replace(R.id.main_container, fragment, fragment_selector);

        if (b) {
            transaction.addToBackStack(fragment_selector);
        }
        transaction.commit();
    }

    @Override
    public void setToolbarTitle(String fragmentTag) {
        mToolbarTitle.setText(fragmentTag);
    }

    @Override
    public void inflateFragment(String fragmentTag, String message) {
        if (fragmentTag.equals("fragment")) {

            ClinicasFragment fragment = new ClinicasFragment();
            doFragmentTransaction(fragment, fragmentTag, true, message);
        }

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        mToolbarTitle.setText("Procurar clinicas");
    }
}
