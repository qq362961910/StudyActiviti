<%@ page contentType="text/html;charset=UTF-8" language="java" session="false" isELIgnored="false"
         pageEncoding="UTF-8" %>
<html>
<head>
    <title>用户任务</title>
    <%@include file="header.jsp" %>
</head>
<body>
<%@include file="banner.jsp" %>
<h3>可领取的任务:
    <button id="queryAvailableTaskBtn">刷新列表</button>
    <br/>
</h3>
<table width="80%" align="center" border="1">
    <thead>
    <tr>
        <th>id</th>
        <th>name</th>
        <th>category</th>
        <th>processDefinitionId</th>
        <th>executionId</th>
        <th>owner</th>
        <th>assignee</th>
        <th>createTime</th>
        <th>description</th>
        <th>操作</th>
    </tr>
    </thead>
    <tbody id="userTaskToBeClaim">
    </tbody>
</table>

<h3>已领取的任务:
    <button id="queryUserClaimedTaskBtn">刷新列表</button>
    <br/>
</h3>
<table width="80%" align="center" border="1">
    <thead>
    <tr>
        <th>id</th>
        <th>name</th>
        <th>category</th>
        <th>processDefinitionId</th>
        <th>executionId</th>
        <th>owner</th>
        <th>assignee</th>
        <th>createTime</th>
        <th>description</th>
        <th>操作</th>
    </tr>
    </thead>
    <tbody id="userTaskClaimed">
    </tbody>
</table>


<script type="text/javascript">
    function queryAvailableTask() {
        var queryUrl = "/user/task/available/list";
        var method = GET;
        var param = {};

        var queryAvailableTaskCallback = function (result) {
            if (result.success) {
                var tasks = result.data.tasks;
                if (tasks && tasks.length > 0) {
                    var tobody = $("#userTaskToBeClaim");
                    tobody.innerHTML = "";
                    for (var i=0; i<tasks.length; i++) {
                        var task = tasks[i];
                        var idTd = $.createTdWithText(task.id);
                        var nameTd = $.createTdWithText(task.taskName);
                        var categoryTd = $.createTdWithText(task.category);
                        var processDefinitionIdTd = $.createTdWithText(task.processDefinitionId);
                        var executionIdTd = $.createTdWithText(task.executionId);
                        var ownerTd = $.createTdWithText(task.owner ? task.owner.id : null);
                        var assigneeTd = $.createTdWithText(task.assignee);
                        var createTimeTd = $.createTdWithText(task.createTime);
                        var descriptionTd = $.createTdWithText(task.description);
                        var operationTdContent = "<a href='javascript:void(0);' onclick='claimUserTask(" + task.id + ")'>领取任务</a>&nbsp;&nbsp;";
                        var operationTd = $.createTdWithHtml(operationTdContent);
                        var tr = $.createTr();
                        tr.appendChild(idTd);
                        tr.appendChild(nameTd);
                        tr.appendChild(categoryTd);
                        tr.appendChild(processDefinitionIdTd);
                        tr.appendChild(executionIdTd);
                        tr.appendChild(ownerTd);
                        tr.appendChild(assigneeTd);
                        tr.appendChild(createTimeTd);
                        tr.appendChild(descriptionTd);
                        tr.appendChild(operationTd);
                        tobody.appendChild(tr);
                    }
                }
            }
            else {
                dealAjaxError(result);
            }
        };
        executeRequest(queryUrl, param, method, queryAvailableTaskCallback);
    }
    queryAvailableTask();
    $.registerEvent("queryAvailableTaskBtn", "click", queryAvailableTask);
</script>

<script type="text/javascript">
    function claimUserTask(taskId) {
        var queryUrl = "/user/task/claime/" + taskId;
        var method = POST;
        var param = {};
        var submitFormDataCallback = function (result) {
            if (result.success) {
                alert("领取任务成功");
            }
            else {
                dealAjaxError(result);
            }
        };
        executeRequest(queryUrl, param, method, submitFormDataCallback);
    }
</script>

<script type="text/javascript">
    function queryUserClaimedTask() {
        var queryUrl = "/user/task/claimed/list";
        var method = GET;
        var param = {};
        var queryUserClaimedTaskCallback = function (result) {
            if (result.success) {
                var tasks = result.data.tasks;
                if (tasks && tasks.length > 0) {
                    var tobody = $("#userTaskClaimed");
                    tobody.innerHTML = "";
                    for (var i=0; i<tasks.length; i++) {
                        var task = tasks[i];
                        var idTd = $.createTdWithText(task.id);
                        var nameTd = $.createTdWithText(task.taskName);
                        var categoryTd = $.createTdWithText(task.category);
                        var processDefinitionIdTd = $.createTdWithText(task.processDefinitionId);
                        var executionIdTd = $.createTdWithText(task.executionId);
                        var ownerTd = $.createTdWithText(task.owner ? task.owner.id : null);
                        var assigneeTd = $.createTdWithText(task.assignee);
                        var createTimeTd = $.createTdWithText(task.createTime);
                        var descriptionTd = $.createTdWithText(task.description);
                        var page = getUserTaskPageName(task.processDefinition.businessKey, task.taskDefinitionKey);
                        var operationTdContent = "<a target='_blank' href='/home/audit/" + page + "/"+ task.id +"'>处理任务</a>&nbsp;&nbsp;";
                        var operationTd = $.createTdWithHtml(operationTdContent);
                        var tr = $.createTr();
                        tr.appendChild(idTd);
                        tr.appendChild(nameTd);
                        tr.appendChild(categoryTd);
                        tr.appendChild(processDefinitionIdTd);
                        tr.appendChild(executionIdTd);
                        tr.appendChild(ownerTd);
                        tr.appendChild(assigneeTd);
                        tr.appendChild(createTimeTd);
                        tr.appendChild(descriptionTd);
                        tr.appendChild(operationTd);
                        tobody.appendChild(tr);
                    }
                }
            }
            else {
                dealAjaxError(result);
            }
        };
        executeRequest(queryUrl, param, method, queryUserClaimedTaskCallback);
    }
    queryUserClaimedTask();
    $.registerEvent("queryUserClaimedTaskBtn", "click", queryUserClaimedTask);
</script>
</body>
</html>
