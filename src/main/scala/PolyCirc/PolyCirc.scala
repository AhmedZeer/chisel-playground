package PolyCirc

import chisel3._
import chisel3.util._
import _root_.circt.stage.ChiselStage

object PolyUtils {
  // These are now "static" methods
  def poly0(x: Int): Int = x * x - 2 * x + 1
  def poly1(x: Int): Int = 2 * x * x + 6 * x + 3
  def poly2(x: Int): Int = 4 * x * x - 10 * x - 5

  def poly(x: Int, s: Int): Int = {
    if (s == 0) poly0(x)
    else if (s == 1) poly1(x)
    else poly2(x)
  }
}

class PolyCirc extends Module {

  val io = IO(new Bundle {
    val s = Input(UInt(2.W))
    val x = Input(SInt(32.W))
    val fx = Output(SInt(32.W))
  })

  val xSquared = Wire(SInt(32.W))
  xSquared := io.x * io.x
  when(io.s === 0.U){
    io.fx := xSquared - 2.S * io.x + 1.S
  }.elsewhen(io.s === 1.U){
    io.fx := xSquared * 2.S + 6.S * io.x + 3.S
  }.otherwise{
    io.fx := xSquared * 4.S - 10.S * io.x - 5.S
  }

}

object Main extends App {
  ChiselStage.emitSystemVerilogFile(
    new PolyCirc,
    Array("--target-dir", "generated/")
  )
  println("Generated PolyCirc.")
}