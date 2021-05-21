package com.example.cameraandgallerytask1.models

import android.net.Uri

class Sign {
    var id: Int? = null
    var name: String? = null
    var content: String? = null
    var type: Int? = null
    var imagePath: String? = null
    var like: Int? = null


    constructor()



    constructor(
        id: Int?,
        name: String?,
        content: String?,
        type: Int?,
        imagePath: String?,
        like: Int?
    ) {
        this.id = id
        this.name = name
        this.content = content
        this.type = type
        this.imagePath = imagePath
        this.like = like
    }

    constructor(name: String?, content: String?, type: Int?, imagePath: String?, like: Int?) {
        this.name = name
        this.content = content
        this.type = type
        this.imagePath = imagePath
        this.like = like
    }

}