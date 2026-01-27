package PassThrough

import chisel3._
import chisel3.util._
import _root_.circt.stage.ChiselStage

class Passthrough extends Module {
  val io = IO(new Bundle {
    val in = Input(UInt(4.W))
    val out = Output(UInt(4.W))
  })
  io.out := io.in
}

class PassthroughGenerator(width :Int) extends Module {
  val io = IO(new Bundle {
    val in = Input(UInt(width.W))
    val out = Output(UInt(width.W))
  })
  io.out := io.in
}

object Main extends App {
  // Generate the simple Passthrough
  ChiselStage.emitSystemVerilogFile(
    new Passthrough,
    Array("--target-dir", "generated/")
  )
  println("Generated Passthrough.")

  ChiselStage.emitSystemVerilogFile(
    new PassthroughGenerator(20),
    Array("--target-dir", "generated/")
  )
  println("Generated PassthroughGenerator.")
}