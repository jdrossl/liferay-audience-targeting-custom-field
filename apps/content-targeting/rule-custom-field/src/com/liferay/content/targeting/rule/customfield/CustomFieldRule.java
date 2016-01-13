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

import com.liferay.content.targeting.anonymous.users.model.AnonymousUser;
import com.liferay.content.targeting.api.model.BaseRule;
import com.liferay.content.targeting.api.model.Rule;
import com.liferay.content.targeting.model.RuleInstance;
import com.liferay.content.targeting.rule.categories.UserAttributesRuleCategory;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.util.StringPool;

import java.util.Locale;
import java.util.Map;

import javax.portlet.PortletRequest;
import javax.portlet.PortletResponse;

import javax.servlet.http.HttpServletRequest;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;

import com.liferay.portal.kernel.json.JSONException;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;

import java.util.List;
import java.util.ArrayList;
import java.util.HashMap;

import com.liferay.portlet.expando.model.ExpandoColumn;
import com.liferay.portlet.expando.model.ExpandoTable;
import com.liferay.portlet.expando.service.ExpandoColumnLocalServiceUtil;
import com.liferay.portlet.expando.service.ExpandoTableLocalServiceUtil;
import com.liferay.portlet.expando.model.ExpandoColumnConstants;

import com.liferay.portal.model.Company;
import com.liferay.portal.model.User;


/**
 * @author joseross
 */
@Component(immediate = true, service = Rule.class)
public class CustomFieldRule extends BaseRule {

	@Activate
	@Override
	public void activate() {
		super.activate();
	}

	@Deactivate
	@Override
	public void deActivate() {
		super.deActivate();
	}

	@Override
	public boolean evaluate(
			HttpServletRequest request, RuleInstance ruleInstance,
			AnonymousUser anonymousUser)
		throws Exception {

		JSONObject jsonObj = JSONFactoryUtil.createJSONObject(
			ruleInstance.getTypeSettings());
		boolean hasCustomFields = jsonObj.getBoolean(Constants.HAS_CUSTOM_FIELDS);
		User user = anonymousUser.getUser();
		
		if(hasCustomFields && user != null) {
			String attributeType = jsonObj.getString(Constants.ATTR_TYPE);
			String attributeName = jsonObj.getString(Constants.ATTR_NAME);
			
			Object currentValue = user.getExpandoBridge()
				.getAttribute(attributeName);

			if(attributeType.equals(Constants.TYPE_TXT)) {
				return TextUtils.evaluate(jsonObj, currentValue);
			} else if(attributeType.equals(Constants.TYPE_BOOL)) {
				return BoolUtils.evaluate(jsonObj, currentValue);
			} else if(attributeType.equals(Constants.TYPE_NUM)) {
				return NumUtils.evaluate(jsonObj, currentValue);
			} else if(attributeType.equals(Constants.TYPE_DATE)) {
				return DateUtils.evaluate(jsonObj, currentValue);
			} else {
				return false;
			}
		} else {
			return false;
		}
	}

	@Override
	public String getIcon() {
		return "icon-user";
	}

	@Override
	public String getRuleCategoryKey() {
		return UserAttributesRuleCategory.KEY;
	}

	@Override
	public String getSummary(RuleInstance ruleInstance, Locale locale) {
		return LanguageUtil.get(locale, ruleInstance.getTypeSettings());
	}

	@Override
	public String processRule(
		PortletRequest request, PortletResponse response, String id,
		Map<String, String> values) {

		JSONObject jsonObj = JSONFactoryUtil.createJSONObject();

		String hasCustomFields = values.get(Constants.HAS_CUSTOM_FIELDS);
		jsonObj.put(Constants.HAS_CUSTOM_FIELDS, hasCustomFields);
		
		if(Boolean.parseBoolean(hasCustomFields)) {
			String attributeType = values.get(Constants.ATTR_TYPE);
			jsonObj.put(Constants.ATTR_TYPE, attributeType);
			String attributeName = values.get(Constants.ATTR_NAME);
			jsonObj.put(Constants.ATTR_NAME, attributeName);
			
			if(attributeType.equals(Constants.TYPE_TXT)) {
				TextUtils.process(jsonObj, values);
			} else if(attributeType.equals(Constants.TYPE_BOOL)) {
				BoolUtils.process(jsonObj, values);	
			} else if(attributeType.equals(Constants.TYPE_NUM)) {
				NumUtils.process(jsonObj, values);
			} else if(attributeType.equals(Constants.TYPE_DATE)) {
				DateUtils.process(jsonObj, values);
			}
		}
		return jsonObj.toString();
	}

	private List<Map> populateFields(List<ExpandoColumn> columns) {
		List<Map> fields = new ArrayList<Map>();
		for(ExpandoColumn column : columns) {
			HashMap map = new HashMap();
			map.put(Constants.MAP_NAME, column.getName());
			String type = Constants.TYPE_TXT;
			int expandoType = column.getType();
			if(expandoType == ExpandoColumnConstants.BOOLEAN) {
				type = Constants.TYPE_BOOL;
			} else if(expandoType == ExpandoColumnConstants.FLOAT
				|| expandoType == ExpandoColumnConstants.INTEGER
				|| expandoType == ExpandoColumnConstants.LONG
				|| expandoType == ExpandoColumnConstants.NUMBER
				|| expandoType == ExpandoColumnConstants.DOUBLE
				|| expandoType == ExpandoColumnConstants.FLOAT) {
				type = Constants.TYPE_NUM;
			} else if(expandoType == ExpandoColumnConstants.DATE) {
				type = Constants.TYPE_DATE;
			}
			map.put(Constants.MAP_TYPE, type);
			fields.add(map);
		}
		return fields;
	}

	@Override
	protected void populateContext(
		RuleInstance ruleInstance, Map<String, Object> context,
		Map<String, String> values) {
		
		JSONObject jsonObj = null;
		if(ruleInstance != null) {
			try {
				String typeSettings = ruleInstance.getTypeSettings();
				jsonObj = JSONFactoryUtil.createJSONObject(
					typeSettings);
			} catch (JSONException jse) {
			}
		}
		String attributeName = StringPool.BLANK;
		String attributeType = StringPool.BLANK;
		boolean hasCustomFields = true;
		
		TextUtils.defaults(context);
		BoolUtils.defaults(context);
		NumUtils.defaults(context);
		DateUtils.defaults(context);
		
		try {
			Company company = (Company)context.get(Constants.MAP_COMPANY);
			long companyId = company.getCompanyId();
			ExpandoTable table = ExpandoTableLocalServiceUtil
				.getDefaultTable(companyId, User.class.getName());
			if(table != null) {
				long tableId = table.getTableId();
				List<ExpandoColumn> columns = ExpandoColumnLocalServiceUtil
					.getColumns(tableId);
				List<Map> fields = populateFields(columns);
				context.put(Constants.CUSTOM_FIELDS, fields);
				hasCustomFields = fields.size() > 0;
				
				if(!values.isEmpty()) {
					attributeName = values.get(Constants.ATTR_NAME);
					attributeType = values.get(Constants.ATTR_TYPE);
				} else if(ruleInstance != null) {
					attributeName = jsonObj.getString(Constants.ATTR_NAME);
					attributeType = jsonObj.getString(Constants.ATTR_TYPE);
				}
				
				if(attributeType.equals(Constants.TYPE_TXT)) {
					TextUtils.populate(jsonObj, ruleInstance, context, values);
				} else if(attributeType.equals(Constants.TYPE_BOOL)) {
					BoolUtils.populate(jsonObj, ruleInstance, context, values);
				} else if(attributeType.equals(Constants.TYPE_NUM)) {
					NumUtils.populate(jsonObj, ruleInstance, context, values);
				} else if(attributeType.equals(Constants.TYPE_DATE)) {
					DateUtils.populate(jsonObj, ruleInstance, context, values);
				}
				
				context.put(Constants.ATTR_NAME, attributeName);
				context.put(Constants.ATTR_TYPE, attributeType);
			}
		} catch (Exception e) {
			// No custom fields found
			//e.printStackTrace();
			hasCustomFields = false;
		}
		context.put(Constants.HAS_CUSTOM_FIELDS, hasCustomFields);
	}

}