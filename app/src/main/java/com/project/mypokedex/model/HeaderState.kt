package com.project.mypokedex.model

enum class HeaderState {
    ShowContent,
    HideContent;

    fun switchState(): HeaderState {
        return when(this) {
            ShowContent -> HideContent
            HideContent -> ShowContent
        }
    }
}