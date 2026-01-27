package CombLogicSpec

import CombLogic._
import chisel3._
import chisel3.simulator.scalatest.ChiselSim
import org.scalatest.funspec.AnyFunSpec

class CombLogicSpec extends AnyFunSpec with ChiselSim {

  describe("CombLogic"){
    it("Tests Mux and Cat operators in Chisel."){
      simulate(new CombLogic) { dut =>
        dut.out_mux.expect(1.U)
        dut.out_cat.expect(6.U)
      }
    }
  }
  
}
