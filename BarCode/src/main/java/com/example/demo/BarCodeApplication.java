package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import com.example.demo.Controller.BarCodeController;
import com.example.demo.Util.EmailUtils;

import io.swagger.v3.oas.models.annotations.OpenAPI30;
import jakarta.servlet.http.HttpServletResponse;

@SpringBootApplication
@OpenAPI30
public class BarCodeApplication {

	public static void main(String[] args) {
		ConfigurableApplicationContext run = SpringApplication.run(BarCodeApplication.class, args);
		BarCodeController bean = run.getBean(BarCodeController.class);
		//HttpServletResponse s=new HttpServletResponse();
		//bean.barcode("",s);
	  // EmailUtils bean2 = run.getBean(EmailUtils.class);
	   //bean2.sendEmail("welcome", "data", "sai953392@gmail.com");
	  // bean2.sendEmail("sai953392@gmail.com", "welomce", "welcome");
	}

}
