README

This Spring Boot application is written in Kotlin and is designed to check the position and balance on the Bingx trading platform using the Bingx API. The application is configured using the `application.yml` file, which contains the API key, secret key, URL, base margin, and loss percentage.

To run this application on Windows, you must first ensure that you have Java and Kotlin installed on your system. Once you have these dependencies installed, you can follow the steps below to run the application.

Steps to Run the Application:

1.  Clone the repository to your local machine using the command below:
    
    bashCopy code
    
    `git clone <repository_url>`
    
2.  Navigate to the project directory:
    
    bashCopy code
    
    `cd <project_directory>`
    
3.  Open the `application.yml` file and update the configuration with your Bingx API key, secret key, and other required values.
    
4.  Build the project using the following command:
    
    bashCopy code
    
    `./gradlew build`
    
5.  Once the build is complete, run the application using the following command:
    
    bashCopy code
    
    `java -jar build/libs/<jar_filename>.jar`
    
    Note: Replace `<jar_filename>` with the name of the jar file that was generated during the build process.
    
6.  The application will now start running, and you can access it by navigating to `http://localhost:<port_number>/` in your web browser.
    

That's it! You have now successfully run the Spring Boot application on your Windows machine.

Note: If you face any issues while running the application, please check the logs for any error messages or contact the project team for assistance.
