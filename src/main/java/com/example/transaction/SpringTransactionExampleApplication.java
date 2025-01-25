package com.example.transaction;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import com.example.transaction.repository.base.InsertUpdateRepositoryImpl;

@SpringBootApplication
@EnableJpaRepositories(repositoryBaseClass = InsertUpdateRepositoryImpl.class)
public class SpringTransactionExampleApplication
{
	public static void main(String[] args)
	{
		SpringApplication.run(SpringTransactionExampleApplication.class, args);
	}
}
