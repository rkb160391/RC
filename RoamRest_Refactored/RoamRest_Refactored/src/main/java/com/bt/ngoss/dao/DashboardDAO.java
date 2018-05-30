package com.bt.ngoss.dao;

import java.util.List;
import java.util.Map;
import java.util.Set;

import com.bt.ngoss.model.report.Dashboard;
import com.bt.ngoss.vo.UserTemplateVO;

public interface DashboardDAO {

	public List<Dashboard> getUserDashboards(UserTemplateVO userTemplateVO) throws Exception;

	public Dashboard getUserDashboard(Long userId, String dashboardId) throws Exception;

	public Dashboard addDashboard(Dashboard dashboard);

	public Dashboard getDashboardType(String userId);

	public void deleteDashboard(Dashboard dashboard);

	public void update(Dashboard dashboard);

	public void updateDashboard(List<Dashboard> dashboards);

	public List<Dashboard> getDeletedMarkDashboard();

	Dashboard updateDashboard(Dashboard dashboard);

	List<Dashboard> getDashboardsAccodingToType(Map<String, Set> values) throws Exception;

	public void deleteMarkDashboard(List<Dashboard> markDeletDashboardList);

	public Dashboard getUserDashboard(long userId) throws Exception;
}
