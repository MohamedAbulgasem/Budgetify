package com.budgetify.app

import androidx.compose.animation.core.animateIntAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.setValue
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.budgetify.app.data.mockBudget
import com.budgetify.app.data.model.Budget
import com.budgetify.app.data.model.Category
import com.budgetify.app.ui.component.BudgetifyCard
import com.budgetify.app.ui.component.BudgetifyGradientCard
import com.budgetify.app.ui.component.BudgetifyPieChart
import com.budgetify.app.ui.component.BudgetifyText
import com.budgetify.app.ui.theme.BudgetifyTheme
import com.budgetify.app.util.toFormattedAmount
import kotlin.math.roundToInt
import kotlin.math.tan

@Composable
fun MainScreen(
    uiState: MainScreenUiState,
    modifier: Modifier = Modifier,
) {
    uiState.budget?.let { budget ->
        Column(
            modifier = modifier
                .background(MaterialTheme.colorScheme.background)
                .padding(16.dp)
        ) {
            Header(
                budget = budget,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(180.dp)
            )

            Summary(
                budget = budget,
                modifier = Modifier
                    .fillMaxWidth()
            )

            Description(
                budget = budget,
                modifier = Modifier
                    .fillMaxWidth()
            )
        }
    }
}

@Composable
private fun Header(
    budget: Budget,
    modifier: Modifier = Modifier,
) {
    BudgetifyGradientCard(modifier = modifier) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(40.dp)
        ) {
            BudgetifyText(
                text = "Total money in",
                fontSize = 14.sp,
                modifier = Modifier.align(Alignment.Start)
            )

            BudgetifyText(
                text = budget.totalAmount.toFormattedAmount(),
                fontSize = 42.sp,
                modifier = Modifier
                    .padding(16.dp)
                    .align(Alignment.CenterHorizontally)
            )
        }
    }

    Spacer(modifier = Modifier.height(20.dp))
}

@Composable
private fun ColumnScope.Summary(
    budget: Budget,
    modifier: Modifier = Modifier,
) {
    var selectedCategoryName by remember { mutableStateOf("") }

    Row(
        horizontalArrangement = Arrangement.SpaceAround,
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
    ) {
        budget.categories.forEach { category ->
            val isSelected = category.name == selectedCategoryName
            val width by animateIntAsState(if (isSelected) 112 else 110, label = "Card width")
            val height by animateIntAsState(if (isSelected) 82 else 80, label = "Card height")

            BudgetifyCard(
                colors = CardDefaults.cardColors().copy(containerColor = category.color),
                modifier = Modifier
                    .width(width.dp)
                    .height(height.dp)
            ) {
                CategorySummary(
                    category = category,
                    isSelected = isSelected,
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                )
            }
        }
    }

    Spacer(modifier = Modifier.height(20.dp))

    BudgetifyPieChart(
        categories = budget.categories,
        onCategorySliceClick = { categoryName ->
            selectedCategoryName = categoryName
        },
        chartPadding = 40,
        modifier = Modifier
            .align(Alignment.CenterHorizontally)
            .size(280.dp)
    )
}

@Composable
private fun Description(
    budget: Budget,
    modifier: Modifier = Modifier,
) {
    Column(modifier = modifier) {
        budget.categories.forEach { category ->
            CategorySection(
                category = category,
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}

@Composable
private fun CategorySummary(
    category: Category,
    isSelected: Boolean,
    modifier: Modifier = Modifier,
) {
    Column(modifier = modifier) {
        BudgetifyText(
            text = category.name,
            fontSize = if (isSelected) 13.sp else 12.sp,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 12.dp)
        )
        BudgetifyText(
            text = category.totalAmount.toFormattedAmount(),
            fontSize = if (isSelected) 17.sp else 16.sp,
            modifier = Modifier.fillMaxWidth()
        )
    }
}

@Composable
private fun CategorySection(
    category: Category,
    modifier: Modifier = Modifier,
) {
    Column(modifier = modifier) {
        Text(
            text = category.name,
            color = MaterialTheme.colorScheme.primary,
            fontSize = 34.sp,
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .height(40.dp)
        )

        /*category.items.forEach { budgetItem ->
            Text(
                text = budgetItem.title + " : " + budgetItem.amount.roundToInt(),
                color = Color.White,
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .height(40.dp)
                    .background(Color.Gray)
            )
        }*/

        LazyVerticalGrid(
            columns = GridCells.FixedSize(110.dp),
            horizontalArrangement = Arrangement.SpaceAround,
            //verticalAlignment = Alignment.CenterVertically,
            modifier = modifier
        ) {
            category.items.forEach { budgetItem ->
                item {
                    BudgetifyCard(
                        colors = CardDefaults.cardColors().copy(containerColor = category.color),
                        modifier = Modifier
                            .width(110.dp)
                            .height(80.dp)
                    ) {
                        Column(modifier = modifier) {
                            BudgetifyText(
                                text = budgetItem.title,
                                fontSize = 12.sp,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(top = 12.dp)
                            )
                            BudgetifyText(
                                text = budgetItem.amount.toFormattedAmount(),
                                fontSize = 16.sp,
                                modifier = Modifier.fillMaxWidth()
                            )
                        }
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun MainScreenPreview() {
    BudgetifyTheme {
        MainScreen(uiState = MainScreenUiState(mockBudget))
    }
}

val Category.color: Color
    @Composable get() = when (name) {
        "Investments" -> MaterialTheme.colorScheme.primary
        "Savings" -> MaterialTheme.colorScheme.secondary
        "Expenses" -> MaterialTheme.colorScheme.tertiary
        else -> MaterialTheme.colorScheme.primary
    }
