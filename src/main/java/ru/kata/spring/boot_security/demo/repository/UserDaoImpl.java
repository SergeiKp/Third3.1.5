package ru.kata.spring.boot_security.demo.repository;

import org.springframework.stereotype.Repository;
import ru.kata.spring.boot_security.demo.model.User;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class UserDaoImpl implements UserDao {

    @PersistenceContext
    private EntityManager em;

    @Override
    public void addUser(User user) {

        em.persist(user);
    }

    @Override
    public void updateUser(User user) {
        em.merge(user);
    }

    @Override
    public void deleteUser(User user) {
        em.remove(em.contains(user) ? user : em.merge(user));
    }

    @Override
    public List<User> getAllUsers() {
        return em.createQuery("from User", User.class).getResultList();
    }

    @Override
    public User getById(long id) {
        return em.find(User.class, id);
    }

    @Override
    public long countUsers() {
        return em.createQuery("select count(u) from User u", Long.class).getSingleResult();
    }

    @Override
    public User findByUsername(String username) {
        return em.createQuery("select u from User u where u.username = :username", User.class)
                .setParameter("username", username)
                .getResultStream()
                .findFirst()
                .orElse(null);
    }

}
