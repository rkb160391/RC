package com.bt.ngoss.service.report.impl;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.bt.dyns.model.common.Attribute;
import com.bt.dyns.model.common.DruidProperty;
import com.bt.dyns.model.druid.request.ReportResponseUI;
import com.bt.ngoss.common.constant.Constant;
import com.bt.ngoss.common.helper.DruidHelper;
import com.bt.ngoss.model.inventory.Property;
import com.bt.ngoss.model.report.BaseLeveReport;
import com.bt.ngoss.model.report.Dashboard;
import com.bt.ngoss.model.report.DashboardReport;
import com.bt.ngoss.model.report.Report;
import com.bt.ngoss.model.report.UserReport;
import com.bt.ngoss.model.report.UserTemplate;
import com.bt.ngoss.model.response.DruidResponse;
import com.bt.ngoss.service.UserAdminService;
import com.bt.ngoss.service.inventory.PropertyCacheService;
import com.bt.ngoss.service.report.DashboardService;
import com.bt.ngoss.service.report.ReportService;
import com.bt.ngoss.service.report.UserTemplateService;
import com.bt.ngoss.vo.DashboardConfig;
import com.bt.ngoss.vo.UserTemplateVO;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.jsonwebtoken.lang.Collections;

@Service
@Transactional
public class ReportServiceimpl implements ReportService {
	List<Property> previousDruidpropertyList;

	@Autowired
	UserTemplateService userTemplateService;

	@Autowired
	UserAdminService userAdminService;

	@Autowired
	DruidHelper druidHelper;

	@Autowired
	DashboardService dashboardService;

	@Autowired
	PropertyCacheService propertyCacheService;

	ObjectMapper mapper = new ObjectMapper();

	/**
	 * 
	 */
	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = true)
	public UserReport getDashboardReports(UserTemplateVO userTemplateVO) throws Exception {
		UserReport userReport = new UserReport();
		List<DashboardReport> dashboardReports = new ArrayList<>();

		List<Dashboard> dashboardList = dashboardService.getDashboards(userTemplateVO);
		if (!dashboardList.isEmpty()) {
			for (Dashboard dashboard : dashboardList) {
				DashboardReport dashBoardReport = new DashboardReport();
				List<DashboardConfig> dashboardConfig = mapper.readValue(dashboard.getArrangement(),
						new TypeReference<List<DashboardConfig>>() {
						});
				dashBoardReport.setId(dashboard.getKey().getDashboardId());
				dashBoardReport.setName(dashboard.getName());
				dashBoardReport.setType(userTemplateVO.getUserName());
				dashBoardReport.setConfig(dashboardConfig);
				dashboardReports.add(dashBoardReport);
			}

		}
		userReport.setDashboardReports(dashboardReports);
		return userReport;
	}

	@Override
	public UserReport getSpecificUserReports(UserTemplateVO userTemplateVO) throws Exception {

		UserReport userreport = new UserReport();
		List<DashboardReport> dashboardReports = new ArrayList<>();
		if (null != userTemplateVO) {
			List<Report> reports = new ArrayList<>();
			Dashboard dashboard = dashboardService.getDashboard(userTemplateVO);
			DashboardReport dashBoardReport = new DashboardReport();
			dashBoardReport.setId(dashboard.getKey().getDashboardId());
			dashBoardReport.setName(dashboard.getName());
			dashBoardReport.setType(userTemplateVO.getUserName()); // priyanka :
																	// getting
													 				// username
																	// null here
			dashboardReports.add(dashBoardReport);

			Report report = new Report();


			
			DruidResponse druidResponse = new DruidResponse();
			druidResponse.setUserTemplateId(userTemplateVO.getReportId());

			HashMap<String, Object> requestParamMap = new HashMap<>();

			String intervals = "";
			int level = 1;
			int totalRecords = 0;
			String reportType = "";
			String jsonInString = "";
			String granularity ="";
			String reportDisplayType =null;
			List<UserTemplate> userTemplateList = null;
					

			if (null != userTemplateVO.getRequestParam()) {
				if (null != userTemplateVO.getRequestParam().get("intervals")) {
					intervals = (String) userTemplateVO.getRequestParam().get("intervals");// .getIntervals();
				}

				if (null != userTemplateVO.getRequestParam().get("totalRecords")) {
					totalRecords = (int) userTemplateVO.getRequestParam().get("totalRecords");
				}
				if (null != userTemplateVO.getRequestParam().get("filter")) {
					jsonInString = mapper.writeValueAsString(userTemplateVO.getRequestParam().get("filter"));
				}
				if (null != userTemplateVO.getRequestParam().get("graphType")) {
					reportDisplayType = (String) userTemplateVO.getRequestParam().get("graphType");
				}
				if (null != userTemplateVO.getRequestParam().get("granularity")) {
					granularity = (String) userTemplateVO.getRequestParam().get("granularity");
				}
			}
			if (userTemplateVO.getLevel() != 0) {
				level = userTemplateVO.getLevel();
			}
			if (null != userTemplateVO.getReportType()) {
				reportType = userTemplateVO.getReportType();
			}
			if(null!=reportDisplayType && reportDisplayType.equals("lineGraph") && level==1)
			{
				userTemplateVO.setLevel(2);
				level=2;
				
			}
			
			
			requestParamMap.put("intervals", intervals);
			requestParamMap.put("level", level);
			requestParamMap.put("value_one", jsonInString);
			requestParamMap.put("reportType", reportType);
			requestParamMap.put("totalRecords", totalRecords);
			requestParamMap.put("reportDisplayType", reportDisplayType);
			requestParamMap.put("granularity", granularity);

			
			
			if(null!=reportDisplayType && reportDisplayType.equals("PIECHART"))
			{
				userTemplateList = userTemplateService.getUserTemplateWithQueryType(userTemplateVO);
				
			}
			else
			{
				userTemplateList = userTemplateService.getUserTemplate(userTemplateVO);
			}
			druidResponse = druidHelper.prepareDruidResponse(userTemplateList, requestParamMap);

			ReportResponseUI fetchedreportRequest = mapper.readValue(userTemplateList.get(0).getResponseTemplate(),
					new TypeReference<ReportResponseUI>() {
					});

			List<DruidProperty> props = fetchedreportRequest.getConfiguration().getProperties();
			List<DruidProperty> reportProps = new ArrayList<>();
			if (userTemplateVO.getLevel() != 3) {
				if (!props.isEmpty() && userTemplateVO.getLevel() == 2) {
					fetchedreportRequest.setLevel(2);
					if (fetchedreportRequest.getConfiguration().getLegends() != null) {
						fetchedreportRequest.getConfiguration().getLegends().forEach(attrribute -> {
							attrribute.setLevel(3);
						});
					}
					// Map<String, Map<String, List<Map<String, Object>>>>
					// responseMap = new HashMap();

					props.forEach(configprop -> {

						List<Property> druidpropertyList;
						try {
							List<Attribute> subHeaderlist = new ArrayList<>();
							druidpropertyList = propertyCacheService.getProperty(configprop.getName());
							if (null != druidpropertyList && druidpropertyList.size() > 0) {
								setPropertyList(druidpropertyList);
								druidpropertyList.forEach(prop -> {
									Attribute attr = new Attribute();
									attr.setDisplayName(prop.getType());
									attr.setName(prop.getType() + configprop.getName());
									attr.setLink(true);
									attr.setLevel(3);
									attr.setRefrenceField(configprop.getRefrenceField().concat(prop.getType().toLowerCase()));
									subHeaderlist.add(attr);
								});

							} else {
								if (previousDruidpropertyList != null) {
									previousDruidpropertyList.forEach(prop -> {
										Attribute attr = new Attribute();
										attr.setDisplayName(prop.getType());
										attr.setName(prop.getType() + configprop.getName());
										attr.setLink(true);
										attr.setLevel(3);
										String type = configprop.getType().replace("count",
												"count".concat(prop.getType().toLowerCase()));
										attr.setType(type);
										subHeaderlist.add(attr);
									});
								}
							}
							configprop.setSubHeaders(subHeaderlist);
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}

					});
				}
			} else {

				if (druidResponse != null && druidResponse.getHeaders() != null) {

					druidResponse.getHeaders().forEach(header -> {
						DruidProperty prop = new DruidProperty();
						prop.setName(header);
						prop.setDisplayName(header);
						prop.setRefrenceField(header);
						List<Attribute> subheaderList = new ArrayList<>();
						Attribute attr = new Attribute();
						attr.setRefrenceField(header);
						attr.setDisplayName(header);
						attr.setName(header);
						subheaderList.add(attr);
						prop.setSubHeaders(subheaderList);
						reportProps.add(prop);
					});
					fetchedreportRequest.getConfiguration().setProperties(reportProps);
				}

			}

			if (reportType.equals(Constant.ROAMERS_REPORT)) {
				// System.out.println("---Subheader----");
				List<DruidProperty> customProperty = new ArrayList<>();
				for (DruidProperty druidProperty : fetchedreportRequest.getConfiguration().getProperties()) {
					if (druidProperty.getName().equals(Constant.INBOUND)) {
						druidProperty.setDisplayName(Constant.IN_IMSI);
						druidProperty.setName(Constant.IN_IMSI);
						druidProperty.setRefrenceField(Constant.IN_IMSI);
						if (level == 2) {
							fetchedreportRequest.setLevel(2);
							druidProperty.setSubHeaders(prepareSubHeaderListForRoamers(Constant.IN_IMSI));
						}
					} else if (druidProperty.getName().equals(Constant.OUTBOUND)) {
						druidProperty.setDisplayName(Constant.OUT_IMSI);
						druidProperty.setName(Constant.OUT_IMSI);
						druidProperty.setRefrenceField(Constant.OUT_IMSI);
						if (level == 2) {
							fetchedreportRequest.setLevel(2);
							druidProperty.setSubHeaders(prepareSubHeaderListForRoamers(Constant.OUT_IMSI));
						}
					}
					customProperty.add(druidProperty);
				}
				fetchedreportRequest.getConfiguration().setProperties(customProperty);
			}

			if (reportType.equals(Constant.ROAMERS_REJECTION_REPORT) && level != 3) {
				System.out.println("---ROMING_REJECTION_ANALYSIS----");
				List<DruidProperty> customProperty = (List<DruidProperty>) fetchedreportRequest.getConfiguration()
						.getProperties();
				DruidProperty indruidProperty2 = new DruidProperty();
				DruidProperty indruidProperty23 = new DruidProperty();
				DruidProperty inSuccessMgsRate = new DruidProperty();
				DruidProperty inSuccessMgsCount = new DruidProperty();

				inSuccessMgsCount.setDisplayName("In Success Message Count");
				inSuccessMgsCount.setName("inSuccessMsgCount");
				inSuccessMgsCount.setRefrenceField("inSuccessMsgCount");
				if (level == 2) {
					inSuccessMgsCount.setSubHeaders(prepareSubHeaderListForRoamers("inSuccessMsgCount"));
				}

				indruidProperty2.setDisplayName("In Update Location Count");
				indruidProperty2.setName("ingsm_map_operation_type_2");
				indruidProperty2.setRefrenceField("ingsm_map_operation_type_2");
				if (level == 2) {
					Attribute attr = new Attribute();
					attr.setDisplayName("GSM");
					attr.setName("gsm" + "gsm_map_operation_type_2");
					attr.setRefrenceField("in" + "gsm_map_operation_type".concat("gsm") + "_2");
					attr.setLink(true);
					attr.setLevel(3);
					List<Attribute> list = new ArrayList<>();
					Attribute attr1 = new Attribute();
					attr1.setDisplayName("Camel");
					
					list.add(attr);
					list.add(attr1);
					indruidProperty2.setSubHeaders(list);
				}

				indruidProperty23.setDisplayName("In Update Location GPRS Count");
				indruidProperty23.setName("ingsm_map_operation_type_23");
				indruidProperty23.setRefrenceField("ingsm_map_operation_type_23");
				if (level == 2) {
					Attribute attr = new Attribute();
					attr.setDisplayName("GSM");
					attr.setName("gsm" + "gsm_map_operation_type_23");
					attr.setRefrenceField("in" + "gsm_map_operation_type".concat("gsm") + "_23");
					attr.setLink(true);
					attr.setLevel(3);
					List<Attribute> list = new ArrayList<>();
					Attribute attr1 = new Attribute();
					attr1.setDisplayName("Camel");
					
					list.add(attr);
					list.add(attr1);
					indruidProperty23.setSubHeaders(list);
				}

				inSuccessMgsRate.setDisplayName("In Success Message Rate %");
				inSuccessMgsRate.setName("inSuccessRate");
				inSuccessMgsRate.setRefrenceField("inSuccessRate");
				if (level == 2) {
					inSuccessMgsRate.setSubHeaders(prepareSubHeaderListForRoamers("inSuccessRate"));
				}

				DruidProperty outdruidProperty2 = new DruidProperty();
				DruidProperty outdruidProperty23 = new DruidProperty();
				DruidProperty outSuccessMgsRate = new DruidProperty();
				DruidProperty outSuccessMgsCount = new DruidProperty();

				outSuccessMgsCount.setDisplayName("Out Success Message Count");
				outSuccessMgsCount.setName("outSuccessMsgCount");
				outSuccessMgsCount.setRefrenceField("outSuccessMsgCount");
				if (level == 2) {
					outSuccessMgsCount.setSubHeaders(prepareSubHeaderListForRoamers("outSuccessMsgCount"));
				}
				outdruidProperty2.setDisplayName("Out Update Location Count");
				outdruidProperty2.setName("outgsm_map_operation_type_2");
				outdruidProperty2.setRefrenceField("outgsm_map_operation_type_2");
				if (level == 2) {
					Attribute attr = new Attribute();
					attr.setDisplayName("GSM");
					attr.setName("gsm" + "gsm_map_operation_type_2");
					attr.setRefrenceField("out" + "gsm_map_operation_type".concat("gsm") + "_2");
					attr.setLink(true);
					attr.setLevel(3);
					List<Attribute> list = new ArrayList<>();
					Attribute attr1 = new Attribute();
					attr1.setDisplayName("Camel");
					
					list.add(attr);
					list.add(attr1);
					outdruidProperty2.setSubHeaders(list);
				}
				outdruidProperty23.setDisplayName("Out Update Location GPRS Count");
				outdruidProperty23.setName("outgsm_map_operation_type_23");
				outdruidProperty23.setRefrenceField("outgsm_map_operation_type_23");
				if (level == 2) {
					Attribute attr = new Attribute();
					attr.setDisplayName("GSM");
					attr.setName("gsm" + "gsm_map_operation_type_23");
					attr.setRefrenceField("out" + "gsm_map_operation_type".concat("gsm") + "_23");
					attr.setLink(true);
					attr.setLevel(3);
					List<Attribute> list = new ArrayList<>();
					Attribute attr1 = new Attribute();
					attr1.setDisplayName("Camel");
					
					list.add(attr);
					list.add(attr1);
					outdruidProperty23.setSubHeaders(list);
				}
				outSuccessMgsRate.setDisplayName("Out Success Message Rate %");
				outSuccessMgsRate.setName("outSuccessRate");
				outSuccessMgsRate.setRefrenceField("outSuccessRate");
				if (level == 2) {
					outSuccessMgsRate.setSubHeaders(prepareSubHeaderListForRoamers("outSuccessRate"));
				}
				customProperty.add(inSuccessMgsCount);
				customProperty.add(indruidProperty2);
				customProperty.add(indruidProperty23);
				customProperty.add(inSuccessMgsRate);
				customProperty.add(outSuccessMgsCount);
				customProperty.add(outdruidProperty2);
				customProperty.add(outdruidProperty23);
				customProperty.add(outSuccessMgsRate);
				customProperty.sort(new Comparator<DruidProperty>() {
					@Override
					public int compare(DruidProperty o1, DruidProperty o2) {
						String d1 = o1.getDisplayName();
						String d2 = o2.getDisplayName();
						return d1.compareTo(d2);
					}
				});
				// System.out.println("-------"+customProperty);

				fetchedreportRequest.getConfiguration().setProperties(customProperty);
			}

			if (druidResponse != null) {
				druidResponse.setReportConfiguration(fetchedreportRequest);
			} // reportRequest.setIntervals(intervals);

			// 1 day' set in UI response
			report.setResponse(druidResponse);
			report.setReportType(userTemplateVO.getReportType());
			reports.add(report);
			dashBoardReport.setReports(reports);
			dashboardReports.add(dashBoardReport);
		}
		userreport.setDashboardReports(dashboardReports);
		return userreport;

	}

	public void setPropertyList(List<Property> druidpropertyList) {
		previousDruidpropertyList = druidpropertyList;
	}

	private Attribute prepareAttribute(String type, String name) {
		Attribute attr = new Attribute();
		attr.setDisplayName(type);
		attr.setName(type + name);
		attr.setRefrenceField(name.concat(type));
		attr.setLink(true);
		attr.setLevel(3);
		return attr;
	}

	private List<Attribute> prepareSubHeaderListForRoamers(String name) {
		List<Attribute> subHeaderlist = new ArrayList<>();
		List<String> tpyeList = new ArrayList<>();
		Attribute attr = null;
		tpyeList.add("GSM");
		tpyeList.add("Camel");
		tpyeList.add("Diameter");
		for (String type : tpyeList) {
			attr = prepareAttribute(type, name);
			subHeaderlist.add(attr);
		}
		return subHeaderlist;
	}

}