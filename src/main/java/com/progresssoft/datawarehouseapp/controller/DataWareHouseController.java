package com.progresssoft.datawarehouseapp.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.progresssoft.datawarehouseapp.beans.FileReportBean;
import com.progresssoft.datawarehouseapp.beans.FxAccumulativeCountBean;
import com.progresssoft.datawarehouseapp.service.DataWareHouseService;


@Controller
public class DataWareHouseController {

	private static final Logger LOGGER = LoggerFactory.getLogger(DataWareHouseController.class);
	
	@Autowired
	private DataWareHouseService dataWareHouseService;
	
	@RequestMapping(value={"/","/file-upload"},method= {RequestMethod.GET,RequestMethod.POST})
	public String loadHomePage(ModelMap model) throws Exception{
		
		LOGGER.debug("loadHomePage Started");
		model.addAttribute("username", "vikas");
		LOGGER.debug("loadHomePage Ended");
		return "file-upload";
	}
	
	
	@RequestMapping(value="/save",method= RequestMethod.POST)
	public String save(@RequestParam MultipartFile file,ModelMap model)throws Exception{
		
		LOGGER.debug("Save Started");
		dataWareHouseService.validateFile(file, model);
		LOGGER.debug("Save Ended");
		return "forward:/file-upload";
	}
	
	
	@RequestMapping(value="/summary-report",method= RequestMethod.GET)
	public String viewSummaryReport(@ModelAttribute("fileReportBean") FileReportBean fileReportBean,ModelMap model)throws Exception{
		
		LOGGER.debug("viewSummaryReport Started");
		List<FileReportBean> fileReportBeans =  dataWareHouseService.searchSummaryReports(fileReportBean);
		model.addAttribute("fileReportBeans", fileReportBeans);
		LOGGER.debug("viewSummaryReport Ended");
		return "summary-report";
	}
	
	@RequestMapping(value="/accumulative-reports",method= RequestMethod.GET)
	public String viewAccumulativeReports(ModelMap model)throws Exception{
		
		LOGGER.debug("viewAccumulativeReports Started");
		List<FxAccumulativeCountBean> fxAccumulativeCountBeans =  dataWareHouseService.searchAccumulativeDeals();
		model.addAttribute("fxAccumulativeCountBeans", fxAccumulativeCountBeans);
		LOGGER.debug("viewAccumulativeReports Ended");
		return "accumulative-reports";
	}
	
}
