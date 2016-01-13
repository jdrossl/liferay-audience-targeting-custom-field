<#assign aui = PortletJspTagLibs["/META-INF/aui.tld"] />
<#assign liferay_ui = PortletJspTagLibs["/META-INF/liferay-ui.tld"] />
<#assign portlet = PortletJspTagLibs["/META-INF/liferay-portlet.tld"] />

<#setting number_format="computer">

<@aui["input"] type="hidden" name="hasCustomFields" value=hasCustomFields></@>

<@aui["script"]>
    var types = {
        <#if hasCustomFields>
            <#list customFields as field>
            "${field.name}":"${field.type}"<#if field_has_next>,</#if>
            </#list>
        </#if>
    };
    function updateFieldType(select) {
        var field = select.value;
        var type = types[field];
        var A = AUI();
        A.all('.rule-fieldset').addClass("hidden");
        if(type) {
            A.one('#<@portlet["namespace"]/>' + type + 'Fieldset').removeClass("hidden");
            A.one('#<@portlet["namespace"]/>attributeType').attr('value', type);
        }
    }
</@>
<#if hasCustomFields>
    <@aui["input"] type="hidden" name="attributeType" value="${attributeType}" />
    <@aui["fieldset"] id="dropdownFieldset">
        <@aui["select"] name="attributeName" onchange="updateFieldType(this)" required=true>
            <@aui["option"] selected=(attributeName == '')>Select a field</@>
            <#list customFields as field>
                <@aui["option"] value="${field.name}" selected=(attributeName == field.name)>${field.name}</@>
            </#list>
        </@>
    </@>
    <#if attributeType!="text"><#assign textHidden="hidden"/><#else><#assign textHidden=""/></#if>
    <@aui["fieldset"] id="textFieldset" cssClass="rule-fieldset ${textHidden}">
        <@aui["input"] name="attributeValueTXT" value=attributeValueTXT label="attribute-value"></@>
        <@aui["field-wrapper"] name="attribute-options">
            <@aui["input"] inlineLabel="right" name="attributeOptTXT" type="radio" value="match" label="text-match" checked=(attributeOptTXT=="match")/>
            <@aui["input"] inlineLabel="right" name="attributeOptTXT" type="radio" value="substr" label="text-substr" checked=(attributeOptTXT=="substr")/>
        </@>
        
    </@>
    <#if attributeType!="boolean"><#assign boolHidden="hidden"/><#else><#assign boolHidden=""/></#if>
    <@aui["fieldset"] id="booleanFieldset" cssClass="rule-fieldset ${boolHidden}">
        <@aui["field-wrapper"] name="attributeValue">
            <@aui["input"] inlineLabel="right" name="attributeValueBL" type="radio" value="true" label="bool-true" checked=(attributeValueBL == "true") />
            <@aui["input"] inlineLabel="right" name="attributeValueBL" type="radio" value="false" label="bool-false" checked=(attributeValueBL == "false") />
        </@>
    </@>
    <#if attributeType!="numeric"><#assign numericHidden="hidden"/><#else><#assign numericHidden=""/></#if>
    <@aui["fieldset"] id="numericFieldset" cssClass="rule-fieldset ${numericHidden}">
        <@aui["field-wrapper"] name="attributeValue">
            <div class="alert alert-info">
              <@liferay_ui["message"] key="number-options-help" />
            </div>
            <@aui["input"] name="attributeValueNUM" value="${attributeValueNUM}" label="number-eq" disabled=(attributeValueGT!='' || attributeValueLT!='')>
                <@aui["validator"] name="number" />
            </@>
            <@aui["input"] name="attributeValueGT" value="${attributeValueGT}" label="number-gt" disabled=(attributeValueNUM!='')>
                <@aui["validator"] name="number" />
            </@>
            <@aui["input"] name="attributeValueLT" value="${attributeValueLT}" label="number-lt" disabled=(attributeValueNUM!='')>
                <@aui["validator"] name="number" />
            </@>
        </@>
    </@>
    <#if attributeType!="date"><#assign dateHidden="hidden"/><#else><#assign dateHidden=""/></#if>
    <@aui["fieldset"] id="dateFieldset" cssClass="rule-fieldset ${dateHidden}">
        <@aui["field-wrapper"] name="attributeValue">
            <div class="alert alert-info">
              <@liferay_ui["message"] key="date-options-help" />
            </div>
            <@aui["input"] name="attributeDateStart" value="${attributeDateStart}" label="date-start">
                <@aui["validator"] name="date" />
            </@>
            <@aui["input"] name="attributeDateEnd" value="${attributeDateEnd}" label="date-end">
                <@aui["validator"] name="date" />
            </@>
        </@>
    </@>
    <@aui["script"] use="event-base,aui-datepicker">
        A.ready(function(){
            new A.DatePicker({
                trigger: '#<@portlet["namespace"]/>attributeDateStart',
                popover: {
                  zIndex: 2000
                }
            });
            new A.DatePicker({
                trigger: '#<@portlet["namespace"]/>attributeDateEnd',
                popover: {
                  zIndex: 2000
                }
            });
            A.one('#<@portlet["namespace"]/>attributeValueNUM')
                .on('valuechange', function(evt) {
                    A.all('#<@portlet["namespace"]/>attributeValueGT,#<@portlet["namespace"]/>attributeValueLT').attr('disabled', evt.newVal);
                });
            A.all('#<@portlet["namespace"]/>attributeValueGT,#<@portlet["namespace"]/>attributeValueLT')
                .on('valuechange', function(evt) {
                    var gtVal = A.one('#<@portlet["namespace"]/>attributeValueGT').val();
                    var ltVal = A.one('#<@portlet["namespace"]/>attributeValueLT').val();
                    A.one('#<@portlet["namespace"]/>attributeValueNUM').attr('disabled', gtVal || ltVal);
                });
        });
    </@>
<#else>
    <div class="alert alert-error">
      <strong><@liferay_ui["message"] key="no-custom-fields-found" /></strong>
    </div>
</#if>