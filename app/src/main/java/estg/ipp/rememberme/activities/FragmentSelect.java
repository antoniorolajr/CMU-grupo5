package estg.ipp.rememberme.activities;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import estg.ipp.rememberme.R;

public class FragmentSelect extends Fragment implements View.OnClickListener {

    private static final String TAG = "SelectorFragment";

    private Button botaoClinica;
    private EditText textotexto;
    private ActivityC mActivity;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivity.setToolbarTitle(getTag());
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_selector, container, false);

        textotexto = view.findViewById(R.id.message);
        botaoClinica = view.findViewById(R.id.btn_fragment_clinica);
        botaoClinica.setOnClickListener(this);

        return view;
    }


    @Override
    public void onClick(View view) {
        String message = textotexto.getText().toString();

        switch (view.getId()) {
            case R.id.btn_fragment_clinica: {
                mActivity.inflateFragment("fragment", message);
                break;
            }
        }
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mActivity = (ActivityC) getActivity();
    }
}
