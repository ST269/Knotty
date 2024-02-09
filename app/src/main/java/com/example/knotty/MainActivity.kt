package com.example.knotty

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.magnifier
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExpandLess
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.material3.Card
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarColors
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.knotty.model.Knot
import com.example.knotty.model.KnotCategory
import com.example.knotty.model.KnotsRespository.knots
import com.example.knotty.ui.theme.KnottyTheme


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            KnottyTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    KnottyApp()
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun KnottyApp(modifier: Modifier = Modifier) {
    Scaffold (
        topBar = {KnottyTopAppBar()}
    ) {
        it ->
        Box() {
            Image(
                painter = painterResource(R.drawable.knots_big_background),
                contentDescription = null,
                contentScale = ContentScale.FillBounds,
                modifier = Modifier.matchParentSize())
            LazyColumn(
                contentPadding = it,
                modifier = Modifier.padding(vertical = 8.dp)
            ) {
                items(knots) {
                    KnotItem(
                        knot = it,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp, horizontal = 16.dp)
                    )
                }
            }
        }
    }
}

@Composable
fun KnotItem(
    knot: Knot,
    modifier: Modifier = Modifier
) {
    var expanded by remember { mutableStateOf(false) }

    Card (modifier = modifier) {
        Column (
            modifier = Modifier.padding(8.dp),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Row (
                modifier = Modifier
                    .heightIn(min = 80.dp)
            ) {
                Column (
                    modifier = Modifier.weight(1f)
                ) {
                    Row (verticalAlignment = Alignment.CenterVertically) {
                        Text(
                            text = stringResource(knot.nameRes),
                            style = MaterialTheme.typography.displayMedium
                        )
                        Spacer(modifier = Modifier.weight(1f))
                        Text(
                            text = knot.category.toString(),
                            style = MaterialTheme.typography.bodyMedium
                            )
                    }
                    Text(
                        text = stringResource(knot.descriptionRes),
                        style = MaterialTheme.typography.bodyLarge,
                        modifier = Modifier.padding(top = 8.dp)
                    )
                }
                KnotItemButton(
                    expanded = expanded,
                    onClick = { expanded = !expanded },
                    modifier = Modifier.align(Alignment.CenterVertically)
                    )
            }
            if (expanded) {
                Box (modifier = Modifier.padding(8.dp)) {
                    Image(
                        painter = painterResource(knot.howToTieRes),
                        contentDescription = "How to tie the ${stringResource(knot.nameRes)}.",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(MaterialTheme.shapes.small)
                    )
                }
            }
        }

    }
}

@Composable
fun KnotItemButton(
    expanded: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    IconButton(onClick = onClick, modifier = modifier) {
        Icon(
            imageVector = if (expanded) Icons.Filled.ExpandLess else Icons.Filled.ExpandMore,
            contentDescription = stringResource(R.string.expand_more_content_description)
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun KnottyTopAppBar(modifier: Modifier = Modifier) {
    CenterAlignedTopAppBar(
        title = {
            Row (
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = stringResource(R.string.app_name),
                    style = MaterialTheme.typography.displayLarge
                )
            }
        },
        colors = TopAppBarDefaults.centerAlignedTopAppBarColors(containerColor = MaterialTheme.colorScheme.primary)
        )
}

@Preview
@Composable
fun KnotItemPreview() {

    KnotItem(knot = Knot(
        nameRes = R.string.knot_name_1,
        descriptionRes = R.string.knot_description_1,
        howToTieRes = R.drawable.how_to_tie_knot_1,
        category = KnotCategory.LineToHook
        ))
}

