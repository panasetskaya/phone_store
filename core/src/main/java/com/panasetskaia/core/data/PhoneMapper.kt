package com.panasetskaia.core.data

import com.panasetskaia.core.data.models.BestSellerDataModel
import com.panasetskaia.core.data.models.HotSaleDataModel
import com.panasetskaia.core.data.models.PhoneDbModel
import com.panasetskaia.core.data.models.PhoneDtoModel
import com.panasetskaia.core.domain.entities.BestSeller
import com.panasetskaia.core.domain.entities.HotSale
import com.panasetskaia.core.domain.entities.Phone

class PhoneMapper {

    fun mapBestSellerDataModelToEntity(bestSellerDataModel: BestSellerDataModel): BestSeller {
        return BestSeller(
            bestSellerDataModel.id,
            bestSellerDataModel.isNew,
            bestSellerDataModel.title,
            bestSellerDataModel.picUrl,
            bestSellerDataModel.noDiscountPrice,
            bestSellerDataModel.discountPrice
        )
    }

    fun mapHotSaleDataModelToEntity(hotSaleDataModel: HotSaleDataModel): HotSale {
        return HotSale(
            hotSaleDataModel.id,
            hotSaleDataModel.isNew,
            hotSaleDataModel.title,
            hotSaleDataModel.subtitle,
            hotSaleDataModel.picUrl,
            hotSaleDataModel.isBuy
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