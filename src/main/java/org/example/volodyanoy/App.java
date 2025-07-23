package org.example.volodyanoy;

import org.example.volodyanoy.model.Person;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

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

            Person person = new Person("Some name", 33);
            session.save(person);
            //Person person = session.get(Person.class, 2);
            //person.setName("New name");
            //session.delete(person);

            session.getTransaction().commit();

            System.out.println(person.getId());
        } finally {
            sessionFactory.close();
        }

    }
}
