Start by cloning the repository to your local machine.

**git clone <repository-url>**

⚙️ Update Configuration
To change the stock name or update stock-specific values:

Navigate to the configuration file:
**src/main/java/com/nse/automation/config/Constants.java**

Update the following fields:

**STOCKNAME: Set the desired stock name.**

**52-week High and Low: Update the corresponding values in the same file.**

▶️ Run the Tests
Open a command prompt.

Navigate to the directory where the pom.xml file is located.

Execute the following command to clean and run the tests: 

**mvn clean test**

📊 Generate Allure Report
After the test execution is complete, generate the Allure report by running: 

**mvn allure:report**

🌐 Launch Allure Report in Browser
To view the Allure report in your browser, use: 

**mvn allure:serve**
