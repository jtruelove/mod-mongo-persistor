package org.vertx.mods.mongo.test.integration.java;

import com.mongodb.BasicDBList;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import org.bson.types.ObjectId;
import org.junit.Test;
import org.vertx.java.core.json.JsonObject;
import org.vertx.mods.MongoUtil;

import java.util.Date;

import static org.junit.Assert.assertTrue;

/**
 * @author truelove@cyngn.com (Jeremy Truelove) 10/9/14
 */
public class MongoUtilTest {
    @Test
    public void testConvertBsonToJson() {
        String testValue = "{\"testKey\" : \"testValue\"}";
        DBObject mongoDbObject = MongoUtil.convertJsonToBson(testValue);
        Date date = new Date();
        mongoDbObject.put("created", date);

        JsonObject json = MongoUtil.convertBsonToJson(mongoDbObject);
        JsonObject value = json.getValue("created");
        assertTrue(value.getLong("$date") == date.getTime());
    }

    @Test
    public void testConvertJsonToBson() {
        ObjectId id = new ObjectId();
        DBObject mongoDBObject = new BasicDBObject();
        mongoDBObject.put("id", id);
        Date date = new Date();
        mongoDBObject.put("created", date);
        Integer [] numbers =  new Integer [] {1,2,3};
        mongoDBObject.put("values", numbers);

        JsonObject jsonObject = MongoUtil.convertBsonToJson(mongoDBObject);
        DBObject convertedObj = MongoUtil.convertJsonToBson(jsonObject);

        assertTrue(convertedObj.get("id").equals(id));
        BasicDBList list = (BasicDBList) convertedObj.get("values");

        for (int i = 0; i < list.size(); i++) {
            assertTrue(list.get(i) == numbers[i]);
        }
        assertTrue(convertedObj.get("created").equals(date));
    }
}
