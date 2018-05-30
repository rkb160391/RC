package com.bt.ngoss.service.report.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bt.ngoss.dao.DashboardDAO;
import com.bt.ngoss.model.report.Dashboard;
import com.bt.ngoss.service.report.DashboardService;
import com.bt.ngoss.vo.UserTemplateVO;

@Service
@Transactional
public class DashboardServiceImpl implements DashboardService {

	@Autowired
	DashboardDAO dashboardDao;



	@Override
	public List<Dashboard> getDashboards(UserTemplateVO userTemplateVO) throws Exception {

		List<Dashboard> listDashboards = dashboardDao.getUserDashboards(userTemplateVO);

		return listDashboards;
	}

	@Override
	public Dashboard getDashboard(UserTemplateVO userTemplateVO) throws Exception {

		Dashboard listDashboards = dashboardDao.getUserDashboard(Long.valueOf(userTemplateVO.getUserId()),userTemplateVO.getDashboardId());

		return listDashboards;
	}

}
