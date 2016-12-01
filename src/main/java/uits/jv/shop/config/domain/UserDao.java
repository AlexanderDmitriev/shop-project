package uits.jv.shop.config.domain;

import java.util.List;

public interface UserDao {
    User loadByUsername(String username);
    User getById(Integer id);
    Integer save(User u);
    List<User> list();
}
