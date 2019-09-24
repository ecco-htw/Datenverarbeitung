package com.example.app.model.frontend_endpoints

/**{
  * "id" "blahblah"
  * "coordinates": {
  *   "longitude" 123
  *   "latitude" 124
  *   }
  * }
  * @param id
  * @param coordinates
  */
case class CoordinatesAndID(id: String, coordinates: Coordinates) {
  def getId: String = id
  def getCoordinates: Coordinates = coordinates
}
