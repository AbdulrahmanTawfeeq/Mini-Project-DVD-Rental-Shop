package DvdPackage;

import java.util.Scanner;

public class Manager extends Person {

	/**
	 * Once this class declared and instantiated somewhere and the program is Run,
	 * it will ask you to define the current user by login or sign up. Because the
	 * default constructor inside the parent class has logic to ask for login.
	 */
	public Manager() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * Once this class declared and instantiated somewhere and the program is Run,
	 * it will ask you to define the current user by login or sign up. Because the
	 * constructor inside the parent class has logic to ask for login.
	 */
	public Manager(String username, String password) {
		super(username, password);
		// TODO Auto-generated constructor stub
	}

	/**
	 * This method goes to the users table in the database and makes disactivated
	 * field equal to 0 according to the username of the employee.
	 * 
	 * @param EmployeeUserName
	 */
	public void hire(String EmployeeUserName) {
		if (this.session() != null) {
			String query = "UPDATE users SET disactivated=0 WHERE username='" + EmployeeUserName + "'";
			try {
				stmt = conn.createStatement();
				if (stmt.executeUpdate(query) == 1) {
					System.out.println(EmployeeUserName + " Employee is being hired now.");
				} else {
					System.out.println("Process failed!");
				}
			} catch (Exception e) {
				System.out.println(e.getMessage());
			}
		} else {
			System.out.println("Login first to be able to hire an employee!");
		}
	}

	/**
	 * This method goes to the users table in the database and makes disactivated
	 * field equal to 1 according to the username of the employee.
	 * 
	 * @param EmployeeUserName
	 */
	public void fire(String EmployeeUserName) {
		if (this.session() != null) {
			String query = "UPDATE users SET disactivated=1 WHERE username='" + EmployeeUserName + "'";
			try {
				stmt = conn.createStatement();
				if (stmt.executeUpdate(query) == 1) {
					System.out.println(EmployeeUserName + " Employee is being fired now.");
				} else {
					System.out.println("Process failed!");
				}
			} catch (Exception e) {
				System.out.println(e.getMessage());
			}
		} else {
			System.out.println("Login first to be able to fire an employee!");
		}
	}

	/**
	 * To let the Manager be able to read all the messages he received from the
	 * customers. He can also mark them as read to make the inbox empty.
	 */
	public void inbox() {
		if (this.session() != null) {
			String query = "SELECT * FROM contactus WHERE status='unread' order by `atime` asc";
			try {
				stmt = conn.createStatement();
				result = stmt.executeQuery(query);
				System.out.println("\n-----INBOX MESSAGES-----\n");
				if (!result.next()) {
					System.out.println("   No Unread Messages!");
				} else {
					result = stmt.executeQuery(query);
					while (result.next()) {
						System.out.println(
								" _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _\n|");
						String from = result.getString("from");
						String topic = result.getString("to");
						String message = result.getString("message");
						String time = result.getString("atime");
						System.out.println("| From: " + from);
						System.out.println("| Topic: " + topic);
						System.out.println("| Message: ");
						if (message.split("\\.").length > 0) {
							String[] arr = message.split("\\.");
							for (int i = 0; i < arr.length; i++) {
								if (i == 1) {
									System.out.println("| \t " + arr[i]);
								} else {
									System.out.println("| \t  " + arr[i]);
								}
							}
						} else {
							System.out.println("| \t " + message);
						}
						System.out.println("| \t  " + time);
						System.out.println(
								"|_ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _");
						System.out.println("\n");
					}
					System.out.println("Mark them as read(Yes or No): ");
					Scanner scan = new Scanner(System.in);
					String value = scan.nextLine();
					if (value.equalsIgnoreCase("Yes")) {
						String query2 = "UPDATE contactus SET status='read'";
						stmt = conn.createStatement();
						if (stmt.executeUpdate(query2) == 1) {

						}
					}
				}

			} catch (Exception e) {
				e.printStackTrace();
			}

		} else {
			System.out.println("Login first to be able to fire an employee!");
		}
	}

	/**
	 * This method is used by the manager to sign up the new Employee to the system.
	 * It uses a method called FormOfSignUp() which is inside the Person class to
	 * let the manager fill out the new Employee info. Then it uses insert into
	 * query to finish the signing up process
	 */
	public void hireEmpolyee() {
		if (this.session() != null) {
			System.out.println("\n----Hire----");
			FormOfSignUp();
			try {
				String Type = "Employee";
				int disactivated = 0;
				String query1 = "insert into users(username,password,firstname,lastname,email,address,phone,type,disactivated)values('"
						+ this.username + "','" + this.password + "','" + this.firstName + "','" + this.lastName + "','"
						+ this.email + "','" + this.address + "','" + this.phone + "','" + Type + "','" + disactivated
						+ "')";
				if (conn.prepareStatement(query1).executeUpdate() == 1) {
					System.out.println(this.username + " hired successfully");
				} else {
					System.out.println("Hiring process is failed!");
				}

			} catch (Exception e) {
				System.out.println(e.getMessage());
			}
		} else {
			System.out.println("Login First! to hire an Employee.");
		}

	}

	/**
	 * This method deletes an employee from the database based on his user name
	 * 
	 * @param EmployeeUserName
	 */
	public void fireEmployee(String EmployeeUserName) {
		connectToDB();
		try {
			String query = "DELETE FROM users WHERE username='" + EmployeeUserName + "'";
			stmt = conn.createStatement();
			if (stmt.executeUpdate(query) == 1) {
				System.out.println(EmployeeUserName + " has been fired.");
			} else {
				System.out.println("Firing process failed! may be this username is not found in the database.");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
