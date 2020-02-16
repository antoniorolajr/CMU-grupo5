package estg.ipp.rememberme.retrofit;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitC {


    public static String URL = "https://nominatim.openstreetmap.org"; //nao alterar

    public static Retrofit getRetrofitInstance() {
        return new Retrofit.Builder()
                .baseUrl(URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    public static OpenStreetMap getApi() {
        return getRetrofitInstance().create(OpenStreetMap.class);
    }
}
