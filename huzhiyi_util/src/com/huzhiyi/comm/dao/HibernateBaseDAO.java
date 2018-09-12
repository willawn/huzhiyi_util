package com.huzhiyi.comm.dao;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Criteria;
import org.hibernate.LockMode;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Example;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.huzhiyi.model.PagingBean;
import com.huzhiyi.model.abstraction.QueryResult;
import com.huzhiyi.util.StringUtils;

/**
 * 
 * @ClassName: HibernateBaseDAO
 * @Description: TODO(������)
 *               <p>
 * @author willter
 * @date Jul 25, 2012
 * 
 */
public class HibernateBaseDAO<ID extends Serializable> extends HibernateDaoSupport {

	protected final Log log = LogFactory.getLog(getClass());

	@Autowired
	public void setSessionFactoryOverride(SessionFactory sessionFactory) {
		super.setSessionFactory(sessionFactory);
	}

	/**
	 * ���Dao���ڵ�ʵ����
	 * 
	 * @return
	 */
	protected Class getEntityClass() {
		return null;
	}

	/**
	 * ��ȡId��
	 * 
	 * @param clz
	 * @return
	 */
	public String getIdName() {
		return getSessionFactory().getClassMetadata(getEntityClass()).getIdentifierPropertyName();
	}

	public String getIdName(String className) {
		return getSessionFactory().getClassMetadata(className).getIdentifierPropertyName();
	}

	/**
	 * ����
	 * 
	 * @param transientInstance
	 */
	public ID save(Object transientInstance) {
		return (ID) getHibernateTemplate().save(transientInstance);
	}

	/**
	 * ����
	 * 
	 * @param transientInstance
	 */
	public void update(Object transientInstance) {
		getHibernateTemplate().update(transientInstance, LockMode.READ);
	}

	/**
	 * ɾ��
	 * 
	 * @param persistentInstance
	 */
	public void delete(Object persistentInstance) {
		getHibernateTemplate().delete(persistentInstance);
	}

	/**
	 * ����Criteria
	 * 
	 * @param clz
	 * @return
	 */
	protected Criteria createCriteria() {
		return super.getSession().createCriteria(getEntityClass());
	}

	/**
	 * ����Query
	 * 
	 * @param hql
	 * @return
	 */
	protected Query createQuery(String hql) {
		return super.getSession().createQuery(hql);
	}

	/**
	 * ת����̬���
	 * 
	 * @param c
	 * @return
	 */
	protected QueryResult toQueryResult(final Criteria c) {
		return new QueryResult() {

			public List getList() {
				return c.list();
			}

			public Object getUniqueResult() {
				return c.uniqueResult();
			}
		};
	}

	/**
	 * ת����̬���
	 * 
	 * @param q
	 * @return
	 */
	protected QueryResult toQueryResult(final Query q) {
		return new QueryResult() {

			public List getList() {
				return q.list();
			}

			public Object getUniqueResult() {
				return q.uniqueResult();
			}
		};
	}

	/**
	 * ����ID��ѯ
	 * 
	 * @param clz
	 * @param id
	 * @return
	 */
	public Object findById(ID id) {
		Object instance = getHibernateTemplate().get(getEntityClass(), id);
		return instance;
	}

	/**
	 * ����ʵ����ѯ
	 * 
	 * @param instance
	 * @return
	 */
	public QueryResult findByExample(Object instance) {
		Criteria c = createCriteria();
		c.add(Example.create(instance));
		return toQueryResult(c);
	}

	/**
	 * ��ѯ����
	 * 
	 * @param clz
	 * @return
	 */
	public QueryResult find() {
		final Criteria c = createCriteria();
		return toQueryResult(c);
	}

	/**
	 * �ϲ�
	 * 
	 * @param detachedInstance
	 * @return
	 */
	public Object merge(Object detachedInstance) {
		Object result = (Object) getHibernateTemplate().merge(detachedInstance);
		return result;

	}

	/**
	 * �������£���ճ��������
	 * 
	 * @param instance
	 */
	public void attachDirty(Object instance) {
		getHibernateTemplate().saveOrUpdate(instance);

	}

	/**
	 * �����������
	 * 
	 * @param instance
	 */
	public void attachClean(Object instance) {
		getHibernateTemplate().lock(instance, LockMode.NONE);

	}

	/**
	 * ������Բ�ѯ
	 * 
	 * @param clz
	 * @param propertyName
	 * @param value
	 * @return
	 */
	public QueryResult findByProperty(String propertyName, Object value) {
		Criteria c = createCriteria();
		if (value instanceof Object[]) {
			c.add(Restrictions.in(propertyName, (Object[]) value));
		} else if (value instanceof Collection) {
			c.add(Restrictions.in(propertyName, (Collection) value));
		} else {
			c.add(Restrictions.eq(propertyName, value));
		}
		return toQueryResult(c);
	}

	/**
	 * ������Բ�ѯ�͹�������
	 * 
	 * @param clz
	 * @param propertyName
	 * @param value
	 * @param order
	 * @param dir
	 * @return
	 */
	public QueryResult findByProperty(String propertyName, Object value, String order, String dir) {
		Criteria c = createCriteria();
		if (value instanceof Object[]) {
			c.add(Restrictions.in(propertyName, (Object[]) value));
		} else if (value instanceof Collection) {
			c.add(Restrictions.in(propertyName, (Collection) value));
		} else {
			c.add(Restrictions.eq(propertyName, value));
		}
		if (StringUtils.isNotEmpty(order) && StringUtils.isNotEmpty(dir)) {
			if ("asc".equalsIgnoreCase(dir)) {
				c.addOrder(Order.asc(order));
			}
			if ("desc".equalsIgnoreCase(dir)) {
				c.addOrder(Order.desc(order));
			}
		}
		return toQueryResult(c);
	}

	/**
	 * ��Ӷ����Բ�ѯ
	 * 
	 * @param clz
	 * @param propertyName
	 * @param value
	 * @return
	 */
	public QueryResult findByProperty(String[] propertyNames, Object[] values, String order, String dir) {
		Criteria c = createCriteria();
		for (int i = 0, len = propertyNames.length; i < len; i++) {
			if (values[i] instanceof Object[]) {
				c.add(Restrictions.in(propertyNames[i], (Object[]) values[i]));
			} else if (values[i] instanceof Collection) {
				c.add(Restrictions.in(propertyNames[i], (Collection) values[i]));
			} else {
				c.add(Restrictions.eq(propertyNames[i], values[i]));
			}
		}
		if (StringUtils.isNotEmpty(order) && StringUtils.isNotEmpty(dir)) {
			if ("asc".equalsIgnoreCase(dir)) {
				c.addOrder(Order.asc(order));
			}
			if ("desc".equalsIgnoreCase(dir)) {
				c.addOrder(Order.desc(order));
			}
		}

		return toQueryResult(c);
	}

	/**
	 * ��ѯ����
	 * 
	 * @param clz
	 * @return
	 */
	public List findAll() {
		return find().getList();
	}

	private static ConcurrentMap<String, String[]> hqlContainer = new ConcurrentHashMap<String, String[]>();

	/**
	 * ��ѯ����з�ҳ
	 * 
	 * @param hql
	 * @param pagingBean
	 * @param params
	 * @return
	 */
	public List findPaging(String hql, PagingBean pagingBean) {
		String newSql = null;
		String countSql = null;
		String nhql = null;
		String order = null;
		// if (hqlContainer.containsKey(hql)) {
		// String[] sqls = hqlContainer.get(hql);
		// newSql = sqls[0];
		// countSql = sqls[1];
		// nhql = sqls[2];
		// } else {

		String entityName = StringUtils.subStr(hql, "from ", " ");
		if (entityName == null) {
			entityName = StringUtils.subStr(hql, "from ");
		}
		String idName = getIdName(entityName);
		// ����hql�Ƿ����where�ؼ���
		String s = hql.lastIndexOf("where") == -1 ? "where" : "and";
		String codition = null;
		if (hql.startsWith("select")) {
			codition = StringUtils.subStr(hql, "select ", " from");
		}
		String selectPart = StringUtils.subStr(hql, "from ");

		String selectHandel = StringUtils.subStr(hql, entityName.concat(" "));
		if (selectHandel.indexOf(" ") != -1) {
			selectHandel = StringUtils.subStr(hql, entityName.concat(" "), " ");
		}

		String selectPartNoOrder = selectPart;
		// ����hql�Ƿ����order by�ؼ���
		if (selectPartNoOrder.lastIndexOf(" order by ") != -1) {
			selectPartNoOrder = selectPartNoOrder.substring(0, selectPartNoOrder.indexOf(" order by "));
		}
		countSql = StringUtils.stringBuilder("select count(", selectHandel, ".", idName, ") ", "from ", selectPartNoOrder).toString();
		// countSql="select count("+selectHandel+"."+idName+") from
		// "+selectPart;

		newSql = StringUtils.stringBuilder("select ", selectHandel, ".", idName, " from ", selectPart).toString();
		// newSql="select "+selectHandel+"."+idName+" from "+selectPart;

		String hqlNoOrder = hql;
		// ����hql�Ƿ����order by�ؼ���
		if (hqlNoOrder.lastIndexOf(" order by ") != -1) {
			order = hqlNoOrder.substring(hqlNoOrder.indexOf(" order by "));
			hqlNoOrder = hqlNoOrder.substring(0, hqlNoOrder.indexOf(" order by "));
		}
		nhql = StringUtils.stringBuilder(hqlNoOrder, " ", s, " ", selectHandel, ".", idName, " in (").toString();
		// nhql=hql+" "+s+" "+selectHandel+"."+idName+" in (";

		// String[] sqls = new String[] { newSql, countSql, nhql };
		// hqlContainer.put(hql, sqls);
		// }

		Query nq = createQuery(newSql);

		if (null != pagingBean) {
			Long cnt = (Long) findOneByHQL(countSql);
			int inCnt = cnt.intValue();
			pagingBean.setMaxRows(inCnt);
			if (inCnt == 0) {
				return new ArrayList();
			}
			// pagingBean.update();

			nq.setFirstResult(pagingBean.getBeginRow());
			nq.setMaxResults(pagingBean.getPageRows());
		}

		StringBuilder sb = new StringBuilder(nhql);
		List<Object> idList = nq.list();
		int size = idList.size();
		if (size == 0) {
			return new ArrayList();
		}
		for (int i = 0; i < size; i++) {
			sb.append("?,");
		}

		int len = sb.length();
		sb.replace(len - 1, len, ")");
		if (order != null && !"".equals(order.trim())) { // �ж��Ƿ������������
			sb.append(order);
		}
		final Query q = createQuery(sb.toString());
		int i = 0;
		for (Object id : idList) {
			q.setParameter(i++, id);
		}
		return q.list();
	}

	public List findPaging(String hql, PagingBean pagingBean, Object... params) {
		Query q = createQuery(hql);
		//System.out.println(q);
		for (int i = 0, len = params.length; i < len; i++) {
			q.setParameter(i, params[i]);
		}
		q.setFirstResult(pagingBean.getBeginRow());
		q.setMaxResults(pagingBean.getPageRows());
		return q.list();
	}

	/**
	 * ����HQL���ִ�в�ѯ�������ض�̬�����
	 * 
	 * @param hql
	 * @return
	 */
	public QueryResult findByHQL(String hql, Object... params) {
		final Query q = createQuery(hql);
		for (int i = 0, len = params.length; i < len; i++) {
			q.setParameter(i, params[i]);
		}
		return new QueryResult() {
			public List getList() {
				return q.list();
			}

			public Object getUniqueResult() {
				return q.uniqueResult();
			}
		};
	}

	/**
	 * ����HQL���ִ�в�ѯ��������һ����¼
	 * 
	 * @param hql
	 * @param params
	 * @return
	 */
	public Object findOneByHQL(String hql, Object... params) {
		final Query q = createQuery(hql);
		for (int i = 0, len = params.length; i < len; i++) {
			q.setParameter(i, params[i]);
		}
		return q.uniqueResult();
	}

	/**
	 * ִ�и���
	 * 
	 * @param hql
	 * @param params
	 * @return
	 */
	public int executeUpdate(String hql, Object... params) {
		final Query q = createQuery(hql);
		for (int i = 0, len = params.length; i < len; i++) {
			q.setParameter(i, params[i]);
		}
		return q.executeUpdate();
	}

	/**
	 * ��Hql��ѯ���ӽ�������ݲ���ҳ
	 * 
	 * @param hql
	 * @param params
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<Object[]> findComplexPaging(String hql, PagingBean pagingBean, Object... params) {
		return findPaging(hql, pagingBean, params);
	}

	/**
	 * ��Hql��ѯ���ӽ��������
	 * 
	 * @param hql
	 * @param params
	 * @return
	 */
	public QueryResult<Object[]> findComplexByHQL(String hql, Object... params) {
		final Query q = createQuery(hql);
		for (int i = 0, len = params.length; i < len; i++) {
			q.setParameter(i, params[i]);
		}
		return new QueryResult<Object[]>() {
			@SuppressWarnings("unchecked")
			public List<Object[]> getList() {
				return q.list();
			}

			public Object[] getUniqueResult() {
				return (Object[]) q.uniqueResult();
			}
		};
	}
}