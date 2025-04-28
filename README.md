# Java Naive Bayes Predictor

## Overview
This is a Java-based GUI application designed to load a CSV file containing permutations of data. The application uses the Naive Bayes algorithm to predict the output when the user inputs their own permutation.

## Features
- Load and parse CSV files with data permutations.
- User-friendly GUI for data input and interaction.
- Predict outcomes based on user-provided permutations using the Naive Bayes algorithm.

## Prerequisites
- Java Development Kit (JDK) 8 or higher.
- A CSV file with properly formatted data for training.

## Installation
1. Clone the repository:
    ```bash
    git clone https://github.com/Kubab511/java-naive-bayes.git
    ```
2. Navigate to the project directory:
    ```bash
    cd java-naive-bayes
    ```
3. Compile the project:
    ```bash
    javac -d bin src/App.java
    ```
4. Run the application:
    ```bash
    java -cp bin Main
    ```
5. (Optional) Generate a frequency table
    ```bash
    python frequency_table.py
    ```

## Usage
1. Launch the application.
2. Load a CSV file containing training data.
3. Input your own permutation in the provided fields.
4. Click the "Predict" button to see the predicted output.

## CSV Format
The CSV file should have the following structure:
- Each row represents a data permutation.
- The last column should contain the expected output (label).

Example:
```
Feature1,Feature2,Feature3,Feature4,Output
Yes,No,Yes,Yes,Yes
No,No,Yes,No,No
```

## Technologies Used
- Java Swing for the GUI.

## License
This project is licensed under the [MIT License](LICENSE).

## Acknowledgments
- Java Swing Documentation