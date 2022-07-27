/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package m2i.userauthentication;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.ws.rs.BadRequestException;
import javax.ws.rs.NotFoundException;

/**
 *
 * @author elouf
 */
public class Dao {
   
    
    
      public List<User> findAll(){
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("TpRestUnit");
        EntityManager entityManager = emf.createEntityManager();
        Query findAllQuery = entityManager.createQuery("select u from User u");
        return findAllQuery.getResultList();
    }
      
      
   
   
    
    public User findById(int id) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("TpRestUnit");
        EntityManager entityManager = emf.createEntityManager();
        User utilisateurFound = entityManager.find(User.class, id);

        if (utilisateurFound == null) {
            System.out.println("Attention le utilisateur avec l'id: " + id + " n'existe pas !");
        }

        return utilisateurFound;
    }

    public void create(User utilisateurToCreate) throws BadRequestException {
        // On vérifie les données que l'on reçoit en paramètre
        if (utilisateurToCreate == null) {
            System.out.println("L'objet utilisateur ne peut pas être null");
            return;
        }
        
        if (utilisateurToCreate.hasAFieldEmpty()) {
            throw new BadRequestException("All the fields must be filled");
        }

        EntityManagerFactory emf = Persistence.createEntityManagerFactory("TpRestUnit");
        EntityManager entityManager = emf.createEntityManager();

        // On déclare notre transaction avec pour valeur par défaut null
        EntityTransaction tx = null;

        try {
            tx = entityManager.getTransaction();
            tx.begin();

            entityManager.persist(utilisateurToCreate);

            tx.commit();
        } catch (Exception e) {
            System.out.println("Une erreur est survenu lors de la création");
            if (tx != null) {
                // Une erreur est survenue, on discard les actions entamés dans la transaction
                tx.rollback();
            }
            throw e;
        }
    }

    
    public void update(int id, User utilisateurData) throws NotFoundException {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("TpRestUnit");
        EntityManager entityManager = emf.createEntityManager();
        
        // On récupère le utilisateur qu'on souhaite modifier
        User utilisateurToUpdate = entityManager.find(User.class, id);

        // Si le utilisateur n'existe pas on ne fait pas d'update
        if (utilisateurToUpdate == null) {
            throw new NotFoundException("L'utilisateur avec l'id:" + id + " n'existe pas");
        }

        // on set les données uniquement si elle ne sont pas null
        utilisateurToUpdate.copy(utilisateurData);

        EntityTransaction tx = null;

        try {
            tx = entityManager.getTransaction();
            tx.begin();
            entityManager.merge(utilisateurToUpdate);
            tx.commit();
        } catch (Exception e) {
            System.out.println("Une erreur est survenu lors de la modification");
            if (tx != null) {
                tx.rollback();
            }
            throw e;
        }
    }

    public void delete(int id) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("TpRestUnit");
        EntityManager entityManager = emf.createEntityManager();
        
        // On récupère le utilisateur qu'on souhaite supprimer
        User utilisateurToDelete = entityManager.find(User.class, id);

        if (utilisateurToDelete == null) {
            throw new NotFoundException("L'utilisateur avec l'id:" + id + " n'existe pas");
        }

        EntityTransaction tx = null;

        try {
            tx = entityManager.getTransaction();
            tx.begin();
            entityManager.remove(utilisateurToDelete);
            tx.commit();
        } catch (Exception e) {
            System.out.println("Une erreur est survenu lors de la suppresion");
            if (tx != null) {
                tx.rollback();
            }
            throw e;
        }
    }
    
    public List<User> search(String query, int count) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("TpRestUnit");
        EntityManager entityManager = emf.createEntityManager();
        
        Query searchQuery = entityManager.createQuery("select u from User u where u.lastname like :query or u.email like :query");

        searchQuery.setParameter("query", "%" + query + "%");
        searchQuery.setMaxResults(count);
        
        return searchQuery.getResultList();
    }
    
}
