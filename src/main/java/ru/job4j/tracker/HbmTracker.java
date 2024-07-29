package ru.job4j.tracker;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

import java.sql.SQLException;
import java.util.List;

public class HbmTracker implements Store, AutoCloseable {

    private final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
            .configure().build();
    private final SessionFactory sf = new MetadataSources(registry)
            .buildMetadata().buildSessionFactory();

    @Override
    public void close() throws Exception {
        StandardServiceRegistryBuilder.destroy(registry);
    }

    @Override
    public Item add(Item item) throws Exception {
        Session session = sf.openSession();
        try {
            session.beginTransaction();
            session.save(item);
            session.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            session.close();
        }
        return item;
    }

    @Override
    public boolean replace(int id, Item item) throws Exception {
        Session session = sf.openSession();
        try {
            session.beginTransaction();
            session.createQuery("update from Item set name = :fName where id = :fId")
                    .setParameter("fId", id)
                    .setParameter("fName", item.getName())
                    .executeUpdate();
            session.getTransaction().commit();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            session.close();
        }
        return false;
    }

    @Override
    public boolean delete(int id) throws Exception {
        Session session = sf.openSession();
        try {
            session.beginTransaction();
            session.createQuery("delete from Item where id = :fId")
                    .setParameter("fId", id)
                    .executeUpdate();
            session.getTransaction().commit();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            session.close();
        }
        return false;
    }

    @Override
    public List<Item> findAll() throws Exception {
        List<Item> itemList;
        Session session = sf.openSession();
        try {
            session.beginTransaction();
            itemList = session.createQuery("from Item order by id", Item.class).getResultList();
            session.getTransaction().commit();
            return List.copyOf(itemList);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            session.close();
        }
        return List.of();
    }

    @Override
    public List<Item> findByName(String key) throws SQLException {
        List<Item> itemList;
        Session session = sf.openSession();
        session.beginTransaction();
        itemList = session.createQuery("from Item where name = :fKey", Item.class)
                .setParameter("fKey", key)
                .getResultList();
        session.getTransaction().commit();
        return List.copyOf(itemList);
    }

    @Override
    public Item findById(int id) throws SQLException {
        Item item = null;
        Session session = sf.openSession();
        try {
            session.beginTransaction();
            item = session.createQuery("from Item where id = :fId", Item.class)
                    .setParameter("fId", id)
                    .getSingleResult();
            session.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            session.close();
        }
        return item;
    }

}
