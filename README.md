# BioHazard
Versatile, compact and easy to use Genetic Algorithm and Genetic Programming engine.

Simple things are beautiful things, a handful of classes constitutes the entire engine!

A minimal amount of configuration and composition to get something running!

## Requirements
*  [**Java 17**](https://sdkman.io/) development kit for compilation of library and execution of demo programs.
*  [**Gradle 7.4.2**](https://gradle.org/) for project and dependancy management.

## Demos
Ensure you have successfully compiled the project before running the demonstrations by executing:

> .\gradlew.bat clean build

---

### Circles
> .\gradlew.bat run --args=circles

This demonstration creates a landscape of small circles and then attempts to find the largest circle that can fit amongst them without overlap. 

Press **R** to reset the demo or resize the window to make the environment smaller and more crowded.

---

### Equations
> .\gradlew.bat run --args=equations

This demonstration attempts to find the best way to arrange a formula of numbers to achieve a target value. 

There are two sets of numbers, a large set [25, 50, 75, 100] and a small set [1..10]. 

The target value is a whole number randomly chosen from the range [1..999], and the basic mathematical operators are used, namely +, -, * and /. 

The challenge here is to find a solution which uses **ALL** the numbers!

---

### Packman
> .\gradlew.bat run --args=packman

This demonstration first generates a convex polygon and then attempts to fill this polygon with 10 non overlapping circles whilst maximizing polygon coverage. 

Press **R** to reset the demo and generate a new convex polygon.

---

### Rostering
> .\gradlew.bat run --args=rostering

This demonstration attempts to find an optimal solution to what is known as the nurse rostering problem. 

Albeit with a simplified domain model of employees, shifts and abscense and a small handful of constraints.

---

### Worms
> .\gradlew.bat run --args=worms

A demonstration of Genetic Programming and emergent behaviour. 

This experiment attempts to evolve worm like creatures that navigate a procedurally generated 2D world (using cellular automata) avoiding obstacles whilst searching for the food.

**Minimize** the window to prevent the replay of current generations champion and thus speed up evolution cycles.

The world changes every 5 minutes to ensure the creatures evolve a more holisitic survival strategy.

## License
Apache 2.0 License

Copyright © 2021 Jean d'Arc