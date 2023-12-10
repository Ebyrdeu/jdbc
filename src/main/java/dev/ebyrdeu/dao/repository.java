package dev.ebyrdeu.dao;

public interface repository<T> {
    void create(T entity);

    void findUnique(T entity);

    void findMany();

    void update(T entity);

    void delete(T entity);

}
