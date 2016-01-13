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

/**
 * @author joseross
 */
public interface Constants {
    
    // --- MAP FIELD NAMES ---
    String MAP_NAME = "name";
    String MAP_TYPE = "type";
    String MAP_COMPANY = "company";
    
    // --- CUSTOM FIELD TYPES ---
    String TYPE_TXT = "text";
    String TYPE_NUM = "numeric";
    String TYPE_BOOL = "boolean";
    String TYPE_DATE = "date";
    
    // --- CUSTOM FIELDS ATTRIBUTE  ---
    String HAS_CUSTOM_FIELDS = "hasCustomFields";
    String CUSTOM_FIELDS = "customFields";
    
    // --- SELECTED CUSTOM FIELD ---
    String ATTR_TYPE = "attributeType";
    String ATTR_NAME = "attributeName";
    
    // --- TEXT TYPE ATTRIBUTES ---
    String ATTR_VAL_TXT = "attributeValueTXT";
    String ATTR_OPT_TXT = "attributeOptTXT";
    String TEXT_MATCH = "match";
    String TEXT_SUBSTR = "substr";
    
    // --- BOOL TYPE ATTRIBUTES ---
    String ATTR_VAL_BOOL = "attributeValueBL";
    
    // --- NUM TYPE ATTRIBUTES ---
    String ATTR_VAL_NUM = "attributeValueNUM";
    String ATTR_VAL_NUM_GT = "attributeValueGT";
    String ATTR_VAL_NUM_LT = "attributeValueLT";

    // --- DATE TYPE ATTRIBUTES ---
    String ATTR_DATE_START = "attributeDateStart";
    String ATTR_DATE_END = "attributeDateEnd";
    String DATE_FORMAT = "MM/dd/yyyy";
 
}