package service;

import dao.GenericDao;

public interface BasicService <T> {
    GenericDao<T> getDao();

    default T insert(T entity){
        return getDao().insert(entity);
    }
    default T update(T entity){
        return getDao().update(entity);
    }
   default void delete(Long id){
        getDao().delete(id);  
    }
}
