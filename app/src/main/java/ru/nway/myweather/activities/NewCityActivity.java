package ru.nway.myweather.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import ru.nway.myweather.R;
import ru.nway.myweather.util.RequestCode;

public class NewCityActivity extends AppCompatActivity {

    private Button mOkButton;
    private Button mCancelButton;
    private EditText mCityNameEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_city);

        mOkButton = (Button)findViewById(R.id.okButton);
        mOkButton.setOnClickListener(okListener);

        mCancelButton = (Button)findViewById(R.id.cancelButton);
        mCancelButton.setOnClickListener(cancelListener);

        mCityNameEditText = (EditText)findViewById(R.id.cityNameEditText);
    }

    View.OnClickListener okListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent();
            intent.putExtra("city", mCityNameEditText.getText().toString());
            setResult(RequestCode.REQUEST_CODE_NEW_CITY, intent);
            finish();
        }
    };

    View.OnClickListener cancelListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            finish();
        }
    };
}
