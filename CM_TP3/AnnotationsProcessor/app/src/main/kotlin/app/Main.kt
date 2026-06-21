package com.cmtp1.app
import com.cmtp1.generated.MyClassWrapper

fun main() {
    val myClass = MyClass()
    val wrappedMyClass = MyClassWrapper(myClass) // Use the wrapper class
    wrappedMyClass.sayHello()
    wrappedMyClass.compute()
}