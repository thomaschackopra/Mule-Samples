/**
 * 
 */
package com.example.pra;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Collection;
import org.mule.module.apikit.exception.NotFoundException;

/**
 * @author thomas.a.chacko
 *
 */
public class CustomerService {

	private HashMap<Integer, Customer> customerMap = new HashMap<Integer, Customer>();
	private Integer empId = 1;
	private Integer customerCount = 0;

	public Status createCustomer(Customer customer) throws Exception {
		System.out.println("Customer Create Params : " + customer.getFirst_name() + " : "+customer.getLast_name());
		boolean customerExist = false;
		Status createStatus = new Status();

		Collection custObjCollection = this.customerMap.values();

		Iterator<Customer> iterator = custObjCollection.iterator();

		while (iterator.hasNext()) {
			Customer tempCust = iterator.next();
			System.out.println("Customer Iterator Params : " + tempCust.getFirst_name() + " : "+tempCust.getLast_name());
			if (tempCust.getFirst_name().equals(customer.getFirst_name()) 
					&& tempCust.getLast_name().equals(customer.getLast_name()) ) {
				customerExist = true;
				break;
			}
		}
		
		if(!customerExist){
			
			customer.setId(this.empId++);

			this.customerMap.put(customer.getId(), customer);
			this.customerCount++;
			createStatus.setMessage("Customer created successfully");

			return createStatus;
		}
		
		else{
			
			throw new Exception("Customer exist with First Name : " + customer.getFirst_name() + " and Last Name : "
					+ customer.getLast_name());
		}
		

	}

	public Status updateCustomer(Customer customer) throws Exception {

		boolean result = false;
		Status updateStatus = new Status();
		if (this.customerMap.containsKey(customer.getId())) {
			this.customerMap.put(customer.getId(), customer);
			updateStatus.setMessage("Customer updated successfully");

			return updateStatus;

		} else {

			throw new NotFoundException("Customer not Found with Id : " + customer.getId());
		}

	}

	public Customer getCustomerById(Integer Id) throws Exception {

		if (this.customerMap.containsKey(Id)) {
			return customerMap.get(Id);
		} else {

			throw new NotFoundException("Customer not Found with Id : " + Id);
		}

	}

	public Status deleteCustomerById(Integer Id) throws Exception {

		boolean result = false;
		Status deleteStatus = new Status();
		if (this.customerMap.containsKey(Id)) {

			this.customerMap.remove(Id);
			this.customerCount--;
			deleteStatus.setMessage("Customer Deleted Successfully");

			return deleteStatus;

		} else {

			throw new NotFoundException("Customer not Found with Id : " + Id);
		}
	}

	public CustomerList getAllCustomers() {

		CustomerList cl = new CustomerList();
		ArrayList<Customer> customerArList;

		//initialize();
		if (this.customerCount == 0) {

			customerArList = new ArrayList<>();

		} else {

			Collection custValues = this.customerMap.values();
			customerArList = new ArrayList<Customer>(custValues);

		}
		cl.setCustomers(customerArList);

		return cl;
	}

	
}
