package FUNIX.project3;

import java.time.LocalDate;
import java.util.Formatter;

public class Employee extends Staff implements ICalculator {
	private double salary = 3000000;
	private double overtimeHour;
	
	public Employee() {
		
	}
	
	public Employee(String id, String name, int age, double payRate, LocalDate startDate, Department department,
			int numDayOff, double overtimeHour) {
		super(id, name, age, payRate, startDate, department, numDayOff);
		this.overtimeHour = overtimeHour;
	}

	public double getSalary() {
		return salary;
	}

	public double getOvertimeHour() {
		return overtimeHour;
	}

	public void setOvertimeHour(double overtimeHour) {
		this.overtimeHour = overtimeHour;
	}

	@Override
	public double calculateSalary() {
		return super.getPayRate() * salary + 200000 * overtimeHour;
	}

	@Override
	public void displayInformation() {
		Formatter formatter = new Formatter();
		//Định dạng hiển thị theo cột
		System.out.print(formatter.format("%1$-8s|%2$-17s|%3$-3s|%4$-22s|%5$-17s|%6$-14s|%7$-18s|%8$-10s|%9$-10s|",
				super.getId(),super.getName(),super.getAge(),
				super.getDepartment().getName(),"Employee",super.getStartDate(),
				super.getNumDayOff(),getOvertimeHour(),super.getPayRate()  ));	
	}	
}
