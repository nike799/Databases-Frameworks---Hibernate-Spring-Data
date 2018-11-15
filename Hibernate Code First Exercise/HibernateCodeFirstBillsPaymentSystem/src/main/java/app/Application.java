package app;

import app.entities.BankAccount;
import app.entities.BillingDetail;
import app.entities.CreditCard;
import app.entities.User;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.time.LocalDate;
import java.util.HashSet;

public class Application {
    public static void main(String[] args) {
        EntityManagerFactory factory = Persistence.createEntityManagerFactory("hibernate_code_first");
        EntityManager manager = factory.createEntityManager();
        // TEST PERSIST ENTITY
//         User user = new User("Pesho","Peshev","peshev@gmail.com","123456",new HashSet<>());
//         LocalDate startDate = LocalDate.of(2018, 9, 18);
//         LocalDate endDate = LocalDate.of(2020, 9, 18);
//         BillingDetail creditCard = new CreditCard(user,"123456","Visa",startDate,endDate);
//         BillingDetail bankAccount = new BankAccount(user,"125874","Bulbank","AAAA");
//         manager.getTransaction().begin();
//         manager.persist(user);
//         manager.persist(creditCard);
//         manager.persist(bankAccount);
//         manager.getTransaction().commit();
        manager.close();
    }
}
