package com.progresssoft.datawarehouseapp.service;

import static org.junit.Assert.*;

import java.util.List;

import javax.transaction.Transactional;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.ui.ModelMap;
import org.springframework.web.multipart.MultipartFile;

import com.progresssoft.datawarehouseapp.beans.FileReportBean;
import com.progresssoft.datawarehouseapp.junit.TestUtils;
import static org.mockito.Mockito.mock;

@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
@TransactionConfiguration(transactionManager="myTxManager",defaultRollback=true)
@ContextConfiguration(locations={"file:src/main/webapp/WEB-INF/datawarehouseapp-dispatcher-servlet.xml"})
@WebAppConfiguration
public class DataWareHouseServiceTest {

	@Autowired
	private TestUtils testUtils;
	
	@Autowired
	private DataWareHouseService dataWareHouseService;	
	
	
	private ModelMap modelMap = mock(ModelMap.class);
	
	
	@Before
	public void setUp(){
		
	}
	
	/*
	 * Checks whether the records are imported within 5 seconds.
	 * File contains 100K records having valid & invalid fx deals.
	 * Here we've passed valid csv file.
	 * 
	 * */
	@Test(timeout=5000)
	public void testValidateFile_WhenCSVFileWithValid100kRecords() throws Exception{
	
		MultipartFile result = testUtils.getMultiPartFile(testUtils.fileWith100KValidRecords);
		dataWareHouseService.validateFile(result,modelMap);
	}

	@Test
	public void testValidateFile_WhenMixRecordsGiven() throws Exception{
	
		MultipartFile result = testUtils.getMultiPartFile(testUtils.fileWith100KMixRecords);
		dataWareHouseService.validateFile(result,modelMap);
	}
	
	@Test(timeout=5000)
	public void testValidateFile_WhenCSVFileWithInValid100kRecords() throws Exception{
	
		MultipartFile result = testUtils.getMultiPartFile(testUtils.fileWith100KInValidRecords);
		dataWareHouseService.validateFile(result,modelMap);
	}
	
	@Test
	public void testSearchDeals_WhenInvalidFileName() throws Exception{
		FileReportBean fileReportBean = new FileReportBean();
		fileReportBean.setFileName("Test.csv");
		List<FileReportBean> fileReportBeans = dataWareHouseService.searchSummaryReports(fileReportBean);
		
		assertTrue(fileReportBeans.isEmpty());
	}

	@Test
	public void testSearchDeals_WhenValidFileName() throws Exception{
		FileReportBean fileReportBean = new FileReportBean();
		fileReportBean.setFileName("ValidCSV11.csv");
		List<FileReportBean> fileReportBeans = dataWareHouseService.searchSummaryReports(fileReportBean);
		
		assertFalse(fileReportBeans.isEmpty());
	}
	
}