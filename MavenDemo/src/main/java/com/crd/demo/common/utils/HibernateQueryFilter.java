package com.crd.demo.common.utils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import lombok.extern.slf4j.Slf4j;

import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Disjunction;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import org.springframework.util.Assert;

import com.crd.demo.common.model.HibernateSort;
import com.crd.demo.common.model.HibernateSort.Direction;
import com.crd.demo.common.model.HibernateSort.Sort;



/**
 * @Description:用于封装Hibernate高级条件查询的类
 */
@SuppressWarnings({ "rawtypes", "unchecked" })
@Slf4j
public class HibernateQueryFilter {
	/** 多个属性间OR关系的分隔符. */
	public static final String OR_SEPARATOR = "-OR-";

	public static final String ENUM_SEPARATOR = "enum_";

	public static final String ENUM_PACKAGE = "com.crd.demo.common.enumbean.";

	/** 属性比较类型. */
	public enum HibernateMatchType {
		EQ, LIKE, LT, GT, LE, GE, IN, NE;
	}

	/** 属性数据类型. */
	public enum HibernatePropertyType {
		S(String.class), I(Integer.class), L(Long.class), N(Double.class), D(Date.class), B(
				Boolean.class);

		private Class<?> clazz;

		private HibernatePropertyType(Class<?> clazz) {
			this.clazz = clazz;
		}

		public Class<?> getValue() {
			return clazz;
		}
	}

	/**
	 * 从HttpRequest中创建PropertyFilter列表, 默认Filter属性名前缀为filter.
	 * 
	 * @see #buildFromHttpRequest(HttpServletRequest, String)
	 */
	public static List<Criterion> buildFromHttpRequest(final HttpServletRequest request) {
		return buildFromHttpRequest(request, "filter");
	}

	/**
	 * @Description: 从HttpRequest中构建hql或sql里where语句中的查询语句字符串。
	 * @param request
	 * @return String <br>
	 *         eg: " and name like '%admin%' and number > 20 "
	 * @Since :2016年7月20日 下午1:42:56
	 */
	public static String buildWhereFromHttpRequest(final HttpServletRequest request) {
		return buildWhereFromHttpRequest(request, "filter");
	}
	/**
	 * 从HttpRequest中创建PropertyFilter列表 PropertyFilter命名规则为Filter属性前缀-比较类型-属性类型-属性名. <br>
	 * eg: filter-EQ-S-name <br>
	 * eg: filter-LIKE-S-name-OR-LIKE-S-loginName <br>
	 * eg: filter-EQ-enum_StatusEnum-status
	 */
	public static List<Criterion> buildFromHttpRequest(final HttpServletRequest request,
			final String filterPrefix) {
		List<Criterion> filterList = new ArrayList<Criterion>();

		// 从request中获取含属性前缀名的参数,构造去除前缀名后的参数Map.
		Map<String, Object> filterParamMap = ServletUtils.getParametersStartingWith(request,
				filterPrefix + "-");

		// 分析参数Map,构造PropertyFilter列表
		for (Map.Entry<String, Object> entry : filterParamMap.entrySet()) {
			String filterName = entry.getKey();
			String value = (String) entry.getValue();
			// 如果value值为空,则忽略此filter.
			if (StringUtils.isNotBlank(value)) {
				Criterion c = buildCriterionByParameter(filterName, value);
				if (c != null) {
					filterList.add(c);
				}
			}
		}

		return filterList;
	}

	/**
	 * @Description: 通过匹配类型，将参数及参数值赋于Criterion查询类中
	 * @param matchTypeString
	 * @param classString
	 * @param propertyName
	 * @param propertyValue
	 * @return Criterion
	 * @Since :2016年7月19日 上午11:00:42
	 */
	public static Criterion buildCriterion(final String matchTypeString, final String classString,
			final String propertyName, final Object propertyValue) {
		Assert.hasText(propertyName, "propertyName不能为空");
		HibernateMatchType matchType = null;
		try {
			matchType = Enum.valueOf(HibernateMatchType.class, matchTypeString);
		} catch (Exception e) {
			log.error("filter名称" + propertyName + "没有按规则编写,无法得到属性比较类型.", e);
			throw new IllegalArgumentException("filter名称" + propertyName + "没有按规则编写,无法得到属性比较类型.", e);
		}
		Criterion criterion = null;
		// 根据MatchType构造criterion
		switch (matchType) {
		case EQ:
			criterion = Restrictions.eq(propertyName, propertyValue);
			break;
		case LIKE:
			criterion = Restrictions.like(propertyName, String.valueOf(propertyValue),
					MatchMode.ANYWHERE);
			break;
		case LE:
			criterion = Restrictions.le(propertyName, propertyValue);
			break;
		case LT:
			criterion = Restrictions.lt(propertyName, propertyValue);
			break;
		case GE:
			criterion = Restrictions.ge(propertyName, propertyValue);
			break;
		case GT:
			criterion = Restrictions.gt(propertyName, propertyValue);
			break;
		case IN:
			String[] strArr = String.valueOf(propertyValue).split(",");
			List<Object> valueList = new ArrayList<Object>();
			for (String str : strArr) {
				if (StringUtils.isNotBlank(str)) {
					Object obj = getMatchValueByClass(null, classString, str);
					valueList.add(obj);
				}
			}
			if (!valueList.isEmpty()) {
				criterion = Restrictions.in(propertyName, valueList.toArray());
			}
			break;
		case NE:
			criterion = Restrictions.ne(propertyName, propertyValue);
			break;
		}
		return criterion;
	}

	/**
	 * 按属性条件列表创建Criterion数组,辅助函数.
	 */
	public static Criterion buildCriterionByParameter(String filterName, String value) {
		String[] nameParts = filterName.split(OR_SEPARATOR);
		if (nameParts.length < 2) { // 只有一个属性需要比较的情况.
			String[] nameArray = filterName.split("-");
			Object matchValue = getMatchValueByClass(nameArray[0], nameArray[1], value);
			return buildCriterion(nameArray[0], nameArray[1], nameArray[2], matchValue);
		} else {// 包含多个属性需要比较的情况,进行or处理.
			Disjunction disjunction = Restrictions.disjunction();
			for (String param : nameParts) {
				String[] nameArray = param.split("-");
				Object matchValue = getMatchValueByClass(nameArray[0], nameArray[1], value);
				Criterion singleCriterion = buildCriterion(nameArray[0], nameArray[1],
						nameArray[2], matchValue);
				if (singleCriterion != null) {
					disjunction.add(singleCriterion);
				}
			}
			return disjunction;
		}
	}

	/**
	 * @Description: 通过过滤参数封装criterion查询类
	 * @param filterName
	 * @param paramValue
	 * @return Criterion
	 * @Since :2016年7月19日 上午11:03:49
	 */
	public static Criterion createCriterionByParam(String filterName, String paramValue) {
		String[] nameArray = filterName.split("-");
		Object matchValue = getMatchValueByClass(nameArray[0], nameArray[1], paramValue);
		return buildCriterion(nameArray[0], nameArray[1], nameArray[2], matchValue);
	}

	/**
	 * @Description: 获取待比较参数的值
	 * @param matchTypeString
	 * @param classString
	 * @param value
	 * @return Object
	 * @Since :2016年7月19日 上午11:41:55
	 */
	public static Object getMatchValueByClass(String matchTypeString, String classString,
			String value) {
		if ("IN".equals(matchTypeString)) {
			return value;
		} else {
			Class cls = null;
			try {
				if (classString.startsWith(ENUM_SEPARATOR)) {
					String classStr = ENUM_PACKAGE
							+ StringUtils.substringAfter(classString, ENUM_SEPARATOR);
					cls = Class.forName(classStr);
					return Enum.valueOf(cls, value);
				} else {
					cls = Enum.valueOf(HibernatePropertyType.class, classString).getValue();
					return ConvertUtils.convert(value, cls);
				}
			} catch (Exception e) {
				log.error("filter名称没有按规则编写,无法得到属性值类型.", e);
				throw new IllegalArgumentException("filter名称没有按规则编写,无法得到属性值类型.", e);
			}
		}
	}

	/**
	 * @Description:从httprequest中获取排序参数。命名规则为：order属性前缀-排序类型-属性名 <br>
	 *                                                           eg: {order-name : ASC} <br>
	 *                                                           eg: {order-createTime : DESC}
	 */
	public static HibernateSort getSortFromHttpRequest(final HttpServletRequest request) {
		// 从request中获取含属性前缀名的参数,构造去除前缀名后的参数Map.
		Map<String, Object> orderMap = ServletUtils.getParametersStartingWith(request, "order-");
		List<Sort> sorts = new ArrayList<Sort>();
		for (Map.Entry<String, Object> orderEntry : orderMap.entrySet()) {
			Direction direction = null;
			try {
				direction = Enum.valueOf(Direction.class, String.valueOf(orderEntry.getValue()));
			} catch (Exception e) {
				log.error("order名称" + orderEntry.getKey() + "没有按规则编写,无法得到属性排序类型.", e);
				throw new IllegalArgumentException("order名称" + orderEntry.getKey()
						+ "没有按规则编写,无法得到属性排序类型.", e);
			}
			sorts.add(new Sort(direction, orderEntry.getKey()));
		}
		return new HibernateSort(sorts);
	}

	/**
	 * @Description: 取httpRequest中的高级条件查询参数，将其封装为数据库语句的where条件（以 and开头）语句字符串
	 * @param request
	 * @param filterPrefix
	 * @return String
	 * @Since :2016年7月20日 下午4:45:45
	 */
	public static String buildWhereFromHttpRequest(final HttpServletRequest request,
			final String filterPrefix) {
		StringBuffer sb = new StringBuffer();
		Map<String, Object> filterParamMap = ServletUtils.getParametersStartingWith(request,
				filterPrefix + "-");
		// 分析参数Map,构造PropertyFilter列表
		for (Map.Entry<String, Object> entry : filterParamMap.entrySet()) {
			String filterName = entry.getKey();
			String value = (String) entry.getValue();
			// 如果value值为空,则忽略此filter.
			if (StringUtils.isNotBlank(value)) {
				String paramWhere = buildWhereByParameter(filterName, value);
				if (StringUtils.isNotBlank(paramWhere)) {
					sb.append(" and ").append(paramWhere);
				}
			}
		}
		return sb.toString();
	}

	/**
	 * @Description: 通过参数名和参数值构建条件语句
	 * @param filterName
	 * @param value
	 * @return String
	 * @Since :2016年7月20日 下午4:49:07
	 */
	private static String buildWhereByParameter(final String filterName, final String value) {
		String[] nameParts = filterName.split(OR_SEPARATOR);
		String whereString = "";
		if (nameParts.length < 2) { // 只有一个属性需要比较的情况.
			String[] nameArray = filterName.split("-");
			whereString = buildWhere(nameArray[0], nameArray[2], value);
		} else {// 包含多个属性需要比较的情况,进行or处理.
			StringBuffer orString = new StringBuffer();
			for (int i = 0; i < nameParts.length; i++) {
				String[] nameArray = nameParts[i].split("-");
				String singleOrString = buildWhere(nameArray[0], nameArray[2], value);
				if (StringUtils.isNotBlank(singleOrString)) {
					orString.append(singleOrString);
					if (i < nameParts.length - 1) {
						orString.append(" or ");
					}
				}
			}
			String resultString = orString.toString();
			if (StringUtils.isNotBlank(resultString)) {
				if (resultString.endsWith(" or ")) {
					resultString = resultString.substring(0, resultString.length() - 4);
				}
				whereString = " (" + resultString + ") ";
			}
		}
		return whereString;
	}

	/**
	 * @Description: 通过比较类型封装条件查询语句
	 * @param matchTypeString
	 * @param propertyName
	 * @param propertyValue
	 * @return String
	 * @Since :2016年7月20日 下午4:50:03
	 */
	private static String buildWhere(final String matchTypeString, final String propertyName,
			final Object propertyValue) {
		Assert.hasText(propertyName, "propertyName不能为空");
		HibernateMatchType matchType = null;
		try {
			matchType = Enum.valueOf(HibernateMatchType.class, matchTypeString);
		} catch (Exception e) {
			log.error("filter名称" + propertyName + "没有按规则编写,无法得到属性比较类型.", e);
			throw new IllegalArgumentException("filter名称" + propertyName + "没有按规则编写,无法得到属性比较类型.", e);
		}
		StringBuffer sb = new StringBuffer();
		// 根据MatchType构造criterion
		switch (matchType) {
		case EQ:
			sb.append(propertyName).append(" = '").append(propertyValue).append("' ");
			break;
		case LIKE:
			sb.append(propertyName).append(" like '%").append(propertyValue).append("%' ");
			break;
		case LE:
			sb.append(propertyName).append(" <= '").append(propertyValue).append("' ");
			break;
		case LT:
			sb.append(propertyName).append(" < '").append(propertyValue).append("' ");
			break;
		case GE:
			sb.append(propertyName).append(" >= '").append(propertyValue).append("' ");
			break;
		case GT:
			sb.append(propertyName).append(" > '").append(propertyValue).append("' ");
			break;
		case IN:
			String[] strArr = String.valueOf(propertyValue).split(",");
			StringBuffer inStr = new StringBuffer();
			for (int i = 0; i < strArr.length; i++) {
				if (StringUtils.isNotBlank(strArr[i])) {
					inStr.append("'").append(strArr[i]).append("'");
					if (i < strArr.length - 1) {
						inStr.append(",");
					}
				}
			}
			String inString = inStr.toString();
			if (StringUtils.isNotBlank(inString)) {
				if (inString.endsWith(",")) {
					inString = inString.substring(0, inString.length() - 1);
				}
				sb.append(propertyName).append(" in (").append(inString).append(") ");
			}
			break;
		case NE:
			sb.append(propertyName).append(" <> '").append(propertyValue).append("' ");
			break;
		}
		return sb.toString();
	}
}
