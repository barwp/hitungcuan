package com.example.data.model

data class Product(
    val id: String,
    val sku: String,
    val name: String,
    val category: String,
    val price: Double,
    val hpp: Double,
    val stock: Int,
    val unit: String = "cup",
    val cuanPercent: Double,
    val isLimited: Boolean = false,
    val imageUrl: String
) {
    val profit: Double get() = price - hpp
    val marginPercentage: Double get() = if (price > 0) ((price - hpp) / price) * 100 else 0.0
}

data class CartItem(
    val product: Product,
    val quantity: Int
)

data class RawMaterial(
    val id: String,
    val sku: String,
    val name: String,
    val category: String,
    val stockDisplay: String,
    val stockPercent: Float,
    val isLowStock: Boolean,
    val purchasePriceDisplay: String,
    val unitCostDisplay: String
)

data class TransactionRecord(
    val id: String,
    val time: String,
    val channel: String,
    val paymentMethod: String,
    val itemsSummary: List<String>,
    val grossAmount: Double,
    val hppAmount: Double,
    val feeCutAmount: Double = 0.0,
    val netProfit: Double,
    val marginPercent: Double,
    val status: String,
    val orderType: String
)

data class RecipeItem(
    val id: String,
    val name: String,
    val spec: String,
    val totalCost: Double,
    val costPerPortion: Double
)

data class MarketplaceChannel(
    val id: String,
    val name: String,
    val adminRate: Double,
    val fixedFee: Double = 1000.0
)

data class BusinessProfile(
    val brandName: String = "Kopi Cuan Nusantara",
    val branchName: String = "Cilandak Express",
    val category: String = "Minuman & Kafe",
    val scale: String = "Rumahan / Pemula",
    val monthlyRevenueTarget: String = "Rp 10 - 50 Jt"
)
