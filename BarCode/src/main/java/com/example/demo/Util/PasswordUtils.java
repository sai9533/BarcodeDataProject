package com.example.demo.Util;

import java.util.Arrays;
import java.util.Random;
import java.util.UUID;

import org.springframework.stereotype.Component;
import java.security.SecureRandom;



@Component
public class PasswordUtils {
	
	
	
	public String getOtp()
	{
		return String.format("%04d", new Random().nextInt(10000));
		
	}
	
	 private static final String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789!@#$%^&*()-_=+";

	    public  String generateRandomPassword(int length) {
	        SecureRandom random = new SecureRandom();
	        StringBuilder password = new StringBuilder();
	        for (int i = 0; i < length; i++) {
	            int randomIndex = random.nextInt(CHARACTERS.length());
	            password.append(CHARACTERS.charAt(randomIndex));
	        }
	        return password.toString();
	    }
	    
	    private static final String GEN= "123456789";

	    public  String generateid(int length) {
	        SecureRandom random = new SecureRandom();
	        StringBuilder password = new StringBuilder();
	        for (int i = 0; i < length; i++) {
	            int randomIndex = random.nextInt(GEN.length());
	            password.append(GEN.charAt(randomIndex));
	        }
	        return password.toString();
	    }
	    public static String generateId() {
	        Random random = new Random();
	        int min = 1000000; // Minimum 8-digit number
	        int max = 9999999; // Maximum 8-digit number
	        int id = random.nextInt(max - min + 1) + min;
	        return String.valueOf(id);
	    }
	    
	    public static String checkSum()
	    {
	    	String id = generateId();
	    //String id="1234567";
	    	int[] a=new int[8];
	    	int count=1;
	    	for(char c:id.toCharArray())
	    	{
	    		System.out.println(c);
	    	     a[count]=Character.getNumericValue(c);
	    	count++;
	    	}
	    	System.out.println(Arrays.toString(a));
	    	int esum=0;
	    	int osum=0;
	    	for(int i=1;i<8;i++)
	    	{
	    		System.out.println(a[i]);
	    		if(i%2!=0)
	    		{
	    			osum=osum+a[i];
	    		}
	    		else
	    		{
	    			esum=esum+a[i];
	    		}
	    	}
	    	System.out.println(esum+" "+osum);
	       int totalSum=osum*3+esum;
	    	System.out.println(osum);
	    	int checksum=(10 - (totalSum % 10)) % 10;
	    	System.out.println();
	    	
			return id+String.valueOf(checksum);
	    }
}
