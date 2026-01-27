package MACSpec

import MAC._
import chisel3._
import chisel3.simulator.scalatest.ChiselSim
import org.scalatest.funspec.AnyFunSpec

class MACSpec extends AnyFunSpec with ChiselSim {

  describe("MACSpec"){
    it("Tests Multiply and ACcumulate Module"){
      simulate(new MAC) { dut =>
        dut.in_a.poke(2.U)
        dut.in_b.poke(2.U)
        dut.in_c.poke(2.U)
        dut.out.expect(6.U)
      }
    }
  }
}
