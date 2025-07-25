package org.example.volodyanoy;

import org.example.volodyanoy.model.Item;
import org.example.volodyanoy.model.Person;
import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.util.List;


public class App
{
    public static void main( String[] args )
    {
        Configuration configuration = new Configuration()
                .addAnnotatedClass(Person.class).addAnnotatedClass(Item.class);

        SessionFactory sessionFactory = configuration.buildSessionFactory();

        try (sessionFactory) {
            Session session = sessionFactory.getCurrentSession();
            session.beginTransaction();

            Person person = session.get(Person.class, 1);
            System.out.println("Получили человека из таблицы");


            //Hibernate.initialize(person.getItems()); // Подгружаем связанные ленивые сущности

            session.getTransaction().commit(); //Также Hibernate автоматически вызывается session.close()

            System.out.println("Сессия закончилась (session.close)");

            //Открываем сессию и транзакцию еще раз (в любом другом месте кода)
            session = sessionFactory.getCurrentSession();
            session.beginTransaction();
            System.out.println("Внутри второй транзакции");
            person = (Person) session.merge(person);

            //Hibernate.initialize(person.getItems());
            List<Item> items = session.createQuery("select i from Item i where i.owner.id=:personId", Item.class).setParameter("personId", person.getId()).getResultList();
            System.out.println(items);
            session.getTransaction().commit();

            System.out.println("Вне второй сессии");

            // Many to many
            /*Удаляем у актера фильм, а у фильма актера
             (Предположим, что на самом деле актер не снимался в данном фильме)
            Actor actor = session.get(Actor.class, 2);
            System.out.println(actor.getMovies());
            Movie movieToRemove = actor.getMovies().get(0);
            actor.getMovies().remove(0);
            movieToRemove.getActors().remove(actor);*/
            /*Создаем фильм, указываем актера. Этому же актеру добавляем новый фильм
            Movie movie = new Movie("Reservoir Dogs", 1992);
            Actor actor = session.get(Actor.class, 1);
            movie.setActors(new ArrayList<>(Collections.singletonList(actor)));
            actor.getMovies().add(movie);
            session.save(movie);*/
            /*Выведем актеров фильма
            Movie movie = session.get(Movie.class, 1);
            System.out.println(movie.getActors());*/
            /*Выведем фильм, в котором снимался выбранный актер
            Actor actor = session.get(Actor.class, 1);
            System.out.println(actor.getMovies());*/
            /* Создаем фильм и два актера
            Movie movie = new Movie("Pulp fiction", 1994);
            Actor actor1 = new Actor("Harvey Keitel", 81);
            Actor actor2 = new Actor("Samuel L Jacjson", 72);

            movie.setActors(new ArrayList<>(List.of(actor1, actor2)));
            actor1.setMovies(new ArrayList<>(Collections.singletonList(movie)));
            actor2.setMovies(new ArrayList<>(Collections.singletonList(movie)));

            session.save(movie);
            session.save(actor1);
            session.save(actor2);*/

            //One to one добавление объектов в БД
            /*Person person = new Person("Test person", 30);
            Passport passport = new Passport(12345);
            person.setPassport(passport);
            session.save(person);*/
            /*Person person = session.get(Person.class, 2);
            session.remove(person);*/
            //One to many + Cascade
            /*Person person = new Person("Test cascading", 30);
            person.addItem(new Item("Test cascading item1"));
            person.addItem(new Item("Test cascading item2"));
            person.addItem(new Item("Test cascading item3"));
            session.save(person);*/
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

        }

    }
}
