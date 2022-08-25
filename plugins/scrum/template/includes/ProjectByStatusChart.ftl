<#--
Licensed to the Apache Software Foundation (ASF) under one
or more contributor license agreements.  See the NOTICE file
distributed with this work for additional information
regarding copyright ownership.  The ASF licenses this file
to you under the Apache License, Version 2.0 (the
"License"); you may not use this file except in compliance
with the License.  You may obtain a copy of the License at

http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing,
software distributed under the License is distributed on an
"AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
KIND, either express or implied.  See the License for the
specific language governing permissions and limitations
under the License.
-->
<div id="params_projectReport" style='display:none'>
    <INPUT type="HIDDEN" name="productId" value="${product.productId}"/>
</div>
<form id="form_projectReport" method="post"></form>
<script type="application/javascript">
    function loadViewerProjectReport(){
    var formObj = document.getElementById( "form_projectReport" );
    var paramContainer = document.getElementById("params_projectReport");
    var oParams = paramContainer.getElementsByTagName('input');
    if( oParams ) {
      for( var i=0;i<oParams.length;i++ ) {
        var param = document.createElement( "INPUT" );
        param.type = "HIDDEN";
        param.name= oParams[i].name;
        param.value= oParams[i].value;
        formObj.appendChild(param);
        
      }
    }
    formObj.action = "/birt/preview?__page=2&__report=component://scrum/webapp/scrum/reports/ProjectByStatusChart.rptdesign&__masterpage=true&__format=html";
    formObj.target = "projectReport";
    formObj.submit( );
    }
</script>
<iframe name="projectReport" frameborder="no"  scrolling = "auto"  style='height:350px;width:100%;' ></iframe>
<script type="application/javascript">loadViewerProjectReport();</script>
