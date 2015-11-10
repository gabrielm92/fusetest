package com.test.android.fusetest.bll.communications;

import android.content.Context;


import com.test.android.fusetest.dal.models.communication.response.CompanyResponseModel;

import java.util.HashMap;

import ro.cvu.connection.mvc.system.IReceiver;
import ro.cvu.connection.mvc.system.request.RequestHandler;
import ro.cvu.connection.mvc.system.request.RequestHandler.ExecutionType;
import ro.cvu.connection.mvc.system.request.RequestService;

/**
 * Helper class for registering all the RESTful operations (request side) needed
 * by the application and performing server requests on behalf of the activities
 * and services. The helper object will be instantiated once for all the
 * activities and services that require server connectivity.
 *
 */
public class ConnectionHelper {

	public static final int CONNECTION_TIMEOUT = 5000;
	public static final int DATA_TIMEOUT = 30000;

	public static final int RESTAURANTS = 1;

	private static ConnectionHelper mInstance = null;

	private static final String serverName = ".fusion-universal.com/api/v1/company.json";
	private String mCompanyName;

	/**
	 * Constructs a new ConnectionHelper mInstance.
	 *
	 */
	protected ConnectionHelper() {
		this.registerOperations();
	}

	/**
	 * 
	 * @return the server URL (https://serverName)
	 */
	public String getServerURL() {
		return "https://" + mCompanyName + serverName;
	}

	/**
	 * @return the ConnectionHelper mInstance
	 */
	public static ConnectionHelper getInstance() {
		if (mInstance == null) {
			mInstance = new ConnectionHelper();
		}

		return mInstance;
	}

	public void registerOperations() {
		RequestService.RegisterOperation(RESTAURANTS,
				CompanyResponseModel.class, getServerURL(),
				ExecutionType.Thread);
	}

	public void getCompanies(IReceiver caller, String companyName) {
		HashMap<String, Object> queryParams = new HashMap<>();

		mCompanyName = companyName;
		registerOperations();

		RequestHandler.DoRequest(caller, ConnectionHelper.RESTAURANTS,
				RequestHandler.HTTP_GET, queryParams);
	}

}
