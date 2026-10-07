# Activité Pratique N°1 : Injection des dépendances

**Étudiante :** Meriem Fennassi Ouaalani  
**Enseignant :** Mohamed YOUSSFI

## Objectif

Mettre en pratique l'injection des dépendances avec une couche DAO et une couche métier qui communiquent par des interfaces (couplage faible).

## Structure du projet

```
demo/
├── pom.xml
└── src/main/
    ├── java/
    │   ├── dao/     (Idao, IdaoImpl, Main)
    │   └── metier/  (Imetier, ImetierImpl)
    └── resources/
        ├── config.properties
        └── config.xml
```

## Couplage faible

- `Idao` : interface avec la méthode `getData()`.
- `IdaoImpl` : implémentation du DAO.
- `Imetier` : interface avec la méthode `calcul()`.
- `ImetierImpl` : implémentation du métier. Elle ne connaît que l'interface `Idao`, qui est injectée par le constructeur.

```java
public class ImetierImpl implements Imetier {
    private final Idao dao;

    public ImetierImpl(Idao dao) {
        this.dao = dao;
    }

    @Override
    public double calcul() {
        return dao.getData() * 2;
    }
}
```

## Les 4 types d'injection

### 1. Instanciation statique
Les objets sont créés avec `new`. Pour changer d'implémentation, il faut modifier le code.

```java
Idao dao = new IdaoImpl();
Imetier metier = new ImetierImpl(dao);
```

### 2. Instanciation dynamique
Les noms des classes sont lus dans `config.properties` et les objets sont créés par réflexion, sans recompiler.

```properties
dao=dao.IdaoImpl
metier=metier.ImetierImpl
```

### 3. Spring avec XML
Les beans sont déclarés dans `config.xml`.

```xml
<bean id="dao" class="dao.IdaoImpl"/>
<bean id="metier" class="metier.ImetierImpl">
    <constructor-arg ref="dao"/>
</bean>
```

```java
ApplicationContext context = new ClassPathXmlApplicationContext("config.xml");
Imetier metier = (Imetier) context.getBean("metier");
```

### 4. Spring avec annotations
Les classes sont annotées avec `@Component("dao")` et `@Component("metier")`. Spring les trouve en scannant les packages.

```java
ApplicationContext context = new AnnotationConfigApplicationContext("dao", "metier");
Imetier metier = context.getBean(Imetier.class);
```

## Exécution

```
Version base de données
Injection par instanciation statique : 25.0
Version base de données
Injection par instanciation dynamique : 25.0
Version base de données
Injection Spring (XML) : 25.0
Version base de données
Injection Spring (annotations) : 25.0
```

![Exécution](images/execution.png)

## Conclusion

L'injection des dépendances sépare la création des objets de leur utilisation. Grâce aux interfaces, le code est plus facile à maintenir et à faire évoluer. Spring automatise cette gestion avec son conteneur IoC, par XML ou par annotations.