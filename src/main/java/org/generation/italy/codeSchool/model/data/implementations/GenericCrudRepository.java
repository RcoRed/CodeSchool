package org.generation.italy.codeSchool.model.data.implementations;

import org.generation.italy.codeSchool.model.data.abstractions.AbstractCrudRepository;
import org.generation.italy.codeSchool.model.data.exceptions.DataException;
import org.generation.italy.codeSchool.model.data.exceptions.EntityNotFoundException;
import org.generation.italy.codeSchool.model.entities.Course;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class GenericCrudRepository<T> implements AbstractCrudRepository<T> {
    @Autowired
    protected Session session;
    protected Class<?> entityClass;
    public GenericCrudRepository(Session session, Class<?> entityClass){
        this.session = session;
        this.entityClass = entityClass;
    }
    @Override
    public List<T> findAll() throws DataException {
        Query<T> q = (Query<T>) session.createQuery("from " + entityClass.getSimpleName(), entityClass);
        return q.list();
    }

    @Override
    public Optional<T> findById(long id) throws DataException {
        T found = (T) session.get(entityClass,id);
        return found != null ? Optional.of(found) : Optional.empty();
    }

    @Override
    public T create(T entity) throws DataException {
        session.persist(entity);
        return entity;
    }

    @Override
    public void update(T entity) throws EntityNotFoundException, DataException {
        session.merge(entity);
    }

    @Override
    public void deleteById(long id) throws EntityNotFoundException, DataException {
        T entity = (T) session.getReference(entityClass, id);
        session.remove(entity);
    }
}
