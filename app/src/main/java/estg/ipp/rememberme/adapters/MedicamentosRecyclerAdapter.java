package estg.ipp.rememberme.adapters;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import estg.ipp.rememberme.R;
import estg.ipp.rememberme.models.Medicamento;
import estg.ipp.rememberme.util.Utility;

public class MedicamentosRecyclerAdapter extends RecyclerView.Adapter<MedicamentosRecyclerAdapter.ViewHolder>{

    private static final String TAG = "MedicamentosRecyclerAda";


    private ArrayList<Medicamento> mMedicamentos = new ArrayList<>();
    private OnMedicamentoListener mOnMedicamentoListener;

    public MedicamentosRecyclerAdapter(ArrayList<Medicamento> medicamentos, OnMedicamentoListener onMedicamentoListener) {

        this.mMedicamentos = medicamentos;
        this.mOnMedicamentoListener = onMedicamentoListener;

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.layout_1medicamento,viewGroup,false);
        return new ViewHolder(view, mOnMedicamentoListener);
    }


    //se tiver 4 medicamentos na lista ,onBindViewHolder vai ser chamado 4 vezes
    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {

        /*
        try {

            String month = mMedicamentos.get(i).getData_registo_medicamento().substring(0,2);
            month = Utility.getMonthFromNumber(month);

            String year = mMedicamentos.get(i).getData_registo_medicamento().substring(3);
            String date = month + " " + year;



        }catch(NullPointerException e){
            Log.e(TAG, "onBindViewHolder: NullPointerException"+ e.getMessage() );
        }
        */

        viewHolder.data_registo_medicamento.setText(mMedicamentos.get(i).getData_registo_medicamento());
        viewHolder.nome_medicamento.setText(mMedicamentos.get(i).getNome_medicamento());
        viewHolder.descricao_medicamento.setText(mMedicamentos.get(i).getDescricao_medicamento());
        //viewHolder.stock.setText(mMedicamentos.get(i).getStock());
        //viewHolder.local.setText(mMedicamentos.get(i).getLocal_da_toma());
        //viewHolder.hora.setText(mMedicamentos.get(i).getHora_da_toma());



    }

    @Override
    public int getItemCount() {
        return mMedicamentos.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {


        private TextView nome_medicamento, data_registo_medicamento, descricao_medicamento ;
        OnMedicamentoListener onMedicamentoListener;

        public ViewHolder(@NonNull View itemView , OnMedicamentoListener onMedicamentoListener) {
            super(itemView);
            nome_medicamento = itemView.findViewById(R.id.nome_medicamento);
            data_registo_medicamento = itemView.findViewById(R.id.data_registo);
            descricao_medicamento = itemView.findViewById(R.id.descricao_medicamento);
            //stock = itemView.findViewById(R.id.stock_medicamento);
            //local = itemView.findViewById(R.id.local_medicamento);
            //hora = itemView.findViewById(R.id.hora_medicamento);

            this.onMedicamentoListener = onMedicamentoListener;

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            onMedicamentoListener.onMedicamentoClick(getAdapterPosition());
        }

    }

    //interface para ao clickar mandar para outra atividade
    public interface OnMedicamentoListener{
        void onMedicamentoClick(int position);
    }

}
