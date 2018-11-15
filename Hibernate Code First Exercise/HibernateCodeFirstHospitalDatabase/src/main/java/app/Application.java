package app;


import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class Application {
    public static void main(String[] args) {
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("hibernate_code_first");
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.close();
    }
}
