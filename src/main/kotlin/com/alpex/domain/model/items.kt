package com.alpex.domain.model

import com.alpex.domain.validation.PositiveInt
import java.util.*

inline class ItemId(val id: UUID)
data class ItemPosition(val itemId: ItemId, val amount: PositiveInt)