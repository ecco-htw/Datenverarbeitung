package com.example.app.model

case class TimedFloat(longitude: Double, latitude: Double, platformNumber: String,
                      projectName: String, juld: Double, platformType: String, configMissionNumber: Int,
                      cycleNumber: Int, pres: Array[Double], temp: Array[Double], psal: Array[Double])  {
  def getLongitude: Double = longitude
  def getLatitude: Double = latitude
  def getPlatformNumber: String = platformNumber
  def getProjectName: String = projectName
  def getJuld: Double = juld
  def getPlatformType: String = platformType
  def getConfigMissionNumber: Int = configMissionNumber
  def getCycleNumber: Int = cycleNumber
  def getPressure: Array[Double] = pres
  def getTemperature: Array[Double] = temp
  def getPsal: Array[Double] = psal

}
