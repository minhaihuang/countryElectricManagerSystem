package com.hhm.elec.util;

import java.util.ArrayList;
import java.util.List;

/**
 * 这是用来生成查询语句的方法
 * 
 * @author 黄帅哥
 * 
 */
public class Conditions {

	// 操作符,运算符
	public enum Operator {
		EQUAL, LIKE, NOT_EQUAL, GREATER, GREATER_EQUAL, LESS, LESS_EQUAL, IS, NOT_IS
	}

	private List<OrderBy> orderByList=new ArrayList<OrderBy>();
	
	
	
	public List<OrderBy> getOrderByList() {
		return orderByList;
	}

	public void setOrderByList(List<OrderBy> orderByList) {
		this.orderByList = orderByList;
	}

	public  void addOrderBy(String key,boolean isAsc){
		if(key==null||key.trim().length()==0){
			return;
		}
		
		orderByList.add(new OrderBy(key, isAsc));
	}
	
	/**
	 * 创建排序语句
	 * @return
	 */
	public String createOrderByString(){
		StringBuilder orderByStr=new StringBuilder();
		orderByStr.append(" order by ");
		for(OrderBy orderBy:orderByList){
			orderByStr.append(orderBy.key);
			
			orderByStr.append(orderBy.isAsc?" Asc ":" Desc ").append(",");
		}
		String finalStr=orderByStr.substring(0,orderByStr.length()-1);
		
		return finalStr;
	}
	
	/**
	 * 排序类
	 * 
	 * @author 黄帅哥
	 * 
	 */
	 class OrderBy {
		private String key;// 按照什么排序
		private boolean isAsc;// 是否是升序排序

		public OrderBy() {
			super();
		}

		public OrderBy(String key, boolean isAsc) {
			super();
			this.key = key;
			this.isAsc = isAsc;
		}

		public String getKey() {
			return key;
		}

		public void setKey(String key) {
			this.key = key;
		}

		public boolean getIsAsc() {
			return isAsc;
		}

		public void setIsAsc(boolean isAsc) {
			this.isAsc = isAsc;
		}

	}

	// 这是内部类Condition，用来生生成一部分一部分语句
	class Condition {
		private String key;// 查询的键
		private Object value;// 值
		private Operator operator;// 操作的符号

		public Condition() {

		}

		public Condition(String key, Object value, Operator operator) {
			super();
			this.key = key;
			this.value = value;
			this.operator = operator;
		}

		public String getKey() {
			return key;
		}

		public void setKey(String key) {
			this.key = key;
		}

		public Object getValue() {
			return value;
		}

		public void setValue(Object value) {
			this.value = value;
		}

		public Operator getOperator() {
			return operator;
		}

		public void setOperator(Operator operator) {
			this.operator = operator;
		}

	}

	// 生成语句和值的主要类，也是内部类
	public class WhereSqlAndValue {
		private String whereSql = "";
		private Object[] values = null;

		public WhereSqlAndValue() {
			super();
		}

		public WhereSqlAndValue(String whereSql, Object[] values) {
			super();
			this.whereSql = whereSql;
			this.values = values;
		}

		public String getWhereSql() {
			return whereSql;
		}

		public void setWhereSql(String whereSql) {
			this.whereSql = whereSql;
		}

		public Object[] getValues() {
			return values;
		}

		public void setValues(Object[] values) {
			this.values = values;
		}

	}

	private List<Condition> conditions = new ArrayList<Condition>();

	/**
	 * 加入条件语句的方法
	 * 
	 * @param key
	 * @param value
	 * @param operator
	 */
	public void addConditions(String key, Object value, Operator operator) {
		// 有效性判断
		if (key.trim().length() == 0 || key == null) {
			System.out.println("条件有误");
			return;
		}

		if (value == null) {
			if (value instanceof String) {
				String str = (String) value;
				if (str.trim().length() == 0 || str == null) {
					System.out.println("条件有误");
					return;
				}
			}

			return;
		}

		conditions.add(new Condition(key, value, operator));
	}

	/**
	 * 返回条件语句与值
	 * 
	 * @return
	 */
	public WhereSqlAndValue createWhereSqlAndValue() {
		List<Object> values = new ArrayList<Object>();
		StringBuilder whereSql = new StringBuilder(" where ");
		// EQUAL, LIKE, NOT_EQUAL, GREATER, GREATER_EQUAL, LESS, LESS, IS,
		// NOT_IS
		for (Condition condition : conditions) {
			switch (condition.operator) {
			case EQUAL:
				whereSql.append(condition.key).append(" = ").append("? ");
				values.add(condition.value);
				break;
			case LIKE:
				whereSql.append(condition.key).append(" like ").append(" ? ");
				values.add("%" + condition.value + "%");
				break;
			case NOT_EQUAL:
				whereSql.append(condition.key).append(" != ").append(" ?");
				values.add(condition.value);
				break;
			case GREATER:
				whereSql.append(condition.key).append(" > ").append(" ?");
				values.add(condition.value);
				break;
			case GREATER_EQUAL:
				whereSql.append(condition.key).append(" >= ").append(" ?");
				values.add(condition.value);
				break;
			case LESS:
				whereSql.append(condition.key).append(" < ").append(" ?");
				values.add(condition.value);
				break;
			case LESS_EQUAL:
				whereSql.append(condition.key).append(" <= ").append(" ?");
				values.add(condition.value);
				break;
			case IS:
				whereSql.append(condition.key).append(" is ").append(" ?");
				values.add(condition.value);
				break;
			case NOT_IS:
				whereSql.append(condition.key).append(" not is ").append(" ?");
				values.add(condition.value);
				break;

			default:
				break;
			}

			whereSql.append(" and ");
		}

		String finalSql = whereSql.substring(0, whereSql.length() - 6);

		WhereSqlAndValue whereSqlAndValue = new WhereSqlAndValue(finalSql,
				values.toArray());
		return whereSqlAndValue;

	}
}
