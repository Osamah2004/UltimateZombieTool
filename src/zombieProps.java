import org.json.JSONArray;
import org.json.JSONObject;
import java.util.regex.*;
import java.util.Iterator;

public class zombieProps {
    private String zombieAlias = "";
    private static jsonData data = new jsonData();
    private static JSONObject objdata;
    private static JSONObject object;
    private JSONArray array;
    private String zombieProps;

    private JSONArray conditions;
    public void setCondition(String condition, double value){
        for (int i = 0; i < conditions.length(); i++) {
            JSONObject jsonObject = conditions.getJSONObject(i);
            if (jsonObject.optString("Condition").equals(condition)){
                jsonObject.put("Percent",value);
            }
            objdata.put("ConditionImmunities",conditions);
        }
    }



    public zombieProps(){
        setAlias();
        this.conditions = getArray("ConditionImmunities");
    }

    public void setAlias() {
        JSONObject zombieStats;
        object = new JSONObject(data.getProps());
        zombieStats = object.getJSONObject("objdata");
        this.objdata = zombieStats;
    }

    public void setAlias(String alias){
        this.zombieAlias = alias;
        object.put("aliases",new String[] {zombieAlias});
    }

    public static void addObject(String key,Object value){
        objdata.put(key,value);
    }
    public void setObjclass(String key){
        object.put("objclass",key);
    }

    public void setArtCenter(double x,double y){
        JSONObject artCenter = objdata.getJSONObject("ArtCenter");
        artCenter.put("x",x);
        artCenter.put("y",y);
        objdata.put("ArtCenter",artCenter);
    }

    public static boolean isValidNumber(String input) {
        if (input.isEmpty()){return false;}
        // Define a regular expression pattern to match digits, dots, and an optional minus sign
        String pattern = "^-?[0-9.]*$";

        // Compile the pattern into a regex object
        Pattern regex = Pattern.compile(pattern);

        // Create a matcher object for the input string
        Matcher matcher = regex.matcher(input);

        // Return true if the input string matches the pattern, false otherwise
        return matcher.matches();
    }


    public static void add2Objdata(JSONObject value) {
        Iterator<String> keys = value.keys();
        while(keys.hasNext()){
            String key = keys.next();
            Object value_ = value.get(key);
            if (value_ instanceof JSONArray) {
                objdata.put(key, new JSONArray(value_.toString()));
            } else if (isValidNumber(value_.toString())) {
                objdata.put(key, Double.parseDouble(value_.toString()));
            }
            else if (!(isValidNumber(value_.toString()))){objdata.put(key,value_);};
        }
        objdata.remove("objclass");
    }


    public void setHitBox(int[] attack,int[] rect){
        String[] keys = {"mHeight", "mWidth", "mX", "mY"};
        JSONObject atk = objdata.getJSONObject("AttackRect");
        JSONObject hit = objdata.getJSONObject("HitRect");
        for (int i = 0; i < 4; i++) {
            atk.put(keys[i],attack[i]);
            hit.put(keys[i],rect[i]);
        }
        objdata.put("AttackRect",atk);
        objdata.put("HitRect",hit);
    }

    public void setBool(String key, boolean value){objdata.put(key,value);}
    public void setHP(double value){objdata.put("Hitpoints",value);}
    public void setSpeed(double value){objdata.put("Speed",value);}
    public void setDPS(double value){objdata.put("EatDPS",value);}
    public JSONObject getObject(String key){return objdata.getJSONObject(key);}
    public JSONArray getArray(String key){return objdata.getJSONArray(key);}

    public static void clearObject(){
        objdata.clear();
    }

    public String toString(int factor){
        object.put("objdata",objdata);
        return object.toString(factor)+",";
    }

}
