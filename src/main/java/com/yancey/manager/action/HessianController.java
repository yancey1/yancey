package com.yancey.manager.action;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yancey.manager.service.hessian.TestHessianService;

@Controller
public class HessianController {
	
	@Autowired
	private TestHessianService testHessianServiceImpl;
	
	@RequestMapping(value = { "/testHessian" }, method = RequestMethod.GET)
	@ResponseBody
	  public String testHessian() {
	    return testHessianServiceImpl.sayHello("1");
	  }
	
	@RequestMapping(value = { "/testHessian1" }, method = RequestMethod.GET)
	@ResponseBody
	  public String testHessian1(String name) {
	    return testHessianServiceImpl.sayWorld(name);
	  }
}
