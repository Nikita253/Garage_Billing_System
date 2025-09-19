package service;

import entity.Customer;
import entity.Invoice;

import java.sql.SQLException;
import java.util.List;

public class BillingService {
    public CustomerService customerService = new CustomerService();
    public InvoiceService invoiceService = new InvoiceService();
    public VehicleService vehicleService = new VehicleService();
    public void  createInvoice(int customerId, int vehicleId, List<Integer> serviceIds) throws SQLException{
        String sids = "";
        for (int serviceId : serviceIds){
            System.out.println("Adding invoice => customerId: " + customerId +
                    ", vehicleId: " + vehicleId +
                    ", serviceId: " + serviceId);

            // Insert one invoice per serviceId
            invoiceService.addInvoice(new Invoice(0, customerId, vehicleId, serviceId));
        }
        System.out.println("Invoice Generated successfully....");
    }

    public void showAllInvoices() throws SQLException
    {
        /*List<Invoice> invoices = invoiceService.getAllInvoices();
        for (Invoice invoice:invoices)
        {
            System.out.println(invoice);
        }*/
        invoiceService.showDetailedInvoices();
    }
}
