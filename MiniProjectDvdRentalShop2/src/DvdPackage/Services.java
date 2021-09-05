package DvdPackage;

public abstract class Services {

	/**
	 * checks any input from the user, if it is empty or not
	 * 
	 * @param input
	 * @return true if any input is empty
	 */
	protected final boolean checkEmptyFields(String input) {
		boolean check = false;
		if (input.trim().equals("")) {
			check = true;
		} else {
			check = false;
		}

		return check;
	}

	/**
	 * checks any email if it is valid or not
	 * 
	 * @param email
	 * @return true if the email is valid
	 */
	protected final boolean chechEmail(String email) {
		String regex = "^[\\w-_\\.+]*[\\w-_\\.]\\@([\\w]+\\.)+[\\w]+[\\w]$";
		return email.matches(regex);
	}

	/**
	 * checks any name, does it contain letters only
	 * 
	 * @param name String to be passed to the methos to be checked
	 * @return true if the name is valid and false if it doesn't contain letters
	 *         only
	 */
	protected final boolean checkName(String name) {
		char[] letter = name.toCharArray();
		for (char c : letter) {
			if (!Character.isLetter(c)) { // to get the methods of char, should use wrapper class
				return false;
			}
		}
		return true;
	}

	/**
	 * checks if the input is true or false only, ignoring the case
	 * 
	 * @param value
	 * @return true if the input is boolean
	 */
	protected final boolean checkBoolean(String value) {
		if (value.equalsIgnoreCase("true") || value.equalsIgnoreCase("false")) {
			return true;
		} else {
			return false;
		}

	}

	/**
	 * checks the value if it is int or not
	 * 
	 * @param value
	 * @return true if it is int
	 */
	protected final boolean checkInt(String value) {
		char[] letter = value.toCharArray();
		for (char c : letter) {
			if (!Character.isDigit(c) && value.charAt(0) != '+' && value.charAt(0) != '-') {
				return false;
			}
		}
		return true;

	}

	/**
	 * checks the String if it is valid in case be parsedDouble
	 * 
	 * @param value as String
	 * @return false if it is invalid
	 */
	protected final boolean checkDouble(String value) {
		char[] letter = value.toCharArray();
		int numOfDot = 0;
		boolean check = false;
		for (int i = 0; i < letter.length; i++) {
			if (letter[i] != '.') {
				if (!Character.isDigit(letter[i])) {
					check = false;
					break;
				} else {
					check = true;
				}
			} else {
				if (i == 0) {
					if (letter[i] == '.') {
						check = false;
						break;
					}
				} else if (i > 0) {
					if (letter[i] == '.') {
						numOfDot++;
						if (numOfDot > 1) {
							check = false;
							break;
						} else {
							check = true;
						}

					}
				}
			}
		}
		return check;

	}

	/**
	 * method to check if the password contains at least letters and numbers
	 * 
	 * @param password
	 * @return true in case the password is valid
	 */
	protected boolean GoodPassword(String password) {
		boolean checkPass;
		if (checkInt(password)) {
			checkPass = false;
		} else if (checkName(password)) {
			checkPass = false;
		} else {
			checkPass = true;
		}
		return checkPass;
	}
}
