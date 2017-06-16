package ru.nway.myweather.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.places.AutocompleteFilter;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceAutocomplete;
import ru.nway.myweather.R;
import ru.nway.myweather.util.RequestCode;

import static android.app.Activity.RESULT_CANCELED;
import static android.app.Activity.RESULT_OK;
import static android.content.ContentValues.TAG;
import static com.google.android.gms.location.places.AutocompleteFilter.TYPE_FILTER_CITIES;

public class NewCityFragment extends Fragment {
    private Button mOkButton;
    private Activity mActivity;
    private Button mSearchButton;
    //private Button mGPSButton;
    private String currentCityName;
    private double[] currentCoords;

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        mActivity = getActivity();
        super.onActivityCreated(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_new_city, container, false);

        mOkButton = (Button)view.findViewById(R.id.okButton);
        mOkButton.setOnClickListener(okListener);

        mSearchButton = (Button)view.findViewById(R.id.searchCityButton);
        mSearchButton.setOnClickListener(searchButtonListener);

        //mGPSButton = (Button)view.findViewById(R.id.GPSButton);

        return view;
    }

    @Override
    public void onPause()
    {
        currentCityName = "";
        currentCoords = new double[2];
        mSearchButton.setText("");
        //mGPSButton.setText("");
        super.onPause();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        if (requestCode == 228) {
            if (resultCode == RESULT_OK)
            {
                Place place = PlaceAutocomplete.getPlace(mActivity, data);

                currentCityName = place.getName().toString();
                mSearchButton.setText(currentCityName);
                currentCoords[0] = place.getLatLng().latitude;
                currentCoords[1] = place.getLatLng().longitude;

                Log.i(TAG, "Place: " + place.getName() + " Lat: " + currentCoords[0] + " Lon: " + currentCoords[1]);
            }

            else if (resultCode == PlaceAutocomplete.RESULT_ERROR)
            {
                Status status = PlaceAutocomplete.getStatus(mActivity, data);
                Toast.makeText(mActivity, "PlaceAutocomplete.RESULT_ERROR", Toast.LENGTH_SHORT).show();
                Log.i(TAG, status.getStatusMessage());
            }

            else if (resultCode == RESULT_CANCELED)
            {
                // The user canceled the operation.
            }
        }

    }

    View.OnClickListener okListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (!currentCityName.equals(""))
                Controller.addCity(currentCityName, currentCoords[0], currentCoords[1]);

            ((FragmentCallback)mActivity).fragmentCallback(RequestCode.CALL_RECYCLER);
        }
    };

    View.OnClickListener searchButtonListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            //TODO: Перенести PLACE_AUTOCOMPLETE_REQUEST_CODE в RequestCode и дать ему нормальный номер
            int PLACE_AUTOCOMPLETE_REQUEST_CODE = 228;
            try
            {
                AutocompleteFilter filter = new AutocompleteFilter.Builder()
                        .setTypeFilter(TYPE_FILTER_CITIES)
                        .build();

                Intent intent = new PlaceAutocomplete.IntentBuilder(PlaceAutocomplete.MODE_FULLSCREEN)
                        .setFilter(filter)
                        .build(mActivity);
                startActivityForResult(intent, PLACE_AUTOCOMPLETE_REQUEST_CODE);
            } catch (GooglePlayServicesRepairableException e) {
                e.printStackTrace();

            } catch (GooglePlayServicesNotAvailableException e) {
                e.printStackTrace();
            }

        }
    };


}
