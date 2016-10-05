package toe.com.toedailyandroid.Activity;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import pl.droidsonroids.gif.GifDrawable;
import pl.droidsonroids.gif.GifImageView;
import toe.com.toedailyandroid.R;
import toe.com.toedailyandroid.Utils.TDAHttpClient;
import toe.com.toedailyandroid.Utils.WeahterTextImgConverter;

/**
 * Created by HQu on 9/27/2016.
 */

public class HomeFragment extends Fragment implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {

    private static final String TAG = "ToeHomeFragment:";
    private GoogleApiClient mGoogleApiClient;
    private Location mLocation;
    private Button mBtn;
    private RequestQueue mRequestQueue;
    private TextView mLocationTV;
//    private ImageView mWeatherIcon;
    private GifImageView mWeatherGif;
    private TextView mTodaysTemperatureTV;
    private TextView mTodaysHumidityTV;
    private TextView mTodaysWindTV;
    private TextView mTodaysWeatherConditionsTV;

    private ImageView mTomorrowConditionsIM;
    private TextView mTomorrowTemperatureTV;
    private ImageView mTwoDayConditionsIM;
    private TextView mTwoDayDateTV;
    private TextView  mTwoDayTempTV;
    private ImageView mThreeDayConditionsIM;
    private TextView mThreeDayDateTV;
    private TextView  mThreeDayTempTV;

    private String mTodaysWeather;
    private JsonParser mJsonParser;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mJsonParser = new JsonParser();
        if (mGoogleApiClient == null) {
            mGoogleApiClient = new GoogleApiClient.Builder(getActivity())
                    .addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this)
                    .addApi(LocationServices.API).build();
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_home_fragment,container,false);
        mBtn = (Button) view.findViewById(R.id.button);
        mLocationTV = (TextView)view.findViewById(R.id.location_text);
//        mWeatherIcon = (ImageView)view.findViewById(R.id.weather_img);
        mWeatherGif = (GifImageView)view.findViewById(R.id.weather_img);
        mTodaysTemperatureTV = (TextView)view.findViewById(R.id.todays_temperature);
        mTodaysHumidityTV = (TextView)view.findViewById(R.id.todays_humidity);
        mTodaysWindTV = (TextView)view.findViewById(R.id.todays_wind);
        mTodaysWeatherConditionsTV = (TextView)view.findViewById(R.id.weather_conditions);
        mTomorrowConditionsIM = (ImageView) view.findViewById(R.id.tomorrow_conditions);
        mTomorrowTemperatureTV = (TextView)view.findViewById(R.id.tomorrow_temp);
        mTwoDayConditionsIM = (ImageView) view.findViewById(R.id.two_day_conditions);
        mTwoDayDateTV = (TextView) view.findViewById(R.id.two_day_date);
        mTwoDayTempTV = (TextView)view.findViewById(R.id.two_day_temp);
        mThreeDayConditionsIM = (ImageView) view.findViewById(R.id.three_day_conditions);
        mThreeDayDateTV = (TextView) view.findViewById(R.id.three_day_date);
        mThreeDayTempTV = (TextView)view.findViewById(R.id.three_day_temp);

        mBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(TAG, "Clicked!");
                mGoogleApiClient.connect();
            }
        });
        return view;
    }

    @Override
    public void onStart() {
        initRequest();
        super.onStart();
    }

    @Override
    public void onStop() {
        mGoogleApiClient.disconnect();
        if (mRequestQueue != null) {
            mRequestQueue.cancelAll(TAG);
        }
        super.onStop();
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        Log.i(TAG, "Connected");
        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        mLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
        if(mLocation != null) {
            Log.i(TAG, String.valueOf(mLocation.getLatitude()));
            Log.i(TAG, String.valueOf(mLocation.getLongitude()));
        }
    }

    @Override
    public void onConnectionSuspended(int i) {
        Toast.makeText(getActivity(), "Connected Suspended!", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Toast.makeText(getActivity(), connectionResult.getErrorMessage(), Toast.LENGTH_SHORT).show();
    }

    public void initRequest() {
        mRequestQueue = Volley.newRequestQueue(getActivity());
        String url = "http://api.wunderground.com/api/7ae3df327a9e5b28/geolookup/conditions/q/38.948449,-77.342215.json";
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                JsonObject displayLocationObj  = mJsonParser.parse(response).getAsJsonObject().getAsJsonObject("current_observation").getAsJsonObject("display_location");
                mLocationTV.setText(displayLocationObj.get("full").getAsString());
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getActivity(), error.getMessage().toString(), Toast.LENGTH_SHORT).show();
            }
        });

        String fcUrl = "http://api.wunderground.com/api/7ae3df327a9e5b28/forecast/q/38.948449,-77.342215.json";
        StringRequest fcStringRequest = new StringRequest(Request.Method.GET, fcUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                JsonArray fcArray = mJsonParser.parse(response).getAsJsonObject().getAsJsonObject("forecast").getAsJsonObject("simpleforecast").getAsJsonArray("forecastday");

//                Display today's weather
                mTodaysWeather = fcArray.get(0).getAsJsonObject().get("icon").getAsString();
                mTodaysWeatherConditionsTV.setText(fcArray.get(0).getAsJsonObject().get("conditions").getAsString());
                Log.i(TAG, mTodaysWeather);

                mWeatherGif.setImageResource(WeahterTextImgConverter.convertToImg(mTodaysWeather));

                mTodaysTemperatureTV.setText(fcArray.get(0).getAsJsonObject().getAsJsonObject("low").get("fahrenheit").getAsString()+" F - "+fcArray.get(0).getAsJsonObject().getAsJsonObject("high").get("fahrenheit").getAsString()+" F");
                mTodaysHumidityTV.setText(fcArray.get(0).getAsJsonObject().get("avehumidity").getAsString());
                mTodaysWindTV.setText(fcArray.get(0).getAsJsonObject().getAsJsonObject("maxwind").get("mph").getAsString()+" mph");

//                Forecast weather is 3 days
                mTomorrowConditionsIM.setImageResource(WeahterTextImgConverter.convertToImg(fcArray.get(1).getAsJsonObject().get("conditions").getAsString()));
                mTomorrowTemperatureTV.setText(fcArray.get(1).getAsJsonObject().getAsJsonObject("low").get("fahrenheit").getAsString()+" F - "+fcArray.get(1).getAsJsonObject().getAsJsonObject("high").get("fahrenheit").getAsString()+" F");
                mTwoDayConditionsIM.setImageResource(WeahterTextImgConverter.convertToImg(fcArray.get(2).getAsJsonObject().get("conditions").getAsString()));
                mTwoDayDateTV.setText(fcArray.get(2).getAsJsonObject().getAsJsonObject("date").get("month").getAsString()+"/"+fcArray.get(2).getAsJsonObject().getAsJsonObject("date").get("day").getAsString()+" "+fcArray.get(2).getAsJsonObject().getAsJsonObject("date").get("weekday").getAsString());
                mTwoDayTempTV.setText(fcArray.get(2).getAsJsonObject().getAsJsonObject("low").get("fahrenheit").getAsString()+" F - "+fcArray.get(2).getAsJsonObject().getAsJsonObject("high").get("fahrenheit").getAsString()+" F");
                mThreeDayConditionsIM.setImageResource(WeahterTextImgConverter.convertToImg(fcArray.get(3).getAsJsonObject().get("conditions").getAsString()));
                mThreeDayDateTV.setText(fcArray.get(3).getAsJsonObject().getAsJsonObject("date").get("month").getAsString()+"/"+fcArray.get(3).getAsJsonObject().getAsJsonObject("date").get("day").getAsString()+" "+fcArray.get(3).getAsJsonObject().getAsJsonObject("date").get("weekday").getAsString());
                mThreeDayTempTV.setText(fcArray.get(3).getAsJsonObject().getAsJsonObject("low").get("fahrenheit").getAsString()+" F - "+fcArray.get(3).getAsJsonObject().getAsJsonObject("high").get("fahrenheit").getAsString()+" F");

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getActivity(), error.getMessage().toString(), Toast.LENGTH_SHORT).show();
            }
        });

        mRequestQueue.add(stringRequest);
        mRequestQueue.add(fcStringRequest);
    }

}
