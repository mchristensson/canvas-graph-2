package org.mac.canvasgraph;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

@SpringBootApplication
public class Application {

    public static void main(String[] args) {
    	/*
    	try {
    		ApplicationContext context =
    				new ClassPathXmlApplicationContext("AppContext.xml");
    		Object o = context.getBean("dataSource");
    		System.out.println("==== " + o.getClass().getName());
			
		} catch (Exception e) {
			e.printStackTrace();
			// TODO: handle exception
		}
		*/
    	SpringApplication.run(Application.class, args);
    }

}