package com.example.tanks.enums

enum class Direction(val rotation: Float, ) {
    UP(0f),
    DOWN(180f),
    RIGHT(90f),
    LEFT(270f)
}