# Horse Race Simulator

The purpose of this project is to simulate horse races on different tracks and in different weather conditions.

## Setup Instructions

To run this project locally, follow these steps:

1. **Clone this repository**
    Clone this repository to your local machine:
    ```
    git clone https://github.com/mujtaba1-1/HorseRaceSimulator.git
    ```
2. **Navigate the the project directory**
    ```
    cd HorseRaceSimulator
    ```

## Dependencies

Ensure you have the latest version of Java JDK (As of writing this JDK 24).

To check if you have Java installed, open command prompt and run:
```
java --version
javac -- version
```

## Running The Program

1. Open the following directory within your terminal:
    ```
    cd HorseRaceSimulator/Part2
    ```
2. Complile the Java files:
    ```
    javac Main.java
    ```
3. Execute the compiled class:
    ```
    java Main
    ```

The program should execute and run

## Usage Guidelines

1. **Track Customistaion**
    The user can customise the following:
    1. Number of lanes
    2. Track length
    3. Track Shape
    4. Track Weather

2. **Horse Customisation**
    The user can customise each horse individually. The number of horses shown depends on the number of lanes. The customisations are as follows:
    1. Horse breed
    2. Coat colour
    3. Given accessory

    The horse name, confidence and image are also shown

3. **Race Simulation**
    After customising the horses, the race begins.

4. **Results**
    A finishing message is displayed, with a different message depending on the outcome of the race. The user is given an option to view the race stats. These stats display the horse information and how they performed on that track.

