package es.uniovi.asw;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class UserInfo {
	
	//private static final Logger log = LoggerFactory.getLogger(UserInfo.class);

    private final String email;
    private final String clave;
    
    @JsonCreator
	public UserInfo(@JsonProperty("email") String email, @JsonProperty("clave")String clave) {
    	//log.info("Creating user " + name + ". age: " + password);
        this.email = email;
        this.clave = clave;
    }

    public String getEmail() {
        return email;
    }

    public String getClave() {
        return clave;
    }
}