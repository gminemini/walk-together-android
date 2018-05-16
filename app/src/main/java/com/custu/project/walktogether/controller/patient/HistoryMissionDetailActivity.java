package com.custu.project.walktogether.controller.patient;

import android.Manifest;
import android.content.Context;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.location.Location;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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
import com.custu.project.walktogether.data.mission.Mission;
import com.custu.project.walktogether.data.mission.PatientMissionList;
import com.custu.project.walktogether.data.mission.Position;
import com.custu.project.walktogether.manager.ConnectServer;
import com.custu.project.walktogether.model.MissionModel;
import com.custu.project.walktogether.stepcounter.StepListener;
import com.custu.project.walktogether.util.ConfigService;
import com.custu.project.walktogether.util.UserManager;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;

import java.util.ArrayList;
import java.util.List;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class HistoryMissionDetailActivity extends FragmentActivity implements OnMapReadyCallback, DirectionCallback, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, GoogleMap.OnMarkerClickListener, LocationListener {
    private static final int REQUEST_PERMISSION_LOCATION = 255;
    private static final int MY_PERMISSIONS_REQUEST_FINE_LOCATION = 111;
    private final static int CONNECTION_FAILURE_RESOLUTION_REQUEST = 9000;

    private SupportMapFragment mapFragment;
    private ArrayList<LatLng> routePoints;
    private GoogleApiClient mGoogleApiClient;
    private LocationRequest mLocationRequest;
    private LatLng origin;
    private LatLng destination;
    private List<LatLng> wayPoints;
    private GoogleMap googleMap;
    private List<Step> stepList;
    private ArrayList<PatientMissionList> missionArrayList;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history_mission_detail);

        mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        if (ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, REQUEST_PERMISSION_LOCATION);
        }

        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(
                    this, // Activity
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    MY_PERMISSIONS_REQUEST_FINE_LOCATION);

        }
        getData();
        initPositionMission();
        initMap();
        initLocation();
    }

    private void getData() {
        routePoints = MissionModel.getInstance().getRouteMissions(getIntent().getStringExtra("route"));
        missionArrayList = MissionModel.getInstance().getPatientMissionListArrayList(getIntent().getStringExtra("mission"));
    }

    private void initLocation() {
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
        wayPoints = new ArrayList<>();

        Position position = missionArrayList.get(0).getPosition();
        origin = new LatLng(position.getLatitude(), position.getLongitude());

        for (int i = 1; i < missionArrayList.size(); i++) {
            position = missionArrayList.get(i).getPosition();
            wayPoints.add(new LatLng(position.getLatitude(), position.getLongitude()));
        }

        position = missionArrayList.get(0).getPosition();
        destination = new LatLng(position.getLatitude(), position.getLongitude());
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        this.googleMap = googleMap;
        this.googleMap.setOnMarkerClickListener(this);
        this.googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(ConfigService.DEFAULT_LAT, ConfigService.DEFAULT_LONG), 8));
    }

    @Override
    public void onDirectionSuccess(Direction direction, String rawBody) {
        if (direction.isOK()) {
            Route route = direction.getRouteList().get(0);
            int legCount = route.getLegList().size();
            for (int index = 0; index < legCount; index++) {
                Leg leg = route.getLegList().get(index);
                googleMap.addMarker(new MarkerOptions()
                        .icon(BitmapDescriptorFactory.fromBitmap(resizeMarker(R.drawable.marker)))
                        .position(leg.getStartLocation().getCoordination())
                        .title(setTitleMarker(missionArrayList.get(index).getMission().getCognitiveCategory().getCognitiveCategoryName())))
                        .setTag(index);
                if (index == legCount - 1) {
                    googleMap.addMarker(new MarkerOptions()
                            .icon(BitmapDescriptorFactory.fromBitmap(resizeMarker(R.drawable.marker)))
                            .position(leg.getEndLocation().getCoordination())
                            .title(setTitleMarker(missionArrayList.get(index).getMission().getCognitiveCategory().getCognitiveCategoryName())))
                            .setTag(missionArrayList.size() - 1);
                }
                stepList = leg.getStepList();
                ArrayList<PolylineOptions> polylineOptionList = DirectionConverter.createTransitPolyline(this, stepList, 5, Color.parseColor("#3e8aed"), 5, Color.parseColor("#3e8aed"));
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

    private String setTitleMarker(String input) {
        return "ภารกิจประเภท " + input;
    }

    private void setCameraWithCoordinationBounds(Route route) {
        LatLng southwest = route.getBound().getSouthwestCoordination().getCoordination();
        LatLng northeast = route.getBound().getNortheastCoordination().getCoordination();
        LatLngBounds bounds = new LatLngBounds(southwest, northeast);
        googleMap.animateCamera(CameraUpdateFactory.newLatLngBounds(bounds, 100));
        //googleMap.getUiSettings().setScrollGesturesEnabled(false);
        drawRoute();
    }

    private Bitmap resizeMarker(int id) {
        int height = 115;
        int width = 85;
        BitmapDrawable drawable = (BitmapDrawable) getResources().getDrawable(id);
        Bitmap bitmap = drawable.getBitmap();
        return Bitmap.createScaledBitmap(bitmap, width, height, false);
    }

    private void drawRoute() {
        PolylineOptions pOptions = new PolylineOptions()
                .width(18)
                .color(Color.GREEN)
                .geodesic(true);

        for (int z = 0; z < routePoints.size(); z++) {
            LatLng point = routePoints.get(z);
            pOptions.add(point);
        }
        googleMap.addPolyline(pOptions);
    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        return false;
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        Location location = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
        if (location == null) {
            LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this);

        } else {
            LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this);
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

    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(base));
    }
}
