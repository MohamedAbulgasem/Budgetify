package com.budgetify.app.ui.component

import android.graphics.Typeface
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import co.yml.charts.common.model.PlotType
import co.yml.charts.ui.piechart.charts.DonutPieChart
import co.yml.charts.ui.piechart.models.PieChartConfig
import co.yml.charts.ui.piechart.models.PieChartData
import com.budgetify.app.color
import com.budgetify.app.data.model.Category

@Composable
fun BudgetifyText(
    text: String,
    fontSize: TextUnit,
    modifier: Modifier = Modifier,
    color: Color = Color.White,
) {
    Text(
        text = text,
        fontSize = fontSize,
        fontFamily = FontFamily.SansSerif,
        fontWeight = FontWeight.Medium,
        color = color,
        textAlign = TextAlign.Center,
        modifier = modifier
    )
}

@Composable
fun BudgetifyCard(
    modifier: Modifier = Modifier,
    colors: CardColors = CardDefaults.cardColors(),
    content: @Composable ColumnScope.() -> Unit,
) {
    Card(
        shape = RoundedCornerShape(24.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
        modifier = modifier
            .width(110.dp)
            .height(80.dp),
        colors = colors,
        content = content
    )
}

@Composable
fun BudgetifyGradientCard(
    modifier: Modifier = Modifier,
    colors: List<Color> = listOf(
        MaterialTheme.colorScheme.primary,
        MaterialTheme.colorScheme.secondary,
        MaterialTheme.colorScheme.tertiary,
    ),
    content: @Composable BoxScope.() -> Unit,
) {
    BudgetifyCard(modifier) {
        Box(
            content = content,
            modifier = Modifier
                .fillMaxSize()
                .background(brush = Brush.verticalGradient(colors = colors))
        )
    }
}

@Composable
fun BudgetifyPieChart(
    categories: List<Category>,
    onCategorySliceClick: (categoryName: String) -> Unit,
    chartPadding: Int,
    modifier: Modifier = Modifier,
) {
    val donutChartData = PieChartData(
        slices = categories.map { category ->
            PieChartData.Slice(
                label = category.name,
                value = category.totalAmount.toFloat(),
                color = category.color
            )
        },
        plotType = PlotType.Donut
    )

    val donutChartConfig = PieChartConfig(
        isAnimationEnable = true,
        backgroundColor = MaterialTheme.colorScheme.background,
        labelVisible = true,
        labelTypeface = Typeface.DEFAULT_BOLD,
        labelColorType = PieChartConfig.LabelColorType.SLICE_COLOR,
        labelFontSize = 32.sp,
        chartPadding = chartPadding,
        strokeWidth = 90.0f
    )

    DonutPieChart(
        modifier = modifier,
        donutChartData,
        donutChartConfig,
        onSliceClick = { slice ->
            onCategorySliceClick(slice.label)
        }
    )
}
