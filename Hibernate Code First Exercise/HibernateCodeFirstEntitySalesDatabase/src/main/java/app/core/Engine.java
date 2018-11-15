package app.core;

import javax.persistence.EntityManager;
import java.io.BufferedReader;

public class Engine implements Runnable {
    BufferedReader reader;
    private EntityManager manager;


    public Engine(BufferedReader reader, EntityManager manager) {
        this.reader = reader;
        this.manager = manager;

    }

    @Override
    public void run() {

    }
}
