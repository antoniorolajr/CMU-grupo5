package estg.ipp.rememberme.retrofit;

import java.util.List;

import estg.ipp.rememberme.models.ClinicaModel;
import estg.ipp.rememberme.models.LocationModel;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface OpenStreetMap {

    //deixar estar como o prof disse

    @GET("/reverse")
    Call<LocationModel> getLocationDetails(@Query("format") String format,
                                           @Query("lat") Double lat, @Query("lon") Double lon);

    @GET("/search")
    Call<List<ClinicaModel>> getLocationClinic(@Query("q") String query, @Query("format") String format);
}
