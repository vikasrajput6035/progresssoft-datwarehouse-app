package com.progresssoft.datawarehouseapp.dao;

import static org.junit.Assert.*;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.test.context.web.WebAppConfiguration;

import com.progresssoft.datawarehouseapp.beans.FileReportBean;
import com.progresssoft.datawarehouseapp.beans.FxDealsBean;
import com.progresssoft.datawarehouseapp.beans.FxInvalidDealsBean;

@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
@TransactionConfiguration(transactionManager="myTxManager",defaultRollback=true)
@ContextConfiguration(locations={"file:src/main/webapp/WEB-INF/datawarehouseapp-dispatcher-servlet.xml"})
@WebAppConfiguration
public class DataWareHouseDaoTest {


	@Autowired
	private DataWareHouseDao dataWareHouseDao;
	
	@Test
	public void persistTest() throws Exception {
		
		FileReportBean fileReportBean = new FileReportBean();
		fileReportBean.setFileName("SampleCsv.csv");
		fileReportBean.setProcessEndTime(new Timestamp(System.currentTimeMillis()));
		fileReportBean.setProcessEndTime(new Timestamp(System.currentTimeMillis()));
		fileReportBean.setNoOfInValidRecords(123);
		fileReportBean.setNoOfValidRecords(123);
		fileReportBean.setCreateddate(new Timestamp(System.currentTimeMillis()));
        dataWareHouseDao.persist(fileReportBean);
        assertTrue(!fileReportBean.getFileReportId().isEmpty());
    }

	@Test
	public void updateFileReportBeanTest() throws Exception {
		
		FileReportBean fileReportBean = new FileReportBean();
		fileReportBean.setFileReportId("22");
		fileReportBean.setProcessEndTime(new Timestamp(System.currentTimeMillis()));
        assertTrue(dataWareHouseDao.updateFileReportBean(fileReportBean) > 0);
    }
	
	@Test
	public void getAllFileReports_withAvailableFile() throws Exception {
		
		FileReportBean fileReportBean = new FileReportBean();
		fileReportBean.setFileName("SampleCsv.csv");
		
		assertFalse(dataWareHouseDao.searchSummaryReport(fileReportBean).isEmpty());
    }

	@Test
	public void getAllFileReports_withUnAvailableFile() throws Exception {
		
		FileReportBean fileReportBean1 = new FileReportBean();
		fileReportBean1.setFileName("ABCDEFFFF.csv");
		
		assertTrue(dataWareHouseDao.searchSummaryReport(fileReportBean1).isEmpty());
    }
	
	@Test(timeout=5000)
	public void saveValidFxRecordsTest() throws Exception {	
		
		List<FxDealsBean> fxDealsBeans = new ArrayList<FxDealsBean>();
		
		for(int i=0; i<100000; i++){
			FxDealsBean fxDealsBean = new FxDealsBean();
			fxDealsBean.setDealAmount(Double.parseDouble("12321"));
			fxDealsBean.setDealTimeStamp(new Timestamp(System.currentTimeMillis()));
			fxDealsBean.setDealUniqueID(12123);
			fxDealsBean.setFromCurrencyISOCode("INR");
			fxDealsBean.setToCurrencyISOCode("AED");
			fxDealsBeans.add(fxDealsBean);
		}
		assertTrue(dataWareHouseDao.saveValidFxRecords(fxDealsBeans));
    }
	
	@Test(timeout=5000)
	public void saveInvalidForexRecordsTest() throws Exception {	
		
		List<FxInvalidDealsBean> fxInvalidDealsBeans = new ArrayList<FxInvalidDealsBean>();
		
		for(int i=0; i<100000; i++){
			FxInvalidDealsBean fxInvalidDealsBean = new FxInvalidDealsBean();
			fxInvalidDealsBean.setDealAmount("12100");
			fxInvalidDealsBean.setDealTimeStamp("12/25/2017 16:22:01");
			fxInvalidDealsBean.setDealUniqueID("12123");
			fxInvalidDealsBean.setFromCurrencyISOCode("INR");
			fxInvalidDealsBean.setToCurrencyISOCode("AED");
			fxInvalidDealsBeans.add(fxInvalidDealsBean);
		}
		assertTrue(dataWareHouseDao.saveInvalidForexRecords(fxInvalidDealsBeans));
    }
	
	@Test
	public void isDuplicateFileUploaded_withDuplicateFile() throws Exception {	
		
		assertTrue(dataWareHouseDao.isDuplicateFileUploaded("SampleCsv.csv"));
    }	

	@Test
	public void isDuplicateFileUploaded_withUniqueFile() throws Exception {	
		
		assertFalse(dataWareHouseDao.isDuplicateFileUploaded("UniqueFile.csv"));
    }	

}
