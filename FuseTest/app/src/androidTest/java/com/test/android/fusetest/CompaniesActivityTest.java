package com.test.android.fusetest;

import android.test.ActivityInstrumentationTestCase2;
import android.test.UiThreadTest;
import android.test.suitebuilder.annotation.SmallTest;
import android.widget.Button;
import android.widget.EditText;

import com.test.android.fusetest.activities.CompaniesActivity;
import com.test.android.fusetest.dal.models.communication.response.CompanyResponseModel;

public class CompaniesActivityTest
            extends
        ActivityInstrumentationTestCase2<CompaniesActivity>{

    private EditText mNameEditText;
    CompaniesActivity mCompaniesActivity;
    private Button mRetryButton;

    public CompaniesActivityTest() {
        super(CompaniesActivity.class);
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();

        System.setProperty("dexmaker.dexcache", getInstrumentation()
                .getTargetContext().getCacheDir().getPath());

        mCompaniesActivity = getActivity();

        mNameEditText = (EditText) mCompaniesActivity
                .findViewById(R.id.nameEditText);
        mRetryButton = (Button) mCompaniesActivity.findViewById(R.id.retryButton);
    }

    @Override
    protected void tearDown() throws Exception {
        super.tearDown();
    }

    @UiThreadTest
    @SmallTest
    public void testCorrectCompanyName() {
        CompanyResponseModel companyResponseModel = new CompanyResponseModel();
        companyResponseModel
                .setLogo("http://fusion.fusion-universal.com/media/W1siZiIsIjIwMTUvMDkvMTcvMTMvMTMvNTMvNjczL0Z1c2VMb2dvX3gyLnBuZyJdLFsicCIsInRodW1iIiwiMTgweDY0Il1d?sha=b114283d")
                .setName("Fuse");

        mNameEditText.setText("fusion");

        assertEquals(mNameEditText.getText().toString().isEmpty(), false);

        mCompaniesActivity.updateScreenData(companyResponseModel);
    }

    @UiThreadTest
    @SmallTest
    public void testIncorrectCompanyName() {
        mNameEditText.setText("test");

        assertEquals(mNameEditText.getText().toString().isEmpty(), false);

        mCompaniesActivity.updateScreenData(null);
    }

    @UiThreadTest
    @SmallTest
    public void testEmptyEditText() {
        mNameEditText.setText("");

        assertEquals(mNameEditText.getText().toString().isEmpty(), true);
    }

    @UiThreadTest
    @SmallTest
    public void testRetry() {
        mRetryButton.performClick();
    }

}
