package PassThroughSpec

import PassThrough._
import chisel3._
import chisel3.simulator.scalatest.ChiselSim
import chisel3.simulator.stimulus.{RunUntilFinished, RunUntilSuccess}
import chisel3.util.Counter
import org.scalatest.funspec.AnyFunSpec

class PassThroughSpec extends AnyFunSpec with ChiselSim {
  describe("Regular PassThrough"){
    it("Tests the regular PassThrough module."){
      simulate(new Passthrough) { dut =>
        dut.io.in.poke(1.U)
        dut.io.out.expect(1.U)
      }
    }
  }

  describe("PassThrough Generator"){
    it("Tests the Generator of PassThrough"){
      simulate(new PassthroughGenerator(5)) { dut =>
        dut.io.in.poke(31.U)
        dut.io.out.expect(31.U)
      }
    }
  }

}
