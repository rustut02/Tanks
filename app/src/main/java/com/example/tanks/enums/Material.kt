package com.example.tanks.enums

enum class Material(val tankCanGoTrough:Boolean) {
    EMPTY(true),
    BRICK(false),
    CONCRETE(false),
    GRASS(true)
}