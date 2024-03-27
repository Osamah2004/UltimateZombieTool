import org.json.JSONObject;

import java.util.Iterator;

public class zombieTypes {
    public static JSONObject object;
    private static JSONObject objdata;
    private static jsonData data = new jsonData();

    //Methods
    public zombieTypes(){setType();}

/*    public JSONObject getPOPANIM(){
        JSONObject popanim = new JSONObject(data.getAnim());
        return popanim;
    }*/

    public void codeName(String name){object.put("aliases",new String[] {name});}
    public void setType(){
        JSONObject objects;
        objects = new JSONObject(data.getType());
        objects.put("aliases",new String[]{"custom"});
        this.object = objects;
        this.objdata = objects.getJSONObject("objdata");
    }

    public static void setObjdata(String value){
        JSONObject anim = new JSONObject(data.getAnim());
        objdata = anim.getJSONObject(value);
    }

    public static void add2Objdata(JSONObject json2) {
        Iterator<String> keys = json2.keys();
        while(keys.hasNext()){
            String key = keys.next();
            Object value = json2.get(key);
            objdata.put(key, value);//objdata is euivilant of json 1
        }
    }



    public void setPopanim(int type){
        JSONObject popanim = new JSONObject(data.getAnim());
        popanim = popanim.getJSONObject("POPANIM_Object");
        objdata.put("PopAnim",popanim.getString("" + type + ""));
    }
    public void setResources(int index){
        objdata.put("ResourceGroups",new String[]{data.resources[index]});
        switch (index){
            case 5: objdata.put("AudioGroups",new String[] {"ZombieDarkAgesBasicAudio"});break;
            case 10: objdata.put("AudioGroups",new String[] {"ZombieDinoBasicAudio"});break;
        }
    }

/*    public void setClass(String zombieClass) throws IOException {
        JSONObject class_ = getPOPANIM();
        class_ = class_.getJSONObject(zombieClass);
        object.put("objdata",class_);
    }*/
    public void setProps(String props){
        objdata.put("Properties","RTID("+ props +"@.)");
    }

    public String toString(int factor) {object.put("objdata",objdata);return object.toString(factor) + ",";}
}
