Executable Specifications for The Job Announcement
==================================================

This project's aim is to create [executable specifications](http://specificationbyexample.com/) for [The Job Announcement showcase](http://the-job-announcement.com/) ([GitHub repository](https://github.com/plexiti/the-job-announcement-fox)) using [ScalaTest](http://www.scalatest.org/).

```scala
  ...
  "The app start page" should "have the correct title" in {
    go to (host + "/index.jsf")
    pageTitle should be ("The Job Announcement - Index")
  }
  
  "Clicking on the 'Start the role play!' link" should "take us to the control center page" in {
    go to (host + "/index.jsf")
	click on linkText("Start the role play!")
    pageTitle should be ("The Job Announcement - The control center")
  }
  
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

    // FIXME: Since job announcements are sorted newest last, select the *last* 'Describe' button
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

    // FIXME: Since job announcements are sorted newest last, select the *last* "Review" button
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
