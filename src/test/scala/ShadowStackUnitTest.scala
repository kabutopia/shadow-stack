// See README.md for license details.

package shadowstack

import java.io.File

import chisel3.iotesters
import chisel3.iotesters.{ChiselFlatSpec, Driver, PeekPokeTester}

class ShadowStackUnitTester(c: ShadowStack) extends PeekPokeTester(c) {
  val retAddList = List(Int)
  //val dest = rnd.nextInt(0xffffffff)
  val dest1 = 0xabcd1234
  val dest2 = 0x1234bcda
  for (i <- 1 to 20) {
    //val toDo = rnd.nextInt(20)
    val toDo = i
    toDo match {
      // test jal
      case 1 => {
        poke(c.io.curentAddr, dest1)
        poke(c.io.jal, 1)
        poke(c.io.ret, 0)
        step(1)
        expect(c.io.outputValid, true)
      } 
      // test jal
      case 2 => {
        poke(c.io.curentAddr, dest2)
        poke(c.io.jal, 1)
        poke(c.io.ret, 0)
        step(1)
        expect(c.io.outputValid, true)
      } 
      // test ret ok
      case 4 => {
        poke(c.io.targetAddr, dest2)
        poke(c.io.jal, 0)
        poke(c.io.ret, 1)
        step(1)
        expect(c.io.outputValid, true)
      } 
      // test ret ok
      case 7 => {
        poke(c.io.targetAddr, dest1)
        poke(c.io.jal, 0)
        poke(c.io.ret, 1)
        step(1)
        expect(c.io.outputValid, true)
      } 
      // test jal
      case 10 => {
        poke(c.io.curentAddr, dest2)
        poke(c.io.jal, 1)
        poke(c.io.ret, 0)
        step(1)
        expect(c.io.outputValid, true)
      }
      // test ret ko
      case 12 => {
        poke(c.io.targetAddr, dest2+4)
        poke(c.io.jal, 0)
        poke(c.io.ret, 1)
        step(1)
        expect(c.io.outputValid, false)
      } 
      case _ => {
        poke(c.io.jal, 0)
        poke(c.io.ret, 0)
        step(1)
      }
    }
  }
}

/**
  * This is a trivial example of how to run this Specification
  * From within sbt use:
  * {{{
  * testOnly gcd.GCDTester
  * }}}
  * From a terminal shell use:
  * {{{
  * sbt 'testOnly gcd.GCDTester'
  * }}}
  */
class ShadowStackTester extends ChiselFlatSpec {
  // Disable this until we fix isCommandAvailable to swallow stderr along with stdout
  private val backendNames = if(false && firrtl.FileUtils.isCommandAvailable(Seq("verilator", "--version"))) {
    Array("firrtl", "verilator")
  }
  else {
    Array("firrtl")
  }
  for ( backendName <- backendNames ) {
    "ShadowStack" should s"calculate proper greatest common denominator (with $backendName)" in {
      Driver(() => new ShadowStack, backendName) {
        c => new ShadowStackUnitTester(c)
      } should be (true)
    }
  }

  "Basic test using Driver.execute" should "be used as an alternative way to run specification" in {
    iotesters.Driver.execute(Array(), () => new ShadowStack) {
      c => new ShadowStackUnitTester(c)
    } should be (true)
  }

  "using --backend-name verilator" should "be an alternative way to run using verilator" in {
    if(backendNames.contains("verilator")) {
      iotesters.Driver.execute(Array("--backend-name", "verilator"), () => new ShadowStack) {
        c => new ShadowStackUnitTester(c)
      } should be(true)
    }
  }

  "running with --is-verbose" should "show more about what's going on in your tester" in {
    iotesters.Driver.execute(Array("--is-verbose"), () => new ShadowStack) {
      c => new ShadowStackUnitTester(c)
    } should be(true)
  }

  /**
    * By default verilator backend produces vcd file, and firrtl and treadle backends do not.
    * Following examples show you how to turn on vcd for firrtl and treadle and how to turn it off for verilator
    */

  "running with --generate-vcd-output on" should "create a vcd file from your test" in {
    iotesters.Driver.execute(
      Array("--generate-vcd-output", "on", "--target-dir", "test_run_dir/make_a_vcd", "--top-name", "make_a_vcd"),
      () => new ShadowStack
    ) {

      c => new ShadowStackUnitTester(c)
    } should be(true)

    new File("test_run_dir/make_a_vcd/make_a_vcd.vcd").exists should be (true)
  }

  "running with --generate-vcd-output off" should "not create a vcd file from your test" in {
    iotesters.Driver.execute(
      Array("--generate-vcd-output", "off", "--target-dir", "test_run_dir/make_no_vcd", "--top-name", "make_no_vcd",
      "--backend-name", "verilator"),
      () => new ShadowStack
    ) {

      c => new ShadowStackUnitTester(c)
    } should be(true)

    new File("test_run_dir/make_no_vcd/make_a_vcd.vcd").exists should be (false)

  }

}
