package ArbiterSpec

import Arbiter._
import chisel3._
import chisel3.simulator.scalatest.ChiselSim
import org.scalatest.funspec.AnyFunSpec
import scala.util.Random

class ArbiterSpec extends AnyFunSpec with ChiselSim {

  describe("Arbiter"){
    it("Passes data from fifo to PE0 or PE1"){
      val data = Random.nextInt(65536)
      simulate(new Arbiter) { c =>
        c.io.fifo_data.poke(data)
        for (i <- 0 until 8) {
          // Test all input cases.
          c.io.fifo_valid.poke((((i >> 0) % 2) != 0).B)
          c.io.pe0_ready.poke((((i >> 1) % 2) != 0).B)
          c.io.pe1_ready.poke((((i >> 2) % 2) != 0).B)

          c.io.fifo_ready.expect((i > 1).B)
          c.io.pe0_valid.expect((i == 3 || i == 7).B)
          c.io.pe1_valid.expect((i == 5).B)

          if (i == 3 || i ==7) {
            c.io.pe0_data.expect((data).U)
          } else if (i == 5) {
            c.io.pe1_data.expect((data).U)
          }
        }
      }
    }
  }
}
