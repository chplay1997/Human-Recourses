package FUNIX.project3;

import java.time.LocalDate;
import java.util.Formatter;

public class Manager extends Staff implements ICalculator{
	private double salary = 5000000;
	private String position;
	
	public Manager() {
		
	}
	
	public Manager(String id, String name, int age, double payRate, LocalDate startDate, Department department,
			int numDayOff,String position) {
		super(id, name, age, payRate, startDate, department, numDayOff);
		this.position = position;
	}
	
	public double getSalary() {
		return salary;
	}
	
	public String getPosition() {
		return position;
	}
	
	public void setPosition(String position) {
		this.position = position;
	}

	@Override
	public double calculateSalary() {
		int responsibleWage = 0;
		//Kiểm tra vị trí và gán lương trách nhiệm vào biến responsibleWage;
		if(position.equalsIgnoreCase("Business")) {		
			responsibleWage = 8000000;
		}
		if(position.equalsIgnoreCase("Project")) {
			responsibleWage = 5000000;
		}
		if(position.equalsIgnoreCase("Technical")) {
			responsibleWage = 6000000;
		}
		return super.getPayRate() * salary + responsibleWage;
	}

	@Override
	public void displayInformation() {
		Formatter formatter = new Formatter();
		//Định dạng hiển thị theo cột
		System.out.print(formatter.format("%1$-8s|%2$-17s|%3$-3s|%4$-22s|%5$-17s|%6$-14s|%7$-18s|%8$-10s|%9$-10s|",
				super.getId(),super.getName(),super.getAge(),
				super.getDepartment().getName(),getPosition() + " Leader",super.getStartDate(),
				super.getNumDayOff(),"",super.getPayRate() ));

		
	}
}
