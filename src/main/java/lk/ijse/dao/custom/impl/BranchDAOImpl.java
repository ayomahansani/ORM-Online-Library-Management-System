package lk.ijse.dao.custom.impl;

import lk.ijse.config.FactoryConfiguration;
import lk.ijse.dao.custom.BranchDAO;
import lk.ijse.entity.Branch;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.List;

public class BranchDAOImpl implements BranchDAO {

    @Override
    public Branch getBranchByAddress(String branchAddress) {

        Session session = FactoryConfiguration.getFactoryConfiguration().getSession();
        Transaction transaction = session.beginTransaction();

        String hql = "FROM Branch WHERE branchAddress=:address";
        Query query = session.createQuery(hql);
        query.setParameter("address", branchAddress);

        Branch branch = (Branch) query.uniqueResult();

        transaction.commit();
        session.close();

        return branch;
    }

    @Override
    public List<Branch> getAll() {

        Session session = FactoryConfiguration.getFactoryConfiguration().getSession();
        Transaction transaction = session.beginTransaction();

        String hql = "FROM Branch";
        Query query = session.createQuery(hql);

        List<Branch> branches = query.list();

        transaction.commit();
        session.close();

        return branches;
    }

    @Override
    public int setCurrentNumber() {

        Session session = FactoryConfiguration.getFactoryConfiguration().getSession();
        Transaction transaction = session.beginTransaction();

        String hql = "SELECT COUNT(branchId) FROM Branch";
        Query query = session.createQuery(hql);

        Long count = (Long) query.uniqueResult();

        transaction.commit();
        session.close();

        return Math.toIntExact(count);  // convert a long value to an int value
    }

    @Override
    public boolean save(Branch branch) {

        Session session = FactoryConfiguration.getFactoryConfiguration().getSession();
        Transaction transaction = session.beginTransaction();

        session.save(branch);

        transaction.commit();
        session.close();

        return true;
    }

    @Override
    public boolean update(Branch branch) {

        Session session = FactoryConfiguration.getFactoryConfiguration().getSession();
        Transaction transaction = session.beginTransaction();

        session.update(branch);

        transaction.commit();
        session.close();

        return true;
    }

    @Override
    public boolean delete(String id) {

        Session session = FactoryConfiguration.getFactoryConfiguration().getSession();
        Transaction transaction = session.beginTransaction();

        Branch branch = session.get(Branch.class, id);

        session.delete(branch);

        transaction.commit();
        session.close();

        return true;
    }

    @Override
    public Branch search(String branchAddress) {

        Session session = FactoryConfiguration.getFactoryConfiguration().getSession();
        Transaction transaction = session.beginTransaction();

        String hql = "FROM Branch WHERE branchAddress=:address";
        Query query = session.createQuery(hql);
        query.setParameter("address", branchAddress);

        Branch branch = (Branch) query.uniqueResult();

        transaction.commit();
        session.close();

        return branch;

    }

    @Override
    public String generateNextId() {

        Session session = FactoryConfiguration.getFactoryConfiguration().getSession();
        Transaction transaction = session.beginTransaction();

        String hql = "SELECT branchId FROM Branch ORDER BY branchId DESC LIMIT 1";
        Query query = session.createQuery(hql);

        String id = (String) query.uniqueResult();

        transaction.commit();
        session.close();

        return splitId(id);
    }

    @Override
    public String splitId(String currentBranchId) {

        if(currentBranchId != null) {
            String[] split = currentBranchId.split("Br0");
            int id = Integer.parseInt(split[1]);
            id++;
            if(id < 10) {
                return "Br00" + id;
            }else {
                return "Br0" + id;
            }
        } else {
            return "Br001";
        }
    }

}
