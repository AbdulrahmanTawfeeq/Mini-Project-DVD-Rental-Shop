package DvdPackage;

public class Manager1 extends Person {
	public Manager1() {
		// TODO Auto-generated constructor stub
		super();
	}

	public Manager1(String username, String password) {
		super(username, password);
		// TODO Auto-generated constructor stub
	}

	public void hireEmp() {
		connectToDB();
		FormOfSignUp();

		try {
			String query2 = "insert into users(username,password,firstname,lastname,email,address,phone,type,disactivated)values('"
					+ this.username + "','" + this.password + "','" + this.firstName + "','" + this.lastName + "','"
					+ this.email + "','" + this.address + "','" + this.phone + "','Employee','" + 0
					+ "')";
			stmt=conn.createStatement();
			if(stmt.executeUpdate(query2)==1) {
				System.out.println("Hired");
			}else {
				System.out.println("Error");
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
	}

	
}
