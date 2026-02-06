package Neuron

import chisel3._
import chisel3.util._
import _root_.circt.stage.ChiselStage


class Neuron(weights: Seq[Int], nParams: Int, act: SInt => SInt) extends Module {
  val io = IO(new Bundle {
    val in = Input(Vec(nParams, SInt(64.W)))
    val out = Output(SInt(64.W))
  })
  val accum = (io.in zip weights).map{case(x, w) => x * w.S}.reduce(_+_)
  io.out := act(accum)
}

object Main extends App {

  val relu: SInt => SInt = x => Mux(x > 0.S, x, 0.S)

  ChiselStage.emitSystemVerilogFile(
    new Neuron(Seq(1, 1), 2, relu),
    Array("--target-dir", "generated/")
  )
  println("Generated Neuron.")
}