package service;
import java.util.List;
public interface crud <T> {
    void ajouter(T t);
    void modifier(T t);
    void supprimer (T t);
    List<T> afficher();

}

