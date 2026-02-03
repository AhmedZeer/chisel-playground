package Collections

import _root_.circt.stage.ChiselStage
import chisel3._
import scala.collection.mutable.ArrayBuffer

class ScalaFIR(taps : Seq[Int]) {
  // Create a list with a specific length,
  // and an initialization value.
  // Because there is not a signal
  // processed until now, we fill it
  // with zeros.
  var registers = List.fill(taps.length)(0)

  // Mimicking Chisel's API.
  def poke(in : Int): Int = {
    // Append the new impulse to the head
    // of our list.
    registers = in :: registers.take(registers.length - 1)
    var res = 0
    for (i <- taps.indices){
      res += taps(i) * registers(i)
    }
    res
  }
};

class FIR(taps : Seq[Int], bitw : Int) extends Module {
  val io = IO( new Bundle {
    val in = Input(UInt(bitw.W))
    val out = Output(UInt(bitw.W))
  })

  val regs = ArrayBuffer[UInt]()
  for(i <- taps.indices){
    if (i==0) regs += io.in
    else regs += RegNext(regs(i-1), 0.U)
  }

  val muls = ArrayBuffer[UInt]()
  for(i <- taps.indices){
    muls += regs(i) * taps(i).U
  }

  val sum = ArrayBuffer[UInt]()
  for(i <- taps.indices){
    if(i == 0) sum += muls(i)
    else sum += sum(i-1) + muls(i)
  }

  io.out := sum.last
}


// Real HARDWARE Collections !
// Previous Scala collections were not
// reflected in the Verilog, real hardware.
// They were mere constant values. Now, we are
// literally carving the hardware.
class FIRv2(nElements : Int, bitWidth:Int) extends Module {
  val io = IO(new Bundle {
    val in = Input(UInt(bitWidth.W))
    val out = Output(UInt(bitWidth.W))
    val weights = Input(Vec(nElements, UInt(bitWidth.W)))
  })

  val regs = RegInit(VecInit(Seq.fill(nElements - 1)(0.U)))
  // Shifting registers simultanously.
  for(i <- 0 until nElements - 1){
    if(i == 0) regs(i) := io.in
    else regs(i) := regs(i-1)
  }

  // Multiplication
  val muls = Wire(Vec(nElements, UInt(bitWidth.W)))
  for(i <- 0 until nElements){
    if(i == 0) muls(i) := io.in * io.weights(i)
    else muls(i) := regs(i-1) * io.weights(i)
  }

  // Accumulation
  val sum = Wire(Vec(nElements, UInt(bitWidth.W)))
  for(i <- 0 until nElements){
    if(i == 0) sum(i) := muls(i)
    else sum(i) := sum(i-1) + muls(i)
  }
  io.out := sum.last
}


object Main extends App {
  ChiselStage.emitSystemVerilogFile(
    new FIRv2(3, 8),
    Array("--target-dir", "generated/")
  )
  println("Generated Parametrized FIR.")
}