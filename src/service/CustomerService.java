package service;

import config.DBConfig;
import entity.Customer;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CustomerService {
    public int addCustomer(Customer customer) throws SQLException {
        Connection conn = DBConfig.getConnection();
        PreparedStatement ps = conn.prepareStatement("INSERT INTO customers (name,phone) values(?,?)",Statement.RETURN_GENERATED_KEYS);
        ps.setString(1, customer.getName());
        ps.setString(2, customer.getPhone());
        ps.executeUpdate();

        int customerId = 0;
        ResultSet rs = ps.getGeneratedKeys();
        if (rs.next()) {
            customerId = rs.getInt(1); // ✅ capture the new customer ID
        }

        rs.close();
        ps.close();
        conn.close();

        return customerId;
    }
    public List<Customer> getAllCustomers() throws SQLException{
        List<Customer> list = new ArrayList<>();
        Connection conn = DBConfig.getConnection();
        Statement st = conn.createStatement();
        ResultSet rs = st.executeQuery("Select * from customers");
        while(rs.next()){
            list.add(new Customer(rs.getInt("id"),
                    rs.getString("name"), rs.getString("phone")));
        }
        return list;
    }

    public Customer getCustomersBasedOnNum(String number) throws SQLException{
        Customer customer = new Customer();
        Connection conn = DBConfig.getConnection();
        Statement st = conn.createStatement();
        ResultSet rs = st.executeQuery("Select * from customers where phone = "+number);
        while(rs.next()){
            customer = new Customer(rs.getInt("id"),
                    rs.getString("name"), rs.getString("phone"));
        }
        return customer;
    }
}