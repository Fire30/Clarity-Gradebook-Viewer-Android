//TJ Corley
package com.fire30claritygradebook;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.HashMap;


public class LoginConnection {
	public static final String SITE = "http://ec2-174-129-141-39.compute-1.amazonaws.com/clarity/logon/";
	//probably should set up domain. w/e.
	private String school;
	private String username;
	private String password;
	private String schoolValue;
	private String errorMessage;
	private boolean isLoggedIn;
	private ClarityLoginJSONObject theJson;
	// set up variables
	
	public LoginConnection(String username,String password,String school)
	{
		this.username = username;
		this.password = password;
		this.school = school;
		this.schoolValue = schoolMap().get(school);
		// translates school name to the value passed in the url.
	}
	public void login()
	{
		try 
		{
			URL url = new URL(SITE+"?user="+username+"&pass="+password+"&school="+schoolValue);
			//format for my server's 'api'
			//maybe security issue?
			//would POSTing be better?
			StringBuilder theString = new StringBuilder();
			BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream(), "UTF-8"));
			String aux;
			while ((aux = reader.readLine()) != null) {
			    theString.append(aux);
			}
			//All JSON in one string
			setTheJson(new ClarityLoginJSONObject(theString.toString()));
			isLoggedIn = !getTheJson().getBoolean("Error");
			theJson.setSchool(school);
			theJson.setUsername(username);
			theJson.setPassword(password);
			//If there is an error it automatically means we are not logged in
			if(!isLoggedIn)
			{
				setErrorMessage(getTheJson().getString("Message"));
				//Sets the error message that is displayed in MainActivity on failed login.
			}
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
		}
	}
	public final static HashMap<String,String> schoolMap()
	{
		// I just hardcoded this because it gets updated once a year.
		//and no need to download everytime.
		//Source for getting it is included in /scripts
		//lol I'm too lazy to copy and paste from site...
		HashMap<String,String> map = new HashMap<String,String>();
		map.put("Academy of Science","693d6f73-c140-4ac3-85d1-68c33de795ff");
		map.put("Aldie Elementary","2da8a78c-aaa9-4ce0-81b1-5817f7af57b4");
		map.put("Algonkian Elementary","877f6bb2-93a7-4f87-a5c5-04a341c6d346");
		map.put("Arcola Elementary","49826805-9ea5-492e-8596-b26e8b1215db");
		map.put("Ashburn Elementary","516b506e-4fb0-468e-a006-111bf11b9fb9");
		map.put("Ball's Bluff Elementary","6c3ba54e-7a42-4927-971c-5b35676cbbeb");
		map.put("Banneker Elementary","58f276c4-d4d8-4766-8cf4-d5a8d9301f7b");
		map.put("Belmont Ridge Middle","72c5812c-4d19-4db7-a116-6a3bb96419c1");
		map.put("Belmont Station Elementary","eaae5575-bc8b-4f30-8669-a611baff7bdb");
		map.put("Blue Ridge Middle School","9f23d086-6dc2-400a-b10f-c8e4348059a8");
		map.put("Briar Woods High","da9a58d6-626a-4608-9587-f6cf7954b50a");
		map.put("Broad Run High","4cbfbd1f-3070-4d3f-a440-a33a98de46a8");
		map.put("Buffalo Trail Elementary","1400f4d5-64a7-4cbb-8890-c22ef3f64c9f");
		map.put("Catoctin Elementary","16dab45b-635d-4dd4-ab5f-9ce6c5a69705");
		map.put("Cedar Lane Elementary","0d1f78d1-e1a6-4afe-a22c-807e78d02d6a");
		map.put("Cool Spring Elementary","d9b047b1-21c0-40cc-b9d4-ead6bd6113af");
		map.put("Countryside Elementary","1b04d346-de63-440c-81fc-fb1eda064464");
		map.put("Creighton's Corner Elementary","3fed087f-2b97-4e82-808c-471149d0de02");
		map.put("Dominion High","15bec8b7-c56c-4b12-a539-8c1ddb59772e");
		map.put("Dominion Trail Elementary","1d7dba76-7be7-4653-82a3-02d33521d434");
		map.put("Douglass School","782021fb-d812-4173-8562-4581a3a50309");
		map.put("Eagle Ridge Middle","d90ee849-7c2f-4a40-82b2-eac9e55288dc");
		map.put("Emerick Elementary","41ca6046-5139-47ee-8d27-e94c72f9df89");
		map.put("Evergreen Mill Elementary","4a9808e7-9d20-48db-9ecf-7bf45d292400");
		map.put("Farmwell Station Middle","908123c7-2fb4-4557-a2ae-a16eec570d12");
		map.put("Forest Grove Elementary","f185606c-5812-444c-b3f1-3c9d28ffdab7");
		map.put("Frances Hazel Reid Elementary","4925a38d-37bc-4910-a95b-4458b94b0228");
		map.put("Frederick Douglass Elementary","0af1a8e0-c38f-4d71-85b7-e8fa8bdeda5f");
		map.put("Freedom High","2995c194-499c-47e7-979d-dcd54bb58d0f");
		map.put("Guilford Elementary","9c5c522c-1389-4da0-8bee-d1b72836bde8");
		map.put("Hamilton Elementary","eef0a31a-b9af-4fe0-b37d-ef95d7f829f8");
		map.put("Harmony Middle","d3482655-f534-4a9c-9291-96a7e8f32e5f");
		map.put("Harper Park Middle","6b96caaa-5bad-4b86-b41f-824e5e549c28");
		map.put("Heritage High","31d4df85-4aea-49a9-bf6a-4986e1c23708");
		map.put("Hillsboro Elementary","9cbeb9a6-2fd2-445b-8847-618737cbe4b0");
		map.put("Hillside Elementary","866548cc-43c0-49fe-8ed4-a0c58ef6ccd4");
		map.put("Horizon Elementary","8929beb9-dc0f-4e77-885b-f25765ce22de");
		map.put("Hutchison Farm Elementary","2a0fa7f6-21f7-414f-886f-00f666d6da38");
		map.put("J. L. Simpson Middle","567332cd-5fe3-42b8-9bd8-1f46ea04bd3a");
		map.put("J. Michael Lunsford Middle","26950609-427b-4756-a217-b1d67d717f43");
		map.put("John Champe High","6b1fae14-062d-4776-b80d-736d04d76f3a");
		map.put("John W. Tolbert Jr. Elementary","d5b3807d-5113-4a24-bd8d-ed0c7188476d");
		map.put("Kenneth W. Culbert Elementary","e89fab27-020c-4327-aac6-b50785509062");
		map.put("LCPS High School Training","2627b995-6f32-4e31-be10-0eb3ca8dfcc2");
		map.put("LCPS HS","191aba31-b74f-46a7-ad78-5c073c1d9cf2");
		map.put("LCPS Middle School Training","28bd0173-5eb3-4b88-81ab-e57b99777cd3");
		map.put("LCPS MS","ce8e78fc-70cd-45ab-a997-c7aa5ce22d58");
		map.put("Leesburg Elementary","036bb96e-7689-4543-a50c-4cfd012775a5");
		map.put("Legacy Elementary","e8fdb87f-7f3b-45b5-9ad5-d35f55e61922");
		map.put("Liberty Elementary","8bf07ad7-6c87-41ab-83ca-7dafa0ac82e5");
		map.put("Lincoln Elementary","830a0b35-be2d-41ef-b935-bfb4f40f5d46");
		map.put("Little River Elementary","9a4ccdaa-62fa-40f5-9b1d-e36b37bc2260");
		map.put("Loudoun County High","633599af-f236-4cdd-bbba-8d9a12991a93");
		map.put("Loudoun County Public Schools Training","95d16aff-c2a9-425d-8a76-ef12f1e64216");
		map.put("Loudoun Valley High","a236b5ca-9795-4ae0-95b1-306d1619baff");
		map.put("Lovettsville Elementary","b843abb3-4e52-46c1-9eb8-4f26132bdee0");
		map.put("Lowes Island Elementary","5d762159-8da1-49cd-8f8f-bd9a3540eb1a");
		map.put("Lucketts Elementary","1dededc8-d398-44ab-8ceb-06cb1d07573f");
		map.put("Meadowland Elementary","7acacdd4-af82-4fd1-a93a-058ab11c3fa2");
		map.put("Mercer Middle","2aa9aefd-c042-4188-8eb5-2cc6056994f6");
		map.put("Middleburg Elementary","a32f0422-507a-43fb-a4e9-39d580935eee");
		map.put("Mill Run Elementary","854a1498-7220-4d7e-9933-d0fbbc3f32ab");
		map.put("Monroe Technology Center","56b98593-2ef3-42da-acdf-2f1e9583cc4e");
		map.put("Mountain View Elementary","81fac6ea-c328-4fc4-b275-6a3ea3289216");
		map.put("Newton-Lee Elementary","570cb8ad-bc70-442d-ab53-170a725756c6");
		map.put("Park View High","c36f676a-0904-41bb-ba7b-a3c5b01dcd46");
		map.put("Pinebrook Elementary","f2644cea-62eb-4233-a92f-5a367835ecf0");
		map.put("Potomac Falls High","28dc61c4-f6b3-4599-957d-ebddcd667157");
		map.put("Potowmack Elementary","afebaf40-b704-4e10-aa23-efc1cb457a1c");
		map.put("River Bend Middle","0f151bb2-017a-4371-8f25-53c293d70c8e");
		map.put("Rolling Ridge Elementary","77e81618-c7c1-48ab-ad71-f44a4730b9b4");
		map.put("Rosa Lee Carter Elementary","2fd11b02-2eec-41e8-a454-538414a89580");
		map.put("Round Hill Elementary","624dd483-f6ce-4c76-8eaf-f551b4301c69");
		map.put("Sanders Corner Elementary","93f41aa9-4a10-472f-ae6d-6421974a0e63");
		map.put("Secondary Testing School","f8859309-a910-4a41-8794-b85f3365c60d");
		map.put("Seldens Landing Elementary","c2bc1a69-5061-4cdd-a9bb-0bd63be7c32d");
		map.put("Seneca Ridge Middle","cf9fd932-8f07-465a-b5f7-d9ff859c2f0d");
		map.put("Smart's Mill Middle","b01527ff-bd07-4277-8cf0-d1d838e75ad2");
		map.put("Sterling Elementary","9a51f6e7-e6d2-49b6-bab6-c7b3c65bc6fc");
		map.put("Sterling Middle","fb899f63-a022-4ca6-928c-368c89fa55fb");
		map.put("Steuart W. Weller Elementary","87a23ccc-3238-4b1b-9751-89f0487f72d5");
		map.put("Stone Bridge High","9fb9ab88-81ad-4943-adce-d2acae684021");
		map.put("Stone Hill Middle","a830467c-c747-49b1-aae6-f62adb44793a");
		map.put("Sugarland Elementary","91d6ad51-2498-49a3-a864-51507c65d52a");
		map.put("Sully Elementary","8131d6c1-256a-4421-86ed-f3a1b9e17527");
		map.put("Summer School HS-2 (Park View)","5ee2f4a9-6f7f-4045-bb38-0609dcee2b20");
		map.put("Summer School HS-3 (Heritage)","69d33e92-bbef-4568-a872-4af68c1b3322");
		map.put("Summer School MS-1","7900285e-ac99-4118-90d0-75fa05c56c2a");
		map.put("Sycolin Creek Elementary","d762374a-e3ea-4053-9caa-77e947c8e1b6");
		map.put("TST School","055256c1-a8d8-4a72-aa2b-48784e8eb405");
		map.put("Tuscarora High School","8c41184f-82b2-4398-85cc-21ef475d0c7f");
		map.put("Waterford Elementary","1a739666-a743-45fb-b56b-42d5123fe50f");
		map.put("Woodgrove High","4dfd3a43-57fe-4d98-b530-986a40e81405");
		return map;
	}
	public boolean isLoggedIn() {
		return isLoggedIn;
	}
	public void setLoggedIn(boolean isLoggedIn) {
		this.isLoggedIn = isLoggedIn;
	}
	public String getErrorMessage() {
		return errorMessage;
	}
	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}
	public ClarityLoginJSONObject getTheJson() {
		return theJson;
	}
	public void setTheJson(ClarityLoginJSONObject theJson) {
		this.theJson = theJson;
	}
}
