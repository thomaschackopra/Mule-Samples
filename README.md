# Customer API Solution
This repository contains the Mule Implementation for Customer REST API designed on Anypoint Platform.

# Requirement
Design a RESTful API using RAML that contains a single resource, customers, and allows the following:

•         List customers

•         Create a new customer

•         Update a customer

•         Deletes a customer

You may constrain the customer object to first name, last name and addresses, and the format to JSON.

The API must be designed to support the following consumer use cases at a minimum:

1.       A consumer may periodically (every 5 minutes) consume the API to enable it (the consumer) to maintain a copy of the provider API's customers (the API represents the system of record)

2.       A mobile application used by customer service representatives that uses the API to retrieve and update the customers details

3.       Simple extension of the API to support future resources such as orders and products

Please implement the RESTful API in Mulesoft with test stubs for the consumer and provider systems.

# Solution

# 1. Design

The Rest API was designed using RAML using the Designer on Anypoint Platform

RAML Location : \dev-customer-app\src\main\api\customer-api.raml

# Key Considerations
The RAML uses traits to enforce the consumers to use the client Id for accessing the API.
The RAML use Resource Type to define collections and collection-item to make it reusable and extend it for orders and products.
The RAML use sample payloads from files to make it more readable and easy to maintain.

The access can be requested through the Anypoint Platform Exchange. There are two applications registered with the API  and are managed through the API Manager:

1) Customer-App-1 : This application will be simulating the periodic calls to the consumer resource to maintain copy of the all customers
2) Mobile-App : This application will be simulating the calls to get and update a customer

# Security and Access Restriction
Rate Limiting SLA Based Policy is applied for this API to ensure Consumer-App-1 can make 1 request every 5 mins
For Mobile user the Rate Limiting SLA Policy is 10 request/min

There is proxy configured on the API Manager to enable clients(consumers) only registered with the API and have valid client_id and Client_secret

# 2.Build

The Mule project was developed with the import of the RAML from Design Center.

# Persistence
The Customer Persistence was achieved using Java Beans. This was the simplest and fastest way to achieve a backend store for customer objects.
Cache scope was used to keep the data for 5 mins for any request whose client_id match Customer-App-1. This client is configured through the property (consumer.clientid) in propert placeholder file - Customer-Dev.properties.

Other Options were :

1) Connect to any Database ( MySQL/Oracle) on the Cloud
2) Connect to Amazon Dynamo DB

The Java artifact includes :
1) Customer.java - Java representation of the Customer JSON object
2) Address.java - Java representation of the Address JSON object
3) CustomerList.java -Java representation of the Customer JSON Collection
4) CustomerService.java - Java Bean to store the customer objects and perform actions - add , update , get and delete.
5) Status.java

# Exception Handling

Any exceptions related to data not found while updating/deleting resource within the persistence methods (java) was handled by throwing exceptions through org.mule.module.apikit.exception.NotFoundException

APIKit Routing Configuration has all the exceptions mapped with appropriate HTTP error codes.

# Efficiency with Network

The API is lightweight with json as the data type which will enable low latency for mobile applications.Also cache scope will enable large response to be cached and available for quick reuse

# Reuse and extend use case 3

There is a consideration to make the java class implementation dynamic based on the resource (customer/order/products). Use of the property placeholders linked to the resource can drive the backend implementation. This need to be explored further.

# Testing

The implemntation was testing using the APIKit Console and Postman

# Deployment

The code was run locally using the Debug Mode as well as on the CloudHub via Proxy service configured through the API Manager.

Implementation URL: http://customer-app-tc.cloudhub.io/api/customer
Proxy URL : http://customer-app-tc-proxy.cloudhub.io/customer




