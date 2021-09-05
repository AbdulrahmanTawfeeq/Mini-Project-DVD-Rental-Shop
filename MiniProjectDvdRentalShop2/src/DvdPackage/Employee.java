package DvdPackage;

import java.util.Scanner;

/**
 * 
 * @author Shadiar's group
 *
 */
public class Employee extends Person {

	/**
	 * Once this class declared and instantiated somewhere and the program is Run,
	 * it will ask you to define the current user by login or sign up. Because the
	 * default constructor inside the parent class has logic to ask for ligin.
	 */
	public Employee() {
		// TODO Auto-generated constructor stub
		super();
	}

	/**
	 * This is another way we can use it to login by passing the username and the
	 * password as parameters. Because the constructor inside the parent class has
	 * logic to ask for ligin.
	 * 
	 * @param username
	 * @param password
	 */
	public Employee(String username, String password) {
		super(username, password);
		// TODO Auto-generated constructor stub
	}

	/**
	 * The Employee will be able to add DVD to the database in case he is logged in
	 * and he is the ADMIN. it uses searchTitle method inherited from the superClass
	 * Person to check if the DVD is already inserted or not. if not, it uses
	 * insertDVDinto which is defied in the same class of the Employee as private
	 * final to be not usable by any other classes.
	 * 
	 * @param dvd
	 */
	public void addDVD(DVD... dvd) {
		// TODO Auto-generated method stub
		if (this.session() != null) {
			if (!checkDisActivity(this.session())) {
				try {
					for (int i = 0; i < dvd.length; i++) {
						String title = searchTitle(dvd[i].getTitle());
						if (title == null) {
							insertDVDinto(dvd[i]); // to insert it into dvdmovie table in the database
						} else {
							System.out.println(dvd[i].getTitle() + " is already inserted!");
						}

					}

				} catch (Exception e) {
					e.printStackTrace();
				}
			} else {
				System.out.println("Dear " + this.session()
						+ ", you currently cannot add DVD, ask the Manager to give you permission!");
			}
		} else {
			System.out.println("Login first!");
		}

	}

	/**
	 * Overloaded method. When it is called, the employee will define the DVD from
	 * this method to add it to the database.
	 */
	public void addDVD() {
		// TODO Auto-generated method stub
		if (this.session() != null) {
			if (!checkDisActivity(this.session())) {
				try {
					DVD dvd = new DVD();
					String title = searchTitle(dvd.getTitle());
					if (title == null) {
						insertDVDinto(dvd); // to insert it into dvdmovie table in the database
					} else {
						System.out.println("Sorry, " + dvd.getTitle() + " is already inserted before!");
					}

				} catch (Exception e) {
					e.printStackTrace();
				}
			} else {
				System.out.println("Dear " + this.session()
						+ ", you currently cannot add DVD, ask the Manager to give you permission!");
			}
		} else {
			System.out.println("Login first!");
		}

	}

	/**
	 * Method to insert the DVD inside the dvdmovie table in the javadb database. It
	 * is private so it can not be used outside this class.
	 * 
	 * @param dvd
	 */
	private final void insertDVDinto(DVD dvd) {
		try {

			String query = "insert into dvdmovie(title,genre,releasedate,rentprice,onsale,saleprice,duration,isrented,country,agerating,copies)values('"
					+ dvd.getTitle() + "','" + dvd.getGenre() + "','" + dvd.getReleasedate() + "','"
					+ dvd.getRentalPrice() + "','" + dvd.isOnSale() + "','" + dvd.getSalePrice() + "','"
					+ dvd.getDuration() + "','" + dvd.isRented() + "','" + dvd.getCountry() + "','" + dvd.getAgerating()
					+ "','" + dvd.getNoOfCopies() + "')";
			if (conn.prepareStatement(query).executeUpdate() == 1) {
				System.out.println(dvd.getTitle() + " inserted to the database successfully");
			}

		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	/**
	 * method provided to Employee only to give him the ability to remove one or
	 * more movie from the database.
	 * 
	 * @param title
	 */
	public void removeDVD(String... title) {
		// TODO Auto-generated method stub
		if (this.session() != null) {
			if (!checkDisActivity(this.session())) {
				for (int i = 0; i < title.length; i++) {
					try {
						String query = "DELETE FROM dvdmovie WHERE title='" + title[i] + "'";
						stmt = conn.createStatement();
						if (stmt.executeUpdate(query) == 1) {
							System.out.println(title[i] + " has been removed.");
						} else {
							System.out.println("Removing process failed, try again.");
						}

					} catch (Exception e) {
						System.out.println(e.getMessage());
					}

				}
			} else {
				System.out.println("Dear " + this.session()
						+ ", you currently cannot remove DVD, ask the Manager to give you permission!");
			}
		} else {
			System.out.println("Login First, to be able to remove DVD!");
		}
	}

	/**
	 * Method provided to the Employee (ADMIN only) to update any property related
	 * to the DVD movie.
	 * 
	 * @param title
	 */
	public void updateDVD(String title) {
		if (this.session() != null) {
			if (!checkDisActivity(this.session())) {
				if (searchTitle(title) != null) {
					try {
						Scanner scan = new Scanner(System.in);
						System.out.println("Type one of those fields related to " + title + " movie to update: ");
						String input = scan.nextLine();
						String field = null;
						int idOfMovie = idOfMovie(title);
						if (input.equalsIgnoreCase("Title")) {
							field = "title";
						} else if (input.equalsIgnoreCase("Genre")) {
							field = "genre";
						} else if (input.equalsIgnoreCase("Release date")) {
							field = "releasedate";
						} else if (input.equalsIgnoreCase("Rental Price")) {
							field = "rentprice";
						} else if (input.equalsIgnoreCase("Sale Price")) {
							field = "saleprice";
						} else if (input.equalsIgnoreCase("On Sale?")) {
							field = "onsale";
						} else if (input.equalsIgnoreCase("Duration")) {
							field = "duration";
						} else if (input.equalsIgnoreCase("Is Rented")) {
							field = "isrented";
						} else if (input.equalsIgnoreCase("Country")) {
							field = "country";
						} else if (input.equalsIgnoreCase("Age rating")) {
							field = "agerating";
						} else if (input.equalsIgnoreCase("Copies")) {
							field = "copies";
						} else if (input.equalsIgnoreCase("Number of Times Rented")) {
							System.out.println("You cannot change this field!");
						} else {
							System.out.println("Type from those fields above only! Please Try again");
						}
						if (field != null) {
							if (field.equals("title") || field.equals("genre") || field.equals("releasedate")
									|| field.equals("duration") || field.equals("country")) {
								System.out.println("Change " + input + " to: ");
								String updatedValue = scan.nextLine();
								if (!checkEmptyFields(updatedValue)) {
									String query = "UPDATE dvdmovie SET " + field + "='" + updatedValue
											+ "' WHERE iddvd=" + idOfMovie + " ";
									executeUpdateQuery(query);
								} else {
									System.out.println("Empty field is not acceptable! Please Try again.");
								}

							} else if (field.equals("rentprice") || field.equals("saleprice")) {
								System.out.println("Change " + input + " to: ");
								String updatedValue = scan.nextLine();
								if (!checkEmptyFields(updatedValue)) {
									while (!checkDouble(updatedValue)) {
										System.out.println("Change " + input + " to(Double value only): ");
										updatedValue = scan.nextLine();
									}
									double updatedValueDouble = Double.parseDouble(updatedValue);
									String query = "UPDATE dvdmovie SET " + field + "=" + updatedValueDouble
											+ " WHERE iddvd=" + idOfMovie + " ";
									executeUpdateQuery(query);
								} else {
									System.out.println("Empty field is not acceptable! Please Try again.");
								}
							} else if (field.equals("agerating") || field.equals("copies")) {
								System.out.println("Change " + input + " to: ");
								String updatedValue = scan.nextLine();
								if (!checkEmptyFields(updatedValue)) {
									while (!checkInt(updatedValue)) {
										System.out.println("Change " + input + " to(Integer value only): ");
										updatedValue = scan.nextLine();
									}
									int updatedValueInteger = Integer.parseInt(updatedValue);
									String query = "UPDATE dvdmovie SET " + field + "=" + updatedValueInteger
											+ " WHERE iddvd=" + idOfMovie + " ";
									executeUpdateQuery(query);
								} else {
									System.out.println("Empty field is not acceptable! Please Try again.");
								}
							} else if (field.equals("isrented") || field.equals("onsale")) {
								System.out.println("Change " + input + " to: ");
								String updatedValueBoolean = scan.nextLine();
								if (!checkEmptyFields(updatedValueBoolean)) {
									while (!checkBoolean(updatedValueBoolean)) {
										System.out.println("Change " + input + " to(True or False only): ");
										updatedValueBoolean = scan.nextLine();
									}
									boolean updatedValueInteger = Boolean.parseBoolean(updatedValueBoolean);
									String query = "UPDATE dvdmovie SET " + field + "=" + updatedValueInteger
											+ " WHERE iddvd=" + idOfMovie + " ";
									executeUpdateQuery(query);
								} else {
									System.out.println("Empty field is not acceptable! Please Try again.");
								}
							}

						}

					} catch (Exception e) {
						System.out.println(e.getMessage());
					}

				} else {
					System.out.println("This movie is not found!");
				}
			} else {
				System.out.println("Dear " + this.session()
						+ ", you currently cannot update DVD, ask the manager to give you permission!");
			}
		} else {
			System.out.println("Login First, to be able to update DVD movie!");
		}

	}

	/**
	 * To execute the Update query and because it is used frequently, it is
	 * separated to decrease code duplication. It is private, so it cannot be used
	 * outside this class
	 */
	private final void executeUpdateQuery(String query) {
		try {
			stmt = conn.createStatement();
			if (stmt.executeUpdate(query) == 1) {
				System.out.println("It has been changed successfully.");
			} else {
				System.out.println("Updating Failed!");
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	/**
	 * To get the id of the movie from the database and because it is used
	 * frequently, it is separated to decrease code duplication.
	 * 
	 * @param title
	 * @return the id as int
	 */
	private final int idOfMovie(String title) {
		int id = 0;
		try {
			String query = "SELECT * FROM dvdmovie WHERE title='" + title + "'";
			stmt = conn.createStatement();
			result = stmt.executeQuery(query);
			while (result.next()) {
				id = result.getInt("iddvd");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return id;
	}

	/**
	 * It gives all the movies stored in the database ordered by the title from a to
	 * z. It uses result() method in the Person superclass to print them out and
	 * uses formatingPrint() method in the Person as well to have them in specific
	 * format
	 */
	public void reportOfMovies() {
		if (this.session() != null) {
			if (!checkDisActivity(this.session())) {
				try {
					String query = "SELECT * FROM dvdmovie ORDER BY title";
					stmt = conn.createStatement();
					result = stmt.executeQuery(query);
					formatingPrint();
					while (result.next()) {
						String Title = result.getString("title");
						result(Title);
					}
				} catch (Exception e) {
					System.out.println(e.getMessage());
				}
			} else {
				System.out.println("Dear " + this.session() + ", ask the user to give you permission!");
			}
		} else {
			System.out.println("Login First to be able to get the collection of DVD collection!");
		}

	}

	private boolean checkDisActivity(String employeeUserName) {
		boolean check = false;
		try {
			String query = "SELECT disactivated FROM users WHERE username='" + employeeUserName + "'";
			stmt = conn.createStatement();
			result = stmt.executeQuery(query);
			while (result.next()) {
				int value = result.getInt("disactivated");
				if (value == 1) {
					check = true;
				}
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return check;
	}

	/**
	 * To Update the costumer's balance, when the customer wants to deposit or
	 * withdrawal amount of money to his account.
	 */
	public void UpdateCustomerBalance() {
		try {
			Scanner scan = new Scanner(System.in);
			System.out.println("Enter the User Name of the Customer: ");
			String CusUserName = scan.nextLine();
			if (idOfCustomer(CusUserName) != 0) {
				int CusID = idOfCustomer(CusUserName);
				System.out.println("Reciet NO. :");
				String recitNo = scan.nextLine();
				while (checkEmptyFields(recitNo) || !checkInt(recitNo)) {
					if (checkEmptyFields(recitNo)) {
						System.out.println("Empty! Enter Reciet NO. :");
					} else if (!checkInt(recitNo)) {
						System.out.println("Integer only! Enter Reciet NO. :");
					}
					recitNo = scan.nextLine();
				}
				int recitNoInt = Integer.parseInt(recitNo);

				System.out.println("Enter The New Balance Amount: ");
				String amount = scan.nextLine();
				while (checkEmptyFields(amount) || !checkDouble(amount)) {
					if (checkEmptyFields(amount)) {
						System.out.println("Empty! Enter The New Balance Amount: ");
					} else if (!checkDouble(amount)) {
						System.out.println("Double only! Enter The New Balance Amount: ");
					}
					amount = scan.nextLine();
				}
				Double amountDouble = Double.parseDouble(amount);

				String queryB = "UPDATE balance SET recietNo=" + recitNoInt + ",amount=" + amountDouble
						+ " WHERE customerID='" + CusID + "'";
				if (stmt.executeUpdate(queryB) == 1) {
					System.out.println("Amount Updated.");
				} else {
					System.out.println("Process Failed!!");
				}
			} else {
				System.out.println("This Customer is not found!");
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

	}

	/**
	 * Gets the id of the customer form the users table in the database
	 * 
	 * @param userName
	 * @return id, if it is not found then the id will be equal to zero.
	 */
	private final int idOfCustomer(String userName) {
		int id = 0;
		try {
			String query = "SELECT iduser FROM users WHERE username='" + userName + "'";
			result = stmt.executeQuery(query);
			while (result.next()) {
				id = result.getInt("iduser");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return id;
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return super.toString();
	}

}
