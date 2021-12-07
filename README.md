# ConvertNumberToWord
## Prerequisite
This is a Java project so make sure you have installed [JDK 1.8 or above](https://www.oracle.com/java/technologies/downloads/#java8-windows) in your PC. The instruction to add JDK to your environment path will be discussed below.

If you want to see the source code and modify the test class, please install [IntelliJ](https://www.jetbrains.com/idea/download/#section=windows) on your PC.

This instruction assumes you will run the program in Windows OS.

---

### Add JDK to the environment path
Please refer to the video on [YouTube](https://www.youtube.com/watch?v=rxKDcenUJhc&t=1s&ab_channel=javatpoint) to add JDK to the environment variable. You can test it by entering “java -version” in your terminal, as the figure below shows.
```
java version "1.8.0_311"
Java(TM) SE Runtime Environment (build 1.8.0_311-b11)
Java HotSpot(TM) 64-Bit Server VM (build 25.311-b11, mixed mode)
```
If you see something like this, it means you have successfully added JDK to your environment variable.

---

## Run the application
### Terminal
There is a runnable jar file named “NumberCoverter.jar” created in NumberCoverter\out\artifacts\NumberCoverter_jar folder. 

Go to the terminal, change the path to where the jar file is located, and then enter```java -jar NumberCoverter.jar```to run the program.

### IntelliJ

- Click “File” -> “Open” -> Select the root folder of the project (NumberConverter) -> Click “OK”.
- Click ”File” -> ”Project Structure…” and then Add SDK.
- Run the main method in the Converter class. 
- Enter the value you want to convert in terminal.

---

### Notes when using the converter
The program will prompt “Please enter a numeric value:” and the user can enter the number they want to convert next to the prompt. 
Here are some examples of valid/invalid inputs listed at the table below.

| Valid Inputs | Invalid Inputs |
| ------------ | -------------- |
|1,234.09|0123.09|
|1234.09|0.9|
|0.35|12,34.56|
|0|12,3456|
|123|non-number Strings|

You can quit the program by entering “quit”, “exit”, or “q”, and you can enter “test” to run the unit tests in the ConverterTest class. Any other string values that are not in currency format will be validated as invalid inputs, and it will display a “Please check the input value format” message. 

---

## Limitation
The program can convert the number between 0.00 and 999,999,999,999,999.99. However, the upper bound can be extended by entering larger units in the largeValue array as shown below. 
```java
public class Converter {
    // line 17
    String[] largeValue = {"", "Thousand", "Million", "Billion", "Trillion"};
}
```
For more names of large numbers, please refer to this [page](https://en.wikipedia.org/wiki/Names_of_large_numbers).