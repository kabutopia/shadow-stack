// See README.md for license details.

package shadowstack

import chisel3._

/**
  * 
  * 
  * 
  */
class ShadowStack(depth: Int = 32) extends Module {
  val io = IO(new Bundle {
    val curentAddr  = Input(UInt(32.W))
    val jal         = Input(Bool())
    val ret         = Input(Bool())
    val targetAddr  = Input(UInt(32.W))
    val outputValid = Output(Bool())
  })

  val savedAddr = Mem(depth, UInt(32.W))
  val idx = RegInit(0.U(32.W))
  val out = RegInit(Bool(true))
  io.outputValid := out

  when(io.jal) {
    // stack one
    when(idx === (depth-1).U) {
      // TODO internal vec overflowed, 
    } .otherwise {
      // save the current address
      savedAddr(idx) := io.curentAddr
      idx := idx+1.U
    }
  }

  when(io.ret) {
    // check if we can destack
    when(idx === 0.U) { 
      out := false.B
    } .otherwise {
      // Check if we return to the good location
      when(io.targetAddr === savedAddr(idx-1.U)) {
        out := true.B
      } .otherwise {
        out := false.B
      }
      idx := idx-1.U
    }
  } .otherwise {
      out := true.B
  }
}

object ShadowStackMain extends App {
  chisel3.Driver.execute(args, () => new ShadowStack)
}
