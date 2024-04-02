Feature: Validating Place API's

@AddPlace
Scenario Outline: Verify if Place is being Successfully added using AddPlaceAPI
	Given Add Place Payload with "<name>" "<language>" "<address>" "<Phone_number>" "<Website>" <Accuracy>
	When user calls "AddPlaceAPI" with "Post" http request
	Then the API call is success with status code 200
	And "status" in response body is "OK"
	And "scope" in response body is "APP"
	And verify placeID created maps to "<name>" using "getPlaceAPI"
	
Examples:
	|name        | language |address                  |Phone_number      |Website                       |Accuracy|
	|Sudipta Bose| English  |29, side layout, cohen 09|(+91) 983 893 3937|https://rahulshettyacademy.com|50      |
#	|Username two| English  |30, side layout, cohen 10|(+91) 897 674 8979|https://rahulshettyacademy.com|50      |

@DeletePlace
Scenario: Verify if Delete Place functionality is working

	Given DeletePlace Payload
	When user calls "deletePlaceAPI" with "POST" http request
	Then the API call is success with status code 200
	 And "status" in response body is "OK"
	