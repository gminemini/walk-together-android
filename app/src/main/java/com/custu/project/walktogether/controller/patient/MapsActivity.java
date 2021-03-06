package com.custu.project.walktogether.controller.patient;

import android.Manifest;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.akexorcist.googledirection.model.Direction;
import com.akexorcist.googledirection.model.Leg;
import com.akexorcist.googledirection.model.Route;
import com.akexorcist.googledirection.model.Step;
import com.akexorcist.googledirection.util.DirectionConverter;
import com.custu.project.project.walktogether.R;
import com.custu.project.walktogether.controller.mission.missiontwo.MissionBoxActivity;
import com.custu.project.walktogether.controller.mission.missiontwo.MissionClockActivity;
import com.custu.project.walktogether.controller.mission.missiontwo.MissionEmotionActivity;
import com.custu.project.walktogether.controller.mission.missiontwo.MissionProverbsActivity;
import com.custu.project.walktogether.controller.mission.missiontwo.MissionTypegroupActivity;
import com.custu.project.walktogether.data.mission.Mission;
import com.custu.project.walktogether.data.mission.PatientGame;
import com.custu.project.walktogether.data.mission.Position;
import com.custu.project.walktogether.manager.ConnectServer;
import com.custu.project.walktogether.model.MissionModel;
import com.custu.project.walktogether.network.callback.OnDataSuccessListener;
import com.custu.project.walktogether.stepcounter.StepDetector;
import com.custu.project.walktogether.stepcounter.StepListener;
import com.custu.project.walktogether.util.ConfigService;
import com.custu.project.walktogether.util.DialogUtil;
import com.custu.project.walktogether.util.NetworkUtil;
import com.custu.project.walktogether.util.StoreMission;
import com.custu.project.walktogether.util.TypeMission;
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
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Retrofit;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

import static com.custu.project.walktogether.util.ConfigService.RADIUS_MISSION;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, LocationListener, SensorEventListener, StepListener, GoogleMap.OnMarkerClickListener {
    private static final int REQUEST_PERMISSION_LOCATION = 255;
    private static final int MY_PERMISSIONS_REQUEST_FINE_LOCATION = 111;
    private final static int CONNECTION_FAILURE_RESOLUTION_REQUEST = 9000;
    private static final String TEXT_NUM_STEPS = "Number of Steps: ";
    @Nullable
    private GoogleMap googleMap;
    private LatLng origin;
    private LatLng destination;
    private List<LatLng> wayPoints;
    private ArrayList<Mission> missionArrayList;
    private GoogleApiClient mGoogleApiClient;
    private LocationRequest mLocationRequest;
    private double currentLatitude;
    private double currentLongitude;
    private ArrayList<LatLng> routePoints;
    private StepDetector simpleStepDetector;
    private int numSteps;
    private SensorManager sensorManager;
    private Sensor angle;
    private List<Step> stepList;
    private int count;
    private Long mapId;
    private ProgressDialog progressDialog;
    private boolean isArrive = false;

    private SupportMapFragment mapFragment;
    private LinearLayout parentPanel;

    private Date startDate;
    private JsonObject directionJsonObject;
    private int distanceMeter;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        if (!NetworkUtil.isLocationEnabled(MapsActivity.this)) {
            Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
            DialogUtil.getInstance().showDialogStartIntent(MapsActivity.this, getString(R.string.open_location), intent);
        }

        mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        parentPanel = findViewById(R.id.parentPanel);

        startDate = new Date();

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
        initLocation();
        initProgressDialog();
        initStepCounter();
        initPositionMission();
        initMap();
    }

    private void initStepCounter() {
        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        angle = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        simpleStepDetector = new StepDetector();
        simpleStepDetector.registerListener(this);

        numSteps = 0;
        count = 0;
        sensorManager.registerListener(MapsActivity.this, angle, SensorManager.SENSOR_DELAY_FASTEST);
    }

    private void initLocation() {
        mapId = getIntent().getLongExtra("mapId", 0);
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
        progressDialog.show();
        JsonObject jsonObject = MissionModel.getInstance().mappingMission(origin, wayPoints, destination);
        ConnectServer.getInstance().post(new OnDataSuccessListener() {
            @Override
            public void onResponse(JsonObject object, Retrofit retrofit) {
                progressDialog.dismiss();
                if (object != null) {
                    onDirectionSuccess(new Gson().fromJson(object.get("data"), Direction.class), new Gson().toJson(object.get("data")));
                }

            }

            @Override
            public void onBodyError(ResponseBody responseBodyError) {
                progressDialog.dismiss();
            }

            @Override
            public void onBodyErrorIsNull() {
                progressDialog.dismiss();
            }

            @Override
            public void onFailure(Throwable t) {
                progressDialog.dismiss();
            }
        }, ConfigService.DIRECTION, jsonObject);
    }

    private void initPositionMission() {
        missionArrayList = UserManager.getInstance(MapsActivity.this).getMission();
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

    public void onDirectionSuccess(Direction direction, String rawBody) {
        if (direction.isOK()) {
            setDetailDirection(rawBody);
            Route route = direction.getRouteList().get(0);
            int legCount = route.getLegList().size();
            for (int index = 0; index < legCount; index++) {
                Leg leg = route.getLegList().get(index);
                googleMap.addMarker(new MarkerOptions()
                        .icon(BitmapDescriptorFactory.fromBitmap(resizeMarker(R.drawable.marker)))
                        .position(leg.getStartLocation().getCoordination()))
                        .setTag(index);
                stepList = leg.getStepList();
                ArrayList<PolylineOptions> polylineOptionList = DirectionConverter.createTransitPolyline(this, stepList, 5, Color.parseColor("#3e8aed"), 5, Color.parseColor("#3e8aed"));
                for (PolylineOptions polylineOption : polylineOptionList) {
                    googleMap.addPolyline(polylineOption);
                }
            }
            setCameraWithCoordinationBounds(route);
        }
    }

    private void setCameraWithCoordinationBounds(Route route) {
        LatLng southwest = route.getBound().getSouthwestCoordination().getCoordination();
        LatLng northeast = route.getBound().getNortheastCoordination().getCoordination();
        LatLngBounds bounds = new LatLngBounds(southwest, northeast);
        googleMap.animateCamera(CameraUpdateFactory.newLatLngBounds(bounds, 100));
        //googleMap.getUiSettings().setScrollGesturesEnabled(false);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        googleMap.setMyLocationEnabled(true);
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
            currentLatitude = location.getLatitude();
            currentLongitude = location.getLongitude();
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
        sensorManager.registerListener(MapsActivity.this, angle, SensorManager.SENSOR_DELAY_FASTEST);
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (mGoogleApiClient.isConnected()) {
            LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient, this);
            mGoogleApiClient.disconnect();
        }
        sensorManager.unregisterListener(this);
    }

    @Override
    public void onLocationChanged(Location location) {
        currentLatitude = location.getLatitude();
        currentLongitude = location.getLongitude();
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
        if (googleMap != null) {
            numSteps++;
            LatLng latLng = new LatLng(currentLatitude, currentLongitude);
            PolylineOptions pOptions = new PolylineOptions()
                    .width(18)
                    .color(Color.GREEN)
                    .geodesic(true);
            routePoints.add(latLng);

            for (int z = 0; z < routePoints.size(); z++) {
                LatLng point = routePoints.get(z);
                pOptions.add(point);
            }

            googleMap.addPolyline(pOptions);
            routePoints.add(latLng);

            for (int i = 0; i < missionArrayList.size(); i++) {
                if (isPlayMission(currentLatitude,
                        currentLongitude,
                        missionArrayList.get(i).getPosition().getLatitude(),
                        missionArrayList.get(i).getPosition().getLongitude())) {
                    isArrive = true;
                    Snackbar.make(parentPanel, R.string.arrive_middion, Snackbar.LENGTH_SHORT).show();
                    break;
                } else {
                    isArrive = false;
                }
            }
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_FINE_LOCATION: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // permission was granted

                } else {
                    // permission was denied
                }
                return;
            }
        }
    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        if (isArrive) {
            int index = (int) marker.getTag();

            marker.remove();
            String typeMission = missionArrayList.get(index).getMissionDetail().getType();
            Intent intent;
            switch (typeMission) {
                case TypeMission.BOX:
                    intent = new Intent(MapsActivity.this, MissionBoxActivity.class);
                    intent.putExtra("mission", new Gson().toJson(missionArrayList.get(index)));
                    intent.putExtra("index", index);
                    startActivityForResult(intent, 1);
                    break;
                case TypeMission.CLOCK:
                    intent = new Intent(MapsActivity.this, MissionClockActivity.class);
                    intent.putExtra("mission", new Gson().toJson(missionArrayList.get(index)));
                    intent.putExtra("index", index);
                    startActivityForResult(intent, 1);
                    break;
                case TypeMission.EMOTION:
                    intent = new Intent(MapsActivity.this, MissionEmotionActivity.class);
                    intent.putExtra("mission", new Gson().toJson(missionArrayList.get(index)));
                    intent.putExtra("index", index);
                    startActivityForResult(intent, 1);
                    break;
                case TypeMission.PROVERB:
                    intent = new Intent(MapsActivity.this, MissionProverbsActivity.class);
                    intent.putExtra("mission", new Gson().toJson(missionArrayList.get(index)));
                    intent.putExtra("index", index);
                    startActivityForResult(intent, 1);
                    break;
                case TypeMission.TYPEGROUP:
                    intent = new Intent(MapsActivity.this, MissionTypegroupActivity.class);
                    intent.putExtra("mission", new Gson().toJson(missionArrayList.get(index)));
                    intent.putExtra("index", index);
                    startActivityForResult(intent, 1);
                    break;
            }
        }
        return false;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == 1) {
            if (resultCode == RESULT_OK) {
                isArrive = false;
                count++;
                if (count < 4) {
                    int index = data.getIntExtra("index", 0);
                    boolean isComplete = data.getBooleanExtra("isComplete", false);
                    Position position = missionArrayList.get(index).getPosition();

                    if (isComplete) {
                        googleMap.addMarker(new MarkerOptions()
                                .icon(BitmapDescriptorFactory.fromBitmap(resizeMarker(R.drawable.marker_complete)))
                                .position(new LatLng(position.getLatitude(), position.getLongitude())))
                                .setTag(index);
                    } else {
                        googleMap.addMarker(new MarkerOptions()
                                .icon(BitmapDescriptorFactory.fromBitmap(resizeMarker(R.drawable.marker_fail)))
                                .position(new LatLng(position.getLatitude(), position.getLongitude())))
                                .setTag(index);
                    }
                } else {
                    sendMission();
                }
            }
        }
    }

    private Bitmap resizeMarker(int id) {
        int height = 115;
        int width = 85;
        BitmapDrawable drawable = (BitmapDrawable) getResources().getDrawable(id);
        Bitmap bitmap = drawable.getBitmap();
        return Bitmap.createScaledBitmap(bitmap, width, height, false);
    }

    private void sendMission() {
        progressDialog.show();
        Long resultTime = new Date().getTime() - startDate.getTime();
        JsonObject jsonObject = StoreMission.getInstance().getAllMission(mapId, new Gson().toJson(routePoints), resultTime, distanceMeter);
        ConnectServer.getInstance().post(new OnDataSuccessListener() {
            @Override
            public void onResponse(JsonObject object, Retrofit retrofit) {
                progressDialog.cancel();
                if (object != null) {
                    if (object.get("status").getAsInt() == 200) {
                        Intent intent = new Intent(MapsActivity.this, MissionCompleteActivity.class);
                        PatientGame patientGame = MissionModel.getInstance().getPatientGame(object);
                        intent.putExtra("time", patientGame.getTime());
                        intent.putExtra("distance", patientGame.getDistance());
                        intent.putExtra("resultScore", patientGame.getResultScore());
                        startActivity(intent);
                        finish();
                    }
                }
            }

            @Override
            public void onBodyError(ResponseBody responseBodyError) {

            }

            @Override
            public void onBodyErrorIsNull() {

            }

            @Override
            public void onFailure(Throwable t) {

            }
        }, ConfigService.MISSION + ConfigService.MISSION_SEND + UserManager.getInstance(MapsActivity.this).getPatient().getId(), jsonObject);
    }

    private void initProgressDialog() {
        progressDialog = new ProgressDialog(MapsActivity.this);
        progressDialog.setTitle(getString(R.string.loading));
        progressDialog.setCanceledOnTouchOutside(false);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.cancel();
        }
        if (mGoogleApiClient.isConnected()) {
            LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient, this);
            mGoogleApiClient.disconnect();
        }
        sensorManager.unregisterListener(this);
    }

    private boolean isPlayMission(double lat1, double lon1, double lat2, double lon2) {
        double theta = lon1 - lon2;
        double dist = Math.sin(deg2rad(lat1))
                * Math.sin(deg2rad(lat2))
                + Math.cos(deg2rad(lat1))
                * Math.cos(deg2rad(lat2))
                * Math.cos(deg2rad(theta));
        dist = Math.acos(dist);
        dist = dist * 180.0 / Math.PI;
        dist = dist * 60 * 1.1515 * 1000;
        return dist < RADIUS_MISSION;
    }

    private double deg2rad(double deg) {
        return (deg * Math.PI / 180.0);
    }

    private void setDetailDirection(String direction) {
        JsonParser parser = new JsonParser();
        directionJsonObject = parser.parse(direction).getAsJsonObject();
        distanceMeter = directionJsonObject.getAsJsonArray("routes").get(0)
                .getAsJsonObject().get("legs").getAsJsonArray().get(0)
                .getAsJsonObject().get("distance")
                .getAsJsonObject().get("value").getAsInt();
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(base));
    }

    @Override
    public void onBackPressed() {
        showDialogExitMission();
    }

    public void showDialogExitMission() {
        final Dialog dialog = new Dialog(MapsActivity.this);
        if (dialog.isShowing())
            dialog.cancel();
        dialog.setContentView(R.layout.dialog_exit);
        dialog.setCancelable(false);
        dialog.setCanceledOnTouchOutside(true);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

        LinearLayout done = dialog.findViewById(R.id.submit);
        LinearLayout cancel = dialog.findViewById(R.id.cancel);
        TextView title = dialog.findViewById(R.id.title);
        TextView detail = dialog.findViewById(R.id.detail);
        title.setText(MapsActivity.this.getResources().getString(R.string.exit_mission_title));
        detail.setText(MapsActivity.this.getResources().getString(R.string.exit_mission));
        done.setOnClickListener(view -> {
            StoreMission.getInstance().destroyMission();
            finish();
            dialog.dismiss();
        });

        cancel.setOnClickListener(view -> dialog.dismiss());
        dialog.show();
    }

}
