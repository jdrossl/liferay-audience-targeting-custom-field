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
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.StringPool;
import java.util.Map;
import java.util.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

/**
 * @author joseross
 */
public abstract class DateUtils {
    
    public static boolean evaluate(JSONObject jsonObj, Object currentValue) {
        SimpleDateFormat sdf = new SimpleDateFormat(Constants.DATE_FORMAT);
        try {
            Date currentDate = (Date) currentValue;
            String attributeDateStart = jsonObj.getString(Constants.ATTR_DATE_START);
            String attributeDateEnd = jsonObj.getString(Constants.ATTR_DATE_END);
            boolean result = false;
            if(!attributeDateStart.isEmpty() && !attributeDateStart.isEmpty()) {
                Date startDate = sdf.parse(attributeDateStart);
                Date endDate = sdf.parse(attributeDateEnd);
                result = currentDate.after(startDate) && currentDate.before(endDate);
            } else if(!attributeDateStart.isEmpty()) {
                Date startDate = sdf.parse(attributeDateStart);
                result = currentDate.after(startDate);
            } else if(!attributeDateEnd.isEmpty()) {
                Date endDate = sdf.parse(attributeDateEnd);
                result = currentDate.before(endDate);
            }
            return result;
        } catch(Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    
    public static void process(JSONObject jsonObj, Map<String, String> values) {
        String attributeDateStart = values.get(Constants.ATTR_DATE_START);	
        jsonObj.put(Constants.ATTR_DATE_START, attributeDateStart);
        String attributeDateEnd = values.get(Constants.ATTR_DATE_END);
        jsonObj.put(Constants.ATTR_DATE_END, attributeDateEnd);
    }
    
    public static void defaults(Map<String, Object> context) {
        context.put(Constants.ATTR_DATE_START, StringPool.BLANK);
        context.put(Constants.ATTR_DATE_END, StringPool.BLANK);
    }
    
    public static void populate(JSONObject jsonObj, RuleInstance ruleInstance, 
        Map<String, Object> context, Map<String, String> values) {
        
        String attributeDateStart = StringPool.BLANK;
        String attributeDateEnd = StringPool.BLANK;
        if(!values.isEmpty()) {
            attributeDateStart = values.get(Constants.ATTR_DATE_START);
            attributeDateEnd = values.get(Constants.ATTR_DATE_END);
        } else if(ruleInstance != null) {
            attributeDateStart = jsonObj.getString(Constants.ATTR_DATE_START);
            attributeDateEnd = jsonObj.getString(Constants.ATTR_DATE_END);
        }
        context.put(Constants.ATTR_DATE_START, attributeDateStart);
        context.put(Constants.ATTR_DATE_END, attributeDateEnd);
    }
    
}