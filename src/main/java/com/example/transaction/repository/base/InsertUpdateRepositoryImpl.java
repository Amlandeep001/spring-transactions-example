package com.example.transaction.repository.base;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.springframework.data.jpa.repository.support.JpaEntityInformation;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import jakarta.persistence.EntityManager;

public class InsertUpdateRepositoryImpl<T, ID extends Serializable> extends SimpleJpaRepository<T, ID> implements InsertUpdateRepository<T, ID>
{
	private final EntityManager entityManager;

	public InsertUpdateRepositoryImpl(JpaEntityInformation<T, ?> entityInformation, EntityManager entityManager)
	{
		super(entityInformation, entityManager);
		this.entityManager = entityManager;
	}

	public InsertUpdateRepositoryImpl(Class<T> domainClass, EntityManager entityManager)
	{
		super(domainClass, entityManager);
		this.entityManager = entityManager;
	}

	@Override
	@Transactional
	public <S extends T> S insert(S entity)
	{
		Assert.notNull(entity, "Entity must not be null.");

		entityManager.persist(entity);
		return entity;
	}

	@Override
	@Transactional
	public <S extends T> S update(S entity)
	{
		Assert.notNull(entity, "Entity must not be null.");

		return entityManager.merge(entity);
	}

	@Override
	@Transactional
	public <S extends T> List<S> insertAll(Iterable<S> entities)
	{
		Assert.notNull(entities, "Entities must not be null!");

		List<S> result = new ArrayList<>();

		for(S entity : entities)
		{
			result.add(insert(entity));
		}

		return result;
	}

	@Override
	@Transactional
	public <S extends T> List<S> updateAll(Iterable<S> entities)
	{
		Assert.notNull(entities, "Entities must not be null!");

		List<S> result = new ArrayList<>();

		for(S entity : entities)
		{
			result.add(update(entity));
		}

		return result;
	}
}