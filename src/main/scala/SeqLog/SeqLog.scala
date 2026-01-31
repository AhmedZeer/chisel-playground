package SeqLog

import chisel3._
import chisel3.util._
import _root_.circt.stage.ChiselStage

class BasicReg extends Module {
  val io = IO(new Bundle {
    val in = Input(UInt(8.W))
    val out = Output(UInt(8.W))
  })

  // val reg = Reg(UInt(8.W))
  // val reg = RegNext(io.in + 1.U) // Automatically infers the bitwidth etc.
  val reg = RegInit(UInt(12.W), 0.U) // Has an initial value.
  reg := io.in + 1.U
  io.out := reg
}


class FindMax extends Module {
  val io = IO(new Bundle {
    val in = Input(UInt(8.W))
    val max = Output(UInt(8.W))
  })
  val reg = RegInit(UInt(8.W), 0.U)
  when(reg < io.in){
    reg := io.in
  }
  io.max := reg
}

class ShiftRegister(val init: Int = 1) extends Module {
  val io = IO(new Bundle {
    val in  = Input(Bool())
    val out = Output(UInt(4.W))
  })

  val reg = RegInit(UInt(4.W), init.U)
  val nextState = (reg << 1) | io.in
  reg := nextState
  io.out := reg
}

object Main extends App {
  ChiselStage.emitSystemVerilogFile(
    new BasicReg,
    Array("--target-dir", "generated/")
  )
  println("Generated RegModule.")
}