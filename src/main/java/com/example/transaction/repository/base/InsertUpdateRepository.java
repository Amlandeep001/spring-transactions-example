package com.example.transaction.repository.base;

import java.io.Serializable;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface InsertUpdateRepository<T, ID extends Serializable> extends JpaRepository<T, ID>
{
	<S extends T> S insert(S object);

	<S extends T> S update(S object);

	<S extends T> List<S> insertAll(Iterable<S> entities);

	<S extends T> List<S> updateAll(Iterable<S> entities);
}