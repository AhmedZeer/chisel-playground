package PolyCircSpec

import PolyCirc._
import chisel3._
import chisel3.simulator.scalatest.ChiselSim
import org.scalatest.funspec.AnyFunSpec

class PolyCircSpec extends AnyFunSpec with ChiselSim {

  describe("PolyCirc") {
    it("Poly Circuit") {
      simulate(new PolyCirc) { c =>
        for (x <- 0 to 20) {
          for (select <- 0 to 2) {
            c.io.s.poke(select.U)
            c.io.x.poke(x.S)
            c.io.fx.expect(PolyUtils.poly(x, select).S)
          }
        }
      }
    }
  }
}