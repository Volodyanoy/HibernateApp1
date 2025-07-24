package org.example.volodyanoy;

import org.example.volodyanoy.model.Person;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.util.List;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        Configuration configuration = new Configuration().addAnnotatedClass(Person.class);
        SessionFactory sessionFactory = configuration.buildSessionFactory();
        Session session = sessionFactory.getCurrentSession();

        try {
            session.beginTransaction();
            //List<Person> people = session.createQuery("FROM Person").getResultList();
            session.createQuery("delete from Person WHERE age < 30").executeUpdate();
            /*for(Person person: people){
                System.out.println(person);
            }*/
            session.getTransaction().commit();


        } finally {
            sessionFactory.close();
        }

    }
}
