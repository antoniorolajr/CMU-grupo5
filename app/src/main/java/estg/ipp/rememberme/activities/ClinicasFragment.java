package estg.ipp.rememberme.activities;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;

import java.util.Iterator;
import java.util.List;
import java.util.Objects;

import estg.ipp.rememberme.R;
import estg.ipp.rememberme.models.ClinicaModel;
import estg.ipp.rememberme.models.LocationModel;
import estg.ipp.rememberme.retrofit.OpenStreetMap;
import estg.ipp.rememberme.retrofit.RetrofitC;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ClinicasFragment extends Fragment implements OnMapReadyCallback {

    private static final String TAG = "CLINIC_FRAGMENT";
    private static final int REQUEST_FINE_LOCATION = 100;

    private View view;
    private FusedLocationProviderClient mFusedLocationProviderClient;
    private AlertDialog checkGPSdialog;

    private SupportMapFragment mMapFragment;
    private GoogleMap mGoogleMap;

    private View mapLoadingView;
    private View mapLoadingFailureView;
    private View mapLoadedView;
    private TextView mapLoadingText;
    private TextView mapLoadFailureText;
    private Button findManualButton;
    private EditText findManualLocation;

    private boolean isGPSOn;
    private boolean isMapLoadFailed;
    private boolean isLoadFailed;
    private boolean isGetLocationFailed;
    private boolean isLoading;

    public ClinicasFragment() {

    }

    private void requestPermissions() {
        if (ActivityCompat.checkSelfPermission(Objects.requireNonNull(getActivity()),
                Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(Objects.requireNonNull(getActivity()),
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    REQUEST_FINE_LOCATION);
        }
    }

    public AlertDialog createManualSearchDialog(Context context) {
        findManualLocation = new EditText(context);
        findManualLocation.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PERSON_NAME);

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setMessage("Introduza o nome da localização:")
                .setCancelable(false)
                .setPositiveButton("Confirmar", null)
                .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    public void onClick(final DialogInterface dialog, @SuppressWarnings("unused") final int id) {
                        dialog.dismiss();
                    }
                })
                .setView(findManualLocation);
        final AlertDialog dialog = builder.create();

        // Set custom positive button listener
        dialog.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialogInterface) {
                Button button = dialog.getButton(AlertDialog.BUTTON_POSITIVE);
                button.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View view) {
                        String input = findManualLocation.getText().toString();
                        if (input.isEmpty()) {
                            findManualLocation.setError("Preenchimento Obrigatório");
                        } else {
                            requestLocationPharmacyListByName(input);
                            dialog.dismiss();
                        }
                    }
                });
            }
        });
        return dialog;
    }

    public AlertDialog createGpsCheckDialog(Context context) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setMessage("O seu GPS encontra-se desativado. Ativar?")
                .setCancelable(false)
                .setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                    public void onClick(@SuppressWarnings("unused") final DialogInterface dialog,
                                        @SuppressWarnings("unused") final int id) {
                        startActivity(new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS));
                    }
                })
                .setNegativeButton("Não", new DialogInterface.OnClickListener() {
                    public void onClick(final DialogInterface dialog, @SuppressWarnings("unused") final int id) {
                        dialog.cancel();
                    }
                });
        return builder.create();
    }

    public boolean checkGPS(Context context, boolean showDialog) {
        LocationManager manager = (LocationManager) context.getSystemService( Context.LOCATION_SERVICE );
        if ( manager != null && !manager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            if (showDialog) checkGPSdialog.show();
            isGPSOn = false;
            return false;
        } else {
            isGPSOn = true;
            return true;
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        checkGPSdialog = createGpsCheckDialog(getContext());

        requestPermissions();
        this.mFusedLocationProviderClient =
                LocationServices.getFusedLocationProviderClient(Objects.requireNonNull(getActivity()));

        isMapLoadFailed = false;
        isLoadFailed = false;
        isGetLocationFailed = false;
        isLoading = true;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_clinicas, container, false);

        mapLoadedView = view.findViewById(R.id.clinic_map_container);
        mapLoadingView = view.findViewById(R.id.clinic_load_map_container);
        mapLoadingFailureView = view.findViewById(R.id.clinic_load_failure_container);
        mapLoadingText = view.findViewById(R.id.clinic_load_text);
        mapLoadFailureText = view.findViewById(R.id.clinic_failure_text);

        findManualButton = view.findViewById(R.id.clinic_failure_manual_button);
        findManualButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isGPSOn = true;
                isMapLoadFailed = false;
                isGetLocationFailed = false;
                isLoadFailed = false;
                isLoading = true;
                createManualSearchDialog(getContext()).show();
            }
        });

        view.findViewById(R.id.clinic_failure_retry_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!checkGPS(getContext(), true)) {
                    updateViews();
                } else {
                    isMapLoadFailed = false;
                    isGetLocationFailed = false;
                    isLoadFailed = false;
                    isLoading = true;
                    requestPermissions();
                    getCurrentLocation((FragmentActivity) view.getContext());
                }
            }
        });

        if (getActivity() != null) {
            mMapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);

            if (mMapFragment != null) {
                mMapFragment.getMapAsync(this);

                if (!checkGPS(getContext(), false)) {
                    updateViews();
                } else {
                    getCurrentLocation((FragmentActivity) view.getContext());
                }

            } else {
                Log.w(TAG, "Não conseguiu gerar o mapa!");
                isMapLoadFailed = true;
                updateViews();
            }
        } else {
            Log.w(TAG, "Não conseguiu obter o fragment!");
            return null;
        }

        return view;
    }

    private void getCurrentLocation(FragmentActivity context) {
        mFusedLocationProviderClient.getLastLocation()
                .addOnSuccessListener(context, new OnSuccessListener<Location>() {
                    @Override
                    public void onSuccess(@NonNull Location location) {
                        if (location != null) {
                            requestLocationDetails(location);
                        } else {
                            isGetLocationFailed = true;
                            updateViews();
                        }
                    }
                })
                .addOnFailureListener(context, new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "Erro ao tentar encontrar a sua localizção");
                        isMapLoadFailed = true;
                        updateViews();
                    }
                });
    }

    private void requestLocationDetails(@NonNull Location location) {

        OpenStreetMap api = RetrofitC.getApi();
        mapLoadingText.setText(R.string.cliniclist_load_text_city);

        api.getLocationDetails("json",
                location.getLatitude(), location.getLongitude())
                .enqueue(new Callback<LocationModel>() {
                    @Override
                    public void onResponse(@NonNull Call<LocationModel> call,
                                           @NonNull Response<LocationModel> response) {
                        requestLocationPharmacyList(response.body());
                    }

                    @Override
                    public void onFailure(@NonNull Call<LocationModel> call,
                                          @NonNull Throwable t) {
                        Log.e(TAG, "Não foi possivel retornar a localização através do retrofit", t);
                        isLoadFailed = true;
                        updateViews();
                    }
                });
    }

    private void requestLocationPharmacyList(@NonNull LocationModel location) {

        OpenStreetMap api = RetrofitC.getApi();
        mapLoadingText.setText(R.string.cliniclist_load_text_list);

        final double llat = location.getLocationLat(), llon = location.getLocationLon();
        String locationName = location.getAddress().getLocationName(),
                query = locationName + " clinica";
        api.getLocationClinic(query, "json")
                .enqueue(new Callback<List<ClinicaModel>>() {
                    @Override
                    public void onResponse(@NonNull Call<List<ClinicaModel>> call,
                                           @NonNull Response<List<ClinicaModel>> response) {
                        List<ClinicaModel> list = response.body();
                        if (list != null && !list.isEmpty()) {
                            updateClinicListMap(list, llat, llon);
                        } else {
                            isLoadFailed = true;
                            updateViews();
                        }
                    }

                    @Override
                    public void onFailure(@NonNull Call<List<ClinicaModel>> call,
                                          @NonNull Throwable t) {
                        Log.e(TAG, "Não foi possível retornar a localização das clínicas através do Retrofit", t);
                        isLoadFailed = true;
                        updateViews();
                    }
                });
    }

    private void requestLocationPharmacyListByName(@NonNull String location) {

        OpenStreetMap api = RetrofitC.getApi();
        mapLoadingText.setText(R.string.cliniclist_load_text_list);

        String query = location + " clinica";
        api.getLocationClinic(query, "json")
                .enqueue(new Callback<List<ClinicaModel>>() {
                    @Override
                    public void onResponse(@NonNull Call<List<ClinicaModel>> call,
                                           @NonNull Response<List<ClinicaModel>> response) {
                        List<ClinicaModel> list = response.body();
                        if (list != null && !list.isEmpty()) {
                            updateClinicListMap(list,
                                    list.get(0).getLatitude(), list.get(0).getLatitude());
                        } else {
                            isLoadFailed = true;
                            updateViews();
                        }
                    }

                    @Override
                    public void onFailure(@NonNull Call<List<ClinicaModel>> call,
                                          @NonNull Throwable t) {
                        Log.e(TAG, "Não foi possível retornar a localização das clínicas através do Retrofit", t);
                        isLoadFailed = true;
                        updateViews();
                    }
                });
    }

    private void updateClinicListMap(List<ClinicaModel> list, double locationLat, double locationLon) {

        mapLoadingText.setText(R.string.cliniclist_load_text_map);

        Iterator<ClinicaModel> it = list.iterator();
        ClinicaModel clinica;
        LatLng latLng;
        while (it.hasNext()) {
            clinica = it.next();
            latLng = new LatLng(clinica.getLatitude(), clinica.getLongitude());
            mGoogleMap.addMarker(new MarkerOptions()
                    .position(latLng)
                    .title(clinica.getDisplay_name()));
        }
        mGoogleMap.getUiSettings().setZoomControlsEnabled(true);
        mGoogleMap.setMyLocationEnabled(true);
        mGoogleMap.moveCamera(CameraUpdateFactory
                .newLatLng(new LatLng(locationLat, locationLon)));

        isLoading = false;
        updateViews();
    }

    private void updateViews() {
        if (!isGPSOn) {
            mapLoadFailureText.setText(R.string.cliniclist_load_failure_gps);
            mapLoadedView.setVisibility(View.GONE);
            mapLoadingView.setVisibility(View.GONE);
            mapLoadingFailureView.setVisibility(View.VISIBLE);
        } else if (isMapLoadFailed) {
            mapLoadFailureText.setText(R.string.cliniclist_load_failure_map);
            mapLoadedView.setVisibility(View.GONE);
            mapLoadingView.setVisibility(View.GONE);
            mapLoadingFailureView.setVisibility(View.VISIBLE);
        } else if (isLoadFailed) {
            mapLoadFailureText.setText(R.string.cliniclist_load_failure_text);
            mapLoadedView.setVisibility(View.GONE);
            mapLoadingView.setVisibility(View.GONE);
            mapLoadingFailureView.setVisibility(View.VISIBLE);
        } else if (isGetLocationFailed) {
            mapLoadFailureText.setText(R.string.cliniclist_load_failure_location);
            mapLoadedView.setVisibility(View.GONE);
            mapLoadingView.setVisibility(View.GONE);
            mapLoadingFailureView.setVisibility(View.VISIBLE);
        } else if (isLoading) {
            mapLoadedView.setVisibility(View.GONE);
            mapLoadingView.setVisibility(View.VISIBLE);
            mapLoadingFailureView.setVisibility(View.GONE);
        } else {
            findManualButton.setVisibility(View.GONE);
            mapLoadedView.setVisibility(View.VISIBLE);
            mapLoadingView.setVisibility(View.GONE);
            mapLoadingFailureView.setVisibility(View.GONE);
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mGoogleMap = googleMap;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        checkGPSdialog.dismiss();
    }
}
