package FUNIX.project3;

public class Department {
	private String id;
	private String name;
	private int currNumberStaff; 
	
	public Department() {
		
	}
	
	public Department(String id, String name) {
		this.id = id;
		this.name = name;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getCurrNumberStaff() {
		return currNumberStaff;
	}
	public void setCurrNumberStaff(int currNumberStaff) {
		this.currNumberStaff = currNumberStaff;
	}
	@Override
	public String toString() {                                       
		return id + "  |" + getCurrNumberStaff() + "             |" + name;
	}

}
