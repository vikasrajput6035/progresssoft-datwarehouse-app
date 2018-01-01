package com.progresssoft.datawarehouseapp.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

import com.progresssoft.datawarehouseapp.dao.DataWareHouseDao;

@ControllerAdvice
public class ExceptionHandlerController {

	private static final Logger LOGGER = LoggerFactory.getLogger(ExceptionHandlerController.class);
	
	@ExceptionHandler(Exception.class)
	public ModelAndView handleCustomException(Exception ex) {

		ModelAndView model = new ModelAndView("common-exception");
		model.addObject("errCode", 100);
		model.addObject("errMsg", ex.getMessage());
		
		LOGGER.info("Error Occurred :: ",ex);
		return model;

	}

}
