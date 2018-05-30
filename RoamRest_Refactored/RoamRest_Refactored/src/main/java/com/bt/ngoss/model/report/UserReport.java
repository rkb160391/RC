package com.bt.ngoss.model.report;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

import com.bt.ngoss.model.UserDetails;

public class UserReport implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6105208486613507558L;

	private UserDetails userDetails;

	private List<DashboardReport> dashboardReports;

	public UserDetails getUserDetails() {
		return userDetails;
	}

	public void setUserDetails(UserDetails userDetails) {
		this.userDetails = userDetails;
	}

	public List<DashboardReport> getDashboardReports() {
		return dashboardReports;
	}

	public void setDashboardReports(List<DashboardReport> dashboardReports) {
		this.dashboardReports = dashboardReports;
	}

}
