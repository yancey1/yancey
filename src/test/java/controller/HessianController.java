package controller;

import java.net.MalformedURLException;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.caucho.hessian.client.HessianProxyFactory;
import com.yancey.manager.service.HessianService;

public class HessianController {

	public static void main(String[] args) {
		ClassPathXmlApplicationContext context=new ClassPathXmlApplicationContext(new String[]{"classpath:spring/applicationContext-hessian.xml"});
		HessianService service=(HessianService)context.getBean("hessianClient");
		System.out.println(service.sayHello(123));
		//String url = "http://114.55.238.89:8080/joseph-app/remote/testHessianService.htm";
		String url = "http://localhost:8080/joseph-app/remote/testHessianService.htm";
		HessianProxyFactory factory = new HessianProxyFactory();
		try {
			HessianService basic = (HessianService) factory.create(HessianService.class, url);
			System.out.println(basic.sayHello(111));
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
