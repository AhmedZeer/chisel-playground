package OptionalParam

import _root_.circt.stage.ChiselStage
import chisel3._

class OptionalResetReg(resetBy: Option[Int] = None) extends Module {
  val io = IO(new Bundle {
    val in = Input(UInt(8.W))
    val out = Output(UInt(8.W))
  })

  // Using a switch case to create a register
  val reg0 = resetBy match {
    case Some(value) => RegInit(UInt(8.W), value.U)
    case None => Reg(UInt(8.W))
  }

  // Using the .get() method on the Some() for
  // optional parameterization.
  val reg1 = if(resetBy.isDefined) RegInit(UInt(8.W), resetBy.get.U) else Reg(UInt(8.W))
}

// Boolean -> Scala Bool
// Bool -> CHISEL Bool
// Using .getOrElse() to either add the c_in or not.
// Also, zero width wires merely depicts the value 0.
class HalfFullAdder(isFull: Boolean) extends Module {
  val io = IO(new Bundle {
    // val cin = if (isFull) Some(Input(UInt(1.W))) else None
    val cin = if (isFull) Input(UInt(1.W)) else Input(UInt(0.W))
    val a = Input(UInt(8.W))
    val b = Input(UInt(8.W))

    val s = Output(UInt(8.W))
    val cout = Output(UInt(1.W))
  })

  // `+&` to sum with carry.
  val sum = io.a +& io.b +& io.cin//.getOrElse(0.U)

  // Sum with carry
  io.s := sum(0)
  io.cout := sum(1)
}


case class OperatorParams(
                           in_bitwidth: Int,
                           out_bitwidth: Int,
                           operation: (UInt, UInt) => UInt
                         )


class Operator(operatorParams: OperatorParams) extends Module {
  val io = IO(new Bundle {
    val a = Input(UInt(operatorParams.in_bitwidth.W))
    val b = Input(UInt(operatorParams.in_bitwidth.W))
    val out = Output(UInt(operatorParams.out_bitwidth.W))
  })
  val result = operatorParams.operation(io.a, io.b)
  io.out := result
}

object Main extends App {
  def add(a: UInt, b:UInt): UInt = (a + b)
  def sub(a: UInt, b:UInt): UInt = (a - b)

  val operatorAdd = OperatorParams(8, 8, add)
  val operatorSub = OperatorParams(16, 16, sub)

  ChiselStage.emitSystemVerilogFile(
    // new OptionalResetReg(resetBy = Some(10)),
    // new Operator(operatorAdd),
    new Operator(operatorSub),
    Array("--target-dir", "generated/")
  )
  println("Generated.")
}

