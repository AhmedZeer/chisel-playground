package MAC

import chisel3._
import chisel3.util._
import _root_.circt.stage.ChiselStage

// (A * B) + C
class MAC extends Module {
  val in_a = IO(Input(UInt(4.W)))
  val in_b = IO(Input(UInt(4.W)))
  val in_c = IO(Input(UInt(4.W)))

  // Since we are multiplying, almost
  // always 4 bits won't be enough to
  // represent 4 bits * bits operation.
  val out = IO(Output(UInt(8.W)))

  out := (in_a * in_b) + in_c
}


object Main extends App {
  // Generate the simple MAC
  ChiselStage.emitSystemVerilogFile(
    new MAC,
    Array("--target-dir", "generated/")
  )
  println("Generated MAC.")
}

