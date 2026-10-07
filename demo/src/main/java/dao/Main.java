package dao;

import java.io.InputStream;
import java.util.Properties;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import metier.Imetier;
import metier.ImetierImpl;

public class Main {

    public static void main(String[] args) throws Exception {
        injectionStatique();
        injectionDynamique();
        injectionSpringXml();
        injectionSpringAnnotations();
    }

    // 1. Injection statique
    private static void injectionStatique() {

        Idao dao = new IdaoImpl();

        Imetier metier = new ImetierImpl(dao);

        System.out.println(
            "Injection par instanciation statique : "
            + metier.calcul()
        );
    }

    // 2. Injection dynamique
    private static void injectionDynamique() throws Exception {

        Properties properties = new Properties();

        try (InputStream input =
                Main.class.getResourceAsStream("/config.properties")) {

            if (input == null) {
                throw new IllegalStateException(
                    "Le fichier config.properties est introuvable."
                );
            }

            properties.load(input);
        }

        String daoClassName =
                properties.getProperty("dao");

        String metierClassName =
                properties.getProperty("metier");

        Idao dao = (Idao) Class
                .forName(daoClassName)
                .getDeclaredConstructor()
                .newInstance();

        Imetier metier = (Imetier) Class
                .forName(metierClassName)
                .getConstructor(Idao.class)
                .newInstance(dao);

        System.out.println(
            "Injection par instanciation dynamique : "
            + metier.calcul()
        );
    }

    // 3. Injection Spring avec XML
    private static void injectionSpringXml() {

        ApplicationContext context =
                new ClassPathXmlApplicationContext("config.xml");

        Imetier metier =
                (Imetier) context.getBean("metier");

        System.out.println(
            "Injection Spring (XML) : "
            + metier.calcul()
        );
    }


    private static void injectionSpringAnnotations() {

        ApplicationContext context =
                new AnnotationConfigApplicationContext(
                    "dao",
                    "metier"
                );

        Imetier metier =
                context.getBean(Imetier.class);

        System.out.println(
            "Injection Spring (annotations) : "
            + metier.calcul()
        );
    }
}