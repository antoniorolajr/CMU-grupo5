package estg.ipp.rememberme.asyncTasks;

import android.os.AsyncTask;
import android.util.Log;

import estg.ipp.rememberme.models.Medicamento;
import estg.ipp.rememberme.database.MedicamentoDao;

public class UpdateAsyncTask extends AsyncTask<Medicamento, Void, Void> {

    private static final String TAG = "UpdateAsyncTask";

    private MedicamentoDao mMedicamentoDao;

    public UpdateAsyncTask(MedicamentoDao dao){
        mMedicamentoDao = dao;
    }

    @Override
    protected Void doInBackground(Medicamento... medicamentos) {
        Log.d(TAG, "doInBackground: thread: "+ Thread.currentThread().getName());
        mMedicamentoDao.update(medicamentos);
        return null;
    }
}
