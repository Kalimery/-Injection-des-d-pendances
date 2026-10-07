package dao;

import org.springframework.stereotype.Component;

@Component("dao")

public class IdaoImpl implements Idao {
    @Override
    public double getData() {
        System.out.println("Version base de données");
        return 12.5;
    }
}

