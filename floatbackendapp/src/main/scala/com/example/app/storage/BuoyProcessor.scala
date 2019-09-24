package com.example.app.storage

import com.example.app.model.Buoy
import com.example.app.model.frontend_endpoints._
import com.example.app.storage.MongoPipeline._
import com.mongodb.spark.MongoSpark
import com.mongodb.spark.rdd.MongoRDD
import org.apache.spark.sql._
import org.bson.Document

class BuoyProcessor {

  private val mongoHost: String = sys.env.getOrElse("MONGO_HOST", throw new IllegalStateException("The environment variable MONGO_HOST is not set."))
  private val mongoPort: String = sys.env.getOrElse("MONGO_PORT", throw new IllegalStateException("The environment variable MONGO_PORT is not set."))
  private val mongoUser: String = sys.env.getOrElse("MONGO_USER", throw new IllegalStateException("The environment variable MONGO_USER is not set."))
  private val mongoPassword: String = sys.env.getOrElse("MONGO_PASSWORD", throw new IllegalStateException("The environment variable MONGO_PASSWORD is not set."))
  private val mongoDBName: String = sys.env.getOrElse("MONGO_DB_NAME", throw new IllegalStateException("The environment variable MONGO_DB_NAME is not set."))
  private val dateURI = s"mongodb://$mongoUser:$mongoPassword@$mongoHost:$mongoPort/$mongoDBName"

  /**
    * This object connects to the database and initializes itself with the configurations specified
    * spark.mongodb.input.uri means that we can write to the database
    * spark.mongodb.output.uri means that we can read from the database
    */
  val sparkSession: SparkSession = SparkSession.builder().master("local[*]")
    .appName("BuoyREST_Interface")
    .config("spark.mongodb.input.uri", dateURI)
    .config("spark.mongodb.output.uri", dateURI)
    .config("spark.ui.port", "4444")
    .getOrCreate()

  /**
    * This import statement is needed to convert the data coming from mongodb to our case class, which will ensure a more
    * readable and robust code structure
    */

  import MongoPipeline.implicits._

  /**
    * The dataset containing the buoyserialnumber as a key and all buoys mapped to that key as value
    */
  val source: MongoRDD[Document] = MongoSpark.load(sparkSession.sparkContext)

  private def pipeline(docs: Seq[String]): MongoRDD[Document] = {
    source.withPipeline(docs.map(Document.parse))
  }

  /**
    * This method wraps the coordinates and ids for endpoint 1 inside an object, which contains the array "data".
    * So processCoordinatesAndIDsEP1 returns the data array, and this method returns an object containing the data array
    *
    * @return the object containing the data array
    */
  def retrieveCoordinatesAndIDs: Ep1DataJsonWrapper = {
    val result = MongoPipeline()
      .Group(
        "_id" -> "$floatSerialNo",
        "id" -> MDoc("$last" -> "$floatSerialNo"),
        "coordinates" -> MDoc(
          "$last" -> MDoc("longitude" -> "$longitude", "latitude" -> "$latitude"))
      )
      .run(source)
      .toDS[CoordinatesAndID].collect()
    Ep1DataJsonWrapper(result)
  }

  def retrieveMeasurements(buoyId: String, cycleNum: String): Ep3DataJsonWrapper = {
    val result = MongoPipeline()
      .Match(and("floatSerialNo" -> buoyId, "cycleNumber" -> cycleNum.toInt))
      .Project("pressureValues" -> "$PRES", "saltinessValues" -> "$PSAL", "temperatureValues" -> "$TEMP")
      .run(source).toDS[Measurements].collect()(0)
    Ep3DataJsonWrapper(result)
  }

  /**
    * Here we save the coordinates for the specified buoy id AND we store the measurements of the buoy with the specified
    * buoy id, by filtering the buoys in the database and finding the ones that match the given id. Then we take all the
    * measurement arrays mapped to that buoy and we save them together with the coordinates inside the object.
    * Then we wrap the object inside the Ep2DataJsonWrapper, which is another object, because thats how the frontend
    * wanted to receive the data
    *
    * @param buoyId the buoy id
    * @return all coordinates mapped to the specified buoy id and all the measurements too
    */
  def retrievePathAndLastMeasurements(buoyId: String): Ep2DataJsonWrapper = {

    // with MongoPipeline wrapper

    val buoysPipeline = MongoPipeline()
      .Match("floatSerialNo" -> buoyId)
      .Sort("juld" -> -1)

    val measurements = buoysPipeline
      .Limit(1)
      .run(source)
      .toDS[Buoy].first()

    val coordinates = buoysPipeline
      .Project("longitude" -> 1, "latitude" -> 1, "cycleNumber" -> 1)
      .run(source)
      .toDS[CoordinatesAndCycleNumber].collect()

    /*
    // without MongoPipeline wrapper

    val measurements = pipeline(Seq(
      "{$match: { floatSerialNo : '" + buoyId + "' }}",
      "{$limit: 1}"
    )).toDS[Buoy].collect()(0)

    val coordinates = pipeline(Seq(
      "{$match: { floatSerialNo: '" + buoyId + "' }}",
      "{$project: {'longitude': 1, 'latitude': 1}}"
    )).toDS[Coordinates].collect()
     */

    val result = PathAndLastMeasurements(measurements.PSAL, measurements.PRES, measurements.TEMP, coordinates)
    Ep2DataJsonWrapper(result)
  }
}



