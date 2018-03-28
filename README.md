# lending-pool

## Basic steps to build and run project
In order to be able to use this code please follow next steps:
* Be sure you have installed Java 8. If you want to use java 9 please build code without test suite (Java 9 is incopatible 
with *PowerMockito* which is used on **lending-data-source** module).
* Be sure you have installed *Maven*.
* Go to *lending-pool* directory and run command: **mvn clean install**
  * This command will build project, run unit tests and also create an executable jar **lendingCalculation.jar** in **/libs** folder.
* Run command: **java -jar libs/lendingCalculation.jar /Users/{your_user}/{your_data_directory}/market.csv 2000**
  * This command will calculate loan for specific input params. First param is a *csv* file with input source. Second param is loan amount.
