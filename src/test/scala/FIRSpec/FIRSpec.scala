package FIRSpec

import FIR._
import chisel3._
import chisel3.simulator.scalatest.ChiselSim
import org.scalatest.funspec.AnyFunSpec

class FIRSpec extends AnyFunSpec with ChiselSim {

  describe("FIR"){
    it("0 weighted sum."){
      simulate(new FIR(0, 0, 0, 0)) { c =>
        c.io.in.poke(0.U)
        c.io.out.expect(0.U)
        c.clock.step(1)
        c.io.in.poke(4.U)
        c.io.out.expect(0.U)
        c.clock.step(1)
        c.io.in.poke(5.U)
        c.io.out.expect(0.U)
        c.clock.step(1)
        c.io.in.poke(2.U)
        c.io.out.expect(0.U)
      }
    }

    it("1 weighted sum"){
      simulate(new FIR(1, 1, 1, 1)) { c =>
        c.io.in.poke(1.U)
        c.io.out.expect(1.U)  // 1, 0, 0, 0
        c.clock.step(1)
        c.io.in.poke(4.U)
        c.io.out.expect(5.U)  // 4, 1, 0, 0
        c.clock.step(1)
        c.io.in.poke(3.U)
        c.io.out.expect(8.U)  // 3, 4, 1, 0
        c.clock.step(1)
        c.io.in.poke(2.U)
        c.io.out.expect(10.U)  // 2, 3, 4, 1
        c.clock.step(1)
        c.io.in.poke(7.U)
        c.io.out.expect(16.U)  // 7, 2, 3, 4
        c.clock.step(1)
        c.io.in.poke(0.U)
        c.io.out.expect(12.U)  // 0, 7, 2, 3
      }
    }

    it("custom weighted sum"){
      simulate(new FIR(1, 2, 3, 4)) {c =>
        c.io.in.poke(1.U)
        c.io.out.expect(1.U)  // 1*1, 0*2, 0*3, 0*4
        c.clock.step(1)
        c.io.in.poke(4.U)
        c.io.out.expect(6.U)  // 4*1, 1*2, 0*3, 0*4
        c.clock.step(1)
        c.io.in.poke(3.U)
        c.io.out.expect(14.U)  // 3*1, 4*2, 1*3, 0*4
        c.clock.step(1)
        c.io.in.poke(2.U)
        c.io.out.expect(24.U)  // 2*1, 3*2, 4*3, 1*4
        c.clock.step(1)
        c.io.in.poke(7.U)
        c.io.out.expect(36.U)  // 7*1, 2*2, 3*3, 4*4
        c.clock.step(1)
        c.io.in.poke(0.U)
        c.io.out.expect(32.U)  // 0*1, 7*2, 2*3, 3*4
      }
    }
  }
}
