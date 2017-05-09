package main.java.parsedData;

public class Agent {
	private String name;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getGivenName() {
		return givenName;
	}
	public void setGivenName(String givenName) {
		this.givenName = givenName;
	}
	public String getMbox() {
		return mbox;
	}
	public void setMbox(String mbox) {
		this.mbox = mbox;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	private String type;
	private String givenName;
	private String mbox;
	private String id;
}
