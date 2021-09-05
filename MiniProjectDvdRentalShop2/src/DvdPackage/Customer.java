package DvdPackage;

import java.sql.PreparedStatement;
import java.text.NumberFormat;
import java.util.Scanner;

public class Customer extends Person {

	/**
	 * Once this class declared and instantiated somewhere and the program is Run,
	 * it will ask you to define the current user by login or sign up.Because the
	 * default constructor inside the parent class has logic to ask for ligin.
	 */
	public Customer() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * This is another way we can use it to login by passing the username and the
	 * password as parameters. Because the constructor inside the parent class has
	 * logic to ask for ligin.
	 * 
	 * @param username
	 * @param password
	 */
	public Customer(String username, String password) {
		super(username, password);
		// TODO Auto-generated constructor stub
	}

	public void myBascket() {
		if (this.session() != null) {
			String query = "SELECT dm.title,d.atime FROM users u join dvdrented d on u.iduser=d.idCustomer join dvdmovie dm on dm.iddvd=d.iddvd where u.username='"
					+ this.session() + "' order by d.atime asc";
			try {
				result = stmt.executeQuery(query);
				System.out.println("\n-----DVD List You Rented-----");
				int i = 1;
				while (result.next()) {
					String title = result.getString("dm.title");
					String time = result.getString("d.atime");
					System.out.println((i++) + "-" + title + "\t" + time);
				}
			} catch (Exception e) {
				System.out.println(e.getMessage());
			}
		} else {
			System.out.println("Login First! to be able to see your Bascket.");
		}
	}

	/**
	 * 
	 * @param title
	 */
	public void rent(String title) {
		if (this.session() != null) {
			try {
				if (searchTitle(title) != null) {
					int id = idOfCustomer(this.session());
					int idDVD = idOfMovie(title);
					if (id != 0) {
						String query = "SELECT amount FROM balance WHERE customerID=" + id + "";
						result = stmt.executeQuery(query);
						double amount = 0;
						while (result.next()) {
							amount = result.getDouble("amount");
						}
						String query2 = "SELECT rentprice,copies,noOfTimesRented FROM dvdmovie WHERE title='" + title
								+ "'";
						result = stmt.executeQuery(query2);
						double rentPrice = 0;
						int Copies = 0;
						int Times = 0;
						while (result.next()) {
							rentPrice = result.getDouble("rentprice");
							Copies = result.getInt("copies");
							Times = result.getInt("noOfTimesRented");
						}
						String query7 = "SELECT * FROM dvdrented WHERE idCustomer=" + id + " AND iddvd=" + idDVD
								+ " AND status='Currently Rented'";
						result = stmt.executeQuery(query7);
						if (result.next()) {
							System.out.println("This movie is already rented by you now!");
						} else {
							if (Copies > 0) {
								if (amount >= rentPrice) {

									String query5 = "insert into dvdrented (idCustomer,iddvd,status,rentPrice) values ("
											+ id + "," + idDVD + ",'Currently Rented'," + rentPrice + ");";
									if (conn.prepareStatement(query5).executeUpdate() == 1) {
										String query3 = "UPDATE dvdmovie SET copies=" + (Copies - 1)
												+ ",noOfTimesRented=" + (Times + 1) + " WHERE iddvd=" + idDVD + "";
										if (stmt.executeUpdate(query3) == 1) {
											String query4 = "UPDATE balance SET amount=" + (amount - rentPrice)
													+ ",recietNo=0,atime=now() WHERE customerID=" + id + "";
											if (stmt.executeUpdate(query4) == 1) {
												System.out.println(title + " is rented, Have a good time watching it.");
											} else {
												System.out.println("Error4 occured!");
											}
										} else {
											System.out.println("Error3 occured!");
										}
									} else {
										System.out.println("Error5!");
									}

								} else {
									System.out.println(
											"No enough money in your account to rent! Try to contact the Employee to deposit money.");
								}
							} else {
								System.out.println("Sorry, there are no copies at this time!");
							}
						}
					}

				} else {
					System.out.println("This DVD movie is not found!");
				}

			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			System.out.println("Login First! To be able to rent DVD movie.");
		}

	}

	/**
	 * Method that tells the customer his current balance.
	 */
	public void myBalance() {
		if (this.session() != null) {
			try {
				int id = idOfCustomer(this.session());
				String query = "SELECT amount FROM balance WHERE customerID=" + id + "";
				result = stmt.executeQuery(query);
				if (result.next()) {
					double balance = result.getDouble("amount");
					NumberFormat fmt = NumberFormat.getCurrencyInstance();
					System.out.println("Your Current Balance: " + fmt.format(balance));
				}
			} catch (Exception e) {
				System.out.println(e.getMessage());
			}
		} else {
			System.out.println("Login First! To be able to see your current balance");
		}
	}

	/**
	 * This method allows the Customer to be able to contact the Manager of the DVD
	 * Movie Rental Store and send him a feedback, issues, or complains
	 */
	public void contact() {
		if (this.session() != null) {
			Scanner scan = new Scanner(System.in);
			System.out.println("From: " + this.session());
			System.out.println("To: Manager");
			System.out.print("Topic: ");
			String topic = scan.nextLine();
			System.out.println("Message: ");
			String message = scan.nextLine();
			System.out.println("Type 'Send' to send your message, or any key to cancel it.");
			String order = scan.nextLine();
			if (order.equalsIgnoreCase("Send")) {
				String query = "INSERT INTO contactus(`from`,`to`,`topic`,`message`) VALUES(?,?,?,?)";
				try {
					// Using prepared statement because here the user insert data to the database
					// and it is important to prevent the database by using this technology
					PreparedStatement ps = conn.prepareStatement(query);
					ps.setString(1, this.session());
					ps.setString(2, "Manager");
					ps.setString(3, topic);
					ps.setString(4, message);
					if (ps.executeUpdate() == 1) {
						System.out.println("Message sent.");
					} else {
						System.out.println("The message has not been sent, something wrong happened!");
					}
				} catch (Exception e) {
					e.printStackTrace();
				}

			} else {
				System.out.println("Your message has not been sent.");
			}
		} else {
			System.out.println("Login First! To be able to contact the Manager.");
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

	/**
	 * To get the id of the movie from the database
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

}
