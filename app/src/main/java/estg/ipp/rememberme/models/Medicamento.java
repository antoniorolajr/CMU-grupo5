package estg.ipp.rememberme.models;


import android.os.Parcel;
import android.os.Parcelable;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "medicamentos")
public class Medicamento implements Parcelable {

    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "nome")
    private String nome_medicamento;

    @ColumnInfo(name = "descricao")
    private String descricao_medicamento;

    @ColumnInfo(name = "data")
    private String data_registo_medicamento;

    @ColumnInfo(name = "stock")
    private String stock;

    @ColumnInfo(name = "local")
    private String local_da_toma;

    @ColumnInfo(name = "hora")
    private String hora_da_toma;


    public Medicamento(String nome_medicamento, String descricao_medicamento, String data_registo_medicamento,
                       String stock, String local_da_toma, String hora_da_toma) {
        this.nome_medicamento = nome_medicamento;
        this.descricao_medicamento = descricao_medicamento;
        this.data_registo_medicamento = data_registo_medicamento;
        this.stock = stock;
        this.local_da_toma = local_da_toma;
        this.hora_da_toma = hora_da_toma;
    }

    @Ignore
    public Medicamento() {
    }


    protected Medicamento(Parcel in) {
        id = in.readInt();
        nome_medicamento = in.readString();
        descricao_medicamento = in.readString();
        data_registo_medicamento = in.readString();
        stock = in.readString();
        local_da_toma = in.readString();
        hora_da_toma = in.readString();
    }

    public static final Creator<Medicamento> CREATOR = new Creator<Medicamento>() {
        @Override
        public Medicamento createFromParcel(Parcel in) {
            return new Medicamento(in);
        }

        @Override
        public Medicamento[] newArray(int size) {
            return new Medicamento[size];
        }
    };

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome_medicamento() {
        return nome_medicamento;
    }

    public void setNome_medicamento(String nome_medicamento) {
        this.nome_medicamento = nome_medicamento;
    }

    public String getDescricao_medicamento() {
        return descricao_medicamento;
    }

    public void setDescricao_medicamento(String descricao_medicamento) {
        this.descricao_medicamento = descricao_medicamento;
    }

    public String getData_registo_medicamento() {
        return data_registo_medicamento;
    }

    public void setData_registo_medicamento(String data_registo_medicamento) {
        this.data_registo_medicamento = data_registo_medicamento;
    }

    public String getStock() {
        return stock;
    }

    public void setStock(String stock) {
        this.stock = stock;
    }

    public String getLocal_da_toma() {
        return local_da_toma;
    }

    public void setLocal_da_toma(String local_da_toma) {
        this.local_da_toma = local_da_toma;
    }

    public String getHora_da_toma() {
        return hora_da_toma;
    }

    public void setHora_da_toma(String hora_da_toma) {
        this.hora_da_toma = hora_da_toma;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(id);
        parcel.writeString(nome_medicamento);
        parcel.writeString(descricao_medicamento);
        parcel.writeString(data_registo_medicamento);
        parcel.writeString(stock);
        parcel.writeString(local_da_toma);
        parcel.writeString(hora_da_toma);
    }

    @Override
    public String toString() {
        return "Medicamento{" +
                "id=" + id +
                ", nome_medicamento='" + nome_medicamento + '\'' +
                ", descricao_medicamento='" + descricao_medicamento + '\'' +
                ", data_registo_medicamento='" + data_registo_medicamento + '\'' +
                ", stock='" + stock + '\'' +
                ", local_da_toma='" + local_da_toma + '\'' +
                ", hora_da_toma='" + hora_da_toma + '\'' +
                '}';
    }
}
