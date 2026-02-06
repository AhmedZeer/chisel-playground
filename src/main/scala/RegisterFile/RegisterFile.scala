package RegisterFile

import chisel3._
import chisel3.util._
import _root_.circt.stage.ChiselStage

class RegisterFile(addrWidth: Int, dataWidth: Int, readPort: Int) extends Module {
  val io = IO( new Bundle {
    val wen = Input(Bool())
    val waddr = Input(UInt(addrWidth.W))
    val wdata = Input(UInt(dataWidth.W))

    val raddr = Input(Vec(readPort, UInt(addrWidth.W)))
    val rdata = Output(Vec(readPort, UInt(dataWidth.W)))
  })
  val regfile = RegInit(VecInit(Seq.fill(1 << addrWidth)(0.U(dataWidth.W))))
  when(io.wen){
    regfile(io.waddr) := io.wdata
  }
  for(i <- 0 until readPort){
    when(io.raddr(i) === 0.U){
      io.rdata(i) := 0.U
    }.otherwise{
      io.rdata(i) := regfile(io.raddr(i))
    }
  }
}

object Main extends App {
  ChiselStage.emitSystemVerilogFile(
    new RegisterFile(5, 32, 2),
    Array("--target-dir", "generated/")
  )
  println("Generated RegFile.")
}