package StateMachine

import chisel3._
import chisel3.util._
import _root_.circt.stage.ChiselStage

object SMUtils {
  def states = Map("idle" -> 0, "coding" -> 1, "writing" -> 2, "grad" -> 3)
  def life(state:Int, coffee:Boolean, pressure:Boolean, idea:Boolean): Int = {
    var nextState = states("idle")
    if (state == states("idle")) {
      if      (coffee) { nextState = states("coding") }
      else if (idea) { nextState = states("idle") }
      else if (pressure) { nextState = states("writing") }
    } else if (state == states("coding")) {
      if      (coffee) { nextState = states("coding") }
      else if (idea || pressure) { nextState = states("writing") }
    } else if (state == states("writing")) {
      if      (coffee || idea) { nextState = states("writing") }
      else if (pressure) { nextState = states("grad") }
    }

    nextState
  }
}


class StateMachine extends Module {
  val io = IO(new Bundle {
    val state = Input(UInt(2.W))
    val coffee = Input(Bool())
    val idea = Input(Bool())
    val pressure = Input(Bool())
    val nextState = Output(UInt(2.W))
  })

  val idle :: coding :: writing :: grad :: Nil = Enum(4)

  when(io.state === idle){
    when(io.coffee){
      io.nextState := coding
    }.elsewhen(io.idea){
      io.nextState := idle
    }.elsewhen(io.pressure){
      io.nextState := writing
    }

  }.elsewhen(io.state === coding){
    when(io.coffee){
      io.nextState := coding
    }.elsewhen(io.idea){
      io.nextState := writing
    }.elsewhen(io.pressure){
      io.nextState := writing
    }
  }.elsewhen(io.state === writing){
    when(io.coffee){
      io.nextState := writing
    }.elsewhen(io.idea){
      io.nextState := writing
    }.elsewhen(io.pressure){
      io.nextState := grad
    }
  }
}

object Main extends App {
  ChiselStage.emitSystemVerilogFile(
    new StateMachine,
    Array("--target-dir", "generated/")
  )
  println("Generated Max3.")
}