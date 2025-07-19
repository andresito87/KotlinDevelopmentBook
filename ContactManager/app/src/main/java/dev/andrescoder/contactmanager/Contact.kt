package dev.andrescoder.contactmanager

interface Callable {
    fun call()
}

open class Person(val name: String?) {
    open fun display(): String = name ?: "Unknown"
}

class Contact(name: String?, val phone: String?) : Person(name), Callable {
    override fun display(): String = "${super.display()}- $phone"
    override fun call() {
        println("Calling $phone")
    }
}
