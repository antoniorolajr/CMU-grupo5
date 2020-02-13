package estg.ipp.rememberme.asyncTasks;

import android.os.AsyncTask;
import android.util.Log;

import estg.ipp.rememberme.models.Medicamento;
import estg.ipp.rememberme.database.MedicamentoDao;

public class DeleteAsyncTask extends AsyncTask<Medicamento, Void, Void> {

    private static final String TAG = "DeleteAsyncTask";

    private MedicamentoDao mMedicamentoDao;

    public DeleteAsyncTask(MedicamentoDao dao){
        mMedicamentoDao = dao;
    }

    @Override
    protected Void doInBackground(Medicamento... medicamentos) {
        Log.d(TAG, "doInBackground: thread: "+ Thread.currentThread().getName());
        mMedicamentoDao.delete(medicamentos);
        return null;
    }
}
