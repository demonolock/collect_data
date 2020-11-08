package com.kak666tus.collect_data.beans.autoru

data class CarInfo(
    var id: String,
    var engineDisplacement: String,
    var enginePower: String,
    var fuelType: String,
    var transmission: String,
    var bodyType: String,
    var driveUnit: String,
    var carColor: String,
    var carPrice: String,
    var productionDate: String,
    var mileage: String
) {
    override fun toString(): String {
        return "$id,$engineDisplacement,$enginePower,$fuelType,$transmission,$bodyType,$driveUnit,$carColor,$carPrice,$productionDate,$mileage"
    }
}
