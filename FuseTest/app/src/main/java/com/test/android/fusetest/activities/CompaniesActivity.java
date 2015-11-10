package com.test.android.fusetest.activities;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.test.android.fusetest.R;
import com.test.android.fusetest.application.ApplicationService;
import com.test.android.fusetest.bll.communications.ConnectionHelper;
import com.test.android.fusetest.dal.models.communication.response.CompanyResponseModel;

import java.util.Dictionary;

import ro.cvu.connection.mvc.system.IReceiver;

public class CompaniesActivity extends Activity implements IReceiver {
    private static final String LOG_TAG = ApplicationService.APP_NAME
            + CompaniesActivity.class.getSimpleName();

    private EditText mNameEditText;
    private ImageView mIconImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_companies);

        mNameEditText = (EditText) findViewById(R.id.nameEditText);
        mIconImageView = (ImageView) findViewById(R.id.iconImageView);

        mNameEditText.setOnKeyListener(new View.OnKeyListener() {
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if ((event.getAction() == KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_ENTER)) {
                    if (!mNameEditText.getText().toString().isEmpty())
                        ConnectionHelper.getInstance().getCompanies(CompaniesActivity.this,
                                mNameEditText.getText().toString().replaceAll("\\s+", "")
                        );
                    else
                        Toast.makeText(CompaniesActivity.this,
                                getString(R.string.emptyText),
                                Toast.LENGTH_LONG)
                                .show();
                }
                return true;
            }
        });

        ((Button) findViewById(R.id.retryButton)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mNameEditText.setBackgroundColor(ContextCompat.getColor(CompaniesActivity.this, R.color.white));
                mNameEditText.getText().clear();

                mIconImageView.setImageDrawable(null);
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_companies, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void ReceivedData(Dictionary<Integer, Object> dictionary) {
        CompanyResponseModel companyResponseModel =
                (CompanyResponseModel) dictionary.get(ConnectionHelper.RESTAURANTS);

        if (companyResponseModel != null){
            mNameEditText.setText(companyResponseModel.getName());
            mNameEditText.setBackgroundColor(ContextCompat.getColor(this, R.color.green));

            Picasso.with(this).load(companyResponseModel.getLogo()).into(mIconImageView);
        }else{
            mNameEditText.setBackgroundColor(ContextCompat.getColor(this, R.color.red));

            Toast.makeText(this,
                    getString(R.string.errorRequestCompany),
                    Toast.LENGTH_LONG)
                    .show();
            Log.d(LOG_TAG, getString(R.string.errorRequestCompany));
        }
    }
}
