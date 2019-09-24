package com.example.app.model.frontend_endpoints

/**
  * "coordinates": {
  *   "longitude": 123
  *   "latitude": 124
  * }
  *
  * @param longitude
  * @param latitude
  */
case class Coordinates(longitude: Double, latitude: Double) {
  def getLongitude: Double = longitude
  def getLatitude: Double = latitude
}
