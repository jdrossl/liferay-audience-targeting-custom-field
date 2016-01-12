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
public abstract class TextUtils {
    
    public static boolean evaluate(JSONObject jsonObj, Object currentValue) {
        String attributeValueTXT = jsonObj.getString(Constants.ATTR_VAL_TXT);
        String attributeOptTXT = jsonObj.getString(Constants.ATTR_OPT_TXT);
        if(attributeOptTXT.equals(Constants.TEXT_MATCH)) {
            return currentValue.equals(attributeValueTXT);
        } else if(attributeOptTXT.equals(Constants.TEXT_SUBSTR)) {
            return ((String) currentValue).contains(attributeValueTXT);
        } else {
            return false;
        }
    }
    
    public static void process(JSONObject jsonObj, Map<String, String> values) {
        String attributeValue = values.get(Constants.ATTR_VAL_TXT);	
        jsonObj.put(Constants.ATTR_VAL_TXT, attributeValue);
        String attributeOptTXT = values.get(Constants.ATTR_OPT_TXT);
        jsonObj.put(Constants.ATTR_OPT_TXT, attributeOptTXT);
    }
    
    public static void defaults(Map<String, Object> context) {
        context.put(Constants.ATTR_VAL_TXT, StringPool.BLANK);
        context.put(Constants.ATTR_OPT_TXT, Constants.TEXT_MATCH);
    }
    
    public static void populate(JSONObject jsonObj, RuleInstance ruleInstance, 
        Map<String, Object> context, Map<String, String> values) {
        
        String attributeValueTXT = StringPool.BLANK;
        String attributeOptTXT = Constants.TEXT_MATCH;
        if(!values.isEmpty()) {
            attributeValueTXT = values.get(Constants.ATTR_VAL_TXT);
            attributeOptTXT = values.get(Constants.ATTR_OPT_TXT);
        } else if(ruleInstance != null) {
            attributeValueTXT = jsonObj.getString(Constants.ATTR_VAL_TXT);
            attributeOptTXT = jsonObj.getString(Constants.ATTR_OPT_TXT);
        }
        context.put(Constants.ATTR_VAL_TXT, attributeValueTXT);
        context.put(Constants.ATTR_OPT_TXT, attributeOptTXT);
    }
    
}