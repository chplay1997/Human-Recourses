package FUNIX.project3;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Formatter;
import java.util.Scanner;

public class HumanResources {
	public static Scanner sc = new Scanner(System.in);

	public static void main(String[] args) {
		System.out.println("CHÀO MỪNG BẠN ĐẾN VỚI CHƯƠNG TRÌNH QUẢN LÝ NHÂN SỰ!");
		ArrayList<Staff> listStaff = new ArrayList<Staff>(); //Khởi tạo một list staff
		
		
		int n;
		boolean checkFast = false;//Biến kiểm tra nhập nhanh
		do {
			System.out.println("\n----------------------------------------------------");
			System.out.println("1.Hiển thị nhân viên hiện có trong công ty");
			System.out.println("2.Hiển thị các bộ phận hiện có trong công ty");
			System.out.println("3.Hiển thị các nhân viên theo từng bộ phận");
			System.out.println("4.Thêm nhân viên mới vào công ty");
			System.out.println("5.Tìm kiếm thông tin nhân viên theo tên hoặc mã nhân viên");
			System.out.println("6.Hiển thị bảng lương của nhân viên toàn công ty");
			System.out.println("7.Hiển thị bảng lương của nhân viên theo thứ tự tăng dần");
			System.out.println("8.Hiển thị bảng lương của nhân viên theo thứ tự giảm dần");
			System.out.println("9.Thêm nhân viên nhanh và hiển thị");
			System.out.println("0.Thoát chương trình\n");
			
			do {			
				System.out.print("Mời bạn chọn(0-9): ");
				n = sc.nextInt();
			}while(n<0 || n>9);
			
			switch(n) {
			case 1:
				showStaff(listStaff,false);
				break;
			case 2:
				showDepartment(listStaff);
				break;
			case 3:
				showStaffDepartment(listStaff);
				break;
			case 4:
				listStaff.add( (Staff) addStaff(listStaff) );
				break;
			case 5:
				searchStaff(listStaff);
				break;
			case 6:
				showStaff(listStaff,true);
				break;
			case 7:
				showSalaryUp(listStaff);
				break;
			case 8:
				showSalaryDown(listStaff);
				break;
			case 9:
				addFast(listStaff,checkFast);
				checkFast = true;
				break;
			case 0:
				System.out.println("Cảm ơn bạn sử dụng chương trình của chúng tôi!");
				System.out.println("----------------------------------------------");
				System.exit(0);
			}
		}while(true);
	}

	//Hiển thị bảng lương theo thứ tự giảm dần
	public static void showSalaryDown(ArrayList<Staff> listStaff) {
		ArrayList<Staff> staff = new ArrayList<Staff>();//Tạo một list staff để lưu trữ tạm dữ liệu
		int size = listStaff.size();//lấy số lượng phần tử của listStaff
		while(size>0) {
			int n = 0;
			double max = ( (ICalculator) listStaff.get(0)).calculateSalary();//Gán tổng lương phần tử đầu tiên của listStaff la lớn nhất
			for(int i =1;i<size;i++) {
				if(max < ((ICalculator) listStaff.get(i)).calculateSalary()) {
					max = ((ICalculator) listStaff.get(i)).calculateSalary();//Nếu tổng lương phần tử hiện tại nhỏ hơn max thì hoán đổi max
					n = i;//ghi nhớ vị trí phần max
				}
			}
			staff.add(listStaff.get(n));//Thêm phần tử lớn nhất của listStaff vào staff
			listStaff.remove(n);//Xóa phần tử lớn nhất trong listStaff
			size--;//Cập nhật lại số lượng phần tử của listStaff
		}
		listStaff.addAll(staff);//Sao chép toàn bộ phần tử đã sắp sếp vào lại listStaff
		staff.clear();//Xóa sạch phần tử trong staff
		
		showStaff(listStaff,true);//In ra listStaff đã được sắp xếp
		System.out.println("Sắp xếp mảng giảm dần thành công !");
	}

	//Hiển thị bảng lương theo thứ tự tăng dần
	public static void showSalaryUp(ArrayList<Staff> listStaff) {
		ArrayList<Staff> staff = new ArrayList<Staff>();//Tạo một list staff để lưu trữ tạm dữ liệu
		int size = listStaff.size(); //lấy số lượng phần tử của listStaff
		while(size>0) {
			int n = 0;
			double min = ( (ICalculator) listStaff.get(0)).calculateSalary();//Gán tổng lương phần tử đầu tiên của listStaff la nhỏ nhất
			//Chạy vòng lặp để tìm ra phần tử nhỏ nhất
			for(int i =1;i<size;i++) {
				if(min > ((ICalculator) listStaff.get(i)).calculateSalary()) {
					min = ((ICalculator) listStaff.get(i)).calculateSalary();//Nếu tổng lương phần tử hiện tại lớn hơn min thì hoán đổi min
					n = i; //ghi nhớ vị trí phần min
				}
			}
			staff.add(listStaff.get(n)); //Thêm phần tử nhỏ nhất của listStaff vào staff
			listStaff.remove(n);//Xóa phần tử nhỏ nhất trong listStaff
			size--;//Cập nhật lại số lượng phần tử của listStaff
		}
		listStaff.addAll(staff);//Sao chép toàn bộ phần tử đã sắp sếp vào lại listStaff
		staff.clear();//Xóa sạch phần tử trong staff
		
		showStaff(listStaff,true);//In ra listStaff đã được sắp xếp
		System.out.println("Sắp xếp mảng tăng dần thành công !");
	}

	//Hàm tìm kiếm nhân viên
	public static void searchStaff(ArrayList<Staff> listStaff) {
		//Chọn kiểu để tìm kiếm
		System.out.println("1.Tìm kiếm theo ID");
		System.out.println("2.Tìm kiếm theo tên");
		int k;
		do {			
			System.out.print("Bạn chọn: ");
			k=sc.nextInt();
		}while(k>2 || k<1);//Chỉ khi n bằng 2 hoặc bằng 1 thì dừng
		String answer = sc.nextLine();//Loại bỏ nút enter khi chuyển từ nhập số sang chuỗi
		
		int size = listStaff.size();// lấy số phần tử của listStaff
		do {
			ArrayList<Staff> staff = new ArrayList<Staff>();//Tạo một list staff
			if(k == 1) {
				System.out.print("Mời bạn nhập ID:");
			}
			else {
				System.out.print("Mời bạn nhập tên:");
			}
			
			String keyWord = sc.nextLine();//Nhập từ khóa để tìm
			for(int i =0;i<size;i++) {
				if(listStaff.get(i).getId().equalsIgnoreCase(keyWord) && k ==1 ) {//Kiểm tra keyword với id trong list
					staff.add(listStaff.get(i));//Nếu đúng thì cho vào list staff 
					break; //Dừng vòng lặp vì ID khác nhau
				}
				else if(listStaff.get(i).getName().equals(keyWord)) {//Kiểm tra keyWord với name trong list
					staff.add(listStaff.get(i));//Nếu đúng thì cho vào list staff và tiếp tục tìm
				}
			}
			showStaff(staff,false);//Hiện kết quả. nếu staff rỗng thì sẽ báo luôn
			
			//Hỏi người dùng có tìm tiếp không
			System.out.print("Bạn có muốn tiếp tục tìm kiếm (c/k): ");
			answer = sc.nextLine();
		}while(answer.equalsIgnoreCase("c"));//Nếu nhập khác c thì dừng vòng lặp
	}

	//Hàm kiểm tra hợp lệ của nhập ngày tháng của addStaff()
	public static boolean isValidDate(String input) {
	    boolean valid = false;
		
	    try {
	        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");//Tạo một đối tượng ngày với định dạng đầu vào dd-MM-yyyy;
	        
	        int day = dateFormat.parse(input).getDate();//Chuyển một chuỗi về ngày
	        int month = dateFormat.parse(input).getMonth()+ 1;//Chuyển một chuỗi về tháng và cộng thêm 1 vì getMonth() bắt đầu từ 0-11
			int year = dateFormat.parse(input).getYear() + 1900;//Chuyển một chuỗi về năm và công thêm 1900 vì getYear() sẽ trừ đi 1900 từ jdk 1.1
	        String output = (day < 10?"0"+day:day )+ "-" + (month < 10 ?("0" + month):month) + "-" + year;
	        
	        valid = input.equals(output);//Kiểm tra năm nhập vào có chuẩn không
	        if(valid) {
	        	//Nếu nhập ngày vào quá ngày hiện tại thì không được chấp nhận
	        	LocalDate currDate = LocalDate.now();
	        	LocalDate inputDate = LocalDate.parse(output,
	                    DateTimeFormatter.ofPattern("dd-MM-yyyy"));
	        	if(inputDate.isAfter(currDate)) {
	        		valid = false;
	        		System.out.println("Bạn đã nhập ngày lớn hơn ngày hiện tại!");
	        	}
	        	
	        }
	        else {
	        	System.out.println("Bạn nhập chưa chính xác !");
	        }
	    } catch (Exception ignore) {//Khi nhập không đúng định dạng dd-MM-yyyy
	    	System.out.println("Bạn nhập không hợp lệ !");
	    }

	    return valid;
	}

	//Hàm lấy bộ phận
	public static Department department(ArrayList<Staff> listStaff,String id) {
		int size = listStaff.size(); //Lấy số phần tử của listStaff
		//duyệt mảng tìm ra các bộ phận trong công ty
		for(int i = 0;i<size;i++) {
			if(listStaff.get(i).getDepartment().getId().equals(id)) {//kiểm tra id đầu vào trùng với id của bộ phận thì trả về
				return listStaff.get(i).getDepartment();
			}
		}
		return null;//Nếu không thấy thì trả về null
	}
	
	//Hàm thêm nhân viên
	public static Object addStaff(ArrayList<Staff> listStaff) {
		System.out.println("1.Thêm nhân viên thông thường");
		System.out.println("2.Thêm nhân viên là cấp quản lý");
		int n;
		do {
			System.out.print("Mời bạn chọn: ");
			n = sc.nextInt();
		}while(n!=1 && n!=2); //Khi n =1 hoặc bằng 2 thì dừng
		String skip = sc.nextLine();//Loại bỏ nút enter khi nhập rồi chuyển sang nhập chuỗi
		if(n == 1) {
			System.out.println("NHẬP THÔNG TIN NHÂN VIÊN THÔNG THƯỜNG");
		}
		else {
			System.out.println("NHẬP THÔNG TIN NHÂN VIÊN CẤP QUẢN LÝ");
		}
		
		//Nhập mã NV và kiểm tra trùng lặp
		int size = listStaff.size();
		String id = "";
		boolean checkId = false;
		do {			
			System.out.print("Mã NV không được để trống: ");
			id = sc.nextLine().trim(); //Loại bỏ khoảng trắng đầu và cuối
			for(int i = 0;i<size;i++) {
				if(id.equalsIgnoreCase(listStaff.get(i).getId())) {//Kiểm tra ID nhập vào có trùng với ID trong list
					checkId = true;
					System.err.println("ID bạn nhập đã bị trùng, xin mời bạn nhập lại!");
					break;
				}
				checkId = false;
			}
		}while(checkId || id.length() == 0);//khi nào id không trùng thì checkId = false và dừng vòng lặp
		
		//Nhập tên nv
		String name;
		do {	
			System.out.print("Tên NV không được để trống: ");
			name = sc.nextLine().trim();
		}while(name.length() == 0);//Nếu tên nhập vào trống hoặc toàn khoảng trắng thì nhập lại
		
		
		//Nhập tuổi nhân viên phải lớn hơn 0
		int age;
		do {	
			System.out.print("Tuổi NV phải lớn hơn 0: ");
			age = sc.nextInt();
		}while(age <= 0);
		
		//Nhập Hệ số lương của nhân viên không được âm
		double payRate;
		do {		
			System.out.print("Hệ số lương phải lớn hơn 0: ");
			payRate = sc.nextDouble();
		}while(payRate <= 0);
		
		//Nhập ngày vào công ty và kiểm tra tính hợp lệ của ngày nhập vào
		skip = sc.nextLine();//Loại bỏ nút enter khi nhập rồi chuyển sang nhập chuỗi
		String date;
		do {
			System.out.print("Vui lòng nhập ngày vào công ty(ngày vào không lớn hơn ngày hiện tại và nhập theo định dạng (dd-MM-yyyy) :");
			date = sc.nextLine();
		}while(!isValidDate(date));
		LocalDate startDate = LocalDate.parse(date,DateTimeFormatter.ofPattern("dd-MM-yyyy"));//Tạo biến lưu trữ ngày với định dạng đầu vào dd-MM-yyyy
		
		//Chọn bộ phận đang làm việc của NV
		System.out.println("Chọn bộ phận của nhân viên");
		System.out.println("1.Sản xuất");
		System.out.println("2.Hoạt động");
		System.out.println("3.Tài chính");
		System.out.println("4.Quản trị");
		System.out.println("5.Tiếp thị / bán hàng");
		System.out.println("6.Điều phối kinh doanh");
		int k;
		do {			
			System.out.print("Bạn chọn(1-6): ");
			k=sc.nextInt();
		}while(k>6 || k<1);
		
		Department department = new Department();//Tạo một đối tượng lưu trữ bộ phận
		
		switch(k) {
		case 1:
			//Kiểm tra xem bộ phận chọn đã có trong list chưa, nếu chưa có thì tạo mới
			department = department(listStaff,"NVX1");
			if(department == null) {
				department = new Department("NVX1", "Sản xuất");
			}
			department.setCurrNumberStaff(department.getCurrNumberStaff()+1);//Tăng số lượng nhân viện hiện có thêm 1
			break;
		case 2:
			//Kiểm tra xem bộ phận chọn đã có trong list chưa, nếu chưa có thì tạo mới
			department = department(listStaff,"NVX2");
			if(department == null) {
				department = new Department("NVX2", "Hoạt động");
			}
			department.setCurrNumberStaff(department.getCurrNumberStaff()+1);//Tăng số lượng nhân viện hiện có thêm 1
			break;
		case 3:
			//Kiểm tra xem bộ phận chọn đã có trong list chưa, nếu chưa có thì tạo mới
			department = department(listStaff,"NVX3");
			if(department == null) {
				department = new Department("NVX3", "Tài chính");
			}
			department.setCurrNumberStaff(department.getCurrNumberStaff()+1);//Tăng số lượng nhân viện hiện có thêm 1
			break;
		case 4:
			//Kiểm tra xem bộ phận chọn đã có trong list chưa, nếu chưa có thì tạo mới
			department = department(listStaff,"NVX4");
			if(department == null) {
				department = new Department("NVX1", "Quản trị");
			}
			department.setCurrNumberStaff(department.getCurrNumberStaff()+1);//Tăng số lượng nhân viện hiện có thêm 1
			break;
		case 5:
			//Kiểm tra xem bộ phận chọn đã có trong list chưa, nếu chưa có thì tạo mới
			department = department(listStaff,"NVX5");
			if(department == null) {
				department = new Department("NVX5", "Tiếp thị / bán hàng");
			}
			department.setCurrNumberStaff(department.getCurrNumberStaff()+1);//Tăng số lượng nhân viện hiện có thêm 1
			break;
		case 6:
			//Kiểm tra xem bộ phận chọn đã có trong list chưa, nếu chưa có thì tạo mới
			department = department(listStaff,"NVX6");
			if(department == null) {
				department = new Department("NVX6", "Điều phối kinh doanh");
			}
			department.setCurrNumberStaff(department.getCurrNumberStaff()+1);//Tăng số lượng nhân viện hiện có thêm 1
			break;
		}
		
		//Nhập số ngày nghỉ phép
		int numDayOff;
		 do {	
			System.out.print("Số ngày nghỉ phép không được âm: ");
			numDayOff = sc.nextInt();
		}while(numDayOff < 0);
		
		if(n==1) {
			//Nhập số ngày làm thêm >=0
			double overtimeHour;
			do {	
				System.out.print("Nhập số giờ làm thêm không được âm: ");
				overtimeHour = sc.nextDouble();
			}while(overtimeHour < 0);
			
			//khởi tạo và trả về một đối tượng nhân viên bình thường
			Staff employee = new Employee(id, name, age, payRate, startDate, department,
				 numDayOff, overtimeHour); //Tạo ra đối tượng nhân viên bình thường
			System.out.println("Thêm thành công nhân viên bình thường!");
			return employee;
		}
		
		//Chọn các chức vụ có sẵn cỏ nhân viên cấp quản lý
		System.out.println("Chọn chức vụ");
		System.out.println("1.Business Leader ");
		System.out.println("2.Project Leader");
		System.out.println("3.Technical Leader");
		do {			
			System.out.print("Bạn chọn(1-3): ");
			k=sc.nextInt();
		}while(k>3 || k<1);
		String position = "";
		//Gán chức vụ vào biến position của cấp quản lý
		if(k == 1) {
			position = "Business";
		}
		else if(k == 2) {
			position = "Project";
		}
		else if(k == 3) {
			position = "Technical";
		}
		
		//khởi tạo và trả về một đối tượng nhân viên cấp quản lý
		Staff manager = new Manager(id, name, age, payRate, startDate, department,
				 numDayOff, position); //Tạo ra đối tượng nhân viên cấp quản lý
		System.out.println("Thêm thành công nhân viên cấp quản lý!");
			return manager;
	}
	
	//Hàm lọc nhân viên theo bộ phận
	public static ArrayList<Staff> staffDepartment(ArrayList<Staff> listStaff,String name) {
		ArrayList<Staff> staff = new ArrayList<Staff>();//Tạo một list tạm thời để trả về
		
		int size = listStaff.size(); //gán số lượng phần tử listStaff cho size
		for(int i =0;i<size;i++) {
			if(listStaff.get(i).getDepartment().getName().equals(name)) {//Khi name trùng với tên của bộ phận
				staff.add(listStaff.get(i));//Thêm vào list staff
			}
		}
		return staff;//Trả về staff
	}

	//Hàm hiển thị nhân viên theo bộ phận
	public static void showStaffDepartment(ArrayList<Staff> listStaff) {
		//Chọn bộ phận để hiển thị nhân viên
		System.out.println("1.Sản xuất");
		System.out.println("2.Hoạt động");
		System.out.println("3.Tài chính");
		System.out.println("4.Quản trị");
		System.out.println("5.Tiếp thị / bán hàng");
		System.out.println("6.Điều phối kinh doanh");
		int k;
		do {			
			System.out.print("Bạn chọn: ");
			k=sc.nextInt();
		}while(k>6 || k<1);//Khi nào k thỏa mãn từ 1 đến 6 thì dừng
		
		//Hiển thị nhân viên của bộ phận và không hiện lương
		switch(k) {
		case 1:
			showStaff(staffDepartment(listStaff,"Sản xuất"),false);
			break;
		case 2:
			showStaff(staffDepartment(listStaff,"Hoạt động"),false);
			break;
		case 3:
			showStaff(staffDepartment(listStaff,"Tài chính"),false);
			break;
		case 4:
			showStaff(staffDepartment(listStaff,"Quản trị"),false);
			break;
		case 5:
			showStaff(staffDepartment(listStaff,"Tiếp thị / bán hàng"),false);
			break;
		case 6:
			showStaff(staffDepartment(listStaff,"Điều phối kinh doanh"),false);
			break;
		}
		
	}

	//Hiển thị các bộ phận đang có trong công ty
	public static void showDepartment(ArrayList<Staff> listStaff) {
		int size = listStaff.size();//Lấy số phần tử của list gán vào size
		System.out.println("All company department information");
		System.out.println("------------------------------------");
		System.out.println("ID    |Current number|NAME");
		
		//lấy thông tin bộ phận của phần tử đầu listStaff vào listDepartmen
		String listDepartment = listStaff.get(0).getDepartment().toString(); 
		
		//Lấy các bộ phận mà có nhân viên
		for(int i = 1;i<size;i++) {
			String currDepar = listStaff.get(i).getDepartment().getId();//Lấy id bộ phận của vòng lặp hiện tại
			if(!listDepartment.contains(currDepar)) {//Thực hiện khi id hiện tại không có trong listDepartment
				 //Thêm thông tin bộ phận khác nhau vào listDepartment
				listDepartment += ("\n" + listStaff.get(i).getDepartment().toString());
			}
		}
		System.out.println(listDepartment);//hiển thị thông tin các bộ phận của công ty
	}

	
	//Hiển thị thông tin nhân viên
	@SuppressWarnings("resource")
	public static void showStaff(ArrayList<Staff> listStaff,boolean showSalary) {
		Formatter formatter = new Formatter();//Khởi tạo định dạng hiển thị của String
		int size = listStaff.size(); //Gán số lượng phần tử trong listStaff vào size
		
		//Thông báo khi list rỗng
		if(size == 0) {
			System.err.println("Chưa có dữ liệu nhân viên trong công ty");
			return;
		}
		System.out.println("                                                        Staff information");
		System.out.println("______________________________________________________________________________________________________________________________________________\n");			
		//Định dạng hiển thị theo cột
		System.out.println(formatter.format("%1$-3s|%2$-8s|%3$-17s|%4$-3s|%5$-22s|%6$-17s|%7$-14s|%8$-18s|%9$-10s|%10$-10s|%11$-13s","NO.",
				"ID","Name","Age",
				"Department","Position","Start Date",
				"Number Day Off","Overtime","Pay Rate","Salary(VND)"));
		
		for(int i = 0;i<size;i++) {	
			System.out.println("______________________________________________________________________________________________________________________________________________\n");			
			System.out.print(String.format("%1$-3s|",i+1));
			listStaff.get(i).displayInformation();
			
			//Hiển thị tiền lương của NV
			if(showSalary) {
				 double calary = ((ICalculator)listStaff.get(i)).calculateSalary(); //Gán lương của phần tử hiện tại vào calary
				System.out.print(String.format("%.1f",calary) + "\n");//Dịnh dạng hiển thị đầy đủ số sau 1 số thập phân
			}
			else {
				System.out.print("\n");
			}
			System.out.println("______________________________________________________________________________________________________________________________________________");

		}
	}
	
	//Hàm nhập dữ liệu có sẵn
	public static void addFast(ArrayList<Staff> listStaff,boolean checkFast) {
		if(checkFast) {//Nếu addFast nhập rồi thì không nhập được nữa vì sẽ trùng nhau
			System.err.println("Bạn đã nhập rồi !");
			return;
		}
		//Kiểm tra bộ phận đã có trong list chưa, nếu chưa thì tạo mới
		Department depa1 = department(listStaff,"NVX1");
		if(depa1 == null) {
			depa1 = new Department("NVX1", "Sản xuất" );
		}
		
		Department depa2 = department(listStaff,"NVX2");
		if(depa2 == null) {
			depa2 = new Department("NVX2", "Hoạt động" );
		}
		
		Department depa3 = department(listStaff,"NVX3");
		if(depa3 == null) {
			depa3 = new Department("NVX3", "Tài chính" );
		}
		
		Department depa4 = department(listStaff,"NVX4");
		if(depa4 == null) {
			depa4 = new Department("NVX4", "Quản trị" );
		}
		
		Department depa5 = department(listStaff,"NVX5");
		if(depa5 == null) {
			depa5 = new Department("NVX5", "Tiếp thị / bán hàng");
		}
		
		Department depa6 = department(listStaff,"NVX6");
		if(depa6 == null) {
			depa6 = new Department("NVX6", "Điều phối kinh doanh");
		}

		//Thêm các nhân viên vào list
		//1
		listStaff.add((Staff)(new Employee("H176933", "Hoàng Văn Đắm", 24, 1.5
				, LocalDate.parse("11-09-2017",DateTimeFormatter.ofPattern("dd-MM-yyyy"))
				, depa1,20, 65.0)));
		depa1.setCurrNumberStaff(depa1.getCurrNumberStaff()+1);//Tăng số lượng nhân viện hiện có của bộ phận
		//2
		listStaff.add((Staff)(new Employee("H176934", "Cao Mạnh Hùng", 20, 1.1
				, LocalDate.parse("12-12-2000",DateTimeFormatter.ofPattern("dd-MM-yyyy"))
				, depa3,29, 70.2)));
		depa3.setCurrNumberStaff(depa3.getCurrNumberStaff()+1);//Tăng số lượng nhân viện hiện có của bộ phận
		//3
		listStaff.add((Staff)(new Employee("H176935", "Vũ Cao Minh", 24, 1.5
				, LocalDate.parse("10-12-2009",DateTimeFormatter.ofPattern("dd-MM-yyyy"))
				,depa2,20, 2.6)));
		depa2.setCurrNumberStaff(depa2.getCurrNumberStaff()+1);//Tăng số lượng nhân viện hiện có của bộ phận
		//4
		listStaff.add((Staff)(new Employee("H176936", "Hoàng Quốc Bảo", 28, 1.9
				, LocalDate.parse("13-06-1999",DateTimeFormatter.ofPattern("dd-MM-yyyy"))
				, depa1,20, 40.0)));
		depa1.setCurrNumberStaff(depa1.getCurrNumberStaff()+1);//Tăng số lượng nhân viện hiện có của bộ phận
		//5
		listStaff.add((Staff)(new Employee("H176937", "Sa Đình Cường", 26, 1.2
				, LocalDate.parse("11-08-2020",DateTimeFormatter.ofPattern("dd-MM-yyyy"))
				, depa5,21, 98.0)));
		depa5.setCurrNumberStaff(depa5.getCurrNumberStaff()+1);//Tăng số lượng nhân viện hiện có của bộ phận
		//6
		listStaff.add((Staff)(new Employee("H176938", "Hoàng Văn Hậu", 33, 2.5
				, LocalDate.parse("14-12-2013",DateTimeFormatter.ofPattern("dd-MM-yyyy"))
				, depa6,20, 14.8)));
		depa6.setCurrNumberStaff(depa6.getCurrNumberStaff()+1);//Tăng số lượng nhân viện hiện có của bộ phận


		//7
		listStaff.add((Staff) new Manager("H001517", "Nguyễn Văn Sức", 38 ,1.9
				,LocalDate.parse("14-02-2002",DateTimeFormatter.ofPattern("dd-MM-yyyy"))
				, depa1,31, "Business"));
		depa1.setCurrNumberStaff(depa1.getCurrNumberStaff()+1);//Tăng số lượng nhân viện hiện có của bộ phận
		//8
		listStaff.add((Staff) new Manager("H001518", "Nguyễn Văn Vương", 35 ,2.1
				,LocalDate.parse("14-03-2002",DateTimeFormatter.ofPattern("dd-MM-yyyy"))
				, depa3,30, "Project"));
		depa3.setCurrNumberStaff(depa3.getCurrNumberStaff()+1);//Tăng số lượng nhân viện hiện có của bộ phận
		//9
		listStaff.add((Staff) new Manager("H001519", "Hoàng Trọng Tấn", 39 ,1.6
				,LocalDate.parse("16-04-2002",DateTimeFormatter.ofPattern("dd-MM-yyyy"))
				, depa2,32, "Technical"));
		depa2.setCurrNumberStaff(depa2.getCurrNumberStaff()+1);//Tăng số lượng nhân viện hiện có của bộ phận
		//10
		listStaff.add((Staff) new Manager("H001520", "Trần Anh Túc", 28 ,1.7
				,LocalDate.parse("10-09-2002",DateTimeFormatter.ofPattern("dd-MM-yyyy"))
				, depa4,31, "Business"));
		depa4.setCurrNumberStaff(depa4.getCurrNumberStaff()+1);//Tăng số lượng nhân viện hiện có của bộ phận
		
		System.out.println("Nhập dữ liệu nhanh thành công!");
		System.out.println("--------------------------------");
		showStaff(listStaff,false);//Hiển thị dữ liệu ra nhưng chưa hiện lương
	}

}
