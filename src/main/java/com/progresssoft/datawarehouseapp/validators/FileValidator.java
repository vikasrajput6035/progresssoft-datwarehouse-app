package com.progresssoft.datawarehouseapp.validators;


import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;

import static com.progresssoft.datawarehouseapp.validators.DateTimeUtil.FORMATTER;

import com.progresssoft.datawarehouseapp.dao.DataWareHouseDao;
import com.progresssoft.datawarehouseapp.validators.CurrencyCodeValidator;

@Component("fileValidator")
public class FileValidator {
	
	public static final String CSV_FILE_CONTENT_TYPE = "text/csv";
	
	@Autowired
	private DataWareHouseDao dataWareHouseDao;
	
	// Checks whether the file is empty or not    
    public boolean isFileEmpty(MultipartFile file){
    	if(file.isEmpty()){
        	return true;
        }else{
        	return false;
        }
    }
    
    // Checks whether content type of file is CSV or not
    public boolean isFileFormatValid(MultipartFile file){
    	if( "csv".equalsIgnoreCase(FilenameUtils.getExtension(file.getOriginalFilename())) ){
        	return true;
        }else{
        	return false;
        }
    }
    
    // Checks whether File is already uploaded or not
    public boolean isFileDuplicate(MultipartFile file) throws Exception{
    	return dataWareHouseDao.isDuplicateFileUploaded(file.getOriginalFilename());
    }
}