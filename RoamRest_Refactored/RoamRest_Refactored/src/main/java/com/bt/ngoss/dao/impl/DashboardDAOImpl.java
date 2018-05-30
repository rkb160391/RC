package com.bt.ngoss.dao.impl;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import com.bt.ngoss.dao.AbstractDAO;
import com.bt.ngoss.dao.DashboardDAO;
import com.bt.ngoss.model.report.Dashboard;
import com.bt.ngoss.vo.UserTemplateVO;

@Repository
public class DashboardDAOImpl extends AbstractDAO<Dashboard> implements DashboardDAO {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1431525346696126209L;

	@Override
	public List<Dashboard> getUserDashboards(UserTemplateVO userTemplateVO) throws Exception {
		StringBuilder selectQuery = new StringBuilder();
		selectQuery.append("from Dashboard db ");
		StringBuilder whereQuery = new StringBuilder();
		if (null != userTemplateVO) {
			whereQuery.append(" where ");
			if (!StringUtils.isEmpty(userTemplateVO.getUserId())) {
				whereQuery.append(" db.key.userId=").append(userTemplateVO.getUserId() + "");
			} else {
				throw new Exception("userId can not be Null");
			}
			selectQuery.append(whereQuery);
		}
		List<Dashboard> dashboardList = executeQuery(selectQuery.toString());
		return dashboardList;
	}

	/*@Override
	public Dashboard getUserDashboard(String dashboardId, Long userId) throws Exception {
		StringBuilder selectQuery = new StringBuilder();
		selectQuery.append(" from Dashboard db ");

		StringBuilder whereQuery = new StringBuilder();

		whereQuery.append("where ");

		if (null != dashboardId || !StringUtils.isEmpty(dashboardId)) {
			whereQuery.append(" db.dashboardId='").append(dashboardId + "'");
		} else {
			throw new Exception(" dashBoardId can not be Null");
		}

		if (null != userId || !StringUtils.isEmpty(userId)) {
			whereQuery.append("and db.userDetails.id='").append(userId + "'");
		} else {
			throw new Exception("userId can not be Null");
		}

		selectQuery.append(whereQuery);
		return executeQueryForUniqueRecord(selectQuery.toString());
	}*/

	@Override
	public Dashboard addDashboard(Dashboard dashboard) {
		save(dashboard);
		return dashboard;
	}

	@Override
	public Dashboard updateDashboard(Dashboard dashboard) {
		update(dashboard);
		return dashboard;
	}

	@Override
	public Dashboard getDashboardType(String userId) {
		String query = "from Dashboard where userName = " + userId;
		return executeQueryForUniqueRecord(query);
	}

	@Override
	public void deleteDashboard(Dashboard dashboard) {
		delete(dashboard);
	}

	@Override
	public void updateDashboard(List<Dashboard> dashboards) {
		updateList(dashboards);
	}

	@Override
	public List<Dashboard> getDeletedMarkDashboard() {
		StringBuilder deleteQuery = new StringBuilder();
		deleteQuery.append("from Dashboard  d");
		deleteQuery.append(" Where deleted = true");

		return executeQuery(deleteQuery.toString());
	}

	@Override
	public List<Dashboard> getDashboardsAccodingToType(Map<String, Set> values) throws Exception {
		return selectWithInClause(values, Dashboard.class);
	}



	@Override
	public void deleteMarkDashboard(List<Dashboard> markDeletDashboardList) {
		deleteList(markDeletDashboardList);

	}

	@Override
	public Dashboard getUserDashboard(long userId) throws Exception {
		// TODO Auto-generated method stub
		StringBuilder selectQuery = new StringBuilder();
		selectQuery.append(" from Dashboard db ");

		StringBuilder whereQuery = new StringBuilder();

		whereQuery.append("where ");
		
		if (!StringUtils.isEmpty(userId)) {
			whereQuery.append("db.key.userId='").append(userId + "'");
		} else {
			throw new Exception("userId can not be Null");
		}

		selectQuery.append(whereQuery);
		return executeQueryForUniqueRecord(selectQuery.toString());
	
	}
	@Override
	public Dashboard getUserDashboard(Long userId,String dashboardId) throws Exception {
		// TODO Auto-generated method stub
		StringBuilder selectQuery = new StringBuilder();
		selectQuery.append(" from Dashboard db ");

		StringBuilder whereQuery = new StringBuilder();

		whereQuery.append("where ");
		
		if (!StringUtils.isEmpty(userId)) {
			whereQuery.append("db.key.userId='").append(userId + "' and db.key.dashboardId='").append(dashboardId+"'");
		} else {
			throw new Exception("userId can not be Null");
		}

		selectQuery.append(whereQuery);
		return executeQueryForUniqueRecord(selectQuery.toString());
	
	}

}
