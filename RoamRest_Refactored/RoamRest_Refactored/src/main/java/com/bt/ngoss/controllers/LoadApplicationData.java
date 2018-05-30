package com.bt.ngoss.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.core.task.SimpleAsyncTaskExecutor;
import org.springframework.stereotype.Component;

import com.bt.ngoss.model.KeyIdentifier;
import com.bt.ngoss.model.UserDetails;
import com.bt.ngoss.model.common.SiteOption;
import com.bt.ngoss.service.KeyIdentifierService;
import com.bt.ngoss.service.SiteOptionService;
import com.bt.ngoss.service.inventory.PropertyCacheService;
import com.bt.ngoss.service.security.LoginService;
import com.bt.ngoss.util.UserFactory;
import com.bt.ngoss.vo.ReportPropertyVO;
import com.bt.ngoss.vo.UserVO;

@EnableAspectJAutoProxy(proxyTargetClass = true)
@Component
public class LoadApplicationData {

	SimpleAsyncTaskExecutor asyncTaskExecutor = new SimpleAsyncTaskExecutor();
	public static HashMap<String, UserVO> userMap = new HashMap<String, UserVO>();
	public static HashMap<String, String> siteOptionMap = new HashMap<String, String>();
	public static Map<String, String> attributesMap = new HashMap<String, String>();
	public static Map<String, KeyIdentifier> operatorsMap = new HashMap<String, KeyIdentifier>();
	public static Map<String, ReportPropertyVO> reportProperty = new HashMap<String, ReportPropertyVO>();

	@Autowired
	LoginService loginService;

	@Autowired
	SiteOptionService siteOptionService;

	@Autowired
	KeyIdentifierService keyIdentifierService;

	@Autowired
	PropertyCacheService propertyCacheService;

	@PostConstruct
	public void loadUsers() throws Exception {
		// final LoadDataAsync loadDataAsync = new LoadDataAsync(siteOptionMap,
		// attributesMap, operatorsMap,
		// reportProperty);
		// loadDataAsync.setLoginService(loginService);
		// loadDataAsync.setKeyIdentifierService(keyIdentifierService);
		// loadDataAsync.setPropertyCacheService(propertyCacheService);
		// loadDataAsync.setSiteOptionService(siteOptionService);
		// if(userMap.isEmpty()){
		List<UserDetails> userDetails = loginService.loadUsers();
		userDetails.forEach((usr) -> {
			UserVO uservo = UserFactory.create(usr);
			userMap.put(uservo.getId(), uservo);
			
		});

		// asyncTaskExecutor.execute(loadDataAsync);

		if (siteOptionMap.isEmpty()) {
			List<SiteOption> siteOptionData = siteOptionService.loadSiteOption();
			siteOptionData.forEach((siteData) -> {
				// SiteOptionVo siteOptionVo =
				// SiteOptionFactory.create(siteData);
				siteOptionMap.put(siteData.getDimension(), siteData.getValue());
			});
		}
		if (operatorsMap.isEmpty()) {
			List<KeyIdentifier> KeyIdentifiers = keyIdentifierService.getKeyIdentifiers();
			KeyIdentifiers.forEach((keyidentifer) -> {
				operatorsMap.put(keyidentifer.getType(), keyidentifer);
			});
		}

	}

}
