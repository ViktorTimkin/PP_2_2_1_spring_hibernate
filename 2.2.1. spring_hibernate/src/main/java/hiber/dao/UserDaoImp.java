package hiber.dao;

import hiber.model.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.TypedQuery;
import java.util.List;

@Repository
public class UserDaoImp implements UserDao {

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public void add(User user) {
        sessionFactory.getCurrentSession().save(user);
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<User> listUsers() {
        TypedQuery<User> query = sessionFactory.getCurrentSession().createQuery("from User");
        return query.getResultList();
    }

    @Override
    public User getModelAndSeries(String model, int series) {
        String hql = "select user from User user where user.car.model=:model and user.car.series=:series";
        try (Session session = sessionFactory.openSession()) {
            TypedQuery<User> query = session.createQuery(hql, User.class);
            query.setParameter("model", model);
            query.setParameter("series", series);
            return query.getSingleResult();
        } catch (Exception e) {
            System.out.println("Ошибка. Юзера с таким авто не существует");
            return new User();
        }
    }
}
