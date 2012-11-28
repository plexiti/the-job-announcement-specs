package com.plexiti.showcase.jobannouncement.tests.acceptance

import org.scalatest.selenium._
import org.scalatest._

class AppSpec extends FlatSpec with ShouldMatchers with Firefox {

  val host = "http://localhost:8080/the-job-announcement"
  
  "The app start page" should "have the correct title" in {
    go to (host + "/index.jsf")
    title should be ("The Job Announcement - Index")
  }
  
  "Clicking on the 'Start the role play!' link" should "take us to the control center page" in {
    go to (host + "/index.jsf")
	click on linkText("Start the role play!")
    title should be ("The Job Announcement - The control center")
  }
  
}