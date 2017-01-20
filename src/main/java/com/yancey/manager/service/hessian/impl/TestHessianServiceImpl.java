package com.yancey.manager.service.hessian.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yancey.manager.service.HessianService;
import com.yancey.manager.service.hessian.TestHessianService;

@Service
public class TestHessianServiceImpl implements TestHessianService {

	
	
	@Override
	public String sayHello(String name) {
		return "";
	}

	@Override
	public String sayWorld(String name) {
		return "";
	}

}
