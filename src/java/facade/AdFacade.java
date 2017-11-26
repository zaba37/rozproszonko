/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package facade;

import entity.Ad;
import java.util.ArrayList;
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
public class AdFacade extends AbstractFacade<Ad> {

    @PersistenceContext(unitName = "projektJSFPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public AdFacade() {
        super(Ad.class);
    }

    public ArrayList<Ad> FindByOwnerId(int ownerID) {
        TypedQuery<Ad> q = em.createNamedQuery("Ad.findByOwnerId", Ad.class);
        q.setParameter("id", ownerID);
        List<Ad> result = q.getResultList();

        if (result.size() != 0) {
            ArrayList<Ad> resultList = new ArrayList();
            resultList.addAll(result);
            return resultList;
        } else {
            return null;
        }
    }

}
