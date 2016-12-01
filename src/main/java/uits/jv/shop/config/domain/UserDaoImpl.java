package uits.jv.shop.config.domain;

import java.util.List;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class UserDaoImpl implements UserDao {
    @Autowired
    private SessionFactory factory;
    
    @Override
    @Transactional
    public User loadByUsername(String username) {
        return (User)factory.getCurrentSession().createCriteria(User.class)
                .add(Restrictions.eq("email", username)).uniqueResult();
    }

    @Override
    @Transactional
    public User getById(Integer id) {
        return (User)factory.getCurrentSession().createCriteria(User.class)
                .add(Restrictions.eq("id", id)).uniqueResult();
    }

    @Override
    @Transactional
    public Integer save(User u) {
        return (Integer)factory.getCurrentSession().save(u);
    }

    @Override
    @Transactional
    public List<User> list() {
        return factory.getCurrentSession().createCriteria(User.class).list();
    }

}
