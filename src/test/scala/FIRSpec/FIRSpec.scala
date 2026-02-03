package FIRSpec

import Collections._
import chisel3._
import chisel3.simulator.scalatest.ChiselSim
import org.scalatest.funspec.AnyFunSpec

class FIRSpec extends AnyFunSpec with ChiselSim {

  val weights = Seq(1, 1, 1, 1)
  val goldenModel = new ScalaFIR(weights)

  describe("FIR") {
    it("Golden Model Comparison.") {
      simulate(new FIR(weights, 8)) { c =>
        for (i <- 0 until 100) {
          val input = scala.util.Random.nextInt(8)
          val goldenModelResult = goldenModel.poke(input)

          c.io.in.poke(input.U)
          c.io.out.expect(goldenModelResult.U, s"i $i, input $input, gm $goldenModelResult, ${c.io.out.peek().litValue}")
          c.clock.step(1)
        }
      }
    }

    it("Golden Model Comparison2.") {
      simulate(new FIRv2(weights.length, 8)) { c =>
        c.io.weights(0).poke(1.U)
        c.io.weights(1).poke(1.U)
        c.io.weights(2).poke(1.U)
        c.io.weights(3).poke(1.U)
        for(i <- 0 until 100) {
          val input = scala.util.Random.nextInt(8)
          val goldenModelResult = goldenModel.poke(input)
          c.io.in.poke(input.U)
          c.io.out.expect(goldenModelResult.U, s"i $i, input $input, gm $goldenModelResult, ${c.io.out.peek().litValue}")
          c.clock.step(1)
        }
      }

    }
  }
}
