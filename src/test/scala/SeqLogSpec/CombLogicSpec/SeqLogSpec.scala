package SeqLogSpec

import SeqLog._
import chisel3._
import chisel3.simulator.scalatest.ChiselSim
import org.scalatest.funspec.AnyFunSpec

class SeqLogSpec extends AnyFunSpec with ChiselSim {

  describe("SeqLog"){
    it("Takes the input, adds one and returns output"){
      simulate(new BasicReg) { dut =>
        dut.io.in.poke(10.U)
        dut.clock.step(1)
        for (i <- 0 until 100) {
          dut.io.in.poke(i.U)
          dut.io.out.expect(11.U)
        }
      }
    }

    it("Holds the maximum number"){
      simulate(new FindMax) { dut =>
        dut.io.max.expect(0.U)
        dut.io.in.poke(10.U)
        dut.clock.step(1)
        dut.io.max.expect(10.U)

        // Hold the larger
        dut.io.in.poke(5.U)
        dut.clock.step(1)
        dut.io.max.expect(10.U)
      }
    }


    it("Shift register"){
      simulate(new ShiftRegister()) { c =>
        var state = c.init
        for (i <- 0 until 10) {
          // poke in LSB of i (i % 2)
          c.io.in.poke(((i % 2) != 0).B)
          // update expected state
          state = ((state * 2) + (i % 2)) & 0xf
          c.clock.step(1)
          c.io.out.expect(state.U)
        }
      }
    }

  }
}
