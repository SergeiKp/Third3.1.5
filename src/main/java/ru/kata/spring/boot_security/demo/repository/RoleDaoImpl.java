package ru.kata.spring.boot_security.demo.repository;

import org.springframework.stereotype.Repository;
import ru.kata.spring.boot_security.demo.model.Role;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Optional;

@Repository
public class RoleDaoImpl implements RoleDao {
    @PersistenceContext
    private EntityManager em;

    @Override
    public List<Role> getRoles() {
        return em.createQuery("from Role", Role.class).getResultList();
    }

    @Override
    public Optional<Role> findById(Long id) {
        return Optional.ofNullable(em.find(Role.class, id));
    }

    @Override
    public Optional<Role> findByName(String name) {
        return em.createQuery("select r from Role r where r.name = :name", Role.class)
                .setParameter("name", name)
                .getResultStream()
                .findFirst();
    }

    @Override
    public void addRole(Role role) {
        em.persist(role);
    }
}
