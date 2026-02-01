package FIR
import chisel3._

class MyShiftRegister(bitwidth: Int) extends  Module {

  val io = IO(new Bundle {
    val in = Input(UInt(bitwidth.W))
    val out = Output(UInt(bitwidth.W))
  })

  val reg0 = RegInit(UInt(bitwidth.W), 0.U)
  val reg1 = RegInit(UInt(bitwidth.W), 0.U)
  val reg2 = RegInit(UInt(bitwidth.W), 0.U)
  val reg3 = RegInit(UInt(bitwidth.W), 0.U)

  io.out := reg3
  reg3 := reg2
  reg2 := reg1
  reg1 := reg0
  reg0 := io.in
}
