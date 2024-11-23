package dao;

import javax.persistence.EntityManager;

import model.Client;

public class ClientDao extends GenericDao<Client> {
	
	public ClientDao() {
		super(Client.class);
	}

	public Client getClient(Long id) {
		EntityManager em = getEntityManager();
		try {
			Client client = em.createQuery("FROM Client WHERE id = '" + id + "'", Client.class).getSingleResult();
			em.close();
			return client;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public Client getClientByCpf(String nextLine) {
		EntityManager em = getEntityManager();
		try {
			Client client = em.createQuery("FROM Client WHERE cpf = '" + nextLine + "'", Client.class).getSingleResult();
			em.close();
			return client;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
}
