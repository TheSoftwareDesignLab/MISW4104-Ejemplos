package com.habitrpg.android.habitica.models

class PurchaseValidationRequest {
    var sku: String? = null
    var transaction: Transaction? = null
    var gift: IAPGift? = null
}
