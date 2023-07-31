package com.project.mypokedex.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.selection.TextSelectionColors
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.project.mypokedex.R
import com.project.mypokedex.ui.theme.BlackTextColor
import com.project.mypokedex.ui.theme.MainBlack
import com.project.mypokedex.ui.theme.MainSelectionTextBackground
import com.project.mypokedex.ui.theme.PokemonGB
import com.project.mypokedex.ui.theme.SearchTextBackground

@Composable
fun SearchInputText(
    searchText: String,
    onSearchChange: (String) -> Unit
) {
    val shape = RoundedCornerShape(4.dp)
    OutlinedTextField(
        modifier = Modifier
            .padding(start = 20.dp, end = 20.dp, top = 10.dp)
            .fillMaxWidth()
            .background(SearchTextBackground, shape),
        value = searchText,
        onValueChange = {
            onSearchChange(it)
        },
        shape = shape,
        leadingIcon = {
            AppIcon(
                id = R.drawable.ic_search
            )
        },
        placeholder = {
            Text(
                text = stringResource(R.string.grid_screen_search),
                style = PokemonGB,
                fontSize = 12.sp,
                maxLines = 1
            )
        },
        colors = TextFieldDefaults.colors(
            focusedContainerColor = SearchTextBackground,
            unfocusedContainerColor = SearchTextBackground,
            disabledContainerColor = SearchTextBackground,
            errorContainerColor = SearchTextBackground,
            focusedTextColor = BlackTextColor,
            disabledTextColor = BlackTextColor,
            errorTextColor = BlackTextColor,
            unfocusedTextColor = BlackTextColor,
            focusedIndicatorColor = MainBlack,
            focusedLeadingIconColor = MainBlack,
            cursorColor = MainBlack,
            selectionColors = TextSelectionColors(MainBlack, MainSelectionTextBackground)
        ),
        textStyle = PokemonGB.copy(fontSize = 12.sp),
        singleLine = true,
        keyboardOptions = KeyboardOptions(
            autoCorrect = false,
            keyboardType = KeyboardType.Password
        )
    )
}