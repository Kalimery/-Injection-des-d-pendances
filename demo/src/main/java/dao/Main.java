package dao;

import java.io.InputStream;
import java.util.Properties;

import metier.Imetier;
import metier.ImetierImpl;

public class Main {
    public static void main(String[] args) throws Exception {
        injectionStatique();
        injectionDynamique();
    }

    private static void injectionStatique() {
        Idao dao = new IdaoImpl();
        Imetier metier = new ImetierImpl(dao);

        System.out.println("Injection par instanciation statique : " + metier.calcul());
    }

    private static void injectionDynamique() throws Exception {
        Properties properties = new Properties();
        try (InputStream input = Main.class.getResourceAsStream("/config.properties")) {
            if (input == null) {
                throw new IllegalStateException("Le fichier config.properties est introuvable.");
            }
            properties.load(input);
        }

        String daoClassName = properties.getProperty("dao");
        String metierClassName = properties.getProperty("metier");

        Idao dao = (Idao) Class.forName(daoClassName)
                .getDeclaredConstructor()
                .newInstance();
        Imetier metier = (Imetier) Class.forName(metierClassName)
                .getConstructor(Idao.class)
                .newInstance(dao);

        System.out.println("Injection par instanciation dynamique : " + metier.calcul());
    }
}