import entity.Customer;
import entity.Vehicle;
import service.BillingService;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class App {
    public static void main(String []args) throws SQLException
    {
        Scanner sc = new Scanner(System.in);
        BillingService service = new BillingService();

        while(true){
            System.out.println("1. Add Customer \n2. Generate Invoice \n3. Show Invoice \n4. Exit");
            int ch = sc.nextInt();

            switch (ch){
                case 1:
                    System.out.print("Customer name: ");
                    String name = sc.next();
                    System.out.print("Phone: ");
                    String phone = sc.next();
                    int customerId = service.customerService.addCustomer(new Customer(0,name,phone));
                    System.out.print("Vehicle No: ");
                    String number_plate = sc.next();
                    System.out.print("Vehicle Model: ");
                    String model = sc.next();
                    int vehicleId = service.vehicleService.addVehicle(new Vehicle(0,customerId,number_plate,model));
                    Customer customerBasedOnNum = service.customerService.getCustomersBasedOnNum(phone);
                    Vehicle getVehicleBasedOnId = service.vehicleService.getVehicleBasedOnId(customerId);
                    break;

                case 2:
                    System.out.print("Enter Customer Id: ");
                    int cid = sc.nextInt();
                    System.out.print("Enter Vehicle Id: ");
                    int vid = sc.nextInt();
                    System.out.print("Enter number of services: ");
                    int n = sc.nextInt();
                    List<Integer> sids = new ArrayList<>();
                    for(int i =0 ; i < n ; i++)
                    {
                        System.out.println("Enter the service Id: ");
                        sids.add(sc.nextInt());
                    }
                    service.createInvoice(cid,vid,sids);
                    break;

                case 3:
                    service.showAllInvoices();
                    break;

                case 4:
                    System.exit(0);

                default:
                    System.out.println("Not a valid choice: ");
                    break;
            }
        }
    }
}
