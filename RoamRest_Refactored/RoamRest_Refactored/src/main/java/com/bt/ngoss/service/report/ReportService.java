package com.bt.ngoss.service.report;

import com.bt.ngoss.model.report.UserReport;
import com.bt.ngoss.vo.UserTemplateVO;

public interface ReportService {

	public UserReport getDashboardReports(UserTemplateVO userTemplateVO) throws Exception;

	public UserReport getSpecificUserReports(UserTemplateVO userTemplateVO) throws Exception;

	
}