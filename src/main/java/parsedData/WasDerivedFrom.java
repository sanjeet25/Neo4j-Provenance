package main.java.parsedData;

public class WasDerivedFrom {
	private Long id;
	private String generatedEntity;
	private String usedEntity;
	
	public String getGeneratedEntity() {
		return generatedEntity;
	}
	public void setGeneratedEntity(String generatedEntity) {
		this.generatedEntity = generatedEntity;
	}
	public String getUsedEntity() {
		return usedEntity;
	}
	public void setUsedEntity(String usedEntity) {
		this.usedEntity = usedEntity;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	private String type;
}
