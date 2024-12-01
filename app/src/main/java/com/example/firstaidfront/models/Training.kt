package com.example.firstaidfront.models

data class Training(
    var name: String = "",
    var iconResId: Int = 0, // Par défaut, la ressource sera 0
    var progress: Int = 0 // Le progrès sera initialisé à 0
)
