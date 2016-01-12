/**
 * Copyright (C) 2005-2015 Rivet Logic Corporation.
 * 
 * This program is free software; you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free Software
 * Foundation; version 3 of the License.
 * 
 * This program is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
 * details.
 * 
 * You should have received a copy of the GNU General Public License along with
 * this program; if not, write to the Free Software Foundation, Inc., 51
 * Franklin Street, Fifth Floor, Boston, MA 02110-1301, USA.
 */
 
package com.liferay.content.targeting.rule.customfield;

import com.liferay.content.targeting.model.RuleInstance;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.util.StringPool;
import java.util.Map;

/**
 * @author joseross
 */
public abstract class BoolUtils {
    
    public static boolean evaluate(JSONObject jsonObj, Object currentValue) {
        Boolean attributeValueBL = jsonObj.getBoolean(Constants.ATTR_VAL_BOOL);
        return currentValue.equals(attributeValueBL);
    }
    
    public static void process(JSONObject jsonObj, Map<String, String> values) {
        String attributeValue = values.get(Constants.ATTR_VAL_BOOL);	
        jsonObj.put(Constants.ATTR_VAL_BOOL, attributeValue);
    }
    
    public static void defaults(Map<String, Object> context) {
        context.put(Constants.ATTR_VAL_BOOL, StringPool.BLANK);
    }
    
    public static void populate(JSONObject jsonObj, RuleInstance ruleInstance, 
        Map<String, Object> context, Map<String, String> values) {
        
        String attributeValueBL = StringPool.BLANK;
        if(!values.isEmpty()) {
            attributeValueBL = values.get(Constants.ATTR_VAL_BOOL);
        } else if(ruleInstance != null) {
            attributeValueBL = jsonObj.getString(Constants.ATTR_VAL_BOOL);
        }
        context.put(Constants.ATTR_VAL_BOOL, attributeValueBL);
    }
    
}