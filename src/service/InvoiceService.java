package service;

import config.DBConfig;
import entity.Customer;
import entity.Invoice;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class InvoiceService {
    public void addInvoice(Invoice invoice) throws SQLException {
        Connection conn = DBConfig.getConnection();
        PreparedStatement ps = conn.prepareStatement("INSERT INTO invoices (customer_id,vehicle_id, service_id) values(?,?,?)");

        ps.setInt(1, invoice.getCustomerId());
        ps.setInt(2, invoice.getVehicleId());
        ps.setInt(3, invoice.getServiceId());

        System.out.println("Adding invoice => customerId: " + invoice.getCustomerId() +
                ", vehicleId: " + invoice.getVehicleId() +
                ", serviceId: " + invoice.getServiceId());


        ps.executeUpdate();
        ps.close();
        conn.close();
    }
    /*public List<Invoice> getAllInvoices() throws SQLException{
        List<Invoice> list = new ArrayList<>();
        Connection conn = DBConfig.getConnection();
        Statement st = conn.createStatement();
        ResultSet rs = st.executeQuery("Select * from invoices");
        while(rs.next()){
            list.add(new Invoice(rs.getInt("id"),rs.getInt("customer_id"),
                    rs.getInt("vehicle_id"), rs.getInt("service_id")));
        }
        return list;
    }*/
    public void showDetailedInvoices() throws SQLException {
        Connection conn = DBConfig.getConnection();
        String query = "SELECT c.name AS customer_name, c.phone, " +
                "v.number_plate, v.model, " +
                "s.description AS service_name, s.cost " +
                "FROM invoices i " +
                "JOIN customers c ON i.customer_id = c.id " +
                "JOIN vehicles v ON i.vehicle_id = v.id " +
                "JOIN services s ON i.service_id = s.id " +
                "ORDER BY c.id, v.id";

        Statement st = conn.createStatement();
        ResultSet rs = st.executeQuery(query);

        String lastCustomer = "";
        String lastVehicle = "";
        double total = 0;

        while (rs.next()) {
            String customer = rs.getString("customer_name") + " (" + rs.getString("phone") + ")";
            String vehicle = rs.getString("number_plate") + " (" + rs.getString("model") + ")";
            String service = rs.getString("service_name");
            double cost = rs.getDouble("cost");

            if (!customer.equals(lastCustomer) || !vehicle.equals(lastVehicle)) {
                // Print previous invoice total
                if (!lastCustomer.equals("")) {
                    System.out.println("---------------------------------");
                    System.out.println("Total: " + total);
                    System.out.println();
                    total = 0;
                }
                System.out.println("Invoice for Customer: " + customer);
                System.out.println("Vehicle: " + vehicle);
                System.out.println("---------------------------------");
                lastCustomer = customer;
                lastVehicle = vehicle;
            }

            System.out.println("Service: " + service + " | Cost: " + cost);
            total += cost;
        }

        // Print last invoice total
        if (!lastCustomer.equals("")) {
            System.out.println("---------------------------------");
            System.out.println("Total: " + total);
        }

        rs.close();
        st.close();
        conn.close();
    }
}
