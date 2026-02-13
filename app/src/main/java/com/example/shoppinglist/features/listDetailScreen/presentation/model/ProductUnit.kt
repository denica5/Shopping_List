package com.example.shoppinglist.features.listDetailScreen.presentation.model

enum class ProductUnit(val label: String, val step: Double) {
    LITERS("л", 1.0),
    MILLILITERS("мл", 100.0),
    PACKAGES("уп", 1.0),
    PACKS("пач", 1.0),
    PIECES("шт", 1.0),
    KILOGRAMS("кг", 1.0),
    GRAMS("г", 100.0);
}