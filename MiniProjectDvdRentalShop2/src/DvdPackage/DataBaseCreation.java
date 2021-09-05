package DvdPackage;

import java.sql.DriverManager;

/**
 * this class is for creating the database and the required tables directly from
 * java. once the constructor is created, all database will be build. this class
 * inherits DBconnection class and uses its conn and stmt properties. this class
 * uses connectToDB() method which is inside DBconnection to connect to javadb
 * in order to create tables in javadb database
 * 
 * @author Shadia's group
 *
 */
public class DataBaseCreation extends DBconnection {

	/**
	 * When declaring and inistantiating this constructor, database and all tables
	 * will be created right away
	 */
	public DataBaseCreation() {

		// TODO Auto-generated constructor stub
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			conn = DriverManager.getConnection(
					"jdbc:mysql://localhost/?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC",
					"root", "");
			CreateDatabase();
			CreateUsersTable();
			CreateDvdmovieTable();
			CreateBalanceTable();
			CreateBalanceHistoryTable();
			CreateDvdRentedTable();
			CreateTriggerOnBalanceTable();
			CreateContactUsTable();
			CreateSalaryTable();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	/**
	 * creates javadb database
	 */
	private final void CreateDatabase() {
		try {
			stmt = conn.createStatement();
			String query = "Create Database javadb";
			stmt.executeUpdate(query);
			System.out.println("javadb database Created Successfully");
			stmt.close();
			conn.close();
		} catch (Exception e) {
			if (e.getMessage().equals("Can't create database 'javadb'; database exists")) {
				System.out.print("");
			} else {
				System.out.println(e.getMessage());
			}
		}
	}

	/**
	 * creats users table in javadb database
	 */
	private final void CreateUsersTable() {
		try {
			connectToDB();
			String query = "CREATE TABLE `javadb`.`users` (`iduser` INT(11) NOT NULL AUTO_INCREMENT,`username` VARCHAR(45) NOT NULL,`password` VARCHAR(255) NOT NULL,`firstname` VARCHAR(45) NOT NULL,`lastname` VARCHAR(45) NOT NULL,`email` VARCHAR(45) NOT NULL,`address` VARCHAR(45) NOT NULL,`phone` VARCHAR(20) NOT NULL,`type` VARCHAR(20) NOT NULL,`disactivated` TINYINT(1) NOT NULL DEFAULT 0,PRIMARY KEY (`iduser`),UNIQUE INDEX `username` (`username` ASC),UNIQUE INDEX `email` (`email` ASC),UNIQUE INDEX `phone` (`phone` ASC))";
			stmt = conn.createStatement();
			stmt.executeUpdate(query);
			System.out.println("users table created successfully.");
			stmt.close();
			conn.close();
		} catch (Exception e) {
			if (e.getMessage().equals("Table 'users' already exists")) {
				System.out.print("");
			} else {
				System.out.println(e.getMessage());
			}
		}
	}
	

	/**
	 * creates dvdmovie table in javadb database
	 */
	private final void CreateDvdmovieTable() {
		try {
			connectToDB();
			String query = "CREATE TABLE `dvdmovie` (`iddvd` int(11) NOT NULL AUTO_INCREMENT UNIQUE,`title` varchar(45) DEFAULT NULL UNIQUE,`genre` varchar(45) DEFAULT NULL,`releasedate` varchar(20) DEFAULT NULL,`rentprice` double DEFAULT NULL,`onsale` varchar(5) DEFAULT NULL,`saleprice` double DEFAULT NULL,`duration` varchar(20) DEFAULT NULL,`noOfTimesRented` int(11) NOT NULL DEFAULT 0,`isrented` varchar(5) DEFAULT NULL,`country` varchar(45) DEFAULT NULL,`agerating` int(11) DEFAULT NULL,`copies` int(11) DEFAULT NULL,PRIMARY KEY (`iddvd`)) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8";
			stmt = conn.createStatement();
			stmt.executeUpdate(query);
			System.out.println("dvdmovie table created successfully.");
			stmt.close();
			conn.close();
		} catch (Exception e) {
			if (e.getMessage().equals("Table 'dvdmovie' already exists")) {
				System.out.print("");
			} else {
				System.out.println(e.getMessage());
			}
		}
	}

	private final void CreateBalanceTable() {
		try {
			connectToDB();
			String query = "CREATE TABLE `javadb`.`balance` (`idbalance` INT(11) NOT NULL AUTO_INCREMENT,`customerID` INT(11) NOT NULL,`recietNo` INT(11) NOT NULL,`atime` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP(),`amount` DOUBLE NOT NULL DEFAULT 0,PRIMARY KEY (`idbalance`),INDEX `fk_balance_users1_idx` (`customerID` ASC),CONSTRAINT `fk_balance_users1`FOREIGN KEY (`customerID`)REFERENCES `javadb`.`users` (`iduser`)ON DELETE NO ACTION ON UPDATE NO ACTION)";
			stmt = conn.createStatement();
			stmt.executeUpdate(query);
			System.out.println("balance table created successfully.");
			stmt.close();
			conn.close();
		} catch (Exception e) {
			if (e.getMessage().equals("Table 'balance' already exists")) {
				System.out.print("");
			} else {
				System.out.println(e.getMessage());
			}
		}
	}
	

	private final void CreateBalanceHistoryTable() {
		try {
			connectToDB();
			String query = "CREATE TABLE `javadb`.`balancehistory` (`idbalance` INT NOT NULL,`customerID` INT NOT NULL,`recietNo` INT NOT NULL,`atime` DATETIME NOT NULL DEFAULT now(),`amount` DOUBLE NOT NULL)";
			stmt = conn.createStatement();
			stmt.executeUpdate(query);
			System.out.println("balancehistory table created successfully.");
			stmt.close();
			conn.close();
		} catch (Exception e) {
			if (e.getMessage().equals("Table 'balancehistory' already exists")) {
				System.out.print("");
			} else {
				System.out.println(e.getMessage());
			}
		}
	}

	private final void CreateTriggerOnBalanceTable() {
		try {
			connectToDB();
			String query = "create trigger myBalanceTrigger AFTER UPDATE ON balance FOR EACH ROW INSERT INTO balanceHistory (idbalance,customerID,recietNo,atime,amount) VALUE (old.idbalance,old.customerID,old.recietNo,now(),old.amount)";
			stmt = conn.createStatement();
			stmt.executeUpdate(query);
			System.out.println("TriggerOnBalanceTable created successfully.");
			stmt.close();
			conn.close();
		} catch (Exception e) {
			if (e.getMessage().equals("Trigger 'javadb.myBalanceTrigger' already exists")) {
				System.out.print("");
			} else {
				System.out.println(e.getMessage());
			}
		}
	}

	private final void CreateDvdRentedTable() {
		try {
			connectToDB();
			String query = "CREATE TABLE `javadb`.`dvdRented` (`id_rented` INT NOT NULL AUTO_INCREMENT,`idCustomer` INT(11) NOT NULL,`iddvd` INT(11) NOT NULL,`status` VARCHAR(45) NULL,`atime` DATETIME NOT NULL DEFAULT now(),`rentPrice` DOUBLE NOT NULL,INDEX `fk_users_has_dvdmovie_dvdmovie1_idx` (`iddvd` ASC),INDEX `fk_users_has_dvdmovie_users1_idx` (`idCustomer` ASC),PRIMARY KEY (`id_rented`),CONSTRAINT `fk_users_has_dvdmovie_users1`FOREIGN KEY (`idCustomer`)REFERENCES `javadb`.`users` (`iduser`),CONSTRAINT `fk_users_has_dvdmovie_dvdmovie1`FOREIGN KEY (`iddvd`)REFERENCES `javadb`.`dvdmovie` (`iddvd`))";
			stmt = conn.createStatement();
			stmt.executeUpdate(query);
			System.out.println("DvdRented Table created successfully.");
			stmt.close();
			conn.close();
		} catch (Exception e) {
			if (e.getMessage().equals("Table 'dvdrented' already exists")) {
				System.out.print("");
			} else {
				System.out.println(e.getMessage());
			}
		}
	}
	
	private final void CreateContactUsTable() {
		try {
			connectToDB();
			String query = "CREATE TABLE `javadb`.`contactus` (`idContact` INT NOT NULL AUTO_INCREMENT,`from` VARCHAR(45) NOT NULL,`to` VARCHAR(10) NOT NULL,`topic` VARCHAR(45) NOT NULL,`message` VARCHAR(1000) NOT NULL,`atime` DATETIME NOT NULL DEFAULT now(),`status` VARCHAR(10) NOT NULL DEFAULT 'unread' ,PRIMARY KEY (`idContact`))";
			stmt = conn.createStatement();
			stmt.executeUpdate(query);
			System.out.println("contactus Table created successfully.");
			stmt.close();
			conn.close();
		} catch (Exception e) {
			if (e.getMessage().equals("Table 'contactus' already exists")) {
				System.out.print("");
			} else {
				System.out.println(e.getMessage());
			}
		}
	}
	
	private final void CreateSalaryTable() {
		try {
			connectToDB();
			String query = "CREATE TABLE IF NOT EXISTS `javadb`.`Salary` (`idSalary` INT NOT NULL AUTO_INCREMENT,`idEmployee` INT(11) NOT NULL,`bounce` DOUBLE NOT NULL DEFAULT 0,`Salary` DOUBLE NOT NULL DEFAULT 0,PRIMARY KEY (`idSalary`),INDEX `fk_Salary_users1_idx` (`idEmployee` ASC),CONSTRAINT `fk_Salary_users1`FOREIGN KEY (`idEmployee`)REFERENCES `javadb`.`users` (`iduser`)ON DELETE NO ACTION ON UPDATE NO ACTION)";
			stmt = conn.createStatement();
			stmt.executeUpdate(query);
			System.out.println("Salary Table created successfully.");
			stmt.close();
			conn.close();
		} catch (Exception e) {
			if (e.getMessage().equals("Table 'Salary' already exists")) {
				System.out.print("");
			} else {
				System.out.println(e.getMessage());
			}
		}
	}
	
	

}
