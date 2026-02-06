package RoutingArbiter

import chisel3._
import chisel3.util._
import _root_.circt.stage.ChiselStage

class MyRoutingArbiter(numChannels: Int) extends Module {
  val io = IO(new Bundle {
    val in = Vec(numChannels, Flipped(Decoupled(UInt(8.W))))
    val out = Decoupled(UInt(8.W))
  })

  // Priority Selection
  val winner = PriorityMux(
    io.in.map(_.valid).zipWithIndex.map{case(valid,index) => (valid, index.U)}
  )

  // Send data
  io.out.bits := io.in(winner).bits

  // Assign it is valid
  // If any channel is valid our output MUST be valid
  io.out.valid := io.in.map(_.valid).reduce(_ || _)

  // Ready signal to the source of the data
  // and not ready signal for others
  for (i <- 0 until numChannels) {
    io.in(i).ready := io.out.ready && (winner === i.U)
  }
}

object Main extends App {
  ChiselStage.emitSystemVerilogFile(
    new MyRoutingArbiter(10),
    Array("--target-dir", "generated/")
  )
  println("Generated Arbiter.")
}