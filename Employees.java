/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package employees;
/*
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;
import org.apache.derby.jdbc.ClientDriver;
*/
/**
 *
 * @author Sonali Singla
 */

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;
//import java.sql.Statement;
//import java.util.logging.Level;
//import java.util.logging.Logger;
/**
 *
 * @author Sonali Singla
 */
public class Employees {

    static Connection con;
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws Exception {
        Employees obj = new Employees();
        connect();
        Scanner sc = new Scanner(System.in);
        System.out.println("Welcome to Employees' Database");
        outer: while(true) {
            System.out.println("Enter 1. to insert\n2. to delete\n3. to update\n4. to display"
                    + "\n5. to exit: ");
            switch(sc.nextInt()) {
                case 1:
                    System.out.print("Enter employee id : ");
                    int id = sc.nextInt();
                    System.out.print("Enter employee name : ");
                    String n = sc.next();
                    System.out.print("Enter employee contact number : ");
                    String phone = sc.next();
                    System.out.print("Enter employee address : ");
                    String add = sc.next();
                    obj.insert(id,n,phone,add);
                    System.out.println("yugjh");
                    break;
                case 2:
                    System.out.print("Enter employee id : ");
                    obj.delete(sc.nextInt());
                    break;
                case 3:
                    System.out.print("Enter employee id : ");
                    int ID = sc.nextInt();
                    while (true) {
                        System.out.println("Enter 1 to update employee name"
                            + "\n2 to update employee contact number\n3 to update"
                            + "employee address : ");
                        int ch = sc.nextInt();
                        switch(ch){
                            case 1:
                                System.out.print("Enter the updated name : ");
                                obj.update(ID,sc.next(),null,null);
                                break;
                            case 2:
                                System.out.print("Enter the updated contact number : ");
                                obj.update(ID,null,sc.next(),null);
                                break;
                            case 3:
                                System.out.print("Enter the updated address : ");
                                obj.update(ID,null,null,sc.next());
                                break;
                            default : 
                                System.out.println("Wrong choice!");
                        }
                        if(ch==4) {
                            break;
                        }
                    }
                    break;
                case 4:
                    obj.display();
                    break ;
                case 5:
                    break outer;
                default :
                    System.out.println("Wrong choice!");
            }
        }
    }

    public static void connect() {
        try {
            Employees.class.forName("org.apache.derby.jdbc.ClientDriver");
            con = DriverManager.getConnection("jdbc:derby://localhost:1527/employee");
            System.out.print(con);
            System.out.println("Connected Successfully !");
            
        } catch (Exception ex) {
            
            System.out.println(ex);
            ex.getStackTrace();
        }
    }

    public void insert(int empid,String empname ,String phone ,String address) throws Exception {
            PreparedStatement ps = con.prepareStatement("Insert into EMPLOYEES_INFO(Employee_id,"
                    + "Employee_name,Employee_contact_number,Employee_address) values(?,?,?,?)");
            ps.setInt(1, empid);
            ps.setString(2,empname);
            ps.setString(3,phone);
            ps.setString(4,address);
            if (ps.executeUpdate()>0) {
                System.out.println("Inserted successfully!");
            }
    }

    public void delete(int empid) {
        try {
            PreparedStatement ps = con.prepareStatement("delete from EMPLOYEES_INFO where "
                    + "Employee_id=?");
            ps.setInt (1,empid);
            if (ps.executeUpdate()>0) {
                System.out.println("Deleted successfully!");
            }
        } catch(SQLException ex) {
            ex.printStackTrace();
            throw new RuntimeException(ex);
        }
    }
    public void update (int empid,String empname ,String phone ,String address) {
        PreparedStatement ps = null;
        try {
            if (empname != null && phone != null && address != null) {
                ps = con.prepareStatement("update EMPLOYEES_INFO set Epmloyee_name = ?,"
                        + "Employee_contact_number = ?, Employee_address = ? where Employee_id = ?");
                ps.setString(1, empname);
                ps.setString(2, phone);
                ps.setString(3, address);
                ps.setInt(4, empid);
            }
            else if (empname != null && phone != null) {
                ps = con.prepareStatement("update EMPLOYEES_INFO set Employee_name = ?,"
                        + "Epmloyee_contact_number = ? where Employee_id = ?");
                ps.setString(1,empname);
                ps.setString(2, phone);
                ps.setInt(3, empid);
            } else if (empname != null && address != null) {
                ps = con.prepareStatement("update EMPLOYEES_INFO set Employee_name = ?,"
                        + "Epmloyee_address = ? where Employee_id = ?");
                ps.setString(1, empname);
                ps.setString(2, address);
                ps.setInt(3, empid);
            } else if(phone != null && address != null) {
                ps = con.prepareStatement("update EMPLOYEES_INFO set Employee_Contact_number = ?,"
                        + "Epmloyee_address = ? where Employee_id = ?");
                ps.setString(1, phone);
                ps.setString(2, address);
                ps.setInt(3, empid);
            } else if(empname != null) {
                ps = con.prepareStatement("update EMPLOYEES_INFO set Employee_name = ?,"
                        + "where Employee_id = ?");
                ps.setString(1, empname);
                ps.setInt(2, empid);
            }else if(phone != null){
                ps = con.prepareStatement("update EMPLOYEES_INFO set Employee_contact_number = ?,"
                        + " where Employee_id = ?");
                ps.setString(1, phone);
                ps.setInt(2, empid);
            } else {
                ps = con.prepareStatement("update EMPLOYEES_INFO set Epmloyee_address = ? where Employee_id = ?");
                ps.setString(1, address);
                ps.setInt(2, empid);                
            } if (ps.executeUpdate()>0) {
                System.out.println("Record updated successfully!");
            } else {
                System.out.println("No such employee id exists!");
            }
        } catch(SQLException ex) {
            ex.printStackTrace();
            throw new RuntimeException (ex);
        }
    }
    public ResultSet display() throws Exception {
            PreparedStatement ps = con.prepareStatement("select * from EMPLOYEES_INFO");
            ResultSet rs = ps.executeQuery();
        return rs;
    }
    public ResultSet search(int empid) throws Exception {
        PreparedStatement ps = con.prepareStatement("select * from EMPLOYEES_INFO where Employee_id = ?");
        ps.setInt(1, empid);
        ResultSet rs = ps.executeQuery();
        return rs;
    }
}