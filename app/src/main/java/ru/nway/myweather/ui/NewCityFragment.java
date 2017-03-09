package ru.nway.myweather.ui;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import ru.nway.myweather.R;
import ru.nway.myweather.util.RequestCode;

public class NewCityFragment extends Fragment {
    private Button mOkButton;
    private Button mCancelButton;
    private EditText mCityNameEditText;
    private Activity mActivity;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        Activity mActivity = (MainActivity)context;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_new_city, container, false);

        mOkButton = (Button)getView().findViewById(R.id.okButton);
        mOkButton.setOnClickListener(okListener);

        mCancelButton = (Button)getView().findViewById(R.id.cancelButton);
        mCancelButton.setOnClickListener(cancelListener);

        mCityNameEditText = (EditText)getView().findViewById(R.id.cityNameEditText);

        return view;
    }

    View.OnClickListener okListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            String city = mCityNameEditText.getText().toString();
            Controller.addCity(city);
            ((FragmentCallback)mActivity).fragmentCallback(RequestCode.CALL_RECYCLER);
        }
    };

    View.OnClickListener cancelListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            ((FragmentCallback)mActivity).fragmentCallback(RequestCode.CALL_RECYCLER);

        }
    };
}
