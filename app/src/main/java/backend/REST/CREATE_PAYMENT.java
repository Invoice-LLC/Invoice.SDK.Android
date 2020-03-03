package backend.REST;

import backend.REST.common.*;

import java.util.List;
import java.util.Map;

public class CREATE_PAYMENT {
    public ORDER order;
    public SETTINGS settings;
    public Map<String, String> custom_parameters;
    public List<ITEM> receipt;
}
