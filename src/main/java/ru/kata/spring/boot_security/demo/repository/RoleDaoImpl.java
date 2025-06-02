package ru.kata.spring.boot_security.demo.repository;

import org.springframework.stereotype.Repository;
import ru.kata.spring.boot_security.demo.model.Role;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class RoleDaoImpl implements RoleDao {
    @PersistenceContext
    private EntityManager em;

    @Override
    public List<Role> getRoles() {
        return em.createQuery("from Role", Role.class).getResultList();
    }

    @Override
    public Role findById(Long id) {
        return em.find(Role.class, id);
    }

    @Override
    public Role findByName(String name) {
        return em.createQuery("select r from Role r where r.name = :name", Role.class)
                .setParameter("name", name)
                .getResultStream()
                .findFirst()
                .orElse(null);
    }

    @Override
    public void addRole(Role role) {
        em.persist(role);
    }
}
