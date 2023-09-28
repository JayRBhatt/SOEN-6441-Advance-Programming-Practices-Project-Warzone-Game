package model;

public class Country {

	private int d_CountryId;
	
	private String d_CountryName;
	
	private String d_Continent;
	
	private int d_ArmiesPresent;
	
	private String d_PlayerName;
	
	

	public int get_CountryId() {
		return d_CountryId;
	}

	public void set_CountryId(int p_CountryId) {
		this.d_CountryId = p_CountryId;
	}

	public String get_CountryName() {
		return d_CountryName;
	}

	public void set_CountryName(String p_CountryName) {
		this.d_CountryName = p_CountryName;
	}

	public String get_Continent() {
		return d_Continent;
	}

	public void set_Continent(String p_Continent) {
		this.d_Continent = p_Continent;
	}

	public int get_ArmiesPresent() {
		return d_ArmiesPresent;
	}

	public void set_ArmiesPresent(int p_ArmiesPresent) {
		this.d_ArmiesPresent = p_ArmiesPresent;
	}

	public String get_PlayerName() {
		return d_PlayerName;
	}

	public void set_PlayerName(String p_PlayerName) {
		this.d_PlayerName = p_PlayerName;
	}
	
	
	
}
