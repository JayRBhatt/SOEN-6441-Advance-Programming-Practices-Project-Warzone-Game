package model;

public class Country {

	private int d_Id;
	
	private String d_Name;
	
	private String d_Continent;
	
	private int d_ArmiesPresent;
	
	private String d_PlayerName;
	
	
	public int getD_Id() {
		return d_Id;
	}

	public void setD_Id(int p_Id) {
		this.d_Id = p_Id;
	}

	public String getD_Name() {
		return d_Name;
	}

	public void setD_Name(String p_Name) {
		this.d_Name = p_Name;
	}

	public String getD_Continent() {
		return d_Continent;
	}

	public void setD_Continent(String p_Continent) {
		this.d_Continent = p_Continent;
	}

	public int getD_ArmiesAssigned() {
		return d_ArmiesPresent;
	}

	public void setD_ArmiesAssigned(int p_ArmiesPresent) {
		this.d_ArmiesPresent = p_ArmiesPresent;
	}

	public String getD_PlayerName() {
		return d_PlayerName;
	}

	public void setD_PlayerName(String d_PlayerName) {
		this.d_PlayerName = d_PlayerName;
	}
}
