package CombLogic

import chisel3._
import chisel3.util.Cat
import circt.stage.ChiselStage

class CombLogic extends Module{
  var out_mux = IO(Output(UInt(2.W)))
  var out_cat = IO(Output(UInt(4.W)))

  // Chisel's overloaded boolean.
  var s = false.B
  // Expecting to choose 1.
  out_mux := Mux(s, 2.U, 1.U)
  // 1, 10 -> 6
  out_cat := Cat(out_mux, 2.U)
}

class SumDataTypes extends Module {
  var out = IO(Output(UInt(2.W)))

  // The difference between Scala's `2`
  // and Chisel's overloaded `2`.
  println(2.U)
  println(2)

  // We can sum Chisel's data types.
  // However, not the way around.
  // `2.U + 2` is not permitted, for example.
  out := 2.U + 1.U
}


object Main extends App {
  // Generate the simple Passthrough
  ChiselStage.emitSystemVerilogFile(
    new CombLogic,
    Array("--target-dir", "generated/")
  )
  println("Generated CombLogic.")
}
