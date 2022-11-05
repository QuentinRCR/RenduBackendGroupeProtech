# Protech Doctolib


## Description
### Goal
The goal of this project is to create the backend of an appointment scheduler.

### How to use it?
This project is a Spring Boot application using gradle. To launch it, go on the root folder and use:
```
gradle bootRun
```
Then, on your web browser, go to `localhost:8080/`. You will be prompted to enter a login and a password. By default, the user `user@gmail.com` with the password `string` is created. You can then test the API by going to http://localhost:8080/swagger-ui/.

### What is it doing
This project is the backend of an appointment scheduler. It deploys a REST API (by default on `localhost/8080`). It contains:
- Spring security with a registration API that asks for confirmation by email when a new account is created.
- A time-slot manager API that allows to create/delete/modify/find by id/find all time-slots in which clients can make appointments.
- An appointment manager API that allows to create/delete/modify/find by id/find all appointments made.

Eventually, the project will allow different access rights depending on the role of the person logged in.

### Use
This repo is used by students of Mines Saint-Étienne Engineering school.

## Contributing
This project is not open to contributions.

## Licence
This project is licenced under MIT license.

## Contact
You can contact us by email at the address `quentin.rey@etu.emse.fr` 
