package com.example.demo.Controller;

import java.io.IOException;
//import java.net.http.HttpHeaders;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.example.demo.Entity.User;
import com.example.demo.Repo.UserRepository;
import com.example.demo.Service.UserService;
import org.apache.catalina.connector.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.DTO.DisplayAll;
import com.example.demo.DTO.ProductDTO;
import com.example.demo.DTO.Sales;
import com.example.demo.Entity.Product;
import com.example.demo.Repo.ProductRepo;
import com.example.demo.Service.BarCodeService;
import com.example.demo.Service.BarService;
import com.example.demo.Util.ImageUtils;
import com.example.demo.Util.PasswordUtils;
import com.example.demo.Util.QrCodeDownload;
import com.google.zxing.WriterException;

import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletResponse;



@Controller
public class MainController {
	

	@Autowired
	ProductRepo repo;
	
	@Autowired
	BarCodeService service;
	
	@Autowired
	QrCodeDownload download;

	@Autowired
	BarService bar;
	
	@Autowired
	PasswordUtils pwdUtils;


	@GetMapping("/")
	public String home(Model model)
	{
		
		return "home";
	}
	/*@GetMapping(value="/display")
	public String listUsers(Model model) {
		
		Product p=new Product();
		model.addAttribute("product",p);
	    
		
		return "Form";
	}*/
	
	/*@PostMapping(value="/successful")
	public String successful(Product product,Model model) throws WriterException, IOException
	{
		
		
		product.setImageData(service.generateQRCodeImage(product.getProductName()));
		System.out.println(product);
		repo.save(product);
		Product p=new Product();
		
		model.addAttribute("product",p);
		
		return "Form";
	}*/
	
	@GetMapping(value="/data")
	public String showdata(Model model)
	{
		List<Product> findAll = repo.findAll();
		
		List<DisplayAll> data=new ArrayList<>();
		for(Product p:findAll)
		{
			System.out.println(p.getProductStatus());
			String productStatus = p.getProductStatus();
			if(!productStatus.equals("sold"))
			{
			DisplayAll dis=new DisplayAll();
			dis.setProductId(p.getProductId());
			dis.setProductName(p.getProductName());
			dis.setProductPrice(p.getProductPrice());
			dis.setProductWeight(p.getProductWeight());
			dis.setProductType(p.getProductType());
			dis.setQrImg(ImageUtils.encodeImage(p.getImageData()));
			dis.setProductImg(ImageUtils.encodeImage(p.getProductImg()));
			data.add(dis);
			}
		}
		
		
		
		//model.addAttribute("proImg", productImages);
		model.addAttribute("data", data);
		//model.addAttribute("imageDataList", base64ImageDataList);
		
		return "display";
	}
   
	
	@GetMapping(value="/display")
	public String listUsers(Model model) {
		
		ProductDTO p=new ProductDTO();
		model.addAttribute("product",p);
	    
		
		return "Form";
	}
	@PostMapping(value="/successful")
	public String successful(ProductDTO product,Model model) throws Exception
	{
		
		//String generateId = PasswordUtils.generateId();
		String generateId = pwdUtils.checkSum();
		System.out.println(generateId);
		Product pwd=new Product();
		pwd.setProductId(Long.valueOf(generateId));
		pwd.setProductName(product.getProductName());
		pwd.setProductImg(product.getFile().getBytes());
		pwd.setProductPrice(product.getProductPrice());
		pwd.setProductWeight(product.getProductWeight());
		pwd.setProductType(product.getProductType());
		//pwd.setImageData(service.generateQRCodeImage("Product Name:"+product.getProductName()+"\nProduct Weight:"+product.getProductWeight()));
		System.out.println(generateId);
		pwd.setImageData(bar.generateBarcode(generateId, 100, 50));
		pwd.setProductStatus("selling");
		System.out.println("Product Weight:"+product.getProductWeight());
		repo.save(pwd);
		ProductDTO p=new ProductDTO();
		
		model.addAttribute("product",p);
		
		return "Form";
	}
	
    
	
	@GetMapping("/status/{id}")
	public String statusSold(@PathVariable String id,HttpServletResponse reponse) throws IOException {
		//System.out.println(id);
		Long ide = Long.valueOf(id);
		Optional<Product> findById = repo.findById(ide);


		Product orElse = findById.orElse(null);
		if (orElse.getProductStatus().equals("selling")) {
			orElse.setProductStatus("sold");
			repo.save(orElse);
			return "redirect:/data";

		} else {
			orElse.setProductStatus("selling");
			repo.save(orElse);
			return "redirect:/sales";

		}
		//System.out.println(orElse.getProductWeight());
		//download.downloadImageWithContent(orElse.getImageData(), reponse);
	}

	@GetMapping("/st/{id}")
	public ResponseEntity<Resource> status(@PathVariable String id,HttpServletResponse reponse) throws IOException
	{
		System.out.println(id);
		Long ide=Long.valueOf(id);
		Optional<Product> findById = repo.findById(ide);
         
	    
	    	Product product = findById.get();
	    	String contentType = "image/png";
		
	    	return ResponseEntity.ok().contentType(MediaType.parseMediaType(contentType))

					.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\""+"codeqr"+"\"")

					.body(new ByteArrayResource(product.getImageData()));
	
		
		
	}
	
	@GetMapping("/sales")
    public String sales(Model model)
	{
		List<Product> findAll = repo.findAll();
		List<Sales> listsales=new ArrayList<>();
		long total=0;
		for(Product p:findAll)
		{
			Sales s=new Sales();
			if(p.getProductStatus().equals("sold"))
			{
				
			s.setProductId(p.getProductId());
			s.setProductName(p.getProductName());
			s.setProductWeight(p.getProductWeight());
			s.setProductPrice(p.getProductPrice());

			total=total+Long.parseLong(p.getProductPrice());
			listsales.add(s);
			}
			
		}
		//System.out.println(total);
		model.addAttribute("sale", listsales);
		model.addAttribute("total", total);
		return "Sales";
	}

	 @GetMapping("/scanner")
     public String scanner(Model mode) {
		 
		 
		 return "Scanner";
	 }
	 @PostMapping("/submitscanner")
	 public String sbscanner(Model model,@RequestParam Long id )
	 {
		Optional<Product> findById = repo.findById(id); 
		DisplayAll pro=new DisplayAll();
		Product product = findById.get();
		pro.setProductId(product.getProductId());
		pro.setProductName(product.getProductName());
		pro.setProductPrice(product.getProductPrice());
		pro.setProductImg(ImageUtils.encodeImage(product.getProductImg()));
		pro.setQrImg(ImageUtils.encodeImage(product.getImageData()));
		pro.setProductWeight(product.getProductWeight());
		pro.setProductType(product.getProductType());
		model.addAttribute("lst", pro);
		
		return "Scanner";
	 }

	
}
