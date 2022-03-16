package vn.cmc.du21.persistence.internal.dao;

import java.util.List;
import java.util.Optional;

public interface Dao <T> {
    List<T> getAll();
    Optional<T> get(Long id);
    List<T> getList( String page, String size, String sort);
    void deleteById(Long id);
    void save(T entity);
    void update(T entity);
}
