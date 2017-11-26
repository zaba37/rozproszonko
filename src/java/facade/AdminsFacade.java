/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package facade;

import entity.Admins;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

/**
 *
 * @author zaba3
 */
@Stateless
public class AdminsFacade extends AbstractFacade<Admins> {

    @PersistenceContext(unitName = "projektJSFPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public AdminsFacade() {
        super(Admins.class);
    }

    public Admins findByLoginAndPassword(String login, String password) {
        TypedQuery<Admins> q = em.createNamedQuery("Admins.findByLoginAndPassword", Admins.class);
        q.setParameter("login", login);
        q.setParameter("password", password);
        List<Admins> result = q.getResultList();

        if (result.size() != 0) {
            return result.get(0);
        } else {
            return null;
        }
    }
}
