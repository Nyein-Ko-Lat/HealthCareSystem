package edu.gisma.gh1043541.healthcaresystem.service;

import java.util.List;

public interface IBaseService<T, ID> {
    T save(T entity);
    void delete(ID id);
    void delete(Long id, Long UpdatedBy);
    T findById(ID id);
    List<T> findAll();
}