package tp.project.goGame.state;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;

import exceptions.AccountExistsException;
import tp.project.goGame.shared.Account;

public class DataBaseConnector {
	
	private static DataBaseConnector instance;
	
	public static DataBaseConnector getInstance(){
		if(instance == null)
		{
			synchronized(DataBaseConnector.class)
			{
				if(instance == null)
					instance = new DataBaseConnector();
			}
		}
		return instance;
	}
	
	public static SessionFactory getSessionFactory() {
		Configuration configuration = new Configuration().configure();
		StandardServiceRegistryBuilder builder = new StandardServiceRegistryBuilder()
				.applySettings(configuration.getProperties());
		SessionFactory sessionFactory = configuration
				.buildSessionFactory(builder.build());
		return sessionFactory;
	}
	
	public static Integer create(Account e) throws AccountExistsException {
		Session session = getSessionFactory().openSession();
		List<Account> Accounts = session.createQuery("FROM Account").list();
		for(Account a: Accounts)
		{
			if(a.getUsername().equals(e.getUsername()))
				throw new AccountExistsException(e.getUsername());
			if(a.getEmail().equals(e.getEmail()))
				throw new AccountExistsException(e.getEmail());
			if(a.getNickname().equals(e.getNickname()))
				throw new AccountExistsException(e.getNickname());
		}
		session.beginTransaction();
		session.save(e);
		session.getTransaction().commit();
		session.close();
		System.out.println("Successfully created " + e.toString());
		return e.getId();

	}

	public static List<Account> read() {
		Session session = getSessionFactory().openSession();
		@SuppressWarnings("unchecked")
		List<Account> Accounts = session.createQuery("FROM Account").list();
		session.close();
		System.out.println("Found " + Accounts.size() + " Accounts");
		return Accounts;

	}

	public static void update(Account e) {
		Session session = getSessionFactory().openSession();
		session.beginTransaction();
		Account em = (Account) session.load(Account.class, e.getId());
		//TODO update
		session.getTransaction().commit();
		session.close();
		System.out.println("Successfully updated " + e.toString());

	}

	public static void delete(Integer id) {
		Session session = getSessionFactory().openSession();
		session.beginTransaction();
		Account e = findByID(id);
		session.delete(e);
		session.getTransaction().commit();
		session.close();
		System.out.println("Successfully deleted " + e.toString());

	}

	public static Account findByID(Integer id) {
		Session session = getSessionFactory().openSession();
		Account e = (Account) session.load(Account.class, id);
		session.close();
		return e;
	}
	
	public static void deleteAll() {
		Session session = getSessionFactory().openSession();
		session.beginTransaction();
		Query query = session.createQuery("DELETE FROM Account ");
		query.executeUpdate();
		session.getTransaction().commit();
		session.close();
		System.out.println("Successfully deleted all Accounts.");
	}
}
