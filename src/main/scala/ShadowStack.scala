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

  val savedAddr = Reg(Vec(depth, UInt(32.W)))
  val idx = RegInit(0.U(32.W))

  when(io.jal) { 
    // stack one
    when(idx === (depth-1).U) {
      // TODO internal vec overflowed, 
    } .otherwise {
      savedAddr(idx) := io.curentAddr
      val printvariable = savedAddr(idx)
      printf(p"jal $idx $printvariable\n")
      idx := idx+1.U
    }
  }

  when(io.ret) {
    // if we can destack
    when(idx === 0.U) { 
      io.outputValid := false.B
      printf(p"ret $idx\n")
    } .otherwise {
      // Check if we return to the good location
      when(io.targetAddr === savedAddr(idx)) {
        io.outputValid := true.B
        val printvariable = savedAddr(idx)
        printf(p"ret ok $idx $printvariable\n")
      } .otherwise {
        io.outputValid := false.B
        val printvariable = savedAddr(idx)
        val printvariable2 = io.outputValid
        printf(p"ret ko $idx $printvariable2 $printvariable\n")
      }
      idx := idx-1.U
    }
  } .otherwise {
      io.outputValid := true.B
  }
}

object ShadowStackMain extends App {
  chisel3.Driver.execute(args, () => new ShadowStack)
}
