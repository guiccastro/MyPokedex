package com.project.mypokedex.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.selection.TextSelectionColors
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.project.mypokedex.R
import com.project.mypokedex.ui.theme.BlackTextColor
import com.project.mypokedex.ui.theme.MainBlack
import com.project.mypokedex.ui.theme.MainBlue
import com.project.mypokedex.ui.theme.MainSelectionTextBackground
import com.project.mypokedex.ui.theme.MyPokedexTheme
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

@Composable
fun CustomTextField(
    text: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    placeholderText: String = "Placeholder",
    textStyle: TextStyle = PokemonGB,
    textSize: TextUnit = 10.sp,
    textColor: Color = MainBlack,
    leadingIcon: (@Composable () -> Unit)? = null,
    trailingIcon: (@Composable () -> Unit)? = null,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    shape: Shape = RoundedCornerShape(4.dp),
    borderWidth: Dp = 1.dp,
    borderColor: Color = MainBlack,
    backgroundColor: Color = MainBlue

) {
    BasicTextField(
        value = text,
        onValueChange = {
            onValueChange(it)
        },
        textStyle = textStyle.copy(fontSize = textSize, color = textColor),
        singleLine = true,
        modifier = modifier
            .border(borderWidth, borderColor, shape)
            .background(backgroundColor, shape),
        keyboardOptions = keyboardOptions,
        decorationBox = { innerTextField ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 6.dp, horizontal = 8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                if (leadingIcon != null) leadingIcon()
                Box(Modifier.weight(1f)) {
                    if (text.isEmpty()) Text(
                        placeholderText,
                        style = textStyle.copy(
                            fontSize = textSize,
                            color = textColor.copy(alpha = 0.8F)
                        )
                    )
                    innerTextField()
                }
                if (trailingIcon != null) trailingIcon()
            }
        }
    )
}

@Preview
@Composable
fun InputHeightDialogPreview() {
    MyPokedexTheme {
        Surface {
            CustomTextField(
                text = "",
                onValueChange = {},
                placeholderText = "Placeholder"
            )
        }
    }
}