package service;

import config.DBConfig;
import entity.Customer;
import entity.Vehicle;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class VehicleService {
    public int addVehicle(Vehicle vehicle) throws SQLException {
        Connection conn = DBConfig.getConnection();
        PreparedStatement ps = conn.prepareStatement("INSERT INTO vehicles (number_plate,model,customer_id) values(?,?,?)",
                Statement.RETURN_GENERATED_KEYS
        );

        ps.setString(1, vehicle.getNumberPlate());
        ps.setString(2, vehicle.getModel());
        ps.setInt(3, vehicle.getCustomerId());
        ps.executeUpdate();

        ResultSet rs = ps.getGeneratedKeys();
        int vehicleId = 0;
        if (rs.next()) {
            vehicleId = rs.getInt(1);
        }

        ps.close();
        conn.close();
        return vehicleId;
    }
    public List<Vehicle> getAllVehicles() throws SQLException{
        List<Vehicle> list = new ArrayList<>();
        Connection conn = DBConfig.getConnection();
        Statement st = conn.createStatement();
        ResultSet rs = st.executeQuery("Select * from vehicles");
        while(rs.next()){
            list.add(new Vehicle(rs.getInt("id"), rs.getInt("customer_id"),
                    rs.getString("number_plate"), rs.getString("model")));
        }
        return list;
    }
    public Vehicle getVehicleBasedOnId(int customerId) throws SQLException {
        Vehicle vehicle = new Vehicle();
        Connection conn = DBConfig.getConnection();
        Statement st = conn.createStatement();
        ResultSet rs = st.executeQuery("Select * from vehicles where customer_id = "+customerId);
        while(rs.next()){
            vehicle = new Vehicle(rs.getInt("id"),rs.getInt("customer_id"),
                    rs.getString("number_plate"), rs.getString("model"));
        }
        return vehicle;
    }
}
