package co.alexwilkinson.springweb.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSourceUtils;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component("offersDao")
public class OffersDAO {

	private JdbcTemplate jdbc;
	private NamedParameterJdbcTemplate jdbcName;
	
	public OffersDAO(){
		System.out.println("Something something");
	}
	
	//@Autowired
	public void setDataSource(DataSource jdbc) {
		// general templating call that will pull from the database
		this.jdbc = new JdbcTemplate(jdbc);
		// Parameter driven template that allows values to be call/added from
		// the database using constructed statements
		this.jdbcName = new NamedParameterJdbcTemplate(jdbc);
	}

	/**
	 * Method to return all the values from the database
	 * 
	 * @return
	 */
	public List<Offers> getOffers() {

		return jdbc.query("SELECT * FROM offers", new RowMapper<Offers>() {

			public Offers mapRow(ResultSet rs, int rowNum) throws SQLException {
				Offers offer = new Offers();

				offer.setId(rs.getInt("id"));
				offer.setName(rs.getString("name"));
				// offer.setEmail(rs.getString("email"));
				offer.setText(rs.getString("text"));

				return offer;
			}
		});

	}

	/**
	 * This method will only return the selected value from the database
	 * 
	 * @param name
	 * @return
	 */
	public List<Offers> getOffer(String name) {

		// class that allows constructed statements to be done in Java
		MapSqlParameterSource params = new MapSqlParameterSource();
		// parameters is (Name, valueToAdd)
		params.addValue("name", name); // list of values

		// values to be added need to be :Value which is corrisponded to in
		// params
		return jdbcName.query("SELECT * FROM offers where name = :name", params, new RowMapper<Offers>() {

			public Offers mapRow(ResultSet rs, int rowNum) throws SQLException {

				Offers offer = new Offers();

				offer.setId(rs.getInt("id"));
				offer.setName(rs.getString("name"));
				offer.setEmail(rs.getString("email"));
				offer.setText(rs.getString("text"));

				return offer;
			}
		});

	}

	/**
	 * This method will only return the selected value from the database
	 * This method is quite a lot of code where as the createOffer() seems better
	 * 
	 * @param name
	 * @return
	 */
	public boolean setOffer(String name, String email, String text) {
		boolean dbUpdate = false;
		// class that allows constructed statements to be done in Java
		MapSqlParameterSource params = new MapSqlParameterSource();
		// parameters is (Name, valueToAdd)
		params.addValue("name", name); // list of values
		params.addValue("email", email); // list of values
		params.addValue("text", text); // list of values

		jdbcName.update("INSERT INTO offers(name,email,text) VALUES(:name,:email,:text)", params);
		dbUpdate = true;
		System.out.println(name + " has been successfully added to the database");

		// values to be added need to be :Value which is corrisponded to in
		// params
		return dbUpdate;

	}
	
	//create using the bean from Offers class
	public boolean createOffer(Offers offer){
		BeanPropertySqlParameterSource params = new BeanPropertySqlParameterSource(offer);
		
		return jdbcName.update("INSERT INTO offers(name,email,text) VALUES(:name,:email,:text)", params) ==1;
	}
	
	/*
	 * batch creation of objects that takes an arraylist of offers objects to add to the database
	 */
	@Transactional //forces all statements below to either all fail or all succeed, can set (isolation= ) or (propagation=)
	public int[] createOffer(List<Offers> offer){

		SqlParameterSource[] params = SqlParameterSourceUtils.createBatch(offer.toArray());
		
		return jdbcName.batchUpdate("INSERT INTO offers(name,email,text) VALUES(:name,:email,:text)", params);
		
	}
	
	//create using the bean from Offers class
	public boolean updateOffer(Offers offer){
		BeanPropertySqlParameterSource params = new BeanPropertySqlParameterSource(offer);
		
		return jdbcName.update("UPDATE offers SET name=:name, text=:text, email=:email, WHERE id=:id", params) ==1;
	}
	

	public boolean deleteOffer(int id) {
		boolean dbUpdate = false;
		// class that allows constructed statements to be done in Java
		MapSqlParameterSource params = new MapSqlParameterSource();
		// parameters is (Name, valueToAdd)
		params.addValue("id", id); // list of values

		jdbcName.update("DELETE FROM offers WHERE id=:id", params);
		dbUpdate = true;
		System.out.println("Id: " + id + " Has been successfully removed from the database");

		// values to be added need to be :Value which is corrisponded to in
		// params
		return dbUpdate;
	}
	
	

}
