package estg.ipp.rememberme.asyncTasks;

import android.os.AsyncTask;
import android.util.Log;

import estg.ipp.rememberme.models.Medicamento;
import estg.ipp.rememberme.database.MedicamentoDao;

//executa a tarefa em background e depois é destruida
public class InsertAsyncTask extends AsyncTask<Medicamento, Void, Void> {

    private static final String TAG = "InsertAsyncTask";

    //global medicamentoDao obj
    private MedicamentoDao mMedicamentoDao;

    public InsertAsyncTask(MedicamentoDao dao){
        mMedicamentoDao = dao;
    }

    @Override
    protected Void doInBackground(Medicamento... medicamentos) {
        Log.d(TAG, "doInBackground: thread: "+ Thread.currentThread().getName());
        mMedicamentoDao.insertMedicamentos(medicamentos);
        return null;
    }
}
