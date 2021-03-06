package sabel.com.wegepunkte;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.Date;

public class MainActivity extends AppCompatActivity {

    // DATA FIELDS
    private Button btn_show;
    private Button btn_save;
    private Button btn_route;
    private LocationManager locationManager;
    private boolean isGPSEnabled;
    private WegepunktRepo wegepunkte;


    // METHODS

    @Override
    protected void onStart() {
        super.onStart();
        if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            if (shouldShowRequestPermissionRationale(Manifest.permission.ACCESS_FINE_LOCATION)) {
                Toast.makeText(this, "I need this Permission", Toast.LENGTH_LONG).show();
            }
            requestPermissions();
        } else {
            this.initLocationManager();
            activateSaveLocation();
        }

    } // END onStart

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (grantResults.length > 0 && requestCode == 4711 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            this.initLocationManager();
            activateSaveLocation();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initComponents();
        initEvents();
    }

    private void initComponents() {
        btn_save = (Button) findViewById(R.id.btn_save);
        btn_show = (Button) findViewById(R.id.btn_show);
        btn_route = (Button) findViewById(R.id.btn_route);
        btn_save.setEnabled(false);
        isGPSEnabled = false;
        wegepunkte = new WegepunktRepo();
    } // END INITCOMPONENTS


    private void initEvents() {
        btn_show.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, ListViewActivity.class);
                intent.putExtra("wegepunkte", wegepunkte);
                startActivity(intent);
            }
        });

        btn_route.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
        if(wegepunkte.size() > 1) {
            WegePunkt startPunkt = wegepunkte.get(0);
            WegePunkt endPunnkt = wegepunkte.get(1);

            String url = "https://www.google.com/maps/dir/?api=1&origin=";
            double latitudeStart =startPunkt.getLatitude();
            double longitudeStart = startPunkt.getLongitude();
            double latitudeEnd = endPunnkt.getLatitude();
            double longitudeEnd = endPunnkt.getLongitude();

            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url + latitudeStart + "," + longitudeStart + "&destination=" + latitudeEnd + "," + longitudeEnd));
            startActivity(intent);


        }
            }
        });

        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                if (ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    return;
                }

                if (isGPSEnabled) {
                    locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 6000, 10, new LocationListener() {
                        @Override
                        public void onLocationChanged(Location location) {

                        }

                        @Override
                        public void onStatusChanged(String s, int i, Bundle bundle) {

                        }

                        @Override
                        public void onProviderEnabled(String s) {

                        }

                        @Override
                        public void onProviderDisabled(String s) {

                        }
                    });

                    Location location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);

                    if (location != null) {
                        WegePunkt wegePunkt = new WegePunkt(new Date(), location.getLatitude(), location.getLongitude());
                        Log.d("Wegepunkt", wegePunkt.toString());
                        wegepunkte.add(wegePunkt);
                        Toast.makeText(MainActivity.this, "Wegepunkt wurde gespeichert", Toast.LENGTH_SHORT).show();
                    }

                }


            }
        });


    } // END INITEVENTS

    private void requestPermissions() {
        String[] permissions = new String[1];
        permissions[0] = Manifest.permission.ACCESS_FINE_LOCATION;
        requestPermissions(permissions, 4711);
    } // END VOID requestPermissions()

    private void initLocationManager() {
        locationManager = (LocationManager) this.getSystemService(this.LOCATION_SERVICE);
        isGPSEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);

    } // END VOID initLocationManager()

    private void activateSaveLocation() {
        this.initLocationManager();
        btn_save.setEnabled(this.isGPSEnabled);
    }


} // END CLASS
