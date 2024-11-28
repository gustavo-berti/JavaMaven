package controller;

import service.BasicService;

public interface BasicController <T> {
    BasicService<T> getService();

    default T insert(T entity){
        return getService().insert(entity);
    }
    default T update(T entity){
        return getService().update(entity);
    }
   default void delete(Long id){
        getService().delete(id);  
    }
}
