package com.example.data.repository

import com.example.data.model.BusinessProfile
import com.example.data.model.CartItem
import com.example.data.model.MarketplaceChannel
import com.example.data.model.Product
import com.example.data.model.RawMaterial
import com.example.data.model.RecipeItem
import com.example.data.model.TransactionRecord
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

object HitungCuanRepository {

    val initialProducts = listOf(
        Product(
            id = "1",
            sku = "KSA-01",
            name = "Kopi Susu Aren",
            category = "Kopi & Espresso",
            price = 18000.0,
            hpp = 9850.0,
            stock = 42,
            unit = "cup",
            cuanPercent = 54.0,
            isLimited = false,
            imageUrl = "https://lh3.googleusercontent.com/aida-public/AB6AXuCJCV6ZCGMYTBbSP49UULtyoaO7B3vieI0nN6el3UsbfN2_Vpf46wqC-_TSkhboD5cMUOXgD_3G9wGlNXC96H3zXU9Q86upNHEQlb8sjzvh1ssjKqJ2Rf7asxmy56gTWBExQC3V-5WF-imjitbR3PcMhfj7dSuPY3jNHVHVkudevsACHj0E_VNRcM2ZDYKcebaoTiRiDu1WoiHhMrQYjo3wRNRsGs0cbJwnhBbs9eImR9vsSAqcoxAI"
        ),
        Product(
            id = "2",
            sku = "CBA-05",
            name = "Croissant Butter",
            category = "Pastry & Bakery",
            price = 24000.0,
            hpp = 12480.0,
            stock = 15,
            unit = "pcs",
            cuanPercent = 48.0,
            isLimited = false,
            imageUrl = "https://lh3.googleusercontent.com/aida-public/AB6AXuCP2yPXM_nZJJxvcM95skln3oEkWWO4AYFnWPzHGcyAauXPU1zMFgdGrQXtFSgOMnIVfmmEkCkk42-yGAnCPW7VaLNvCr-NSNdTmYBZQKd7plgQ38T9WaP7AeCUjoORlg4nuA0FGJj5lgaFcUHuUZ3XQwgFgcz3EU5JAzsAX-AfEnhWBq_V9hEP3i4dCY6439zcOP__PP4h2ONBlux_MCkVoOp9S-DteUtz6tR8Tw4eud9lOuXFILk5"
        ),
        Product(
            id = "3",
            sku = "MLO-02",
            name = "Matcha Oatmilk",
            category = "Non-Kopi",
            price = 22000.0,
            hpp = 10560.0,
            stock = 8,
            unit = "cup",
            cuanPercent = 52.0,
            isLimited = true,
            imageUrl = "https://lh3.googleusercontent.com/aida-public/AB6AXuDr80fmvBdc-XjvDk6NV6n-LDy-euTb4y5gHP_zL0rMykX0XgeCCc1LTOJXRCCHqqSlM_fCcbzmEzud6JLrphkuN5vCEz2yi19vb2Xp5jQSfP3nGeg8IWwVxuuo4jUs4P7zKNIe3qnffnjY4H9qwof0D48Ufsju6Nm4tXYpitq6UOV6EU7TIU8lg8ZhLXkJl6V-WTPFn1g7Z1wNJ5kSll_hzYDnuDSZt8b7nUaOZUvuEBVXTViJlJJd"
        ),
        Product(
            id = "4",
            sku = "CBN-03",
            name = "Cold Brew Nut",
            category = "Kopi & Espresso",
            price = 26000.0,
            hpp = 10140.0,
            stock = 24,
            unit = "cup",
            cuanPercent = 61.0,
            isLimited = false,
            imageUrl = "https://lh3.googleusercontent.com/aida-public/AB6AXuAiRUd6Ft44dO4x_MbKwZ7qljgiuw4t7LcZ_-4z1C-qpr-TCKEz-fURzkZcFHq4nZG3Q2t6cWDxD9GR7GOa4UjFHbtaEglzGmR8GmBn6YpcA9AsCuh32TXVa88X9bTIKNjrRHuI-z__YlFoVKPT9xSmF6VCyQ99iMFCvCWtAwohtQS9YtjH9Ay1TeTUezE-oLILgL-OBwC0yhq1k1Fbd4Yy5zSKR0YqwEgS26tPyTTXTEN7GfilMccF"
        ),
        Product(
            id = "5",
            sku = "RVR-04",
            name = "Red Velvet Roll",
            category = "Pastry & Bakery",
            price = 20000.0,
            hpp = 10200.0,
            stock = 19,
            unit = "slice",
            cuanPercent = 49.0,
            isLimited = false,
            imageUrl = "https://lh3.googleusercontent.com/aida-public/AB6AXuBPmrALYkK7o3-VWcefvB5PdoD50-Y_ac1rFxV-AOdnyomkwFuoWYvERmIBRXh2PstIxakiI0yXQND-Cpwst-aTe5ATaz26WgOR3jdJyHXf9cid7-BL0LFjgXS0ejYfwFBGYAkaIUtDnvONDObbDVcgHWhyGkK8PxXPoFphTv4dHohiCkvXgL0JPzVEvNA6uZxd6aYzzFtvZq-s4joBrpVOAaTzxh0Xc46y9GYo4atQbqa9nNXuB_oK"
        ),
        Product(
            id = "6",
            sku = "EGT-06",
            name = "Earl Grey Tea",
            category = "Non-Kopi",
            price = 21000.0,
            hpp = 9030.0,
            stock = 31,
            unit = "cup",
            cuanPercent = 57.0,
            isLimited = false,
            imageUrl = "https://lh3.googleusercontent.com/aida-public/AB6AXuCJYNFlu5_00-fETMsLQcmmypuMdyb1_4JCSVIytWIszk7pzE4l2gmUIMylqGIQ7Nky3AqPmLMX-M4I37BzYPT2x9Lyj4qqwpf_UcIgk565b7JFqFfsl-6VzePH4kwPlgMbcM529VobhUrx3Zmd3kuBnDxiSNLeQdE9yfvKTBR309thBVIu20lDc95fjZA85Ro5XH3nuElvSHpaF3nRg5kF31S3fRrZjCXXisV-_w0a0uOf28_Nmouk"
        )
    )

    private val _products = MutableStateFlow(initialProducts)
    val products: StateFlow<List<Product>> = _products.asStateFlow()

    // Cart State - pre-populated with 3 items (2x Kopi Susu Aren, 1x Croissant Butter, etc.) totaling Rp 82.000
    private val _cart = MutableStateFlow(
        listOf(
            CartItem(initialProducts[0], 2), // 2x Kopi Susu Aren = 36.000 or custom price
            CartItem(initialProducts[1], 1), // 1x Croissant Butter = 24.000
            CartItem(initialProducts[2], 1)  // 1x Matcha Oatmilk = 22.000 -> Total = 36k + 24k + 22k = 82.000!
        )
    )
    val cart: StateFlow<List<CartItem>> = _cart.asStateFlow()

    // Raw Materials
    private val _rawMaterials = MutableStateFlow(
        listOf(
            RawMaterial(
                id = "bb-1",
                sku = "BB-S-01",
                name = "Susu UHT Full Cream 1L",
                category = "Bahan Baku",
                stockDisplay = "Sisa 1.2 Liter",
                stockPercent = 0.12f,
                isLowStock = true,
                purchasePriceDisplay = "Rp 18.500 / kotak",
                unitCostDisplay = "Biaya: Rp 18,5 / ml"
            ),
            RawMaterial(
                id = "bb-2",
                sku = "BB-C-14",
                name = "Cup PET Dingin 14oz",
                category = "Kemasan",
                stockDisplay = "Sisa 28 pcs",
                stockPercent = 0.25f,
                isLowStock = true,
                purchasePriceDisplay = "Rp 35.000 / roll (50 pcs)",
                unitCostDisplay = "Biaya: Rp 700 / pcs"
            ),
            RawMaterial(
                id = "bb-3",
                sku = "BB-K-03",
                name = "Biji Kopi House Blend Arabica-Robusta",
                category = "Bahan Baku",
                stockDisplay = "Sisa 6.4 Kg (Aman)",
                stockPercent = 0.78f,
                isLowStock = false,
                purchasePriceDisplay = "Rp 160.000 / kg",
                unitCostDisplay = "Biaya: Rp 160 / gr"
            ),
            RawMaterial(
                id = "bb-4",
                sku = "BB-G-05",
                name = "Gula Aren Cair Organik Premium",
                category = "Bahan Baku",
                stockDisplay = "Sisa 3.5 Liter",
                stockPercent = 0.65f,
                isLowStock = false,
                purchasePriceDisplay = "Rp 45.000 / liter",
                unitCostDisplay = "Biaya: Rp 45 / ml"
            )
        )
    )
    val rawMaterials: StateFlow<List<RawMaterial>> = _rawMaterials.asStateFlow()

    // Transactions History
    private val _transactions = MutableStateFlow(
        listOf(
            TransactionRecord(
                id = "#ORD-0842",
                time = "15:42 WIB",
                channel = "Kasir Offline",
                paymentMethod = "QRIS",
                itemsSummary = listOf("2x Kopi Susu Aren", "1x Croissant Butter"),
                grossAmount = 82000.0,
                hppAmount = 38700.0,
                feeCutAmount = 0.0,
                netProfit = 43300.0,
                marginPercent = 52.8,
                status = "Selesai • Potong Stok Aman",
                orderType = "Dine In"
            ),
            TransactionRecord(
                id = "#ORD-0841",
                time = "15:15 WIB",
                channel = "GoFood",
                paymentMethod = "% Fee App 20%",
                itemsSummary = listOf("1x Cold Brew Hazelnut", "1x Matcha Latte Oat"),
                grossAmount = 58000.0,
                hppAmount = 26600.0,
                feeCutAmount = 11600.0,
                netProfit = 19800.0,
                marginPercent = 42.7,
                status = "Driver On-The-Way (Take Away)",
                orderType = "Auto Payout"
            ),
            TransactionRecord(
                id = "#ORD-0840",
                time = "14:48 WIB",
                channel = "Kasir Offline",
                paymentMethod = "Tunai (Cash)",
                itemsSummary = listOf("1x Earl Grey Milk Tea"),
                grossAmount = 21000.0,
                hppAmount = 9030.0,
                feeCutAmount = 0.0,
                netProfit = 11970.0,
                marginPercent = 57.0,
                status = "Uang Diterima di Laci",
                orderType = "Take Away"
            ),
            TransactionRecord(
                id = "#ORD-0839",
                time = "13:55 WIB",
                channel = "ShopeeFood",
                paymentMethod = "App Promo 15%",
                itemsSummary = listOf("2x Aren Spesial 250ml"),
                grossAmount = 36000.0,
                hppAmount = 19952.0,
                feeCutAmount = 0.0,
                netProfit = 16048.0,
                marginPercent = 44.6,
                status = "Selesai • Terverifikasi",
                orderType = "ShopeePay"
            )
        )
    )
    val transactions: StateFlow<List<TransactionRecord>> = _transactions.asStateFlow()

    // Business Profile
    private val _businessProfile = MutableStateFlow(BusinessProfile())
    val businessProfile: StateFlow<BusinessProfile> = _businessProfile.asStateFlow()

    // Recipe Items
    val rawIngredients = listOf(
        RecipeItem("ri-1", "Biji Kopi Espresso Blend", "200 gr × Rp 180/gr", 36000.0, 3600.0),
        RecipeItem("ri-2", "Fresh Milk Pasteurisasi", "1.500 ml × Rp 20/ml", 30000.0, 3000.0),
        RecipeItem("ri-3", "Gula Aren Cair Organik", "300 ml × Rp 25/ml", 7500.0, 750.0)
    )

    val packagingIngredients = listOf(
        RecipeItem("pk-1", "Botol Almond 250ml + Tutup Segel", "10 pcs × Rp 1.200", 12000.0, 1200.0),
        RecipeItem("pk-2", "Stiker Vinyl Label Logo Anti Air", "10 pcs × Rp 350", 3500.0, 350.0)
    )

    val overheadItems = listOf(
        RecipeItem("ov-1", "Upah Barista / Peracik", "Perhitungan alokasi per botol", 5000.0, 500.0),
        RecipeItem("ov-2", "Listrik, Gas & Sanitasi", "Alokasi utilities batch", 2000.0, 200.0)
    )

    // Marketplace Channels
    val marketplaceChannels = listOf(
        MarketplaceChannel("shopee", "Shopee Super", 0.065, 1000.0),
        MarketplaceChannel("tokopedia", "Tokopedia PM", 0.060, 1000.0),
        MarketplaceChannel("tiktok", "TikTok Shop", 0.075, 1000.0),
        MarketplaceChannel("gofood", "Grab/GoFood", 0.200, 1000.0)
    )

    fun addToCart(product: Product) {
        _cart.update { current ->
            val index = current.indexOfFirst { it.product.id == product.id }
            if (index >= 0) {
                current.mapIndexed { i, item ->
                    if (i == index) item.copy(quantity = item.quantity + 1) else item
                }
            } else {
                current + CartItem(product, 1)
            }
        }
    }

    fun removeFromCart(productId: String) {
        _cart.update { current ->
            val index = current.indexOfFirst { it.product.id == productId }
            if (index >= 0) {
                val item = current[index]
                if (item.quantity > 1) {
                    current.mapIndexed { i, itm ->
                        if (i == index) itm.copy(quantity = itm.quantity - 1) else itm
                    }
                } else {
                    current.filterNot { it.product.id == productId }
                }
            } else current
        }
    }

    fun clearCart() {
        _cart.value = emptyList()
    }

    fun completeCheckout(paymentMethod: String, amountReceived: Double) {
        val currentCart = _cart.value
        if (currentCart.isEmpty()) return

        val totalGross = currentCart.sumOf { it.product.price * it.quantity }
        val totalHpp = currentCart.sumOf { it.product.hpp * it.quantity }
        val netProfit = totalGross - totalHpp
        val margin = if (totalGross > 0) (netProfit / totalGross) * 100 else 0.0

        val newRecord = TransactionRecord(
            id = "#ORD-084${_transactions.value.size + 3}",
            time = "Sekarang",
            channel = "Kasir Offline",
            paymentMethod = paymentMethod,
            itemsSummary = currentCart.map { "${it.quantity}x ${it.product.name}" },
            grossAmount = totalGross,
            hppAmount = totalHpp,
            feeCutAmount = 0.0,
            netProfit = netProfit,
            marginPercent = margin,
            status = "Selesai • Potong Stok Aman",
            orderType = "Dine In"
        )

        _transactions.update { listOf(newRecord) + it }
        clearCart()
    }

    fun addManualTransaction(
        productName: String,
        amount: Double,
        channel: String,
        paymentMethod: String
    ) {
        val estimatedHpp = amount * 0.48
        val profit = amount - estimatedHpp
        val margin = 52.0

        val newRecord = TransactionRecord(
            id = "#ORD-084${_transactions.value.size + 4}",
            time = "Manual",
            channel = channel,
            paymentMethod = paymentMethod,
            itemsSummary = listOf("1x $productName"),
            grossAmount = amount,
            hppAmount = estimatedHpp,
            feeCutAmount = 0.0,
            netProfit = profit,
            marginPercent = margin,
            status = "Selesai • Manual Entry",
            orderType = "Take Away"
        )
        _transactions.update { listOf(newRecord) + it }
    }

    fun updateProfile(brandName: String, category: String, scale: String, revenue: String) {
        _businessProfile.update {
            it.copy(
                brandName = brandName,
                category = category,
                scale = scale,
                monthlyRevenueTarget = revenue
            )
        }
    }
}
