# Scheduler-App
This Appointment manager app is a desktop application that is able to schedule, store, and manage appoitments.<br /> 
It has an SQL database backend and a GUI frontend.<br /> 

Author Robert Uhl<br /> 
Version 1.0<br /> 
1/15/2023<br /> 
InteliJ IDE Community<br />
IntelliJ IDEA 2021.1.3 (Community Edition)
Build #IC-211.7628.21, built on June 30, 2021<br />
Runtime version: 11.0.11+9-b1341.60 amd64<br />
VM: OpenJDK 64-Bit Server VM by JetBrains s.r.o.<br />
Windows 10 10.0<br />
GC: G1 Young Generation, G1 Old Generation<br />
Memory: 768M<br />
Cores: 4<br />
Kotlin: 211-1.4.32-release-IJ7628.19<br />
Java JDK 17.0.1<br /> 
JavaFX SDK 17.0.1<br /> 
mysql-connector-java-8.0.25.jar<br /> 
<br /> 
How to run the program:
- Connect application to SQL database
- Launch Application
- Login with username(test) and password(test)
- Select an option on main menu, either appointments, customer, reports, or logout.
- On the appointment screen you can add, modify, or delete appointments
- Customer screen you can add, modify, or delete customers.
- The reports screen will allow you to generate a report by selecting one or more filters.
<br /> 
<br /> 

Login Screen, with user authentication:

![alt text](https://github.com/sudorob0/Scheduler-App/blob/master/screen_shots/loginScreen.png?raw=true)

Main Menu:

![alt text](https://github.com/sudorob0/Scheduler-App/blob/master/screen_shots/mainMenu.png?raw=true)

Appointment Screen, here you can view all scheduled appointment and delete any appointments:

![alt text](https://github.com/sudorob0/Scheduler-App/blob/master/screen_shots/appointmentScreen.png?raw=true)

Add and modify appointment screen:

![alt text](https://github.com/sudorob0/Scheduler-App/blob/master/screen_shots/addAppointment.png?raw=true)

Customer screen, this screen displays all customers:

![alt text](https://github.com/sudorob0/Scheduler-App/blob/master/screen_shots/customerScreen.png?raw=true)

This screen you can add or modify customers:

![alt text](https://github.com/sudorob0/Scheduler-App/blob/master/screen_shots/addCustomer.png?raw=true)

This is the report screen were you can create customer reports and select up to 2 filters.
The table view dynamicly builds the table based of the sql database queries.

![alt text](https://github.com/sudorob0/Scheduler-App/blob/master/screen_shots/reportScreen.png?raw=true)
