package estg.ipp.rememberme.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import estg.ipp.rememberme.R;
import estg.ipp.rememberme.activities.MedicamentoActivity;
import estg.ipp.rememberme.adapters.MedicamentosRecyclerAdapter;
import estg.ipp.rememberme.database.Repository;
import estg.ipp.rememberme.models.Medicamento;

public class FragmentMedicamentos extends Fragment implements MedicamentosRecyclerAdapter.OnMedicamentoListener, View.OnClickListener{

    private static final String TAG="FragmentMedicamentos";

    //ui components
    private RecyclerView mRecyclerView;

    //variables
    private ArrayList<Medicamento> mMedicamentos = new ArrayList<>();
    private MedicamentosRecyclerAdapter mMedicamentosRecyclerAdapter;
    private Repository mRepository;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_medicamentos_layout,container,false);

        Toolbar toolbar = view.findViewById(R.id.toolbar1);
        toolbar.setTitle("Medicamentos");

        view.findViewById(R.id.fab).setOnClickListener(this);

        mRepository = new Repository(getContext());

        mRecyclerView = view.findViewById(R.id.recyclerView);

        initRecyclerView();
        //insertFakeMeds();
        retrieveMedicamentos();

        return view;
    }

    //livedata a funcionar, qd observa mudanças no obj livedata, vai disparar este metodo
    //attach o obeserver ao livedata obj que retorna da db, sempre que ha alteração na db o observer chama o onChanged
    // e limpa, adiciona e informa a recycler view adapter que os dados foram alterados
    //e feito em background se nao a app crasha
    private void retrieveMedicamentos() {
        mRepository.retrieveMedicamentoTask().observe(this, new Observer<List<Medicamento>>() {
            @Override
            public void onChanged(@Nullable List<Medicamento> medicamentos) {
                if(mMedicamentos.size() > 0){
                    mMedicamentos.clear();
                }
                if(medicamentos != null){
                    mMedicamentos.addAll(medicamentos);
                }
                mMedicamentosRecyclerAdapter.notifyDataSetChanged();
            }
        });
    }


    private void initRecyclerView(){
        LinearLayoutManager linerLayoutManager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(linerLayoutManager);
        //swipe to delete
        new ItemTouchHelper(itemTouchHelperCallback).attachToRecyclerView(mRecyclerView);
        mMedicamentosRecyclerAdapter = new MedicamentosRecyclerAdapter(mMedicamentos,this);
        mRecyclerView.setAdapter(mMedicamentosRecyclerAdapter);

    }

    private void insertFakeMeds() {
        for (int i = 0; i < 7; i++) {
            Medicamento medicamento = new Medicamento();
            medicamento.setNome_medicamento("Nome #" + i);
            medicamento.setDescricao_medicamento("Descrição #: " + i);
            medicamento.setData_registo_medicamento("10 Fevereiro 2020");
            medicamento.setStock("Stock #: " + i);
            medicamento.setLocal_da_toma("Local: Felgueiras" +i);
            medicamento.setHora_da_toma("Hora: 12:3" +i);

            mMedicamentos.add(medicamento);
        }
        mMedicamentosRecyclerAdapter.notifyDataSetChanged();
    }

    @Override
    public void onMedicamentoClick(int position) {
        Log.d(TAG, "onMedicamentoClick: Click no medicamento  "+position);
        Intent intent = new Intent(getContext(), MedicamentoActivity.class);
        intent.putExtra("selected_med", mMedicamentos.get(position));
        startActivity(intent);
    }

    @Override
    public void onClick(View view) {
        Intent intent = new Intent(getContext(), MedicamentoActivity.class);
        startActivity(intent);
    }

    private void deleteMedicamento(Medicamento medicamento) {
        mMedicamentos.remove(medicamento);
        mMedicamentosRecyclerAdapter.notifyDataSetChanged();

        mRepository.deleteMedicamento(medicamento);
    }


    private ItemTouchHelper.SimpleCallback itemTouchHelperCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT) {
        @Override
        public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
            return false;
        }

        @Override
        public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
            deleteMedicamento(mMedicamentos.get(viewHolder.getAdapterPosition()));

        }
    };
}
