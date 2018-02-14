package com.custu.project.walktogether;

import android.Manifest;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.location.Location;

import com.custu.project.walktogether.stepcounter.StepDetector;
import com.custu.project.walktogether.stepcounter.StepListener;
import com.github.tony19.timber.loggly.LogglyTree;
import com.google.android.gms.location.LocationListener;

import android.os.Build;
import android.os.CountDownTimer;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.widget.Toast;

import com.akexorcist.googledirection.DirectionCallback;
import com.akexorcist.googledirection.GoogleDirection;
import com.akexorcist.googledirection.constant.TransportMode;
import com.akexorcist.googledirection.model.Direction;
import com.akexorcist.googledirection.model.Leg;
import com.akexorcist.googledirection.model.Route;
import com.akexorcist.googledirection.model.Step;
import com.akexorcist.googledirection.util.DirectionConverter;
import com.custu.project.project.walktogether.R;
import com.custu.project.walktogether.util.ConfigService;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import timber.log.Timber;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback, DirectionCallback, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, LocationListener, SensorEventListener, StepListener {

    private GoogleMap googleMap;
    private LatLng origin;
    private LatLng destination;
    private List<LatLng> wayPoints;
    private static final int REQUEST_PERMISSION_LOCATION = 255;

    private final static int CONNECTION_FAILURE_RESOLUTION_REQUEST = 9000;
    private GoogleApiClient mGoogleApiClient;
    private LocationRequest mLocationRequest;
    private double currentLatitude;
    private double currentLongitude;
    private ArrayList<LatLng> routePoints;
    private int i = 0;

    private StepDetector simpleStepDetector;
    private static final String TEXT_NUM_STEPS = "Number of Steps: ";
    private int numSteps;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        Timber.plant(new LogglyTree(ConfigService.LOG_KEY));

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        if (ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, REQUEST_PERMISSION_LOCATION);
        }

        initStepCounter();
        initPositionMission();
        initMap();
        initLocation();
        //testPlay();
    }

    private void initStepCounter() {
        SensorManager sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        Sensor angle = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        simpleStepDetector = new StepDetector();
        simpleStepDetector.registerListener(this);

        numSteps = 0;
        sensorManager.registerListener(MapsActivity.this, angle, SensorManager.SENSOR_DELAY_FASTEST);
    }

    private void testPlay() {
        new CountDownTimer(30000, 5000) {
            public void onTick(long millisUntilFinished) {
                if (i < wayPoints.size()) {
                    double latitude = wayPoints.get(i).latitude;
                    double longitude = wayPoints.get(i).longitude;
                    LatLng latLng = new LatLng(latitude, longitude);
                    Log.d("onTick: ", "onTick: " + latitude + " WORKS " + longitude + " " + i);

                    PolylineOptions pOptions = new PolylineOptions()
                            .width(20)
                            .color(Color.GREEN)
                            .geodesic(true);
                    routePoints.add(latLng);

                    for (int z = 0; z < routePoints.size(); z++) {
                        LatLng point = routePoints.get(z);
                        pOptions.add(point);
                    }
                    Polyline line = googleMap.addPolyline(pOptions);
                    i++;
                }
            }

            public void onFinish() {

            }
        }.start();
    }

    private void initLocation() {
        routePoints = new ArrayList<>();
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();

        mLocationRequest = LocationRequest.create()
                .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
                .setInterval(3000)
                .setFastestInterval(1000);
    }

    private void initMap() {
        GoogleDirection.withServerKey(ConfigService.GOOGLE_API_KEY)
                .from(origin)
                .and(wayPoints)
                .to(destination)
                .transportMode(TransportMode.WALKING)
                .execute(this);
    }

    private void initPositionMission() {
        origin = new LatLng(14.065676, 100.605330);

        wayPoints = Arrays.asList(
                new LatLng(14.066160, 100.605536),
                new LatLng(14.066105, 100.607095),
                new LatLng(14.066131, 100.607875),
                new LatLng(14.066129, 100.606083));

        destination = new LatLng(14.066129, 100.606655);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        this.googleMap = googleMap;
    }

    @Override
    public void onDirectionSuccess(Direction direction, String rawBody) {
        if (direction.isOK()) {
            Route route = direction.getRouteList().get(0);
            int legCount = route.getLegList().size();
            for (int index = 0; index < legCount; index++) {
                Leg leg = route.getLegList().get(index);
                googleMap.addMarker(new MarkerOptions().position(leg.getStartLocation().getCoordination()));
                if (index == legCount - 1) {
                    googleMap.addMarker(new MarkerOptions().position(leg.getEndLocation().getCoordination()));
                }
                List<Step> stepList = leg.getStepList();
                ArrayList<PolylineOptions> polylineOptionList = DirectionConverter.createTransitPolyline(this, stepList, 3, Color.RED, 3, Color.BLUE);
                for (PolylineOptions polylineOption : polylineOptionList) {
                    googleMap.addPolyline(polylineOption);
                }
            }
            setCameraWithCoordinationBounds(route);
        }
    }

    @Override
    public void onDirectionFailure(Throwable t) {
        Log.d("onDirectionFailure: ", "onDirectionFailure: " + t);
    }

    private void setCameraWithCoordinationBounds(Route route) {
        LatLng southwest = route.getBound().getSouthwestCoordination().getCoordination();
        LatLng northeast = route.getBound().getNortheastCoordination().getCoordination();
        LatLngBounds bounds = new LatLngBounds(southwest, northeast);
        googleMap.animateCamera(CameraUpdateFactory.newLatLngBounds(bounds, 100));
        googleMap.getUiSettings().setAllGesturesEnabled(false);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        googleMap.setMyLocationEnabled(true);
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        Location location = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
        if (location == null) {
            LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this);

        } else {
            currentLatitude = location.getLatitude();
            currentLongitude = location.getLongitude();
            LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this);
            Toast.makeText(this, currentLatitude + " WORKS " + currentLongitude + "", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        if (connectionResult.hasResolution()) {
            try {
                connectionResult.startResolutionForResult(this, CONNECTION_FAILURE_RESOLUTION_REQUEST);
            } catch (IntentSender.SendIntentException e) {
                e.printStackTrace();
            }
        } else {
            Log.e("Error", "Location services connection failed with code " + connectionResult.getErrorCode());
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        mGoogleApiClient.connect();
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (mGoogleApiClient.isConnected()) {
            LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient, this);
            mGoogleApiClient.disconnect();
        }

    }

    @Override
    public void onLocationChanged(Location location) {
        currentLatitude = location.getLatitude();
        currentLongitude = location.getLongitude();

        Toast.makeText(this, currentLatitude + " Changed " + currentLongitude + "", Toast.LENGTH_LONG).show();
        updateCameraBearing(googleMap, location.getBearing());

    }

    private void updateCameraBearing(GoogleMap googleMap, float bearing) {
        if (googleMap == null) return;
        CameraPosition camPos = CameraPosition
                .builder(
                        googleMap.getCameraPosition() // current Camera
                )
                .bearing(bearing)
                .build();
        googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(camPos));
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            simpleStepDetector.updateAccel(
                    event.timestamp, event.values[0], event.values[1], event.values[2]);
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }

    @Override
    public void step(long timeNs) {
        numSteps++;
        LatLng latLng = new LatLng(currentLatitude, currentLongitude);

        PolylineOptions pOptions = new PolylineOptions()
                .width(5)
                .color(Color.GREEN)
                .geodesic(true);
        routePoints.add(latLng);
        Timber.tag("route");
        Timber.i(routePoints.toString());
        Toast.makeText(this, TEXT_NUM_STEPS + numSteps + " " + currentLatitude + " Changed " + currentLongitude + " "+routePoints.size(), Toast.LENGTH_LONG).show();

        for (int z = 0; z < routePoints.size(); z++) {
            LatLng point = routePoints.get(z);
            pOptions.add(point);
        }
        googleMap.addPolyline(pOptions);
        routePoints.add(latLng);
    }
}
