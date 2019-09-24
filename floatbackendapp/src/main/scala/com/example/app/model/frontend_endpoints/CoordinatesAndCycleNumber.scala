package com.example.app.model.frontend_endpoints

/**
  * "coordinates": {
  *   "longitude": 123
  *   "latitude": 124
  *   "cycleNumber": 12
  * }
  *
  * @param longitude
  * @param latitude
  * @param cycleNumber
  */
case class CoordinatesAndCycleNumber(longitude: Double, latitude: Double, cycleNumber: Int) {
}
