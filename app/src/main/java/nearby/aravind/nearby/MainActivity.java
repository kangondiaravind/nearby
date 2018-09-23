package nearby.aravind.nearby;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Spinner selectplace = (Spinner) findViewById(R.id.sp_search);
        ArrayAdapter adapter = ArrayAdapter.createFromResource(this, R.array.supported_places, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        selectplace.setAdapter(adapter);
    }
}
