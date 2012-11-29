Executable Specifications for The Job Announcement
==================================================

This project's aim is to create [executable specifications](http://specificationbyexample.com/) for [The Job Announcement showcase](http://the-job-announcement.com/) ([GitHub repository](https://github.com/plexiti/the-job-announcement-fox)) using [ScalaTest](http://www.scalatest.org/).

```scala
...
class AppSpec extends FlatSpec with ShouldMatchers with Firefox {
  ...
  "Gonzo The Great" should "be able to request a new job announcement" in {
    go to (host + "/start.jsf")
    pageTitle should (include ("The control center"))
    
    switchToUser("Gonzo The Great")
    click on cssSelector("input[value='Request new announcement']")
    pageTitle should (endWith("New job anouncement"))
    
    // Fill in the "I need" and "Job Title" fields and submit the form
    textArea(TagNameQuery("textarea")).value = "An experienced Java software developer for our next product!"
    textField(CssSelectorQuery("input[name*='title']")).value = "A great Java developer wanted"  
    click on cssSelector("input[value='Request description']")  
    pageTitle should (include ("The control center"))
  }

  "Fozzie Bear" should "be able to describe the job announcement" in {
    go to (host + "/start.jsf")
    pageTitle should (include ("The control center"))
    
    switchToUser("Fozzie Bear")

    switchToTab("To describe");

    // FIXME: Since job announcements are sorted newest last, we should select the *last* 'Describe' button
    click on cssSelector("input[value='Describe']")
    pageTitle should (include("Describe job announcement"))

    textArea(CssSelectorQuery("textarea[name*='description']")).value = 
      """|- Java developer with 10+ years experience
         |- Good knowledge of open source frameworks
         |- Communication skills
         |""".stripMargin

    // Retrieve the job title from the form field
    val jobTitle = textField(CssSelectorQuery("input[name*='title']")).value
    
    // NOTE: We add the current date to the tweet to avoid rejection from the Twitter API of duplicate tweets  
    val now = new Date()
    textArea(CssSelectorQuery("textarea[name*='tweet']")).value = jobTitle + " (" + now + ")"
    
    textArea(CssSelectorQuery("textarea[name*='comment']")).value = "What do you think about the description?!"

    // Click the "Request review" button
    click on cssSelector("input[value='Request review']")
    
    pageTitle should (include ("The control center"))
  }

  "Gonzo The Great" should "be able to review a job announcement" in {
    go to (host + "/start.jsf")
    pageTitle should (include ("The control center"))
    
    switchToUser("Gonzo The Great")
    
    switchToTab("To review");

    // FIXME: Since job announcements are sorted newest last, we should select the *last* "Review" button
    click on cssSelector("input[name*='review']");
    pageTitle should (include("Review job announcement"))
    textArea(CssSelectorQuery("textarea[name*='comment']")).value = "Looks great! Publish it!"    
    click on cssSelector("input[value='Approve for publication']")
    pageTitle should (include ("The control center"))
  }
  
  "Fozzie Bear" should "be able to publish the job announcement" in {
    go to (host + "/start.jsf")
    pageTitle should (include ("The control center"))

    switchToUser("Fozzie Bear");
    switchToTab("To publish");

    // Click on "Publish" button
    click on cssSelector("input[name*='publish']")
    pageTitle should (include("Publish job announcement"))

    click on cssSelector("input[value='Publish']")

    switchToTab("Published");

    click on cssSelector("input[name*='published']")
    pageTitle should (include("Published job announcement"))  
  }
  ...
```

# Executing the specification yourself?

Follow these steps:

1. Install and start The Job Announcement application as described [here](https://github.com/plexiti/the-job-announcement-fox#building-the-showcase-yourself).
1. Make sure the app is running by pointing your browser to `http://localhost:8080/the-job-announcement`
1. Install 
    * the [sbt](http://www.scala-sbt.org) build tool for Scala and Java projects
    * the [Firefox web browser](http://www.mozilla.org/en-US/firefox/new/) 
1. Clone this repository with `git clone git@github.com:plexiti/the-job-announcement-specs.git`
1. `cd the-job-announcement-specs` and execute the specification with `sbt test`
1. If you want to open the project in Eclipse, do `sbt eclipse` 
