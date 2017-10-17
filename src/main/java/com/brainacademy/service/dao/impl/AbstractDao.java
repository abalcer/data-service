package com.brainacademy.service.dao.impl;

import com.brainacademy.service.dao.GenericDao;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import javax.persistence.TypedQuery;
import java.util.List;

abstract class AbstractDao<T>
        implements GenericDao<T> {

    final SessionFactory sessionFactory;

    AbstractDao(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public T create(T entity) {
        try (Session session = sessionFactory.openSession()) {
            session.save(entity);
            return entity;
        }
    }

    @Override
    public void update(T entity) {
        try (Session session = sessionFactory.openSession()) {
            session.update(entity);
        }
    }

    @Override
    public T getOne(int id) {
        try (Session session = sessionFactory.openSession()) {
            return session.get(getEntityType(), id);
        }
    }

    @Override
    public List<T> getAll() {
        try (Session session = sessionFactory.openSession()) {
            TypedQuery<T> query = session.createQuery(
                    "from " + getEntityType().getSimpleName(),
                    getEntityType());
            return query.getResultList();
        }
    }

    protected abstract Class<T> getEntityType();
}