package com.bt.ngoss.service.report;

import java.util.List;
import java.util.Set;

import com.bt.ngoss.model.UserDetails;
import com.bt.ngoss.model.report.Dashboard;
import com.bt.ngoss.vo.DashBoardVO;
import com.bt.ngoss.vo.UserTemplateVO;

public interface DashboardService {

	List<Dashboard> getDashboards(UserTemplateVO userTemplateVO) throws Exception;

	Dashboard getDashboard(UserTemplateVO userTemplateVO) throws Exception;

}
