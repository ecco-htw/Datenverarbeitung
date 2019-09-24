package com.example.app.model.frontend_endpoints

// Maps this:
/**
  * Maps this: ITS ONLY FOR ONE BUOY ! The buoy gets identified by the id that the frontend already received in endpoint1
  *{
  *  "data": {
  *   {
  *     "saltinessValues": [...],
  *     "pressureValues": [...],
  *     "tempValues": [...],
  *     "path": [
  *       {
  *       "longitude": 123,
  *       "latitude: 124,
  *       }
  *       {
  *         "longitude": 125,
  *         "latitude: 126
  *       }
  *     ]
  *   }
  *   }
  *}
   */
case class Ep2DataJsonWrapper(data: PathAndLastMeasurements) {
  def getData: PathAndLastMeasurements = data
}
