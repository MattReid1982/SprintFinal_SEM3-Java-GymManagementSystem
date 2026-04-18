package com.keyin.gymmanagement.dao;

import java.util.List;

public interface IBaseDAO<T> {
  List<T> findAll();

  T findById(int id);

  boolean create(T entity);

  boolean update(T entity);

  boolean delete(int id);
}
