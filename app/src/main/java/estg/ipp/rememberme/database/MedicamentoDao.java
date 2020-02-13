package estg.ipp.rememberme.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import estg.ipp.rememberme.models.Medicamento;

@Dao
public interface MedicamentoDao {

    @Insert
    long[] insertMedicamentos(Medicamento... medicamentos);

    @Query("SELECT * FROM medicamentos")
    LiveData<List<Medicamento>> getMedicamentos();
    /* data observation class */


    @Delete
    int delete(Medicamento... medicamentos);

    @Update
    int update(Medicamento... medicamentos);

}
