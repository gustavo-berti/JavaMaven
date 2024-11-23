package dao;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public abstract class GenericDao<T> {
    private static final EntityManagerFactory emf = Persistence.createEntityManagerFactory("nomePU");
    private final Class<T> entityClass;

    public GenericDao(Class<T> entityClass) {
        this.entityClass = entityClass;
    }

    protected EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public T insert(T entity) {
        EntityManager em = getEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(entity);
            em.getTransaction().commit();
            em.close();
            return entity;
        } catch (Exception e) {
            em.getTransaction().rollback();
            em.close();
            return null;
        }
    }

    public T update(T entity) {
        EntityManager em = getEntityManager();
        try {
            em.getTransaction().begin();
            T entityUpdated = em.merge(entity);
            em.getTransaction().commit();
            em.close();
            return entityUpdated;
        } catch (Exception e) {
            em.getTransaction().rollback();
            em.close();
            return null;
        }
    }

    public T delete(Long id) {
        EntityManager em = getEntityManager();
        try {
            em.getTransaction().begin();
            T entity = em.find(entityClass, id);
            if (entity != null) {
                em.remove(entity);
            }
            em.getTransaction().commit();
            em.close();
            return entity;
        } catch (Exception e) {
            em.getTransaction().rollback();
            em.close();
            return null;
        }
    }

}
