# CHISEL Code Snippets. 

A repository where I collate my Hardware modules.  
Since it is used for educational purposes, it also includes
working, modern and modified code from the outdated [Chisel Bootcamp](https://github.com/freechipsproject/chisel-bootcamp).

---
For the sake of documenting the learning steps, here is what I 
learned in each module.

## Parameterization & Generators
* Writing generic & parametric hardware modules.
* Conditional parameters with `Some()` or `None`.
* Using `match` statement for decision-making.
* `Bool` VS. `Boolean`, the former is CHISEL's dtype and the latter is Scala's.
* `0 Width` wires reflection at Verilog elaboration.
* `+&` and `-&` operators for passing the sum/sub with carry.
* `case` classes and `implicit` args.

## Finite Impulse Response (FIR) Filter
* Used `RegNext()` modules to implement a Shift Register.
* Basic MAC operations to implement delayed signal convolution.

## SeqLog
* Used various register types : `Reg(), RegInit() and RegNext()`.
* Implemented a Shift Register.
* Implemented a Max module

## StateMachine
* Simulating a state machine using flow control.
* Basic mapping and enum operations for states.

## PolyCirc
* Calculating polynomial functions.
* Switching between available implementations through flow control.

## Sort4
* Sort given 4 inputs.
* Storing intermediate switching using `Wire`.

## Max3
* Select the maximum input.
* Use `when(), .elsewhen() and .otherwise()` clauses for controlling the flow.

## Arbiter
* Given a FIFO and various PE signals, distribute data with priority.
* Using Bundled IO

## MAC
* A general Multiply-Accumulate component.

## CombLogic
* How Scala's and Chisel's datatypes differ.
* Mux and Cat operations in Chisel.

## PassThrough
* Straightforward component that merely reflects its input.
* Creating parameterized Chisel Generators.
* Running unit tests.
* Introduction to Chisel datatypes and SystemVerilog elaboration.
