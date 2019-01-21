Shadow-Stack Chisel Project 
===========================

### Testing the project
```sh
sbt 'testOnly shadowstack.ShadowStackTester -- -z Basic'
```
>This tells the test harness to only run the test in ShadowStackTester that contains the word Basic
There are a number of other examples of ways to run tests in there, but we just want to see that
one works.

### It worked!
You are ready to go. We have a few recommended practices and things to do.
* Use packages and following conventions for [structure](http://www.scala-sbt.org/0.13/docs/Directories.html) and [naming](http://docs.scala-lang.org/style/naming-conventions.html)
* Package names should be clearly reflected in the testing hierarchy
* Build tests for all your work.
* This template includes a dependency on the Chisel3 IOTesters, this is a reasonable starting point for most tests
* You can remove this dependency in the build.sbt file if necessary
* Change the name of your project in the build.sbt file
* Change your README.md

There are [instructions for generating Verilog](https://github.com/freechipsproject/chisel3/wiki/Frequently-Asked-Questions#get-me-verilog) on the Chisel wiki.

Some backends (verilator for example) produce VCD files by default, while other backends (firrtl and treadle) do not.
You can control the generation of VCD files with the `--generate-vcd-output` flag.

To run the simulation and generate a VCD output file regardless of the backend:
```bash
sbt 'test:runMain shadowstack.ShadowStackMain --generate-vcd-output on'
```

To run the simulation and suppress the generation of a VCD output file:
```bash
sbt 'test:runMain shadowstack.ShadowStackMain --generate-vcd-output off'
```
## License
This is free and unencumbered software released into the public domain.

Anyone is free to copy, modify, publish, use, compile, sell, or
distribute this software, either in source code form or as a compiled
binary, for any purpose, commercial or non-commercial, and by any
means.

In jurisdictions that recognize copyright laws, the author or authors
of this software dedicate any and all copyright interest in the
software to the public domain. We make this dedication for the benefit
of the public at large and to the detriment of our heirs and
successors. We intend this dedication to be an overt act of
relinquishment in perpetuity of all present and future rights to this
software under copyright law.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND,
EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF
MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT.
IN NO EVENT SHALL THE AUTHORS BE LIABLE FOR ANY CLAIM, DAMAGES OR
OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE,
ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR
OTHER DEALINGS IN THE SOFTWARE.

For more information, please refer to <http://unlicense.org/>
