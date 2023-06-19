package org.aptech.t2109e.jspservlet.jpa;

import org.aptech.t2109e.jspservlet.utils.select.Pagination;

import java.sql.ResultSet;
import java.util.List;

public interface JpaExecutor <T> {
    List<T> findAll();
    List<T> entityParser(ResultSet rs);
    T findById(int id);
    T findByField(String fieldName, String value);
    List<T> findAllByField(String fieldName, String value);
    T create(T entity);
    List<T> createMany(List<T> list);
    boolean deleteById(int id);
    T updateById(int id, T entity);

    Pagination<T> filterAndPagination(Pagination<T> pagination);

}
