package metier;

import dao.Idao;
import org.springframework.stereotype.Component;

@Component("metier")


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