package com.wayoup.server.core.component;

import com.tinkerpop.blueprints.impls.orient.OrientGraphFactory;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by Marco on 02/06/2015.
 */
public class OrientDb {

    private static final Logger logger = Logger.getLogger(OrientDb.class.getName());

    private static OrientDb instance;
    private OrientGraphFactory factory;

    private OrientDb(String address, String username, String password) {
        factory = new OrientGraphFactory(address, username, password);
    }

    public static OrientGraphFactory getFactory(String address, String username, String password) {
        if (instance==null) {
            instance = new OrientDb(address, username, password);
            logger.log(Level.INFO, "Created OrientGraphFactory instance");
        }
        return instance.factory;
    }

}
