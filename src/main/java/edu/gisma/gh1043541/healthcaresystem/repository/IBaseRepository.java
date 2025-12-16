package edu.gisma.gh1043541.healthcaresystem.repository;

import edu.gisma.gh1043541.healthcaresystem.entity.Doctor;

import java.util.List;

public interface IBaseRepository<T, ID> {
    Long save(T entity);
    T findById(ID id);

    void delete(ID doctorID);

    void delete(ID doctorID, Long updatedBy);

    List<T> findAll();
}
