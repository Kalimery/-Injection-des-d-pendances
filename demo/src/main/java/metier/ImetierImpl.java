package metier;

import dao.Idao;

public class ImetierImpl implements Imetier {
    private final Idao dao;

    public ImetierImpl(Idao dao) {
        this.dao = dao;
    }

    @Override
    public double calcul() {
        return dao.getData();
    }
}