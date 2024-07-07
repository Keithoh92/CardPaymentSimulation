package com.example.salestestapp.ui.shimmer.view.cardflip.model

enum class CardFace2(val angle: Float) {

    CardSignature(180f) {
        override val next: CardFace2
            get() = CardDetails
    },


    CardDetails(0f) {
        override val next: CardFace2
            get() = CardSignature
    };

    abstract val next: CardFace2
}