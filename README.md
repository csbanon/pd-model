# Preferential Deletion Model (*PDModel*)

## Overview
- **Year:** 2020
- **Language(s):** Java, TeX
- **Discipline(s):** Algorithm Design and Analysis, Graph Theory, Network Theory
- **Keywords:** `Degree-Distribution`, `Dynamic-Random-Graphs`, `Dynamic-Random-Networks`, `Graphs`, `Graph-Algorithms`, `Graph-Generation`, `Graph-Theory`, `Networks`, `Network-Theory`, `Preferential-Deletion`, `Preferential-Node-Deletion`, `Social-Circles`, `Social-Media`, `Social-Networks`, `Social-Network-Analysis`, `Web-Like-Networks`

## Description
The *Preferential Deletion Model (*PDModel*)* is an implementation of the original discrete-time random graph generation process described by Narsingh Deo and Aurel Cami in their 2005 paper [*Preferential Deletion in Dynamic Models of Web-Like Networks*](https://www.sciencedirect.com/science/article/abs/pii/S0020019006003632).

This implementation was then used to replicate the findings of Deo and Cami's study. This project resulted in the following:

1. [*An Implementation of Preferential Deletion in Dynamic Models of Web-Like Networks*](https://github.com/csbanon/pd-model/blob/master/paper/pd-model-paper.pdf), a research-style paper in which the findings from Deo and Cami's original study are replicated. Please note that this is not a published paper.
2. The source code for the implementation of the *PDModel*, developed in Java.

This project is the first of a two-part research study conducted in the *COT 5405: Design and Analysis of Algorithms* graduate course at the University of Central Florida. This part presents an implementation of Deo and Cami's original model and replicates their findings, while the second part extends the implementation to account for changes in existing connections throughout the random graph generation process.

## Build Instructions
1. Download the `PDModel` repository.
2. Open `Run.java` in the `code` directory. Use this file to modify the values of `TIME`, `TRIALS`, and `P`. These values are defined as follows:
* `TIME`: the discrete value of time the model will run for.
* `TRIALS`: the number of trials to run.
* `P`: the value of p for the model. (There may be various values for p to be used for comparisons. For a detailed explanation of p, see the [paper](https://github.com/csbanon/pd-model/blob/master/paper/pd-model-paper.pdf).)
3. Compile `Run.java` using the following command: `javac Run.java`
4. Run the file using the following command: `java Run`
