package com.budgetify.app.ui.screen.display

import androidx.compose.animation.core.animateIntAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
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
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.budgetify.app.data.model.Budget
import com.budgetify.app.data.model.Category
import com.budgetify.app.ui.component.BudgetifyCard
import com.budgetify.app.ui.component.BudgetifyPieChart
import com.budgetify.app.ui.component.BudgetifyText
import com.budgetify.app.ui.component.GradientCard
import com.budgetify.app.util.toFormattedAmount
import com.budgetify.app.util.toFormattedPercentage

@Composable
fun DisplayScreen(
    budget: Budget,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .verticalScroll(rememberScrollState())
            .padding(16.dp)
    ) {
        Header(
            totalAmount = budget.totalAmount,
            modifier = Modifier
                .fillMaxWidth()
                .height(180.dp)
        )

        Summary(
            categories = budget.categories,
            modifier = Modifier
                .fillMaxWidth()
                .height(320.dp)
        )

        CategorySections(
            categories = budget.categories,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 40.dp)
                .height(800.dp)
        )
    }
}

@Composable
private fun Header(
    totalAmount: Double,
    modifier: Modifier = Modifier,
) {
    GradientCard(modifier = modifier) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(40.dp)
        ) {
            BudgetifyText(
                text = "Total money in",
                fontSize = 22.sp,
                modifier = Modifier.align(Alignment.Start)
            )

            BudgetifyText(
                text = totalAmount.toFormattedAmount(),
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
private fun Summary(
    categories: List<Category>,
    modifier: Modifier = Modifier,
) {
    var selectedCategoryName by remember { mutableStateOf("") }

    Column(modifier = modifier) {
        Row(
            horizontalArrangement = Arrangement.SpaceAround,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            categories.forEach { category ->
                val isSelected = category.name == selectedCategoryName
                val width by animateIntAsState(if (isSelected) 112 else 110, label = "Card width")
                val height by animateIntAsState(if (isSelected) 82 else 80, label = "Card height")

                CategorySummaryCard(
                    category = category,
                    isSelected = isSelected,
                    modifier = Modifier
                        .size(width = width.dp, height = height.dp)
                )
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        BudgetifyPieChart(
            categories = categories,
            onCategorySliceClick = { categoryName ->
                selectedCategoryName = categoryName
            },
            chartPadding = 40,
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .size(280.dp)
        )
    }
}

@Composable
private fun CategorySections(
    categories: List<Category>,
    modifier: Modifier = Modifier,
) {
    Column(modifier = modifier) {
        categories.forEach { category ->
            CategorySection(
                category = category,
                percentage = (category.totalAmount / categories.sumOf { it.totalAmount }).toFloat(),
                modifier = Modifier
                    .fillMaxWidth()
            )
        }
    }
}

@Composable
private fun CategorySummaryCard(
    category: Category,
    isSelected: Boolean,
    modifier: Modifier = Modifier,
) {
    BudgetifyCard(
        colors = CardDefaults.cardColors().copy(containerColor = category.color),
        modifier = modifier
    ) {
        Column(modifier = Modifier.align(Alignment.CenterHorizontally)) {
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
}

@Composable
private fun CategorySection(
    category: Category,
    percentage: Float,
    modifier: Modifier = Modifier,
) {
    Column(modifier = modifier) {
        Text(
            text = category.name,
            color = category.color,
            fontSize = 34.sp,
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
        )

        Text(
            text = category.totalAmount.toFormattedAmount() + " (${percentage.toFormattedPercentage()})",
            color = category.color.copy(alpha = 0.6f),
            fontSize = 28.sp,
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(vertical = 6.dp)
        )

        Spacer(modifier = Modifier.height(20.dp))

        LazyVerticalGrid(
            columns = GridCells.FixedSize(110.dp),
            horizontalArrangement = Arrangement.SpaceAround,
        ) {
            category.items.forEach { budgetItem ->
                item {
                    BudgetifyCard(
                        colors = CardDefaults.cardColors().copy(containerColor = category.color),
                        modifier = Modifier
                            .width(110.dp)
                            .height(100.dp)
                            .padding(bottom = 20.dp)
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

        Spacer(modifier = Modifier.height(20.dp))
    }
}
