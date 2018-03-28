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
  
## Some explanation about algorithm
1. The algorithm parse all lenders and split their amount on small bundles by £10.
2. All lenders are sorted by *interest rate*.
3. Algorithm take only 1 bundle from each lender to compute the requeste amount.
4. If the requested amount is computed then is calculated monthly interest rate.
5. If the requested amount is not computed after parsing all lenders, algorithm is trying to compute sum by taking one more bundle from sorted lenders in asceding order by rate **(Step 3)**.
6. If there requeste amount is not computed and there are no lenders with available bundles then an exception is thrown saying that there are no sufficient funds.
