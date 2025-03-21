package ru.teamfc.fancust

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.scheduling.annotation.EnableScheduling

@SpringBootApplication
@EnableScheduling
class FancustApplication

fun main(args: Array<String>) {
    runApplication<FancustApplication>(*args)
}