package com.example.salestestapp.ui.shimmer.cardflip.model

enum class CardFace(val angle: Float) {

    Loading(0f) {
        override val next: CardFace
            get() = Front

        override fun getNext(isChipAndSignature: Boolean): CardFace {
            return if (isChipAndSignature) {
                Signature
            } else {
                Front
            }
        }
    },

    Front(180f) {
        override val next: CardFace
            get() = Signature

        override fun getNext(isChipAndSignature: Boolean): CardFace {
            return Signature
        }
    },

    Signature(180f) {
        override val next: CardFace
            get() = Front

        override fun getNext(isChipAndSignature: Boolean): CardFace {
            return Front2
        }
    },

    Front2(360f) {
        override val next: CardFace
            get() = Front

        override fun getNext(isChipAndSignature: Boolean): CardFace {
            return Signature
        }
    };

    abstract fun getNext(isChipAndSignature: Boolean): CardFace

    abstract val next: CardFace
}