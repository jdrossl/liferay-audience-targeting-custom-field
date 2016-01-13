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
public abstract class NumUtils {
    
    public static boolean evaluate(JSONObject jsonObj, Object currentValue) {
        double value = ((Number)currentValue).doubleValue();
        String strEQ = jsonObj.getString(Constants.ATTR_VAL_NUM);
        String strGT = jsonObj.getString(Constants.ATTR_VAL_NUM_GT);
        String strLT = jsonObj.getString(Constants.ATTR_VAL_NUM_LT);
        if(!strEQ.isEmpty()) {
            double eq = Double.parseDouble(strEQ);
            return value == eq;
        } else if(!strGT.isEmpty() && !strLT.isEmpty()) {
            double gt = Double.parseDouble(strGT);
            double lt = Double.parseDouble(strLT);
            return value >= gt && value <= lt;
        } else if(!strGT.isEmpty()) {
            double gt = Double.parseDouble(strGT);
            return value >= gt;
        } else if(!strLT.isEmpty()) {
            double lt = Double.parseDouble(strLT);
            return value <= lt;
        } else {
            return false;
        }
    }
    
    public static void process(JSONObject jsonObj, Map<String, String> values) {
        String attributeValueNUM = values.get(Constants.ATTR_VAL_NUM);
        String attributeValueLT = values.get(Constants.ATTR_VAL_NUM_LT);
        String attributeValueGT = values.get(Constants.ATTR_VAL_NUM_GT);
        jsonObj.put(Constants.ATTR_VAL_NUM, attributeValueNUM);
        jsonObj.put(Constants.ATTR_VAL_NUM_LT, attributeValueLT);
        jsonObj.put(Constants.ATTR_VAL_NUM_GT, attributeValueGT);
    }
    
    public static void defaults(Map<String, Object> context) {
        context.put(Constants.ATTR_VAL_NUM, StringPool.BLANK);
		context.put(Constants.ATTR_VAL_NUM_LT, StringPool.BLANK);
        context.put(Constants.ATTR_VAL_NUM_GT, StringPool.BLANK);
    }
    
    public static void populate(JSONObject jsonObj, RuleInstance ruleInstance, 
        Map<String, Object> context, Map<String, String> values) {
        
        String attributeValueNUM = StringPool.BLANK;
        String attributeValueLT = StringPool.BLANK;
        String attributeValueGT = StringPool.BLANK;
        if(!values.isEmpty()) {
            attributeValueNUM = values.get(Constants.ATTR_VAL_NUM);
            attributeValueLT = values.get(Constants.ATTR_VAL_NUM_LT);
            attributeValueGT = values.get(Constants.ATTR_VAL_NUM_GT);
        } else if(ruleInstance != null) {
            attributeValueNUM = jsonObj.getString(Constants.ATTR_VAL_NUM);
            attributeValueLT = jsonObj.getString(Constants.ATTR_VAL_NUM_LT);
            attributeValueGT = jsonObj.getString(Constants.ATTR_VAL_NUM_GT);
        }
        context.put(Constants.ATTR_VAL_NUM, attributeValueNUM);
        context.put(Constants.ATTR_VAL_NUM_LT, attributeValueLT);
        context.put(Constants.ATTR_VAL_NUM_GT, attributeValueGT);
    }
    
}