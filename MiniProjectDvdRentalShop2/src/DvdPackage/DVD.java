package DvdPackage;

import java.util.Scanner;

/**
 * Class for defining a dvd movie by ask the user to type several info about it
 * from the keyboard. it inherits from services to use its methods to check the
 * input valid or not
 * 
 * @author Shadiar's group
 *
 */
public class DVD extends Services {
	private String title;
	private String genre;
	private String releasedate;
	private double rentalPrice;
	private double salePrice;
	private boolean onSale;
	private String duration;
	private boolean isRented;
	private String country;
	private int agerating;
	private int numOfTimesRented;
	private int noOfCopies;

	/**
	 * Constructor calls the method of defining the DVD.
	 */
	public DVD() {
		super();
		defineDVD();
	}

	/**
	 * Scanner to let the user fill out the properties of the DVD class. it Uses
	 * several methods that inherited from the indirect superclass (Services) to
	 * check each input if it is valid or not.
	 */
	final void defineDVD() {
		Scanner scan = new Scanner(System.in);
		System.out.println("\n-----DVD-----");
		try {
			System.out.println("Title: ");
			String title = scan.nextLine();
			while (checkEmptyFields(title)) {
				System.out.println("Title: ");
				title = scan.nextLine();
			}
			this.title = title;

			System.out.println("Genre: ");
			String genre = scan.nextLine();
			while (checkEmptyFields(genre)) {
				System.out.println("Genre: ");
				genre = scan.nextLine();
			}
			this.genre = genre;

			System.out.println("Duration: ");
			String duration = scan.nextLine();
			while (checkEmptyFields(duration)) {
				System.out.println("Duration: ");
				duration = scan.nextLine();
			}
			this.duration = duration;

			System.out.println("Country: ");
			String country = scan.nextLine();
			while (checkEmptyFields(country)) {
				System.out.println("Country: ");
				country = scan.nextLine();
			}
			this.country = country;

			System.out.println("Release Date: ");
			String releasedate = scan.nextLine();
			while (checkEmptyFields(releasedate)) {
				System.out.println("Release Date: ");
				releasedate = scan.nextLine();
			}
			this.releasedate = releasedate;

			System.out.println("Rental Price: ");
			String value6 = scan.nextLine();
			while (!checkDouble(value6) || checkEmptyFields(value6)) {
				if (!checkEmptyFields(value6)) {
					System.out.println("Rental Price(Double only!): ");
				}
				value6 = scan.nextLine();
			}
			double rentalPrice = Double.parseDouble(value6);
			this.rentalPrice = rentalPrice;

			System.out.println("Sale Price: ");
			String value7 = scan.nextLine();
			while (!checkDouble(value7) || checkEmptyFields(value7)) {
				if (!checkEmptyFields(value7)) {
					System.out.println("Sale Price(Double only!): ");
				}
				value7 = scan.nextLine();
			}
			double salePrice = Double.parseDouble(value7);
			this.salePrice = salePrice;

			System.out.println("OnSale?: ");
			String value = scan.nextLine();
			while (!checkBoolean(value) || checkEmptyFields(value)) {
				if (!checkEmptyFields(value)) {
					System.out.println("OnSale(True|false): ");
				}
				value = scan.nextLine();
			}
			boolean onSale = Boolean.parseBoolean(value);
			this.onSale = onSale;

			System.out.println("IsRented?: ");
			String value2 = scan.nextLine();
			while (!checkBoolean(value2) || checkEmptyFields(value2)) {
				if (!checkEmptyFields(value2)) {
					System.out.println("IsRented(True|false): ");
				}
				value2 = scan.nextLine();
			}
			boolean isRented = Boolean.parseBoolean(value2);
			this.isRented = isRented;

			System.out.println("Age rating: ");
			String value3 = scan.nextLine();
			while (!checkInt(value3) || checkEmptyFields(value3)) {
				if (!checkEmptyFields(value3)) {
					System.out.println("Age rating(Integer only): ");
				}
				value3 = scan.nextLine();
			}
			int agerating = Integer.parseInt(value3);
			this.agerating = agerating;

			System.out.println("No. of Copies: ");
			String value5 = scan.nextLine();
			while (!checkInt(value5) || checkEmptyFields(value5)) {
				if (!checkEmptyFields(value5)) {
					System.out.println("No. of Copies(Integer only): ");
				}
				value5 = scan.nextLine();
			}
			int noOfCopies = Integer.parseInt(value5);
			this.noOfCopies = noOfCopies;

		} catch (Exception e) {
			System.err.println(e);
		}
	}

	// end of setters and getters

	public String getTitle() {
		return title;
	}

	public String getGenre() {
		return genre;
	}

	public String getReleasedate() {
		return releasedate;
	}

	public double getRentalPrice() {
		return rentalPrice;
	}

	public double getSalePrice() {
		return salePrice;
	}

	public boolean isOnSale() {
		return onSale;
	}

	public String getDuration() {
		return duration;
	}

	public boolean isRented() {
		return isRented;
	}

	public String getCountry() {
		return country;
	}

	public int getAgerating() {
		return agerating;
	}

	public int getNumOfTimesRented() {
		return numOfTimesRented;
	}

	public int getNoOfCopies() {
		return noOfCopies;
	}

}
