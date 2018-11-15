package app;

import app.core.Engine;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        EntityManagerFactory factory = Persistence.createEntityManagerFactory("exercise_db");
        EntityManager manager = factory.createEntityManager();
        Engine engine = new Engine(reader, manager);
        engine.run();
        manager.close();
    }
}

