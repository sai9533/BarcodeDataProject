package com.example.demo.Util;

import org.hibernate.id.IdentifierGenerator;
import java.io.Serializable;
import java.util.Random;

import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SharedSessionContractImplementor;

public class IdGenerator implements IdentifierGenerator{

	@Override
	public Serializable generate(SharedSessionContractImplementor session, Object object) throws HibernateException {
		Random random = new Random();
        int min = 10000000; // Minimum 8-digit number
        int max = 99999999; // Maximum 8-digit number
        return (long) (random.nextInt(max - min + 1) + min);
		
	}

}
