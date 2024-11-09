#Author: your.email@your.domain.com
#Keywords Summary :
#Feature: List of scenarios.
#Scenario: Business rule through list of steps with arguments.
#Given: Some precondition step
#When: Some key actions
#Then: To observe outcomes or validation
#And,But: To enumerate more Given,When,Then steps
#Scenario Outline: List of steps for data-driven as an Examples and <placeholder>
#Examples: Container for s table
#Background: List of steps run before each of the scenarios
#""" (Doc Strings)
#| (Data Tables)
#@ (Tags/Labels):To group Scenarios
#<> (placeholder)
#""
## (Comments)
#Sample Feature Definition Template


#@tag
#Feature: Title of your feature
 # I want to use this template for my feature file

  #@tag1
  #Scenario: Title of your scenario
   # Given I want to write a step with precondition
    #And some other precondition
    #When I complete action
    #And some other action
    #And yet another action
    #Then I validate the outcomes
    #And check more outcomes

 # @tag2
  #Scenario Outline: Title of your scenario outline
   #When I check for the <value> in step
    #Then I verify the <status> in step

   # Examples: 
    #  | name  | value | status  |
     # | name1 |     5 | success |
      #| name2 |     7 | Fail    |




@tag
Feature: Purchase the order from Ecommerce Website
  I want to use this template for my feature file

	Background:
	Given I landed on Ecommerce Page

  @Regression
  Scenario Outline: Positive Test of Submitting the order
  
    Given Logged in with username <name> and password <password>
    When I add product <productName> to Cart
    And Checkout <productName> and submit the order
    Then "THANKYOU FOR THE ORDER." message is displayed on ConfirmationPage


    Examples: 
      | name  								|  password		    |	productName |
      | nadoliam@gmail.com    |  Zxcv%4321      | ZARA COAT 3 | 
