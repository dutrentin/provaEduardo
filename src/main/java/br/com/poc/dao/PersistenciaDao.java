package br.com.poc.dao;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.OptimisticLockException;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import javax.persistence.Query;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.google.common.base.Function;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Iterables;

import br.com.poc.exception.LockException;
import br.com.poc.util.Paginacao;


/**
 * Classe responsável por gerenciar as ação da apli cação com o banco de dados
 * 
 * @author jose
 *
 * @param <T>
 * 
 *        Copyright notice (c) 2021 ASQ
 * 
 */


public class PersistenciaDao<T>  implements Serializable {

	private static final long serialVersionUID = -3480403392475173995L;

	private static final Logger log = Logger.getLogger(PersistenciaDao.class);

	@PersistenceContext
	private EntityManager entityManager;

	private Class<T> classePersistencia;
	
	@Autowired
	@Qualifier("flushBase" )
	private String flushBase;
	
	public EntityManager getEntityManager() {
		return entityManager;
	}

	
	@SuppressWarnings("unchecked")
	public PersistenciaDao() {
		super();
		this.classePersistencia = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
	}


	private void verifyFlush() {
		if (this.flushBase != null && this.flushBase.equalsIgnoreCase("true")) {
			this.entityManager.flush();
		}
	}

	
	public void save(T obj) {
		try {
			this.entityManager.persist(obj);
			verifyFlush();
		} catch (OptimisticLockException e) {
			log.error(e);
			throw new LockException(e);
		} catch (PersistenceException e) {
			log.error(e);
			throw e;
		}
	}

	public void detach(T obj) {
		try {
			this.entityManager.detach(obj);
		} catch (OptimisticLockException e) {
			log.error(e);
			throw new LockException(e);
		} catch (PersistenceException e) {
			log.error(e);
			throw e;
		}
	}

	
	public void saveAll(List<T> list) {	
		try {
			for (T obj : list) {
				this.entityManager.persist(obj);
			}
			verifyFlush();
		} catch (OptimisticLockException e) {
			log.error(e);
			throw new LockException(e);
		} catch (PersistenceException e) {
			log.error(e);
			throw e;
		}
	}

	/**
	 * Salva em batch uma lista de entidades.
	 *
	 * @param entities
	 * @param batchSize
	 * @return
	 */
	public List<T> saveInBatches(final Iterable<T> entities, final int batchSize) {
		log.debug("Executando insert em batch. Tamanho do lote: " + batchSize);
		return ImmutableList.copyOf(Iterables.concat(Iterables.transform(Iterables.partition(entities, batchSize),
				new Function<List<? extends T>, Iterable<? extends T>>() {
					
					public Iterable<? extends T> apply(final List<? extends T> input) {
						List<T> saved = saveBatch(input);
						entityManager.flush();
						return saved;
					}
				})));
	}

	public List<T> updateInBatches(final Iterable<T> entities, final int batchSize) {
		log.debug("Executando merge em batch. Tamanho do lote: " + batchSize);
		return ImmutableList.copyOf(Iterables.concat(Iterables.transform(Iterables.partition(entities, batchSize),
				new Function<List<? extends T>, Iterable<? extends T>>() {
					
					public Iterable<? extends T> apply(final List<? extends T> input) {
						List<T> saved = updateBatch(input);
						entityManager.flush();
						return saved;
					}
				})));
	}

	/**
	 * Executa o persist para um bloco de entidades, retornando para ser enviado
	 * para o banco de dados.
	 *
	 * @param entities
	 * @return
	 */
	private List<T> saveBatch(Iterable<? extends T> entities) {
		List<T> result = new ArrayList<T>();
		for (T entity : entities) {
			entityManager.persist(entity);
			result.add(entity);
		}
		return result;
	}

	private List<T> updateBatch(Iterable<? extends T> entities) {
		List<T> result = new ArrayList<T>();
		for (T entity : entities) {
			entityManager.merge(entity);
			result.add(entity);
		}
		return result;
	}

	
	public void update(T obj) {
		try {
			this.entityManager.merge(obj);
			verifyFlush();
		} catch (OptimisticLockException e) {
			log.error(e);
			throw new LockException(e);
		} catch (PersistenceException e) {
			log.error(e);
			throw e;
		}
	}

	
	public void delete(Serializable id) {
		try {
			this.entityManager.remove(findById(id));
			verifyFlush();
		} catch (OptimisticLockException e) {
			log.error(e);
			throw new LockException(e);
		} catch (PersistenceException e) {
			log.error(e);
			throw e;
		}
	}

	
	public Integer count() {
		String jpql = "SELECT count(t) FROM " + getclassePersistencia() + " t ";
		Long count = (Long) this.entityManager.createQuery(jpql).getSingleResult();
		return count.intValue();
	}

	@SuppressWarnings("unchecked")
	
	public List<T> findAll() {
		String sql = "SELECT t FROM " + getclassePersistencia().getSimpleName() + " t ";
		return this.entityManager.createQuery(sql).getResultList();
	}

	
	public T findById(Serializable id) {
		return this.entityManager.find(classePersistencia, id);
	}

	
	@SuppressWarnings("unchecked")
	public T findByNativeQuerySingleResult(String queryStr, Object... params) {
		log.debug("Executando a query nativa: " + queryStr);
		Query query = this.entityManager.createNativeQuery(queryStr, classePersistencia);
		setQueryParams(query, params);
		return (T) query.getSingleResult();
	}

	
	public List<?> find(String queryStr, Object... params) {
		log.debug("Executando a JPQL query: " + queryStr);
		Query query = this.entityManager.createQuery(queryStr, classePersistencia);
		setQueryParams(query, params);
		return query.getResultList();
	}

	@SuppressWarnings("unchecked")
	public T findByQueryAndParametros(String queryStr, Object... params) {
		log.debug("Executando a JPQL query: " + queryStr);
		Query query = this.entityManager.createQuery(queryStr, classePersistencia);
		setQueryParams(query, params);
		return (T) query.getSingleResult();
	}

	
	@SuppressWarnings({ "unchecked" })
	public List<T> findByNamedParams(String queryname, Map<String, Object> params) {
		log.debug("Executando a named query: " + queryname);
		Query query = this.entityManager.createNamedQuery(queryname);
		setQueryMapParams(params, query);
		return query.getResultList();
	}

	
	public List<?> findByNamedParamsHql(String queryName, Map<String, Object> params) {
		log.debug("Executando a named query: " + queryName);
		Query query = this.entityManager.createNamedQuery(queryName);
		setQueryMapParams(params, query);
		return query.getResultList();
	}

	
	@SuppressWarnings("unchecked")
	public T findByNativeQueryParams(String sql, Map<String, Object> params) {
		log.debug("Executando a native query: " + sql);
		Query query = this.entityManager.createNativeQuery(sql, classePersistencia);
		setQueryMapParams(params, query);
		return (T) query.getSingleResult();
	}

	
	@SuppressWarnings("unchecked")
	public List<T> findByNativeQuery(String sql, Object... params) {
		log.debug("Executando a native query: " + sql);
		Query query = this.entityManager.createNativeQuery(sql, classePersistencia);
		setQueryParams(query, params);
		return query.getResultList();
	}

	@SuppressWarnings("unchecked")
	
	public List<T> findByNamedQuery(String namedQuery, Paginacao paginacao, Object... params) {
		log.debug("Executando a named query: " + namedQuery);
		Query query = this.entityManager.createNamedQuery(namedQuery);
		setQueryParams(query, params);
		paginar(paginacao, query);
		return query.getResultList();
	}

	@SuppressWarnings("unchecked")
	
	public List<T> findByNamedQuery(String namedQuery, Object... params) {
		log.debug("Executando a named query: " + namedQuery);
		Query query = this.entityManager.createNamedQuery(namedQuery);
		setQueryParams(query, params);
		return query.getResultList();
	}

	
	@SuppressWarnings("unchecked")
	public List<T> findByNativeQuery(String sql, Paginacao paginacao, Object... params) {
		log.debug("Executando a native query: " + sql);
		Query query = this.entityManager.createNativeQuery(sql, classePersistencia);
		setQueryParams(query, params);
		paginar(paginacao, query);
		return query.getResultList();
	}

	
	public Object findSingleResult(String queryStr, Object... params) {
		log.debug("Executando a native query: " + queryStr);
		Query query = this.entityManager.createNativeQuery(queryStr);
		setQueryParams(query, params);
		return query.getSingleResult();
	}

	
	public List<T> listByQuery(String jpql) {
		log.debug("Executando a JPQL query: " + jpql);
		return this.entityManager.createQuery(jpql, classePersistencia).getResultList();
	}

	/**
	 * Atribuir os parâmetros da query.
	 *
	 * @param query
	 * @param params
	 */
	private void setQueryParams(Query query, Object... params) {
		log.debug("Parametros da query: ");
		if ((params != null) && (params.length > 0)) {
			for (int i = 0; i < params.length; i++) {
				query.setParameter(i + 1, params[i]);

				log.debug("Parametro " + (i + 1) + ": " + params[i]);
			}
		}
	}

	/**
	 * @return Class<T>
	 */
	public final Class<T> getclassePersistencia() {
		if (this.classePersistencia == null) {
			throw new RuntimeException("É necessário invocar o método setclassePersistencia(Class<T> clazz)");
		}
		return this.classePersistencia;
	}
	
	
	public void setClassePersistencia(Class<T> classePersistencia) {
		this.classePersistencia = classePersistencia;
	}
	

	/**
	 * Atribuir os parâmetros da query.
	 *
	 * @param params
	 * @param query
	 */
	@SuppressWarnings("rawtypes")
	private void setQueryMapParams(Map<String, Object> params, Query query) {
		log.debug("Parametros da query: ");
		for (Iterator ite = params.keySet().iterator(); ite.hasNext();) {
			String key = (String) ite.next();
			query.setParameter(key, params.get(key));

			log.debug("Parametro " + key + ": " + params.get(key));
		}
	}

	/**
	 * Atribuir o objeto paginação a query.
	 *
	 * @param paginacao
	 * @param query
	 */
	private void paginar(Paginacao paginacao, Query query) {
		if (paginacao != null) {
			if (paginacao.getPosicao() != null) {
				query.setFirstResult(paginacao.getPosicao());
			}
			if (paginacao.getLimite() != null) {
				query.setMaxResults(paginacao.getLimite());
			}
		}
	}





}
