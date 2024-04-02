package stepDefinations;

import java.io.IOException;

import io.cucumber.java.Before;

public class Hooks {
	
	@Before("@DeletePlace")
	public void beforeScenario()throws IOException
	{
		stepDefination stepDef=new stepDefination();
		if(stepDefination.place_id==null)
		{
		stepDef.add_place_payload("Sudipta","English","India","9856457520","www.sudipta.com",50);
		stepDef.user_calls_with_post_http_request("AddPlaceAPI", "POST");
		stepDef.verify_place_id_created_maps_to_using("Sudipta", "getPlaceAPI");
		
	}
	}

}
