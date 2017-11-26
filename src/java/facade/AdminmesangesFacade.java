/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package facade;

import entity.Adminmesanges;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author zaba3
 */
@Stateless
public class AdminmesangesFacade extends AbstractFacade<Adminmesanges> {
    @PersistenceContext(unitName = "projektJSFPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public AdminmesangesFacade() {
        super(Adminmesanges.class);
    }
    
}
