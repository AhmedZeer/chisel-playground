# CHISEL Code Snippets. 

A repository where I collate my Hardware modules.  
Since it is used for educational purposes, it also includes
working, modern and modified code from the outdated [Chisel Bootcamp](https://github.com/freechipsproject/chisel-bootcamp).

---
For the sake of documenting the learning steps, here is what I 
learned in each module.

## MyRoutingArbiter
* We implemented the FIR using `for` loops to calculate the accumulation and multiplications.
* However, Scala has `High-Order Functions` that makes it easier for us to iterate through collections.
* `.map()`, `.map{case(a,b) => a+b}`, `.filter`, `.fold`, `.zipWithIndex`, `.reduce` etc. can be used to generate and manipulate Hardware.
* New FIR can be implemented with `io.out := (io.const zip taps).map{case(a, b) => }.reduce(_ + _)`, for example.
* This eliminates all the for loops required previously and provides more compact form.
* **Ready-Valid** Protocol for communication between digital modules is implemented in Chisel with `DecoupledIO`.
* A `Decoupled` consists of `bits: Output(), valid :Output() and ready: Input()` submodules.
* Using `Flipped(Decoupled)` reverses the functionality of each submodule from Input to Output and vice versa.

## RegisterFile
* Parameterized by the number of Read Ports, this module implements *RV32I* style of a register file.
* Implements the output read ports using `Vec`

## Collections
* Used `Vec` and `Seq` by Chisel and Scala respectively, to implement different hardware generators.
* The `Seq` Scala approach provides *static* parametrization, where the hardware cant take different input without compilation again.
* `Vec` on the other hand generates a *real* hardware that can interact with dynamic input.
* The latter is more expensive and prohibitive than the former.
* Used GoldenModel software model to cross-check check the hardware's output.
* Used Scala's `mutable.ArrayBuffer()` to dynamically add hardware static collections

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
