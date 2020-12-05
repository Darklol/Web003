package ru.itmo.web.DataBase;

import org.hibernate.*;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import ru.itmo.web.model.Point;

import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import java.io.Serializable;
import java.util.List;
import java.util.function.Consumer;

@ManagedBean(name = "dao")
@ApplicationScoped
public class DataBaseStorage implements PointsRepository, Serializable {


    private final SessionFactory sessionFactory;

    public DataBaseStorage() {
        Configuration configuration = new Configuration().configure();
        configuration.addAnnotatedClass(Point.class);
        ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
                .applySettings(configuration.getProperties()).build();
        sessionFactory = configuration.buildSessionFactory(serviceRegistry);
    }

    @Override
    public void addPoint(Point point) {
        openTransactionFor(session -> session.save(point));
    }

    @Override
    public void removePoint(Point Point) {
        openTransactionFor(session -> session.delete(Point));
    }

    @Override
    public void updatePoint(Point Point) {
        //maybe later
    }

    @Override
    public List<Point> getAllPoints() {
        Session session = sessionFactory.openSession();
        List res = session.createQuery("from Point").list();
        session.close();
        return res;
    }

    private void openTransactionFor(Consumer<Session> action) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        action.accept(session);
        transaction.commit();
        session.close();
    }
}