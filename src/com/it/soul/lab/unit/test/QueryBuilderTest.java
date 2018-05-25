package com.it.soul.lab.unit.test;

import org.junit.Assert;
import org.junit.Test;

import com.it.soul.lab.jpql.query.JPQLQuery;
import com.it.soul.lab.sql.query.SQLQuery;
import com.it.soul.lab.sql.query.SQLQuery.DataType;
import com.it.soul.lab.sql.query.SQLQuery.Logic;
import com.it.soul.lab.sql.query.SQLQuery.Operator;
import com.it.soul.lab.sql.query.SQLQuery.QueryType;
import com.it.soul.lab.sql.query.models.AndExpression;
import com.it.soul.lab.sql.query.models.Expression;
import com.it.soul.lab.sql.query.models.LogicExpression;
import com.it.soul.lab.sql.query.models.OrExpression;
import com.it.soul.lab.sql.query.models.Properties;
import com.it.soul.lab.sql.query.models.Property;

public class QueryBuilderTest {
	private static String SELECT_ALL = "SELECT * FROM Passenger";
	private static String SELECT_NAME_ID = "SELECT name, id FROM Passenger";
	private static String SELECT_WHERE_OR = "SELECT name, age FROM Passenger WHERE id = ? OR age = ?";
	private static String SELECT_WHERE = "SELECT name, age FROM Passenger WHERE ( name LIKE ? OR ( id = ? AND age >= ? ) )";
	
	private static String COUNT_VALUE = "SELECT COUNT(id) From Passenger Where name = 'sohana'";
	private static String COUNT_WHERE = "SELECT COUNT(*) From Passenger WHERE name = ?";
	
	private static String DISTINCT_VALUE = "SELECT DISTINCT(name) From Passenger Where name = 'sohana'";
	private static String DISTINCT_WHERE = "SELECT DISTINCT(*) From Passenger WHERE name = ?";
	
	private static String INSERT_INTO = "INSERT INTO Passenger ( name, age, sex) VALUES ( ?, ?, ?)";
	
	private static String UPDATE = "UPDATE Passenger SET name = ?, age = ? WHERE ( name = ? AND age > ? )";
	
	private static String DELETE = "DELETE FROM Passenger WHERE ( name = ? AND age > ? )";
	
	private static String JPQL_SELECT = "SELECT e.name, e.age, e.sex FROM Passenger e WHERE ( e.name = :name AND e.age > :age )";
	
	private static String JPQL_UPDATE = "UPDATE Passenger e SET e.name = :name, e.age = :age, e.sex = :sex WHERE ( e.name = :name AND e.age > :age )";
	
	@Test
	public void selectAllTest() {
		
		SQLQuery query = new SQLQuery.Builder(QueryType.SELECT)
									.columns()
									.from("Passenger")
									.build();
		
		Assert.assertEquals(SELECT_ALL
				, query.toString());
	}
	
	@Test
	public void selectName_IDTest(){
		SQLQuery qu2 = new SQLQuery.Builder(QueryType.SELECT)
							.columns("name", "id")
							.from("Passenger")
							.build();
		Assert.assertEquals(SELECT_NAME_ID, qu2.toString());
	}
	
	@Test
	public void select_Where_test(){
		
		SQLQuery qu5 = new SQLQuery.Builder(QueryType.SELECT)
									.columns("name","age")
									.from("Passenger")
									.whereParams(Logic.OR, "id", "age")
									.build();
		
		Assert.assertEquals(SELECT_WHERE_OR, qu5.toString());
		
	}
	
	@Test
	public void select_where_expressionTest(){
		
		LogicExpression andExp = new AndExpression(new Expression("id", Operator.EQUAL), new Expression("age", Operator.GREATER_THAN_OR_EQUAL));
		LogicExpression orExp = new OrExpression(new Expression("name", Operator.LIKE), andExp);
		
		SQLQuery qu6 = new SQLQuery.Builder(QueryType.SELECT)
									.columns("name","age")
									.from("Passenger")
									.where(orExp)
									.build();
		
		Assert.assertEquals(SELECT_WHERE, qu6.toString());
	}
	
	@Test
	public void countTest(){
		Property prop = new Property("name", "sohana", DataType.STRING);
		Expression comps = new Expression("name", Operator.EQUAL);
		
		SQLQuery count = new SQLQuery.Builder(QueryType.COUNT)
										.columns("id")
										.on("Passenger")
										.scalerClause(prop, comps)
										.build();
		Assert.assertEquals(COUNT_VALUE, count.toString());
		
		SQLQuery count2 = new SQLQuery.Builder(QueryType.COUNT)
										.columns().on("Passenger")
										.where(comps)
										.build();
		Assert.assertEquals(COUNT_WHERE, count2.toString());
	}
	
	@Test 
	public void distinctTest(){
		
		Property prop = new Property("name", "sohana", DataType.STRING);
		Expression comps = new Expression("name", Operator.EQUAL);
		
		SQLQuery distinct = new SQLQuery.Builder(QueryType.DISTINCT)
										.columns("name")
										.on("Passenger")
										.scalerClause(prop, comps)
										.build();
		Assert.assertEquals(DISTINCT_VALUE, distinct.toString());
		
		SQLQuery count2 = new SQLQuery.Builder(QueryType.DISTINCT)
									.columns().on("Passenger")
									.where(comps)
									.build();
		Assert.assertEquals(DISTINCT_WHERE, count2.toString());
		
	}
	
	@Test
	public void insertTest(){
		
		Properties nP = new Properties().add("name").add("age").add("sex");
		Property[] values =  (Property[]) nP.getProperties().toArray(new Property[0]);
		
		SQLQuery insert = new SQLQuery.Builder(QueryType.INSERT)
									.into("Passenger")
									.values(values)
									.build();
		Assert.assertEquals(INSERT_INTO, insert.toString());
		
	}
	
	@Test
	public void updateTest(){
		
		LogicExpression andExpression = new AndExpression(new Expression("name", Operator.EQUAL), new Expression("age", Operator.GREATER_THAN));
		
		SQLQuery update = new SQLQuery.Builder(QueryType.UPDATE)
								.columns("name","age")
								.from("Passenger")
								.where(andExpression)
								.build();
		
		Assert.assertEquals(UPDATE, update.toString());
		
	}
	
	@Test
	public void deleteTest(){
		
		LogicExpression andExpression = new AndExpression(new Expression("name", Operator.EQUAL), new Expression("age", Operator.GREATER_THAN));
		
		SQLQuery delete = new SQLQuery.Builder(QueryType.DELETE)
										.rowsFrom("Passenger")
										.where(andExpression)
										.build();
		
		Assert.assertEquals(DELETE, delete.toString());
		
	}
	
	@Test
	public void jpqlTest(){
		
		LogicExpression andExpression = new AndExpression(new Expression("name", Operator.EQUAL), new Expression("age", Operator.GREATER_THAN));
		
		SQLQuery jqpSel = new JPQLQuery.Builder(QueryType.SELECT)
											.columns("name","age","sex")
											.from("Passenger")
											.where(andExpression)
											.build();
		Assert.assertEquals(JPQL_SELECT, jqpSel.toString());
		
		SQLQuery jpqlUp = new JPQLQuery.Builder(QueryType.UPDATE)
											.columns("name", "age", "sex")
											.from("Passenger")
											.where(andExpression)
											.build();
		Assert.assertEquals(JPQL_UPDATE, jpqlUp.toString());
	}
}