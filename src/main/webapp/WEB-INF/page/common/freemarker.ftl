
<#-- 定义页面引用的JS和CSS文件 -->
<#macro header>
<#assign base=request.contextPath />
<link rel="shortcut icon" type="image/x-icon" href="${base}/resources/image/favicon.ico" />
<link href="${base}/resources/js/easyui/themes/default/easyui-min.css" rel="stylesheet"/>
<link href="${base}/resources/js/easyui/themes/icon-min.css" rel="stylesheet"/>
<link href="${base}/resources/css/common-min.css" rel="stylesheet"/>
<script src="${base}/resources/js/jquery-1.8.0.min.js"></script>
<script src="${base}/resources/js/easyui/jquery.easyui.min.js"></script>
<script src="${base}/resources/js/easyui/locale/easyui-lang-zh_CN.js"></script>
<script src="${base}/resources/js/artDialog/artDialog.js?skin=blue"></script>
<script src="${base}/resources/js/artDialog/iframeTools.js"></script>
<script src="${base}/resources/js/common.js"></script>
<script src="${base}/resources/js/ajaxfileupload.js"></script>
<script src="${base}/resources/js/checkInput-min.js"></script>
</#macro>

<#macro copyright date>
Copyright © 2011-${date},  All Rights Reserved.
</#macro>