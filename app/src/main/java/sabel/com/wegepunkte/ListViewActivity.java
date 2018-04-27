package sabel.com.wegepunkte;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.io.Serializable;

public class ListViewActivity extends AppCompatActivity {

    // DATA FIELDS
    private ListView listView;
    private WegepunktRepo wegepunkte;


    // METHODS

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_view);

        listView = findViewById(R.id.lv_wegepunkte);
        Serializable serializable = getIntent().getSerializableExtra("wegepunkte");
        if (serializable instanceof WegepunktRepo) {
            wegepunkte = (WegepunktRepo) serializable;

            ArrayAdapter<WegePunkt> arrayAdapter = new ArrayAdapter<WegePunkt>(this, android.R.layout.simple_list_item_1, wegepunkte.getWegepunkte());
            listView.setAdapter(arrayAdapter);
        } // END IF

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                // Toast.makeText(ListViewActivity.this, listView.getItemAtPosition(i).toString(), Toast.LENGTH_SHORT).show();
                WegePunkt wegePunkt = wegepunkte.get(i);
                String url = "https://www.google.com/maps/search/?api=1&query=";
                //String url = "https://www.google.com/maps/dir/?api=1&origin=x,y&destination=x,y&";
                double latitude = wegePunkt.getLatitude();
                double longitude = wegePunkt.getLongitude();
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url + latitude + "," + longitude));
                startActivity(intent);
            }
        });

    } // END

} // END CLASS
