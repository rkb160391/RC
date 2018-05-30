package com.bt.ngoss.service.inventory;

import org.springframework.web.multipart.MultipartFile;

public interface RoamInventoryService {
	
	String processRoamConnectInventory(String[] names,MultipartFile[] files);

}
