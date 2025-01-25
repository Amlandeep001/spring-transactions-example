package com.example.transaction.handler;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.example.transaction.entity.Product;
import com.example.transaction.repository.InventoryRepository;

@Service
public class InventoryHandler
{
	private final InventoryRepository inventoryRepository;

	public InventoryHandler(InventoryRepository inventoryRepository)
	{
		this.inventoryRepository = inventoryRepository;
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public Product updateProductDetails(Product product)
	{
		// forcefully throwing exception to simulate use of tx
		if(product.getPrice() > 5000)
		{
			throw new RuntimeException("DB crashed.....");
		}
		return inventoryRepository.update(product);
	}

	public Product getProduct(int id)
	{
		return inventoryRepository.findById(id)
				.orElseThrow(
						() -> new RuntimeException("Product not available with id : " + id));
	}
}
