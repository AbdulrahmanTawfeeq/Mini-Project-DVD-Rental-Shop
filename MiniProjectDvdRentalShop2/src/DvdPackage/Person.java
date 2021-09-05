package DvdPackage;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Abstract class, not going to be declared. Parent of Employee and costumer
 * classes. This class contains the common attributes and methods that all child
 * classes may have. This class uses several methods inherited from the indirect
 * superclass (Services) to check the inputs from the user if it is valid or
 * not.
 * 
 * @author Shadiar's group
 *
 */
public abstract class Person extends DBconnection {

	protected String username;
	protected String password;
	protected String firstName;
	protected String lastName;
	protected String email;
	protected String address;
	protected String phone;
	private String session;

	/**
	 * Default constructor has a logic to force the user to login or sign up to use
	 * the system
	 */
	public Person() {
		// TODO Auto-generated constructor stub
		String status = this.logIn();
		while (status == null) {
			Scanner scan = new Scanner(System.in);
			System.out.println("Type 'Up' to sign up, or any key to try again.");
			status = scan.nextLine();
			if (!status.equalsIgnoreCase("Up")) {
				status = null;
				this.logIn();
			}

		}
		if (status.equalsIgnoreCase("Up")) {
			this.signUp();
		}
	}

	/**
	 * Constructor using username and password fields and has logic to ask for login
	 * 
	 * @param username
	 * @param password
	 */
	public Person(String username, String password) {
		super();
		this.username = username;
		this.password = password;
		this.logIn(username, password);
		while (this.session() == null) {
			System.out.println();
			logIn();
		}
	}

	/**
	 * catches the user name of the user once he or she login or sign up
	 * 
	 * @return loggedIn protected property which is null or contain the user name
	 */
	protected String session() {
		return this.session;
	}

	/**
	 * uses connectToDB() method from the superclass to get connection to the
	 * database. asks the user to type specific information to sign up himself uses
	 * several private methods in the same class or from Services class to check the
	 * inputs from the user are valid or not.
	 * 
	 * @return this.session as String which contains the username property or null
	 *         to be used in the session method.
	 */
	protected String signUp() {
		String Type = this.getClass().getSimpleName();
		if (session() != null) {
			System.out.println("Logout first!");
			Scanner scan = new Scanner(System.in);
			System.out.println("Type 'L' to logout.");
			String logout = scan.nextLine();
			if (logout.equals("L")) {
				LogOut();
				signUp();
			}
		} else if (Type.equals("Employee")) {
			System.out.println("Ask the Manager to hire you!");
		} else {
			connectToDB(); // he should connect to db first to sign up
			FormOfSignUp();
			try {
				int disactivated = 0;
				String query2 = "insert into users(username,password,firstname,lastname,email,address,phone,type,disactivated)values('"
						+ this.username + "','" + this.password + "','" + this.firstName + "','" + this.lastName + "','"
						+ this.email + "','" + this.address + "','" + this.phone + "','" + Type + "','" + disactivated
						+ "')";
				if (conn.prepareStatement(query2).executeUpdate() == 1) {
					System.out.println("You are signed up successfully");
					this.session = this.username;
					System.out.println("Welcome " + this.session + "(" + Type + ")");
				}

				if (Type.equals("Customer")) {
					String query4 = "SELECT iduser FROM users WHERE username='" + this.username + "'";
					result = stmt.executeQuery(query4);
					int CusID = 0;
					while (result.next()) {
						CusID = result.getInt("iduser");
					}
					if (CusID != 0) {
						String query3 = "insert into balance(customerID, recietNo, amount)value(" + CusID + ",0,0.0)";
						if (conn.prepareStatement(query3).executeUpdate() == 1) {
							System.out.println("Your Balance is 0, Contact the Empolyee to deposit amount of money.");
						}
					}
				}

			} catch (Exception e) {
				System.out.println(e.getMessage());
			}

		}
		return this.session;
	}

	/**
	 * This method is just a sign up form to let the user fill out his info to sign
	 * up to the system.
	 */
	protected void FormOfSignUp() {
		String Type = this.getClass().getSimpleName();
		if (!Type.equals("Manager")) {
			System.out.println("-----Sign Up-----");
		}

		try {
			Scanner scan = new Scanner(System.in);
			System.out.println("User Name: ");
			this.username = scan.nextLine();
			while (isExixts("username", this.username) || checkEmptyFields(this.username)) {
				if (isExixts("username", this.username)) {
					System.out.println("This user name is already Taken");
				} else if (checkEmptyFields(this.username)) {
					System.out.println("Fill out the empty field!");
				}
				this.username = scan.nextLine();
			}
			System.out.println("Password: ");
			this.password = scan.nextLine();
			while (!GoodPassword(this.password) || this.password.length() < 8) {
				if (this.password.length() < 8) {
					System.out.println("Password should not be less than 8 chars");
				} else if (!GoodPassword(this.password)) {
					System.out.println("Password should contans letters and numbers");
				}
				System.out.println("Password: ");
				this.password = scan.nextLine();
			}
			System.out.println("Confirm Password: ");
			String ConfirmPassWord = scan.nextLine();
			while (!this.password.equals(ConfirmPassWord) || checkEmptyFields(this.password)) {
				if (!this.password.equals(ConfirmPassWord)) {
					System.out.println("Passwords are not matching!");
				} else if (checkEmptyFields(this.password)) {
					System.out.println("Fill out the empty field!");
				}
				System.out.println("Password: ");
				this.password = scan.nextLine();
				System.out.println("Confirm Password: ");
				ConfirmPassWord = scan.nextLine();
			}
			System.out.println("First Name: ");
			this.firstName = scan.nextLine();
			while (!checkName(this.firstName) || checkEmptyFields(this.firstName)) {
				if (!checkName(this.firstName)) {
					System.out.println("First Name should be Lettres only!");
				} else if (checkEmptyFields(this.firstName)) {
					System.out.println("Fill out the empty field!");
				}
				System.out.println("First Name: ");
				this.firstName = scan.nextLine();
			}
			System.out.println("Last Name: ");
			this.lastName = scan.nextLine();
			while (!checkName(this.lastName) || checkEmptyFields(this.lastName)) {
				if (!checkName(this.lastName)) {
					System.out.println("Last Name should be Lettres only!");
				} else if (checkEmptyFields(this.lastName)) {
					System.out.println("Fill out the empty field!");
				}
				System.out.println("Last Name: ");
				this.lastName = scan.nextLine();
			}
			System.out.println("E-mail: ");
			this.email = scan.nextLine();
			while (isExixts("email", this.email) || !chechEmail(this.email) || checkEmptyFields(this.email)) {
				if (!chechEmail(this.email)) {
					System.out.println("Email is invalid!!");
				} else if (checkEmptyFields(this.email)) {
					System.out.println("Fill out the empty field!");
				} else if (isExixts("email", this.email)) {
					System.out.println("This email is used before, use another one!");
				}
				System.out.println("E-mail: ");
				this.email = scan.nextLine();
			}
			System.out.println("Address: ");
			this.address = scan.nextLine();
			while (checkEmptyFields(this.address)) {
				System.out.println("Fill out the empty field!");
				System.out.println("Address: ");
				this.address = scan.nextLine();
			}

			System.out.println("Phone Number: ");
			this.phone = scan.nextLine();
			while (isExixts("phone", this.phone) || !checkInt(this.phone) || checkEmptyFields(this.phone)) {
				if (checkEmptyFields(this.phone)) {
					System.out.println("Fill out the empty field!");
				} else if (!checkInt(this.phone)) {
					System.out.println("Phone Number should be Integer only!");
				} else if (isExixts("phone", this.phone)) {
					System.out.println("This phone number is used before by another user, use another one!");
				}
				System.out.println("Phone Number: ");
				this.phone = scan.nextLine();
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	/**
	 * uses connectToDB() method to get connection to the database. asks the user to
	 * type his username and password to login himself. fetches data from database
	 * and compare it with the inputs
	 * 
	 * @return this.session property which will contain the username in case there
	 *         is a match, otherwise null. to be used by the session method.
	 */
	protected String logIn() {
		connectToDB(); // he should connect to db first to login
		if (session() != null) {
			System.out.println("You already Logged in.");
		} else {
			System.out.println("-----Login-----");
			Scanner scan = new Scanner(System.in);
			System.out.println("User Name: ");
			String userName = scan.nextLine();
			while (checkEmptyFields(userName)) {
				System.out.println("Fill out the empty field");
				System.out.println("User Name: ");
				userName = scan.nextLine();
			}
			System.out.println("Password: ");
			String passWord = scan.nextLine();
			while (checkEmptyFields(passWord)) {
				System.out.println("Fill out the empty field");
				System.out.println("Password: ");
				passWord = scan.nextLine();
			}
			try {
				String query = "Select username,password,type From users";
				stmt = conn.createStatement();
				result = stmt.executeQuery(query);
				String UserType = this.getClass().getSimpleName();
				while (result.next()) {
					String username = result.getString("username");
					String password = result.getString("password");
					String Type = result.getString("type");
					if (userName.equals(username) && passWord.equals(password) && Type.equals(UserType)) {
						this.session = userName;
					}
				}
				if (this.session.equals(userName)) {
					System.out.println("Welcome " + this.session + "(" + UserType + ")");
				} else {
					System.out.println("Wrong User Name or Password!!!");
				}

			} catch (Exception e) {
				System.out.println(e.getMessage());
			}
		}
		return this.session;

	}

	/**
	 * makes the session equal to null, means the user should login again to be able
	 * to react with the program
	 */
	protected void LogOut() {
		String name = this.session;
		this.session = null;
		String Type = this.getClass().getSimpleName();
		System.out.println(Type + " " + name + ", you are logged out.");
	}

	/**
	 * Overloaded login method uses different parameters. we can use the constructor
	 * which accept parameters to pass username and ppassword directly as onother
	 * way instead of scanner.
	 * 
	 * @param userName
	 * @param passWord
	 * @return
	 */
	protected String logIn(String userName, String passWord) {
		connectToDB(); // he should connect to db first to login
		if (session() != null) {
			System.out.println("You already Logged in.");
		} else {
			try {
				if (checkEmptyFields(userName) && checkEmptyFields(passWord)) {
					System.out.println("Login Failed: Fill out the username & password parameters!");
				} else if (checkEmptyFields(userName)) {
					System.out.println("Login Failed: Fill out the username parameter!");
				} else if (checkEmptyFields(passWord)) {
					System.out.println("Login Failed: Fill out the password parameter!");
				}
				String query = "Select username,password,type From users";
				stmt = conn.createStatement();
				result = stmt.executeQuery(query);
				String UserType = this.getClass().getSimpleName();
				while (result.next()) {
					String username = result.getString("username");
					String password = result.getString("password");
					String Type = result.getString("type");
					if (userName.equals(username) && passWord.equals(password) && Type.equals(UserType)) {
						this.session = userName;
					}
				}
				if (this.session.equals(userName)) {
					String Type = this.getClass().getSimpleName();
					System.out.println("Welcome " + this.session + "(" + Type + ")");
				}

			} catch (Exception e) {
				if (e.getMessage() == null) {
					System.out.println("Login Failed: Wrong username or password!!");
				} else {
					System.out.println(e.getMessage());
				}

			}
		}
		return this.session;

	}

	/**
	 * checks the username, email and phone number that the user will input when
	 * signing up, is it taken or not, because all these values are unique.
	 * 
	 * @param field
	 * @param value
	 * @return true if the value is already taken
	 */
	private final boolean isExixts(String field, String value) {
		boolean check = false;
		try {
			String query = "Select " + field + " From users where " + field + "='" + value + "'";
			stmt = conn.createStatement();
			result = stmt.executeQuery(query);
			String valueFromDB;
			while (result.next()) {
				valueFromDB = result.getString(field);
				if (valueFromDB != null && valueFromDB.equals(value)) {
					check = true;
				} else {
					check = false;
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return check;

	}

	/**
	 * Because Person class inherits from DBconnection, this method can search for a
	 * movie in the database. searches for a movie in the database based on the
	 * title which is a unique value. prints all the information about the movie. it
	 * is here to let the the Employee and the user be able to search for dvd movie
	 * title. The person should be logged in in order to get connection to the
	 * database to search, otherwise null value will be returned. It is used when
	 * the Employee want to insert DVD in the database, this function will tell him
	 * if he inserted the same DVD movie before or not.
	 * 
	 * @param title
	 * @return title, if there is a match. otherwise, it returns null
	 */
	protected final String searchTitle(String title) {
		String Title = null;
		try {
			if (this.session != null) {
				String query = "Select * From dvdmovie where title LIKE '" + title + "'";
				stmt = conn.createStatement();
				result = stmt.executeQuery(query);
				while (result.next()) {
					Title = result.getString("title");
					if (Title != null) {
						formatingPrint();
						result(Title); // calling for the method defined in the same class to print the search results
					}
				}

			} else {
				System.out.println("Login First!");
			}

		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		if (title.equalsIgnoreCase(Title)) {
			return title;
		} else {
			return null;
		}

	}

	/**
	 * methods provided to all kind of users which are logged in the system to
	 * search for every detail about the movie. this method uses several methods
	 * from the indirect superclass Services.. once you run it, it will ask you to
	 * select the term that you want to search under, then to type the value of the
	 * search.
	 */
	protected final void searchFor() {
		if (this.session != null) {
			ArrayList<String> terms = new ArrayList<String>(10);
			terms.add("Title");
			terms.add("Genre");
			terms.add("Release Date");
			terms.add("Rent Price");
			terms.add("Sale Price");
			terms.add("On Sale");
			terms.add("Duration");
			terms.add("Is Rented");
			terms.add("Country");
			terms.add("Age Rating");
			terms.add("Times of Renting");
			try {
				String query = null;
				Scanner scan = new Scanner(System.in);
				System.out.println("-----Search-----");
				for (String string : terms) {
					System.out.print(string + " | ");
				}
				System.out.println();
				System.out.println("Select one of those terms above to search about: ");
				String term = scan.nextLine();
				while (!terms.contains(term)) {
					System.out.println("Select only from those terms above to search about!");
					term = scan.nextLine();
				}
				System.out.println(term + ": ");
				String search = scan.nextLine();
				while (checkEmptyFields(search)) {
					System.out.println("Type something to search for!");
					search = scan.nextLine();
				}

				if (term.equals("Genre")) {
					query = "Select * From dvdmovie where genre LIKE '" + search + "%'";
				} else if (term.equals("Howmany Times Rented")) {
					while (!checkInt(search)) {
						System.out.println("Type Integer only!");
						System.out.println(term + ": ");
						search = scan.nextLine();
					}
					int searchInt = Integer.parseInt(search);
					query = "Select * From dvdmovie where noOfTimesRented='" + searchInt + "' OR noOfTimesRented='"
							+ (searchInt + 1) + "' OR noOfTimesRented='" + (searchInt - 1) + "'";

				} else if (term.equals("Title")) {
					query = "Select * From dvdmovie where title LIKE '" + search + "%'";
				} else if (term.equals("Release Date")) {
					query = "Select * From dvdmovie where releasedate LIKE '" + search + "'";
				} else if (term.equals("Rent Price")) {
					while (!checkDouble(search)) {
						System.out.println("Type Integer or Double only!");
						System.out.println(term + ": ");
						search = scan.nextLine();
					}
					double searchDouble = Double.parseDouble(search);
					query = "Select * From dvdmovie where rentprice='" + searchDouble + "' OR rentprice='"
							+ (searchDouble + 0.5) + "' OR rentprice='" + (searchDouble - 0.5) + "' OR rentprice='"
							+ (searchDouble + 1) + "' OR rentprice='" + (searchDouble - 1) + "'";

				} else if (term.equals("Sale Price")) {
					while (!checkDouble(search)) {
						System.out.println("Type Integer or Double only!");
						System.out.println(term + ": ");
						search = scan.nextLine();
					}
					double searchDouble = Double.parseDouble(search);
					query = "Select * From dvdmovie where saleprice='" + searchDouble + "' OR rentprice='"
							+ (searchDouble + 0.5) + "' OR rentprice='" + (searchDouble - 0.5) + "' OR rentprice='"
							+ (searchDouble + 1) + "' OR rentprice='" + (searchDouble - 1) + "'";

				} else if (term.equals("On Sale")) {
					while (!checkBoolean(search)) {
						System.out.println("Type True or False only!");
						System.out.println(term + ": ");
						search = scan.nextLine();
					}
					boolean searchBoolean = Boolean.parseBoolean(search);
					query = "Select * From dvdmovie where onsale LIKE '" + searchBoolean + "'";
				} else if (term.equals("Duration")) {
					query = "Select * From dvdmovie where duration LIKE '" + search + "%'";
				} else if (term.equals("Is Rented")) {
					while (!checkBoolean(search)) {
						System.out.println("Type True or False only!");
						System.out.println(term + ": ");
						search = scan.nextLine();
					}
					boolean searchBoolean = Boolean.parseBoolean(search);
					query = "Select * From dvdmovie where isrented LIKE '" + searchBoolean + "'";
				} else if (term.equals("Country")) {
					query = "Select * From dvdmovie where country LIKE '" + search + "%'";
				} else if (term.equals("Age Rating")) {
					while (!checkInt(search)) {
						System.out.println("Type Integer only!");
						System.out.println(term + ": ");
						search = scan.nextLine();
					}
					int searchInt = Integer.parseInt(search);
					query = "Select * From dvdmovie where agerating='" + searchInt + "' OR agerating='"
							+ (searchInt + 1) + "' OR agerating='" + (searchInt - 1) + "'";
				}
				stmt = conn.createStatement();
				result = stmt.executeQuery(query);
				formatingPrint();
				while (result.next()) {
					String Title = result.getString("title");
					result(Title);// calling for the method defined in the same class to print the search results

				}
			} catch (Exception e) {
				System.out.println(e.getMessage());
			}
		} else {
			System.out.println("Login First!");
		}
	}

	protected final void formatingPrint() {
		System.out.println("Title\t\t\t" + "Genre\t\t" + "Release Date\t" + "Rental Price\t" + "Sale Price\t"
				+ "On Sale\t\t" + "Duration\t" + "Is Rented\t" + "Country\t\t" + "Age Rating\t" + "Times of Renting\t"
				+ "Copies\t");

		System.out.println("-----\t\t\t" + "-----\t\t" + "------------\t" + "------------\t" + "----------\t"
				+ "-------\t\t" + "--------\t" + "--------\t" + "-------\t\t" + "----------\t" + "----------------\t"
				+ "------\t");
	}

	/**
	 * Method for printing the results, it is related to the search methods
	 */
	protected final void result(String Title) {
		try {
			NumberFormat fmt = NumberFormat.getCurrencyInstance();
			String Genre = result.getString("genre");
			String Releasedate = result.getString("releasedate");
			double RentalPrice = result.getDouble("rentPrice");
			double SalePrice = result.getDouble("salePrice");
			boolean OnSale = result.getBoolean("onSale");
			String Duration = result.getString("duration");
			boolean IsRented = result.getBoolean("isRented");
			String Country = result.getString("country");
			String Agerating = result.getString("agerating");
			int NumOfTimesRented = result.getInt("noOfTimesRented");
			int NoOfCopies = result.getInt("copies");
			System.out.println(Title + "   \t\t" + Genre + "\t\t" + Releasedate + "\t\t" + fmt.format(RentalPrice)
					+ "\t\t" + fmt.format(SalePrice) + "\t\t" + OnSale + "\t\t" + Duration + "\t\t" + IsRented + "\t\t"
					+ Country + "\t\t" + Agerating + "\t\t" + NumOfTimesRented + "\t\t\t" + NoOfCopies);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	public String getFirstName() {
		return firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public String getUsername() {
		return username;
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return username + " " + password;
	}

}
