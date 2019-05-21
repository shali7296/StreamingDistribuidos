package Example

import org.apache.spark._
import org.apache.spark.SparkContext._
//import org.apache.spark.streaming.{Seconds, StreamingContext}
import org.apache.spark.streaming.StreamingContext._
import org.apache.spark.streaming._
import org.apache.spark.streaming.twitter._
import org.apache.log4j.Level
import Utils._

object Main {

  def main(args: Array[String]) {

    // Configure Twitter credentials using twitter.txt
    setupTwitter()
    val ssc = new StreamingContext("local[*]", "Main", Seconds(1))
    setupLogging()

    // Create a DStream from Twitter using our streaming context
    val tweets = TwitterUtils.createStream(ssc, None)

    // Now extract the text of each status update into RDD's using map()
    val statuses = tweets.map(status => status.getText())
    statuses.print()

    ssc.start()
    ssc.awaitTermination()
  }
}
