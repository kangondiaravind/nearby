package nearby.aravind.nearby;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import nearby.aravind.nearby.adapter.PlacesAdapter;
import nearby.aravind.nearby.model.Places;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static android.location.LocationManager.GPS_PROVIDER;

public class MainActivity extends AppCompatActivity {
    public static final int MY_PERMISSIONS_REQUEST_LOCATION = 99;
    private ListView resultList;
    String searchType;
    Double searchRadius;
    String searchKeyword;
    String apiKey;
    Spinner selectPlace;
    EditText keyword;
    Location location;
    Geocoder geocoder;
    List<Address> addresses;
    TextView displayLocationAddress;
    TextView NoResults;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button search = findViewById(R.id.btn_search);
        keyword = findViewById(R.id.tv_keyword);
        NoResults = findViewById(R.id.tv_no_results);
        displayLocationAddress = findViewById(R.id.tv_address);
        resultList = findViewById(R.id.lv_result);
        selectPlace = findViewById(R.id.sp_search);
        ArrayAdapter adapter = ArrayAdapter.createFromResource(this, R.array.supported_places, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        selectPlace.setAdapter(adapter);

        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fetchResult();
            }
        });
    }

    private boolean isLocationPermissionAllowed() throws IOException {
        int result = ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION);
        if (result == PackageManager.PERMISSION_GRANTED) {
            LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
            location = locationManager.getLastKnownLocation(GPS_PROVIDER);
            getAddressFromLatLng(location);
            return true;
        }
        Toast.makeText(this, "Permission not granted", Toast.LENGTH_SHORT).show();
        return false;
    }

    private void requestLocationPermission() {

        if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_FINE_LOCATION)) {
            //If the user has denied the permission previously your code will come to this block
            //Here you can explain why you need this permission
            //Explain here why you need this permission
        }
        //And finally ask for the permission
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, MY_PERMISSIONS_REQUEST_LOCATION);
    }

    //This method will be called when the user will tap on allow or deny
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        //Checking the request code of our request
        if (requestCode == MY_PERMISSIONS_REQUEST_LOCATION) {
            //If permission is granted
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "Permission granted ", Toast.LENGTH_LONG).show();
                fetchResult();
            } else {
                Toast.makeText(this, "Oops you just denied the permission", Toast.LENGTH_LONG).show();
            }
        }
    }

    private void fetchResult() {
        try {
            if (isLocationPermissionAllowed()) {
                searchKeyword = keyword.getText().toString().trim();
                String searchLocation = String.valueOf(location.getLatitude() + "," + String.valueOf(location.getLongitude()));
                searchRadius = 1000.0;
                searchType = selectPlace.getSelectedItem().toString();
                apiKey = getString(R.string.api_key);

                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl("https://maps.googleapis.com/")
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();

                PlacesAPI service = retrofit.create(PlacesAPI.class);
                Call<Places> call = service.getPlaces(searchLocation, searchRadius, searchType, searchKeyword, apiKey);
                call.enqueue(new Callback<Places>() {
                    @Override
                    public void onResponse(Call<Places> call, Response<Places> response) {
                        Places places = response.body();
                        if (places.getList().size() == 0) {
                            Log.e("Empty", "Emp");
                            NoResults.setVisibility(View.VISIBLE);
                            resultList.setVisibility(View.GONE);
                        } else {
                            NoResults.setVisibility(View.GONE);
                            resultList.setVisibility(View.VISIBLE);
                            resultList.setAdapter(new PlacesAdapter(MainActivity.this, places));
                            Log.d("places", new Gson().toJson(places));
                        }
                    }

                    @Override
                    public void onFailure(Call<Places> call, Throwable t) {
                        Toast.makeText(MainActivity.this, "Unable to process your request", Toast.LENGTH_SHORT).show();
                    }
                });
                return;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        requestLocationPermission();
    }

    private void getAddressFromLatLng(Location location) throws IOException {
        geocoder = new Geocoder(this, Locale.getDefault());
        addresses = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1); // Here 1 represent max location result to returned, by documents it recommended 1 to 5
        Log.e("latt", "" + location.getLatitude());
        Log.e("long", "" + location.getLongitude());
        String address = addresses.get(0).getAddressLine(0); // If any additional address line present than only, check with max available address lines by getMaxAddressLineIndex()
        String city = addresses.get(0).getLocality();
        String state = addresses.get(0).getAdminArea();
        String country = addresses.get(0).getCountryName();
        String postalCode = addresses.get(0).getPostalCode();
        String knownName = addresses.get(0).getFeatureName();
        Log.e("Address is", "getAddressFromLatLng: " + address + city + state + country + postalCode + knownName);
        displayLocationAddress.setText(" Near by \n " + address);
    }
}