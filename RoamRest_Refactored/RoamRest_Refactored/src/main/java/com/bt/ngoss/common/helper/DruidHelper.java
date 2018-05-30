package com.bt.ngoss.common.helper;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.bt.dyns.model.druid.request.DruidBaseRequest.DruidBaseRequestBuilder;
import com.bt.dyns.model.common.Rule;
import com.bt.dyns.model.common.Rules;
import com.bt.dyns.model.druid.common.Fields;
import com.bt.dyns.model.druid.common.Filter;
import com.bt.dyns.model.druid.request.DruidTimeseriesRequest;
import com.bt.dyns.model.druid.request.ReportRequestQuery;
import com.bt.dyns.model.druid.request.ReportResponseUI;
import com.bt.ngoss.client.druid.DruidRestClient;
import com.bt.ngoss.common.constant.Constant;
import com.bt.ngoss.common.constant.DateConstant;
import com.bt.ngoss.common.property.CommonAttribute;
import com.bt.ngoss.controllers.LoadApplicationData;
import com.bt.ngoss.dao.CommonDAO;
import com.bt.ngoss.model.report.BaseLeveReport;
import com.bt.ngoss.model.report.BaseLeveReportComparatpr;
import com.bt.ngoss.model.report.ErrorDetail;
import com.bt.ngoss.model.report.UserTemplate;
import com.bt.ngoss.model.response.DruidGroupByResponse;
import com.bt.ngoss.model.response.DruidResponse;
import com.bt.ngoss.model.response.DruidSelectResponse;
import com.bt.ngoss.model.response.DruidTimeSeriesResponse;
import com.bt.ngoss.model.response.Response;
/*import com.bt.ngoss.service.AggregationService;
import com.bt.ngoss.service.DatasourceService;
import com.bt.ngoss.service.GranularityMetadataService;*/
import com.bt.ngoss.service.druid.DruidTemplateService;
import com.bt.ngoss.service.inventory.PropertyCacheService;
import com.bt.ngoss.util.ObjectFactory;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class DruidHelper {

	@Autowired
	CommonAttribute commonAttribute;

	@Autowired
	DruidTemplateService druidTemplateService;
	/*
	 * @Autowired DatasourceService datasourceService;
	 */

	@Autowired
	CommonDAO<ErrorDetail> commonDao;

	/*
	 * @Autowired GranularityMetadataService granularityMetadataService;
	 * 
	 * @Autowired AggregationService aggregationService;
	 */

	@Autowired
	PropertyCacheService propertyCacheService;

	ObjectMapper mapper = new ObjectMapper();

	private DruidRestClient<?> druidRestClient = ObjectFactory.getDruidRestClient();

	public String getDruidURI() {
		return LoadApplicationData.siteOptionMap.get("druidUri");
	}

	public String generateIntervalString(String interval) throws Exception {
		LocalDateTime currentDateTime = LocalDateTime.now(ZoneId.of(commonAttribute.getZoneId()));

		if (interval.toLowerCase().contains(Constant.CURRENT)) {
			return DateHelper.generateDruidIntervalForCurrent(currentDateTime, interval);
		} else if (interval.toLowerCase().contains(Constant.PREVIOUS)) {
			return DateHelper.generateDruidIntervalForPrevious(currentDateTime, interval);
		} else if (interval.toLowerCase().contains(Constant.LAST)) {
			return DateHelper.generateDruidIntervalForLast(currentDateTime, interval);
		} else if (interval.equalsIgnoreCase(DateConstant.TODAY) || interval.equalsIgnoreCase(DateConstant.YESTERDAY)) {
			return DateHelper.generateDruidIntervalForYesterdayToday(currentDateTime, interval);
		}

		return interval;
	}

	public void prepareTimeseriesResponse(List<Response> responseList, ReportRequestQuery reportRequest,
			DruidBaseRequestBuilder druidBaseRequestBuilder) throws Exception {
		DruidTimeseriesRequest druidTimeseriesRequest = new DruidTimeseriesRequest.TimeseriesRequestBuilder(
				druidBaseRequestBuilder, reportRequest).build();
		DruidTimeSeriesResponse[] druidTimeSeriesResponseArray = (DruidTimeSeriesResponse[]) druidRestClient
				.druidResponse(getDruidURI(), druidTimeseriesRequest);
		for (DruidTimeSeriesResponse druidTimeSeriesResponse : druidTimeSeriesResponseArray) {
			Response response = new Response();
			List<Map<String, Object>> events = new ArrayList<>();
			Map<String, Object> event = druidTimeSeriesResponse.getResult();
			event.put(Constant.TIMESTAMP, druidTimeSeriesResponse.getTimestamp());

			// Enriching druid response currently disable enreiching facility
			// event = getRespWithPropertiesValues(reportRequest, event);

			events.add(event);
			response.setEvents(events);
			responseList.add(response);
		}
	}

	@SuppressWarnings("unchecked")
	public void prepareSelectResponse(List<Response> responseList, ReportRequestQuery reportRequest,
			DruidBaseRequestBuilder druidBaseRequestBuilder) throws Exception {
		// DruidSelectRequest druidSelectRequest = new
		// DruidSelectRequest.SelectRequestBuilder(druidBaseRequestBuilder,
		// reportRequest).build();

		DruidSelectResponse[] druidSelectResponseArray = (DruidSelectResponse[]) druidRestClient
				.druidResponse(getDruidURI(), reportRequest);

		// DruidSelectResponse[] druidSelectResponseArray =
		// (DruidSelectResponse[]) druidRestClient
		// .druidResponse(getDruidURI(), druidSelectRequest);
		for (DruidSelectResponse druidSelectResponse : druidSelectResponseArray) {
			Response response = new Response();
			response.setPagingIdentifiers(
					(Map<String, Object>) druidSelectResponse.getResult().get(Constant.PAGING_IDENTIFIERS));
			List<Map<String, Object>> events = (List<Map<String, Object>>) druidSelectResponse.getResult()
					.get(Constant.EVENTS);
			events.forEach(event -> {
				event.remove("segmentId");
				event.remove("offset");
				event.putAll((Map<String, Object>) event.get(Constant.EVENT));

				event.remove(Constant.EVENT);
			});
			response.setEvents(events);
			responseList.add(response);
		}
	}

	/*
	 * public void prepareTopNResponse(List<Response> responseList, String
	 * metricName, ReportRequestQuery reportRequest, DruidBaseRequestBuilder
	 * druidBaseRequestBuilder) throws Exception { DruidTopNRequest topnRequest
	 * = new DruidTopNRequest.TopNRequestBuilder(metricName,
	 * druidBaseRequestBuilder, reportRequest).build(); DruidTopNResponse[]
	 * druidTopNResponseArray = (DruidTopNResponse[])
	 * druidRestClient.druidResponse(getDruidURI(), topnRequest);
	 * 
	 * for (DruidTopNResponse druidTopNResponse : druidTopNResponseArray) {
	 * Response response = new Response(); List<Map<String, Object>> resultList
	 * = druidTopNResponse.getResult(); resultList.forEach(resultMap -> {
	 * resultMap.put(Constant.TIMESTAMP, druidTopNResponse.getTimestamp());
	 * 
	 * }); response.setEvents(resultList); responseList.add(response); } }
	 */

	/*
	 * public DruidResponse prepareDruidResponse(ReportRequest reportRequest)
	 * throws Exception { DruidResponse druidResponse = new DruidResponse();
	 * DruidTemplate druidTemplate =
	 * druidTemplateService.getTemplate(reportRequest.getConfiguration().getType
	 * ()); String granularity = granularityMetadataService
	 * .getGranularity(DruidRequestHelper.fetchDruidUnitFromSamplingPeriod(
	 * reportRequest.getSamplingPeriod()));
	 * 
	 * if (ReportType.TABLE.getValue().equals(reportRequest.getConfiguration().
	 * getType().toLowerCase())) { List<DruidProperty> props =
	 * reportRequest.getConfiguration().getProperties();
	 * 
	 * if (!props.isEmpty()) { Map<String, Map<String, Integer>> responseMap =
	 * new HashMap(); props.forEach(druidprop -> { try { List<Property>
	 * propertyList = propertyCacheService.getProperty(druidprop.getName());
	 * List<Response> responseList = new ArrayList<>();
	 * propertyList.forEach(prop -> { try {
	 * 
	 * // reportRequest.getConfiguration().setProperties(druidprop.getValue());
	 * 
	 * // Set for aggregation List<Metric> metricList = new ArrayList<>();
	 * Metric metric = new Metric(); metric.setAggregationType("count");
	 * metric.setName(prop.getName() + "_" + metric.getAggregationType());
	 * metric.setDisplayName(prop.getName() + "_" +
	 * metric.getAggregationType()); metricList.add(metric);
	 * reportRequest.getConfiguration().setMetrics(metricList);
	 * 
	 * // Set for dimension reportRequest.getConfiguration().setProperties(new
	 * ArrayList<>()); DruidProperty druidPropety = new DruidProperty();
	 * druidPropety.setId(prop.getId());
	 * druidPropety.setDisplayName(prop.getName());
	 * druidPropety.setName(prop.getName());
	 * druidPropety.setLink(Boolean.FALSE);
	 * 
	 * reportRequest.getConfiguration().getProperties().add(druidPropety);
	 * 
	 * DruidBaseRequestBuilder druidBaseRequestBuilder = new
	 * DruidBaseRequestBuilder(
	 * druidTemplate.getDruidQueryTemplate().getQueryType(),
	 * prop.getDataSource(), granularity, reportRequest.getIntervals());
	 * Map<String, Map<String, Integer>> dataWithTimeStamp = new HashMap<>();
	 * prepareGroupByResponse(dataWithTimeStamp, reportRequest,
	 * druidBaseRequestBuilder); for (Map.Entry<String, Map<String, Integer>>
	 * entry : dataWithTimeStamp.entrySet()) { Map<String, Integer>
	 * resultFteched = responseMap.get(entry.getKey()); if (null ==
	 * resultFteched || resultFteched.isEmpty()) { resultFteched = new
	 * HashMap<>(); resultFteched.putAll(entry.getValue()); } else { for
	 * (Map.Entry<String, Integer> res : resultFteched.entrySet()) { Integer
	 * count = entry.getValue().get(res.getKey());
	 * res.setValue(Integer.sum(res.getValue(), count)); } } }
	 * 
	 * } catch (Exception ex) {
	 * 
	 * } }); } catch (Exception ex) {
	 * 
	 * } }); System.out.println(responseMap); }
	 * 
	 * }
	 * 
	 * return druidResponse;
	 * 
	 * }
	 */

	public void prepareGroupByResponseForSecurityViolation(
			Map<String, Map<String, List<Map<String, Object>>>> baseReportMaps, ReportRequestQuery reportRequest,
			DruidBaseRequestBuilder druidBaseRequestBuilder, ReportResponseUI reportResponseUI, String type)
			throws Exception {
		DruidGroupByResponse[] druidGroupByResponseArray = (DruidGroupByResponse[]) druidRestClient
				.druidResponse(getDruidURI(), reportRequest);
		int level = reportResponseUI.getLevel();
		if (null != druidGroupByResponseArray && (level == 1)) {
			String eventKeyName = reportResponseUI.getConfiguration().getProperties().get(0).getId();
			for (DruidGroupByResponse rd : druidGroupByResponseArray) {
				Map<String, Object> eventData = rd.getEvent();
				String date = rd.getTimestamp();
				if (baseReportMaps.isEmpty()) {
					Map<String, List<Map<String, Object>>> result = new HashMap<>();
					String name = String.valueOf(eventData.get(eventKeyName));
					List<Map<String, Object>> dataMapList = new ArrayList<>();
					eventData.remove(eventKeyName);
					dataMapList.add(eventData);
					result.put(name, dataMapList);
					baseReportMaps.put(date, result);
				} else {
					Map<String, List<Map<String, Object>>> result = baseReportMaps.get(date);
					String name = String.valueOf(eventData.get(eventKeyName));
					List<Map<String, Object>> dataMapList = result.get(name);
					if (null != dataMapList) {
						eventData.remove(eventKeyName);
						dataMapList.add(eventData);
						result.put(name, dataMapList);
						baseReportMaps.put(date, result);
					} else {
						dataMapList = new ArrayList<>();
						eventData.remove(eventKeyName);
						dataMapList.add(eventData);
						result.put(name, dataMapList);
						baseReportMaps.put(date, result);
					}
				}
			}
		} else if (null != druidGroupByResponseArray && level == 2) {
			// String eventKeyName =
			// reportResponseUI.getConfiguration().getProperties().get(0).getId();
			String eventKeyName = reportRequest.getDimensions()[0];
			for (DruidGroupByResponse rd : druidGroupByResponseArray) {
				Map<String, Object> eventData = rd.getEvent();
				String date = rd.getTimestamp();
				if (baseReportMaps.isEmpty()) {
					Map<String, List<Map<String, Object>>> result = new HashMap<>();
					String name = String.valueOf(eventData.get(eventKeyName));
					List<Map<String, Object>> dataMapList = new ArrayList<>();
					eventData.remove(eventKeyName);
					Map<String, Object> eventData1 = new HashMap<>();
					for (Map.Entry<String, Object> entry : eventData.entrySet()) {
						String key = entry.getKey().concat(type.toLowerCase());
						Object value = entry.getValue();
						eventData1.put(key, value);
					}

					dataMapList.add(eventData1);
					result.put(name, dataMapList);
					baseReportMaps.put(date, result);
				} else {
					Map<String, List<Map<String, Object>>> result = baseReportMaps.get(date);
					String name = String.valueOf(eventData.get(eventKeyName));
					List<Map<String, Object>> dataMapList = result.get(name);
					if (null != dataMapList) {
						eventData.remove(eventKeyName);
						Map<String, Object> eventData1 = new HashMap<>();
						for (Map.Entry<String, Object> entry : eventData.entrySet()) {
							String key = entry.getKey().concat(type.toLowerCase());
							Object value = entry.getValue();
							eventData1.put(key, value);
						}
						dataMapList.add(eventData1);
						result.put(name, dataMapList);
						baseReportMaps.put(date, result);
					} else {
						dataMapList = new ArrayList<>();
						eventData.remove(eventKeyName);
						Map<String, Object> eventData1 = new HashMap<>();
						for (Map.Entry<String, Object> entry : eventData.entrySet()) {
							String key = entry.getKey().concat(type.toLowerCase());
							Object value = entry.getValue();
							eventData1.put(key, value);
						}
						dataMapList.add(eventData1);
						result.put(name, dataMapList);
						baseReportMaps.put(date, result);
					}
				}

			}
		}

	}

	public void prepareGroupByResponseForSecurityViolationPieChart(
			Map<String, Map<String, List<List<Map<String, Object>>>>> baseReportMaps, ReportRequestQuery reportRequest,
			DruidBaseRequestBuilder druidBaseRequestBuilder, ReportResponseUI reportResponseUI, String type)
			throws Exception {
		DruidGroupByResponse[] druidGroupByResponseArray = (DruidGroupByResponse[]) druidRestClient
				.druidResponse(getDruidURI(), reportRequest);
		int level = reportResponseUI.getLevel();
		if (null != druidGroupByResponseArray && level == 2 && null != reportResponseUI.getReportType()
				&& reportResponseUI.getReportType().equals("PIECHART")) {
			String eventKeyName = reportResponseUI.getConfiguration().getProperties().get(0).getId();
			for (DruidGroupByResponse rd : druidGroupByResponseArray) {
				Map<String, Object> eventData = rd.getEvent();
				String date = rd.getTimestamp();
				if (baseReportMaps.isEmpty()) {
					Map<String, List<List<Map<String, Object>>>> result = new HashMap<>();
					String name = (String) eventData.get(reportRequest.getDimensions()[1]);
					// String name =
					// String.valueOf(eventData.get(eventKeyName));
					List<List<Map<String, Object>>> dataMapList = new ArrayList<>();
					// eventData.remove(eventKeyName);
					List<Map<String, Object>> operationMapList = new ArrayList<>();
					Map<String, Object> eventData1 = new HashMap<>();
					String operationType = (String) eventData.get(reportRequest.getDimensions()[0]);
					int aggregationName = (Integer) eventData.get(reportRequest.getAggregations()[0].getName());

					eventData1.put(reportRequest.getDimensions()[0] + "_" + operationType, aggregationName);

					/*
					 * for (Map.Entry<String, Object> entry :
					 * eventData.entrySet()) { String key =
					 * entry.getKey().concat("_"+entry.getValue()); Object value
					 * = entry.getValue(); eventData1.put(key, value); }
					 */
					operationMapList.add(eventData1);
					dataMapList.add(operationMapList);
					result.put(name, dataMapList);
					baseReportMaps.put(date, result);
				} else {
					Map<String, List<List<Map<String, Object>>>> result = baseReportMaps.get(date);
					String name = (String) eventData.get(reportRequest.getDimensions()[1]);
					// String name =
					// String.valueOf(eventData.get(eventKeyName));
					List<List<Map<String, Object>>> dataMapList = result.get(name);

					List<Map<String, Object>> operationMapList = new ArrayList<>();
					if (null != dataMapList) {
						// eventData.remove(eventKeyName);

						Map<String, Object> eventData1 = new HashMap<>();
						String operationType = (String) eventData.get(reportRequest.getDimensions()[0]);
						int aggregationName = (Integer) eventData.get(reportRequest.getAggregations()[0].getName());

						eventData1.put(reportRequest.getDimensions()[0] + "_" + operationType, aggregationName);

						/*
						 * for (Map.Entry<String, Object> entry :
						 * eventData.entrySet()) { String key =
						 * entry.getKey().concat("_"+entry.getValue()); Object
						 * value = entry.getValue(); eventData1.put(key, value);
						 * }
						 */
						operationMapList.add(eventData1);
						dataMapList.add(operationMapList);
						result.put(name, dataMapList);
						baseReportMaps.put(date, result);
					} else {
						dataMapList = new ArrayList<>();
						eventData.remove(eventKeyName);
						Map<String, Object> eventData1 = new HashMap<>();
						/*
						 * for (Map.Entry<String, Object> entry :
						 * eventData.entrySet()) { String key =
						 * entry.getKey().concat(type.toLowerCase()); Object
						 * value = entry.getValue(); eventData1.put(key, value);
						 * }
						 */
						String operationType = (String) eventData.get(reportRequest.getDimensions()[0]);
						int aggregationName = (Integer) eventData.get(reportRequest.getAggregations()[0].getName());

						eventData1.put(reportRequest.getDimensions()[0] + "_" + operationType, aggregationName);

						operationMapList.add(eventData1);
						dataMapList.add(operationMapList);
						result.put(name, dataMapList);
						baseReportMaps.put(date, result);
					}
				}
			}

		}

	}

	public void prepareGroupByResponseForErrorPieChart(
			Map<String, Map<String, List<List<Map<String, Object>>>>> baseReportMaps, ReportRequestQuery reportRequest,
			DruidBaseRequestBuilder druidBaseRequestBuilder, ReportResponseUI reportResponseUI, String type)
			throws Exception {
		DruidGroupByResponse[] druidGroupByResponseArray = (DruidGroupByResponse[]) druidRestClient
				.druidResponse(getDruidURI(), reportRequest);
		int level = reportResponseUI.getLevel();
		if (null != druidGroupByResponseArray && level == 1 && null != reportResponseUI.getReportType()
				&& reportResponseUI.getReportType().equals("ERRORPIECHART")) {
			String eventKeyName = reportResponseUI.getConfiguration().getProperties().get(0).getId();
			for (DruidGroupByResponse rd : druidGroupByResponseArray) {
				Map<String, Object> eventData = rd.getEvent();
				String date = rd.getTimestamp();
				if (baseReportMaps.isEmpty()) {
					Map<String, List<List<Map<String, Object>>>> result = new HashMap<>();
					String name = (String) eventData.get(reportRequest.getDimensions()[0]);
					// String name =
					// String.valueOf(eventData.get(eventKeyName));
					List<List<Map<String, Object>>> dataMapList = new ArrayList<>();
					// eventData.remove(eventKeyName);
					List<Map<String, Object>> operationMapList = new ArrayList<>();
					Map<String, Object> eventData1 = new HashMap<>();
					String operationType = (String) eventData.get(reportRequest.getDimensions()[1]);
					int aggregationName = (Integer) eventData.get(reportRequest.getAggregations()[0].getName());

					// eventData1.put(reportRequest.getDimensions()[0]+"_"+operationType,
					// aggregationName);
					eventData1.put(reportRequest.getAggregations()[0].getName() + " : " + operationType,
							aggregationName);
					/*
					 * for (Map.Entry<String, Object> entry :
					 * eventData.entrySet()) { String key =
					 * entry.getKey().concat("_"+entry.getValue()); Object value
					 * = entry.getValue(); eventData1.put(key, value); }
					 */
					operationMapList.add(eventData1);
					dataMapList.add(operationMapList);
					result.put(name, dataMapList);
					baseReportMaps.put(date, result);
				} else {
					Map<String, List<List<Map<String, Object>>>> result = baseReportMaps.get(date);
					String name = (String) eventData.get(reportRequest.getDimensions()[0]);
					// String name =
					// String.valueOf(eventData.get(eventKeyName));
					List<List<Map<String, Object>>> dataMapList = result.get(name);

					List<Map<String, Object>> operationMapList = new ArrayList<>();
					if (null != dataMapList) {
						// eventData.remove(eventKeyName);

						Map<String, Object> eventData1 = new HashMap<>();
						String operationType = (String) eventData.get(reportRequest.getDimensions()[1]);
						int aggregationName = (Integer) eventData.get(reportRequest.getAggregations()[0].getName());

						// eventData1.put(reportRequest.getDimensions()[0]+"_"+operationType,
						// aggregationName);
						eventData1.put(reportRequest.getAggregations()[0].getName() + " : " + operationType,
								aggregationName);

						/*
						 * for (Map.Entry<String, Object> entry :
						 * eventData.entrySet()) { String key =
						 * entry.getKey().concat("_"+entry.getValue()); Object
						 * value = entry.getValue(); eventData1.put(key, value);
						 * }
						 */
						operationMapList.add(eventData1);
						dataMapList.add(operationMapList);
						result.put(name, dataMapList);
						baseReportMaps.put(date, result);
					} else {
						dataMapList = new ArrayList<>();
						eventData.remove(eventKeyName);
						Map<String, Object> eventData1 = new HashMap<>();
						/*
						 * for (Map.Entry<String, Object> entry :
						 * eventData.entrySet()) { String key =
						 * entry.getKey().concat(type.toLowerCase()); Object
						 * value = entry.getValue(); eventData1.put(key, value);
						 * }
						 */
						String operationType = (String) eventData.get(reportRequest.getDimensions()[1]);
						int aggregationName = (Integer) eventData.get(reportRequest.getAggregations()[0].getName());

						// eventData1.put(reportRequest.getDimensions()[0]+"_"+operationType,
						// aggregationName);
						// eventData1.put(reportRequest.getDimensions()[0]+"_"+operationType,
						// aggregationName);
						eventData1.put(reportRequest.getAggregations()[0].getName() + " : " + operationType,
								aggregationName);

						operationMapList.add(eventData1);
						dataMapList.add(operationMapList);
						result.put(name, dataMapList);
						baseReportMaps.put(date, result);
					}
				}
			}

		}

		if (null != druidGroupByResponseArray && level == 2 && null != reportResponseUI.getReportType()
				&& reportResponseUI.getReportType().equals("ERRORPIECHART")) {
			String eventKeyName = reportResponseUI.getConfiguration().getProperties().get(0).getId();
			for (DruidGroupByResponse rd : druidGroupByResponseArray) {
				Map<String, Object> eventData = rd.getEvent();
				String date = rd.getTimestamp();
				if (baseReportMaps.isEmpty()) {
					Map<String, List<List<Map<String, Object>>>> result = new HashMap<>();
					String name = (String) eventData.get(reportRequest.getDimensions()[0]);
					// String name =
					// String.valueOf(eventData.get(eventKeyName));
					List<List<Map<String, Object>>> dataMapList = new ArrayList<>();
					// eventData.remove(eventKeyName);
					List<Map<String, Object>> operationMapList = new ArrayList<>();
					Map<String, Object> eventData1 = new HashMap<>();
					String operationType = (String) eventData.get(reportRequest.getDimensions()[1]);
					int aggregationName = (Integer) eventData.get(reportRequest.getAggregations()[0].getName());

					// eventData1.put(reportRequest.getDimensions()[0]+"_"+operationType,
					// aggregationName);
					// eventData1.put(reportRequest.getDimensions()[0]+"_"+operationType,
					// aggregationName);
					eventData1.put(reportRequest.getAggregations()[0].getName() + " : " + operationType,
							aggregationName);

					/*
					 * for (Map.Entry<String, Object> entry :
					 * eventData.entrySet()) { String key =
					 * entry.getKey().concat("_"+entry.getValue()); Object value
					 * = entry.getValue(); eventData1.put(key, value); }
					 */
					operationMapList.add(eventData1);
					dataMapList.add(operationMapList);
					result.put(name, dataMapList);
					baseReportMaps.put(date, result);
				} else {
					Map<String, List<List<Map<String, Object>>>> result = baseReportMaps.get(date);
					String name = (String) eventData.get(reportRequest.getDimensions()[0]);
					// String name =
					// String.valueOf(eventData.get(eventKeyName));
					List<List<Map<String, Object>>> dataMapList = result.get(name);

					List<Map<String, Object>> operationMapList = new ArrayList<>();
					if (null != dataMapList) {
						// eventData.remove(eventKeyName);

						Map<String, Object> eventData1 = new HashMap<>();
						String operationType = (String) eventData.get(reportRequest.getDimensions()[1]);
						int aggregationName = (Integer) eventData.get(reportRequest.getAggregations()[0].getName());

						// eventData1.put(reportRequest.getDimensions()[0]+"_"+operationType,
						// aggregationName);
						eventData1.put(reportRequest.getAggregations()[0].getName() + " : " + operationType,
								aggregationName);

						/*
						 * for (Map.Entry<String, Object> entry :
						 * eventData.entrySet()) { String key =
						 * entry.getKey().concat("_"+entry.getValue()); Object
						 * value = entry.getValue(); eventData1.put(key, value);
						 * }
						 */
						operationMapList.add(eventData1);
						dataMapList.add(operationMapList);
						result.put(name, dataMapList);
						baseReportMaps.put(date, result);
					} else {
						dataMapList = new ArrayList<>();
						eventData.remove(eventKeyName);
						Map<String, Object> eventData1 = new HashMap<>();
						/*
						 * for (Map.Entry<String, Object> entry :
						 * eventData.entrySet()) { String key =
						 * entry.getKey().concat(type.toLowerCase()); Object
						 * value = entry.getValue(); eventData1.put(key, value);
						 * }
						 */
						String operationType = (String) eventData.get(reportRequest.getDimensions()[1]);
						int aggregationName = (Integer) eventData.get(reportRequest.getAggregations()[0].getName());

						// eventData1.put(reportRequest.getDimensions()[0]+"_"+operationType,
						// aggregationName);
						eventData1.put(reportRequest.getAggregations()[0].getName() + " : " + operationType,
								aggregationName);

						operationMapList.add(eventData1);
						dataMapList.add(operationMapList);
						result.put(name, dataMapList);
						baseReportMaps.put(date, result);
					}
				}
			}

		}

	}

	public void prepareGroupByResponse(Map<String, Map<String, List<Map<String, Object>>>> baseReportMaps,
			ReportRequestQuery reportRequest, DruidBaseRequestBuilder druidBaseRequestBuilder,
			ReportResponseUI reportResponseUI, String type) throws Exception {

		DruidGroupByResponse[] druidGroupByResponseArray = (DruidGroupByResponse[]) druidRestClient
				.druidResponse(getDruidURI(), reportRequest);

		int level = reportResponseUI.getLevel();
		if (null != druidGroupByResponseArray && (level == 1)) {
			String eventKeyName = reportResponseUI.getConfiguration().getProperties().get(0).getId();
			for (DruidGroupByResponse rd : druidGroupByResponseArray) {
				Map<String, Object> eventData = rd.getEvent();
				String date = rd.getTimestamp();
				if (baseReportMaps.isEmpty()) {
					Map<String, List<Map<String, Object>>> result = new HashMap<>();
					String name = String.valueOf(eventData.get(eventKeyName));
					List<Map<String, Object>> dataMapList = new ArrayList<>();
					eventData.remove(eventKeyName);
					dataMapList.add(eventData);
					result.put(name, dataMapList);
					baseReportMaps.put(date, result);
				} else {
					Map<String, List<Map<String, Object>>> result = baseReportMaps.get(date);
					String name = String.valueOf(eventData.get(eventKeyName));
					if(null!=result)
					{
					List<Map<String, Object>> dataMapList = result.get(name);
					if (null != dataMapList) {
						eventData.remove(eventKeyName);
						dataMapList.add(eventData);
						result.put(name, dataMapList);
						baseReportMaps.put(date, result);
					} else {
						dataMapList = new ArrayList<>();
						eventData.remove(eventKeyName);
						dataMapList.add(eventData);
						result.put(name, dataMapList);
						baseReportMaps.put(date, result);
					}
					}
				}

			}
		} else if (null != druidGroupByResponseArray && level == 2) {
			String eventKeyName = reportResponseUI.getConfiguration().getProperties().get(0).getId();
			for (DruidGroupByResponse rd : druidGroupByResponseArray) {
				Map<String, Object> eventData = rd.getEvent();
				String date = rd.getTimestamp();
				if (baseReportMaps.isEmpty()) {
					Map<String, List<Map<String, Object>>> result = new HashMap<>();
					String name = String.valueOf(eventData.get(eventKeyName));
					List<Map<String, Object>> dataMapList = new ArrayList<>();
					eventData.remove(eventKeyName);
					Map<String, Object> eventData1 = new HashMap<>();
					for (Map.Entry<String, Object> entry : eventData.entrySet()) {
						String key = entry.getKey().concat(type.toLowerCase());
						Object value = entry.getValue();
						eventData1.put(key, value);
					}

					dataMapList.add(eventData1);
					result.put(name, dataMapList);
					baseReportMaps.put(date, result);
				} else {
					Map<String, List<Map<String, Object>>> result = baseReportMaps.get(date);
					String name = String.valueOf(eventData.get(eventKeyName));
					List<Map<String, Object>> dataMapList = result.get(name);
					if (null != dataMapList) {
						eventData.remove(eventKeyName);
						Map<String, Object> eventData1 = new HashMap<>();
						for (Map.Entry<String, Object> entry : eventData.entrySet()) {
							String key = entry.getKey().concat(type.toLowerCase());
							Object value = entry.getValue();
							eventData1.put(key, value);
						}
						dataMapList.add(eventData1);
						result.put(name, dataMapList);
						baseReportMaps.put(date, result);
					} else {
						dataMapList = new ArrayList<>();
						eventData.remove(eventKeyName);
						Map<String, Object> eventData1 = new HashMap<>();
						for (Map.Entry<String, Object> entry : eventData.entrySet()) {
							String key = entry.getKey().concat(type.toLowerCase());
							Object value = entry.getValue();
							eventData1.put(key, value);
						}
						dataMapList.add(eventData1);
						result.put(name, dataMapList);
						baseReportMaps.put(date, result);
					}
				}

			}

		}

	}

	public void prepareGroupByResponseFullday(Map<String, Map<String, List<Map<String, Object>>>> baseReportMaps,
			ReportRequestQuery reportRequest, DruidBaseRequestBuilder druidBaseRequestBuilder,
			ReportResponseUI reportResponseUI, Map<String, Object> data) throws Exception {

		DruidGroupByResponse[] druidGroupByResponseArray = (DruidGroupByResponse[]) druidRestClient
				.druidResponse(getDruidURI(), reportRequest);

		String type = (String) data.get("type");
		String inputDate = (String) data.get("date");
		int level = reportResponseUI.getLevel();
		if (null != druidGroupByResponseArray && (level == 1)) {
			String eventKeyName = reportResponseUI.getConfiguration().getProperties().get(0).getId();
			for (DruidGroupByResponse rd : druidGroupByResponseArray) {
				Map<String, Object> eventData = rd.getEvent();
				String date = inputDate.concat("T00:00:00.000Z");

				if (baseReportMaps.isEmpty()) {
					Map<String, List<Map<String, Object>>> result = new HashMap<>();
					String name = String.valueOf(eventData.get(eventKeyName));
					List<Map<String, Object>> dataMapList = new ArrayList<>();
					eventData.remove(eventKeyName);
					dataMapList.add(eventData);
					result.put(name, dataMapList);
					baseReportMaps.put(date, result);
				} else {
					Map<String, List<Map<String, Object>>> result = baseReportMaps.get(date);
					String name = String.valueOf(eventData.get(eventKeyName));
					if (null != result) {
						List<Map<String, Object>> dataMapList = result.get(name);
						if (null != dataMapList) {
							eventData.remove(eventKeyName);
							dataMapList.add(eventData);
							result.put(name, dataMapList);
							baseReportMaps.put(date, result);
						} else {
							dataMapList = new ArrayList<>();
							eventData.remove(eventKeyName);
							dataMapList.add(eventData);
							result.put(name, dataMapList);
							baseReportMaps.put(date, result);
						}
					}
				}

			}
		} else if (null != druidGroupByResponseArray && level == 2) {
			String eventKeyName = reportResponseUI.getConfiguration().getProperties().get(0).getId();
			for (DruidGroupByResponse rd : druidGroupByResponseArray) {
				Map<String, Object> eventData = rd.getEvent();
				String date = inputDate.concat("T00:00:00.000Z");
				if (baseReportMaps.isEmpty()) {
					Map<String, List<Map<String, Object>>> result = new HashMap<>();
					String name = String.valueOf(eventData.get(eventKeyName));
					List<Map<String, Object>> dataMapList = new ArrayList<>();
					eventData.remove(eventKeyName);
					Map<String, Object> eventData1 = new HashMap<>();
					for (Map.Entry<String, Object> entry : eventData.entrySet()) {
						String key = entry.getKey().concat(type.toLowerCase());
						Object value = entry.getValue();
						eventData1.put(key, value);
					}

					dataMapList.add(eventData1);
					result.put(name, dataMapList);
					baseReportMaps.put(date, result);
				} else {
					Map<String, List<Map<String, Object>>> result = baseReportMaps.get(date);
					String name = String.valueOf(eventData.get(eventKeyName));
					List<Map<String, Object>> dataMapList = result.get(name);
					if (null != dataMapList) {
						eventData.remove(eventKeyName);
						Map<String, Object> eventData1 = new HashMap<>();
						for (Map.Entry<String, Object> entry : eventData.entrySet()) {
							String key = entry.getKey().concat(type.toLowerCase());
							Object value = entry.getValue();
							eventData1.put(key, value);
						}
						dataMapList.add(eventData1);
						result.put(name, dataMapList);
						baseReportMaps.put(date, result);
					} else {
						dataMapList = new ArrayList<>();
						eventData.remove(eventKeyName);
						Map<String, Object> eventData1 = new HashMap<>();
						for (Map.Entry<String, Object> entry : eventData.entrySet()) {
							String key = entry.getKey().concat(type.toLowerCase());
							Object value = entry.getValue();
							eventData1.put(key, value);
						}
						dataMapList.add(eventData1);
						result.put(name, dataMapList);
						baseReportMaps.put(date, result);
					}
				}

			}

		}

	}

	public void prepareGroupByResponseForErrorReport(Map<String, Map<String, List<Map<String, Object>>>> baseReportMaps,
			ReportRequestQuery reportRequest, DruidBaseRequestBuilder druidBaseRequestBuilder,
			ReportResponseUI reportResponseUI, String type) throws Exception {

		DruidGroupByResponse[] druidGroupByResponseArray = (DruidGroupByResponse[]) druidRestClient
				.druidResponse(getDruidURI(), reportRequest);

		int level = reportResponseUI.getLevel();
		if (null != druidGroupByResponseArray && (level == 1) && null != reportResponseUI.getReportType()
				&& reportResponseUI.getReportType().equals("PIECHART")) {
			String eventKeyName = reportResponseUI.getConfiguration().getProperties().get(0).getId();
			for (DruidGroupByResponse rd : druidGroupByResponseArray) {
				Map<String, Object> eventData = rd.getEvent();
				String date = rd.getTimestamp();
				if (baseReportMaps.isEmpty()) {
					Map<String, List<Map<String, Object>>> result = new HashMap<>();
					String name = String.valueOf(eventData.get(eventKeyName));
					List<Map<String, Object>> dataMapList = new ArrayList<>();
					eventData.remove(eventKeyName);
					dataMapList.add(eventData);
					result.put(name, dataMapList);
					baseReportMaps.put(date, result);
				} else {
					Map<String, List<Map<String, Object>>> result = baseReportMaps.get(date);
					String name = String.valueOf(eventData.get(eventKeyName));
					List<Map<String, Object>> dataMapList = result.get(name);
					if (null != dataMapList) {
						eventData.remove(eventKeyName);
						dataMapList.add(eventData);
						result.put(name, dataMapList);
						baseReportMaps.put(date, result);
					} else {
						dataMapList = new ArrayList<>();
						eventData.remove(eventKeyName);
						dataMapList.add(eventData);
						result.put(name, dataMapList);
						baseReportMaps.put(date, result);
					}
				}

			}
		} else if (null != druidGroupByResponseArray && (level == 1)) {
			String eventKeyName = reportResponseUI.getConfiguration().getProperties().get(0).getId();
			for (DruidGroupByResponse rd : druidGroupByResponseArray) {
				Map<String, Object> eventData = rd.getEvent();
				String date = rd.getTimestamp();
				if (baseReportMaps.isEmpty()) {
					Map<String, List<Map<String, Object>>> result = new HashMap<>();
					String name = String.valueOf(eventData.get(eventKeyName));
					List<Map<String, Object>> dataMapList = new ArrayList<>();
					eventData.remove(eventKeyName);
					dataMapList.add(eventData);
					result.put(name, dataMapList);
					baseReportMaps.put(date, result);
				} else {
					Map<String, List<Map<String, Object>>> result = baseReportMaps.get(date);
					String name = String.valueOf(eventData.get(eventKeyName));
					List<Map<String, Object>> dataMapList = result.get(name);
					if (null != dataMapList) {
						eventData.remove(eventKeyName);
						dataMapList.add(eventData);
						result.put(name, dataMapList);
						baseReportMaps.put(date, result);
					} else {
						dataMapList = new ArrayList<>();
						eventData.remove(eventKeyName);
						dataMapList.add(eventData);
						result.put(name, dataMapList);
						baseReportMaps.put(date, result);
					}
				}

			}
		} else if (null != druidGroupByResponseArray && level == 2) {
			String eventKeyName = reportResponseUI.getConfiguration().getProperties().get(0).getId();
			for (DruidGroupByResponse rd : druidGroupByResponseArray) {
				Map<String, Object> eventData = rd.getEvent();
				String date = rd.getTimestamp();
				if (baseReportMaps.isEmpty()) {
					Map<String, List<Map<String, Object>>> result = new HashMap<>();
					String name = String.valueOf(eventData.get(eventKeyName));
					List<Map<String, Object>> dataMapList = new ArrayList<>();
					eventData.remove(eventKeyName);
					Map<String, Object> eventData1 = new HashMap<>();
					for (Map.Entry<String, Object> entry : eventData.entrySet()) {
						String key = entry.getKey().concat(type.toLowerCase());
						Object value = entry.getValue();
						eventData1.put(key, value);
					}

					dataMapList.add(eventData1);
					result.put(name, dataMapList);
					baseReportMaps.put(date, result);
				} else {
					Map<String, List<Map<String, Object>>> result = baseReportMaps.get(date);
					String name = String.valueOf(eventData.get(eventKeyName));
					List<Map<String, Object>> dataMapList = result.get(name);
					if (null != dataMapList) {
						eventData.remove(eventKeyName);
						Map<String, Object> eventData1 = new HashMap<>();
						for (Map.Entry<String, Object> entry : eventData.entrySet()) {
							String key = entry.getKey().concat(type.toLowerCase());
							Object value = entry.getValue();
							eventData1.put(key, value);
						}
						dataMapList.add(eventData1);
						result.put(name, dataMapList);
						baseReportMaps.put(date, result);
					} else {
						dataMapList = new ArrayList<>();
						eventData.remove(eventKeyName);
						Map<String, Object> eventData1 = new HashMap<>();
						for (Map.Entry<String, Object> entry : eventData.entrySet()) {
							String key = entry.getKey().concat(type.toLowerCase());
							Object value = entry.getValue();
							eventData1.put(key, value);
						}
						dataMapList.add(eventData1);
						result.put(name, dataMapList);
						baseReportMaps.put(date, result);
					}
				}

			}

		}

	}

	public DruidResponse prepareDruidResponse(List<UserTemplate> userTemplateList,
			HashMap<String, Object> requestParamMap) throws Exception {

		DruidResponse druidResponse = new DruidResponse();
		List<Response> responseList = new ArrayList<>();

		Map<String, Map<String, List<Map<String, Object>>>> responseMap = new HashMap<>();
		Map<String, Map<String, List<List<Map<String, Object>>>>> pieChartResponseMap = new HashMap<>();
		// Map<String, Map<String, List<Map<String, Object>>>> responseMap = new
		// HashMap<>();
		Map<String, Map<String, List<Map<String, Object>>>> responseHeatMap = new HashMap<>();
		Map<String, Map<String, List<Map<String, Object>>>> responseMapForPeakAndAvg = new HashMap<>();
		Map<String, Map<String, Object>> finalpeakAvgMap = new HashMap<>();
		for (UserTemplate userTemplate : userTemplateList) {
			// String reportType = userTemplate.getReportType();
			// Report report = new Report();

			ReportRequestQuery reportRequestQuery = mapper.readValue(userTemplate.getJsonString(),
					new TypeReference<ReportRequestQuery>() {
					});

			// String level = userTemplate.getLevel();
			// userTemplate.getResponseTemplate().getReportResponseId();

			ReportResponseUI reportResponseUI = mapper.readValue(userTemplate.getResponseTemplate(),
					new TypeReference<ReportResponseUI>() {
					});
			/* if(requestParamMap.get("level")!=null){ */
			reportResponseUI.setLevel(Integer.parseInt(requestParamMap.get("level").toString()));
			/*
			 * }else{ reportResponseUI.setLevel(Integer.parseInt(level)); }
			 */

			if (!StringUtils.isEmpty((String) requestParamMap.get("intervals"))) {
				// reportRequest.setIntervals("2017-02-23T10:00:00.000Z/2017-02-23T10:15:00.000Z");
				reportRequestQuery.setIntervals((String) requestParamMap.get("intervals"));
			}

			if (!StringUtils.isEmpty((String) requestParamMap.get("reportDisplayType"))) {
				reportResponseUI.setReportType((String) requestParamMap.get("reportDisplayType"));
			}

			setQueryFilterParameters(reportRequestQuery, requestParamMap, reportResponseUI);

			//

			String queryType = reportRequestQuery.getQueryType();

			if (queryType.equals(Constant.GROUP_BY)) {
				DruidBaseRequestBuilder druidBaseRequestBuilder = new DruidBaseRequestBuilder(queryType,
						reportRequestQuery.getDataSource(), reportRequestQuery.getGranularity(),
						reportRequestQuery.getIntervals());

				if (null != reportRequestQuery.getDimensions() && null != reportRequestQuery.getDimensions()[0]) {
					reportResponseUI.getConfiguration().getProperties().get(0)
							.setId(reportRequestQuery.getDimensions()[0]);
				}

				/*
				 * if (null != reportRequestQuery.getDimensions() && null !=
				 * reportRequestQuery.getDimensions()[1]) {
				 * reportResponseUI.getConfiguration().getProperties().get(1)
				 * .setId(reportRequestQuery.getDimensions()[0]); }
				 */
				if (userTemplate.getReportType().equals(Constant.HEATMAP_FORTOP10MSU)
						|| userTemplate.getReportType().equals(Constant.ERROR_LISTING_HEATMAP)) {
					prepareGroupByResponseMSU(responseHeatMap, reportRequestQuery, druidBaseRequestBuilder,
							reportResponseUI, userTemplate.getType());
				}
				if (userTemplate.getReportType().equals(Constant.TOP10_SUCCESS_RATE)) {
					prepareGroupByResponseForRoamers(responseMap, reportRequestQuery, druidBaseRequestBuilder,
							reportResponseUI, userTemplate.getType());
				}

				
				String granularity = (String) requestParamMap.get("granularity");
				
				switch (userTemplate.getReportType()) {
				case Constant.ROAMERS_REPORT:
					if (null!=reportResponseUI.getReportType()
					&& reportResponseUI.getReportType().equals("lineGraph")) {

				prepareGroupByResponseForRoamersLineGraph(responseMap, reportRequestQuery, druidBaseRequestBuilder,
						reportResponseUI, userTemplate.getType());
			} else
				
			{
					prepareGroupByResponseForRoamers(responseMap, reportRequestQuery, druidBaseRequestBuilder,
							reportResponseUI, userTemplate.getType());
			}
					break;
				case Constant.BILLING_REPORT:
					prepareGroupByResponseBilling(responseMap, reportRequestQuery, druidBaseRequestBuilder,
							reportResponseUI, userTemplate.getType());
					break;
				case Constant.ROAMERS_REJECTION_REPORT:
					
					prepareGroupByResponseForRoamingRejection(responseMap, reportRequestQuery, druidBaseRequestBuilder,
							reportResponseUI, userTemplate.getType());
			
					break;
				case Constant.ERROR_REPORT:
					if (null != reportResponseUI.getReportType()
							&& reportResponseUI.getReportType().equals("ERRORPIECHART")) {
						prepareGroupByResponseForErrorPieChart(pieChartResponseMap, reportRequestQuery,
								druidBaseRequestBuilder, reportResponseUI, userTemplate.getType());

					} else if (null != reportResponseUI.getReportType()
							&& reportResponseUI.getReportType().equals("lineGraph")) {
						prepareGroupByResponseLineGraph(responseMap, reportRequestQuery, druidBaseRequestBuilder,
								reportResponseUI, userTemplate.getType());

					} else
						prepareGroupByResponseForErrorReport(responseMap, reportRequestQuery, druidBaseRequestBuilder,
								reportResponseUI, userTemplate.getType());
					break;
				case Constant.DESTINATION_REPORT:
					if (null!=reportResponseUI.getReportType()
							&& reportResponseUI.getReportType().equals("lineGraph")) {

						prepareGroupByResponseLineGraph(responseMap, reportRequestQuery, druidBaseRequestBuilder,
								reportResponseUI, userTemplate.getType());
					} else if (requestParamMap.get("granularity").equals("day")) {
						boolean isAvgPeak = userTemplate.getQuery_type().equals("AVG_PEAK") ? true : false;

						Map<String, Object> data = new HashMap<String, Object>();
						data.put("type", userTemplate.getType());
						String date = (String) requestParamMap.get("intervals");
						data.put("date", date.split("/")[0].split("T")[0]);

						if (isAvgPeak) {
							getIntervalForAvgAndPeak(reportRequestQuery);
							prepareGroupByResponseFullday(responseMapForPeakAndAvg, reportRequestQuery,
									druidBaseRequestBuilder, reportResponseUI, data);
						} else {
							prepareGroupByResponseFullday(responseMap, reportRequestQuery, druidBaseRequestBuilder,
									reportResponseUI, data);
						}

					} else {
						boolean isAvgPeak = userTemplate.getQuery_type().equals("AVG_PEAK") ? true : false;
						if (isAvgPeak) {
							getIntervalForAvgAndPeak(reportRequestQuery);
							prepareGroupByResponse(responseMapForPeakAndAvg, reportRequestQuery,
									druidBaseRequestBuilder, reportResponseUI, userTemplate.getType());
						} else {
							prepareGroupByResponse(responseMap, reportRequestQuery, druidBaseRequestBuilder,
									reportResponseUI, userTemplate.getType());
						}
					}

					break;
				case Constant.SECURITY_VIOLATION_REPORT:

					if (null != reportResponseUI.getReportType()
							&& reportResponseUI.getReportType().equals("PIECHART")) {
						prepareGroupByResponseForSecurityViolationPieChart(pieChartResponseMap, reportRequestQuery,
								druidBaseRequestBuilder, reportResponseUI, userTemplate.getType());

					} 
					
					
					else if (null!=reportResponseUI.getReportType()
							&& reportResponseUI.getReportType().equals("lineGraph")) {

						prepareGroupByResponseLineGraph(responseMap, reportRequestQuery, druidBaseRequestBuilder,
								reportResponseUI, userTemplate.getType());
					}
									
					else {
						prepareGroupByResponseForSecurityViolation(responseMap, reportRequestQuery,
								druidBaseRequestBuilder, reportResponseUI, userTemplate.getType());
					}
					break;

				default:
					break;
				}

				System.out.println(Constant.GROUP_BY);
			} else if (queryType.equals(Constant.SELECT)) {
				DruidBaseRequestBuilder druidBaseRequestBuilder = new DruidBaseRequestBuilder(queryType,
						reportRequestQuery.getDataSource(), reportRequestQuery.getGranularity(),
						reportRequestQuery.getIntervals());

				prepareSelectResponse(responseList, reportRequestQuery, druidBaseRequestBuilder);
				System.out.println(Constant.SELECT);

			}

		}

		List<BaseLeveReport> reports = new ArrayList<>();
		String reportType = (String) requestParamMap.get(Constant.REPORT_TYPE);
		int level = (int) requestParamMap.get(Constant.LEVEL);
		String graphType =(String) requestParamMap.get("reportDisplayType");
		
		switch (reportType) {
		
		case Constant.ERROR_REPORT:
	
			if (!responseMap.isEmpty()) {
				if (!reports.isEmpty()) {
					reports.clear();
				}
				if(graphType!=null && graphType.equals("lineGraph"))
				{
						reports.addAll(handleResponseForReportsLineGraph(Constant.ERROR_REPORT, responseMap));
				}
				else
				{
						reports.addAll(handleResponseForReports(Constant.ERROR_REPORT, responseMap));
				}
			} else if (!pieChartResponseMap.isEmpty()) {

				reports.addAll(handleResponseForErrorPieChart(Constant.SECURITY_VIOLATION_REPORT, pieChartResponseMap));
			}
			break;
		case Constant.ROAMERS_REPORT:
			if (!responseMap.isEmpty()) {
				if (!reports.isEmpty()) {
					reports.clear();
				}
				if(graphType!=null && graphType.equals("lineGraph"))
				{
					reports.addAll(handleResponseForReportsLineGraphRoamers(Constant.ROAMERS_REPORT, responseMap));
				}
				else
				{
					reports.addAll(handleResponseForReports(Constant.ROAMERS_REPORT, responseMap));
				}
			}
			break;
		case Constant.ROAMERS_REJECTION_REPORT:
			if (!responseMap.isEmpty()) {
				if (!reports.isEmpty()) {
					reports.clear();
				}
				
				reports.addAll(handleRomarsRejectionResponse(responseMap, level));
			}
			break;
		case Constant.DESTINATION_REPORT:
			
			if (!responseMap.isEmpty()) {
				prepareResponseForAvgAndPeak(responseMapForPeakAndAvg, finalpeakAvgMap);
				if (null==graphType){// && !graphType.equals("lineGraph")) {
					handleResponseForAvgAndPeak(responseMap, finalpeakAvgMap, reports);
					// reports.addAll(handleResponseForAvgAndPeak(responseMap,finalpeakAvgMap,reports));\
				} else if(graphType.equals("lineGraph")) {
					
					handleResponseForAvgAndPeakLineGraph(responseMap, finalpeakAvgMap, reports);
				}
			}
			break;

		case Constant.BILLING_REPORT:
			if (!responseMap.isEmpty()) {
				if (!reports.isEmpty()) {
					reports.clear();
				}
				reports.addAll(handleBillingReportReponse(responseMap));
			}
			break;

		case Constant.SECURITY_VIOLATION_REPORT:
			if (!responseMap.isEmpty()) {
				if(null!=graphType && graphType.equals("lineGraph"))
				{
					reports.addAll(handleResponseForReportsLineGraph(Constant.SECURITY_VIOLATION_REPORT, responseMap));
				}
				else
				reports.addAll(handleResponseForReports(Constant.SECURITY_VIOLATION_REPORT, responseMap));
			} else if (!pieChartResponseMap.isEmpty()) {
				reports.addAll(handleResponseForSecurityViolationPieChart(Constant.SECURITY_VIOLATION_REPORT,
						pieChartResponseMap));
			}

			break;
		default:
			break;
		}

		if (reportType.equals(Constant.HEATMAP_FORTOP10MSU)) {
			if (!responseHeatMap.isEmpty()) {
				if (!reports.isEmpty()) {
					reports.clear();
				}
				reports.addAll(handleResponseForReports(reportType, responseHeatMap));
			}
			List<BaseLeveReport> top10Reports = filterTop10(reports);
			reports.clear();
			reports.addAll(top10Reports);

		}
		if (reportType.equals(Constant.TOP10_SUCCESS_RATE)) {
			if (!responseMap.isEmpty()) {
				if (!reports.isEmpty()) {
					reports.clear();
				}
				reports.addAll(handleResponseForReports(reportType, responseMap));
			}
			// System.out.println("---reports---"+reports);
			List<BaseLeveReport> top10Reports = filterTop10(reports);
			reports.clear();
			reports.addAll(top10Reports);
			// System.out.println("---reports---"+reports);

		}
		if (reportType.equals(Constant.ERROR_LISTING_HEATMAP)) {
			if (!responseHeatMap.isEmpty()) {
				if (!reports.isEmpty()) {
					reports.clear();
				}
				reports.addAll(handleResponseForErrorListingHeatMap(reportType, responseHeatMap));
			}
			List<BaseLeveReport> top10Reports = filterTop10(reports);
			reports.clear();
			reports.addAll(top10Reports);
		}
		// Response from select query
		if (!responseList.isEmpty()) {
			responseList.forEach(response -> {
				List<Map<String, Object>> events = response.getEvents();
				events.forEach(event -> {
					BaseLeveReport baseReport = new BaseLeveReport();
					baseReport.setTimeStamp((String) event.get("timestamp"));
					event.remove("timestamp");
					baseReport.setData(event);
					baseReport.setOperatorName((String) event.get("gsm_map_calling_inv_carrier_name"));
					baseReport.setProduct("2G/3G");
					/*
					 * if (reportType.equals(Constant.ROAMERS_REJECTION_REPORT))
					 * { reports.clear(); }
					 */
					reports.add(baseReport);
					druidResponse.setHeaders(event.keySet());
				});

			});

		}

		druidResponse.setReports(reports);
		responseHeatMap.clear();
		responseMap.clear();
		return druidResponse;

	}

	public void prepareGroupByResponseLineGraph(Map<String, Map<String, List<Map<String, Object>>>> baseReportMaps,
			ReportRequestQuery reportRequest, DruidBaseRequestBuilder druidBaseRequestBuilder,
			ReportResponseUI reportResponseUI, String type) throws Exception {
		DruidGroupByResponse[] druidGroupByResponseArray = (DruidGroupByResponse[]) druidRestClient
				.druidResponse(getDruidURI(), reportRequest);

		int level = reportResponseUI.getLevel();
		if (null != druidGroupByResponseArray && level == 2) {
			String eventKeyName = reportResponseUI.getConfiguration().getProperties().get(0).getId();
			for (DruidGroupByResponse rd : druidGroupByResponseArray) {
				Map<String, Object> eventData = rd.getEvent();
				String date = rd.getTimestamp();
				String name = String.valueOf(eventData.get(eventKeyName));
				if (baseReportMaps.isEmpty()) {
					Map<String, List<Map<String, Object>>> result = new HashMap<>();
					// String name =
					// String.valueOf(eventData.get(eventKeyName));
					List<Map<String, Object>> dataMapList = new ArrayList<>();
					eventData.remove(eventKeyName);
					Map<String, Object> eventData1 = new HashMap<>();
					for (Map.Entry<String, Object> entry : eventData.entrySet()) {
						String key = entry.getKey();// .concat(type.toLowerCase());
						Object value = entry.getValue();
						eventData1.put(key, value);
					}

					dataMapList.add(eventData1);
					result.put(date, dataMapList);
					baseReportMaps.put(name, result);
				} else {
					Map<String, List<Map<String, Object>>> result = baseReportMaps.get(name);
					// String name =
					// String.valueOf(eventData.get(eventKeyName));
					if (null != result) {
						List<Map<String, Object>> dataMapList = result.get(date);// i
																					// guess
																					// need
																					// check
						if (null != dataMapList) {
							eventData.remove(eventKeyName);
							Map<String, Object> eventData1 = new HashMap<>();
							for (Map.Entry<String, Object> entry : eventData.entrySet()) {
								String key = entry.getKey();// .concat(type.toLowerCase());
								Object value = entry.getValue();
								eventData1.put(key, value);
							}
							dataMapList.add(eventData1);
							result.put(date, dataMapList);
							baseReportMaps.put(name, result);
						} else {
							dataMapList = new ArrayList<>();
							eventData.remove(eventKeyName);
							Map<String, Object> eventData1 = new HashMap<>();
							for (Map.Entry<String, Object> entry : eventData.entrySet()) {
								String key = entry.getKey();// .concat(type.toLowerCase());
								Object value = entry.getValue();
								eventData1.put(key, value);
							}
							dataMapList.add(eventData1);
							result.put(date, dataMapList);
							baseReportMaps.put(name, result);
						}
					}else
					{
					//	Map<String, List<Map<String, Object>>> result = new HashMap<>();
						// String name =
						// String.valueOf(eventData.get(eventKeyName));
							result = new HashMap<>();
						List<Map<String, Object>> dataMapList = new ArrayList<>();
						eventData.remove(eventKeyName);
						Map<String, Object> eventData1 = new HashMap<>();
						for (Map.Entry<String, Object> entry : eventData.entrySet()) {
							String key = entry.getKey();// .concat(type.toLowerCase());
							Object value = entry.getValue();
							eventData1.put(key, value);
						}

						dataMapList.add(eventData1);
						result.put(date, dataMapList);
						baseReportMaps.put(name, result);
						
					}
				}

			}

		}

	}

	private List<BaseLeveReport> handleResponseForAvgAndPeak(
			Map<String, Map<String, List<Map<String, Object>>>> responseMap,
			Map<String, Map<String, Object>> finalpeakAvgMap, List<BaseLeveReport> reports) {
		for (Map.Entry<String, Map<String, List<Map<String, Object>>>> result : responseMap

				.entrySet()) {
			String timestamp = result.getKey();
			result.getValue().entrySet().forEach(ent -> {
				BaseLeveReport baseReport = new BaseLeveReport();
				baseReport.setTimeStamp(timestamp);
				baseReport.setOperatorName(ent.getKey());
				baseReport.setProduct("2G/3G");
				Map<String, Object> data = new HashMap<>();
				ent.getValue().forEach(p -> {
					p.entrySet().forEach(q -> {
						Object count = data.get(q.getKey());
						if (null == count) {
							Integer record = (Integer) q.getValue();
							data.put(q.getKey(), record);
						} else {
							Integer record = (Integer) q.getValue();
							Integer count1 = (Integer) count;
							data.put(q.getKey(), Integer.sum(count1, record));
						}

					});
				});
				if (null != finalpeakAvgMap) {
					if (null != finalpeakAvgMap.get(ent.getKey())) {
						Map<String, Object> mapobj = finalpeakAvgMap.get(ent.getKey());
						// int avgMSU = (int) mapobj.get("TotalMSU") ;
						double avgMSU = (int) mapobj.get("TotalMSU");
						avgMSU = avgMSU / 96;
						data.put("AvgMSU", avgMSU);
						data.put("peakMSU", mapobj.get("peakMSU"));
					}
				}
				baseReport.setData(data);
				reports.add(baseReport);
			});
		}
		return reports;
	}

	private List<BaseLeveReport> handleResponseForAvgAndPeakLineGraph(
			Map<String, Map<String, List<Map<String, Object>>>> responseMap,
			Map<String, Map<String, Object>> finalpeakAvgMap, List<BaseLeveReport> reports) {
		for (Map.Entry<String, Map<String, List<Map<String, Object>>>> result : responseMap

				.entrySet()) {

			result.getValue().entrySet().forEach(ent -> {
				//String timestamp = result.getKey();
				BaseLeveReport baseReport = new BaseLeveReport();
				baseReport.setTimeStamp(ent.getKey());
				baseReport.setOperatorName(result.getKey());// ent.getKey());
				baseReport.setProduct("2G/3G");
				Map<String, Object> data = new HashMap<>();
				ent.getValue().forEach(p -> {
					p.entrySet().forEach(q -> {
						Object count = data.get(q.getKey());
						if (null == count) {
							Integer record = (Integer) q.getValue();
							data.put(q.getKey(), record);
						} else {
							Integer record = (Integer) q.getValue();
							Integer count1 = (Integer) count;
							data.put(q.getKey(), Integer.sum(count1, record));
						}

					});
				});
				if (null != finalpeakAvgMap) {
					if (null != finalpeakAvgMap.get(ent.getKey())) {
						Map<String, Object> mapobj = finalpeakAvgMap.get(ent.getKey());
						// int avgMSU = (int) mapobj.get("TotalMSU") ;
						double avgMSU = (int) mapobj.get("TotalMSU");
						avgMSU = avgMSU / 96;
						data.put("AvgMSU", avgMSU);
						data.put("peakMSU", mapobj.get("peakMSU"));
					}
				}
				baseReport.setData(data);
				reports.add(baseReport);
			});
		}
		return reports;
	}

	private void prepareResponseForAvgAndPeak(
			Map<String, Map<String, List<Map<String, Object>>>> responseMapForPeakAndAvg,
			Map<String, Map<String, Object>> finalpeakAvgMap) {
		if (!responseMapForPeakAndAvg.isEmpty()) {
			Map<String, Map<String, Map<String, Object>>> peakAvgMap = new HashMap<>();
			for (Map.Entry<String, Map<String, List<Map<String, Object>>>> result : responseMapForPeakAndAvg
					.entrySet()) {

				for (Map.Entry<String, List<Map<String, Object>>> entry : result.getValue().entrySet()) {
					String timestamp = result.getKey();

					String mapkey = entry.getKey();
					entry.getValue().forEach(p -> {
						p.entrySet().forEach(q -> {
							if (peakAvgMap.get(timestamp) != null) {
								Map<String, Map<String, Object>> mapobj = peakAvgMap.get(timestamp);
								if (null != mapobj.get(mapkey)) {
									Map<String, Object> dataMap = mapobj.get(mapkey);
									int peakMSU = (int) dataMap.get("peakMSU");
									int totalMSU = (int) dataMap.get("TotalMSU");
									Integer record = (Integer) q.getValue();
									dataMap.put("peakMSU", Integer.sum(peakMSU, record));
									dataMap.put("TotalMSU", Integer.sum(totalMSU, record));
									peakAvgMap.put(timestamp, mapobj);
								} else {

									Map<String, Object> dataMap = new HashMap<>();
									Integer value = (Integer) q.getValue();
									dataMap.put("TotalMSU", value);
									dataMap.put("peakMSU", value);
									mapobj.put(mapkey, dataMap);
									peakAvgMap.put(timestamp, mapobj);
								}
							} else {
								Map<String, Map<String, Object>> mapobj = new HashMap<>();
								Map<String, Object> dataMap = new HashMap<>();
								Integer value = (Integer) q.getValue();
								dataMap.put("TotalMSU", value);
								dataMap.put("peakMSU", value);
								mapobj.put(mapkey, dataMap);
								peakAvgMap.put(timestamp, mapobj);
							}
						});
					});
				}
			}
			System.out.println("peakAvgMap  " + peakAvgMap.isEmpty());
			if (!peakAvgMap.isEmpty()) {

				for (Map.Entry<String, Map<String, Map<String, Object>>> result : peakAvgMap.entrySet()) {
					result.getValue().forEach((key, value) -> {
						if (null != finalpeakAvgMap.get(key)) {
							Map<String, Object> mapobj = finalpeakAvgMap.get(key);
							int peakMSU = (int) mapobj.get("peakMSU");
							int totalMSU = (int) mapobj.get("TotalMSU");
							int currentMSU = (int) value.get("peakMSU");
							if (peakMSU < currentMSU) {
								peakMSU = currentMSU;
							}
							totalMSU = totalMSU + (int) value.get("TotalMSU");
							mapobj.put("TotalMSU", totalMSU);
							mapobj.put("peakMSU", peakMSU);
						} else {
							finalpeakAvgMap.put(key, value);
						}
					});
				}
				System.out.println("finalpeakAvgMap" + finalpeakAvgMap);
			}
		}

	}

	private void getIntervalForAvgAndPeak(ReportRequestQuery reportRequestQuery) {
		
		  String requestInterval = reportRequestQuery.getIntervals(); String[]
		  intervals = org.apache.commons.lang.StringUtils.split(requestInterval, "/"); String[] enddate
		  = org.apache.commons.lang.StringUtils.split(intervals[0], "T"); String[] date =
				  org.apache.commons.lang.StringUtils.split(enddate[0], "-"); String startdateForRequest =
				  org.apache.commons.lang.StringUtils.join(date, "-"); date[2] =
		  String.valueOf(Integer.parseInt(date[2]) + 1); String
		 enddateForRequest = (org.apache.commons.lang.StringUtils.join(date, "-"));
		  
		  reportRequestQuery.setIntervals(startdateForRequest + "/" +
		  enddateForRequest);
		 
	}

	private void prepareGroupByResponseForRoamers(Map<String, Map<String, List<Map<String, Object>>>> baseReportMaps,
			ReportRequestQuery reportRequest, DruidBaseRequestBuilder druidBaseRequestBuilder,
			ReportResponseUI reportResponseUI, String type) throws Exception {

		DruidGroupByResponse[] druidGroupByResponseArray = (DruidGroupByResponse[]) druidRestClient
				.druidResponse(getDruidURI(), reportRequest);
		// System.out.println("---FROm
		// RUID---"+druidGroupByResponseArray.toString());
		int level = reportResponseUI.getLevel();
		if (null != druidGroupByResponseArray && (level == 1)) {
			// System.out.println("-----------Level1-------------" + level);
			String eventKeyName = reportResponseUI.getConfiguration().getProperties().get(0).getId();
			String imsi = reportResponseUI.getConfiguration().getMetrics().get(0).getName();
			String dataSource = reportResponseUI.getDatasource();

			// System.out.println("dataSource " + dataSource +
			// "..../eventKeyName---" + eventKeyName + "----/imsi---" + imsi);
			for (DruidGroupByResponse rd : druidGroupByResponseArray) {
				Map<String, Object> eventData = rd.getEvent();
				// System.out.println("---eventData--"+eventData);
				String date = rd.getTimestamp();
				if (baseReportMaps.isEmpty()) {
					Map<String, List<Map<String, Object>>> result = new HashMap<>();
					String name = String.valueOf(eventData.get(eventKeyName));
					List<Map<String, Object>> dataMapList = new ArrayList<>();
					eventData.remove(eventKeyName);
					Map<String, Object> dataAfterKey = new HashMap<String, Object>();
					if (eventKeyName.equals("gsm_map_called_inv_carrier_name")) {
						dataAfterKey.put("OutIMSI", eventData.get(imsi));
						dataAfterKey.put("dataSource", dataSource);
					} else if (eventKeyName.equals("gsm_map_calling_inv_carrier_name")) {
						dataAfterKey.put("InIMSI", eventData.get(imsi));
						dataAfterKey.put("dataSource", dataSource);
					}

					dataMapList.add(dataAfterKey);
					result.put(name, dataMapList);
					baseReportMaps.put(date, result);
				} else {
					Map<String, List<Map<String, Object>>> result = baseReportMaps.get(date);
					String name = String.valueOf(eventData.get(eventKeyName));
					List<Map<String, Object>> dataMapList = result.get(name);
					if (null != dataMapList) {
						eventData.remove(eventKeyName);

						Map<String, Object> dataAfterKey = new HashMap<String, Object>();
						if (eventKeyName.equals("gsm_map_called_inv_carrier_name")) {
							dataAfterKey.put("OutIMSI", eventData.get(imsi));
							dataAfterKey.put("dataSource", dataSource);
						} else if (eventKeyName.equals("gsm_map_calling_inv_carrier_name")) {
							dataAfterKey.put("InIMSI", eventData.get(imsi));
							dataAfterKey.put("dataSource", dataSource);
						}
						dataMapList.add(dataAfterKey);
						result.put(name, dataMapList);
						baseReportMaps.put(date, result);
					} else {
						dataMapList = new ArrayList<>();
						eventData.remove(eventKeyName);

						Map<String, Object> dataAfterKey = new HashMap<String, Object>();
						if (eventKeyName.equals("gsm_map_called_inv_carrier_name")) {
							dataAfterKey.put("OutIMSI", eventData.get(imsi));
							dataAfterKey.put("dataSource", dataSource);
						} else if (eventKeyName.equals("gsm_map_calling_inv_carrier_name")) {
							dataAfterKey.put("InIMSI", eventData.get(imsi));
							dataAfterKey.put("dataSource", dataSource);
						}

						dataMapList.add(dataAfterKey);
						result.put(name, dataMapList);
						baseReportMaps.put(date, result);
					}
				}

			}
		} else if (null != druidGroupByResponseArray && level == 2) {
			// System.out.println("-----------Level2-------------"+level);
			String eventKeyName = reportResponseUI.getConfiguration().getProperties().get(0).getId();
			String dataSource = reportResponseUI.getDatasource();
			for (DruidGroupByResponse rd : druidGroupByResponseArray) {
				Map<String, Object> eventData = rd.getEvent();
				// System.out.println("---eventData--"+eventData);
				String date = rd.getTimestamp();
				if (baseReportMaps.isEmpty()) {
					Map<String, List<Map<String, Object>>> result = new HashMap<>();
					String name = String.valueOf(eventData.get(eventKeyName));
					List<Map<String, Object>> dataMapList = new ArrayList<>();
					eventData.remove(eventKeyName);
					Map<String, Object> eventData1 = new HashMap<>();

					Map<String, Object> dataAfterKey = new HashMap<String, Object>();
					for (Map.Entry<String, Object> entry : eventData.entrySet()) {
						String key = entry.getKey().concat(type);
						Object value = entry.getValue();
						eventData1.put(key, value);
						// System.out.println("eventKeyName: " + eventKeyName +
						// " ref :" + ref);
						if (eventKeyName.equals("gsm_map_calling_inv_carrier_name")) {
							dataAfterKey.put("OutIMSI" + type, eventData1.get(key));
							dataAfterKey.put("dataSource", dataSource);
						} else if (eventKeyName.equals("gsm_map_called_inv_carrier_name")) {
							dataAfterKey.put("InIMSI" + type, eventData1.get(key));
							dataAfterKey.put("dataSource", dataSource);
						}
					}
					dataMapList.add(dataAfterKey);
					result.put(name, dataMapList);
					baseReportMaps.put(date, result);
				} else {
					Map<String, List<Map<String, Object>>> result = baseReportMaps.get(date);
					String name = String.valueOf(eventData.get(eventKeyName));
					List<Map<String, Object>> dataMapList = result.get(name);
					if (null != dataMapList) {
						eventData.remove(eventKeyName);
						Map<String, Object> eventData1 = new HashMap<>();
						Map<String, Object> dataAfterKey = new HashMap<String, Object>();
						for (Map.Entry<String, Object> entry : eventData.entrySet()) {
							String key = entry.getKey().concat(type);
							Object value = entry.getValue();
							eventData1.put(key, value);
							if (eventKeyName.equals("gsm_map_calling_inv_carrier_name")) {
								dataAfterKey.put("OutIMSI" + type, eventData1.get(key));
								dataAfterKey.put("dataSource", dataSource);
							} else if (eventKeyName.equals("gsm_map_called_inv_carrier_name")) {
								dataAfterKey.put("InIMSI" + type, eventData1.get(key));
								dataAfterKey.put("dataSource", dataSource);
							}
						}
						dataMapList.add(dataAfterKey);
						result.put(name, dataMapList);
						baseReportMaps.put(date, result);
					} else {
						String comKey = "";
						dataMapList = new ArrayList<>();
						eventData.remove(eventKeyName);
						Map<String, Object> eventData1 = new HashMap<>();
						for (Map.Entry<String, Object> entry : eventData.entrySet()) {
							String key = entry.getKey().concat(type);
							comKey = key;
							Object value = entry.getValue();
							eventData1.put(key, value);
						}

						Map<String, Object> dataAfterKey = new HashMap<String, Object>();
						if (eventKeyName.equals("gsm_map_calling_inv_carrier_name")) {
							dataAfterKey.put("OutIMSI" + type, eventData1.get(comKey));
							dataAfterKey.put("dataSource", dataSource);
						} else if (eventKeyName.equals("gsm_map_called_inv_carrier_name")) {
							dataAfterKey.put("InIMSI" + type, eventData1.get(comKey));
							dataAfterKey.put("dataSource", dataSource);
						}

						dataMapList.add(dataAfterKey);
						result.put(name, dataMapList);
						baseReportMaps.put(date, result);
					}
				}

			}

		}

	}
	
	
	
	
	
	private void prepareGroupByResponseForRoamersLineGraph(Map<String, Map<String, List<Map<String, Object>>>> baseReportMaps,
			ReportRequestQuery reportRequest, DruidBaseRequestBuilder druidBaseRequestBuilder,
			ReportResponseUI reportResponseUI, String type) throws Exception {

	DruidGroupByResponse[] druidGroupByResponseArray = (DruidGroupByResponse[]) druidRestClient
			.druidResponse(getDruidURI(), reportRequest);
	// System.out.println("---FROm
	// RUID---"+druidGroupByResponseArray.toString());
	int level = reportResponseUI.getLevel();
	if (null != druidGroupByResponseArray && (level == 1)) {
		// System.out.println("-----------Level1-------------" + level);
		String eventKeyName = reportResponseUI.getConfiguration().getProperties().get(0).getId();
		String imsi = reportResponseUI.getConfiguration().getMetrics().get(0).getName();
		String dataSource = reportResponseUI.getDatasource();

		// System.out.println("dataSource " + dataSource +
		// "..../eventKeyName---" + eventKeyName + "----/imsi---" + imsi);
		for (DruidGroupByResponse rd : druidGroupByResponseArray) {
			Map<String, Object> eventData = rd.getEvent();
			// System.out.println("---eventData--"+eventData);
			String date = rd.getTimestamp();
			if (baseReportMaps.isEmpty()) {
				Map<String, List<Map<String, Object>>> result = new HashMap<>();
				String name = String.valueOf(eventData.get(eventKeyName));
				List<Map<String, Object>> dataMapList = new ArrayList<>();
				eventData.remove(eventKeyName);
				Map<String, Object> dataAfterKey = new HashMap<String, Object>();
				if (eventKeyName.equals("gsm_map_called_inv_carrier_name")) {
					dataAfterKey.put("OutIMSI", eventData.get(imsi));
					dataAfterKey.put("dataSource", dataSource);
				} else if (eventKeyName.equals("gsm_map_calling_inv_carrier_name")) {
					dataAfterKey.put("InIMSI", eventData.get(imsi));
					dataAfterKey.put("dataSource", dataSource);
				}

				dataMapList.add(dataAfterKey);
				result.put(name, dataMapList);
				baseReportMaps.put(date, result);
			} else {
				Map<String, List<Map<String, Object>>> result = baseReportMaps.get(date);
				String name = String.valueOf(eventData.get(eventKeyName));
				List<Map<String, Object>> dataMapList = result.get(name);
				if (null != dataMapList) {
					eventData.remove(eventKeyName);

					Map<String, Object> dataAfterKey = new HashMap<String, Object>();
					if (eventKeyName.equals("gsm_map_called_inv_carrier_name")) {
						dataAfterKey.put("OutIMSI", eventData.get(imsi));
						dataAfterKey.put("dataSource", dataSource);
					} else if (eventKeyName.equals("gsm_map_calling_inv_carrier_name")) {
						dataAfterKey.put("InIMSI", eventData.get(imsi));
						dataAfterKey.put("dataSource", dataSource);
					}
					dataMapList.add(dataAfterKey);
					result.put(name, dataMapList);
					baseReportMaps.put(date, result);
				} else {
					dataMapList = new ArrayList<>();
					eventData.remove(eventKeyName);

					Map<String, Object> dataAfterKey = new HashMap<String, Object>();
					if (eventKeyName.equals("gsm_map_called_inv_carrier_name")) {
						dataAfterKey.put("OutIMSI", eventData.get(imsi));
						dataAfterKey.put("dataSource", dataSource);
					} else if (eventKeyName.equals("gsm_map_calling_inv_carrier_name")) {
						dataAfterKey.put("InIMSI", eventData.get(imsi));
						dataAfterKey.put("dataSource", dataSource);
					}

					dataMapList.add(dataAfterKey);
					result.put(name, dataMapList);
					baseReportMaps.put(date, result);
				}
			}

		}
	} else if (null != druidGroupByResponseArray && level == 2) {
		// System.out.println("-----------Level2-------------"+level);
		String eventKeyName = reportResponseUI.getConfiguration().getProperties().get(0).getId();
		String dataSource = reportResponseUI.getDatasource();
		for (DruidGroupByResponse rd : druidGroupByResponseArray) {
			Map<String, Object> eventData = rd.getEvent();
			// System.out.println("---eventData--"+eventData);
			String date = rd.getTimestamp();
			if (baseReportMaps.isEmpty()) {
				Map<String, List<Map<String, Object>>> result = new HashMap<>();
				String name = String.valueOf(eventData.get(eventKeyName));
				List<Map<String, Object>> dataMapList = new ArrayList<>();
				eventData.remove(eventKeyName);
				Map<String, Object> eventData1 = new HashMap<>();

				Map<String, Object> dataAfterKey = new HashMap<String, Object>();
				for (Map.Entry<String, Object> entry : eventData.entrySet()) {
					String key = entry.getKey().concat(type);
					Object value = entry.getValue();
					eventData1.put(key, value);
					// System.out.println("eventKeyName: " + eventKeyName +
					// " ref :" + ref);
					if (eventKeyName.equals("gsm_map_calling_inv_carrier_name")) {
						dataAfterKey.put("OutIMSI" , eventData1.get(key));
						dataAfterKey.put("dataSource", dataSource);
					} else if (eventKeyName.equals("gsm_map_called_inv_carrier_name")) {
						dataAfterKey.put("InIMSI" , eventData1.get(key));
						dataAfterKey.put("dataSource", dataSource);
					}
				}
				dataMapList.add(dataAfterKey);
				result.put(name, dataMapList);
				baseReportMaps.put(date, result);
			} else {
				Map<String, List<Map<String, Object>>> result = baseReportMaps.get(date);
				String name = String.valueOf(eventData.get(eventKeyName));
				List<Map<String, Object>> dataMapList = result.get(name);
				if (null != dataMapList) {
					eventData.remove(eventKeyName);
					Map<String, Object> eventData1 = new HashMap<>();
					Map<String, Object> dataAfterKey = new HashMap<String, Object>();
					for (Map.Entry<String, Object> entry : eventData.entrySet()) {
						String key = entry.getKey().concat(type);
						Object value = entry.getValue();
						eventData1.put(key, value);
						if (eventKeyName.equals("gsm_map_calling_inv_carrier_name")) {
							dataAfterKey.put("OutIMSI" , eventData1.get(key));
							dataAfterKey.put("dataSource", dataSource);
						} else if (eventKeyName.equals("gsm_map_called_inv_carrier_name")) {
							dataAfterKey.put("InIMSI" , eventData1.get(key));
							dataAfterKey.put("dataSource", dataSource);
						}
					}
					dataMapList.add(dataAfterKey);
					result.put(name, dataMapList);
					baseReportMaps.put(date, result);
				} else {
					String comKey = "";
					dataMapList = new ArrayList<>();
					eventData.remove(eventKeyName);
					Map<String, Object> eventData1 = new HashMap<>();
					for (Map.Entry<String, Object> entry : eventData.entrySet()) {
						String key = entry.getKey().concat(type);
						comKey = key;
						Object value = entry.getValue();
						eventData1.put(key, value);
					}

					Map<String, Object> dataAfterKey = new HashMap<String, Object>();
					if (eventKeyName.equals("gsm_map_calling_inv_carrier_name")) {
						dataAfterKey.put("OutIMSI" , eventData1.get(comKey));
						dataAfterKey.put("dataSource", dataSource);
					} else if (eventKeyName.equals("gsm_map_called_inv_carrier_name")) {
						dataAfterKey.put("InIMSI" , eventData1.get(comKey));
						dataAfterKey.put("dataSource", dataSource);
					}

					dataMapList.add(dataAfterKey);
					result.put(name, dataMapList);
					baseReportMaps.put(date, result);
				}
			}

		}}

	}


	

	private List<BaseLeveReport> handleResponseForSecurityViolationPieChart(String reportType,
			Map<String, Map<String, List<List<Map<String, Object>>>>> responseMap) {
		List<BaseLeveReport> reports = new ArrayList<>();

		for (Map.Entry<String, Map<String, List<List<Map<String, Object>>>>> result : responseMap.entrySet()) {
			String timestamp = result.getKey();
			result.getValue().entrySet().forEach(ent -> {
				BaseLeveReport baseReport = new BaseLeveReport();
				baseReport.setTimeStamp(timestamp);
				baseReport.setOperatorName(ent.getKey());
				baseReport.setProduct("2G/3G");

				Map<String, Object> map = new HashMap<>();

				// Map<String, Object> map= new HashMap<>();
				Map<String, Map<String, Object>> dataMap = new LinkedHashMap<>();
				List<Map<String, Map<String, Object>>> data = new LinkedList<>();
				ent.getValue().forEach(p -> {
					p.forEach(k -> {
						k.entrySet().forEach(q -> {

							// Object count = data.get(q.getKey());

							map.put(q.getKey(), q.getValue());

						});

					});

				});

				dataMap.put("Operation_Type", map);
				data.add(dataMap);
				baseReport.setDataList(data);
				reports.add(baseReport);
			});

		}
		return reports;
	}

	private List<BaseLeveReport> handleResponseForErrorPieChart(String reportType,
			Map<String, Map<String, List<List<Map<String, Object>>>>> responseMap) {
		List<BaseLeveReport> reports = new ArrayList<>();

		for (Map.Entry<String, Map<String, List<List<Map<String, Object>>>>> result : responseMap.entrySet()) {
			String timestamp = result.getKey();
			result.getValue().entrySet().forEach(ent -> {
				BaseLeveReport baseReport = new BaseLeveReport();
				baseReport.setTimeStamp(timestamp);
				baseReport.setOperatorName(ent.getKey());
				baseReport.setProduct("2G/3G");

				Map<String, Object> inboundMap = new HashMap<>();
				Map<String, Object> outboundMap = new HashMap<>();
				Map<String, Map<String, Object>> dataMap = new LinkedHashMap<>();
				List<Map<String, Map<String, Object>>> data = new LinkedList<>();
				ent.getValue().forEach(p -> {
					p.forEach(k -> {
						k.entrySet().forEach(q -> {

							// Object count = data.get(q.getKey());

							if (q.getKey().contains("calling")) {
								inboundMap.put(q.getKey(), q.getValue());
							} else {
								outboundMap.put(q.getKey(), q.getValue());
							}

						});

					});

				});

				dataMap.put("Inbound_Error_Code", inboundMap);
				dataMap.put("Outbound_Error_Code", outboundMap);
				data.add(dataMap);
				baseReport.setDataList(data);
				reports.add(baseReport);
			});

		}
		return reports;
	}

	private List<BaseLeveReport> handleResponseForReports(String reportType,
			Map<String, Map<String, List<Map<String, Object>>>> responseMap) {
		List<BaseLeveReport> reports = new ArrayList<>();

		for (Map.Entry<String, Map<String, List<Map<String, Object>>>> result : responseMap.entrySet()) {
			String timestamp = result.getKey();
			result.getValue().entrySet().forEach(ent -> {
				BaseLeveReport baseReport = new BaseLeveReport();
				baseReport.setTimeStamp(timestamp);
				baseReport.setOperatorName(ent.getKey());
				baseReport.setProduct("2G/3G");
				Map<String, Object> data = new HashMap<>();
				ent.getValue().forEach(p -> {

					p.entrySet().forEach(q -> {

						Object count = data.get(q.getKey());

						switch (reportType) {
						case Constant.ERROR_REPORT:
							if (q.getKey().equals("gsm_map_error_code")) {
								HashMap<String, Object> errorCodeMap = new HashMap<>();
								errorCodeMap.put(q.getKey(), q.getValue());
								data.put(q.getKey(), errorCodeMap);
							}
							if (null == count) {
								Integer record = Integer.parseInt(q.getValue().toString());
								data.put(q.getKey(), record);
							} else {
								Integer record = Integer.parseInt(q.getValue().toString());
								Integer count1 = Integer.parseInt(count.toString());
								data.put(q.getKey(), Integer.sum(count1, record));
							}
							// data.put(q.getKey(), q.getValue());
							if (q.getValue().equals("gsm_map_data_source")
									|| q.getValue().equals("camel_data_source")) {
								baseReport.setProduct("2G/3G");
							} else {
								baseReport.setProduct("LTE");
							}

							break;
						case Constant.HEATMAP_FORTOP10MSU:
							if (null == count) {
								Integer record = (Integer) q.getValue();
								data.put(q.getKey(), record);
							} else {
								Integer record = (Integer) q.getValue();
								Integer count1 = (Integer) count;
								data.put(q.getKey(), Integer.sum(count1, record));
							}
							if (data.containsKey(Constant.TOTAL_MSU)) {
								Integer pre = (Integer) data.get(Constant.TOTAL_MSU);
								Integer value = (Integer) q.getValue();
								data.put(Constant.TOTAL_MSU, Integer.sum(pre, value));
							} else {
								data.put(Constant.TOTAL_MSU, q.getValue());
							}
							break;
						case Constant.ROAMERS_REPORT:
							if (q.getValue() != null) {
								if (null == count) {
									String input = q.getValue().toString();
									int index = input.indexOf(".");
									if (index > 0)
										input = input.substring(0, index);
									Integer record = Integer.parseInt(input);
									data.put(q.getKey(), record);
								} else {
									String input = q.getValue().toString();
									int index = input.indexOf(".");
									if (index > 0)
										input = input.substring(0, index);
									Integer record = Integer.parseInt(input);
									Integer count1 = Integer.parseInt(count.toString());
									data.put(q.getKey(), Integer.sum(count1, record));
								}
							} else {
								// System.out.println("-----else---null---");
							}
							break;
						case Constant.TOP10_SUCCESS_RATE:
							if (q.getValue() != null) {
								if (null == count) {
									String input = q.getValue().toString();
									int index = input.indexOf(".");
									if (index > 0)
										input = input.substring(0, index);
									Integer record = Integer.parseInt(input);
									data.put(q.getKey(), record);
								} else {
									String input = q.getValue().toString();
									int index = input.indexOf(".");
									if (index > 0)
										input = input.substring(0, index);
									Integer record = Integer.parseInt(input);
									Integer count1 = Integer.parseInt(count.toString());
									data.put(q.getKey(), Integer.sum(count1, record));
								}
								if (data.containsKey(Constant.TOTAL_MSU)) {
									Integer pre = (Integer) data.get(Constant.TOTAL_MSU);
									String input = q.getValue().toString();
									int index = input.indexOf(".");
									if (index > 0)
										input = input.substring(0, index);
									Integer record = Integer.parseInt(input);
									data.put(Constant.TOTAL_MSU, Integer.sum(pre, record));
								} else {
									String input = q.getValue().toString();
									int index = input.indexOf(".");
									if (index > 0)
										input = input.substring(0, index);
									Integer record = Integer.parseInt(input);
									data.put(Constant.TOTAL_MSU, record);
								}
							} else {
								// System.out.println("-----else---null---");
							}
							break;
						case Constant.SECURITY_VIOLATION_REPORT:

							data.put(q.getKey(), q.getValue());
							/*
							 * if(q.getValue().equals("gsm_map")||q.getValue().
							 * equals("camel")){ baseReport.setProduct("2G/3G");
							 * }else{ baseReport.setProduct("LTE"); }
							 */
							break;

						default:
							break;
						}
					});
				});
				baseReport.setData(data);
				reports.add(baseReport);
			});
		}
		return reports;
	}
	
	private List<BaseLeveReport> handleResponseForReportsLineGraph(String reportType,
			Map<String, Map<String, List<Map<String, Object>>>> responseMap) {
		List<BaseLeveReport> reports = new ArrayList<>();

		for (Map.Entry<String, Map<String, List<Map<String, Object>>>> result : responseMap.entrySet()) {
		
			result.getValue().entrySet().forEach(ent -> {
				String timestamp = ent.getKey();//result.getKey();
				BaseLeveReport baseReport = new BaseLeveReport();
				baseReport.setTimeStamp(timestamp);
				baseReport.setOperatorName(result.getKey());
				baseReport.setProduct("2G/3G");
				Map<String, Object> data = new HashMap<>();
				ent.getValue().forEach(p -> {

					p.entrySet().forEach(q -> {

						Object count = data.get(q.getKey());

						switch (reportType) {
						case Constant.ERROR_REPORT:
							if (q.getKey().equals("gsm_map_error_code")) {
								HashMap<String, Object> errorCodeMap = new HashMap<>();
								errorCodeMap.put(q.getKey(), q.getValue());
								data.put(q.getKey(), errorCodeMap);
							}
							if (null == count) {
								Integer record = Integer.parseInt(q.getValue().toString());
								data.put(q.getKey(), record);
							} else {
								Integer record = Integer.parseInt(q.getValue().toString());
								Integer count1 = Integer.parseInt(count.toString());
								data.put(q.getKey(), Integer.sum(count1, record));
							}
							// data.put(q.getKey(), q.getValue());
							if (q.getValue().equals("gsm_map_data_source")
									|| q.getValue().equals("camel_data_source")) {
								baseReport.setProduct("2G/3G");
							} else {
								baseReport.setProduct("LTE");
							}

							break;
						case Constant.HEATMAP_FORTOP10MSU:
							if (null == count) {
								Integer record = (Integer) q.getValue();
								data.put(q.getKey(), record);
							} else {
								Integer record = (Integer) q.getValue();
								Integer count1 = (Integer) count;
								data.put(q.getKey(), Integer.sum(count1, record));
							}
							if (data.containsKey(Constant.TOTAL_MSU)) {
								Integer pre = (Integer) data.get(Constant.TOTAL_MSU);
								Integer value = (Integer) q.getValue();
								data.put(Constant.TOTAL_MSU, Integer.sum(pre, value));
							} else {
								data.put(Constant.TOTAL_MSU, q.getValue());
							}
							break;
						case Constant.ROAMERS_REPORT:
							if (q.getValue() != null) {
								if (null == count) {
									String input = q.getValue().toString();
									int index = input.indexOf(".");
									if (index > 0)
										input = input.substring(0, index);
									Integer record = Integer.parseInt(input);
									data.put(q.getKey(), record);
								} else {
									String input = q.getValue().toString();
									int index = input.indexOf(".");
									if (index > 0)
										input = input.substring(0, index);
									Integer record = Integer.parseInt(input);
									Integer count1 = Integer.parseInt(count.toString());
									data.put(q.getKey(), Integer.sum(count1, record));
								}
							} else {
								// System.out.println("-----else---null---");
							}
							break;
						case Constant.TOP10_SUCCESS_RATE:
							if (q.getValue() != null) {
								if (null == count) {
									String input = q.getValue().toString();
									int index = input.indexOf(".");
									if (index > 0)
										input = input.substring(0, index);
									Integer record = Integer.parseInt(input);
									data.put(q.getKey(), record);
								} else {
									String input = q.getValue().toString();
									int index = input.indexOf(".");
									if (index > 0)
										input = input.substring(0, index);
									Integer record = Integer.parseInt(input);
									Integer count1 = Integer.parseInt(count.toString());
									data.put(q.getKey(), Integer.sum(count1, record));
								}
								if (data.containsKey(Constant.TOTAL_MSU)) {
									Integer pre = (Integer) data.get(Constant.TOTAL_MSU);
									String input = q.getValue().toString();
									int index = input.indexOf(".");
									if (index > 0)
										input = input.substring(0, index);
									Integer record = Integer.parseInt(input);
									data.put(Constant.TOTAL_MSU, Integer.sum(pre, record));
								} else {
									String input = q.getValue().toString();
									int index = input.indexOf(".");
									if (index > 0)
										input = input.substring(0, index);
									Integer record = Integer.parseInt(input);
									data.put(Constant.TOTAL_MSU, record);
								}
							} else {
								// System.out.println("-----else---null---");
							}
							break;
						case Constant.SECURITY_VIOLATION_REPORT:

							data.put(q.getKey(), q.getValue());
							/*
							 * if(q.getValue().equals("gsm_map")||q.getValue().
							 * equals("camel")){ baseReport.setProduct("2G/3G");
							 * }else{ baseReport.setProduct("LTE"); }
							 */
							break;

						default:
							break;
						}
					});
				});
				baseReport.setData(data);
				reports.add(baseReport);
			});
		}
		return reports;
	}
	private List<BaseLeveReport> handleResponseForReportsLineGraphRoamers(String reportType,
			Map<String, Map<String, List<Map<String, Object>>>> responseMap) {
		List<BaseLeveReport> reports = new ArrayList<>();

		for (Map.Entry<String, Map<String, List<Map<String, Object>>>> result : responseMap.entrySet()) {
		
			result.getValue().entrySet().forEach(ent -> {
				String timestamp = result.getKey();
				BaseLeveReport baseReport = new BaseLeveReport();
				baseReport.setTimeStamp(timestamp);
				baseReport.setOperatorName(ent.getKey());//result.getKey());
				baseReport.setProduct("2G/3G");
				Map<String, Object> data = new HashMap<>();
				ent.getValue().forEach(p -> {

					p.entrySet().forEach(q -> {

						Object count = data.get(q.getKey());

						switch (reportType) {
						case Constant.ERROR_REPORT:
							if (q.getKey().equals("gsm_map_error_code")) {
								HashMap<String, Object> errorCodeMap = new HashMap<>();
								errorCodeMap.put(q.getKey(), q.getValue());
								data.put(q.getKey(), errorCodeMap);
							}
							if (null == count) {
								Integer record = Integer.parseInt(q.getValue().toString());
								data.put(q.getKey(), record);
							} else {
								Integer record = Integer.parseInt(q.getValue().toString());
								Integer count1 = Integer.parseInt(count.toString());
								data.put(q.getKey(), Integer.sum(count1, record));
							}
							// data.put(q.getKey(), q.getValue());
							if (q.getValue().equals("gsm_map_data_source")
									|| q.getValue().equals("camel_data_source")) {
								baseReport.setProduct("2G/3G");
							} else {
								baseReport.setProduct("LTE");
							}

							break;
						case Constant.HEATMAP_FORTOP10MSU:
							if (null == count) {
								Integer record = (Integer) q.getValue();
								data.put(q.getKey(), record);
							} else {
								Integer record = (Integer) q.getValue();
								Integer count1 = (Integer) count;
								data.put(q.getKey(), Integer.sum(count1, record));
							}
							if (data.containsKey(Constant.TOTAL_MSU)) {
								Integer pre = (Integer) data.get(Constant.TOTAL_MSU);
								Integer value = (Integer) q.getValue();
								data.put(Constant.TOTAL_MSU, Integer.sum(pre, value));
							} else {
								data.put(Constant.TOTAL_MSU, q.getValue());
							}
							break;
						case Constant.ROAMERS_REPORT:
							if (q.getValue() != null) {
								if (null == count) {
									String input = q.getValue().toString();
									int index = input.indexOf(".");
									if (index > 0)
										input = input.substring(0, index);
									Integer record = Integer.parseInt(input);
									
									data.put(q.getKey(), record);
								} else {
									String input = q.getValue().toString();
									int index = input.indexOf(".");
									if (index > 0)
										input = input.substring(0, index);
									Integer record = Integer.parseInt(input);
									Integer count1 = Integer.parseInt(count.toString());
									data.put(q.getKey(), Integer.sum(count1, record));
								}
							} else {
								// System.out.println("-----else---null---");
							}
							break;
						case Constant.TOP10_SUCCESS_RATE:
							if (q.getValue() != null) {
								if (null == count) {
									String input = q.getValue().toString();
									int index = input.indexOf(".");
									if (index > 0)
										input = input.substring(0, index);
									Integer record = Integer.parseInt(input);
									data.put(q.getKey(), record);
								} else {
									String input = q.getValue().toString();
									int index = input.indexOf(".");
									if (index > 0)
										input = input.substring(0, index);
									Integer record = Integer.parseInt(input);
									Integer count1 = Integer.parseInt(count.toString());
									data.put(q.getKey(), Integer.sum(count1, record));
								}
								if (data.containsKey(Constant.TOTAL_MSU)) {
									Integer pre = (Integer) data.get(Constant.TOTAL_MSU);
									String input = q.getValue().toString();
									int index = input.indexOf(".");
									if (index > 0)
										input = input.substring(0, index);
									Integer record = Integer.parseInt(input);
									data.put(Constant.TOTAL_MSU, Integer.sum(pre, record));
								} else {
									String input = q.getValue().toString();
									int index = input.indexOf(".");
									if (index > 0)
										input = input.substring(0, index);
									Integer record = Integer.parseInt(input);
									data.put(Constant.TOTAL_MSU, record);
								}
							} else {
								// System.out.println("-----else---null---");
							}
							break;
						case Constant.SECURITY_VIOLATION_REPORT:

							data.put(q.getKey(), q.getValue());
							/*
							 * if(q.getValue().equals("gsm_map")||q.getValue().
							 * equals("camel")){ baseReport.setProduct("2G/3G");
							 * }else{ baseReport.setProduct("LTE"); }
							 */
							break;

						default:
							break;
						}
					});
				});
				baseReport.setData(data);
				reports.add(baseReport);
			});
		}
		return reports;
	}


	private List<BaseLeveReport> handleResponseForErrorListingHeatMap(String reportType,
			Map<String, Map<String, List<Map<String, Object>>>> responseMap) {
		List<BaseLeveReport> reports = new ArrayList<>();
		Map<String, Map<String, Integer>> getMaxErrorCount = getMaxErrorTypeCount(responseMap);
		Map<String, ErrorDetail> allErrorRecordsMap = new HashMap<>();
		List<ErrorDetail> allErrorRecordsList = commonDao.getAllErrorRecords();
		for (ErrorDetail errorDetail : allErrorRecordsList) {
			allErrorRecordsMap.put(errorDetail.getErrorCode(), errorDetail);
		}
		for (Map.Entry<String, Map<String, List<Map<String, Object>>>> result : responseMap.entrySet()) {
			String timestamp = result.getKey();
			result.getValue().entrySet().forEach(ent -> {
				BaseLeveReport baseReport = new BaseLeveReport();
				baseReport.setTimeStamp(timestamp);
				baseReport.setOperatorName(ent.getKey());
				Map<String, Integer> getMap = getMaxErrorCount.get(ent.getKey());
				Map<String, Object> data = new HashMap<>();
				ent.getValue().forEach(p -> {
					Object out = p.get("OUTBOUND_count");
					Object in = p.get("INBOUND_count");
					p.entrySet().forEach(q -> {
						String getKey = q.getKey();
						Object getValue = q.getValue();
						Object count = data.get(q.getKey());
						if (null == count) {
							Integer record = Integer.parseInt(q.getValue().toString());
							data.put(q.getKey(), record);
						} else {
							Integer record = Integer.parseInt(q.getValue().toString());
							Integer count1 = (Integer) count;
							data.put(q.getKey(), Integer.sum(count1, record));
						}

						if (data.containsKey(Constant.TOTAL_MSU)) {
							Integer pre = Integer.parseInt(data.get(Constant.TOTAL_MSU).toString());
							Integer value = Integer.parseInt(q.getValue().toString());
							data.put(Constant.TOTAL_MSU, Integer.sum(pre, value));
						} else {
							data.put(Constant.TOTAL_MSU, q.getValue());
						}

						if (out != null) {
							if (getMap.containsKey(getKey + "_" + getValue)) {
								Integer errorCount = Integer.parseInt(getMap.get(getKey + "_" + getValue).toString());
								data.put("outboundErrorCode", getValue);
								data.put("outboundErrorCount", errorCount);
								// ErrorDetail
								// error=commonDao.getErrorByErrorCode(getValue.toString());
								ErrorDetail error = allErrorRecordsMap.get(getValue.toString());
								if (error != null) {
									data.put("outboundDiscription", error.getErrorDescription());
								}
							}
						} else if (in != null) {
							if (getMap.containsKey(getKey + "_" + getValue)) {
								Integer errorCount = Integer.parseInt(getMap.get(getKey + "_" + getValue).toString());
								data.put("inboundErrorCode", getValue);
								data.put("inboundErrorCount", errorCount);
								// ErrorDetail
								// error=commonDao.getErrorByErrorCode(getValue.toString());
								ErrorDetail error = allErrorRecordsMap.get(getValue.toString());
								if (error != null) {
									data.put("inboundDiscription", error.getErrorDescription());
								}
							}
						}
					});
				});
				baseReport.setData(data);
				reports.add(baseReport);
			});
		}
		return reports;
	}

	private Map<String, Map<String, Integer>> getMaxErrorTypeCount(
			Map<String, Map<String, List<Map<String, Object>>>> responseMap) {
		Map<String, Map<String, Integer>> maxErrorCount = new HashMap<>();
		for (Map.Entry<String, Map<String, List<Map<String, Object>>>> result : responseMap.entrySet()) {
			result.getValue().entrySet().forEach(ent -> {
				Map<String, Integer> errorCountMap = new HashMap<>();
				ent.getValue().forEach(p -> {
					p.entrySet().forEach(q -> {
						String key = q.getKey();
						Object value = q.getValue();
						if (value != null
								&& (key.equals("gsm_map_error_code") || key.equals("gsm_map_error_codegsm"))) {
							if (errorCountMap.containsKey(key + "_" + value)) {
								int count = Integer.parseInt(errorCountMap.get(key + "_" + value).toString());
								if (count > 0) {
									errorCountMap.put(key + "_" + value, ++count);
									maxErrorCount.put(ent.getKey(), errorCountMap);
								}
							} else {
								errorCountMap.put(key + "_" + value, 1);
								maxErrorCount.put(ent.getKey(), errorCountMap);
							}
						}

					});

				});
			});
		}
		return maxErrorCount;
	}

	private List<BaseLeveReport> handleBillingReportReponse(
			Map<String, Map<String, List<Map<String, Object>>>> responseMap) {
		List<BaseLeveReport> reports = new ArrayList<>();
		for (Map.Entry<String, Map<String, List<Map<String, Object>>>> result : responseMap.entrySet()) {
			String timestamp = result.getKey();
			BaseLeveReport baseReport = new BaseLeveReport();
			BaseLeveReport baseReport4G = new BaseLeveReport();
			Map<String, Object> data = new HashMap<>();
			Map<String, Object> data4G = new HashMap<>();
			result.getValue().entrySet().forEach(ent -> {
				baseReport.setTimeStamp(timestamp);
				baseReport4G.setTimeStamp(timestamp);
				ent.getValue().forEach(p -> {
					if (p.containsValue("camel_data_source") || p.containsValue("gsm_map_data_source")) {
						p.entrySet().forEach(q -> {
							Object count = data.get(q.getKey());
							if (!q.getKey().contains("dataSource")) {
								if (null == count) {
									Integer record = (Integer) q.getValue();
									data.put(q.getKey(), record);
								} else {
									Integer record = (Integer) q.getValue();
									Integer count1 = (Integer) count;
									data.put(q.getKey(), Integer.sum(count1, record));
								}
							} else {
								data.put("dataSource_" + q.getValue(), q.getValue());
								baseReport.setProduct("2G/3G");
							}

						});
					} else {
						data.put("INBOUND_count", 0);
						data.put("OUTBOUND_count", 0);
						data.put("dataSource_gsm_map", "gsm_map");
						data.put("dataSource_camel", "camel");
						baseReport.setProduct("2G/3G");
					}

					if (p.containsValue("diameter")) {
						p.entrySet().forEach(q -> {
							Object count = data.get(q.getKey());
							if (!q.getKey().contains("dataSource")) {
								if (null == count) {
									Integer record = (Integer) q.getValue();
									data4G.put(q.getKey(), record);
								} else {
									Integer record = (Integer) q.getValue();
									Integer count1 = (Integer) count;
									data4G.put(q.getKey(), Integer.sum(count1, record));
								}
							} else {
								data4G.put("dataSource_" + q.getValue(), q.getValue());
								baseReport4G.setProduct("LTE/4G");
							}

						});
					} else {
						data4G.put("INBOUND_count", 0);
						data4G.put("OUTBOUND_count", 0);
						data4G.put("dataSource_diameter", "diameter");
						baseReport4G.setProduct("LTE/4G");
					}

				});
			});
			baseReport.setData(data);
			baseReport4G.setData(data4G);
			reports.add(baseReport);
			reports.add(baseReport4G);
		}
		return reports;
	}

	private void prepareGroupByResponseMSU(Map<String, Map<String, List<Map<String, Object>>>> baseReportMaps,
			ReportRequestQuery reportRequest, DruidBaseRequestBuilder druidBaseRequestBuilder,
			ReportResponseUI reportResponseUI, String type) throws Exception {

		DruidGroupByResponse[] druidGroupByResponseArray = (DruidGroupByResponse[]) druidRestClient
				.druidResponse(getDruidURI(), reportRequest);

		if (null != druidGroupByResponseArray) {
			String eventKeyName = reportResponseUI.getConfiguration().getProperties().get(0).getId();
			for (DruidGroupByResponse rd : druidGroupByResponseArray) {
				Map<String, Object> eventData = rd.getEvent();
				String date = rd.getTimestamp();
				if (baseReportMaps.isEmpty()) {
					Map<String, List<Map<String, Object>>> result = new HashMap<>();
					String name = String.valueOf(eventData.get(eventKeyName));
					List<Map<String, Object>> dataMapList = new ArrayList<>();
					eventData.remove(eventKeyName);
					dataMapList.add(eventData);
					result.put(name, dataMapList);
					baseReportMaps.put(date, result);
				} else {
					Map<String, List<Map<String, Object>>> result = baseReportMaps.get(date);
					if (result != null) {
						String name = String.valueOf(eventData.get(eventKeyName));
						List<Map<String, Object>> dataMapList = result.get(name);
						if (null != dataMapList) {
							eventData.remove(eventKeyName);
							dataMapList.add(eventData);
							// System.out.println(eventData);
							result.put(name, dataMapList);
							baseReportMaps.put(date, result);
						} else {
							dataMapList = new ArrayList<>();
							eventData.remove(eventKeyName);
							dataMapList.add(eventData);
							// System.out.println(eventData);
							result.put(name, dataMapList);
							baseReportMaps.put(date, result);
						}
					} else {
						Map<String, List<Map<String, Object>>> result1 = new HashMap<>();
						String name = String.valueOf(eventData.get(eventKeyName));
						List<Map<String, Object>> dataMapList = new ArrayList<>();
						eventData.remove(eventKeyName);
						dataMapList.add(eventData);
						// System.out.println(eventData);
						result1.put(name, dataMapList);
						baseReportMaps.put(date, result1);
					}
				}

			}
			// }
		}

	}

	private void setQueryFilterParameters(ReportRequestQuery reportRequestQuery,
			HashMap<String, Object> requestParamMap, ReportResponseUI reportResponseUI)
			throws JsonParseException, JsonMappingException, IOException {

		switch (requestParamMap.get(Constant.REPORT_TYPE).toString()) {
		case Constant.ERROR_REPORT:
			if ((Integer) requestParamMap.get("level") == 2 && null != requestParamMap.get("reportDisplayType")
					&& requestParamMap.get("reportDisplayType").equals("ERRORPIECHART")) {

				Filter filter = mapper.readValue(requestParamMap.get("value_one").toString(),
						new TypeReference<Filter>() {
						});
				String value = filter.getFields().get(0).getValue();
				String valuetwo = filter.getFields().get(1).getValue();

				Rules rules = reportResponseUI.getFilter();
				Filter oldfilter = reportRequestQuery.getFilter();

				Fields field1 = new Fields();
				field1.setType(oldfilter.getFields().get(0).getType());
				field1.setDimension(reportRequestQuery.getDimensions()[0]);
				field1.setValue(value);

				Fields field2 = new Fields();
				field2.setType(oldfilter.getFields().get(0).getType());
				field2.setDimension(oldfilter.getFields().get(0).getDimension());
				field2.setValue(valuetwo);

				Fields field3 = new Fields();
				field3.setType(oldfilter.getFields().get(1).getType());

				Fields field33 = new Fields();
				field33.setType(oldfilter.getFields().get(1).getField().getType());
				field33.setDimension(oldfilter.getFields().get(1).getField().getDimension());
				field33.setValue(oldfilter.getFields().get(1).getField().getValue());

				field3.setField(field33);

				Filter newFilter = new Filter();
				newFilter.setType(rules.getCondition());

				List<Fields> fields = new ArrayList<>();

				fields.add(field1);
				fields.add(field2);
				fields.add(field3);
				newFilter.setFields(fields);
				reportRequestQuery.setFilter(newFilter);
				reportRequestQuery
						.setDimensions(new String[] { reportRequestQuery.getFilter().getFields().get(0).getDimension(),
								reportRequestQuery.getFilter().getFields().get(2).getField().getDimension() });
			} else if ((Integer) requestParamMap.get("level") == 2 && null != requestParamMap.get("reportDisplayType")
					&& requestParamMap.get("reportDisplayType").equals("lineGraph")) {

				Filter filter = mapper.readValue(requestParamMap.get("value_one").toString(),
						new TypeReference<Filter>() {
						});
				String value = filter.getFields().get(0).getValue();

				reportRequestQuery.getFilter().getFields().get(0).setValue(value);

			} else if ((Integer) requestParamMap.get("level") == 2) {
				Filter filter = mapper.readValue(requestParamMap.get("value_one").toString(),
						new TypeReference<Filter>() {
						});
				String value = filter.getFields().get(0).getValue();

				reportRequestQuery.getFilter().getFields().get(0).setValue(value);

			} else if ((Integer) requestParamMap.get("level") == 3) {
				Filter filter = mapper.readValue(requestParamMap.get("value_one").toString(),
						new TypeReference<Filter>() {
						});
				String value = filter.getFields().get(0).getValue();

				String value1 = filter.getFields().get(1).getValue();
				
				reportRequestQuery.getFilter().getFields().get(0).setValue(value);
				reportRequestQuery.getFilter().getFields().get(1).setValue(value1);

			
				// Filter filter2 =
				// mapper.readValue(requestParamMap.get("value_two").toString(),
				// new TypeReference<Filter>() {
				// });
				String value2 = filter.getFields().get(1).getValue();

				reportRequestQuery.getFilter().getFields().get(1).setValue(value2);

				reportRequestQuery.getPagingSpec().setThreshold((int) requestParamMap.get("totalRecords"));

			} else if ((Integer) requestParamMap.get("level") == 1
					&& null != requestParamMap.get("reportDisplayType")) {
				/*
				 * Rules rules =
				 * mapper.readValue(reportResponseUI.getFilter().toString(), new
				 * TypeReference<Rules>() { });
				 */
				Rules rules = reportResponseUI.getFilter();
				Filter oldfilter = reportRequestQuery.getFilter();

				Filter newFilter = new Filter();
				newFilter.setType(rules.getCondition());

				List<Fields> fields = new LinkedList<>();

				Fields field1 = new Fields();
				// Rule rule = (Rule)rules.getRules()[0];
				field1.setType(rules.getRules().get(0).getType());
				// field1.setDimension(rules.getRules().get(0).getField());
				field1.setDimension(reportRequestQuery.getDimensions()[0]);
				field1.setValue(rules.getRules().get(0).getValue());

				Fields field3 = new Fields();
				field3.setType(oldfilter.getType());

				Fields field2 = new Fields();
				field2.setType(rules.getRules().get(0).getType());
				field2.setDimension(oldfilter.getField().getDimension());
				// field2.setDimension(reportRequestQuery.getDimensions()[]);
				field2.setValue(oldfilter.getField().getValue());

				field3.setField(field2);
				fields.add(field1);
				fields.add(field3);

				newFilter.setFields(fields);
				reportRequestQuery.setFilter(newFilter);

				Filter filter = mapper.readValue(requestParamMap.get("value_one").toString(),
						new TypeReference<Filter>() {
						});
				String value = filter.getFields().get(0).getValue();
				//
				reportRequestQuery.getFilter().getFields().get(0).setValue(value);

				// reportRequestQuery.setDimensions(new
				// String[]{reportRequestQuery.getFilter().getFields().get(1).getField().getDimension()});
				reportRequestQuery
						.setDimensions(new String[] { reportRequestQuery.getFilter().getFields().get(0).getDimension(),
								reportRequestQuery.getFilter().getFields().get(1).getField().getDimension() });
				//
				// reportRequestQuery.getPagingSpec().setThreshold((int)
				// requestParamMap.get("totalRecords"));
			}
			break;
		case Constant.DESTINATION_REPORT:

			if ((Integer) requestParamMap.get("level") == 2) {
				Filter filter = mapper.readValue(requestParamMap.get("value_one").toString(),
						new TypeReference<Filter>() {
						});
				String value = filter.getFields().get(0).getValue();

				reportRequestQuery.getFilter().getFields().get(0).setValue(value);

			} else if ((Integer) requestParamMap.get("level") == 3) {
				Filter filter = mapper.readValue(requestParamMap.get("value_one").toString(),
						new TypeReference<Filter>() {
						});
				String value = filter.getFields().get(0).getValue();

				String value1 = filter.getFields().get(1).getValue();
				reportRequestQuery.getFilter().getFields().get(0).setValue(value);
				reportRequestQuery.getFilter().getFields().get(1).setValue(value1);
				reportRequestQuery.getPagingSpec().setThreshold((int) requestParamMap.get("totalRecords"));
			}
			break;

		case Constant.ROAMERS_REPORT:
			if ((Integer) requestParamMap.get("level") == 2) {
				Filter filter = mapper.readValue(requestParamMap.get("value_one").toString(),
						new TypeReference<Filter>() {
						});
				String value = filter.getFields().get(0).getValue();
				reportRequestQuery.getFilter().getFields().get(0).setValue(value);
			} else if ((Integer) requestParamMap.get("level") == 3) {
				Filter filter = mapper.readValue(requestParamMap.get("value_one").toString(),
						new TypeReference<Filter>() {
						});
				String value = filter.getFields().get(0).getValue();

				reportRequestQuery.getFilter().getFields().get(0).setValue(value);

				reportRequestQuery.getPagingSpec().setThreshold((int) requestParamMap.get("totalRecords"));
				// reportRequestQuery.getPagingSpec().setThreshold((int)
				// requestParamMap.get("totalRecords"));
			}
			break;
		case Constant.TOP10_SUCCESS_RATE:
			if ((Integer) requestParamMap.get("level") == 2) {
				Filter filter = mapper.readValue(requestParamMap.get("value_one").toString(),
						new TypeReference<Filter>() {
						});
				String value = filter.getFields().get(0).getValue();
				reportRequestQuery.getFilter().getFields().get(0).setValue(value);
			}
			break;
		case Constant.ERROR_LISTING_HEATMAP:
			if ((Integer) requestParamMap.get("level") == 2) {
				Filter filter = mapper.readValue(requestParamMap.get("value_one").toString(),
						new TypeReference<Filter>() {
						});
				String value = filter.getFields().get(0).getValue();
				reportRequestQuery.getFilter().getFields().get(0).setValue(value);

			}
			break;
		case Constant.HEATMAP_FORTOP10MSU:
			if ((Integer) requestParamMap.get("level") == 2) {
				Filter filter = mapper.readValue(requestParamMap.get("value_one").toString(),
						new TypeReference<Filter>() {
						});
				String value = filter.getFields().get(0).getValue();
				reportRequestQuery.getFilter().getFields().get(0).setValue(value);

			}
			break;

		case Constant.ROAMERS_REJECTION_REPORT:
			if ((Integer) requestParamMap.get("level") == 2) {
				Filter filter = mapper.readValue(requestParamMap.get("value_one").toString(),
						new TypeReference<Filter>() {
						});
				String value = filter.getFields().get(0).getValue();
				reportRequestQuery.getFilter().getFields().get(0).setValue(value);
			} else if ((Integer) requestParamMap.get("level") == 3) {
				Filter filter = mapper.readValue(requestParamMap.get("value_one").toString(),
						new TypeReference<Filter>() {
						});
				String value = filter.getFields().get(0).getValue();

				reportRequestQuery.getFilter().getFields().get(0).setValue(value);

				reportRequestQuery.getPagingSpec().setThreshold((int) requestParamMap.get("totalRecords"));
				// reportRequestQuery.getPagingSpec().setThreshold((int)
				// requestParamMap.get("totalRecords"));
			}
			break;

		case Constant.SECURITY_VIOLATION_REPORT:
			if ((Integer) requestParamMap.get("level") == 2) {
				Filter filter = mapper.readValue(requestParamMap.get("value_one").toString(),
						new TypeReference<Filter>() {
						});
				String value = filter.getFields().get(0).getValue();
				reportRequestQuery.getFilter().getFields().get(0).setValue(value);
			} else if ((Integer) requestParamMap.get("level") == 3) {
				Filter filter = mapper.readValue(requestParamMap.get("value_one").toString(),
						new TypeReference<Filter>() {
						});
				String value = filter.getFields().get(0).getValue();

				String value1 = filter.getFields().get(1).getValue();

				reportRequestQuery.getFilter().getFields().get(0).setValue(value);

				reportRequestQuery.getFilter().getFields().get(1).setValue(value1);

				reportRequestQuery.getPagingSpec().setThreshold((int) requestParamMap.get("totalRecords"));
				// reportRequestQuery.getPagingSpec().setThreshold((int)
				// requestParamMap.get("totalRecords"));
			} else if ((Integer) requestParamMap.get("level") == 2
					&& requestParamMap.get("reportDisplayType").equals("pieChart")) {
				Filter filter = mapper.readValue(requestParamMap.get("value_one").toString(),
						new TypeReference<Filter>() {
						});
				String value = filter.getFields().get(0).getValue();
				reportRequestQuery.getFilter().getFields().get(0).setValue(value);

				String secondValue = filter.getFields().get(1).getValue();
				reportRequestQuery.getFilter().getFields().get(1).setValue(secondValue);
			}
			break;

		}

	}

	public void prepareGroupByResponseBilling(Map<String, Map<String, List<Map<String, Object>>>> baseReportMaps,
			ReportRequestQuery reportRequest, DruidBaseRequestBuilder druidBaseRequestBuilder,
			ReportResponseUI reportResponseUI, String type) throws Exception {

		DruidGroupByResponse[] druidGroupByResponseArray = (DruidGroupByResponse[]) druidRestClient
				.druidResponse(getDruidURI(), reportRequest);

		int level = reportResponseUI.getLevel();
		if (null != druidGroupByResponseArray && (level == 1)) {
			String eventKeyName = reportResponseUI.getConfiguration().getProperties().get(0).getId();
			for (DruidGroupByResponse rd : druidGroupByResponseArray) {
				Map<String, Object> eventData = rd.getEvent();
				String date = rd.getTimestamp();
				if (baseReportMaps.isEmpty()) {
					Map<String, List<Map<String, Object>>> result = new HashMap<>();
					String name = String.valueOf(eventData.get(eventKeyName));
					List<Map<String, Object>> dataMapList = new ArrayList<>();
					eventData.remove(eventKeyName);
					eventData.put("dataSource_" + reportRequest.getDataSource(), reportRequest.getDataSource());
					dataMapList.add(eventData);
					result.put(name, dataMapList);
					baseReportMaps.put(date, result);
				} else {
					Map<String, List<Map<String, Object>>> result = baseReportMaps.get(date);
					if (result != null) {
						String name = String.valueOf(eventData.get(eventKeyName));
						List<Map<String, Object>> dataMapList = result.get(name);
						if (null != dataMapList) {
							eventData.remove(eventKeyName);
							eventData.put("dataSource_" + reportRequest.getDataSource(), reportRequest.getDataSource());
							dataMapList.add(eventData);
							result.put(name, dataMapList);
							baseReportMaps.put(date, result);
						} else {
							dataMapList = new ArrayList<>();
							eventData.remove(eventKeyName);
							eventData.put("dataSource_" + reportRequest.getDataSource(), reportRequest.getDataSource());
							dataMapList.add(eventData);
							result.put(name, dataMapList);
							baseReportMaps.put(date, result);
						}
					} else {
						Map<String, List<Map<String, Object>>> result1 = new HashMap<>();
						String name = String.valueOf(eventData.get(eventKeyName));
						List<Map<String, Object>> dataMapList = new ArrayList<>();
						eventData.remove(eventKeyName);
						eventData.put("dataSource_" + reportRequest.getDataSource(), reportRequest.getDataSource());
						dataMapList.add(eventData);
						result1.put(name, dataMapList);
						baseReportMaps.put(date, result1);
					}
				}

			}
		} else if (null != druidGroupByResponseArray && level == 2) {
			String eventKeyName = reportResponseUI.getConfiguration().getProperties().get(0).getId();
			for (DruidGroupByResponse rd : druidGroupByResponseArray) {
				Map<String, Object> eventData = rd.getEvent();
				String date = rd.getTimestamp();
				if (baseReportMaps.isEmpty()) {
					Map<String, List<Map<String, Object>>> result = new HashMap<>();
					String name = String.valueOf(eventData.get(eventKeyName));
					List<Map<String, Object>> dataMapList = new ArrayList<>();
					eventData.remove(eventKeyName);

					Map<String, Object> eventData1 = new HashMap<>();
					for (Map.Entry<String, Object> entry : eventData.entrySet()) {
						String key = entry.getKey().concat(type.toLowerCase());
						Object value = entry.getValue();
						eventData1.put(key, value);
					}
					eventData1.put("dataSource_" + reportRequest.getDataSource(), reportRequest.getDataSource());
					dataMapList.add(eventData1);
					result.put(name, dataMapList);
					baseReportMaps.put(date, result);
				} else {
					Map<String, List<Map<String, Object>>> result = baseReportMaps.get(date);
					String name = String.valueOf(eventData.get(eventKeyName));
					List<Map<String, Object>> dataMapList = result.get(name);
					if (null != dataMapList) {
						eventData.remove(eventKeyName);
						Map<String, Object> eventData1 = new HashMap<>();
						for (Map.Entry<String, Object> entry : eventData.entrySet()) {
							String key = entry.getKey().concat(type.toLowerCase());
							Object value = entry.getValue();
							eventData1.put(key, value);
						}
						eventData1.put("dataSource_" + reportRequest.getDataSource(), reportRequest.getDataSource());
						dataMapList.add(eventData1);
						result.put(name, dataMapList);
						baseReportMaps.put(date, result);
					} else {
						dataMapList = new ArrayList<>();
						eventData.remove(eventKeyName);
						Map<String, Object> eventData1 = new HashMap<>();
						for (Map.Entry<String, Object> entry : eventData.entrySet()) {
							String key = entry.getKey().concat(type.toLowerCase());
							Object value = entry.getValue();
							eventData1.put(key, value);
						}
						eventData1.put("dataSource_" + reportRequest.getDataSource(), reportRequest.getDataSource());
						dataMapList.add(eventData1);
						result.put(name, dataMapList);
						baseReportMaps.put(date, result);
					}
				}

			}

		}

	}

	public void prepareGroupByResponseForRoamingRejection(
			Map<String, Map<String, List<Map<String, Object>>>> baseReportMaps, ReportRequestQuery reportRequest,
			DruidBaseRequestBuilder druidBaseRequestBuilder, ReportResponseUI reportResponseUI, String type)
			throws Exception {

		DruidGroupByResponse[] druidGroupByResponseArray = (DruidGroupByResponse[]) druidRestClient
				.druidResponse(getDruidURI(), reportRequest);

		int level = reportResponseUI.getLevel();
		if (null != druidGroupByResponseArray && (level == 1)) {
			String eventKeyName = reportResponseUI.getConfiguration().getProperties().get(0).getId();
			// String eventKey = "gsm_map_operation_type";
			for (DruidGroupByResponse rd : druidGroupByResponseArray) {
				Map<String, Object> eventData = rd.getEvent();
				String date = rd.getTimestamp();
				// System.out.println("----eventData---"+eventData);

				if (baseReportMaps.isEmpty()) {
					Map<String, List<Map<String, Object>>> result = new HashMap<>();
					String name = String.valueOf(eventData.get(eventKeyName));
					List<Map<String, Object>> dataMapList = new ArrayList<>();
					eventData.remove(eventKeyName);
					dataMapList.add(eventData);
					result.put(name, dataMapList);
					baseReportMaps.put(date, result);
				} else {
					Map<String, List<Map<String, Object>>> result = baseReportMaps.get(date);
					if (result != null) {
						String name = String.valueOf(eventData.get(eventKeyName));
						List<Map<String, Object>> dataMapList = result.get(name);
						if (null != dataMapList) {
							eventData.remove(eventKeyName);
							dataMapList.add(eventData);
							result.put(name, dataMapList);
							baseReportMaps.put(date, result);
						} else {
							dataMapList = new ArrayList<>();
							eventData.remove(eventKeyName);
							dataMapList.add(eventData);
							result.put(name, dataMapList);
							baseReportMaps.put(date, result);
						}
					} else {
						Map<String, List<Map<String, Object>>> result1 = new HashMap<>();
						String name = String.valueOf(eventData.get(eventKeyName));
						List<Map<String, Object>> dataMapList = new ArrayList<>();
						eventData.remove(eventKeyName);
						dataMapList.add(eventData);
						result1.put(name, dataMapList);
						baseReportMaps.put(date, result1);
					}
				}
			}
		} else if (null != druidGroupByResponseArray && level == 2) {
			// System.out.println("-----------Level2-------------"+level);
			String eventKeyName = reportResponseUI.getConfiguration().getProperties().get(0).getId();

			for (DruidGroupByResponse rd : druidGroupByResponseArray) {
				Map<String, Object> eventData = rd.getEvent();
				// System.out.println("---eventData--"+eventData);
				String date = rd.getTimestamp();
				if (baseReportMaps.isEmpty()) {
					Map<String, List<Map<String, Object>>> result = new HashMap<>();
					String name = String.valueOf(eventData.get(eventKeyName));
					List<Map<String, Object>> dataMapList = new ArrayList<>();
					eventData.remove(eventKeyName);
					Map<String, Object> eventData1 = new HashMap<>();

					for (Map.Entry<String, Object> entry : eventData.entrySet()) {
						String key = entry.getKey().concat(type.toLowerCase());
						Object value = entry.getValue();
						eventData1.put(key, value);
					}
					dataMapList.add(eventData1);
					result.put(name, dataMapList);
					baseReportMaps.put(date, result);
				} else {
					Map<String, List<Map<String, Object>>> result = baseReportMaps.get(date);
					String name = String.valueOf(eventData.get(eventKeyName));
					List<Map<String, Object>> dataMapList = result.get(name);
					if (null != dataMapList) {
						eventData.remove(eventKeyName);
						Map<String, Object> eventData1 = new HashMap<>();
						for (Map.Entry<String, Object> entry : eventData.entrySet()) {
							String key = entry.getKey().concat(type.toLowerCase());
							Object value = entry.getValue();
							eventData1.put(key, value);
						}
						dataMapList.add(eventData1);
						result.put(name, dataMapList);
						baseReportMaps.put(date, result);
					} else {
						dataMapList = new ArrayList<>();
						eventData.remove(eventKeyName);
						Map<String, Object> eventData1 = new HashMap<>();
						for (Map.Entry<String, Object> entry : eventData.entrySet()) {
							String key = entry.getKey().concat(type.toLowerCase());
							Object value = entry.getValue();
							eventData1.put(key, value);
						}
						dataMapList.add(eventData1);
						result.put(name, dataMapList);
						baseReportMaps.put(date, result);
					}
				}

			}

		}

	}

	private Map<String, Map<String, Object>> getOperatorTypeCount(
			Map<String, Map<String, List<Map<String, Object>>>> responseMap) {

		Map<String, Map<String, Object>> mapOperator = new HashMap<>();
		Map<String, Map<String, Object>> mapSuccessfullCount = new HashMap<>();
		if (!mapOperator.isEmpty() && !mapSuccessfullCount.isEmpty()) {
			mapOperator.clear();
			mapSuccessfullCount.clear();
		}
		for (Map.Entry<String, Map<String, List<Map<String, Object>>>> result : responseMap.entrySet()) {
			result.getValue().entrySet().forEach(ent -> {
				String operatorName = ent.getKey();
				Map<String, Object> countMap = new HashMap<>();
				ent.getValue().forEach(p -> {
					Object in = p.get("OUTBOUND_count");
					Object out = p.get("INBOUND_count");
					Object inGsm = p.get("OUTBOUND_countgsm");
					Object outGsm = p.get("INBOUND_countgsm");
					p.entrySet().forEach(q -> {
						String key = q.getKey();
						Object value = q.getValue();

						if ((in != null || inGsm != null) && value != null) {
							if ((key.equals("gsm_map_operation_type") || key.equals("gsm_map_operation_typegsm"))
									&& (value.equals("2") || value.equals("23"))) {
								if (countMap.containsKey("in" + key + "_" + value)) {
									int valueCount = Integer
											.parseInt(countMap.get("in" + key + "_" + value).toString());
									if (in != null)
										countMap.put("in" + key + "_" + value,
												valueCount + Integer.parseInt(in.toString()));
									if (inGsm != null)
										countMap.put("in" + key + "_" + value,
												valueCount + Integer.parseInt(inGsm.toString()));
								} else {
									if (in != null)
										countMap.put("in" + key + "_" + value, Integer.parseInt(in.toString()));
									if (inGsm != null)
										countMap.put("in" + key + "_" + value, Integer.parseInt(inGsm.toString()));
								}
								mapOperator.put(operatorName, countMap);
							} else {
							}
						} else if ((out != null || outGsm != null) && value != null) {
							if ((key.equals("gsm_map_operation_type") || key.equals("gsm_map_operation_typegsm"))
									&& (value.equals("2") || value.equals("23"))) {
								if (countMap.containsKey("out" + key + "_" + value)) {
									int valueCount = Integer
											.parseInt(countMap.get("out" + key + "_" + value).toString());
									if (out != null)
										countMap.put("out" + key + "_" + value,
												valueCount + Integer.parseInt(out.toString()));
									if (outGsm != null)
										countMap.put("out" + key + "_" + value,
												valueCount + Integer.parseInt(outGsm.toString()));
								} else {
									if (out != null)
										countMap.put("out" + key + "_" + value, Integer.parseInt(out.toString()));
									if (outGsm != null)
										countMap.put("out" + key + "_" + value, Integer.parseInt(outGsm.toString()));
								}
								mapOperator.put(operatorName, countMap);
							} else {
							}
						}
					});
				});
			});
		}
		return mapOperator;
	}

	private Map<String, Map<String, Object>> getSuccessRate(
			Map<String, Map<String, List<Map<String, Object>>>> responseMap) {

		Map<String, Map<String, Object>> mapSuccessfullCount = new HashMap<>();

		if (!mapSuccessfullCount.isEmpty()) {
			mapSuccessfullCount.clear();
		}
		for (Map.Entry<String, Map<String, List<Map<String, Object>>>> result : responseMap.entrySet()) {
			result.getValue().entrySet().forEach(ent -> {
				String operatorName = ent.getKey();
				Map<String, Object> successMap = new HashMap<>();
				Map<String, Object> data = new HashMap<>();
				if (!successMap.isEmpty()) {
					successMap.clear();
				}

				ent.getValue().forEach(p -> {

					Object out = p.get("OUTBOUND_count");
					Object in = p.get("INBOUND_count");
					Object outGsm = p.get("OUTBOUND_countgsm");
					Object inGsm = p.get("INBOUND_countgsm");
					p.entrySet().forEach(q -> {
						Object count = data.get(q.getKey());
						Object key = q.getKey();
						Object value = q.getValue();

						/*
						 * if(key.equals("OUTBOUND_count") ||
						 * key.equals("INBOUND_count")){ if (null == count) {
						 * Integer record =
						 * Integer.parseInt(q.getValue().toString());
						 * data.put(q.getKey(), record); } else { Integer record
						 * = Integer.parseInt(q.getValue().toString()); Integer
						 * count1 = Integer.parseInt(count.toString());
						 * data.put(q.getKey(), Integer.sum(count1, record)); }
						 * }
						 */

						if (key.equals("gsm_map_error_code") || key.equals("gsm_map_error_codegsm")) {

							if (out != null || outGsm != null) {
								if (value == null) {
									if (successMap.containsKey("outSuccessMsgCount")) {
										int valueCount = Integer
												.parseInt(successMap.get("outSuccessMsgCount").toString());
										if (valueCount > 0) {
											successMap.put("outSuccessMsgCount", ++valueCount);
											mapSuccessfullCount.put(operatorName, successMap);
										}
										// System.out.println("---mapSuccessfullCount---:"+mapSuccessfullCount);
									} else {
										successMap.put("outSuccessMsgCount", 1);
										mapSuccessfullCount.put(operatorName, successMap);
										// System.out.println("---mapSuccessfullCount---:"+mapSuccessfullCount);
									}

								} else {
									if (successMap.containsKey("outErrorMsgCount")) {
										int valueCount = Integer
												.parseInt(successMap.get("outErrorMsgCount").toString());
										if (valueCount > 0) {
											successMap.put("outErrorMsgCount", ++valueCount);
											mapSuccessfullCount.put(operatorName, successMap);
										}
										// System.out.println("---mapSuccessfullCount---:"+mapSuccessfullCount);
									} else {
										successMap.put("outErrorMsgCount", 1);
										mapSuccessfullCount.put(operatorName, successMap);
										// System.out.println("---mapSuccessfullCount---:"+mapSuccessfullCount);
									}

									// System.out.println("---else---:"+value);
								}
							} else if (in != null || inGsm != null) {
								if (value == null) {
									if (successMap.containsKey("inSuccessMsgCount")) {
										int valueCount = Integer
												.parseInt(successMap.get("inSuccessMsgCount").toString());
										if (valueCount > 0) {
											successMap.put("inSuccessMsgCount", ++valueCount);
											mapSuccessfullCount.put(operatorName, successMap);
										}
										// System.out.println("---mapSuccessfullCount---:"+mapSuccessfullCount);
									} else {
										successMap.put("inSuccessMsgCount", 1);
										mapSuccessfullCount.put(operatorName, successMap);
										// System.out.println("---mapSuccessfullCount---:"+mapSuccessfullCount);
									}

								} else {
									if (successMap.containsKey("inErrorMsgCount")) {
										int valueCount = Integer.parseInt(successMap.get("inErrorMsgCount").toString());
										if (valueCount > 0) {
											successMap.put("inErrorMsgCount", ++valueCount);
											mapSuccessfullCount.put(operatorName, successMap);
										}
										// System.out.println("---mapSuccessfullCount---:"+mapSuccessfullCount);
									} else {
										successMap.put("inErrorMsgCount", 1);
										mapSuccessfullCount.put(operatorName, successMap);
										// System.out.println("---mapSuccessfullCount---:"+mapSuccessfullCount);
									}

									// System.out.println("---else---:"+value);
								}
							}
							// mapSuccessfullCount.put(operatorName,
							// successMap);
						}

					});
				});
			});
		}
		return mapSuccessfullCount;
	}

	private List<BaseLeveReport> handleRomarsRejectionResponse(
			Map<String, Map<String, List<Map<String, Object>>>> responseMap, int level) {
		List<BaseLeveReport> reports = new ArrayList<>();
		if (level == 1) {

			Map<String, Map<String, Object>> countMap = getOperatorTypeCount(responseMap);
			Map<String, Map<String, Object>> successCount = getSuccessRate(responseMap);
			// System.out.println("------countMap-------" + countMap);
			for (Map.Entry<String, Map<String, List<Map<String, Object>>>> result : responseMap.entrySet()) {
				String timestamp = result.getKey();
				result.getValue().entrySet().forEach(ent -> {
					String operatorName = ent.getKey();
					BaseLeveReport baseReport = new BaseLeveReport();
					baseReport.setTimeStamp(timestamp);
					baseReport.setOperatorName(ent.getKey());
					baseReport.setProduct("2G/3G");
					Map<String, Object> data = new HashMap<>();
					// Map<String,Object> countMap=new HashMap<>();
					ent.getValue().forEach(p -> {

						Object out = p.get("OUTBOUND_count");
						Object in = p.get("INBOUND_count");
						// System.out.println("out: "+out+"/ in :"+in);
						p.entrySet().forEach(q -> {
							Object count = data.get(q.getKey());
							String key = q.getKey();
							Object value = q.getValue();
							// System.out.println("key: "+key+"/ value
							// :"+value);

							if (key.equals("gsm_map_operation_type") && value != null
									&& (value.equals("2") || value.equals("23"))) {
								if (countMap.containsKey(operatorName)) {
									Map<String, Object> operatorMapCount = countMap.get(operatorName);
									// System.out.println("---operatorMapCount---
									// :"+operatorMapCount);
									Object inOperatorCount = operatorMapCount.get("in" + key + "_" + value);
									Object outOperatorCount = operatorMapCount.get("out" + key + "_" + value);

									data.put("in" + key + "_" + value, inOperatorCount);
									data.put("out" + key + "_" + value, outOperatorCount);
									// System.out.println("---data--00-"+data);
								} else {
									// System.out.println("---else--11-");
									// data.put(key.toString()+"_"+value, 0);
								}
							}

							if (in != null && value != null) {

								if (key.equals("INBOUND_count")) {
									if (null == count) {
										Integer record = Integer.parseInt(q.getValue().toString());
										data.put(q.getKey(), record);
									} else {
										Integer record = Integer.parseInt(q.getValue().toString());
										Integer count1 = Integer.parseInt(count.toString());
										data.put(q.getKey(), Integer.sum(count1, record));
									}
								} else if (key.equals("gsm_map_error_code")) {
									if (successCount.containsKey(ent.getKey())) {
										Map<String, Object> successMapCount = successCount.get(ent.getKey());
										Object succCount = successMapCount.get("inSuccessMsgCount");
										Object errCount = successMapCount.get("inErrorMsgCount");
										int rate = 0;
										int totalSuccessCount = 0;
										int totalErrorCount = 0;
										if (succCount != null) {
											totalSuccessCount = Integer.parseInt(succCount.toString());
										} else {
											totalSuccessCount = 0;
										}
										if (errCount != null) {
											totalErrorCount = Integer.parseInt(errCount.toString());
										} else {
											totalErrorCount = 0;
										}
										rate = (totalSuccessCount * 100) / (totalSuccessCount + totalErrorCount);
										data.put("inSuccessRate", rate);
										data.put("inSuccessMsgCount", succCount);
										// System.out.println("---if
										// data---1"+data);
									} else {
										data.put("inSuccessRate", 0);
										data.put("inSuccessMsgCount", 0);
										// System.out.println("--else
										// -data---12"+data);
									}
								} else {
									// System.out.println("---else--end-");
								}
							} else if (out != null && value != null) {

								/*
								 * if(key.equals("gsm_map_operation_type") &&
								 * (value.equals("2") || value.equals("23"))){
								 * if(countMap.containsKey(operatorName)){
								 * Map<String, Object>
								 * operatorMapCount=countMap.get(operatorName);
								 * //
								 * System.out.println("---operatorMapCount--- :"
								 * +operatorMapCount); Object
								 * operatorCount=operatorMapCount.get("out"+key+
								 * "_"+value); data.put("out"+key+"_"+value,
								 * operatorCount); //
								 * System.out.println("---data--00-"+data);
								 * }else{ // System.out.println("---else--11-");
								 * //data.put(key.toString()+"_"+value, 0); } }
								 */
								if (key.equals("OUTBOUND_count")) {
									if (null == count) {
										Integer record = Integer.parseInt(q.getValue().toString());
										data.put(q.getKey(), record);
									} else {
										Integer record = Integer.parseInt(q.getValue().toString());
										Integer count1 = Integer.parseInt(count.toString());
										data.put(q.getKey(), Integer.sum(count1, record));
									}
								} else if (key.equals("gsm_map_error_code")) {
									if (successCount.containsKey(ent.getKey())) {
										Map<String, Object> successMapCount = successCount.get(ent.getKey());
										Object succCount = successMapCount.get("outSuccessMsgCount");
										Object errCount = successMapCount.get("outErrorMsgCount");
										int rate = 0;
										int totalSuccessCount = 0;
										int totalErrorCount = 0;
										if (succCount != null) {
											totalSuccessCount = Integer.parseInt(succCount.toString());
										} else {
											totalSuccessCount = 0;
										}
										if (errCount != null) {
											totalErrorCount = Integer.parseInt(errCount.toString());
										} else {
											totalErrorCount = 0;
										}
										rate = (totalSuccessCount * 100) / (totalSuccessCount + totalErrorCount);
										data.put("outSuccessRate", rate);
										data.put("outSuccessMsgCount", succCount);
										// System.out.println("---if
										// data---1"+data);
									} else {
										data.put("outSuccessRate", 0);
										data.put("outSuccessMsgCount", 0);
										// System.out.println("--else
										// -data---12"+data);
									}
								} else {
									// System.out.println("---else--end-");
								}

							} else {
								// System.out.println("---else--end-");
							}
						});
					});

					baseReport.setData(data);
					reports.add(baseReport);
				});
			}
		} else if (level == 2) {

			Map<String, Map<String, Object>> countMap = getOperatorTypeCount(responseMap);
			Map<String, Map<String, Object>> successCount = getSuccessRate(responseMap);
			// System.out.println("------countMap----" + level + "----" +
			// countMap);
			for (Map.Entry<String, Map<String, List<Map<String, Object>>>> result : responseMap.entrySet()) {
				String timestamp = result.getKey();
				result.getValue().entrySet().forEach(ent -> {
					String operatorName = ent.getKey();
					BaseLeveReport baseReport = new BaseLeveReport();
					baseReport.setTimeStamp(timestamp);
					baseReport.setOperatorName(ent.getKey());
					baseReport.setProduct("2G/3G");
					Map<String, Object> data = new HashMap<>();
					// Map<String,Object> countMap=new HashMap<>();
					ent.getValue().forEach(p -> {

						Object out = p.get("OUTBOUND_countgsm");
						Object in = p.get("INBOUND_countgsm");
						// Object operator=p.get("gsm_map_operation_type");
						// System.out.println("out: " + out + "/ in :" + in);
						p.entrySet().forEach(q -> {
							Object count = data.get(q.getKey());
							String key = q.getKey();
							Object value = q.getValue();
							// System.out.println("key: "+key+"/ value
							// :"+value);

							if (key.equals("gsm_map_operation_typegsm") && value != null
									&& (value.equals("2") || value.equals("23"))) {
								if (countMap.containsKey(operatorName)) {
									Map<String, Object> operatorMapCount = countMap.get(operatorName);
									// System.out.println("---operatorMapCount---
									// :"+operatorMapCount);
									Object inOperatorCount = operatorMapCount.get("in" + key + "_" + value);
									Object outOperatorCount = operatorMapCount.get("out" + key + "_" + value);

									data.put("in" + key + "_" + value, inOperatorCount);
									data.put("out" + key + "_" + value, outOperatorCount);
									// System.out.println("---data--00-"+data);
								} else {
									// System.out.println("---else--11-");
								}
							}

							if (in != null && value != null) {

								/*
								 * if(key.equals("gsm_map_operation_typegsm") &&
								 * (value.equals("2") || value.equals("23"))){
								 * if(countMap.containsKey(operatorName)){
								 * Map<String, Object>
								 * operatorMapCount=countMap.get(operatorName);
								 * //
								 * System.out.println("---operatorMapCount--- :"
								 * +operatorMapCount); Object
								 * operatorCount=operatorMapCount.get("in"+key+
								 * "_"+value); data.put("in"+key+"_"+value,
								 * operatorCount);
								 * System.out.println("---data--00-"+data);
								 * }else{ // System.out.println("---else--11-");
								 * } }
								 */
								if (key.equals("INBOUND_countgsm")) {
									if (null == count) {
										Integer record = Integer.parseInt(q.getValue().toString());
										data.put(q.getKey(), record);
									} else {
										Integer record = Integer.parseInt(q.getValue().toString());
										Integer count1 = Integer.parseInt(count.toString());
										data.put(q.getKey(), Integer.sum(count1, record));
									}
								} else if (key.equals("gsm_map_error_codegsm")) {
									if (successCount.containsKey(ent.getKey())) {
										Map<String, Object> successMapCount = successCount.get(ent.getKey());
										Object succCount = successMapCount.get("inSuccessMsgCount");
										Object errCount = successMapCount.get("inErrorMsgCount");
										int rate = 0;
										int totalSuccessCount = 0;
										int totalErrorCount = 0;
										if (succCount != null) {
											totalSuccessCount = Integer.parseInt(succCount.toString());
										} else {
											totalSuccessCount = 0;
										}
										if (errCount != null) {
											totalErrorCount = Integer.parseInt(errCount.toString());
										} else {
											totalErrorCount = 0;
										}
										rate = (totalSuccessCount * 100) / (totalSuccessCount + totalErrorCount);
										data.put("inSuccessRate", rate);
										data.put("inSuccessMsgCount", succCount);
										// System.out.println("---if
										// data---1"+data);
									} else {
										data.put("inSuccessRate", 0);
										data.put("inSuccessMsgCount", 0);
										// System.out.println("--else
										// -data---12"+data);
									}
								} else {
									// System.out.println("---else--end-");
								}
							} else if (out != null) {

								/*
								 * if(key.equals("gsm_map_operation_typegsm") &&
								 * (value.equals("2") || value.equals("23"))){
								 * if(countMap.containsKey(operatorName)){
								 * Map<String, Object>
								 * operatorMapCount=countMap.get(operatorName);
								 * //
								 * System.out.println("---operatorMapCount--- :"
								 * +operatorMapCount); Object
								 * operatorCount=operatorMapCount.get("out"+key+
								 * "_"+value); data.put("out"+key+"_"+value,
								 * operatorCount);
								 * System.out.println("---data--11-"+data);
								 * }else{ // System.out.println("---else--11-");
								 * } }
								 */
								if (key.equals("OUTBOUND_countgsm")) {
									if (null == count) {
										Integer record = Integer.parseInt(q.getValue().toString());
										data.put(q.getKey(), record);
									} else {
										Integer record = Integer.parseInt(q.getValue().toString());
										Integer count1 = Integer.parseInt(count.toString());
										data.put(q.getKey(), Integer.sum(count1, record));
									}
								} else if (key.equals("gsm_map_error_codegsm")) {
									if (successCount.containsKey(ent.getKey())) {
										Map<String, Object> successMapCount = successCount.get(ent.getKey());
										Object succCount = successMapCount.get("outSuccessMsgCount");
										Object errCount = successMapCount.get("outErrorMsgCount");
										int rate = 0;
										int totalSuccessCount = 0;
										int totalErrorCount = 0;
										if (succCount != null) {
											totalSuccessCount = Integer.parseInt(succCount.toString());
										} else {
											totalSuccessCount = 0;
										}
										if (errCount != null) {
											totalErrorCount = Integer.parseInt(errCount.toString());
										} else {
											totalErrorCount = 0;
										}
										rate = (totalSuccessCount * 100) / (totalSuccessCount + totalErrorCount);
										data.put("outSuccessRate", rate);
										data.put("outSuccessMsgCount", succCount);
										// System.out.println("---if
										// data---1"+data);
									} else {
										data.put("outSuccessRate", 0);
										data.put("outSuccessMsgCount", 0);
										// System.out.println("--else
										// -data---12"+data);
									}
								} else {
									// System.out.println("---else--end-");
								}

							} else {
								// System.out.println("---else--end-");
							}

						});
					});

					baseReport.setData(data);
					reports.add(baseReport);
				});
			}

		}
		return reports;
	}

	private List<BaseLeveReport> filterTop10(List<BaseLeveReport> reports) {
		Collections.sort(reports, new BaseLeveReportComparatpr());
		List<BaseLeveReport> top10Reports = new ArrayList<>(10);
		if (reports.size() > 10) {
			for (int i = 0; i < 10; i++) {
				top10Reports.add(reports.get(i));
			}
		} else {
			for (int i = 0; i < reports.size(); i++) {
				top10Reports.add(reports.get(i));
			}
		}
		return top10Reports;
	}
}
