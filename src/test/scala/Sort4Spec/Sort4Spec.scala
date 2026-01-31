package Sort4Spec

import Sort4._
import chisel3._
import chisel3.simulator.scalatest.ChiselSim
import org.scalatest.funspec.AnyFunSpec

import scala.util.Random

class Sort4Spec extends AnyFunSpec with ChiselSim {

  describe("Sort4"){
    it("Sorts 4 given elements"){
      simulate(new Sort4) { dut =>
        dut.io.in0.poke(4.U)
        dut.io.in1.poke(3.U)
        dut.io.in2.poke(2.U)
        dut.io.in3.poke(1.U)

        dut.io.out0.expect(1.U)
        dut.io.out1.expect(2.U)
        dut.io.out2.expect(3.U)
        dut.io.out3.expect(4.U)
      }
    }
  }
}
