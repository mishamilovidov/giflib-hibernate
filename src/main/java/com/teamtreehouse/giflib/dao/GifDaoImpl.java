package com.teamtreehouse.giflib.dao;

import com.teamtreehouse.giflib.model.Gif;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class GifDaoImpl implements GifDao {

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    @SuppressWarnings("unchecked")
    public List<Gif> findAll() {
        Session session = sessionFactory.openSession();
        List<Gif> gifs = session.createCriteria(Gif.class).list();
        session.close();
        return gifs;
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<Gif> searchByName(String queryString) {

        // Create a list to put search results in
        List<Gif> gifs = new ArrayList<>();

        // Get a list of all the GIFs in the DB
        Session session = sessionFactory.openSession();
        List<Gif> all_gifs = session.createCriteria(Gif.class).list();
        session.close();

        // Populate search results list based on query string
        for(Gif gif : all_gifs) {
            if(gif.getDescription().toLowerCase().contains(queryString.toLowerCase())) {
                gifs.add(gif);
            }
        }
        return gifs;
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<Gif> getFavorites() {
        Session session = sessionFactory.openSession();
        List<Gif> gifs = session.createCriteria(Gif.class).add(Restrictions.like("favorite", true)).list();
        session.close();

        return gifs;
    }

    @Override
    public Gif findById(Long id) {
        Session session = sessionFactory.openSession();
        Gif gif = session.get(Gif.class,id);
        session.close();
        return gif;
    }

    @Override
    @SuppressWarnings("Duplicates")
    public void save(Gif gif) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        session.saveOrUpdate(gif);
        session.getTransaction().commit();
        session.close();
    }

    @Override
    @SuppressWarnings("Duplicates")
    public void delete(Gif gif) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        session.delete(gif);
        session.getTransaction().commit();
        session.close();

    }
}
