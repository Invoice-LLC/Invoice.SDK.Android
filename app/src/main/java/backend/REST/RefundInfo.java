package backend.REST;

import backend.REST.common.*;
import com.fasterxml.jackson.core.JsonToken;

import java.util.Date;
import java.util.Map;

public class RefundInfo {
    public String id;
    public ORDER order;
    public REFUND_INFO refund;
    public PAYMENT_STATE status;
    public String payment_id;
    public PAYMENT_METHOD payment_method;
    public Date create_date;
    public Date update_date;
    public Map<String, JsonToken> custom_parameters;
    public String error;
    public String description;
}
