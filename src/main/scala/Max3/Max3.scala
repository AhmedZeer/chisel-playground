package Max3

import chisel3._
import chisel3.util._
import _root_.circt.stage.ChiselStage

class Max3 extends Module {
  val io = IO(new Bundle {
    val in0  = Input(UInt(16.W))
    val in1  = Input(UInt(16.W))
    val in2  = Input(UInt(16.W))

    // Max of 3
    val out0  = Output(UInt(16.W))
  })

  when(io.in0 > io.in1 && io.in0 > io.in2){
    io.out0 := io.in0
  }.elsewhen(io.in1 > io.in2){
    io.out0 := io.in1
  }.otherwise{
    io.out0 := io.in2
  }
}

object Main extends App {
  ChiselStage.emitSystemVerilogFile(
    new Max3,
    Array("--target-dir", "generated/")
  )
  println("Generated Max3.")
}

