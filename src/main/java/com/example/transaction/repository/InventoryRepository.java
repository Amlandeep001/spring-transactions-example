package com.example.transaction.repository;

import com.example.transaction.entity.Product;
import com.example.transaction.repository.base.InsertUpdateRepository;

public interface InventoryRepository extends InsertUpdateRepository<Product, Integer>
{
}
