

import java.lang.reflect.Type;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.Locale;

import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONObject;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;


public class UtilityGson {
	
	private static final Logger log = Logger.getLogger(UtilityGson.class);
	public static final String FORMAT_DD_MM_YYYY = "dd/MM/yyyy";
	public static final String FORMAT_DD_MM_YYYY_HH_MM = "dd/MM/yyyy HH:mm";
	
	
	static final String[] dateFormats = {FORMAT_DD_MM_YYYY_HH_MM, FORMAT_DD_MM_YYYY};
	
	public static Gson getGson(){
		GsonBuilder gsonBuilder = new GsonBuilder().registerTypeAdapter(JSONObject.class, JSONObjectAdapter.sInstance)
				                  .registerTypeAdapter(JSONArray.class, JSONArrayAdapter.sInstance);
		
		gsonBuilder.registerTypeAdapter(Date.class, new JsonDeserializer<Date>() {	    	 
	    	  public Date deserialize(JsonElement jsonElement, Type typeOF, JsonDeserializationContext context) {
	    		 if("".equals(jsonElement.getAsString())){
	    			 return null;
	    		 }else{
		    		 for (String format : dateFormats) {
		 	            try {
		 	            	if(format.contains("EEE")) {
		 	            		return new SimpleDateFormat(format, Locale.US).parse(jsonElement.getAsString());
		 	            	}else {
		 	            		return new SimpleDateFormat(format, Locale.ITALIAN).parse(jsonElement.getAsString());
		 	            	}
		 	            } catch (ParseException e) {
		 	            	//caso standard, le prova tutte se nessuna va bene allora è un errore
		 	            	log.debug(Arrays.toString(e.getStackTrace()));
		 	            }
		 	        }
		    		log.error("Nessuna conversione valida per la data selezionata:" +jsonElement.getAsString());
	    		 }
	    		 return null;
	 	        
	    	  }
	    });
		
		return gsonBuilder.create();
	}
}
