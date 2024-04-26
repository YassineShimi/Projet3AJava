package services;

import entities.Exposees;

import java.sql.SQLException;
import java.util.List;

public interface CrudInterface<T> {
    void create(T entity);
    void update(T entity);
    public void delete(T t) throws SQLException;
    T getById(int id);
    List<T> getAll();
    public void ajouter(T t) throws SQLException;
    public void modifier(T t) throws SQLException;
    public void supprimer(T t) throws SQLException;
    public List<T> afficher() throws SQLException;
}
