package org.example.volodyanoy;

import org.example.volodyanoy.model.Item;
import org.example.volodyanoy.model.Person;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        Configuration configuration = new Configuration()
                .addAnnotatedClass(Person.class).addAnnotatedClass(Item.class);
        SessionFactory sessionFactory = configuration.buildSessionFactory();
        Session session = sessionFactory.getCurrentSession();

        try {
            session.beginTransaction();
            Person person = new Person("Test cascading", 30);
            person.addItem(new Item("Test cascading item1"));
            person.addItem(new Item("Test cascading item2"));
            person.addItem(new Item("Test cascading item3"));

            session.save(person);

            //Метод persist
            //session.persist(person); // т.к. настроено каскадирование Persist, то session.persist(item) сделает за нас Hibernate

            //Различные операции с данными с помощью Hibernate
            //Меняем владельца у товара
            /*Person person = session.get(Person.class, 4);
            Item item = session.get(Item.class, 1);
            item.getOwner().getItems().remove(item);
            item.setOwner(person);
            person.getItems().add(item);*/

            //Удаление человека
            /*Person person = session.get(Person.class ,2);
            session.remove(person);
            person.getItems().forEach(i -> i.setOwner(null));*/

            //Удаление всех товаров определенного человека
            /*Person person = session.get(Person.class, 3);
            List<Item> items = person.getItems();
            for(Item item: items)
                session.remove(item);
            person.getItems().clear();*/

            //Добавление товара и человека
            /*Person person = new Person("Test person", 30);
            Item newItem = new Item("Item from Hibernate 2", person);
            person.setItems(new ArrayList<>(Collections.singletonList(newItem)));
            session.save(person);
            session.save(newItem);*/


            //Добавление нового товара для человека. Добавляем в person новый товар,
            // т.к. в кэше Hibernate может быть старая версия person
            /*Person person = session.get(Person.class, 2);
            Item newItem = new Item("Item from Hibernate", person);
            //гарантируем синхронизацию данных в сущностях Hibernate и БД
            person.getItems().add(newItem);
            session.save(newItem);*/

            //Получение товара и его владельца
            /*Item item = session.get(Item.class, 5);
            System.out.println(item);
            Person person = item.getOwner();
            System.out.println(person);*/

            //Получение человека и списка его товаров
            /*Person person = session.get(Person.class, 3);
            System.out.println(person);
            List<Item> items = person.getItems();
            System.out.println(items);*/

            session.getTransaction().commit();

        } finally {
            sessionFactory.close();
        }

    }
}
