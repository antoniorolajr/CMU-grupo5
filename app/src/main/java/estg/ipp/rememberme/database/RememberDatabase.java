package estg.ipp.rememberme.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import estg.ipp.rememberme.models.Medicamento;

//com a room se alterar entidades,estrutura da db tenho de mudar a versao
@Database(entities = {Medicamento.class}, version =1, exportSchema = false)
public abstract class RememberDatabase extends RoomDatabase {

    public static final String DATABASE_NAME = "remember_db";

    private static RememberDatabase instance;

    static RememberDatabase getInstance(final Context context){
        if(instance == null){
            instance = Room.databaseBuilder(
                    context.getApplicationContext(),
                    RememberDatabase.class,
                    DATABASE_NAME
            ).build();
        }
        return instance;
    }

    public abstract MedicamentoDao getMedicamentoDao();

}


