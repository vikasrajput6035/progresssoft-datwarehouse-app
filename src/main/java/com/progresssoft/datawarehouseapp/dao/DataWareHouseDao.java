package com.progresssoft.datawarehouseapp.dao;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.progresssoft.datawarehouseapp.beans.FileReportBean;
import com.progresssoft.datawarehouseapp.beans.FxAccumulativeCountBean;
import com.progresssoft.datawarehouseapp.beans.FxDealsBean;
import com.progresssoft.datawarehouseapp.beans.FxInvalidDealsBean;


@Component
public class DataWareHouseDao {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(DataWareHouseDao.class);
	
	@PersistenceContext
	private EntityManager em;
		
	@Transactional
    public void persist(FileReportBean fileReportBean) throws Exception{
		LOGGER.debug("persist Start");
		em.persist(fileReportBean);
		LOGGER.debug("persist End");
    }
	
	@Transactional
    public int updateFileReportBean(FileReportBean fileReportBean) throws Exception{
        
		LOGGER.debug("updateFileReportBean Start");
		Timestamp endTimeStamp = new Timestamp(System.currentTimeMillis());
		fileReportBean.setProcessEndTime(endTimeStamp);
		Query query = em.createQuery("UPDATE FileReportBean SET processEndTime =:processEndTime WHERE fileReportId =:fileReportId");
		LOGGER.debug("updateFileReportBean End");
		return query.setParameter("processEndTime", endTimeStamp).setParameter("fileReportId", fileReportBean.getFileReportId()).executeUpdate();
    }
	
	@Transactional
    public List<FileReportBean> searchSummaryReport(FileReportBean fileReportBean) throws Exception{
        
		LOGGER.debug("searchSummaryReport Start");
		if( null == fileReportBean.getFileName()){
			fileReportBean.setFileName("");
		}
		TypedQuery<FileReportBean> query = em.createQuery("SELECT F FROM FileReportBean F where fileName like:fileName ORDER BY F.createddate desc", FileReportBean.class);
        query.setParameter("fileName", "%"+fileReportBean.getFileName()+"%");
        LOGGER.debug("searchSummaryReport End");
        return query.getResultList();
    }
    
	@Transactional
    public List<FxAccumulativeCountBean> getAccumulativeDeals() throws Exception{
		
		LOGGER.debug("getAccumulativeDeals Start");
		TypedQuery<FxAccumulativeCountBean> query = em.createQuery("SELECT F FROM FxAccumulativeCountBean F ", FxAccumulativeCountBean.class);
		LOGGER.debug("getAccumulativeDeals End");
        return query.getResultList();
    }

	@Transactional
    public boolean saveValidFxRecords(List<FxDealsBean> fxDealsBeans) throws Exception{
    
    	LOGGER.debug("saveValidFxRecords Start");
    	StringBuilder selectQueryBuilder = new StringBuilder();
    	StringBuilder whereClauseBuilder = new StringBuilder();
    	int entityCount = fxDealsBeans.size();
    	int batchSize = 25000;
    	 
    	//EntityManager entityManager = null;
    	//EntityTransaction transaction = null;
    	//EntityManagerFactory emf = Persistence.createEntityManagerFactory("GuestbookPU");
    	//try {
    	    //entityManager = emf.createEntityManager();
    	 
    	    /*transaction = em.getTransaction();
    	    transaction.begin();*/
    	selectQueryBuilder = new StringBuilder("INSERT INTO fxdealstable (dealUniqueID,fromCurrencyISOCode,toCurrencyISOCode,dealTimeStamp,dealAmount) VALUES ");
    	    for ( int i = 0; i < entityCount; i++ ) {
    	       
    	    	String dealTimeStampStr = null;
    	    	String toCurrStr = null;
    	    	String fromCurrStr = null;
    	    	
    	    	if( null != fxDealsBeans.get(i).getDealTimeStamp()){
    	    		dealTimeStampStr = "'"+fxDealsBeans.get(i).getDealTimeStamp()+"'";
    	    	}
    	    	
    	    	if( !StringUtils.isEmpty(fxDealsBeans.get(i).getToCurrencyISOCode()) ){
    	    		toCurrStr = "'"+fxDealsBeans.get(i).getToCurrencyISOCode()+"'";
    	    	}
    	    	
    	    	if( !StringUtils.isEmpty(fxDealsBeans.get(i).getFromCurrencyISOCode())){
    	    		fromCurrStr = "'"+fxDealsBeans.get(i).getFromCurrencyISOCode()+"'";
    	    	}
    	    	
    	    	whereClauseBuilder.append("("+fxDealsBeans.get(i).getDealUniqueID()+","+fromCurrStr+","+toCurrStr+","+dealTimeStampStr+","+fxDealsBeans.get(i).getDealAmount()+")");
            	if ( /*(i > 0 && ( i% batchSize == 0)) ||*/ (i==(entityCount-1)) ) {
    	            /*entityManager.flush();
    	            entityManager.clear();*/
    	            
    	            Query query = em.createNativeQuery(selectQueryBuilder.append(whereClauseBuilder).toString());
    	            whereClauseBuilder = new StringBuilder("");
    	            query.executeUpdate();

    	            /*transaction.commit();
    	            transaction.begin();*/
    	        }else{
    	        	whereClauseBuilder.append(",");
    	        }
    	    }
    	 
    	    /*transaction.commit();*/
    	    LOGGER.debug("saveValidFxRecords End");
    	    return true;
    	/*} catch (RuntimeException e) {
    	    if ( transaction != null && 
    	         transaction.isActive()) {
    	        transaction.rollback();
    	    }
    	    throw e;
    	} finally {
    	    if (entityManager != null) {
    	        entityManager.close();
    	    }
    	    
    	    
    	}*/
    }

	@Transactional
    public boolean saveInvalidForexRecords(List<FxInvalidDealsBean> fxInvalidDealsBeans)throws Exception{
        
    	LOGGER.debug("saveInvalidForexRecords Start");
    	StringBuilder selectQueryBuilder = new StringBuilder();
    	StringBuilder whereClauseBuilder = new StringBuilder();
    	int entityCount = fxInvalidDealsBeans.size();
    	int batchSize = 25000;
    	 
    	/*EntityManager entityManager = null;
    	EntityTransaction transaction = null;*/
    	//EntityManagerFactory emf = Persistence.createEntityManagerFactory("GuestbookPU");
    	//try {
    	    //entityManager = emf.createEntityManager();
    	 
    	    /*transaction = em.getTransaction();
    	    transaction.begin();*/
    		selectQueryBuilder = new StringBuilder("INSERT INTO fxInvalidDealsTable (dealUniqueID,fromCurrencyISOCode,toCurrencyISOCode,dealTimeStamp,dealAmount) VALUES ");
    	    for ( int i = 0; i < entityCount; i++ ) {
    	        
    	    	whereClauseBuilder.append("('"+fxInvalidDealsBeans.get(i).getDealUniqueID()+"','"+fxInvalidDealsBeans.get(i).getFromCurrencyISOCode()+"','"+fxInvalidDealsBeans.get(i).getToCurrencyISOCode()+"','"+fxInvalidDealsBeans.get(i).getDealTimeStamp()+"','"+fxInvalidDealsBeans.get(i).getDealAmount()+"')");
            	if ( /*(i > 0 && ( i% batchSize == 0)) ||*/ (i==(entityCount-1)) ) {
    	            /*entityManager.flush();
    	            entityManager.clear();*/
    	            
    	            Query query = em.createNativeQuery(selectQueryBuilder.append(whereClauseBuilder).toString());
    	            whereClauseBuilder = new StringBuilder("");
    	            query.executeUpdate();
    	        	
    	            /*transaction.commit();
    	            transaction.begin();*/
    	            
    	        }else{
    	        	whereClauseBuilder.append(",");
    	        }
    	    }
    	 
    	    //transaction.commit();
    	    LOGGER.debug("saveInvalidForexRecords End");
    	    return true;
    	/*} catch (RuntimeException e) {
    	    if ( transaction != null && 
    	         transaction.isActive()) {
    	        transaction.rollback();
    	    }
    	    throw e;
    	} finally {
    	    if (entityManager != null) {
    	        entityManager.close();
    	    }
    	}*/
    	
    }

    @Transactional
    public boolean isDuplicateFileUploaded(String fileName) throws Exception{
        
    	LOGGER.debug("isDuplicateFileUploaded Start");
    	TypedQuery<FileReportBean> query = em.createQuery("SELECT F FROM FileReportBean F where fileName =:fileName ", FileReportBean.class);
        query.setParameter("fileName",fileName);
        List<FileReportBean> list = (ArrayList<FileReportBean>)query.getResultList(); 
        if(list.isEmpty()){
        	LOGGER.debug("isDuplicateFileUploaded End with Empty list");
        	return false;
        }else{
        	LOGGER.debug("isDuplicateFileUploaded End with non-Emtpy list");
        	return true;
        }
        
    }
    
    @Transactional
    public void persistAccumulativeCount(Map<String,Integer> accuNewMap) throws Exception{
        
    	LOGGER.debug("persistAccumulativeCount Start");
    	TypedQuery<FxAccumulativeCountBean> query = em.createQuery("SELECT F FROM FxAccumulativeCountBean F ", FxAccumulativeCountBean.class);
        List<FxAccumulativeCountBean> list = (ArrayList<FxAccumulativeCountBean>)query.getResultList(); 
    	
    	Iterator it = accuNewMap.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry pair = (Map.Entry)it.next();
            System.out.println(pair.getKey() + " = " + pair.getValue());
            FxAccumulativeCountBean countBean = new FxAccumulativeCountBean();
            countBean.setCurrencyCode(pair.getKey().toString());
            countBean.setDealCount(Integer.parseInt(pair.getValue().toString()));
            for(FxAccumulativeCountBean accumulativeCountBean:list){
    			
            	if(pair.getKey().equals(accumulativeCountBean.getCurrencyCode())){
        			countBean.setDealCount(accumulativeCountBean.getDealCount()+accuNewMap.get(accumulativeCountBean.getCurrencyCode()));
        			//em.merge(countBean);
        		}
            }
            em.merge(countBean);
        }
        
        LOGGER.debug("persistAccumulativeCount End");
    }
}
