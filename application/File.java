package application;

public class File {
	
	private String filename;
	private String username;
	private String password;
	private String website;
	private String email;
	private String icon;
	private String notes;
	
	public void setFile(String name) {
		filename = name;
	}
	
	public String getFile() {
		return filename;
	}
	
	public void setUser(String user) {
		username = user;
	}
	
	public String getUser() {
		return username;
	}
	
	public void setPass(String pass) {
		password = pass;
	}
	
	public String getPass() {
		return password;
	}
	
	public void setWeb(String web) {
		website = web;
	}
	
	public String getWeb() {
		return website;
	}
	
	public void setEmail(String email) {
		this.email = email;
	}
	
	public String getEmail() {
		return email;
	}
	
	public void setIcon(String icon) {
		this.icon = icon;
	}
	
	public String getIcon() {
		return icon;
	}
	
	public void setNotes(String notes) {
		this.notes = notes;
	}
	
	public String getNotes() {
		return notes;
	}

}
