package dao;

import entity.BaseEntity;
import entity.User;
import jakarta.persistence.EntityManager;

public class UserRepository extends RepositoryBase<Long, User> {
    public UserRepository(EntityManager entityManager) {
        super(User.class, entityManager);
    }
}
