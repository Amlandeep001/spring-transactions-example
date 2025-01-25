package com.example.transaction.service.isolation;

import org.springframework.stereotype.Component;

import com.example.transaction.service.ProductService;

@Component
public class SerializableIsolationDemo
{
	private final ProductService productService;

	public SerializableIsolationDemo(ProductService productService)
	{
		this.productService = productService;
	}

	public void testSerializableIsolation(int productId) throws InterruptedException
	{
		Thread transactionA = new Thread(() ->
		{
			try
			{
				productService.updateStock(productId, 5); // Update stock to 5
			}
			catch(InterruptedException e)
			{
				e.printStackTrace();
			}
		});

		Thread transactionB = new Thread(() ->
		{
			try
			{
				Thread.sleep(1000); // Ensure Transaction A starts first
				int stock = productService.checkStock(productId); // Attempt to read stock
				System.out.println("Transaction B: Final stock: " + stock);
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
		});

		transactionA.start();
		transactionB.start();

		transactionA.join();
		transactionB.join();
	}
}
