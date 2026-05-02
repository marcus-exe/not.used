package br.com.graest.project

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform