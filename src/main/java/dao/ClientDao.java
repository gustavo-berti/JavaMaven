package dao;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import model.Client;
import model.Transaction;

public class ClientDao {
	protected EntityManager em;

	public ClientDao() {
		em = getEntityManager();
	}

	private EntityManager getEntityManager() {
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("nomePU");
		if (em == null) {
			em = emf.createEntityManager();
		}
		return em;
	}

	public Client insert(Client client) {
		try {
			em.getTransaction().begin();
			em.persist(client);
			em.getTransaction().commit();
			em.close();
			return null;
		} catch (Exception e) {
			em.getTransaction().rollback();
			em.close();

			return null;
		}
	}

	public Client update(Client client) {
		Client bankClient = null;
		try {
			if (client.getId() != null) {
				em.getTransaction().begin();

				bankClient = findById(client.getId());
				if (bankClient != null) {
					em.merge(bankClient);
				}

				em.getTransaction().commit();
				em.close();
			}
			return bankClient;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public void delete(Long id) {
		try {
			em.getTransaction().begin();
			Client bankClient = findById(id);
			if (bankClient != null) {
				em.remove(bankClient);
			}
			em.getTransaction().commit();
			em.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private Client findById(Long id) {
		Client client = em.find(Client.class, id);
		em.close();
		return client;
	}

	public Client getClient(Long id) {
		try {
			Client client = em.createQuery("FROM Client WHERE id = '" + id + "'", Client.class).getSingleResult();
			em.close();
			return client;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
}
