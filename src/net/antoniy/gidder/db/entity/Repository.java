package net.antoniy.gidder.db.entity;

import java.io.Serializable;

import net.antoniy.gidder.db.DBC;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = DBC.repositories.table_name)
public class Repository implements Serializable {
	private static final long serialVersionUID = 20111204L;

	@DatabaseField(columnName = DBC.repositories.column_id, generatedId = true, canBeNull = false)
	private int id;
	
	@DatabaseField(columnName = DBC.repositories.column_name, canBeNull = false)
	private String name;
	
	@DatabaseField(columnName = DBC.repositories.column_mapping, canBeNull = false, unique = true)
	private String mapping;
	
	@DatabaseField(columnName = DBC.repositories.column_description, canBeNull = true)
	private String description;
	
	public Repository() {
	}

	public Repository(int id, String name, String mapping, String description) {
		this.id = id;
		this.name = name;
		this.mapping = mapping;
		this.description = description;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getMapping() {
		return mapping;
	}

	public void setMapping(String mapping) {
		this.mapping = mapping;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
}
