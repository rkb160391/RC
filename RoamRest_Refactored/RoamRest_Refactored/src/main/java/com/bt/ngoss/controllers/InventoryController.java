package com.bt.ngoss.controllers;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.bt.ngoss.service.inventory.RoamInventoryService;

@EnableAspectJAutoProxy(proxyTargetClass = true)
@RequestMapping(value = "/api/inventory")
@RestController
public class InventoryController {
	
	private static final Logger logger = Logger.getLogger(InventoryController.class);
	
	@Autowired
	private RoamInventoryService roamInventoryService;

	
	@RequestMapping(value = "/upload", method = RequestMethod.POST)
	public @ResponseBody String uploadMultipleInventoryFileHandler(@RequestParam("uploadFile") MultipartFile file) {
	
		MultipartFile [] arrayMultiPart=new MultipartFile[2];
		String[] names=new String[2];
		 System.out.println("-----------Upload File-----------");
		if(!file.isEmpty()){
            System.out.println("accepted: "+file.getOriginalFilename());
            File convFile = new File( file.getOriginalFilename());
            MultipartFile multipartFile=null;
            try {
                file.transferTo(convFile);
                multipartFile=(MultipartFile)file;
                arrayMultiPart[0]=multipartFile;
                names[0]=file.getName();
                System.out.println("Files: "+arrayMultiPart[0]);
                System.out.println("name: "+names[0]);
                //ao.setLien_alfresco_cr(convFile.getAbsolutePath());
               // service.addAppelOffre(ao);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
		return roamInventoryService.processRoamConnectInventory(names, arrayMultiPart);
	}

}
