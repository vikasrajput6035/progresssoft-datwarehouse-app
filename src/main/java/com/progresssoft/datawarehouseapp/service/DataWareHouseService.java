package com.progresssoft.datawarehouseapp.service;

import static com.progresssoft.datawarehouseapp.validators.DateTimeUtil.FORMATTER;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.ModelMap;
import org.springframework.web.multipart.MultipartFile;

import com.progresssoft.datawarehouseapp.beans.FileReportBean;
import com.progresssoft.datawarehouseapp.beans.FxAccumulativeCountBean;
import com.progresssoft.datawarehouseapp.beans.FxDealsBean;
import com.progresssoft.datawarehouseapp.beans.FxInvalidDealsBean;
import com.progresssoft.datawarehouseapp.controller.DataWareHouseController;
import com.progresssoft.datawarehouseapp.dao.DataWareHouseDao;
import com.progresssoft.datawarehouseapp.validators.FileValidator;
import com.progresssoft.datawarehouseapp.validators.FxDealsValidator;

import au.com.bytecode.opencsv.CSVReader;

@Service
public class DataWareHouseService {

	private static final Logger LOGGER = LoggerFactory.getLogger(DataWareHouseService.class);
	
	@Autowired
	private DataWareHouseDao dataWareHouseDao; 
		
	@Autowired
	private FxDealsValidator fxDealsValidator; 

	@Autowired
	private FileValidator fileValidator; 

	public List<FileReportBean> searchSummaryReports(FileReportBean fileReportBean) throws Exception{
		
		LOGGER.debug("searchSummaryReports Start");
		List<FileReportBean> fileReportBeans =  dataWareHouseDao.searchSummaryReport(fileReportBean);
		LOGGER.debug("searchSummaryReports End");
		return fileReportBeans;
	}
	
	public ModelMap validateFile(MultipartFile file , ModelMap modelMap) throws Exception{
		
		LOGGER.debug("validateFile Start");
		Timestamp startTimeStamp = null;
    	Timestamp endTimeStamp = null;
    	FileReportBean fileReportBean = null;
		List<FxDealsBean> fxDealsBeans = new ArrayList<FxDealsBean>();
		List<FxInvalidDealsBean> fxInvalidDealsBeans = new ArrayList<FxInvalidDealsBean>();
	    InputStream is = null;	 
	    String[] nextLine;
	    CSVReader reader = null;
    	Map<String,Integer> accuCountNewMap = new HashMap<String, Integer>();
	    
    	startTimeStamp = new Timestamp(System.currentTimeMillis());
		
    	if ( fileValidator.isFileEmpty(file) ) {
	        modelMap.addAttribute("message","Upload Failed. Empty File Found");
	        return modelMap;
	    }
		
		if( !fileValidator.isFileFormatValid(file)){
			modelMap.addAttribute("message","Upload Failed. Invalid File Found. Please Upload .csv File.");
			return modelMap;
		}
		
		if( fileValidator.isFileDuplicate(file)){
			modelMap.addAttribute("message","Upload Failed. Duplicate File Found");
			return modelMap;
		}
		
    	is = file.getInputStream();
    	InputStreamReader isreader = new InputStreamReader(is);
    	reader = new CSVReader(isreader, ',', '\'', 1); 
    	FxDealsBean fxDealsBean;
    	FxInvalidDealsBean fxInvalidDealsBean;
    	
    	int validCount = 0;
    	int inValidCount = 0;
    	
    	while ((nextLine = reader.readNext()) != null) {
            
            	if(fxDealsValidator.isValid(nextLine)){
            		fxDealsBean = fillFxDealBean(nextLine);
                	fxDealsBeans.add(fxDealsBean);
                	validCount++;
                	
                	if( null != accuCountNewMap.get(fxDealsBean.getFromCurrencyISOCode())){
                		int count = accuCountNewMap.get(fxDealsBean.getFromCurrencyISOCode())+1;
                		accuCountNewMap.put(fxDealsBean.getFromCurrencyISOCode(),count);
                	}else{
                		accuCountNewMap.put(fxDealsBean.getFromCurrencyISOCode(),1);
                	}
                	
            	}else{
            		
            		fxInvalidDealsBean = fillFxInvalidDealBean(nextLine);
                	fxInvalidDealsBeans.add(fxInvalidDealsBean);
                	inValidCount++;
            	}
        }
    	
    	dataWareHouseDao.saveValidFxRecords(fxDealsBeans);
    	dataWareHouseDao.saveInvalidForexRecords(fxInvalidDealsBeans);
    	dataWareHouseDao.persistAccumulativeCount(accuCountNewMap);
    	
    	fileReportBean = new FileReportBean();
    	fileReportBean.setNoOfValidRecords(validCount);
    	fileReportBean.setNoOfInValidRecords(inValidCount);
    	fileReportBean.setCreateddate(new Timestamp(System.currentTimeMillis()));
    	fileReportBean.setProcessStartTime(startTimeStamp);
    	fileReportBean.setProcessEndTime(new Timestamp(System.currentTimeMillis()));
    	fileReportBean.setFileName(file.getOriginalFilename());
    	
    	dataWareHouseDao.persist(fileReportBean);
    	modelMap.addAttribute("successMessage","File Uploaded successfully");
    	LOGGER.info("{} File Imported successfully within {} milliseconds ",fileReportBean.getFileName(),(fileReportBean.getProcessEndTime().getTime()-fileReportBean.getProcessStartTime().getTime()));
    	LOGGER.info("{} File had {} Valid Deals and {} Invalid Deals ",fileReportBean.getFileName(),fileReportBean.getNoOfInValidRecords(),fileReportBean.getNoOfInValidRecords());
        LOGGER.debug("validateFile End");	
		return modelMap;
	}
	
	
	public List<FxAccumulativeCountBean> searchAccumulativeDeals() throws Exception{
		
		LOGGER.debug("searchAccumulativeDeals Start");
		List<FxAccumulativeCountBean> fxAccumulativeCountBeans =  dataWareHouseDao.getAccumulativeDeals();
		LOGGER.debug("searchAccumulativeDeals End");
		return fxAccumulativeCountBeans;
	}
	
	public FxDealsBean fillFxDealBean(String[] nextLine) throws Exception{
		
		//LOGGER.debug("fillFxDealBean Start");
		FxDealsBean fxDealsBean = new FxDealsBean();
		fxDealsBean.setDealUniqueID(Long.parseLong(nextLine[0]));
    	fxDealsBean.setFromCurrencyISOCode(nextLine[1]);
    	fxDealsBean.setToCurrencyISOCode(nextLine[2]);
    	fxDealsBean.setDealTimeStamp(Timestamp.valueOf(LocalDateTime.parse(nextLine[3], FORMATTER)));
    	fxDealsBean.setDealAmount(Double.parseDouble(nextLine[4]));
    	//LOGGER.debug("fillFxDealBean End");
    	return fxDealsBean;
	}
	
	public FxInvalidDealsBean fillFxInvalidDealBean(String[] nextLine) throws Exception{
		
		//LOGGER.debug("fillFxInvalidDealBean Start");
		FxInvalidDealsBean fxInvalidDealsBean = new FxInvalidDealsBean();
		
		try{
			fxInvalidDealsBean.setDealUniqueID(blankWhenNull(nextLine[0]));
		}catch(ArrayIndexOutOfBoundsException e){
			fxInvalidDealsBean.setDealUniqueID(null);
		}
		
		try{
			fxInvalidDealsBean.setFromCurrencyISOCode(blankWhenNull(nextLine[1]));
		}catch(ArrayIndexOutOfBoundsException e){
			fxInvalidDealsBean.setFromCurrencyISOCode(null);
		}
		
		try{
			fxInvalidDealsBean.setToCurrencyISOCode(blankWhenNull(nextLine[2]));
		}catch(ArrayIndexOutOfBoundsException e){
			fxInvalidDealsBean.setToCurrencyISOCode(null);
		}
		
		try{
			fxInvalidDealsBean.setDealTimeStamp(blankWhenNull(nextLine[3]));
		}catch(ArrayIndexOutOfBoundsException e){
			fxInvalidDealsBean.setDealTimeStamp(null);
		}
		
		try{
			fxInvalidDealsBean.setDealAmount(blankWhenNull(nextLine[4]));
		}catch(ArrayIndexOutOfBoundsException e){
			fxInvalidDealsBean.setDealAmount(null);
		}
		//LOGGER.debug("fillFxInvalidDealBean End");
    	return fxInvalidDealsBean;
	}
	
	
	public String blankWhenNull(String value) throws Exception{
		
		//LOGGER.debug("blankWhenNull Start");
		if(StringUtils.isEmpty(value)){
			//LOGGER.debug("blankWhenNull End with Empty value");
			return null;
		}else{
			//LOGGER.debug("blankWhenNull End with Non-Empty value");
			return value;
		}
	}
}
