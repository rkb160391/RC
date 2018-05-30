package com.bt.ngoss.service.inventory;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.bt.ngoss.dao.RoamInventoryDao;
import com.bt.ngoss.model.RoamInventory;

@Service
@Transactional
public class RoamInventoryServiceImpl implements RoamInventoryService {

	private static final Logger logger = Logger.getLogger(RoamInventoryServiceImpl.class);

	@Autowired
	private Environment env;

	@Autowired
	private RoamInventoryDao roamInventoryDao;

	
	@Override
	public String processRoamConnectInventory(String[] names, MultipartFile[] files) {

		for (int i = 0; i < files.length; i++) {
			MultipartFile file = files[i];
			String name = names[i];
			try {
				byte[] bytes = file.getBytes();
				// Creating the directory to store file ,read loaction from properties file
				String rootPath = env.getProperty("restAPI.inventoryPath");
				System.out.println("RoothPath is:"+rootPath);
				File dir = new File(rootPath + File.separator + "inventoryFiles");
				System.out.println(dir);
				if (!dir.exists()) {
					dir.mkdirs();
				}
				// Create the file on server
				File serverFile = new File(dir.getAbsolutePath() + File.separator + name);
				BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(serverFile));
				stream.write(bytes);
				stream.close();
				logger.info("Server File Location=" + serverFile.getAbsolutePath());
				scheduleInventoryProcessingAfterOneHr(serverFile.getAbsolutePath());
			} catch (Exception e) {
				logger.error("error reading the input file");
			}
		}
		return "success";
	}

	private void scheduleInventoryProcessingAfterOneHr(String absolutePath) {
		ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();
		Runnable task = new Runnable() {
			public void run() {
				// Row vaues will be in sequenc operatorName$operatorId$globalTitle
				List<String> inventedRows = readInventoryFile(absolutePath);
				logger.debug("sending inventory rows to kafka topic");
				CreateKafkaProducer.sendKafkaMessage(inventedRows);
			}
		};
		scheduler.schedule(task, Integer.parseInt(env.getProperty("restAPI.inventoryDelay")), TimeUnit.MILLISECONDS);
		scheduler.shutdown();
	}

	
	@Transactional
	private List<String> readInventoryFile(String absolutePath) {

		logger.debug(
				"###################Inside readInventory scheduled after 1 hour from file upload###################");
		String csvFile = absolutePath;
		BufferedReader br = null;
		String line = "";
		String cvsSplitBy = ",";
		List<String> inventedRows = new ArrayList<String>();
		try {

			br = new BufferedReader(new FileReader(csvFile));
			while ((line = br.readLine()) != null) {

				// use comma as separator
				if (line.split('"' + cvsSplitBy).length > 1) {
					String operatorName = line.split('"' + cvsSplitBy)[0].replaceAll("^\"|\"$", "");
					String operatorCode = line.split('"' + cvsSplitBy)[1].split(cvsSplitBy)[0];
					String globalTitle = line.split('"' + cvsSplitBy)[1].split(cvsSplitBy)[1];
					boolean isUpdateSaveSuccess = AddInventoryToMetaDeta(operatorName, operatorCode, globalTitle);

					if (isUpdateSaveSuccess) {
						logger.debug("successfully inserted/updated row for global title:" + globalTitle);
						inventedRows.add(operatorName + "##" + operatorCode + "##" + globalTitle);
					}else {
						logger.error("something went wrong in updation");
					}

				}

			}

		} catch (FileNotFoundException e) {
			logger.error("inventory file not found please check the path");
		} catch (IOException e) {
			logger.error("inventory file parsing/reading error");
		} finally {
			if (br != null) {
				try {
					br.close();
				} catch (IOException e) {
					logger.error("Error closing the inventory file");
				}
			}
		}
		logger.debug("################# readInventoryFile ended ###############################");
		return inventedRows;
	}

	private boolean AddInventoryToMetaDeta(String currentRowString, String operatorCode, String globalTitle) {
		logger.debug("################# inside  AddInventoryToMetaDeta ###############################");
		RoamInventory inventory = new RoamInventory(globalTitle, operatorCode, currentRowString);
		return roamInventoryDao.saveOrUpdateInventory(inventory);
	}

}
