package com.panasetskaia.core.data

import com.panasetskaia.core.data.models.BestSellerDtoModel
import com.panasetskaia.core.data.models.HotSaleDtoModel
import com.panasetskaia.core.data.models.PhoneDbModel
import com.panasetskaia.core.data.models.PhoneDtoModel
import com.panasetskaia.core.domain.entities.BestSeller
import com.panasetskaia.core.domain.entities.HotSale
import com.panasetskaia.core.domain.entities.Phone

class PhoneMapper {

    fun mapBestSellerDataModelToEntity(bestSellerDtoModel: BestSellerDtoModel): BestSeller {
        return BestSeller(
            bestSellerDtoModel.id,
            bestSellerDtoModel.isFav,
            bestSellerDtoModel.title,
            bestSellerDtoModel.picUrl,
            bestSellerDtoModel.noDiscountPrice,
            bestSellerDtoModel.discountPrice
        )
    }

    fun mapHotSaleDataModelToEntity(hotSaleDtoModel: HotSaleDtoModel): HotSale {
        return HotSale(
            hotSaleDtoModel.id,
            hotSaleDtoModel.isNew,
            hotSaleDtoModel.title,
            hotSaleDtoModel.subtitle,
            hotSaleDtoModel.picUrl,
            hotSaleDtoModel.isBuy
        )
    }

    fun mapPhoneDtoModelToEntity(phoneDtoModel: PhoneDtoModel): Phone {
        val id = phoneDtoModel.id?.toInt() ?: 0
        return Phone(
            id,
            phoneDtoModel.isFavorite,
            phoneDtoModel.CPU,
            phoneDtoModel.camera,
            phoneDtoModel.capacities,
            phoneDtoModel.colors,
            phoneDtoModel.images,
            phoneDtoModel.price,
            phoneDtoModel.rating,
            phoneDtoModel.sd,
            phoneDtoModel.ssd,
            phoneDtoModel.title
        )
    }

    fun mapPhoneToDbModel(phone: Phone): PhoneDbModel {
        return PhoneDbModel(
            phone.id,
            phone.isFavorite,
            phone.CPU,
            phone.camera,
            phone.capacities,
            phone.colors,
            phone.images,
            phone.price,
            phone.rating,
            phone.sd,
            phone.ssd,
            phone.title,
            phone.quantity
        )
    }

    fun mapDbModelToPhone(phone: PhoneDbModel): Phone {
        return Phone(
            phone.id,
            phone.isFavorite,
            phone.CPU,
            phone.camera,
            phone.capacities,
            phone.colors,
            phone.images,
            phone.price,
            phone.rating,
            phone.sd,
            phone.ssd,
            phone.title,
            phone.quantity
        )
    }
}