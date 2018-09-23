package nearby.aravind.nearby;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;

import com.google.gson.Gson;

import nearby.aravind.nearby.adapter.PlacesAdapter;
import nearby.aravind.nearby.model.Places;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    private ListView resultList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button search = (Button) findViewById(R.id.btn_search);
        resultList = (ListView) findViewById(R.id.lv_result);
        Spinner selectplace = (Spinner) findViewById(R.id.sp_search);
        ArrayAdapter adapter = ArrayAdapter.createFromResource(this, R.array.supported_places, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        selectplace.setAdapter(adapter);
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fetchResult();
            }
        });
    }

    private void fetchResult() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://maps.googleapis.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        PlacesAPI service = retrofit.create(PlacesAPI.class);
        Call<Places> call = service.getPlaces();
        call.enqueue(new Callback<Places>() {
            @Override
            public void onResponse(Call<Places> call, Response<Places> response) {
                Places places = response.body();
                resultList.setAdapter(new PlacesAdapter(MainActivity.this, places));
                Log.d("places", new Gson().toJson(places));
            }

            @Override
            public void onFailure(Call<Places> call, Throwable t) {

            }
        });
    }
}
