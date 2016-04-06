# address-book

Just a simple address book application...

## Getting Started

The REST service requires a small cobbled-together dataset to function.  Using
the scripts in the `support` directory, you can quickly get up and running.

1. Make sure you've got Docker and Maven installed and working.
2. From the `support` directory, run `./run-mysql.sh`
3. Once MySQL is up, run `./load-data.sh`, also from the `support` directory
4. Back in the project root, run `mvn spring-boot:run`.
5. When the app is up, hit http://localhost:8080/person for a listing or
   http://localhost:8080/person/1 etc. for individual people.

If you experience issues with MySQL, [this](https://hub.docker.com/_/mysql/) is
what was used for this project.