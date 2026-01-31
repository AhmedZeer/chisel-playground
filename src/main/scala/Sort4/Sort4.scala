package Sort4

import chisel3._
import chisel3.util._
import _root_.circt.stage.ChiselStage

class Sort4 extends Module {
  val io = IO(new Bundle {
    // Unsorted Array
    val in0  = Input(UInt(16.W))
    val in1  = Input(UInt(16.W))
    val in2  = Input(UInt(16.W))
    val in3  = Input(UInt(16.W))

    // Sorted Array
    val out0  = Output(UInt(16.W))
    val out1  = Output(UInt(16.W))
    val out2  = Output(UInt(16.W))
    val out3  = Output(UInt(16.W))
  })

  val temp00 = Wire(UInt(16.W))
  val temp01 = Wire(UInt(16.W))
  val temp02 = Wire(UInt(16.W))
  val temp03 = Wire(UInt(16.W))
  
  when(io.in0 < io.in1){
    temp00 := io.in0
    temp01 := io.in1
  }.otherwise{
    temp00 := io.in1
    temp01 := io.in0
  }

  when(io.in2 < io.in3){
    temp02 := io.in2
    temp03 := io.in3
  }.otherwise{
    temp02 := io.in3
    temp03 := io.in2
  }

  val temp10 = Wire(UInt(16.W))
  val temp11 = Wire(UInt(16.W))
  val temp12 = Wire(UInt(16.W))
  val temp13 = Wire(UInt(16.W))
  when(temp00 < temp03){
    temp10 := temp00
    temp13 := temp03
  }.otherwise{
    temp10 := temp03
    temp13 := temp00
  }

  when(temp01 < temp02){
    temp11 := temp01
    temp12 := temp02
  }.otherwise{
    temp11 := temp02
    temp12 := temp01
  }

  // Final phase.
  when(temp10 < temp11){
    io.out0 := temp10
    io.out1 := temp11
  }.otherwise{
    io.out0 := temp11
    io.out1 := temp10
  }

  when(temp12 < temp13){
    io.out2 := temp12
    io.out3 := temp13
  }.otherwise{
    io.out2 := temp13
    io.out3 := temp12
  }

}

object Main extends App {
  ChiselStage.emitSystemVerilogFile(
    new Sort4,
    Array("--target-dir", "generated/")
  )
  println("Generated Max3.")
}