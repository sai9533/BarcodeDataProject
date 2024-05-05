package com.example.demo.Controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.demo.DTO.DisplayAll;
import com.example.demo.DTO.ProductDTO;
import com.example.demo.DTO.Sales;
import com.example.demo.Entity.Product;
import com.example.demo.Repo.ProductRepo;
import com.example.demo.Service.BarCodeService;
import com.example.demo.Service.BarService;
import com.example.demo.Util.ImageUtils;
import com.example.demo.Util.QrCodeDownload;

import jakarta.servlet.http.HttpServletResponse;


@Controller
@RequestMapping("/admin")
public class AdminController {
	
	@Autowired
	ProductRepo repo;
	
	@Autowired
	BarCodeService service;
	
	@Autowired
	QrCodeDownload download;

	@Autowired
	BarService bar;


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
		
		Product pwd=new Product();
		pwd.setProductName(product.getProductName());
		pwd.setProductImg(product.getFile().getBytes());
		pwd.setProductPrice(product.getProductPrice());
		pwd.setProductWeight(product.getProductWeight());
		pwd.setProductType(product.getProductType());
		//pwd.setImageData(service.generateQRCodeImage("Product Name:"+product.getProductName()+"\nProduct Weight:"+product.getProductWeight()));
		pwd.setImageData(bar.generateBarcode("123456789", 100, 50));
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
	
	@GetMapping("admin/sales")
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




}
