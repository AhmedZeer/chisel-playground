package FIR

import chisel3._
import chisel3.util._
import _root_.circt.stage.ChiselStage


class FIR(b0: Int, b1: Int, b2: Int, b3: Int) extends Module {
  val io = IO(new Bundle {
    val in = Input(UInt(8.W))
    val out = Output(UInt(8.W))
  })
  val reg0 = RegNext(io.in, 0.U)
  val reg1 = RegNext(reg0, 0.U)
  val reg2 = RegNext(reg1, 0.U)
  io.out := (io.in * b0.U(8.W)) +
            (reg0 * b1.U(8.W)) +
            (reg1 * b2.U(8.W)) +
            (reg2 * b3.U(8.W))
}

object Main extends App {
  ChiselStage.emitSystemVerilogFile(
    new FIR(1, 2, 3, 4),
    Array("--target-dir", "generated/")
  )
  println("Generated FIR.")
}

