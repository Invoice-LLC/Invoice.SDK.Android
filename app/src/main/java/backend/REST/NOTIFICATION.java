package backend.REST;

import backend.REST.common.*;
import com.fasterxml.jackson.core.JsonToken;

import java.util.Date;
import java.util.Map;

public class NOTIFICATION {
    public NOTIFICATION_TYPE notification_type;
    public String id;
    public ORDER order;
    public PAYMENT_STATE status;
    public String status_description;
    public String ip;
    public PAYMENT_METHOD payment_method;
    public Date create_date;
    public Date update_date;
    public Date expire_date;
    public Map<String, JsonToken> custom_parameters;
    public String signature;
}
