package com.example.demo;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;

public class ParentChildService {

    public void createAndStoreParentWithChildren(String parentName, List<String> childNames) {
        try (EntityManager entityManager = HibernateUtil.getEntityManagerFactory().createEntityManager()) {
            EntityTransaction transaction = entityManager.getTransaction();
            transaction.begin();

            // Create Parent
            Parent parent = new Parent();
            parent.setName(parentName);

            // Create Children and associate with Parent
            List<Child> children = new ArrayList<>();
            for (String childName : childNames) {
                Child child = new Child();
                child.setName(childName);
                child.setParent(parent);
                children.add(child);
            }
            parent.setChildren(children);

            // Persist Parent (Children will be persisted due to CascadeType.ALL)
            entityManager.persist(parent);

            transaction.commit();
        } catch (Exception e) {
            System.err.println("Error occurred: " + e.getMessage());
        }
    }

    public List<Parent> getAllParents() {
        try (EntityManager entityManager = HibernateUtil.getEntityManagerFactory().createEntityManager()) {
            return entityManager.createQuery("FROM Parent", Parent.class).getResultList();
        }
    }

    public List<Child> getChildrenByParentId(Long parentId) {
        try (EntityManager entityManager = HibernateUtil.getEntityManagerFactory().createEntityManager()) {
            return entityManager.createQuery("FROM Child WHERE parent.id = :parentId", Child.class)
                    .setParameter("parentId", parentId)
                    .getResultList();
        }
    }
}