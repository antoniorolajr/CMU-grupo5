package estg.ipp.rememberme.database;

import android.content.Context;

import androidx.lifecycle.LiveData;

import java.util.List;

import estg.ipp.rememberme.asyncTasks.DeleteAsyncTask;
import estg.ipp.rememberme.asyncTasks.InsertAsyncTask;
import estg.ipp.rememberme.asyncTasks.UpdateAsyncTask;
import estg.ipp.rememberme.models.Medicamento;



public class Repository {

    private RememberDatabase mRememberDatabase;

    public Repository(Context context) {
        mRememberDatabase = RememberDatabase.getInstance(context);
    }

    public void insertMedicamento(Medicamento medicamento){
        new InsertAsyncTask(mRememberDatabase.getMedicamentoDao()).execute(medicamento);
    }

    public void updateMedicamento(Medicamento medicamento){
        new UpdateAsyncTask(mRememberDatabase.getMedicamentoDao()).execute(medicamento);
    }

    public LiveData<List<Medicamento>> retrieveMedicamentoTask() {

        return  mRememberDatabase.getMedicamentoDao().getMedicamentos();
    }

    public void deleteMedicamento(Medicamento medicamento){
        new DeleteAsyncTask(mRememberDatabase.getMedicamentoDao()).execute(medicamento);
    }



}
