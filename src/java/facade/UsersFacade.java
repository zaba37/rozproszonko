/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package facade;

import entity.Users;
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
public class UsersFacade extends AbstractFacade<Users> {

    @PersistenceContext(unitName = "projektJSFPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public UsersFacade() {
        super(Users.class);
    }

    public Users findByLoginAndPassword(String login, String password) {
        TypedQuery<Users> q = em.createNamedQuery("Users.findByLoginAndPassword", Users.class);
        q.setParameter("login", login);
        q.setParameter("password", password);
        List<Users> result = q.getResultList();

        if (result.size() != 0) {
            return result.get(0);
        } else {
            return null;
        }
    }

    public Users FindByLogin(String login) {
        TypedQuery<Users> q = em.createNamedQuery("Users.findByLogin", Users.class);
        q.setParameter("login", login);
        List<Users> result = q.getResultList();

        if (result.size() != 0) {
            return result.get(0);
        } else {
            return null;
        }
    }

}
