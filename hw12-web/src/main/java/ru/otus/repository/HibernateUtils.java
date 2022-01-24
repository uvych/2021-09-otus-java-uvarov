package ru.otus.repository;

import org.hibernate.*;
import org.hibernate.boot.*;
import org.hibernate.boot.registry.*;
import org.hibernate.cfg.*;

import java.util.*;

public final class HibernateUtils {

    private HibernateUtils() {
    }

    public static SessionFactory buildSessionFactory(Configuration configuration, Class<?>... annotatedClasses) {
        MetadataSources metadataSources = new MetadataSources(createServiceRegistry(configuration));
        Arrays.stream(annotatedClasses).forEach(metadataSources::addAnnotatedClass);

        Metadata metadata = metadataSources.getMetadataBuilder().build();
        return metadata.getSessionFactoryBuilder().build();
    }

    private static StandardServiceRegistry createServiceRegistry(Configuration configuration) {
        return new StandardServiceRegistryBuilder()
                .applySettings(configuration.getProperties()).build();
    }
}
