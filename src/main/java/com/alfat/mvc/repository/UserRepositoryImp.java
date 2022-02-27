package com.alfat.mvc.repository;

import com.alfat.mvc.repository.iml.UserRepository;
import com.alfat.mvc.model.User;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class UserRepositoryImp implements UserRepository {

    @PersistenceContext
    private EntityManager entityManager;

    protected EntityManager getEntityManager() {
        return this.entityManager;
    }

    @Override
    public void addUser(User user) {
        getEntityManager().persist(user);
    }

    @Override
    public void deleteUser(Long id) {
        getEntityManager()
                .createQuery("delete from User where id=: id")
                .setParameter("id", id)
                .executeUpdate();
    }

    @Override
    public void editUser(User user) {
        getEntityManager().merge(user);
    }

    @Override
    public User getUserById(Long id) {
        return getEntityManager().find(User.class, id);
    }

    @Override
    public List<User> getAllUsers() {
        return getEntityManager().createQuery("From User").getResultList();
    }
}
